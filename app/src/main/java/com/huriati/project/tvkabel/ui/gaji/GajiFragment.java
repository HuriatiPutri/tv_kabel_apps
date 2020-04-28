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


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        gajiViewModel =
                ViewModelProviders.of(this).get(GajiViewModel.class);
        View root = inflater.inflate(R.layout.fragment_gallery, container, false);
        TextView txtNama = root.findViewById(R.id.txtName);
        TextView txtEmail = root.findViewById(R.id.txtEmail);
        TextView txtNotelp = root.findViewById(R.id.txtNoTelp);
        TextView txtAlamat = root.findViewById(R.id.txtAlamat);
        TextView txtWilayah = root.findViewById(R.id.txtWilayahTugas);


        SharedPrefManager sharedPrefManager = new SharedPrefManager(getContext());

        txtNama.setText(sharedPrefManager.getName());
        txtEmail.setText(sharedPrefManager.getSPEmail());
        txtNotelp.setText(sharedPrefManager.getSpNotelp());
        txtAlamat.setText(sharedPrefManager.getSpAlamat());
        txtWilayah.setText(sharedPrefManager.getSpWilayahNama());

        gajiViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
            }
        });
        return root;
    }

}