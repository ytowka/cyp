package com.eoples.sc.models;

import javax.swing.text.html.HTML;

public class TagView {

    public boolean checked;

    public String getLabel() {
        return Resources.getRuLabel(id);
    }

    public String id;

    public TagView(boolean checked, String id) {
        this.checked = checked;
        this.id = id;
    }
    public TagView(){
        checked = false;
        id = "not defined";
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public boolean isChecked() {
        return checked;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
