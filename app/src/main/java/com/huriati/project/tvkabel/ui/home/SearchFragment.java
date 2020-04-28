package com.huriati.project.tvkabel.ui.home;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.dicoding.picodiploma.mynavigationdrawer.R;
import com.huriati.project.tvkabel.SharedPrefManager;
import com.huriati.project.tvkabel.adapter.PelangganAdapter;
import com.huriati.project.tvkabel.api.BaseApiService;
import com.huriati.project.tvkabel.api.UtilsApi;
import com.huriati.project.tvkabel.model.Pelanggan;
import com.huriati.project.tvkabel.model.PelangganResponse;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchFragment extends Fragment {

    public static String KEY_ACTIVITY = "msg_activity";

    private HomeViewModel homeViewModel;
    Context context;
    BaseApiService mApiService;
    ProgressDialog loading;
    SharedPrefManager sharedPrefManager;
    TextView total_tagihan;

    RecyclerView list;

    public static SearchFragment newInstance(String messeage) {
        SearchFragment fragment = new SearchFragment();
        Bundle bundle = new Bundle();
        bundle.putString(KEY_ACTIVITY, messeage);
        fragment.setArguments(bundle);
        return fragment;
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_search, container, false);
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
                totalTagihan();
                try {
                    assert getArguments() != null;
                    String message = getArguments().getString(KEY_ACTIVITY);
                    if (message != null) {
                        list.setAdapter(null);
                        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
//                        getPelangganSearch(message, sharedPrefManager.getSpWilayah());
                    } else {
                        list.setAdapter(null);
                    }

                } catch (Exception ex) {
                    ex.printStackTrace();
                }

            }
        });
        return root;
    }

        private void getPelangganSearch(String idPel, String wilayah, int page) {

        mApiService.pelangganRequest(idPel,wilayah, page).enqueue(new Callback<PelangganResponse>() {
            @Override
            public void onResponse(Call<PelangganResponse> call, Response<PelangganResponse> response) {
//                list.setAdapter(null);
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
//        getPelanggan(sharedPrefManager.getSpWilayah());
        totalTagihan();
    }
}