package com.example.flightgearappandroid.model;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

import androidx.annotation.Nullable;
import androidx.databinding.BaseObservable;

import static android.os.SystemClock.sleep;

public class FgModel extends BaseObservable {

    // fields
    private String ip;
    private String port;
    private Socket connection;
    private boolean connected;
    private PrintWriter out;
    private ExecutorService pool;
    private int throttle;
    private int rudder;
    // constructor
    public FgModel(){
        ip = null; // the ip
        port = null; // the port
        connection = null; // the connection Socket
        connected = false; // bool to know if we connected
        out = null; // the stream with the flight gear
        pool = Executors.newFixedThreadPool(1); // thread pool with one object (active object)
        throttle = 0;
        rudder = 100;
    }
    // Setters & Getters
    public void set_ip_and_port(String i, String p){
        this.ip = i;
        this.port = p;
    }
    public void setIp(String ip){
        this.ip = ip;
    }
    public String getIp(){
        return this.ip;
    }
    public void setPort(String p){
        this.port = p;
    }
    public String getPort(){
        return this.port;
    }
    public void setThrottle(int x){
        this.throttle = x;
    }
    public int getThrottle(){
        return this.throttle;
    }
    public int getRudder(){
        return this.rudder;
    }
    public void setRudder(int x){
        this.rudder = x;
    }
    // connect to the fg simulator
    public boolean connect() {
        int ports = Integer.parseInt(this.port); // change the port to int.
        // do the connection in diffrent thread.
        this.pool.execute(() ->{ //
            try {
                connection = new Socket(ip, ports);
                out = new PrintWriter(connection.getOutputStream(),true);
                connected = true;
            }catch (java.io.IOException e) {connected = false;}
        });
        // i wait 30 ms to be to know if we sucees to connect.
        sleep(50);
        return connected;
    }
    public boolean isConnected(){
        return this.connected;
    }
    public void SetThrottle(int x){
        this.throttle = x;
    }
    // change the aircraft movment
    public void ChangeTrottle(){
        this.pool.execute(() ->{
            double f = this.throttle/100.0;
            String t = Double.toString(f);
            out.print("set /controls/engines/current-engine/throttle "+t+"\r\n");
            out.flush();
        });
    }
    public void changeRudder(){
        this.pool.execute(() ->{
            int rudderCopy = rudder - 100;
            double f = rudderCopy/100.0;
            String t = Double.toString(f);
            out.print("set /controls/flight/rudder "+t+"\r\n");
            out.flush();
        });
    }
    public void changeAileron(float x){
        this.pool.execute( () ->{
            float Aileron = x - 500;
            Aileron = Aileron/500;
            String a = Float.toString(Aileron);
            out.print("set /controls/flight/aileron "+a+"\r\n");
            out.flush();
        });
    }
    public void changeEvelator(float x){
        this.pool.execute( () ->{
            float Evelator = x - 160;
            Evelator = Evelator/160;
            String a = Float.toString(Evelator);
            out.print("set /controls/flight/elevator "+a+"\r\n");
            out.flush();
        });
    }




}
