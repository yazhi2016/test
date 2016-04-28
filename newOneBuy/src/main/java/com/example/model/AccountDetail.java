package com.example.model;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AccountDetail {

    @Expose
    private List<Account> account = new ArrayList<Account>();
    @SerializedName("account_in")
    @Expose
    private Integer accountIn;
    @SerializedName("account_out")
    @Expose
    private Integer accountOut;

    /**
     * 
     * @return
     *     The account
     */
    public List<Account> getAccount() {
        return account;
    }

    /**
     * 
     * @param account
     *     The account
     */
    public void setAccount(List<Account> account) {
        this.account = account;
    }

    /**
     * 
     * @return
     *     The accountIn
     */
    public Integer getAccountIn() {
        return accountIn;
    }

    /**
     * 
     * @param accountIn
     *     The account_in
     */
    public void setAccountIn(Integer accountIn) {
        this.accountIn = accountIn;
    }

    /**
     * 
     * @return
     *     The accountOut
     */
    public Integer getAccountOut() {
        return accountOut;
    }

    /**
     * 
     * @param accountOut
     *     The account_out
     */
    public void setAccountOut(Integer accountOut) {
        this.accountOut = accountOut;
    }

}
