package com.example.nivaserviceandroid.viewmodel.bluetooth;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;

import com.example.nivaserviceandroid.viewmodel.MainViewModel;
import com.example.nivaserviceandroid.viewmodel.adapter.BluetoothConsts;

public class BluetoothConnection {

    private final Context context;
    private final SharedPreferences pref;
    private final BluetoothAdapter btAdapter;
    private BluetoothDevice device;
    public ConnectThread connectThread;

    public BluetoothConnection(Context context) {
        this.context = context;
        pref  = context.getSharedPreferences(BluetoothConsts.MY_PREF, Context.MODE_PRIVATE);
        btAdapter = BluetoothAdapter.getDefaultAdapter();
    }

    //TODO add progress bar while communication
    public void connect(){
        String mac = pref.getString(BluetoothConsts.MAC_KEY, "");
        if(!btAdapter.isEnabled() || mac.isEmpty()) return;
        device = btAdapter.getRemoteDevice(mac);
        if(device == null) return;
        connectThread = new ConnectThread(btAdapter, device, context);
        connectThread.start();
    }

    public void sendData() throws InterruptedException {
        if(connectThread != null) {
            connectThread.receiveThread.sendData();
        } else {
            Toast.makeText(context, "Подключитесь к устройству NivaService.", Toast.LENGTH_SHORT).show();
        }
    }
}
