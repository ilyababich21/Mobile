package com.example.nivaserviceandroid.viewmodel.bluetooth;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import java.io.IOException;

public class ConnectThread extends Thread{
    private final Context context;
    private final BluetoothAdapter btAdapter;
    private final BluetoothDevice device;
    private final Toast successfulConnect;
    private final Toast unsuccessfulConnect;
    private BluetoothSocket mSocket;
    public ReceiveThread receiveThread;
    public static final String UUID = "00001101-0000-1000-8000-00805F9B34FB";

    @SuppressLint("MissingPermission")
    public ConnectThread(BluetoothAdapter btAdapter, BluetoothDevice device, Context context){
        this.btAdapter = btAdapter;
        this.device = device;
        this.context = context;
        successfulConnect = Toast.makeText(context, "Подключено", Toast.LENGTH_SHORT);
        unsuccessfulConnect = Toast.makeText(context, "Не подключено, попробуйте еще раз", Toast.LENGTH_SHORT);
        try{
            mSocket = device.createInsecureRfcommSocketToServiceRecord(java.util.UUID.fromString(UUID));
        }
        catch (IOException e){
        }
    }

    @SuppressLint("MissingPermission")
    @Override
    public void run(){
        btAdapter.cancelDiscovery();

        try{
            mSocket.connect();
            receiveThread = new ReceiveThread(mSocket);
            receiveThread.start();
            successfulConnect.show();
            Log.d("MyLog", "Connected");

        } catch (IOException e){
            unsuccessfulConnect.show();
            Log.d("MyLog", "Not connected");
            Log.d("MyLog", e.getMessage());
            closeConnection();
        }
    }

    public void closeConnection(){
        try {
            mSocket.close();
        }
        catch (IOException ex) {
        }
    }

    public ReceiveThread getReceiveThread() {
        return receiveThread;
    }
}
