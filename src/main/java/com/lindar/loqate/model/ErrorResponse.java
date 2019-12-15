package com.lindar.loqate.model;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public class ErrorResponse {
    @SerializedName("Error")
    private String error;

    @SerializedName("Description")
    private String description;

    @SerializedName("Cause")
    private String cause;

    @SerializedName("Resolution")
    private String resolution;
}
