package com.market.stock.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import org.springframework.validation.annotation.Validated;

@Validated
@JsonRootName("errorMessage")
public class ErrorMessage {
    @JsonProperty("errorCode")
    private String errorCode;
    @JsonProperty("errorDef")
    private String errorDef;

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorDef() {
        return errorDef;
    }

    public void setErrorDef(String errorDef) {
        this.errorDef = errorDef;
    }
}
