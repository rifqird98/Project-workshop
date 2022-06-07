package com.example.rpmtravel;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AdapterPaket extends RecyclerView.Adapter<AdapterPaket.MyViewHolder> {

    Context context;
    ArrayList<ListTickets> listTickets;

    public AdapterPaket (Context c, ArrayList<ListTickets> p){
        context = c;
        listTickets = p;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.item_myprogram, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
//        myViewHolder.xbg_program.setImageURI(listTickets.get(i).getBg_program());
        myViewHolder.xharga_tiket.setText(listTickets.get(i).getHarga_tiket());
        myViewHolder.xnama_paket.setText(listTickets.get(i).getNama_paket());


        final String getKode_paket = listTickets.get(i).getKode_paket();

        myViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gotoprogramdetail = new Intent(context, My_Program.class);
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

        TextView xnama_paket, xharga_tiket;

            public MyViewHolder(@NonNull View itemView) {
                super(itemView);

                xnama_paket = itemView.findViewById(R.id.xnama_paket);
                xharga_tiket = itemView.findViewById(R.id.xharga_tiket);
            }
        }
    }
