package com.example.model;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;

public class Address {

    @Expose
    private List<Address_> address = new ArrayList<Address_>();

    /**
     * 
     * @return
     *     The address
     */
    public List<Address_> getAddress() {
        return address;
    }

    /**
     * 
     * @param address
     *     The address
     */
    public void setAddress(List<Address_> address) {
        this.address = address;
    }

}
