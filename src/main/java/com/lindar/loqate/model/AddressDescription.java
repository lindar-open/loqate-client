package com.lindar.loqate.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public class AddressDescription {

    @SerializedName("Id")
    @Expose
    private String id;
    @SerializedName("Type")
    @Expose
    private String type;
    @SerializedName("Text")
    @Expose
    private String text;
    @SerializedName("Highlight")
    @Expose
    private String highlight;
    @SerializedName("Description")
    @Expose
    private String description;
}
