package com.financialapp.model;

public class HomeBanking {

    String bankName;
    String homeBankingURL;

    public HomeBanking(String bankName, String homeBankingURL) {
        this.bankName = bankName;
        this.homeBankingURL = homeBankingURL;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getHomeBankingURL() {
        return homeBankingURL;
    }

    public void setHomeBankingURL(String homeBankingURL) {
        this.homeBankingURL = homeBankingURL;
    }

}
