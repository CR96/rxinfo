package com.github.rxinfo.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Meta implements Serializable {

    private final static long serialVersionUID = -6888293659039718597L;
    @SerializedName("disclaimer")
    @Expose
    public String disclaimer;
    @SerializedName("terms")
    @Expose
    public String terms;
    @SerializedName("license")
    @Expose
    public String license;
    @SerializedName("last_updated")
    @Expose
    public String lastUpdated;
    @SerializedName("results")
    @Expose
    public Results results;

}
