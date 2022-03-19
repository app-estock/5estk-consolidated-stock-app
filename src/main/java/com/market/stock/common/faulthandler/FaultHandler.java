package com.market.stock.common.faulthandler;

public class FaultHandler {

    private String faultCode;
    private String faultMessage;
    private String responseFaultMessage;

    public FaultHandler(String faultCode, String faultMessage,String responseFaultMessage) {
        this.faultCode = faultCode;
        this.faultMessage = faultMessage;
        this.responseFaultMessage=responseFaultMessage;
    }

    public String getFaultCode() {
        return faultCode;
    }

    public void setFaultCode(String faultCode) {
        this.faultCode = faultCode;
    }

    public String getFaultMessage() {
        return faultMessage;
    }

    public void setFaultMessage(String faultMessage) {
        this.faultMessage = faultMessage;
    }

    public String getResponseFaultMessage() {
        return responseFaultMessage;
    }

    public void setResponseFaultMessage(String responseFaultMessage) {
        this.responseFaultMessage = responseFaultMessage;
    }

    @Override
    public String toString() {
        return "ERROR [" + faultCode + ":" + faultMessage+":" + responseFaultMessage + "]" ;

    }
}
