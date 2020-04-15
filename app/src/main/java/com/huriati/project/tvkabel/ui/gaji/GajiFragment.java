package com.huriati.project.tvkabel.ui.gaji;

import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import com.dicoding.picodiploma.mynavigationdrawer.R;
import com.huriati.project.tvkabel.SharedPrefManager;
import com.huriati.project.tvkabel.api.BaseApiService;
import com.huriati.project.tvkabel.api.UtilsApi;
import com.huriati.project.tvkabel.model.NamaBulan;
import com.huriati.project.tvkabel.model.Utility;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;

public class GajiFragment extends Fragment {

    private GajiViewModel gajiViewModel;
    Context context;
    BaseApiService mApiService;
    ProgressDialog loading;
    SharedPrefManager sharedPrefManager;
    NamaBulan namaBulan;
    ListView listView, listView2, listView3;
    TextView txtTotal;
    String textTunj[];
    String txttunjangan[];
    String textGaji[];
    String gajipokok[];
    String textPotongan[];
    String txtpotongan[];

    int Total = 0;
    int GAJIPOKOK = 0;
    int TUNJANGAN = 0;
    int POTONGAN = 0;

    Button btn;
    SimpleAdapter adapter,adapter2,adapter3;
    HashMap<String, String> map;
    HashMap<String, String> map2;
    HashMap<String, String> map3;
    ArrayList<HashMap<String, String>> mylist;
    ArrayList<HashMap<String, String>> mylist3;
    ArrayList<HashMap<String, String>> mylist2;

    DownloadManager downloadManager;

    String TAG = "";
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        gajiViewModel =
                ViewModelProviders.of(this).get(GajiViewModel.class);
        View root = inflater.inflate(R.layout.fragment_gallery, container, false);
        final TextView textView = root.findViewById(R.id.text_gallery);
        txtTotal = root.findViewById(R.id.total);
        listView = root.findViewById(R.id.list);
        listView2 = root.findViewById(R.id.list2);
        listView3 = root.findViewById(R.id.list3);

        context = getContext();
        sharedPrefManager = new SharedPrefManager(context);
        namaBulan = new NamaBulan();
        mApiService = UtilsApi.getAPIService(sharedPrefManager.getAuth());


        mylist = new ArrayList<HashMap<String, String>>();
        mylist2 = new ArrayList<HashMap<String, String>>();
        mylist3 = new ArrayList<HashMap<String, String>>();

        gajiViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
                loading = ProgressDialog.show(context, null, "Loading...", true, false);
                getSalary(sharedPrefManager.getAuth());
            }
        });
        return root;
    }

    private void getSalary(String auth) {
        final NumberFormat formatKurensi = NumberFormat.getCurrencyInstance(Locale.getDefault());

        mApiService.salaryRequest(auth).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.isSuccessful()){
                    loading.dismiss();
                    try{
                        JSONObject jsonRESULTS = new JSONObject(response.body().string());

                        JSONArray tunjangan = jsonRESULTS.getJSONObject("Tunjangan").names();
                        textTunj = new String[tunjangan.length()];
                        txttunjangan = new String[tunjangan.length()];

                        for(int i=0; i<tunjangan.length(); i++){
                            if(!jsonRESULTS.getJSONObject("Tunjangan").getString(tunjangan.getString(i)).equals("null")) {
                                textTunj[i] = String.valueOf(tunjangan.getString(i));
                                txttunjangan[i] = jsonRESULTS.getJSONObject("Tunjangan").getString(tunjangan.getString(i));

                                map = new HashMap<String, String>();
                                map.put("judul", textTunj[i]);
                                map.put("text", formatKurensi.format(Integer.parseInt(txttunjangan[i])));
                                mylist.add(map);

                                TUNJANGAN = TUNJANGAN + Integer.parseInt(jsonRESULTS.getJSONObject("Tunjangan").getString(tunjangan.getString(i)));

                            }
                        }

//
                        adapter = new SimpleAdapter(context, mylist, R.layout.list_item,
                                new String[]{"judul","text"}, new int[]{R.id.txtText, R.id.txtAmount});
                        listView.setAdapter(adapter);

                        JSONArray gaji_pokok = jsonRESULTS.getJSONObject("Gaji Pokok").names();
                        textGaji = new String[2];
                        gajipokok = new String[2];

                        for(int i=0; i<gaji_pokok.length(); i++){
                            if(!jsonRESULTS.getJSONObject("Gaji Pokok").getString(gaji_pokok.getString(i)).equals("null")) {
                                textGaji[i] = gaji_pokok.getString(i);
                                gajipokok[i] = jsonRESULTS.getJSONObject("Gaji Pokok").getString(gaji_pokok.getString(i));

                                map3 = new HashMap<String, String>();
                                map3.put("judul", textGaji[i]);
                                map3.put("text", formatKurensi.format(Integer.parseInt(gajipokok[i])));
                                mylist3.add(map3);

                                GAJIPOKOK = GAJIPOKOK + Integer.parseInt(jsonRESULTS.getJSONObject("Gaji Pokok").getString(gaji_pokok.getString(i)));

                            }
                        }

//
                        adapter3 = new SimpleAdapter(context, mylist3, R.layout.list_item,
                                new String[]{"judul","text"}, new int[]{R.id.txtText, R.id.txtAmount});
                        listView3.setAdapter(adapter3);

                        JSONArray potongan = jsonRESULTS.getJSONObject("Potongan").names();
                        textPotongan = new String[tunjangan.length()];
                        txtpotongan = new String[tunjangan.length()];

                        for(int i=0; i<potongan.length(); i++){
                            if(!jsonRESULTS.getJSONObject("Potongan").getString(potongan.getString(i)).equals("null")) {
                                textPotongan[i] = potongan.getString(i);
                                txtpotongan[i] = jsonRESULTS.getJSONObject("Potongan").getString(potongan.getString(i));

                                map2 = new HashMap<String, String>();
                                map2.put("judul", textPotongan[i]);
                                map2.put("text", formatKurensi.format(Integer.parseInt(txtpotongan[i])));
                                mylist2.add(map2);

                                POTONGAN = POTONGAN + Integer.parseInt(jsonRESULTS.getJSONObject("Potongan").getString(potongan.getString(i)));

                            }
                        }

//
                        adapter2 = new SimpleAdapter(context, mylist2, R.layout.list_item,
                                new String[]{"judul","text"}, new int[]{R.id.txtText, R.id.txtAmount});
                        listView2.setAdapter(adapter2);

                        Total = (GAJIPOKOK + TUNJANGAN) - POTONGAN;

                        Utility.setListViewHeightBasedOnChildren(listView);
                        Utility.setListViewHeightBasedOnChildren(listView2);
                        Utility.setListViewHeightBasedOnChildren(listView3);
                        txtTotal.setText(formatKurensi.format(Total));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }else{
                    loading.dismiss();
                    Toast.makeText(context, "No Data", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                loading.dismiss();
                Toast.makeText(context, "No Internet Connection" + t, Toast.LENGTH_SHORT).show();

            }
        });
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.gaji, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.slip_gaji :
                downloadZipFile();
                Toast.makeText(context, "File saved in " + Environment.getExternalStorageDirectory().getAbsolutePath()+"/slip/slip gaji.pdf", Toast.LENGTH_LONG).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void downloadZipFile() {
        Call<ResponseBody> call = mApiService.downloadFileWithDynamicUrlAsync("employee/print");
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, final Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    Log.d(TAG, "Got the body for the file");

                    new AsyncTask<Void, Long, Void>() {
                        @Override
                        protected Void doInBackground(Void... voids) {
                            saveToDisk(response.body());
                            return null;
                        }
                    }.execute();

                } else {
                    Log.d(TAG, "Connection failed " + response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                t.printStackTrace();
                Log.e(TAG, t.getMessage());
            }
        });
    }

    public void saveToDisk(ResponseBody body) {
        try {
            new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/slip/").mkdir();
            File destinationFile = new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/slip/slip gaji.pdf");

            InputStream is = null;
            OutputStream os = null;

            try {
                Log.d(TAG, "File Size=" + body.contentLength());

                is = body.byteStream();
                os = new FileOutputStream(destinationFile);

                byte data[] = new byte[4096];
                int count;
                int progress = 0;
                while ((count = is.read(data)) != -1) {
                    os.write(data, 0, count);
                    progress +=count;
                    Log.d(TAG, "Progress: " + progress + "/" + body.contentLength() + " >>>> " + (float) progress/body.contentLength());
                }

                os.flush();

                Log.d(TAG, "File saved successfully!" + destinationFile);
                return;
            } catch (IOException e) {
                e.printStackTrace();
                Log.d(TAG, "Failed to save the file!");
                return;
            } finally {
                if (is != null) is.close();
                if (os != null) os.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
            Log.d(TAG, "Failed to save the file!");
            return;
        }
    }



}