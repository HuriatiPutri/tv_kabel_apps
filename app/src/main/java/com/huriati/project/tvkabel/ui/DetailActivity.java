package com.huriati.project.tvkabel.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.dicoding.picodiploma.mynavigationdrawer.R;
import com.huriati.project.tvkabel.SharedPrefManager;
import com.huriati.project.tvkabel.adapter.PelangganAdapter;
import com.huriati.project.tvkabel.adapter.TagihanAdapter;
import com.huriati.project.tvkabel.api.BaseApiService;
import com.huriati.project.tvkabel.api.UtilsApi;
import com.huriati.project.tvkabel.model.Pelanggan;
import com.huriati.project.tvkabel.model.PelangganResponse;
import com.huriati.project.tvkabel.model.Tagihan;
import com.huriati.project.tvkabel.model.TagihanResponse;

import java.util.List;

public class DetailActivity extends AppCompatActivity {

    public static TextView txtId, txtNama, txtHp, txtAlamat;
    RecyclerView list;

    Context context;
    BaseApiService mApiService;
    ProgressDialog loading;
    SharedPrefManager sharedPrefManager;
    ImageButton ic_telp;
    String idPel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        txtId = findViewById(R.id.idPel);
        txtNama = findViewById(R.id.namaPel);
        txtHp = findViewById(R.id.noTelp);
        txtAlamat = findViewById(R.id.alamat);
        list = findViewById(R.id.list);
        ic_telp = findViewById(R.id.ic_telp);

        idPel = getIntent().getStringExtra("idPel");
        String namaPel = getIntent().getStringExtra("namaPel");
        String hp = getIntent().getStringExtra("hp");
        String alamat = getIntent().getStringExtra("alamat");

        txtId.setText(idPel);
        txtNama.setText(namaPel);
        txtHp.setText(hp);
        txtAlamat.setText(alamat);

        list.setHasFixedSize(true);
        list.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        context = getApplicationContext();
        sharedPrefManager = new SharedPrefManager(context);

        mApiService = UtilsApi.getAPIService(sharedPrefManager.getAuth());


        mApiService = UtilsApi.getAPIService(sharedPrefManager.getAuth());
        getTagihan(idPel);
    }

    private void getTagihan(String id) {
//        loading = ProgressDialog.show(getApplicationContext(), null, "Loading...", true, false);

        mApiService.tagihanRequest(id).enqueue(new Callback<TagihanResponse>() {
            @Override
            public void onResponse(Call<TagihanResponse> call, Response<TagihanResponse> response) {
                if(response.isSuccessful()){
//                    loading.dismiss();
                    final List<Tagihan> semuaData = response.body().getTagihan();
                    list.setAdapter(new TagihanAdapter(getApplicationContext(), semuaData));

                }else{
//                    loading.dismiss();
                    Toast.makeText(context, "No Data", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<TagihanResponse> call, Throwable t) {
//                loading.dismiss();
                Toast.makeText(context, "No Internet Connection" + t, Toast.LENGTH_SHORT).show();

            }
        });
    }

    public void telpon(View view) {
        Uri number = Uri.parse("tel:"+txtHp.getText().toString());
        Intent callIntent = new Intent(Intent.ACTION_DIAL, number);
        startActivity(callIntent);
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        getTagihan(idPel);
    }
}
