package com.raj577.deviceinfo;

import android.widget.Filter;

import java.util.ArrayList;

public class CustomFilter extends Filter {
    MyAdapter adapter;
    ArrayList<Model> filterList;

    public CustomFilter( ArrayList<Model> filterList,MyAdapter adapter) {
        this.adapter = adapter;
        this.filterList = filterList;
    }


    //filtering occurs
    @Override
    protected FilterResults performFiltering(CharSequence constraint) {

        FilterResults results = new FilterResults();
        if(constraint != null && constraint.length()>0){
            //change to upper case
            constraint = constraint.toString().toUpperCase();
            ArrayList<Model> filteredModels = new ArrayList<>();
            for (int i = 0;i<filterList.size();i++){

                //check
                if(filterList.get(i).getName().toUpperCase().contains(constraint)){
                    //add model to filter models
                    filteredModels.add(filterList.get(i));
                }


            }
            results.count= filteredModels.size();
            results.values=filteredModels;
        }
        else {
            results.count = filterList.size();
            results.values=filterList;
        }
        return results;
    }

    @Override
    protected void publishResults(CharSequence constraint, FilterResults results) {





    }
}
