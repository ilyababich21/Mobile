package com.example.nivaserviceandroid.viewmodel.bluetooth;

import android.bluetooth.BluetoothSocket;
import android.util.Log;
import com.example.nivaserviceandroid.datagenerator.MachineDataGenerator;
import com.example.nivaserviceandroid.model.AlarmModel;
import com.example.nivaserviceandroid.model.EventModel;
import com.example.nivaserviceandroid.model.HarvesterModel;
import com.example.nivaserviceandroid.viewmodel.MainViewModel;
import com.example.nivaserviceandroid.viewmodel.modbus.Modbus;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

public class ReceiveThread extends Thread {
    private final BluetoothSocket bluetoothSocket;
    private InputStream inputStream;
    private OutputStream outputStream;
    private final MachineDataGenerator machineDataGenerator = new MachineDataGenerator();
    private final Modbus modbus = new Modbus();
    private int[] frame;

    private int telemetryID = 0;
    private int alarmID = 0;
    private int eventID = 0;
    private int countOfNullPockets = 0;

    public ReceiveThread(BluetoothSocket bluetoothSocket) {
        this.bluetoothSocket = bluetoothSocket;
        try {
            inputStream = bluetoothSocket.getInputStream();
            modbus.inputStream = inputStream;
        } catch (IOException e) {
        }
        try {
            outputStream = bluetoothSocket.getOutputStream();
            modbus.outputStream = outputStream;
        } catch (IOException e) {
        }
    }

    public void sendData() {
        Runnable runnable = () -> {
            try {
                modbus.ReadHoldingRegisters(1, 0, 20);
            } catch (IOException e) {
                e.printStackTrace();
            }
        };
        Thread thread = new Thread(runnable);
        thread.start();
    }

    @Override
    public void run() {
        while (true) {
            try {
                if(frame == null) {
                    modbus.ReadHoldingRegisters(1, 0, 8);
                    Thread.sleep(200);
                    getDataFromResponce(modbus.readPDU().Raw);
                } else if(frame[0] == 0){
                    receiveStatusData(frame[0]);
                } else if(frame[0] == 1){
                    receiveStatusData(frame[0]);
                } else if(frame[0] == 2){
                    receiveTelemetryData();
                }

            } catch (IOException e) {
                Log.d("MyLog", e.getMessage());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if(countOfNullPockets > 10){
                break;
            }
        }
    }

    private void receiveStatusData(int statusID) throws IOException, InterruptedException {
        modbus.ReadHoldingRegisters(1, 0, 9);
        Thread.sleep(50);
        getDataFromResponce(modbus.readPDU().Raw);

        if(statusID == 0) {
            AlarmModel alarm = new AlarmModel();
            fillWithTheAlarmData(alarm);
            MainViewModel.loadAlarms(alarm);
        } else {
            EventModel event = new EventModel();
            fillWithTheEventData(event);
            MainViewModel.loadEvents(event);
        }
    }

    public void receiveTelemetryData() throws IOException, InterruptedException{
        modbus.ReadHoldingRegisters(1, 0, 42);
        Thread.sleep(100);
        getDataFromResponce(modbus.readPDU().Raw);
        HarvesterModel harvester = new HarvesterModel();
        fillWithTheTelemetryData(harvester);
        MainViewModel.loadHarvesters(harvester);
    }

    public void getDataFromResponce(byte[] receiveBuffer) {
        int j = 0;
        frame = new int[50];
        if (receiveBuffer != null && receiveBuffer[0] == 0x01) {
            receiveBuffer = Arrays.copyOfRange(receiveBuffer, 3, receiveBuffer.length - 1);

            for (int i = 0; i < receiveBuffer.length / 2; i++) {
                frame[i] = fromBytes(receiveBuffer[j + 1], receiveBuffer[j]);
                j += 2;
            }
        }
    }

    private void fillWithTheTelemetryData(HarvesterModel harvester) {
        harvester.setId(++telemetryID);

        SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss.SSS");
        Date date = new Date(frame[4], frame[3], frame[2], frame[5], frame[6], frame[7]);
        harvester.setDate(formatter.format(date));

        harvester.setVoltage24DC(frame[8]);
        harvester.setVoltage42AC(frame[9]);
        harvester.setVoltage110AC(frame[10]);
        harvester.setVoltage127AC(frame[11]);

        harvester.setVoltage1140ACFazeA(frame[12]);
        harvester.setVoltage1140ACFazeB(frame[13]);
        harvester.setVoltage1140ACFazeC(frame[14]);

        harvester.setOilLevel(frame[15]);
        harvester.setOilTemperature(frame[16]);

        harvester.setLeftSpeed(frame[17]);
        harvester.setRightSpeed(frame[18]);

        harvester.setVerticalAngle(frame[19]);
        harvester.setHorizontalAngle(frame[20]);

        harvester.setDustExtractorAmperage(frame[21]);
        harvester.setConveyorAmperage(frame[22]);
        harvester.setBermAmperage(frame[23]);
        harvester.setSecondBermAmperage(frame[24]);
        harvester.setCuttingDiscsAmperage(frame[25]);
        harvester.setPumpingStationAmperage(frame[26]);
        harvester.setBunkerLoaderAmperage(frame[27]);
        harvester.setApshAmperage(frame[28]);

        harvester.setDisconnectorEngineHours(frame[29]);
        harvester.setDustExtractorEngineHours(frame[30]);
        harvester.setConveyorEngineHours(frame[31]);
        harvester.setBermEngineHours(frame[32]);
        harvester.setSecondBermEngineHours(frame[33]);
        harvester.setCuttingDiscsEngineHours(frame[34]);
        harvester.setPumpingStationEngineHours(frame[35]);
        harvester.setBunkerLoaderEngineHours(frame[36]);
    }

    private void fillWithTheAlarmData(AlarmModel alarm){
        alarm.setId(++alarmID);
        SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
        Date date = new Date(frame[4] - 1900, frame[3], frame[2], frame[5], frame[6], frame[7]);
        alarm.setDate(formatter.format(date));
        if (frame[8] == 254 || frame[8] == 255) {
            alarm.setAlarm(alarm.AdditionalAlarmMessages[frame[8] - 254]);
        }
        else {
            alarm.setAlarm(alarm.AlarmMessages[frame[8]]);
        }
    }

    private void fillWithTheEventData(EventModel eventModel){
        eventModel.setId(++eventID);
        SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
        Date date = new Date(frame[4], frame[3], frame[2], frame[5], frame[6], frame[7]);
        eventModel.setDate(formatter.format(date));
        eventModel.setEvent(eventModel.EventMessages[frame[8]]);
    }

    public static char fromBytes(byte LoVal, byte HiVal) {
        return (char) (HiVal << 8 | LoVal & 0xFF);
    }
}
