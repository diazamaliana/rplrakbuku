package com.example.rakbuku.rakbuku;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewHolder> {


    private Context mContext ;
    ArrayList<BorrowBook> borrowBookArrayList;



    public MyRecyclerViewAdapter(Context mContext, ArrayList<BorrowBook> borrowBookArrayList) {

        this.borrowBookArrayList = borrowBookArrayList;
        this.mContext = mContext;

    }

    @NonNull
    @Override
    public MyRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        View view = layoutInflater.inflate(R.layout.borrowcardview, parent, false);
        return new MyRecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyRecyclerViewHolder holder, final int position) {


        BorrowBook Listbuku = borrowBookArrayList.get(position);

        holder.midJudulBuku.setText(Listbuku.getJudul());
        holder.midPemilikBuku.setText(Listbuku.getEmail());
        holder.midUrlBuku.setText(Listbuku.getUrlBook());

        //load foto
       Picasso.with(mContext).load(Listbuku.getUrlBook()).into(holder.idFotoBuku);


        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(mContext, BorrowDeskripsiBuku.class);

                // passing data to Borrow Deskripsi Book
                intent.putExtra("Judul",borrowBookArrayList.get(position).getJudul());
                intent.putExtra("Email",borrowBookArrayList.get(position).getEmail());
                intent.putExtra("UrlBook",borrowBookArrayList.get(position).getUrlBook());
                intent.putExtra("Harga",borrowBookArrayList.get(position).getHarga());
                intent.putExtra("Tipe",borrowBookArrayList.get(position).getTipe());
                intent.putExtra("Penulis",borrowBookArrayList.get(position).getPenulis());
                intent.putExtra("Penerbit",borrowBookArrayList.get(position).getPenerbit());
                // start the activity
                mContext.startActivity(intent);

            }
        });


    }

    @Override
    public int getItemCount() {
        return borrowBookArrayList.size();
    }


}
