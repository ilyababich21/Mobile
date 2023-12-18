package com.example.nivaserviceandroid.datagenerator;

import androidx.annotation.NonNull;

import com.example.nivaserviceandroid.model.MachineModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

public class MachineDataGenerator {
    private final String[] Errors = new String[] {
            "Выбрана 2 скорость.",
            "Нажат сигнал.",
            "Ход назад включен.",
            "Скорость 1 хода включена.",
            "Скорость 2 хода включена.",
            "Ход выключен.",
            "Маслостанция включена.",
            "Автоматическое переключение света.",
            "Отсутствуют опорные напряжения.",
            "Обрыв заземляющего провода прав. ЭД ход.",
            "Отсутствуют опорные напряжения."
    };

    private final Random random = new Random();

    public ArrayList<MachineModel> GenerateTestData(ArrayList<MachineModel> machines, int numberOfRecordsToGenerate)
    {
        for (int i = 0; i < numberOfRecordsToGenerate; i++)
        {
            MachineModel emp = new MachineModel();
            fillWithTheRandomData(emp, i);
            machines.add(emp);
        }
        return machines;
    }

    private void fillWithTheRandomData(@NonNull MachineModel e, int i)
    {
        e.setId(i);

        e.setDate(getDateOfMessage(i));
        e.setEvent(getRandomError());
        e.setAlarm(getRandomError());

        e.setVoltageA(random.nextInt(100));
        e.setVoltageB(random.nextInt(100));
        e.setVoltageC(random.nextInt(100));

        e.setAmperagePhaseAM1(random.nextInt(50));
        e.setAmperagePhaseBM1(random.nextInt(50));
        e.setAmperagePhaseCM1(random.nextInt(50));

        e.setAmperagePhaseAM2(random.nextInt(100));
        e.setAmperagePhaseBM2(random.nextInt(100));
        e.setAmperagePhaseCM2(random.nextInt(100));

        e.setAmperagePhaseAM3(random.nextInt(100));
        e.setAmperagePhaseBM3(random.nextInt(100));
        e.setAmperagePhaseCM3(random.nextInt(100));

        e.setVoltage24AC(random.nextInt(100));
        e.setVoltage24DC(random.nextInt(100));
        e.setVoltage220AC(random.nextInt(100));
        e.setVoltage42AC(random.nextInt(100));

        e.setVoltage127VA(random.nextInt(100));
        e.setVoltage127VB(random.nextInt(100));
        e.setVoltage127VC(random.nextInt(100));

        e.setSpeed(random.nextInt(250));
    }

    public String getRandomError()
    {
        int number = random.nextInt(Errors.length - 1);
        return Errors[number];
    }

    public String getDateOfMessage(int i)
    {
        SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss.SSS");
        Date date = new Date();

        return formatter.format(date);
    }
}
