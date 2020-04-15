package com.huriati.project.tvkabel.ui;

import androidx.appcompat.app.AppCompatActivity;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.dicoding.picodiploma.mynavigationdrawer.R;
import com.huriati.project.tvkabel.SharedPrefManager;
import com.huriati.project.tvkabel.api.BaseApiService;
import com.huriati.project.tvkabel.api.UtilsApi;

import java.text.SimpleDateFormat;
import java.util.Date;

public class BayarActivity extends AppCompatActivity {

    TextView txtbulan, txtNominal, txtTotal, txtNamapel, txtIdpel;


    BaseApiService mApiService;
    ProgressDialog loading;
    SharedPrefManager sharedPrefManager;
    String idTagihan,idPel, nominal;
    Button btnBayar, btnCetak;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bayar);

        sharedPrefManager = new SharedPrefManager(this);
        mApiService = UtilsApi.getAPIService(sharedPrefManager.getAuth());

        txtNamapel = findViewById(R.id.namaPel);
        txtIdpel = findViewById(R.id.idPel);
        txtbulan = findViewById(R.id.bulan);
        txtNominal = findViewById(R.id.nominal);
        txtTotal = findViewById(R.id.total_bayar);
        btnBayar = findViewById(R.id.bayar);
        btnCetak = findViewById(R.id.cetak);

        String status = getIntent().getStringExtra("status");
        String bulan = getIntent().getStringExtra("bulan");
        String nama = getIntent().getStringExtra("nama");
        idPel = getIntent().getStringExtra("idPel");
        nominal = getIntent().getStringExtra("nominal");
        idTagihan = getIntent().getStringExtra("idTagihan");

        txtbulan.setText(bulan);
        txtNamapel.setText(nama);
        txtIdpel.setText(idPel);
        txtNominal.setText("Rp. "+ nominal);
        txtTotal.setText("Rp. "+nominal);

        if(status.equalsIgnoreCase("1")){
            btnBayar.setEnabled(false);
            btnCetak.setEnabled(true);
        }else{
            btnBayar.setEnabled(true);
            btnCetak.setEnabled(false);
        }
    }

    public void bayar(View view) {

        String ambilBulan = (getCurrentDate()).substring(6,7);
        mApiService.tagihanDetailRequest(
                sharedPrefManager.getSpIdkolektor(),
                idTagihan,
                idPel,
                getCurrentDate(),
                ambilBulan,
                Integer.parseInt(nominal)).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.isSuccessful()){
                    Toast.makeText(getApplicationContext(), "Telah dibayar", Toast.LENGTH_SHORT).show();
                    btnCetak.setEnabled(true);
                    btnBayar.setEnabled(false);

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
}
