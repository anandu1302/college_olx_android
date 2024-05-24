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
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.nextgen.collegeolx.GlobalPreference;
import com.nextgen.collegeolx.ModelClass.MyProductsModelClass;
import com.nextgen.collegeolx.ModelClass.ProductsModelClass;
import com.nextgen.collegeolx.ProductDetailsActivity;
import com.nextgen.collegeolx.R;
import com.nextgen.collegeolx.UpdateProductActivity;

import java.util.ArrayList;

public class MyProductsAdapter extends RecyclerView.Adapter<MyProductsAdapter.MyViewHolder>{

    ArrayList<MyProductsModelClass> list;
    Context context;
    String ip;

    public MyProductsAdapter(ArrayList<MyProductsModelClass> list, Context context) {
        this.list = list;
        this.context = context;

        GlobalPreference globalPreference = new GlobalPreference(context);
        ip = globalPreference.getIP();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.raw_user_products,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        MyProductsModelClass myProductList = list.get(position);
        holder.productNameTV.setText(myProductList.getPname());
        holder.descTV.setText(myProductList.getDesc());
        holder.priceTV.setText("₹ "+myProductList.getPrice());

        Glide.with(context).load("http://" + ip +"/collegeOLX/products_tbl/uploads/" + myProductList.getImage()).into(holder.productIV);

        holder.productsCV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, UpdateProductActivity.class);
                intent.putExtra("pid",myProductList.getId());
                intent.putExtra("pName",myProductList.getPname());
                intent.putExtra("pPrice",myProductList.getPrice());
                intent.putExtra("pImage",myProductList.getImage());
                context.startActivity(intent);
            }
        });

        holder.deleteProductIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(context, ""+myProductList.getId(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView productNameTV;
        TextView descTV;
        TextView priceTV;
        ImageView productIV;
        CardView productsCV;
        ImageView deleteProductIV;

        public MyViewHolder(@NonNull View itemview) {
            super(itemview);

            productNameTV = itemview.findViewById(R.id.mProductNameTextView);
            descTV = itemview.findViewById(R.id.mProductDescTextView);
            priceTV = itemview.findViewById(R.id.mProductPriceTextView);
            productIV = itemview.findViewById(R.id.mProductImageView);
            deleteProductIV = itemview.findViewById(R.id.mDeleteProductImageView);
            productsCV = itemview.findViewById(R.id.card_my_products);
        }
    }
}
