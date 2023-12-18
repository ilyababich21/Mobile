package com.example.nivaserviceandroid.datagenerator;

import androidx.annotation.NonNull;

import com.example.nivaserviceandroid.model.AlarmModel;
import com.example.nivaserviceandroid.model.EventModel;
import com.example.nivaserviceandroid.model.HarvesterModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

public class HarvesterDataGenerator {
    private final Random random = new Random();
    private final AlarmModel alarmModel = new AlarmModel();
    private final EventModel eventModel = new EventModel();

    public ArrayList<HarvesterModel> GenerateTestData(ArrayList<HarvesterModel> harvesters,
                                                      int numberOfRecordsToGenerate)
    {
        for (int i = 0; i < numberOfRecordsToGenerate; i++)
        {
            HarvesterModel harvester = new HarvesterModel();
            fillWithTheRandomData(harvester, i);
            harvesters.add(harvester);
        }
        return harvesters;
    }

    public ArrayList<AlarmModel> GenerateAlarmTestData(ArrayList<AlarmModel> alarms,
                                                       int numberOfRecordsToGenerate)
    {
        for (int i = 0; i < numberOfRecordsToGenerate; i++)
        {
            AlarmModel alarm = new AlarmModel();
            alarm.setId(i);
            alarm.setDate(getDateOfMessage(i));
            alarm.setAlarm(getRandomAlarm());
            alarms.add(alarm);
        }
        return alarms;
    }

    public ArrayList<EventModel> GenerateEventTestData(ArrayList<EventModel> events,
                                                       int numberOfRecordsToGenerate)
    {
        for (int i = 0; i < numberOfRecordsToGenerate; i++)
        {
            EventModel event = new EventModel();
            event.setId(i);
            event.setDate(getDateOfMessage(i));
            event.setEvent(getRandomEvent());
            events.add(event);
        }
        return events;
    }

    private void fillWithTheRandomData(@NonNull HarvesterModel harvester, int i)
    {
        harvester.setId(i);

        harvester.setDate(getDateOfMessage(i));

        harvester.setVoltage24DC(random.nextInt(24));
        harvester.setVoltage42AC(random.nextInt(42));
        harvester.setVoltage110AC(random.nextInt(110));
        harvester.setVoltage127AC(random.nextInt(127));

        harvester.setVoltage1140ACFazeA(random.nextInt(1140));
        harvester.setVoltage1140ACFazeB(random.nextInt(1140));
        harvester.setVoltage1140ACFazeC(random.nextInt(1140));

        harvester.setOilLevel(random.nextInt(100));
        harvester.setOilTemperature(random.nextInt(100));

        harvester.setLeftSpeed(random.nextInt(100));
        harvester.setRightSpeed(random.nextInt(100));

        harvester.setVerticalAngle(random.nextInt(100));
        harvester.setHorizontalAngle(random.nextInt(100));

        harvester.setDustExtractorAmperage(random.nextInt(100));
        harvester.setConveyorAmperage(random.nextInt(100));
        harvester.setBermAmperage(random.nextInt(100));
        harvester.setSecondBermAmperage(random.nextInt(100));
        harvester.setCuttingDiscsAmperage(random.nextInt(100));
        harvester.setPumpingStationAmperage(random.nextInt(100));
        harvester.setBunkerLoaderAmperage(random.nextInt(100));
        harvester.setApshAmperage(random.nextInt(100));

        harvester.setDisconnectorEngineHours(random.nextInt(100));
        harvester.setDustExtractorEngineHours(random.nextInt(100));
        harvester.setConveyorEngineHours(random.nextInt(100));
        harvester.setBermEngineHours(random.nextInt(100));
        harvester.setSecondBermEngineHours(random.nextInt(100));
        harvester.setCuttingDiscsEngineHours(random.nextInt(100));
        harvester.setPumpingStationEngineHours(random.nextInt(100));
        harvester.setBunkerLoaderEngineHours(random.nextInt(100));
    }

    public String getRandomAlarm()
    {
        int number = random.nextInt(alarmModel.AlarmMessages.length - 1);
        return alarmModel.AlarmMessages[number];
    }

    public String getRandomEvent()
    {
        int number = random.nextInt(eventModel.EventMessages.length - 1);
        return eventModel.EventMessages[number];
    }

    public String getDateOfMessage(int i)
    {
        SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
        Date date = new Date();
        date.setSeconds(date.getSeconds() + i);

        return formatter.format(date);
    }
}
