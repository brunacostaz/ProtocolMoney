package br.com.protocolmoney.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@XmlAccessorType(XmlAccessType.FIELD)
public class AssetPosition {

    private String assetCode;
    private String assetDescription;
    private double balance;
    private String dueDate;

    public AssetPosition() {
    }

    public AssetPosition(String assetCode, String assetDescription, double balance, String dueDate) {
        this.assetCode = assetCode;
        this.assetDescription = assetDescription;
        this.balance = balance;
        this.dueDate = dueDate;
    }

    public String getAssetCode() {
        return assetCode;
    }

    public void setAssetCode(String assetCode) {
        this.assetCode = assetCode;
    }

    public String getAssetDescription() {
        return assetDescription;
    }

    public void setAssetDescription(String assetDescription) {
        this.assetDescription = assetDescription;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }
}
