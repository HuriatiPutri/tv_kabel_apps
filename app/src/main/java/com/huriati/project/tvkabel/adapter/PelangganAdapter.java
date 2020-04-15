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
import com.huriati.project.tvkabel.model.Pelanggan;
import com.huriati.project.tvkabel.ui.DetailActivity;
import com.huriati.project.tvkabel.ui.TestActivity;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class PelangganAdapter extends RecyclerView.Adapter<PelangganAdapter.UsersHolder> {

    BaseApiService mApiService;
    SharedPrefManager sharedPrefManager;
    private Context context;
    private List<Pelanggan> list;

    public PelangganAdapter(Context context, List<Pelanggan> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public UsersHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemRow = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pelanggan, parent, false);
        return new UsersHolder(itemRow);
    }

    @Override
    public void onBindViewHolder(@NonNull UsersHolder holder, final int position) {
        sharedPrefManager = new SharedPrefManager(context);
        mApiService = UtilsApi.getAPIService(sharedPrefManager.getAuth());

        holder.txtidPel.setText(list.get(position).getId());
        holder.txtnamaPel.setText(list.get(position).getNama());
        holder.txttotal.setText("Rp. " + list.get(position).getJumlah_tagihan());
        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(context, TestActivity.class);
                intent.putExtra("idPel", list.get(position).getId());
                intent.putExtra("namaPel", list.get(position).getNama());
                intent.putExtra("hp", list.get(position).getNo_telp());
                intent.putExtra("alamat", list.get(position).getAlamat());
                context.startActivity(intent);
            }
        });
    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    class UsersHolder extends RecyclerView.ViewHolder{

        TextView txtidPel,txtnamaPel, txttotal;
        LinearLayout layout;
        public UsersHolder(View itemView) {
            super(itemView);
            layout = itemView.findViewById(R.id.layout);
            txtidPel = itemView.findViewById(R.id.idPel);
            txtnamaPel = itemView.findViewById(R.id.namaPel);
            txttotal = itemView.findViewById(R.id.total);
        }
    }


}

