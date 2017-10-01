
package com.github.rxinfo.model;

import java.io.Serializable;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Drug implements Serializable
{

    @SerializedName("meta")
    @Expose
    public Meta meta;
    @SerializedName("results")
    @Expose
    public List<Result> results = null;
    private final static long serialVersionUID = 8005577782071272345L;

}
