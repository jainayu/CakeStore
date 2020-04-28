package com.example.cakestore.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.cakestore.R;
import com.example.cakestore.models.CakeDataModel;
import com.example.cakestore.models.CakeDataWLPModel;
import com.example.cakestore.models.CakeResponse;
import com.example.cakestore.utils.ImageURL;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Callback;

public class CartListRVAdapter extends RecyclerView.Adapter<CartListRVAdapter.ViewHolder> {
    private ArrayList<CakeDataModel> cakeDataModelList;
    private CartClickListeners cartClickListener;

    public CartListRVAdapter(ArrayList<CakeDataModel> cakeDataModelList, CartClickListeners cartClickListener){
        this.cakeDataModelList = cakeDataModelList;
        this.cartClickListener = cartClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View item = layoutInflater.inflate(R.layout.item_cart_list, parent, false);
        ViewHolder viewHolder = new ViewHolder(item);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {

        String cakeName = cakeDataModelList.get(position).getCake_name();
        List<CakeDataWLPModel> WLPList = cakeDataModelList.get(position).getW_l_p();
        String url = new ImageURL().getImageURL(WLPList);

        Picasso.get().load(url).into(holder.cakeImage);
        holder.cakePriceTextView.setText("₹"+ WLPList.get(0).getPrice());
        holder.cakeWeightTextView.setText(WLPList.get(0).getWeight());
        holder.cakeNameTextView.setText(cakeName);

        holder.moveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cartClickListener.MoveCartOnClick(view, position);
            }
        });

        holder.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cartClickListener.DeleteCartOnClick(view, position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return cakeDataModelList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        ImageView cakeImage;
        TextView cakePriceTextView;
        TextView cakeNameTextView;
        TextView cakeWeightTextView;
        Button moveButton, deleteButton;

        public ViewHolder(View itemView){
            super(itemView);
            cakeImage = itemView.findViewById(R.id.cakeImageView);
            cakePriceTextView = itemView.findViewById(R.id.cakePrice);
            cakeNameTextView = itemView.findViewById(R.id.cakeName);
            cakeWeightTextView = itemView.findViewById(R.id.cakeWeight);
            moveButton = itemView.findViewById(R.id.move_button);
            deleteButton = itemView.findViewById(R.id.delete_button);
        }
    }

    public void notifyData(ArrayList<CakeDataModel> cakeList){
        cakeDataModelList = cakeList;
        notifyDataSetChanged();
    }
    public interface CartClickListeners{
        void DeleteCartOnClick(View view, int position);
        void MoveCartOnClick(View view, int position);
    }

}
