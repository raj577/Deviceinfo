package com.raj577.deviceinfo;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MyHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public ImageView img;
    public TextView nameTxt;
    ItemClickListner itemClickListner;


    public MyHolder(@NonNull View itemView) {
        super(itemView);
        this.img = itemView.findViewById(R.id.modelImage);
        this.nameTxt = itemView.findViewById(R.id.modelTxt);

        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        this.itemClickListner.onItemClick(v , getLayoutPosition());

    }

    public void setItemClickListner(ItemClickListner ic){
        this.itemClickListner = ic;
    }
}
