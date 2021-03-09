package com.eoples.sc.models;

import static java.util.Map.entry;

import java.util.*;

public class Resources {
    public static final Map<Character, String> romMarks= Map.ofEntries(
            entry('p',"очень мало"),
            entry('l', "мало"),
            entry('n',"достаточно"),
            entry('i', "идеально"),
            entry('m',"с избытком")
    );
    public static Set<String> ruAllTagsSet(){
        Set<String> tags = new HashSet<>();
        tags.addAll(ruTagsMap.values());
        tags.addAll(ruAutoTagsMap.values());
        return tags;
    }
    public static Set<String> brandsSet(){
        return Set.of("Samsung","HONOR","XIAOMI","Apple","realme");
    }
    public static Set<String> colorsSet(){
        return Set.copyOf(colorMap.values());
    }
    public static final Map<String,String> colorMap = Map.ofEntries(
            entry("red","красный"),
            entry("blue", "синий"),
            entry("white","белый"),
            entry("green","зеленый"),
            entry("black","черный"),
            entry("yellow","желтый"),
            entry("gray","серый")
    );
    public static final Map<String, String> ruTagsMap = Map.ofEntries(
            entry("durable","прочный"),
            entry("premium","премиум"),
            entry("modern","современный"),
            entry("noCut","без выреза в экране")
            );
    public static final Map<String, String> ruAutoTagsMap = Map.ofEntries(
            entry("gaming","игровой"),
            entry("bigScreen", "большой экран"),
            entry("smallScreen","компактный экран"),
            entry("goodCamera", "хорошая камера"),
            entry("goodBattery", "большая баттарея"),
            entry("fast","быстрый"),
            entry("goodScreen","хороший экран"),
            entry("bigRom","много памяти"),
            entry("2sim","2 сим карты"),
            entry("nfc","бесконтактная оплата"),
            entry("waterres","защита от воды")
    );
    public static final Map<String, String> ru = Map.ofEntries(
            entry("noLimit","без ораничения")
    );
    public static String getRuLabel(String id){
        if(colorMap.containsKey(id)){
            return colorMap.get(id);
        }
        if(ruTagsMap.containsKey(id)){
            return ruTagsMap.get(id);
        }
        if(ruAutoTagsMap.containsKey(id)){
            return ruAutoTagsMap.get(id);
        }
        if(brandsSet().contains(id)){
            return id;
        }
        return "error";
    }


    public static final String img = ".png";
    public static String sepstr(){
        return "--";
    }

    public static final float under30kPriceMultiplier = 0.8f;
    public static final float under15kPriceMultiplier = 0.6f;
    public static final int cpuGaming = 100000;
    public static final int gpuGaming = 70000;
    public static final int cpuFast = 70000;
    public static final int goodCamera = 70;
    public static final int fastRam = 6;
}
