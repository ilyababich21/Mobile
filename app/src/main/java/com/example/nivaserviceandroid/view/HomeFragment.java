package com.example.nivaserviceandroid.view;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.Toast;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.nivaserviceandroid.R;
import com.example.nivaserviceandroid.model.AlarmModel;
import com.example.nivaserviceandroid.model.DashboardModel;
import com.example.nivaserviceandroid.model.EventModel;
import com.example.nivaserviceandroid.model.HarvesterModel;
import com.example.nivaserviceandroid.viewmodel.HomeAdapter;
import com.example.nivaserviceandroid.viewmodel.MainViewModel;
import com.example.nivaserviceandroid.viewmodel.adapter.BluetoothConsts;

import java.util.ArrayList;

@RequiresApi(api = Build.VERSION_CODES.S)
public class HomeFragment extends Fragment {
    private static final int RESULT_OK = -1;
    private GridView coursesGV;
    private MainViewModel mainViewModel;

    private final ArrayList<DashboardModel> DashboardModel = new ArrayList<>();
    private ArrayList<HarvesterModel> machineModels = new ArrayList<>();
    private ArrayList<AlarmModel> alarmModels = new ArrayList<>();
    private ArrayList<EventModel> eventModels = new ArrayList<>();

    private BluetoothAdapter bluetoothAdapter;
    private final int ENABLE_REQUEST = 15;
    private SharedPreferences pref;
    private MenuItem menuItem;

    private View rootView;

    final String[] PERMISSIONS = {
            Manifest.permission.BLUETOOTH,
            Manifest.permission.BLUETOOTH_CONNECT,
            Manifest.permission.BLUETOOTH_SCAN
    };

    int machineEngineHours;

    private ActivityResultContracts.RequestMultiplePermissions multiplePermissionsContract;
    private ActivityResultLauncher<String[]> multiplePermissionLauncher;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mainViewModel = new ViewModelProvider(this).get(MainViewModel.class);
        rootView = inflater.inflate(R.layout.fragment_home, container, false);
        coursesGV = rootView.findViewById(R.id.summaryGrid);

        init();

        multiplePermissionsContract = new ActivityResultContracts.RequestMultiplePermissions();
        multiplePermissionLauncher = registerForActivityResult(multiplePermissionsContract, isGranted -> {
            if (isGranted.containsValue(false)) {
                multiplePermissionLauncher.launch(PERMISSIONS);
            }
        });
        askPermissions(multiplePermissionLauncher);

        if(!machineModels.isEmpty() || !alarmModels.isEmpty() || !eventModels.isEmpty()) {
            getSummary();
        }

        HomeAdapter adapter = new HomeAdapter(rootView.getContext(), DashboardModel);
        coursesGV.setAdapter(adapter);

        return rootView;
    }

    private void askPermissions(ActivityResultLauncher<String[]> multiplePermissionLauncher) {
        if (!hasPermissions(PERMISSIONS)) {
            multiplePermissionLauncher.launch(PERMISSIONS);
        }
    }

    private boolean hasPermissions(String[] permissions) {
        if (permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(rootView.getContext(), permission) != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(rootView.getContext(), "Разрешите доступ приложению к bluetooth, для ипользования функционала",
                            Toast.LENGTH_SHORT).show();
                    return false;
                }
                Toast.makeText(rootView.getContext(), "Доступ приложению к bluetooth разрешен",
                        Toast.LENGTH_SHORT).show();
            }
            return true;
        }
        return false;
    }


    private void getSummary() {
        int telemetryLastIndex = machineModels.size() - 1;

        DashboardModel lastRecordData = new DashboardModel();
        lastRecordData.setLabel("Последняя запись");
        lastRecordData.setImgid(R.drawable.ic_last_record);
        lastRecordData.setValue(machineModels.get(telemetryLastIndex).getDate());
        DashboardModel.add(lastRecordData);

        DashboardModel countOfTelemetryRecords = new DashboardModel();
        countOfTelemetryRecords.setLabel("Записей с телеметрией");
        countOfTelemetryRecords.setImgid(R.drawable.ic_records_count);
        countOfTelemetryRecords.setValue(String.valueOf(machineModels.size()));
        DashboardModel.add(countOfTelemetryRecords);

        DashboardModel countOfAlarmRecords = new DashboardModel();
        countOfAlarmRecords.setLabel("Записей с авариями");
        countOfAlarmRecords.setImgid(R.drawable.ic_records_count);
        countOfAlarmRecords.setValue(String.valueOf(alarmModels.size()));
        DashboardModel.add(countOfAlarmRecords);

        DashboardModel countOfEventRecords = new DashboardModel();
        countOfEventRecords.setLabel("Записей с событиями");
        countOfEventRecords.setImgid(R.drawable.ic_records_count);
        countOfEventRecords.setValue(String.valueOf(eventModels.size()));
        DashboardModel.add(countOfEventRecords);

        DashboardModel disconnectorEngineHours = new DashboardModel();
        disconnectorEngineHours.setLabel("Моточасы разъединителя");
        disconnectorEngineHours.setImgid(R.drawable.ic_engine_hours);
        disconnectorEngineHours.setValue(String.valueOf(machineModels.get(telemetryLastIndex).
                getDisconnectorEngineHours()));
        DashboardModel.add(disconnectorEngineHours);

        DashboardModel dustExtractorEngineHours = new DashboardModel();
        dustExtractorEngineHours.setLabel("Моточасы пылеотсоса(М1)");
        dustExtractorEngineHours.setImgid(R.drawable.ic_hours_to_first_service);
        dustExtractorEngineHours.setValue(String.valueOf(machineModels.get(telemetryLastIndex).
                getDustExtractorEngineHours()));
        DashboardModel.add(dustExtractorEngineHours);

        DashboardModel conveyorEngineHours = new DashboardModel();
        conveyorEngineHours.setLabel("Моточасы конвейера(М2)");
        conveyorEngineHours.setImgid(R.drawable.ic_hours_to_first_service);
        conveyorEngineHours.setValue(String.valueOf(machineModels.get(telemetryLastIndex).
                getConveyorEngineHours()));
        DashboardModel.add(conveyorEngineHours);

        DashboardModel bermFirstEngineHours = new DashboardModel();
        bermFirstEngineHours.setLabel("Моточасы органа бермового(М4)");
        bermFirstEngineHours.setImgid(R.drawable.ic_hours_to_first_service);
        bermFirstEngineHours.setValue(String.valueOf(machineModels.get(telemetryLastIndex).
                getBermEngineHours()));
        DashboardModel.add(bermFirstEngineHours);

        DashboardModel bermSecondEngineHours = new DashboardModel();
        bermSecondEngineHours.setLabel("Моточасы органа бермового(М5)");
        bermSecondEngineHours.setImgid(R.drawable.ic_hours_to_second_service);
        bermSecondEngineHours.setValue(String.valueOf(machineModels.get(telemetryLastIndex).
                getSecondBermEngineHours()));
        DashboardModel.add(bermSecondEngineHours);

        DashboardModel cuttingDiscsEngineHours = new DashboardModel();
        cuttingDiscsEngineHours.setLabel("Моточасы дисков режущих(М6)");
        cuttingDiscsEngineHours.setImgid(R.drawable.ic_hours_to_second_service);
        cuttingDiscsEngineHours.setValue(String.valueOf(machineModels.get(telemetryLastIndex).
                getCuttingDiscsEngineHours()));
        DashboardModel.add(cuttingDiscsEngineHours);

        DashboardModel pumpingStationEngineHours = new DashboardModel();
        pumpingStationEngineHours.setLabel("Моточасы станции насосной(М10)");
        pumpingStationEngineHours.setImgid(R.drawable.ic_hours_to_second_service);
        pumpingStationEngineHours.setValue(String.valueOf(machineModels.get(telemetryLastIndex).
                getPumpingStationEngineHours()));
        DashboardModel.add(pumpingStationEngineHours);

        DashboardModel bunkerLoaderEngineHours = new DashboardModel();
        bunkerLoaderEngineHours.setLabel("Моточасы бункера-перегружателя(М12)");
        bunkerLoaderEngineHours.setImgid(R.drawable.ic_hours_to_second_service);
        bunkerLoaderEngineHours.setValue(String.valueOf(machineModels.get(telemetryLastIndex).
                getBunkerLoaderEngineHours()));
        DashboardModel.add(bunkerLoaderEngineHours);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState){
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.home_menu, menu);
        menuItem = menu.findItem(R.id.id_bluetooth);
        setButtonIcon();

        super.onCreateOptionsMenu(menu, inflater);
    }

    @SuppressLint("MissingPermission")
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.id_bluetooth) {
            if (!bluetoothAdapter.isEnabled()) {
                enableBluetooth();
            } else {
                bluetoothAdapter.disable();
                menuItem.setIcon(R.drawable.ic_bluetooth);
            }
        } else if (item.getItemId() == R.id.id_bluetooth_list) {
            if (bluetoothAdapter.isEnabled()) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new BluetoothListFragment()).commit();
            } else {
                Toast.makeText(rootView.getContext(), "Включите блютуз для перехода на этот экран",
                        Toast.LENGTH_SHORT).show();
            }
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ENABLE_REQUEST) {

            if (resultCode == RESULT_OK) {
                setButtonIcon();
            }
        }
    }

    private void setButtonIcon() {
        if (bluetoothAdapter.isEnabled()) {
            menuItem.setIcon(R.drawable.ic_bluetooth_disabled);
        } else {
            menuItem.setIcon(R.drawable.ic_bluetooth);
        }
    }

    private void enableBluetooth() {
        Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
        /*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {

        } else {*/
            startActivityForResult(intent, ENABLE_REQUEST);
        //}
    }

    private void init(){
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        pref = this.getActivity().getSharedPreferences(BluetoothConsts.MY_PREF, Context.MODE_PRIVATE);
    }
}
