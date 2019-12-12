package com.raj577.deviceinfo;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.text.NumberFormat;

public class DisplayActivity extends AppCompatActivity {

    //array items to display
    private String titles[];
    private String descriptions[];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);

        //Actionbar
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setTitle(R.string.actionbarDisplay);
            //set back button in action bar
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
        }

        //Screensize in pixels,Width x Height
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;
        int height = dm.heightPixels;
        String resolution = width + "x"+ height +" pixel";

        //Physical size in inch
        double x = Math.pow(width/dm.xdpi,2);
        double y = Math.pow(height/dm.ydpi,2);
        double screenInches = Math.sqrt(x+y); //this will return a floating value
        NumberFormat form = NumberFormat.getCurrencyInstance(); //limit number of values after fraction
        form.setMinimumFractionDigits(2); //minimum two values after fraction
        form.setMaximumFractionDigits(2); //maximum two values after fraction
        String screenIncheOutput = form.format(screenInches);

        //Referesh rate
        Display display = ((WindowManager)getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        float refereshRating = display.getRefreshRate();
        NumberFormat form1 = NumberFormat.getCurrencyInstance(); //limit number of values after fraction
        form1.setMinimumFractionDigits(1); //minimum two values after fraction
        form1.setMaximumFractionDigits(1); //maximum two values after fraction
        String outputRefereshRate = form1.format(refereshRating);

        titles = new String[]{"Resolution","Density","Physical Size","Referesh Rate","Orientation"};
        descriptions = new String[]{resolution,DisplayMetrics.DENSITY_XHIGH + "dpi(xhdpi)",screenIncheOutput+ "inch",outputRefereshRate+" Hz", this.getResources().getConfiguration().orientation+""};
        /* this.getResources().getConfiguration().orientation
        1 means portrait orientation
        2 means landscape orientation*/


        //ListView
        ListView list = findViewById(R.id.displayList);

        //adapter
        MyAdapter adapter = new MyAdapter(this,titles,descriptions);
        list.setAdapter(adapter);

    }

    //custom adapter class for listview
    private class MyAdapter extends ArrayAdapter<String>{
        Context context;
        String myTitles[];
        String myDescriptions[];


        //constructor
        MyAdapter(Context c,String[] titles,String[] descriptions){
            super(c,R.layout.tworow,R.id.title,titles);
            this.context = c;
            this.myTitles = titles;
            this.myDescriptions = descriptions;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            //inflate layout tworow
            LayoutInflater layoutInflater = (LayoutInflater)getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View row = layoutInflater.inflate(R.layout.tworow,parent,false);

            //Views from tworow
            TextView myTitle = row.findViewById(R.id.titleTv);
            TextView myDescr = row.findViewById(R.id.descTv);

            //set text to these views
            myTitle.setText(titles[position]);
            myDescr.setText(descriptions[position]);

            return row;
        }
    }

    //go to previous activity on back button pressed from from action bar

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        menu.findItem(R.id.action_search).setVisible(false);
        return true;
    }

}
