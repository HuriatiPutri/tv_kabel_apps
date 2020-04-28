package com.huriati.project.tvkabel.adapter;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.dicoding.picodiploma.mynavigationdrawer.R;
import com.huriati.project.tvkabel.SharedPrefManager;
import com.huriati.project.tvkabel.api.BaseApiService;
import com.huriati.project.tvkabel.api.UtilsApi;
import com.huriati.project.tvkabel.model.NamaBulan;
import com.huriati.project.tvkabel.model.Tagihan;
import com.huriati.project.tvkabel.ui.BayarActivity;
import com.huriati.project.tvkabel.ui.DetailActivity;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TagihanAdapter extends RecyclerView.Adapter<TagihanAdapter.UsersHolder> {

    BaseApiService mApiService;
    SharedPrefManager sharedPrefManager;
    private Context context;
    private List<Tagihan> list;

    public TagihanAdapter(Context context, List<Tagihan> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public UsersHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemRow = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_tagihan, parent, false);
        return new UsersHolder(itemRow);
    }

    @Override
    public void onBindViewHolder(@NonNull final UsersHolder holder, final int position) {
        sharedPrefManager = new SharedPrefManager(context);
        NamaBulan namaBulan = new NamaBulan();

        mApiService = UtilsApi.getAPIService(sharedPrefManager.getAuth());
        String ambilBulan = (list.get(position).getBulan_id()).substring(6,7);

        holder.tanggal.setText("Jatuh Tempo : "+list.get(position).getBulan_id());
        holder.bulan.setText(namaBulan.NamaBulan(ambilBulan));
        holder.total.setText("Rp. " +list.get(position).getJumlah_tagihan());
        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, BayarActivity.class);
                intent.putExtra("idTagihan", list.get(position).getId());
                intent.putExtra("bulan",holder.bulan.getText().toString());
                intent.putExtra("nominal",list.get(position).getJumlah_tagihan());
                intent.putExtra("nama", DetailActivity.txtNama.getText().toString());
                intent.putExtra("idPel", DetailActivity.txtId.getText().toString());
                intent.putExtra("status", "0");
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }

        });

    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    class UsersHolder extends RecyclerView.ViewHolder{

        TextView tanggal, bulan, total;
        LinearLayout layout;
        public UsersHolder(View itemView) {
            super(itemView);
            layout = itemView.findViewById(R.id.layout);
            tanggal = itemView.findViewById(R.id.tanggal);
            bulan = itemView.findViewById(R.id.bulan);
            total = itemView.findViewById(R.id.total);
        }
    }


}

