package com.github.rxinfo.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TopDrugs {

    @SerializedName("top_drugs")
    @Expose
    public List<TopDrug> topDrugs = null;
}