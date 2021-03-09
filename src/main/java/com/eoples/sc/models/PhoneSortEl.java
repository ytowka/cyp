package com.eoples.sc.models;

import javax.persistence.SecondaryTable;
import java.util.HashSet;
import java.util.Set;

public class PhoneSortEl {
    public Phone phone;
    public int tagMatches;
    public boolean allMatch = false;
    public Set<String> missTags;

    public PhoneSortEl(Phone phone, int tagMatchs) {
        missTags = new HashSet<>();
        this.phone = phone;
        this.tagMatches = tagMatchs;


    }


}
