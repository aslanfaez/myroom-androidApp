package com.example.aslan.aslanroom;

import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.StrictMode;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.charset.Charset;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;


public class MainActivity extends AppCompatActivity {

    private static final String TAG = "TCPClient";
    Socket s = null;
    OutputStream ou = null;
    EditText txtcon = null;
    SeekBar lightPower = null;
    SeekBar lightWormth = null;
    SeekBar RR = null;
    SeekBar GG = null;
    SeekBar BB = null;
    Button rgb = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        txtcon = (EditText)findViewById(R.id.conState);
        lightPower = (SeekBar)findViewById(R.id.seekLight);
        lightWormth = (SeekBar)findViewById(R.id.seekTemp);
        RR = (SeekBar)findViewById(R.id.seekRed);
        GG = (SeekBar)findViewById(R.id.seekGreen);
        BB = (SeekBar)findViewById(R.id.seekBlue);
        rgb = (Button)findViewById(R.id.button12);
        RR.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                PreRGB(seekBar);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });        GG.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                PreRGB(seekBar);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });        BB.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                PreRGB(seekBar);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        try {
            s = new Socket("192.168.0.30",8);
        } catch (IOException e) {
            e.printStackTrace();
        }
        catch(Exception e){

        }
        try {
            ou = s.getOutputStream();
            txtcon.setText("Connected");
        } catch (IOException e) {
            e.printStackTrace();
        }
        catch(Exception e){

        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void lightsOn(View v) {
        sendcmd("1,");
    }
    public void lightsOff(View v) {
        sendcmd("2,");
    }
    public void spkOn(View v){
        sendcmd("7,");
    }
    public void spkOff(View v){
        sendcmd("8,");
    }
    public void DiscoOn(View v){
        sendcmd("9,");
    }
    public void DiskoOff(View v){
        sendcmd("10,");
    }
    public void RGBOn(View v){
        sendcmd("4,");
    }
    public void RGBOff(View v){
        sendcmd("5,");
    }
    public void PreRGB(View v){
        int red = RR.getProgress();
        int green = GG.getProgress();
        int blue = BB.getProgress();
        rgb.setBackgroundColor(Color.rgb(red,green,blue));
    }
    public void setRGB(View v){

        int red = RR.getProgress();
        int green = GG.getProgress();
        int blue = BB.getProgress();
        String cm = "6," + String.valueOf(red)+","+String.valueOf(green)+","+String.valueOf(blue)+",";
        sendcmd(cm);
    }
    public void setLights(View v){
        int pp = lightPower.getProgress();
        int tmp = lightWormth.getProgress();

        int white = 0;
        int yellow = 0;
        if(tmp > 255){
            white = 255;
            yellow = 255-(tmp - 255);
        }
        else{
            yellow = 255;
            white = tmp;
        }
        white = (int)(white*pp/255);
        yellow =(int)(yellow*pp/255);

        String cm = "3," + String.valueOf(white)+","+String.valueOf(yellow)+",";
        sendcmd(cm);
    }
    public void reconnect(View v){
        try {
            s = new Socket("192.168.0.30",8);
        } catch (IOException e) {
            e.printStackTrace();
        }
        catch(Exception e){

        }
        try {
            ou = s.getOutputStream();
            txtcon.setText("connected");
        } catch (IOException e) {
            e.printStackTrace();
        }
        catch(Exception e){

        }

    }
    public void sendcmd( String msg){
        try {

            ou.write(msg.getBytes());

            //outgoing stream redirect to socket


        }
        catch (Exception e){
            txtcon.setText("not connected");
        }
}}

