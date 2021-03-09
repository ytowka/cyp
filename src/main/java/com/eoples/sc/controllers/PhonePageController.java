package com.eoples.sc.controllers;

import com.eoples.sc.models.Phone;
import com.eoples.sc.repo.PhoneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.List;

@Controller
public class PhonePageController {
    @Autowired
    PhoneRepository phoneRepository;

    @GetMapping("/select/{id}")
    public String getPhonePage(@PathVariable("id") long id,@RequestParam(value = "pff",defaultValue = "0") int pf,@RequestParam(value = "ptt",defaultValue = "1000000") int pt, Model model){
        Phone ph = phoneRepository.getPhoneById(id);

        List<Phone> phones = phoneRepository.getPhonesByPrice(pf,pt);
        if (ph == null){
            return "phone-error-page";
        }else{
            float avgCam = 0;
            float avgScreen = 0;
            float avgBattery = 0;
            float avgPower = 0;

            for (Phone i : phones){
                avgCam += i.getPhotoScore();
                avgBattery += i.getBatterySize();
                avgScreen += i.screenScore();
                avgPower += i.powerScore();
            }
            avgCam /= phones.size();
            avgBattery /= phones.size();
            avgPower /= phones.size();
            avgScreen /= phones.size();

            float camDiff = 100/(avgCam / ph.getPhotoScore()) - 100;
            float batDiff = 100/(avgBattery / ph.getBatterySize()) - 100;
            float powDiff = 100/(avgPower / ph.powerScore()) - 100;
            float scrDiff = 100/(avgScreen / ph.screenScore()) -100;

            int camPer = Math.round(camDiff);
            int batPer = Math.round(batDiff);
            int powPer = Math.round(powDiff);
            int scrPer = Math.round(scrDiff);

            String camSet = "камера "+getTemplate(camPer);
            String batSet = "время работы примерно " + ph.lifeHours() + " часов от полной зарядки";
            String powSet = "мощность " + getTemplate(powPer);
            String scrSet = "качество экрана " + getTemplate(scrPer);
            String rom = "количество памяти: "+ph.getRomSize()+"gb ("+ph.getRomMark()+") ";

            model.addAttribute("cam", camSet);
            model.addAttribute("bat", batSet);
            model.addAttribute("pow", powSet);
            model.addAttribute("scr", scrSet);
            model.addAttribute("rom", rom);

            model.addAttribute("phone",ph);
            return "phone-page";
        }
    }
    private String getTemplate(int percent){
        String template;
        if(percent > 4){
            template = "лучше среднего в ценовой категории на " + percent + "%";
        }else if (percent < -4){
            template = "хуже среднего в ценовой категории на " + percent*-1 + "%";
        }else{
            template = "средняя";
        }
        return template;
    }
}
