package com.raj577.deviceinfo;

import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
//import android.widget.SearchView;
import android.app.SearchManager;
import android.widget.SearchView;
import android.widget.SearchView.OnQueryTextListener;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.MenuItemCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private MyAdapter mAdapter;

    private TextView mManufacturerTv,mAndroidVersionTv;
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Toolbar toolbar = findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);

        initCollapsingToolbar();

        //RecyclerView
        mRecyclerView = findViewById(R.id.myRecycler);
        //set its properties
        mRecyclerView.setLayoutManager(new GridLayoutManager(this,2)); // grid view with 2 columns in each row
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        //Adapter
        mAdapter = new MyAdapter(this,getModels());
        mRecyclerView.setAdapter(mAdapter);
    }

    private void initCollapsingToolbar() {
        CollapsingToolbarLayout collapsingToolbarLayout = findViewById(R.id.collapsing_toolbar);
        collapsingToolbarLayout.setTitle("");
        AppBarLayout appBarLayout = findViewById(R.id.appbar);
        appBarLayout.setExpanded(true);


        //set imageview
        imageView =findViewById(R.id.backdrop);
        //Set collapsing toolbar title

        collapsingToolbarLayout.setTitle("Device Info");

        //get device manufacturer name

        String mManufacturer = Build.MANUFACTURER;//for example samsung,oppo etc
        String model = Build.MODEL;
        //getting device android version
        String version = Build.VERSION.RELEASE;
        //getting android version name
        String verName = Build.VERSION_CODES.class.getFields()[Build.VERSION.SDK_INT].getName();

        //setting these to out views
        mManufacturerTv = findViewById(R.id.device_model);
       mAndroidVersionTv = findViewById(R.id.andro_verion);

        mManufacturerTv.setText(mManufacturer.toUpperCase() + "" + model);
        mAndroidVersionTv.setText(version.toUpperCase() + "" + verName);

        //display android version logo
        try{
            if(Build.VERSION.SDK_INT == Build.VERSION_CODES.JELLY_BEAN){
                Glide.with(this).load(R.drawable.android41).into(imageView);
            }
            if(Build.VERSION.SDK_INT == Build.VERSION_CODES.JELLY_BEAN_MR1){
                Glide.with(this).load(R.drawable.android41).into(imageView);
            }
            if(Build.VERSION.SDK_INT == Build.VERSION_CODES.JELLY_BEAN_MR2){
                Glide.with(this).load(R.drawable.android41).into(imageView);
            }
            if(Build.VERSION.SDK_INT == Build.VERSION_CODES.KITKAT){
                Glide.with(this).load(R.drawable.android44).into(imageView);
            }
            if(Build.VERSION.SDK_INT == Build.VERSION_CODES.LOLLIPOP){
                Glide.with(this).load(R.drawable.android50).into(imageView);
            }
            if(Build.VERSION.SDK_INT == Build.VERSION_CODES.LOLLIPOP_MR1){
                Glide.with(this).load(R.drawable.android50).into(imageView);
            } if(Build.VERSION.SDK_INT == Build.VERSION_CODES.M){
                Glide.with(this).load(R.drawable.android60).into(imageView);
            }
            if(Build.VERSION.SDK_INT == Build.VERSION_CODES.N_MR1){
                Glide.with(this).load(R.drawable.android70).into(imageView);
            }
            if(Build.VERSION.SDK_INT == Build.VERSION_CODES.N_MR1){
                Glide.with(this).load(R.drawable.android70).into(imageView);
            }
            if(Build.VERSION.SDK_INT == Build.VERSION_CODES.O){
                Glide.with(this).load(R.drawable.android80).into(imageView);
            }
            if(Build.VERSION.SDK_INT == Build.VERSION_CODES.O_MR1){
                Glide.with(this).load(R.drawable.android80).into(imageView);
            }
            if(Build.VERSION.SDK_INT == Build.VERSION_CODES.P){
                Glide.with(this).load(R.drawable.android90).into(imageView);
            }
            if(Build.VERSION.SDK_INT == Build.VERSION_CODES.Q){
                Glide.with(this).load(R.drawable.androidq1).into(imageView);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    //Add models to ArrayList
    private ArrayList<Model> getModels(){

        ArrayList<Model> models = new ArrayList<>();
        Model p = new Model();

        p = new Model();
        p.setName("General");
        p.setImg(R.drawable.general);
        models.add(p);


        p = new Model();
        p.setName("Device Id");
        p.setImg(R.drawable.devid);
        models.add(p);

        p = new Model();
        p.setName("Display");
        p.setImg(R.drawable.display);
        models.add(p);
        p = new Model();
        p.setName("Battery");
        p.setImg(R.drawable.battery);
        models.add(p);

        p = new Model();
        p.setName("User Apps");
        p.setImg(R.drawable.userapps);
        models.add(p);


        p = new Model();
        p.setName("System Apps");
        p.setImg(R.drawable.systemapps);
        models.add(p);

        p = new Model();
        p.setName("Memory");
        p.setImg(R.drawable.memory);
        models.add(p);

        p = new Model();
        p.setName("CPU");
        p.setImg(R.drawable.cpu);
        models.add(p);


        p = new Model();
        p.setName("Sensors");
        p.setImg(R.drawable.sensor);
        models.add(p);

        p = new Model();
        p.setName("SIM");
        p.setImg(R.drawable.sim);
        models.add(p);

        return models;

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        MenuItem item = menu.findItem(R.id.action_search);
//        SearchView searchView = (SearchView)MenuItemCompat.getActionView(item);


//        ActionProvider searchView;
//        searchView=MenuItemCompat.getActionView(item)
//        searchView = (SearchView) MenuItemCompat.getActionProvider(item);
//        searchView.setOnQueryTextListener(new SearchView().OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String query) {
//                mAdapter.getFilter().filter(query);
//                return false;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String newText) {
//                //this function is called when search button in keyboard is pressed
//                mAdapter.getFilter().filter(newText);
//                return false;
//            }
//        });
//        return true;
//    }


//        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String query) {
//                mAdapter.getFilter().filter(query);
//                return false;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String newText) {
//                mAdapter.getFilter().filter(newText);
//                return false;
//            }
//        });
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        //handle other menu item clicks here
        if(id == R.id.action_settings){
            Toast.makeText(this, "Settings", Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }
}

