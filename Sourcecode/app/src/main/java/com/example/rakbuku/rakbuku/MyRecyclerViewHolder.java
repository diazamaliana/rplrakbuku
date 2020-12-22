package com.example.rakbuku.rakbuku;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class MyRecyclerViewHolder extends RecyclerView.ViewHolder {

    public TextView midJudulBuku, midPemilikBuku, midUrlBuku;
    public ImageView idFotoBuku;
    public CardView cardView;

    public  MyRecyclerViewHolder(View itemView){
        super(itemView);

        midJudulBuku = itemView.findViewById(R.id.idJudulBuku);
        midPemilikBuku = itemView.findViewById(R.id.idPemilikBuku);
        midUrlBuku = itemView.findViewById(R.id.idUrlBuku);
        idFotoBuku = (ImageView) itemView.findViewById(R.id.idFotoBuku);
        cardView = (CardView) itemView.findViewById(R.id.cardview_id);




        }
}
