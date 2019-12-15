package com.lindar.loqate.model;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

import java.util.List;

@Data
public class EmailVerificationItems {
    @SerializedName("Items")
    private List<EmailVerificationResponse> items;
}
