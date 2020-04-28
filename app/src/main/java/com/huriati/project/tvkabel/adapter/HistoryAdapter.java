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
import com.huriati.project.tvkabel.ui.DetailActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

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
        int date = Integer.parseInt(list.get(position).getUpdated_at());
        String myDate= new SimpleDateFormat("yyyy- MM-d")
                .format(new Date(date * 1000L));
        holder.tanggal.setText("Tanggal Pembayaran: " + myDate);
        String bulan = list.get(position).getBulan_id().substring(6,7);
        holder.bulan.setText(namaBulan.NamaBulan(bulan));
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

