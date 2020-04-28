package com.huriati.project.tvkabel.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.widget.Toast;

import com.dicoding.picodiploma.mynavigationdrawer.R;
import com.huriati.project.tvkabel.SharedPrefManager;
import com.huriati.project.tvkabel.adapter.PelangganAdapter;
import com.huriati.project.tvkabel.api.BaseApiService;
import com.huriati.project.tvkabel.api.UtilsApi;
import com.huriati.project.tvkabel.model.Pelanggan;
import com.huriati.project.tvkabel.model.PelangganResponse;

import java.util.List;

public class SearchActivity extends AppCompatActivity {


    Context context;
    BaseApiService mApiService;
    ProgressDialog loading;
    SharedPrefManager sharedPrefManager;
    RecyclerView list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        String query = getIntent().getStringExtra("query");

        sharedPrefManager = new SharedPrefManager(this);
        mApiService = UtilsApi.getAPIService(sharedPrefManager.getAuth());

        list = findViewById(R.id.list);
        list.setHasFixedSize(true);
        list.setLayoutManager(new LinearLayoutManager(context));

        getPelangganSearch(query, sharedPrefManager.getSpWilayah(), 1);
    }

    private void getPelangganSearch(String idPel, String wilayah, int page) {
        loading = ProgressDialog.show(this, null, "Loading...", true, false);

        mApiService.pelangganRequest(idPel,wilayah, page).enqueue(new Callback<PelangganResponse>() {
            @Override
            public void onResponse(Call<PelangganResponse> call, Response<PelangganResponse> response) {
//                list.setAdapter(null);
                if(response.isSuccessful()){
                    loading.dismiss();
                    final List<Pelanggan> semuaData = response.body().getData();
                    list.setAdapter(new PelangganAdapter(getApplicationContext(), semuaData));
                }else{
                    loading.dismiss();
                    Toast.makeText(getApplicationContext(), "No Data", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<PelangganResponse> call, Throwable t) {
                loading.dismiss();
                Toast.makeText(context, "No Internet Connection" + t, Toast.LENGTH_SHORT).show();

            }
        });
    }

}
