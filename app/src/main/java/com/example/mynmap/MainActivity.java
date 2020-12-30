package com.example.mynmap;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import java.io.DataOutputStream;

public class MainActivity extends AppCompatActivity {
    private boolean stopflag = false;
    static Handler handler;
    Button hostscan_bt, useshell;
    EditText ipView;
    TextView scanning;
    String command, mOutput, scanning_ip, thisDir;
    CheckBox forall;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ipView = (EditText) findViewById(R.id.command);
        scanning = (TextView) findViewById(R.id.scanning);
        hostscan_bt = (Button) findViewById(R.id.hostscan_bt);
        useshell = (Button) findViewById(R.id.useshell);
        forall = (CheckBox)findViewById(R.id.forall);

        thisDir = this.getFilesDir() + "";
        Asset asset = new Asset();
        IP ip = new IP();
        handler = new Handler(){
            @Override
            public void handleMessage(Message msg){
                if(msg.arg1 == 1){
                    scanning.setText("");
                    hostscan_bt.setEnabled(true);
                    useshell.setEnabled(true);
                    Intent intent = new Intent(getApplicationContext(), HostScan.class);
                    intent.putExtra("data", mOutput);
                    startActivity(intent);
                }
                if(msg.what > 0){
                    scanning.setText(scanning_ip + "를 스캔합니다\n" + "Scanning..." + msg.what + "초 경과");
                }
            }
        };
        asset.copyAssets(this, "nmap");
        try{
            scanning_ip = ip.getMyIPAddress();
            ipView.setText(scanning_ip);
            Process p = Runtime.getRuntime().exec("su");
            DataOutputStream os = new DataOutputStream(p.getOutputStream());
            os.writeBytes("chmod -R 777 " + thisDir + "/nmap" + "\n");
        } catch (Exception e) {
            e.printStackTrace();
        }

        hostscan_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                scanning.setText("Scanning...");
                hostscan_bt.setEnabled(false);
                useshell.setEnabled(false);
                stopflag = false;
                try{
                    new Thread(new Runnable() {
                        @Override
                        public void run(){
                            for(int i = 1; !stopflag; i++){
                                try{
                                    handler.sendEmptyMessage(i);
                                    Thread.sleep(1000);
                                } catch (InterruptedException e) { }
                            }
                        }
                    }).start();

                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            Message message = Message.obtain();
                            ShellExecuter exe = new ShellExecuter();
                            scanning_ip = ipView.getText().toString();
                            if(forall.isChecked()){
                                String[] splited_Ip = scanning_ip.split("\\.");
                                scanning_ip = splited_Ip[0] + "." + splited_Ip[1] + "." + splited_Ip[2] + ".1/24";
                            }
                            command = thisDir + "/nmap/bin/nmap -sV --dns-servers 8.8.8.8 --script=vulscan --script-args vulscan.db=cve.csv " + scanning_ip;
                            mOutput = exe.Executer(command);
                            stopflag = true;
                            message.arg1 = 1;
                            handler.sendMessage(message);
                        }
                    }).start();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        useshell.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent goShell = new Intent(getApplicationContext(), UseShell.class);
                startActivity(goShell);
            }
        });
    }
}
