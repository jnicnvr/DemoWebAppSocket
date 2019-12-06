package com.example.demoapp.demoapp;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.Console;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

public class MainActivity extends AppCompatActivity {

        public static final String EXTRA_MESSAGE="com.example.demoapp.demoapp";
        String imei1 = "imei";
    private Socket socket;
    private Socket mSocket;
    {
        try {
            //online connection----------
            //mSocket = IO.socket("http://139.180.137.36:8080"); //i-Alert (Iriga City)

            //local connection----------
            mSocket = IO.socket("http://192.168.1.7:8080");
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
   }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        socket = mSocket;
        Log.d("Connected1", mSocket.toString());
        socket.connect();
        Log.d("Connected2", "nico12333");
        socket.on(Socket.EVENT_CONNECT, onConnect);

    }

    public static String getImei(TelephonyManager tm) {
        String imei;
        try{
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                imei = tm.getDeviceId(0);
            }else{
                imei = tm.getDeviceId();
            }
        }catch(SecurityException e){
            imei = "";
        }
        return imei;
    }

    public void sendMessage(View view) {
//        Intent intent = new Intent(this, DisplayMessageActivity.class);
////        EditText editText = (EditText) findViewById(R.id.tb_submit);
////        String message = editText.getText().toString();
////        intent.putExtra(EXTRA_MESSAGE, message);
////        startActivity(intent);
//
//
        imei1 = getImei((TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE));
        Log.d("IMEI_NICO", imei1);
         socket.emit("onReceived",imei1);

   }

    private Emitter.Listener onConnect = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            System.out.println("Connected");
            Log.d("User_id:", socket.id());
        }
    };



}
