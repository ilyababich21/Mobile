package com.example.nivaserviceandroid.view;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.example.nivaserviceandroid.R;
import com.example.nivaserviceandroid.viewmodel.adapter.BluetoothListItem;
import com.example.nivaserviceandroid.viewmodel.adapter.BtAdapter;
import com.example.nivaserviceandroid.viewmodel.bluetooth.BluetoothConnection;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class BluetoothListFragment extends Fragment {
    private final int BT_REQUEST_PERM = 111;
    private ListView listView;
    private BtAdapter adapter;
    private BluetoothAdapter btAdapter;
    private List<BluetoothListItem> list;
    private boolean isBtPermissionGranted = false;
    private boolean isDiscovery = false;
    private ActionBar actionBar;
    private BluetoothConnection bluetoothConnection;
    private View rootView;
    ProgressBar progressBar;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        actionBar = ((MainActivity) getActivity()).getSupportActionBar();
        btAdapter = BluetoothAdapter.getDefaultAdapter();
        rootView = inflater.inflate(R.layout.fragment_bluetooth_list, container, false);
        progressBar = rootView.findViewById(R.id.progressBar);
        list = new ArrayList<>();
        listView = rootView.findViewById(R.id.listView);
        bluetoothConnection = new BluetoothConnection(rootView.getContext());
        adapter = new BtAdapter(rootView.getContext(), R.layout.bluetooth_list_item, list);
        listView.setAdapter(adapter);
        getBtPermission();
        getPairedDevices();
        onItemClickListener();

        return rootView;
    }

    @SuppressLint("MissingPermission")
    private void onItemClickListener() {
        listView.setOnItemClickListener((parent, view, position, id) -> {
            BluetoothListItem item = (BluetoothListItem) parent.getItemAtPosition(position);
            if (item.getItemType().equals(BtAdapter.DISCOVERY_ITEM_TYPE))
                item.getBluetoothDevice().createBond();
        });
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        IntentFilter intentFilter1 = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        IntentFilter intentFilter2 = new IntentFilter(BluetoothDevice.ACTION_BOND_STATE_CHANGED);
        IntentFilter intentFilter3 = new IntentFilter(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
        rootView.getContext().registerReceiver(broadcastReceiver, intentFilter1);
        rootView.getContext().registerReceiver(broadcastReceiver, intentFilter2);
        rootView.getContext().registerReceiver(broadcastReceiver, intentFilter3);
    }

    @Override
    public void onPause() {
        super.onPause();
        rootView.getContext().unregisterReceiver(broadcastReceiver);
    }

    @SuppressLint("RestrictedApi")
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.bluetooth_list_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
        actionBar.setDefaultDisplayHomeAsUpEnabled(true);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @SuppressLint("MissingPermission")
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.id_search) {
            if (isDiscovery) return true;
            actionBar.setTitle(R.string.discovering);
            list.clear();
            BluetoothListItem itemTitle = new BluetoothListItem();
            itemTitle.setItemType(BtAdapter.TITLE_ITEM_TYPE);
            list.add(itemTitle);
            adapter.notifyDataSetChanged();
            btAdapter.startDiscovery();
            isDiscovery = true;

        } else if (item.getItemId() == R.id.id_connect) {
            //progressBar.setVisibility(View.VISIBLE);
            bluetoothConnection.connect();
            //TODO: after disconnection disable send request button
        } else if (item.getItemId() == R.id.id_send_request) {
            if (true) {
                try {
                    bluetoothConnection.sendData();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } else {
                Toast.makeText(rootView.getContext(), "Передача невозможна, подключитесь к устройству.",
                        Toast.LENGTH_LONG).show();
            }
        }
        return true;
    }

    @SuppressLint("MissingPermission")
    private void getPairedDevices() {
        @SuppressLint("MissingPermission")
        Set<BluetoothDevice> pairedDevices = btAdapter.getBondedDevices();
        if (pairedDevices.size() > 0) {
            list.clear();
            for (BluetoothDevice device : pairedDevices) {
                @SuppressLint("MissingPermission")
                BluetoothListItem item = new BluetoothListItem();
                item.setBluetoothDevice(device);
                list.add(item);
            }
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == BT_REQUEST_PERM) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                isBtPermissionGranted = true;
                Toast.makeText(rootView.getContext(), "Разрешение получено.", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(rootView.getContext(), "Нет разрешения на поиск блютуз устроиств.",
                        Toast.LENGTH_SHORT).show();
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    private void getBtPermission() {
        if (ContextCompat.checkSelfPermission(rootView.getContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions((Activity) rootView.getContext(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    BT_REQUEST_PERM);
        } else {
            isBtPermissionGranted = true;
        }
    }

    private final BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @SuppressLint("MissingPermission")
        @Override
        public void onReceive(Context context, Intent intent) {
            if (BluetoothDevice.ACTION_FOUND.equals(intent.getAction())) {
                BluetoothDevice bluetoothDevice = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                BluetoothListItem item = new BluetoothListItem();
                item.setBluetoothDevice(bluetoothDevice);
                item.setItemType(BtAdapter.DISCOVERY_ITEM_TYPE);
                if (bluetoothDevice.getName() != null) list.add(item);
                adapter.notifyDataSetChanged();
            } else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(intent.getAction())) {
                isDiscovery = false;
                getPairedDevices();
                actionBar.setTitle(R.string.app_name);
            } else if (BluetoothDevice.ACTION_BOND_STATE_CHANGED.equals(intent.getAction())) {
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                if (device.getBondState() == BluetoothDevice.BOND_BONDED) {
                    getPairedDevices();
                }
            }
        }
    };
}
