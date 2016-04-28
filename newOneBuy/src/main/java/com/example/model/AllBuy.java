package com.example.model;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;

public class AllBuy {

    @Expose
    private List<Cord> cords = new ArrayList<Cord>();

    /**
     * 
     * @return
     *     The cords
     */
    public List<Cord> getCords() {
        return cords;
    }

    /**
     * 
     * @param cords
     *     The cords
     */
    public void setCords(List<Cord> cords) {
        this.cords = cords;
    }

}
