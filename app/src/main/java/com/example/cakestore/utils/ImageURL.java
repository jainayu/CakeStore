package com.example.cakestore.utils;

import com.example.cakestore.models.CakeDataWLPModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class ImageURL {
    private static final String IMAGE_BASE_URL = "http://kekizadmin.com/uploads/catrgories/";
    public String getImageURL(List<CakeDataWLPModel> cakeDataWLPModelList){
        String fileName = null;
        try {
            JSONObject jsonObject = new JSONObject(cakeDataWLPModelList.get(0).getPictures());
            fileName = jsonObject.getString("file_name");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return IMAGE_BASE_URL+fileName;
    }
}
