package com.github.rxinfo.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class Drug implements Serializable {

    private final static long serialVersionUID = 8005577782071272345L;
    @SerializedName("meta")
    @Expose
    public Meta meta;
    @SerializedName("results")
    @Expose
    final public List<Result> results = null;

}
