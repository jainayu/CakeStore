package com.example.cakestore.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class CakeDataModel implements Parcelable {

    private List<CakeDataWLPModel> w_l_p;
    private String cake_name;

    protected CakeDataModel(Parcel in) {
        cake_name = in.readString();
    }

    public static final Creator<CakeDataModel> CREATOR = new Creator<CakeDataModel>() {
        @Override
        public CakeDataModel createFromParcel(Parcel in) {
            return new CakeDataModel(in);
        }

        @Override
        public CakeDataModel[] newArray(int size) {
            return new CakeDataModel[size];
        }
    };

    public List<CakeDataWLPModel> getW_l_p() {
        return w_l_p;
    }

    public void setW_l_p(List<CakeDataWLPModel> w_l_p) {
        this.w_l_p = w_l_p;
    }

    public String getCake_name() {
        return cake_name;
    }

    public void setCake_name(String cake_name) {
        this.cake_name = cake_name;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeArray(new List[]{w_l_p});
    }
}
