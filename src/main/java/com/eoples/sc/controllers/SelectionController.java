package com.eoples.sc.controllers;

import com.eoples.sc.models.*;
import com.eoples.sc.repo.PhoneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Controller
public class SelectionController {

    @Autowired
    PhoneRepository phoneRepository;

    @GetMapping("search")
    public String search(@RequestParam("text") String searchText, Model model){
        List<Phone> phones = new ArrayList<>();
        Iterable<Phone> phoneIterable = phoneRepository.findAll();
        for(Phone i : phoneIterable){
            phones.add(i);
        }
        PhoneSortEl[] sortingPhones = new PhoneSortEl[phones.size()];

        int k = 0;
        char[] searchTextChars = searchText.toCharArray();
        for (Phone i : phones) {
            char[] phoneName = (i.getBrand()+i.getModel()).toCharArray();
            int currentPhoneTagsMatch = 0;

            int j = 0;
            for(char ch1 : searchTextChars){
                for(char ch2 : phoneName){
                    if( Character.toUpperCase(ch1) == ch2 || Character.toLowerCase(ch1) == ch2) currentPhoneTagsMatch++;
                }
                j++;
                if(j > 30) break;
            }
            System.out.println(i.toString() +" "+currentPhoneTagsMatch);

            PhoneSortEl el = new PhoneSortEl(i, currentPhoneTagsMatch);
            sortingPhones[k] = (el);
            k++;
        }

        boolean sorted = false;
        PhoneSortEl temp;
        while (!sorted) {
            sorted = true;
            for (int i = 0; i < sortingPhones.length - 1; i++) {
                if (sortingPhones[i].tagMatches < sortingPhones[i + 1].tagMatches) {
                    temp = sortingPhones[i];
                    sortingPhones[i] = sortingPhones[i + 1];
                    sortingPhones[i + 1] = temp;
                    sorted = false;
                }
            }
        }

        model.addAttribute("text", "Результаты поиска по совпадению: "+searchText);
        model.addAttribute("intPf",sortingPhones[0].phone.getValue() - 5000);
        model.addAttribute("intPt",sortingPhones[0].phone.getValue() + 5000);
        model.addAttribute("phones", sortingPhones);

        return "phone_search";
    }

    @PostMapping("/select")
    public String select(@RequestParam(defaultValue = "0") int pf, @RequestParam(defaultValue = "1000000") int pt, @ModelAttribute TagDto form, Model model) {
        ArrayList<String> tagsArray = new ArrayList<>();

        for (TagView i : form.getTagsList()){
            if(i.checked){
                System.out.println(i.getId());
                tagsArray.add(i.getLabel());
            }
        }

        boolean priceFromGiven = pf != 0;
        boolean priceToGiven = pt != 1000000;
        if (priceFromGiven) {
            model.addAttribute("pf", pf + "p");
        } else {
            model.addAttribute("pf", Resources.ru.get("noLimit"));
        }
        if (priceToGiven) {
            model.addAttribute("pt", pt + "p");
        } else {
            model.addAttribute("pt", Resources.ru.get("noLimit"));
        }
        model.addAttribute("intPf",pf);
        model.addAttribute("intPt",pt);
        //String[] tagsArray = tags.split(Resources.sepstr());

        model.addAttribute("tags", tagsArray);

        List<Phone> phones = phoneRepository.getPhonesByPrice(pf, pt);
        PhoneSortEl[] sortingPhones = new PhoneSortEl[phones.size()];

        int k = 0;
        for (Phone i : phones) {
            Set<String> missTags = new HashSet<>();
            boolean allMatch = true;
            int currentPhoneTagsMatch = 0;
            for (String tag : tagsArray) {
                if (i.getTags().contains(tag)) {
                    currentPhoneTagsMatch++;
                } else {
                    allMatch = false;
                    missTags.add(tag);
                }
            }
            PhoneSortEl el = new PhoneSortEl(i, currentPhoneTagsMatch);
            el.missTags = missTags;
            el.allMatch = allMatch;
            sortingPhones[k] = (el);
            k++;
        }

        boolean sorted = false;
        PhoneSortEl temp;
        while (!sorted) {
            sorted = true;
            for (int i = 0; i < sortingPhones.length - 1; i++) {
                if (sortingPhones[i].tagMatches < sortingPhones[i + 1].tagMatches) {
                    temp = sortingPhones[i];
                    sortingPhones[i] = sortingPhones[i + 1];
                    sortingPhones[i + 1] = temp;
                    sorted = false;
                }
            }
        }

        model.addAttribute("phones", sortingPhones);

        return "phone_choose";
    }
}
