package com.example.model;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;

public class Shaidan5 {

    @Expose
    private List<Shaidan6> shaidan = new ArrayList<Shaidan6>();
    @Expose
    private Integer sum;

    /**
     * 
     * @return
     *     The shaidan
     */
    public List<Shaidan6> getShaidan() {
        return shaidan;
    }

    /**
     * 
     * @param shaidan
     *     The shaidan
     */
    public void setShaidan(List<Shaidan6> shaidan) {
        this.shaidan = shaidan;
    }

    /**
     * 
     * @return
     *     The sum
     */
    public Integer getSum() {
        return sum;
    }

    /**
     * 
     * @param sum
     *     The sum
     */
    public void setSum(Integer sum) {
        this.sum = sum;
    }

}
