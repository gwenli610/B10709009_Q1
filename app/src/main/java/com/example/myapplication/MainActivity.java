package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private TextView tv;
    private Button left;
    private Button right;
    SharedPreferences preferences=getSharedPreferences("preFile",MODE_PRIVATE);
    SharedPreferences.Editor editor=preferences.edit();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        notification_method();
        setContentView(R.layout.activity_main);
        tv=(TextView)findViewById(R.id.tv);
        left=(Button)findViewById(R.id.left);
        right=(Button)findViewById(R.id.right);
        left.setOnClickListener(leftdo);
        right.setOnClickListener(rightdo);
        tv.setText("Left Count:"+lefttime);
        tv.setText("Left Total:"+leftsum);
        tv.setText("Right Count:"+righttime);
        tv.setText("Right Total:"+rightsum);
    }
    private int leftsum=0;
    private int rightsum=0;
    private int lefttime=0;
    private int righttime=0;
    ArrayList urlData1 =new ArrayList();
    ArrayList urlData2 =new ArrayList();

       Button.OnClickListener leftdo=new Button.OnClickListener(){
       @Override
       public void onClick(View v) {

           String decodedString;
           String WebURL="https://boiling-headland-50805.herokuapp.com/click/1";
           lefttime++;
           try {
               //建立連線物件
               HttpURLConnection hc = null;
               //建立網址物件
               URL url = new URL(WebURL);

               hc = (HttpURLConnection) url.openConnection();

               //hc.setRequestMethod("GET");

               hc.setDoInput(true);

               hc.setDoOutput(true);

               hc.connect();
               //用BufferedReader讀回來
               BufferedReader in = new BufferedReader(new InputStreamReader(

                       hc.getInputStream()));

               while ((decodedString = in.readLine()) != null) {
                   urlData1.add(decodedString);
                    String valuestr=decodedString.substring(16);
                    int value=Integer.parseInt(valuestr);
                    leftsum+=value;
                    editor.putString("1",valuestr);
               }

               in.close();

           } catch (Exception e) {

               Log.e("ERROR", e.toString());

           }

       }

    };
    Button.OnClickListener rightdo=new Button.OnClickListener(){
        @Override
        public void onClick(View v) {
            String decodedString;
            String WebURL="https://boiling-headland-50805.herokuapp.com/click/2";
            righttime++;
            try {
                //建立連線物件
                HttpURLConnection hc = null;
                //建立網址物件
                URL url = new URL(WebURL);

                hc = (HttpURLConnection) url.openConnection();

                //hc.setRequestMethod("GET");

                hc.setDoInput(true);

                hc.setDoOutput(true);

                hc.connect();
                //用BufferedReader讀回來
                BufferedReader in = new BufferedReader(new InputStreamReader(

                        hc.getInputStream()));

                while ((decodedString = in.readLine()) != null) {
                    urlData2.add(decodedString);
                    String valuestr=decodedString.substring(16);
                    int value=Integer.parseInt(valuestr);
                    rightsum+=value;
                    editor.putString("2",valuestr);
                }

                in.close();

            } catch (Exception e) {

                Log.e("ERROR", e.toString());

            }
        }

    };
    private Notification notification_method() {
        Log.d("Debug","notification");
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, new Intent(this,MainActivity.class), 0);
        Notification notification;
        NotificationManager manager;
        //        建構notification物件，1.設定標題、2.設定訊息、3.設定時間、4.設定小圖示
        return notification = new Notification.Builder(this)
                .setContentTitle("exam2service")
                .setContentText("choose left or right/nPlease click one of thr following button")
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.drawable.banana)
                .build();
    }
}
