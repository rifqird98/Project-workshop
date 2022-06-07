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

public class AdapterDoa extends RecyclerView.Adapter<AdapterDoa.MyViewHolder> {

    Context context;
    ArrayList<ListDoa> listDoas;

    public AdapterDoa (Context c, ArrayList<ListDoa> p){
        context = c;
        listDoas= p;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.list_doa, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
//        myViewHolder.xbg_program.setImageURI(listTickets.get(i).getBg_program());
        myViewHolder.nama_doa.setText(listDoas.get(i).getNama_doa());
//        myViewHolder.text_doa.setText(listDoas.get(i).getText_doa());
//        myViewHolder.latin.setText(listDoas.get(i).getLatin_doa());
//        myViewHolder.terjemah.setText(listDoas.get(i).getTerjemah_doa());



        final String getKode_paket = listDoas.get(i).getKode_doa();

        myViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gotoprogramdetail = new Intent(context, MyDoa.class);
                gotoprogramdetail.putExtra("kode_doa", getKode_paket);
                context.startActivity(gotoprogramdetail);
            }
        });

    }

    @Override
    public int getItemCount() {
        return listDoas.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView nama_doa, text_doa, latin, terjemah;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            nama_doa = itemView.findViewById(R.id.nama_doa);
//            text_doa = itemView.findViewById(R.id.text_doa);
//            latin=itemView.findViewById(R.id.latin);
//            terjemah=itemView.findViewById(R.id.terjemah);
        }
    }
}
