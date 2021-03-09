package com.eoples.sc.controllers;

import com.eoples.sc.models.Phone;
import com.eoples.sc.models.Resources;
import com.eoples.sc.models.TagDto;
import com.eoples.sc.models.TagView;
import com.eoples.sc.repo.PhoneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@Controller
public class MainController {
    @Autowired
    PhoneRepository repository;

    @GetMapping("/")
    public String mainPage(Model model){

        TagDto tagsForm = new TagDto();
        tagsForm.addAll(Resources.ruAutoTagsMap.keySet());
        tagsForm.addAll(Resources.ruTagsMap.keySet());
        tagsForm.addAll(Resources.colorMap.keySet());
        tagsForm.addAll(Resources.brandsSet());

        model.addAttribute("form",tagsForm);

        for (TagView i : tagsForm.getTagsList()){
            System.out.println(i.id +" "+i.checked);
        }
        return "main_page";
    }

    @GetMapping("/db")
    public String getdbpage(Model model){
        Iterable<Phone> phones = repository.findAll();
        model.addAttribute("phones",phones);
        return "db";
    }

    @GetMapping("/addPhone")
    public String addPhonePage(Model model){
        String[] tags = new String[Resources.ruTagsMap.size()];
        int i = 0;
        for(Map.Entry<String, String> entry : Resources.ruTagsMap.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();

            tags[i] = value +" : "+key;
            i++;
        }
        model.addAttribute("form",tags);
        return "create-phone";
    }

    @PostMapping("/addPhone")
    public String addPhone(
            @RequestParam("brand") String brand,
            @RequestParam("model") String modelName,
            @RequestParam("value") int value,
            @RequestParam("tags") String tags,
            @RequestParam("cpu") int cpu,
            @RequestParam("gpu") int gpu,
            @RequestParam("ram") int ram,
            @RequestParam("rom") int rom,
            @RequestParam("photo") int photo,
            @RequestParam("video") int video,
            @RequestParam(value = "miniJack", defaultValue = "false") boolean miniJack,
            @RequestParam(value = "nfc", defaultValue = "false") boolean nfc,
            @RequestParam(value = "sd",defaultValue = "false") boolean sd,
            @RequestParam("simSlots") int simSlots,
            @RequestParam("waterres") int waterres,
            @RequestParam("screenSize") float screenSize,
            @RequestParam("screenQuality") int screenQuality,
            @RequestParam("screenPPI") int screenPPI,
            @RequestParam("batterySize") int batterySize,
            @RequestParam("color") String color,
            @RequestParam("materials") String materials,
            @RequestParam("releaseDate") String date,
            @RequestParam("link") String link,
            Model model
    ){
        Phone p = new Phone();

        String addTags = tags.replace(" ",Resources.sepstr());
        p.setValue(value);
        p.setAdditionalTags(addTags);
        p.setBrand(brand);
        p.setModel(modelName);
        p.setColor(color);
        p.setMaterials(materials);
        p.setBatterySize(batterySize);
        p.setCpuScore(cpu);
        p.setGpuScore(gpu);
        p.setHaveMiniJack(miniJack);
        p.setHaveNfc(nfc);
        p.setRamSize(ram);
        p.setRomSize(rom);
        p.setPhotoScore(photo);
        p.setVideoScore(video);
        p.setHaveSdSlot(sd);
        p.setSimSlotsCount(simSlots);
        p.setWaterResistance(waterres);
        p.setScreenPPI(screenPPI);
        p.setScreenQuality(screenQuality);
        p.setScreenSize(screenSize);
        p.setReleaseDate(date);
        p.setLink(link);

        repository.save(p);

        System.out.println(value);

        model.addAttribute("tags",Resources.ruTagsMap);

        return "create-phone";
    }
    @PostMapping("/deletePhone")
    public String deletePhone(Model model){
        return "";
    }
}
