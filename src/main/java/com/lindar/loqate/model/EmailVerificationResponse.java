package com.lindar.loqate.model;

import com.google.gson.annotations.SerializedName;
import com.lindar.loqate.util.EmailVerificationCodes;
import lombok.Data;
import lombok.ToString;

@Data
@ToString(callSuper = true)
public class EmailVerificationResponse extends ErrorResponse {

    @SerializedName("ResponseCode")
    private EmailVerificationCodes responseCode;

    @SerializedName("ResponseMessage")
    private String responseMessage;

    @SerializedName("EmailAddress")
    private String emailAddress;

    @SerializedName("UserAccount")
    private String userAccount;

    @SerializedName("Domain")
    private String domain;

    @SerializedName("IsDisposableOrTemporary")
    private Boolean isDisposableOrTemporary;

    @SerializedName("IsComplainerOrFraudRisk")
    private Boolean isComplainerOrFraudRisk;

    @SerializedName("Duration")
    private Double duration;
}
