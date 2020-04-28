package com.huriati.project.tvkabel;

import androidx.appcompat.app.AppCompatActivity;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.dicoding.picodiploma.mynavigationdrawer.R;
import com.huriati.project.tvkabel.api.BaseApiService;
import com.huriati.project.tvkabel.api.UtilsApi;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class LoginActivity extends AppCompatActivity {

    private EditText edtEmail;
    private EditText edtPassword;

    private Button btnLogin;

    ProgressDialog loading;

    SharedPrefManager sharedPrefManager;
    SharedPrefProfil sharedPrefProfil;
    Context mContext;
    BaseApiService mApiService;

    private AlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        sharedPrefManager = new SharedPrefManager(this);
        sharedPrefProfil = new SharedPrefProfil(this);
        mContext = this;
        mApiService = UtilsApi.getAPIService2();

        edtEmail = findViewById(R.id.email);
        edtPassword = findViewById(R.id.password);

        if (sharedPrefManager.getSPSudahLogin()) {
            Intent intent = new Intent(mContext, MainActivity.class);
            startActivity(intent);
        }
    }

    public void signin(View view) {
        loading = ProgressDialog.show(mContext, null, "Please Wait...", true, false);
        requestLogin();
    }

    private void requestLogin() {
        mApiService.loginRequest(edtEmail.getText().toString(), edtPassword.getText().toString()).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    loading.dismiss();
                    try {
                        JSONObject jsonRESULTS = new JSONObject(response.body().string());
                        if (jsonRESULTS.getString("status").equals("success")) {
                            // Jika login berhasil maka data nama yang ada di response API
                            // akan diparsing ke activity selanjutnya.
                            String id = jsonRESULTS.getJSONObject("data").getString("id");
                            String email = jsonRESULTS.getJSONObject("data").getString("email");
                            String name = jsonRESULTS.getJSONObject("data").getString("name");
                            String wilayah_id = jsonRESULTS.getJSONObject("data").getString("wilayah_id");
                            String auth = jsonRESULTS.getJSONObject("data").getString("token");

                            String notelp = jsonRESULTS.getJSONObject("data").getString("no_telp");
                            String alamat = jsonRESULTS.getJSONObject("data").getString("alamat");
                            String wilayah = jsonRESULTS.getJSONObject("data").getString("wilayah");

                            sharedPrefManager.saveSPBoolean(SharedPrefManager.SP_SUDAH_LOGIN, true);
                            sharedPrefManager.saveSPString(SharedPrefManager.SP_ID, id);
                            sharedPrefManager.saveSPString(SharedPrefManager.SP_EMAIL, email);
                            sharedPrefManager.saveSPString(SharedPrefManager.SP_NAME, name);
                            sharedPrefManager.saveSPString(SharedPrefManager.SP_WILAYAH, wilayah_id);
                            sharedPrefManager.saveSPString(SharedPrefManager.SP_AUTH, auth);

                            sharedPrefManager.saveSPString(SharedPrefManager.SP_NOTELP, notelp);
                            sharedPrefManager.saveSPString(SharedPrefManager.SP_ALAMAT, alamat);
                            sharedPrefManager.saveSPString(SharedPrefManager.SP_WILAYAHNAME, wilayah);

                            Toast.makeText(mContext, "Success", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(mContext, MainActivity.class);
                            intent.putExtra("auth", auth);
                            startActivity(intent);
//                                finish();
//                                    String nama = jsonRESULTS.getJSONObject("user").getString("email");
//                                    String nisn = jsonRESULTS.getJSONObject("user").getString("password");

                        } else {
                            // Jika login gagal
                            String error_message = jsonRESULTS.getString("message");
                            Toast.makeText(mContext, error_message, Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    loading.dismiss();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                loading.dismiss();
                Toast.makeText(getApplicationContext(), "No Internet Connection" + t, Toast.LENGTH_SHORT).show();
            }
        });
    }

}
