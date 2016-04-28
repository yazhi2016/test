package com.example.model;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;

public class Data2 {

    @Expose
    private List<All> all = new ArrayList<All>();
    @Expose
    private List<All> pre = new ArrayList<All>();
    @Expose
    private List<All> complete = new ArrayList<All>();

    /**
     * 
     * @return
     *     The all
     */
    public List<All> getAll() {
        return all;
    }

    /**
     * 
     * @param all
     *     The all
     */
    public void setAll(List<All> all) {
        this.all = all;
    }

    /**
     * 
     * @return
     *     The pre
     */
    public List<All> getPre() {
        return pre;
    }

    /**
     * 
     * @param pre
     *     The pre
     */
    public void setPre(List<All> pre) {
        this.pre = pre;
    }

    /**
     * 
     * @return
     *     The complete
     */
    public List<All> getComplete() {
        return complete;
    }

    /**
     * 
     * @param complete
     *     The complete
     */
    public void setComplete(List<All> complete) {
        this.complete = complete;
    }

}
