package com.example.nivaserviceandroid.model;

import androidx.annotation.Nullable;

import java.io.Serializable;
import java.util.Date;

public class HarvesterModel implements Serializable {
    private int id;
    private String date;
    private int voltage24DC;
    private int voltage42AC;
    private int voltage110AC;
    private int voltage127AC;

    private int voltage1140ACFazeA;
    private int voltage1140ACFazeB;
    private int voltage1140ACFazeC;

    private int oilLevel;
    private int oilTemperature;

    private float leftSpeed;
    private float rightSpeed;

    private int verticalAngle;
    private int horizontalAngle;

    private int dustExtractorAmperage;
    private int conveyorAmperage;
    private int bermAmperage;
    private int secondBermAmperage;
    private int cuttingDiscsAmperage;
    private int pumpingStationAmperage;
    private int bunkerLoaderAmperage;
    private int apshAmperage;

    private int disconnectorEngineHours;
    private int dustExtractorEngineHours;
    private int conveyorEngineHours;
    private int bermEngineHours;
    private int secondBermEngineHours;
    private int cuttingDiscsEngineHours;
    private int pumpingStationEngineHours;
    private int bunkerLoaderEngineHours;



    @Nullable
    public String getDate() {
        return date;
    }

    public void setDate(@Nullable String date) {
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getVoltage24DC() {
        return voltage24DC;
    }

    public void setVoltage24DC(int voltage24DC) {
        this.voltage24DC = voltage24DC;
    }

    public int getVoltage42AC() {
        return voltage42AC;
    }

    public void setVoltage42AC(int voltage42AC) {
        this.voltage42AC = voltage42AC;
    }

    public int getVoltage110AC() {
        return voltage110AC;
    }

    public void setVoltage110AC(int voltage110AC) {
        this.voltage110AC = voltage110AC;
    }

    public int getVoltage127AC() {
        return voltage127AC;
    }

    public void setVoltage127AC(int voltage127AC) {
        this.voltage127AC = voltage127AC;
    }

    public int getVoltage1140ACFazeA() {
        return voltage1140ACFazeA;
    }

    public void setVoltage1140ACFazeA(int voltage1140ACFazeA) {
        this.voltage1140ACFazeA = voltage1140ACFazeA;
    }

    public int getVoltage1140ACFazeB() {
        return voltage1140ACFazeB;
    }

    public void setVoltage1140ACFazeB(int voltage1140ACFazeB) {
        this.voltage1140ACFazeB = voltage1140ACFazeB;
    }

    public int getVoltage1140ACFazeC() {
        return voltage1140ACFazeC;
    }

    public void setVoltage1140ACFazeC(int voltage1140ACFazeC) {
        this.voltage1140ACFazeC = voltage1140ACFazeC;
    }

    public int getOilLevel() {
        return oilLevel;
    }

    public void setOilLevel(int oilLevel) {
        this.oilLevel = oilLevel;
    }

    public int getOilTemperature() {
        return oilTemperature;
    }

    public void setOilTemperature(int oilTemperature) {
        this.oilTemperature = oilTemperature;
    }

    public float getLeftSpeed() {
        return leftSpeed;
    }

    public void setLeftSpeed(float leftSpeed) {
        this.leftSpeed = leftSpeed;
    }

    public float getRightSpeed() {
        return rightSpeed;
    }

    public void setRightSpeed(float rightSpeed) {
        this.rightSpeed = rightSpeed;
    }

    public int getVerticalAngle() {
        return verticalAngle;
    }

    public void setVerticalAngle(int verticalAngle) {
        this.verticalAngle = verticalAngle;
    }

    public int getHorizontalAngle() {
        return horizontalAngle;
    }

    public void setHorizontalAngle(int horizontalAngle) {
        this.horizontalAngle = horizontalAngle;
    }

    public int getDustExtractorAmperage() {
        return dustExtractorAmperage;
    }

    public void setDustExtractorAmperage(int dustExtractorAmperage) {
        this.dustExtractorAmperage = dustExtractorAmperage;
    }

    public int getConveyorAmperage() {
        return conveyorAmperage;
    }

    public void setConveyorAmperage(int conveyorAmperage) {
        this.conveyorAmperage = conveyorAmperage;
    }

    public int getBermAmperage() {
        return bermAmperage;
    }

    public void setBermAmperage(int bermAmperage) {
        this.bermAmperage = bermAmperage;
    }

    public int getSecondBermAmperage() {
        return secondBermAmperage;
    }

    public void setSecondBermAmperage(int secondBermAmperage) {
        this.secondBermAmperage = secondBermAmperage;
    }

    public int getCuttingDiscsAmperage() {
        return cuttingDiscsAmperage;
    }

    public void setCuttingDiscsAmperage(int cuttingDiscsAmperage) {
        this.cuttingDiscsAmperage = cuttingDiscsAmperage;
    }

    public int getPumpingStationAmperage() {
        return pumpingStationAmperage;
    }

    public void setPumpingStationAmperage(int pumpingStationAmperage) {
        this.pumpingStationAmperage = pumpingStationAmperage;
    }

    public int getBunkerLoaderAmperage() {
        return bunkerLoaderAmperage;
    }


    public void setBunkerLoaderAmperage(int bunkerLoaderAmperage) {
        this.bunkerLoaderAmperage = bunkerLoaderAmperage;
    }

    public int getApshAmperage() {
        return apshAmperage;
    }

    public void setApshAmperage(int apshAmperage) {
        this.apshAmperage = apshAmperage;
    }

    public int getDisconnectorEngineHours() {
        return disconnectorEngineHours;
    }

    public void setDisconnectorEngineHours(int disconnectorEngineHours) {
        this.disconnectorEngineHours = disconnectorEngineHours;
    }

    public int getDustExtractorEngineHours() {
        return dustExtractorEngineHours;
    }

    public void setDustExtractorEngineHours(int dustExtractorEngineHours) {
        this.dustExtractorEngineHours = dustExtractorEngineHours;
    }

    public int getConveyorEngineHours() {
        return conveyorEngineHours;
    }

    public void setConveyorEngineHours(int conveyorEngineHours) {
        this.conveyorEngineHours = conveyorEngineHours;
    }

    public int getBermEngineHours() {
        return bermEngineHours;
    }

    public void setBermEngineHours(int bermEngineHours) {
        this.bermEngineHours = bermEngineHours;
    }

    public int getSecondBermEngineHours() {
        return secondBermEngineHours;
    }

    public void setSecondBermEngineHours(int secondBermEngineHours) {
        this.secondBermEngineHours = secondBermEngineHours;
    }

    public int getCuttingDiscsEngineHours() {
        return cuttingDiscsEngineHours;
    }

    public void setCuttingDiscsEngineHours(int cuttingDiscsEngineHours) {
        this.cuttingDiscsEngineHours = cuttingDiscsEngineHours;
    }

    public int getPumpingStationEngineHours() {
        return pumpingStationEngineHours;
    }

    public void setPumpingStationEngineHours(int pumpingStationEngineHours) {
        this.pumpingStationEngineHours = pumpingStationEngineHours;
    }

    public int getBunkerLoaderEngineHours() {
        return bunkerLoaderEngineHours;
    }

    public void setBunkerLoaderEngineHours(int bunkerLoaderEngineHours) {
        this.bunkerLoaderEngineHours = bunkerLoaderEngineHours;
    }
}
