package com.lindar.loqate.util;

import com.google.gson.annotations.SerializedName;

public enum EmailVerificationCodes {
    @SerializedName("Valid")
    VALID,
    @SerializedName("Valid_CatchAll")
    VALID_CATCH_ALL,
    @SerializedName("Invalid")
    INVALID,
    @SerializedName("Timeout")
    TIMEOUT;
}
