package com.huriati.project.tvkabel.ui.home;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import com.dicoding.picodiploma.mynavigationdrawer.R;
import com.huriati.project.tvkabel.MainActivity;
import com.huriati.project.tvkabel.SharedPrefManager;
import com.huriati.project.tvkabel.adapter.PelangganAdapter;
import com.huriati.project.tvkabel.api.BaseApiService;
import com.huriati.project.tvkabel.api.UtilsApi;
import com.huriati.project.tvkabel.model.Pelanggan;
import com.huriati.project.tvkabel.model.PelangganResponse;
import com.huriati.project.tvkabel.model.TagihanResponse;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class HomeFragment extends Fragment {


    private HomeViewModel homeViewModel;
    Context context;
    BaseApiService mApiService;
    ProgressDialog loading;
    SharedPrefManager sharedPrefManager;
    TextView total_tagihan;

    RecyclerView list;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        context = getContext();
        sharedPrefManager = new SharedPrefManager(context);

        total_tagihan = root.findViewById(R.id.total_tagihan);
        list = root.findViewById(R.id.list);
        list.setHasFixedSize(true);
        list.setLayoutManager(new LinearLayoutManager(context));

        mApiService = UtilsApi.getAPIService(sharedPrefManager.getAuth());
        homeViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                loading = ProgressDialog.show(context, null, "Loading...", true, false);
                getPelanggan(sharedPrefManager.getSpWilayah());
                sendKolektor();
                totalTagihan();
            }
        });
        return root;
    }

    private void getPelanggan(String wilayah) {

        mApiService.pelangganRequest(wilayah).enqueue(new Callback<PelangganResponse>() {
            @Override
            public void onResponse(Call<PelangganResponse> call, Response<PelangganResponse> response) {
                if(response.isSuccessful()){
                    loading.dismiss();
                    final List<Pelanggan> semuaData = response.body().getData();
                    list.setAdapter(new PelangganAdapter(context, semuaData));

                }else{
                    loading.dismiss();
                    Toast.makeText(context, "No Data", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<PelangganResponse> call, Throwable t) {
                loading.dismiss();
                Toast.makeText(context, "No Internet Connection" + t, Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void sendKolektor(){
        mApiService.kolektorRequest(sharedPrefManager.getSPId(),getCurrentDate()).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.isSuccessful()){
                    Log.i("TAG", "Response Berhasil");
                    try {
                        JSONObject jsonRESULTS = new JSONObject(response.body().string());
                        if (jsonRESULTS.getString("status").equals("1")) {

                            String id = jsonRESULTS.getJSONObject("data").getString("id");

                            sharedPrefManager.saveSPString(SharedPrefManager.SP_IDTAGIHAN, id);

                        } else {
                            // Jika login gagal
                            String error_message = jsonRESULTS.getString("error_code");
                            Toast.makeText(context, error_message, Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }

    private void totalTagihan() {
        mApiService.totalRequest(sharedPrefManager.getSPId()).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.isSuccessful()){
                    try {
                        JSONObject jsonRESULTS = new JSONObject(response.body().string());
                        if (jsonRESULTS.getString("status").equals("1")) {

                           total_tagihan.setText("Rp. "+ jsonRESULTS.getString("tagihan"));

                        } else {
                            // Jika login gagal
                            String error_message = jsonRESULTS.getString("status");
                            Toast.makeText(context, error_message, Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }


    private String getCurrentDate() {
        Date current = new Date();
        SimpleDateFormat frmt = new SimpleDateFormat("yyyy-MM-dd");
        String dateString = frmt.format(current);
        return dateString;
    }

    @Override
    public void onResume() {
        super.onResume();
        getPelanggan(sharedPrefManager.getSpWilayah());
        totalTagihan();
    }
}