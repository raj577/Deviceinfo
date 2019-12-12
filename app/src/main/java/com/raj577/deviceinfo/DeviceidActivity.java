package com.raj577.deviceinfo;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.format.Formatter;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Collections;
import java.util.List;

public class DeviceidActivity extends AppCompatActivity {

    private String titles[];
    private String descriptions[];
    private static final int READ_PHONE_STATE_PERMISSION = 1;
    private TelephonyManager tm;
    private String imei, simCardSerial, simSubscriberID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deviceid);
        //actionbar
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Device Id");
        if(actionBar != null) {
            //set back button in actionbar
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
        }



        //Android Device Id
        String deviceid = Settings.Secure.getString(this.getContentResolver(), Settings.Secure.ANDROID_ID);
        //IMEI number
        tm = (TelephonyManager)getSystemService(TELEPHONY_SERVICE);
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
          if(checkSelfPermission(Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_DENIED){
                String[] permissions = {Manifest.permission.READ_PHONE_STATE};
                requestPermissions(permissions,READ_PHONE_STATE_PERMISSION);
          }
          else{
              imei = tm.getDeviceId();
              simCardSerial = tm.getSimSerialNumber();
              simSubscriberID = tm.getSubscriberId();
          }
        }else{
            imei = tm.getDeviceId();
            simCardSerial = tm.getSimSerialNumber();
            simSubscriberID = tm.getSubscriberId();
        }



        //IP Address
        String ipAdress = "No Internet Connection";
        ConnectivityManager connManager = (ConnectivityManager)this.getSystemService(CONNECTIVITY_SERVICE);
        boolean is3GEnabled = false;
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            Network[] networks = connManager.getAllNetworks();
            for(Network network: networks){
               final NetworkInfo info = connManager.getActiveNetworkInfo();
                if(info != null){
                    if(info.getType() == ConnectivityManager.TYPE_MOBILE){
                        ipAdress = getMobileIPAddress();
                    }
                }
            }

        }
        else {
            NetworkInfo mMobile = connManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
            if(mMobile != null){
                ipAdress = is3GEnabled + "";
            }
        }

        //Wifi Mac Adress
        String WiFiAdress = "No WiFi Connection";


        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            Network[] networks = connManager.getAllNetworks();
            for(Network network: networks){
                final NetworkInfo info = connManager.getActiveNetworkInfo();
                if(info != null){
                    if(info.getType() == ConnectivityManager.TYPE_WIFI){
                        WiFiAdress = getWiFiIPAddress();
                    }
                }
            }

        }
        else {
            NetworkInfo mMobile = connManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
            if(mMobile != null){
                ipAdress = is3GEnabled + "";
            }
        }

            //Bluetooth Mac Address
        String bt = android.provider.Settings.Secure.getString(this.getContentResolver(), "bluetooth_address");


        //array conatining data
        titles = new String[]{"Android Device ID","IMEI","Hardware Serial No","SIM Subsciber ID","Bluetooth Mac Address","WiFi Mac Address","IP Address", "Build Fingerprint"};
        descriptions =new String[]{deviceid,imei, Build.SERIAL,  simSubscriberID,  bt, ipAdress, WiFiAdress, Build.FINGERPRINT };

        ListView listView= findViewById(R.id.devIdList);

        //set adapter to listview
        MyAdapter adapter = new MyAdapter(this,titles,descriptions);
        listView.setAdapter(adapter);

    }

    private String getWiFiIPAddress() {

        WifiManager wifiManager = (WifiManager)getApplicationContext().getSystemService(WIFI_SERVICE);
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        int ip = wifiInfo.getIpAddress();
        return Formatter.formatIpAddress(ip);

    }

    private static String getMobileIPAddress() {


        try {
            List<NetworkInterface> interfaces = Collections.list(NetworkInterface.getNetworkInterfaces());
            for(NetworkInterface inif: interfaces){
                List<InetAddress> addrs = Collections.list(inif.getInetAddresses());
                for(InetAddress addr : addrs){
                    if(!addr.isLoopbackAddress()){
                        return addr.getHostAddress();
                    }
                }
            }
        }
        catch (SocketException e) { }
            return "";
        }




    //creating custom adapter class
    private class MyAdapter extends ArrayAdapter<String>{
        Context context;
        String myTitles[];
        String myDescriptions[];

        //constructor
        public MyAdapter(@NonNull Context c, String[] titles, String[] descriptions) {
            super(c, R.layout.tworow, R.id.title, titles);
              this.context = c;
              this.myTitles = titles;
              this.myDescriptions = descriptions;
        }


        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            //Inflating the tworow layout
            LayoutInflater layoutInflater = (LayoutInflater)getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View row = layoutInflater.inflate(R.layout.tworow,parent,false);
            //Views in tworow
            TextView myTitle = row.findViewById(R.id.titleTv);
            TextView myDesr = row.findViewById(R.id.descTv);

            //set text to these views
            myTitle.setText(titles[position]);
            myDesr.setText(descriptions[position]);


            return row;
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case READ_PHONE_STATE_PERMISSION:{
                if(grantResults.length >=0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    recreate(); //restart the activity on permission granted
                    //permission granted
                    imei = tm.getDeviceId();
                    simCardSerial = tm.getSimSerialNumber();
                    simSubscriberID = tm.getSubscriberId();
                }
                else{
                    //permission denied
                    Toast.makeText(this, "Phone state permission is required to show DeviceId data", Toast.LENGTH_SHORT).show();
                }
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        menu.findItem(R.id.action_search).setVisible(false);
        return true;
    }




}
