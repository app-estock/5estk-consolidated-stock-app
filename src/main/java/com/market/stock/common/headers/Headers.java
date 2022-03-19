package com.market.stock.common.headers;

public class Headers {

    String  estk_transactionID;
    String estk_sessionID;
    String estk_messageID;
    String estk_creationtimestamp;
    public Headers()
    {

    }
    public Headers(String estk_transactionID, String estk_sessionID, String estk_messageID, String estk_creationtimestamp) {
        this.estk_transactionID = estk_transactionID;
        this.estk_sessionID = estk_sessionID;
        this.estk_messageID = estk_messageID;
        this.estk_creationtimestamp = estk_creationtimestamp;
    }

    public String getEstk_transactionID() {
        return estk_transactionID;
    }

    public void setEstk_transactionID(String estk_transactionID) {
        this.estk_transactionID = estk_transactionID;
    }

    public String getEstk_sessionID() {
        return estk_sessionID;
    }

    public void setEstk_sessionID(String estk_sessionID) {
        this.estk_sessionID = estk_sessionID;
    }

    public String getEstk_messageID() {
        return estk_messageID;
    }

    public void setEstk_messageID(String estk_messageID) {
        this.estk_messageID = estk_messageID;
    }

    public String getEstk_creationtimestamp() {
        return estk_creationtimestamp;
    }

    public void setEstk_creationtimestamp(String estk_creationtimestamp) {
        this.estk_creationtimestamp = estk_creationtimestamp;
    }
}
