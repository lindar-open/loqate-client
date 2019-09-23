package com.lindar.loqate.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public class AddressDescription {

    @Expose @SerializedName("Id")
    private String id;
    @Expose @SerializedName("Type")
    private String type;
    @Expose @SerializedName("Text")
    private String text;
    @Expose @SerializedName("Highlight")
    private String highlight;
    @Expose @SerializedName("Description")
    private String description;
}
