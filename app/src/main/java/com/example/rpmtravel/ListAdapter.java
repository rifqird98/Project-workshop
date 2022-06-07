package com.example.rpmtravel;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.MyViewHolder>{

    Context context;
    ArrayList<ListTickets> listTickets;

    public ListAdapter (Context c, ArrayList<ListTickets> t){
        context = c;
        listTickets = t;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.item_listprogram, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {

//        myViewHolder.xbg_program.setImageURI(listTickets.get(i).getBg_program());
        myViewHolder.xharga_tiket.setText(listTickets.get(i).getHarga_tiket());
        myViewHolder.xwaktu_keberangkatan.setText(listTickets.get(i).getWaktu_keberangkatan());
        myViewHolder.xdurasi.setText(listTickets.get(i).getDurasi());
        myViewHolder.xrute.setText(listTickets.get(i).getRute());

        Picasso.with(context).load(lis tTickets.get(i).getUrl_thumbnail()).into(myViewHolder.xbg_program);

        final String getKode_paket = listTickets.get(i).getKode_paket();

        myViewHolder.itemView.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gotoprogramdetail = new Intent(context, Program_Detail.class);
                gotoprogramdetail.putExtra("kode_paket", getKode_paket);
                context.startActivity(gotoprogramdetail);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listTickets.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView xbg_program;
        TextView xharga_tiket, xwaktu_keberangkatan, xdurasi, xrute;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            xbg_program = itemView.findViewById(R.id.xbg_program);
            xharga_tiket = itemView.findViewById(R.id.xharga_tiket);
            xwaktu_keberangkatan = itemView.findViewById(R.id.xwaktu_keberangkatan);
            xdurasi = itemView.findViewById(R.id.xdurasi);
            xrute = itemView.findViewById(R.id.xrute);
        }
    }

}
