package com.github.rxinfo.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TopDrug {

    @SerializedName("brand_name")
    @Expose
    public String brandName;
    @SerializedName("generic_name")
    @Expose
    public String genericName;
}