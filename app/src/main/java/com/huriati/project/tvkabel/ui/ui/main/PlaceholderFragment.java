package com.huriati.project.tvkabel.ui.ui.main;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.dicoding.picodiploma.mynavigationdrawer.R;
import com.huriati.project.tvkabel.SharedPrefManager;
import com.huriati.project.tvkabel.adapter.HistoryAdapter;
import com.huriati.project.tvkabel.adapter.TagihanAdapter;
import com.huriati.project.tvkabel.api.BaseApiService;
import com.huriati.project.tvkabel.api.UtilsApi;
import com.huriati.project.tvkabel.model.History;
import com.huriati.project.tvkabel.model.HistoryResponse;
import com.huriati.project.tvkabel.model.Tagihan;
import com.huriati.project.tvkabel.model.TagihanResponse;
import com.huriati.project.tvkabel.ui.TestActivity;

import java.util.List;

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


/**
 * A placeholder fragment containing a simple view.
 */
public class PlaceholderFragment extends Fragment {

    private static final String ARG_SECTION_NUMBER = "section_number";

    private PageViewModel pageViewModel;

    Context context;
    BaseApiService mApiService;
    ProgressDialog loading;
    SharedPrefManager sharedPrefManager;

    RecyclerView list;
    TextView label;
    public static PlaceholderFragment newInstance(int index) {
        PlaceholderFragment fragment = new PlaceholderFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_SECTION_NUMBER, index);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pageViewModel = ViewModelProviders.of(this).get(PageViewModel.class);
        int index = 1;
        if (getArguments() != null) {
            index = getArguments().getInt(ARG_SECTION_NUMBER);
        }
        pageViewModel.setIndex(index);
    }

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_test, container, false);
        list = root.findViewById(R.id.list);
        label = root.findViewById(R.id.section_label);

        context = getContext();
        sharedPrefManager = new SharedPrefManager(context);

        list.setHasFixedSize(true);
        list.setLayoutManager(new LinearLayoutManager(context));
        pageViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                mApiService = UtilsApi.getAPIService(sharedPrefManager.getAuth());
                label.setText(s);
               if(s.equalsIgnoreCase("1")){
                   getTagihan(TestActivity.idPel);
               }else{
                   getHistory(sharedPrefManager.getSpIdkolektor(), TestActivity.idPel);
               }
            }
        });
        return root;
    }

    private void getHistory(String spIdkolektor, String idPel) {
        mApiService.historyRequest(spIdkolektor, idPel).enqueue(new Callback<HistoryResponse>() {
            @Override
            public void onResponse(Call<HistoryResponse> call, Response<HistoryResponse> response) {
                if(response.isSuccessful()){
//                    loading.dismiss();
                    final List<History> semuaData = response.body().getData();

                    list.setAdapter(new HistoryAdapter(context, semuaData));

                }else{
//                    loading.dismiss();
                    Toast.makeText(context, "No Data", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<HistoryResponse> call, Throwable t) {

            }
        });
    }

    private void getTagihan(String id) {
//        loading = ProgressDialog.show(getApplicationContext(), null, "Loading...", true, false);

        mApiService.tagihanRequest(id).enqueue(new Callback<TagihanResponse>() {
            @Override
            public void onResponse(Call<TagihanResponse> call, Response<TagihanResponse> response) {
                if(response.isSuccessful()){
//                    loading.dismiss();
                    final List<Tagihan> semuaData = response.body().getTagihan();
                    list.setAdapter(new TagihanAdapter(context, semuaData));

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

    @Override
    public void onResume() {
        super.onResume();
        if(label.getText().toString().equalsIgnoreCase("1")){
            getTagihan(TestActivity.idPel);
        }else{
            getHistory(sharedPrefManager.getSpIdkolektor(), TestActivity.idPel);
        }
    }
}