package com.example.model;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;

public class ProductShare {

    @Expose
    private List<Shaidan> shaidan = new ArrayList<Shaidan>();
    @Expose
    private Integer sum;

    /**
     * 
     * @return
     *     The shaidan
     */
    public List<Shaidan> getShaidan() {
        return shaidan;
    }

    /**
     * 
     * @param shaidan
     *     The shaidan
     */
    public void setShaidan(List<Shaidan> shaidan) {
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
