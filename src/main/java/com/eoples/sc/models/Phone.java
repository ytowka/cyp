package com.eoples.sc.models;

import static com.eoples.sc.models.Resources.*;

import javax.persistence.*;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Phone {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String brand;
    private String model;

    private int value;

    private int cpuScore;
    private int ramSize;

    private int gpuScore;

    private int RomSize;

    private int photoScore;
    private int videoScore;

    private boolean haveMiniJack;
    private boolean haveNfc;
    private boolean haveSdSlot;

    private int simSlotsCount;

    private int waterResistance; //0 - no water resistance, 1 - drops water resistance(ip53,ip67), 2 - full water resistance(ip68)

    private float screenSize;
    private int screenQuality;
    private int screenPPI;

    private int batterySize;
    private float batteryRate = 1;

    private String color;
    private String materials;
    private String releaseDate;

    private String link;

    public String photo(){
        return id+img;
    }

    private String additionalTags;

    private float priceCoef(){
        if(value <= 10000) return 0.5f;
        if(value <= 20000) return 0.7f;
        if(value <= 30000) return 0.8f;
        if(value <= 40000) return 0.9f;
        return 1;
    }

    private String calculatedTags(){
        String tags = "";
        if(cpuScore >= cpuGaming*priceCoef() && gpuScore >= gpuGaming*priceCoef()) tags += ("gaming")+sepstr();
        if(screenSize >= 6) tags+= ("bigScreen")+sepstr();
        if(screenSize <= 5.7) tags+=("smallScreen")+sepstr();
        if(photoScore >= goodCamera*priceCoef()) tags+=("goodCamera") + sepstr();
        if(batterySize >= 3800) tags+= ("goodBattery") + sepstr();
        if(cpuScore >= cpuFast * priceCoef() && ramSize >= fastRam * priceCoef()) tags+=("fast")+sepstr();
        if(screenPPI >= 320) tags+= ("goodScreen")+sepstr();
        if(RomSize >= 128*priceCoef()*0.7f) tags+= ("bigRom")+sepstr();
        if(simSlotsCount>=2)tags+=("2sim")+sepstr();
        if(haveNfc) tags+=("nfc")+sepstr();
        if(waterResistance>=2) tags+=("waterres")+sepstr();

        return tags;
    }

    @Override
    public String toString() {
        return brand+" "+model;
    }

    public Set<String> getTags(){
        String calcTags = calculatedTags();
        String addTags = additionalTags;

        if(calcTags.endsWith(Resources.sepstr())){
            calcTags = calcTags.substring(0,calcTags.length()-Resources.sepstr().length());
        }
        if(addTags.endsWith(Resources.sepstr())){
            addTags = addTags.substring(0, addTags.length() - Resources.sepstr().length());
        }

        String[] calcTagsArray = calculatedTags().split(Resources.sepstr());
        String[] addTagsArray = additionalTags.split(Resources.sepstr());

        Set<String> tags = new HashSet<>();
        for (String item : calcTagsArray) {
            tags.add(ruAutoTagsMap.get(item));
        }
        for (String s : addTagsArray) {
            tags.add(ruTagsMap.get(s));
        }
        tags.add(brand);
        tags.add(colorMap.get(color));
        return tags;
    }

    public Set<String> getTagIds(){
        String calcTags = calculatedTags();
        String addTags = additionalTags;

        if(calcTags.endsWith(Resources.sepstr())){
            calcTags = calcTags.substring(0,calcTags.length()-Resources.sepstr().length());
        }
        if(addTags.endsWith(Resources.sepstr())){
            addTags = addTags.substring(0, addTags.length() - Resources.sepstr().length());
        }

        String[] calcTagsArray = calculatedTags().split(Resources.sepstr());
        String[] addTagsArray = additionalTags.split(Resources.sepstr());

        Set<String> tags = new HashSet<>();
        Collections.addAll(tags, calcTagsArray);
        Collections.addAll(tags, addTagsArray);
        tags.add(brand);
        tags.add(color);
        return tags;
    }
    public int powerScore(){
        return gpuScore+cpuScore+ramSize*10000;
    }
    public String lifeHours(){
        return String.format("%.1f",((float)batterySize/387.5f));
    }

    public int screenScore(){
        return screenQuality*screenPPI;
    }
    public String getRomMark(){
        int romCoef = 1;
        if(value >= 8000){
            romCoef = 2;
        }
        int rom = RomSize * romCoef;
        String mark = Resources.romMarks.get('P');

        if(rom <= 32){
           mark = Resources.romMarks.get('p');
        }
        if(rom >= 32){
            mark = Resources.romMarks.get('l');
        }
        if(rom >= 64){
            mark = Resources.romMarks.get('n');
        }
        if(rom >= 128){
            mark = Resources.romMarks.get('i');
        }
        if(rom >= 256){
            mark = Resources.romMarks.get('m');
        }
        return mark;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public int getCpuScore() {
        return cpuScore;
    }

    public void setCpuScore(int cpuScore) {
        this.cpuScore = cpuScore;
    }

    public int getRamSize() {
        return ramSize;
    }

    public void setRamSize(int ramSize) {
        this.ramSize = ramSize;
    }

    public int getGpuScore() {
        return gpuScore;
    }

    public void setGpuScore(int gpuScore) {
        this.gpuScore = gpuScore;
    }

    public int getRomSize() {
        return RomSize;
    }

    public void setRomSize(int romSize) {
        RomSize = romSize;
    }

    public int getPhotoScore() {
        return photoScore;
    }

    public void setPhotoScore(int photoScore) {
        this.photoScore = photoScore;
    }

    public int getVideoScore() {
        return videoScore;
    }

    public void setVideoScore(int videoScore) {
        this.videoScore = videoScore;
    }

    public boolean isHaveMiniJack() {
        return haveMiniJack;
    }

    public void setHaveMiniJack(boolean haveMiniJack) {
        this.haveMiniJack = haveMiniJack;
    }

    public boolean isHaveNfc() {
        return haveNfc;
    }

    public void setHaveNfc(boolean haveNfc) {
        this.haveNfc = haveNfc;
    }

    public int getSimSlotsCount() {
        return simSlotsCount;
    }

    public void setSimSlotsCount(int simSlotsCount) {
        this.simSlotsCount = simSlotsCount;
    }

    public int getWaterResistance() {
        return waterResistance;
    }

    public void setWaterResistance(int waterResistance) {
        this.waterResistance = waterResistance;
    }

    public float getScreenSize() {
        return screenSize;
    }

    public void setScreenSize(float screenSize) {
        this.screenSize = screenSize;
    }

    public int getScreenQuality() {
        return screenQuality;
    }

    public void setScreenQuality(int screenQuality) {
        this.screenQuality = screenQuality;
    }

    public int getScreenPPI() {
        return screenPPI;
    }

    public void setScreenPPI(int screenPPI) {
        this.screenPPI = screenPPI;
    }

    public int getBatterySize() {
        return batterySize;
    }

    public void setBatterySize(int batterySize) {
        this.batterySize = batterySize;
    }

    public float getBatteryRate() {
        return batteryRate;
    }

    public void setBatteryRate(float batteryRate) {
        this.batteryRate = batteryRate;
    }

    public boolean isHaveSdSlot() {
        return haveSdSlot;
    }

    public void setHaveSdSlot(boolean haveSdSlot) {
        this.haveSdSlot = haveSdSlot;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getMaterials() {
        return materials;
    }

    public void setMaterials(String materials) {
        this.materials = materials;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }


    public void setAdditionalTags(String additionalTags) {
        this.additionalTags = additionalTags;
    }

    public String getTTX(){
        return "";
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }


    public long getId() {
        return id;
    }
}
