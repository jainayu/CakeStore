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
import com.example.cakestore.utils.ImageURL;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class SelectedRVAdapter extends RecyclerView.Adapter<SelectedRVAdapter.ViewHolder> {
    private ArrayList<CakeDataModel> cakeDataModelList;
    private SelectedClickListener selectedClickListener;

    public SelectedRVAdapter(ArrayList<CakeDataModel> cakeDataModelList, SelectedClickListener selectedClickListener){
        this.cakeDataModelList = cakeDataModelList;
        this.selectedClickListener = selectedClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View item = layoutInflater.inflate(R.layout.item_selected_list, parent, false);
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

        holder.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedClickListener.DeleteSelectedOnClick(view, position);
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
        Button  deleteButton;
        public ViewHolder(View itemView){
            super(itemView);
            cakeImage = itemView.findViewById(R.id.cakeSelectedImageView);
            cakePriceTextView = itemView.findViewById(R.id.cakeSelectedPrice);
            cakeNameTextView = itemView.findViewById(R.id.cakeSelectedName);
            cakeWeightTextView = itemView.findViewById(R.id.cakeSelectedWeight);
            deleteButton = itemView.findViewById(R.id.delete_button_cakeSelected);
        }
    }
    public void notifyData(ArrayList<CakeDataModel> cakeList){
        cakeDataModelList = cakeList;
        notifyDataSetChanged();
    }
    public void notifyDraggedData(ArrayList<CakeDataModel> cakeList, int posDragged, int posTarget){
        cakeDataModelList = cakeList;
        notifyItemMoved(posDragged, posTarget);
    }
    public interface SelectedClickListener{
        void DeleteSelectedOnClick(View view, int position);
    }
}
