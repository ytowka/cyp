package com.eoples.sc.models;

import java.util.*;

public class TagDto {
    public ArrayList<TagView> tagsList;

    public TagDto(){
        tagsList = new ArrayList<>();
    }
    public void addAll(Set<String> set){

        for(String i: set){
            tagsList.add(new TagView(false,i));
        }
    }

    public List<TagView> getTagsList() {
        return tagsList;
    }

    public void setTagsList(ArrayList<TagView> tagsList) {
        this.tagsList = tagsList;
    }
}
