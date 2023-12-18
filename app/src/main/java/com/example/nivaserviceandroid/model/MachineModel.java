package com.example.nivaserviceandroid.model;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.io.Serializable;

public class MachineModel implements Serializable {
    @Nullable
    private String event, alarm, date;

    int id;
    int voltageA;
    int voltageB;
    int voltageC;
    int amperagePhaseAM1;
    int amperagePhaseBM1;
    int amperagePhaseCM1;
    int amperagePhaseAM2;
    int amperagePhaseBM2;
    int amperagePhaseCM2;
    int amperagePhaseAM3;
    int amperagePhaseBM3;
    int amperagePhaseCM3;
    int voltage127VA;
    int voltage127VB;
    int voltage127VC;
    int voltage24DC;
    int voltage24AC;
    int voltage42AC;
    int voltage220AC;
    int speed;

    @Nullable
    public String getAlarm() {
        return alarm;
    }

    public void setAlarm(@Nullable String alarm) {
        this.alarm = alarm;
    }

    public int getVoltage24AC() {
        return voltage24AC;
    }

    public void setVoltage24AC(int voltage24AC) {
        this.voltage24AC = voltage24AC;
    }

    public int getVoltage42AC() {
        return voltage42AC;
    }

    public void setVoltage42AC(int voltage42AC) {
        this.voltage42AC = voltage42AC;
    }

    public int getVoltage220AC() {
        return voltage220AC;
    }

    public void setVoltage220AC(int voltage220AC) {
        this.voltage220AC = voltage220AC;
    }

    public int getVoltage24DC() {
        return voltage24DC;
    }

    public void setVoltage24DC(int voltage24DC) {
        this.voltage24DC = voltage24DC;
    }

    @NonNull
    public int getId() { return id; }
    public void setId(@NonNull int id) { this.id = id; }

    @Nullable
    public String getDate() { return date; }
    public void setDate(@Nullable String date) { this.date = date; }

    @Nullable
    public String getEvent() { return event; }
    public void setEvent(@Nullable String event) { this.event = event; }

    @Nullable
    public int getVoltageA() { return voltageA; }
    public void setVoltageA(@Nullable int voltageA) {
        this.voltageA = voltageA; }

    public int getVoltageB() { return voltageB; }
    public void setVoltageB(@Nullable int voltageB) {
        this.voltageB = voltageB; }

    public int getVoltageC() { return voltageC; }
    public void setVoltageC(@Nullable int voltageC) {
        this.voltageC = voltageC; }

    public int getAmperagePhaseAM1() { return amperagePhaseAM1; }
    public void setAmperagePhaseAM1(@Nullable int amperagePhaseAM1) {
        this.amperagePhaseAM1 = amperagePhaseAM1; }

    public int getAmperagePhaseBM1() { return amperagePhaseBM1; }
    public void setAmperagePhaseBM1(@Nullable int amperagePhaseBM1) {
        this.amperagePhaseBM1 = amperagePhaseBM1; }

    public int getAmperagePhaseCM1() { return amperagePhaseCM1; }
    public void setAmperagePhaseCM1(@Nullable int amperagePhaseCM1) {
        this.amperagePhaseCM1 = amperagePhaseCM1; }

    public int getAmperagePhaseAM2() { return amperagePhaseAM2; }
    public void setAmperagePhaseAM2(@Nullable int amperagePhaseAM2) {
        this.amperagePhaseAM2 = amperagePhaseAM2; }

    public int getAmperagePhaseBM2() { return amperagePhaseBM2; }
    public void setAmperagePhaseBM2(@Nullable int amperagePhaseBM2) {
        this.amperagePhaseBM2 = amperagePhaseBM2; }

    public int getAmperagePhaseCM2() { return amperagePhaseCM2; }
    public void setAmperagePhaseCM2(@Nullable int amperagePhaseCM2) {
        this.amperagePhaseCM2 = amperagePhaseCM2; }

    public int getAmperagePhaseAM3() { return amperagePhaseAM3; }
    public void setAmperagePhaseAM3(@Nullable int amperagePhaseAM3) {
        this.amperagePhaseAM3 = amperagePhaseAM3; }

    public int getAmperagePhaseBM3() { return amperagePhaseBM3; }
    public void setAmperagePhaseBM3(@Nullable int amperagePhaseBM3) {
        this.amperagePhaseBM3 = amperagePhaseBM3; }

    public int getAmperagePhaseCM3() { return amperagePhaseCM3; }
    public void setAmperagePhaseCM3(@Nullable int amperagePhaseCM3) {
        this.amperagePhaseCM3 = amperagePhaseCM3; }

    public int getVoltage127VA() { return voltage127VA; }
    public void setVoltage127VA(@Nullable int voltage127VA) {
        this.voltage127VA = voltage127VA; }

    public int getVoltage127VB() { return voltage127VB; }
    public void setVoltage127VB(@Nullable int voltage127VB) {
        this.voltage127VB = voltage127VB; }

    public int getVoltage127VC() { return voltage127VC; }
    public void setVoltage127VC(@Nullable int voltage127VC) {
        this.voltage127VC = voltage127VC; }

    public int getSpeed() { return speed; }
    public void setSpeed(@Nullable int speed) {
        this.speed = speed; }
}
