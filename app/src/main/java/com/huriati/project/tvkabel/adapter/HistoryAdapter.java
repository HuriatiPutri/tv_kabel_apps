package com.huriati.project.tvkabel.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dicoding.picodiploma.mynavigationdrawer.R;
import com.huriati.project.tvkabel.SharedPrefManager;
import com.huriati.project.tvkabel.api.BaseApiService;
import com.huriati.project.tvkabel.api.UtilsApi;
import com.huriati.project.tvkabel.model.History;
import com.huriati.project.tvkabel.model.NamaBulan;
import com.huriati.project.tvkabel.model.Tagihan;
import com.huriati.project.tvkabel.ui.BayarActivity;
import com.huriati.project.tvkabel.ui.TestActivity;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.UsersHolder> {

    BaseApiService mApiService;
    SharedPrefManager sharedPrefManager;
    private Context context;
    private List<History> list;

    public HistoryAdapter(Context context, List<History> list) {
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
        NamaBulan namaBulan = new NamaBulan();
        holder.tanggal.setText(list.get(position).getTgl_tagihan());
        holder.bulan.setText(namaBulan.NamaBulan(list.get(position).getBulan_tagihan()));
        holder.total.setText("Rp. " +list.get(position).getJumlah_pembayaran());
        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, BayarActivity.class);
                intent.putExtra("idTagihan", list.get(position).getId());
                intent.putExtra("bulan",holder.bulan.getText().toString());
                intent.putExtra("nominal",list.get(position).getJumlah_pembayaran());
                intent.putExtra("nama", TestActivity.txtNama.getText().toString());
                intent.putExtra("idPel", TestActivity.txtId.getText().toString());
                intent.putExtra("status", "1");
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

