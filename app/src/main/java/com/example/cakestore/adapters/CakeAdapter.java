package com.example.cakestore.adapters;
import com.example.cakestore.AddToCartActivity;
import com.example.cakestore.R;
import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.cakestore.models.CakeDataModel;
import com.example.cakestore.models.CakeDataWLPModel;
import com.example.cakestore.utils.ImageURL;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.sql.Struct;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class CakeAdapter extends ArrayAdapter<CakeDataModel> {
    private Context mCtx;
    private int gridLayoutRes;
    private List<CakeDataModel> cakesList;
    private List<CakeDataWLPModel> WLPList;
    private String cakeName;
    public CakeDetailsListener cakeDetailsListener;

    public CakeAdapter(Context mCtx, int gridLayoutRes, List<CakeDataModel> cakesList, CakeDetailsListener cakeDetailsListener){
        super(mCtx, gridLayoutRes, cakesList);

        this.mCtx = mCtx;
        this.gridLayoutRes = gridLayoutRes;
        this.cakesList = cakesList;
        this.cakeDetailsListener = cakeDetailsListener;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        final LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(gridLayoutRes, null);

        ImageView cakeImage = view.findViewById(R.id.cakeImageView);
        TextView cakePriceTextView = view.findViewById(R.id.cakePrice);
        TextView cakeNameTextView = view.findViewById(R.id.cakeName);
        TextView cakeWeightTextView = view.findViewById(R.id.cakeWeight);
        Button addToCart = view.findViewById(R.id.add_to_cart);

        cakeName = cakesList.get(position).getCake_name();
        WLPList = cakesList.get(position).getW_l_p();
        String url = new ImageURL().getImageURL(WLPList);

        Picasso.get().load(url).into(cakeImage);
        cakePriceTextView.setText("₹"+WLPList.get(0).getPrice());
        cakeWeightTextView.setText(WLPList.get(0).getWeight());
        cakeNameTextView.setText(cakeName);
        addToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               cakeDetailsListener.AddToCartOnClick(view, position);
            }
        });
        return view;
    }

    public interface CakeDetailsListener{
        void AddToCartOnClick(View view, int position);
    }

}
