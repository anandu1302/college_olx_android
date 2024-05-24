package com.nextgen.collegeolx.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.nextgen.collegeolx.CartActivity;
import com.nextgen.collegeolx.GlobalPreference;
import com.nextgen.collegeolx.ModelClass.CartModelClass;
import com.nextgen.collegeolx.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.MyViewHolder>{

    ArrayList<CartModelClass> list;
    Context context;
    String ip,uid;

    public CartAdapter(ArrayList<CartModelClass> list, Context context) {
        this.list = list;
        this.context = context;

        GlobalPreference globalPreference = new GlobalPreference(context);
        ip = globalPreference.getIP();
        uid = globalPreference.getID();
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.raw_cart,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        CartModelClass cartList = list.get(position);
        holder.itemNameTV.setText(cartList.getPname());

        if (cartList.getPrice().equals("0")){

            holder.itemPriceTV.setText("FREE");
            holder.itemPriceTV.setTextColor(ContextCompat.getColor(context, R.color.greenText));
        }else{

            holder.itemPriceTV.setText("₹ "+cartList.getPrice());
        }


        Glide.with(context).load("http://" + ip +"/collegeOLX/products_tbl/uploads/" + cartList.getImage()).into(holder.itemImageView);

        holder.deleteItemImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // Toast.makeText(context, ""+cartList.getId(), Toast.LENGTH_SHORT).show();
                deleteItem(cartList.getId());
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView itemNameTV;
        TextView itemPriceTV;
        ImageView itemImageView;
        ImageView deleteItemImageView;
       // TextView cItemFreeTV;


        public MyViewHolder(@NonNull View itemview) {
            super(itemview);

            itemNameTV = itemview.findViewById(R.id.cItemTitleTextView);
            itemPriceTV = itemview.findViewById(R.id.cItemPriceTextView);
            itemImageView = itemview.findViewById(R.id.cartImageView);
            deleteItemImageView = itemview.findViewById(R.id.cartDeleteItemImageView);
          //  cItemFreeTV = itemview.findViewById(R.id.cItemFreeTextView);

        }
    }

    private void deleteItem(String itemId) {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://" + ip + "/collegeOLX/api/deleteCartItem.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.equals("success")) {
                    Toast.makeText(context.getApplicationContext(), "Item Deleted", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(context.getApplicationContext(), CartActivity.class);
                    context.startActivity(intent);



                } else {
                    Toast.makeText(context.getApplicationContext(), "" + response, Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context.getApplicationContext(), "" + error, Toast.LENGTH_SHORT).show();
            }
        }) {
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("itemId",itemId);
                params.put("uid",uid);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(context.getApplicationContext());
        requestQueue.add(stringRequest);
    }

}
