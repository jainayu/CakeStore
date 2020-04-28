package com.example.cakestore;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.os.Bundle;
import android.view.View;

import com.example.cakestore.adapters.CartListRVAdapter;
import com.example.cakestore.adapters.SelectedRVAdapter;
import com.example.cakestore.models.CakeDataModel;
import com.example.cakestore.models.CakeResponse;
import com.example.cakestore.networking.RetrofitClient;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static com.example.cakestore.adapters.CartListRVAdapter.*;

public class AddToCartActivity extends AppCompatActivity implements CartClickListeners, SelectedRVAdapter.SelectedClickListener {
    RecyclerView cartListRV, selectedListRV;
    ArrayList<CakeDataModel> cartCakesList;
    ArrayList<CakeDataModel> selectedCakesList = new ArrayList<>();
    CartClickListeners cartClickListeners;
    CartListRVAdapter cartListRVAdapter;
    SelectedRVAdapter selectedRVAdapter;
    LinearLayoutManager cartLayoutManager;
    LinearLayoutManager selectedLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_to_cart);

        cartLayoutManager = new LinearLayoutManager(this);
        cartLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        selectedLayoutManager = new LinearLayoutManager(this);
        selectedLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        cartListRV = findViewById(R.id.cart_list);
        cartListRV.setLayoutManager(cartLayoutManager);
        cartClickListeners = this;

        selectedListRV = findViewById(R.id.selected_list);
        selectedListRV.setLayoutManager(selectedLayoutManager);
        selectedRVAdapter = new SelectedRVAdapter(selectedCakesList, this);
        selectedListRV.setAdapter(selectedRVAdapter);

        ItemTouchHelper helper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP| ItemTouchHelper.DOWN, 0) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder dragged, @NonNull RecyclerView.ViewHolder target) {
                int posDragged = dragged.getAdapterPosition();
                int posTarget = target.getAdapterPosition();
                if (posDragged < posTarget) {
                    for (int i = posDragged; i < posTarget; i++) {
                        Collections.swap(selectedCakesList, i, i + 1);
                    }
                } else {
                    for (int i = posDragged; i > posTarget; i--) {
                        Collections.swap(selectedCakesList, i, i - 1);
                    }
                }
                selectedRVAdapter.notifyDraggedData(selectedCakesList, posDragged, posTarget);
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

            }
        });

        helper.attachToRecyclerView(selectedListRV);

        fetchData();
    }

    private void fetchData(){
        Call<CakeResponse> call = RetrofitClient
                .getInstance()
                .getApi()
                .cakeDetails();

        call.enqueue(new Callback<CakeResponse>() {
            @Override
            public void onResponse(Call<CakeResponse> call, Response<CakeResponse> response) {
                CakeResponse cakeResponse = response.body();
                if(response.code() == 200){
                    if(cakeResponse.getData() != null) {
                        cartCakesList = (ArrayList<CakeDataModel>) cakeResponse.getData();
                        cartListRVAdapter = new CartListRVAdapter(cartCakesList, cartClickListeners);
                        cartListRV.setAdapter(cartListRVAdapter);
                    }
                    else{
                        System.out.println(cakeResponse.getStatus());
                        System.out.println(cakeResponse.getError_msg());
                    }
                }
                else if (response.code() == 422){

                }
            }

            @Override
            public void onFailure(Call<CakeResponse> call, Throwable t) {

            }
        });
    }

    @Override
    public void DeleteCartOnClick(View view, int position) {
        cartCakesList.remove(position);
        cartListRVAdapter.notifyData(cartCakesList);
    }

    @Override
    public void MoveCartOnClick(View view, int position) {
        CakeDataModel cakeDataModel = cartCakesList.get(position);
        selectedCakesList.add(cakeDataModel);
        selectedRVAdapter.notifyData(selectedCakesList);
    }

    @Override
    public void DeleteSelectedOnClick(View view, int position) {
        selectedCakesList.remove(position);
        selectedRVAdapter.notifyData(selectedCakesList);
    }
}
