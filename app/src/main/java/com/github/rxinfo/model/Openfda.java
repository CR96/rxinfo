package com.github.rxinfo.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class Openfda implements Serializable {

    private final static long serialVersionUID = -2382154789844175251L;
    @SerializedName("product_ndc")
    @Expose
    public List<String> productNdc = null;
    @SerializedName("is_original_packager")
    @Expose
    public List<Boolean> isOriginalPackager = null;
    @SerializedName("package_ndc")
    @Expose
    public List<String> packageNdc = null;
    @SerializedName("generic_name")
    @Expose
    public List<String> genericName = null;
    @SerializedName("spl_set_id")
    @Expose
    public List<String> splSetId = null;
    @SerializedName("brand_name")
    @Expose
    public List<String> brandName = null;
    @SerializedName("manufacturer_name")
    @Expose
    public List<String> manufacturerName = null;
    @SerializedName("unii")
    @Expose
    public List<String> unii = null;
    @SerializedName("rxcui")
    @Expose
    public List<String> rxcui = null;
    @SerializedName("spl_id")
    @Expose
    public List<String> splId = null;
    @SerializedName("substance_name")
    @Expose
    public List<String> substanceName = null;
    @SerializedName("product_type")
    @Expose
    public List<String> productType = null;
    @SerializedName("route")
    @Expose
    public List<String> route = null;
    @SerializedName("application_number")
    @Expose
    public List<String> applicationNumber = null;

}
