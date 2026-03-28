package br.com.protocolmoney.response;

import br.com.protocolmoney.model.InvestorPosition;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "positionResponse")
@XmlAccessorType(XmlAccessType.FIELD)
public class PositionResponse {

    private String message;
    private boolean success;
    private InvestorPosition data;

    public PositionResponse() {}

    public PositionResponse(String message, boolean success, InvestorPosition data) {
        this.message = message;
        this.success = success;
        this.data = data;
    }


    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public InvestorPosition getData() {
        return data;
    }

    public void setData(InvestorPosition data) {
        this.data = data;
    }
}
