package com.raj577.deviceinfo;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyAdapter  extends RecyclerView.Adapter<MyHolder> implements Filterable {

    Context c;
    ArrayList<Model> models ,filterList;
    CustomFilter filter;

    public MyAdapter(Context ctx, ArrayList<Model> models) {
        this.c = ctx;
        this.models = models;
        this.filterList = models;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        //Convert XML to OBJ
       View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.model,null);
       //Holder
       MyHolder holder= new MyHolder(v);
       return holder;

    }


    //Data bound to views
    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {

         holder.nameTxt.setText(models.get(position).getName());
         holder.img.setImageResource(models.get(position).getImg());

         //handle item clicks
        holder.setItemClickListner(new ItemClickListner() {
            @Override
            public void onItemClick(View v, int pos) {
                if(models.get(pos).getName().equals("General")){
                    Toast.makeText(c, "General", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(c,GeneralActivity.class);
                    c.startActivity(intent);
                }
                else if(models.get(pos).getName().equals("Device Id")){
                    Toast.makeText(c, "Device id", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(c,DeviceidActivity.class);
                    c.startActivity(intent);

                }
               else  if(models.get(pos).getName().equals("Display")){
                    Toast.makeText(c, "Display", Toast.LENGTH_SHORT).show();

                }
                else if(models.get(pos).getName().equals("Battery")){
                    Toast.makeText(c, "Battery", Toast.LENGTH_SHORT).show();
                }
                else if(models.get(pos).getName().equals("User Apps")){
                    Toast.makeText(c, "User Apps", Toast.LENGTH_SHORT).show();
                }
                else if(models.get(pos).getName().equals("System Apps")){
                    Toast.makeText(c, "System Apps", Toast.LENGTH_SHORT).show();
                }
                else if(models.get(pos).getName().equals("Memory")){
                    Toast.makeText(c, "Memory", Toast.LENGTH_SHORT).show();
                }
                else if(models.get(pos).getName().equals("CPU")){
                    Toast.makeText(c, "CPU", Toast.LENGTH_SHORT).show();
                }
                else if(models.get(pos).getName().equals("Sensors")){
                    Toast.makeText(c, "Sensors", Toast.LENGTH_SHORT).show();
                }
                else if(models.get(pos).getName().equals("SIM")){
                    Toast.makeText(c, "Sim", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    @Override
    public int getItemCount() {


        return models.size();
    }


    //return filter object
    @Override
    public Filter getFilter() {
        if(filter == null) {
            filter = new CustomFilter(filterList,this);
        }
        return filter;
    }
}
