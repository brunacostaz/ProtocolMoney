package br.com.protocolmoney.model;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement(name = "investorPosition")
@XmlAccessorType(XmlAccessType.FIELD)
public class InvestorPosition {

    private String personId;
    private double totalBalance;

    @XmlElementWrapper(name = "assets")
    @XmlElement(name = "asset")
    private List<AssetPosition> investorAssets = new ArrayList<>();

    public InvestorPosition() {}

    public InvestorPosition(String personId) {
        this.personId = personId;
    }

    // Atualiza a posição consolidada para retornar ao cliente
    public void updateBalance() {
        double soma = 0.0;
        for (AssetPosition a : investorAssets) {
            soma = soma + a.getBalance();
        }
        this.totalBalance = soma;
    }

    public String getPersonId() {
        return personId;
    }

    public void setPersonId(String personId) {
        this.personId = personId;
    }

    public double getTotalBalance() {
        return totalBalance;
    }

    public void setTotalBalance(double totalBalance) {
        this.totalBalance = totalBalance;
    }

    public List<AssetPosition> getInvestorAssets() {
        return investorAssets;
    }

    public void setInvestorAssets(List<AssetPosition> investorAssets) {
        this.investorAssets = investorAssets;
    }
}
