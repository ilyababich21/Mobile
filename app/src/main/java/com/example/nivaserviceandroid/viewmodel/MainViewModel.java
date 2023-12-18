package com.example.nivaserviceandroid.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.nivaserviceandroid.datagenerator.HarvesterDataGenerator;
import com.example.nivaserviceandroid.datagenerator.MachineDataGenerator;
import com.example.nivaserviceandroid.model.AlarmModel;
import com.example.nivaserviceandroid.model.EventModel;
import com.example.nivaserviceandroid.model.HarvesterModel;
import com.example.nivaserviceandroid.model.MachineModel;

import java.util.ArrayList;

public class MainViewModel extends ViewModel {
    private static MutableLiveData<ArrayList<HarvesterModel>> harvesters;
    private static MutableLiveData<ArrayList<AlarmModel>> alarms;
    private static MutableLiveData<ArrayList<EventModel>> events;

    private static final ArrayList<HarvesterModel> harvesterList = new ArrayList<>();
    private static final ArrayList<AlarmModel> alarmsList = new ArrayList<>();
    private static final ArrayList<EventModel> eventsList = new ArrayList<>();

    public MutableLiveData<ArrayList<HarvesterModel>> getMachines() {
        if (harvesters == null) {
            harvesters = new MutableLiveData<>();
        }
        return harvesters;
    }

    public MutableLiveData<ArrayList<AlarmModel>> getAlarms() {
        if (alarms == null) {
            alarms = new MutableLiveData<>();
        }
        return alarms;
    }

    public MutableLiveData<ArrayList<EventModel>> getEvents() {
        if (events == null) {
            events = new MutableLiveData<>();
        }
        return events;
    }

    public static void loadAlarms(AlarmModel alarm){
        new Thread(() -> {
            alarmsList.add(alarm);
            alarms.postValue(alarmsList);
        }).start();
    }

    public static void loadEvents(EventModel event){
        new Thread(() -> {
            eventsList.add(event);
            events.postValue(eventsList);
        }).start();
    }

    public static void loadHarvesters(HarvesterModel harvester){
        new Thread(() -> {
            harvesterList.add(harvester);
            harvesters.postValue(harvesterList);
        }).start();
    }
}
