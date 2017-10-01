package com.github.rxinfo.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Results implements Serializable {

    private final static long serialVersionUID = -9030953540103656458L;
    @SerializedName("skip")
    @Expose
    public Integer skip;
    @SerializedName("limit")
    @Expose
    public Integer limit;
    @SerializedName("total")
    @Expose
    public Integer total;

}
