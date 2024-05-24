package com.nextgen.collegeolx.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.nextgen.collegeolx.GlobalPreference;
import com.nextgen.collegeolx.ModelClass.PurchaseHistoryModelClass;
import com.nextgen.collegeolx.R;

import java.util.ArrayList;

public class PurchaseHistoryAdapter extends RecyclerView.Adapter<PurchaseHistoryAdapter.MyViewHolder>{

    ArrayList<PurchaseHistoryModelClass> list;
    Context context;
    String ip;

    public PurchaseHistoryAdapter(ArrayList<PurchaseHistoryModelClass> list, Context context) {
        this.list = list;
        this.context = context;

        GlobalPreference globalPreference = new GlobalPreference(context);
        ip = globalPreference.getIP();

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.raw_history,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        PurchaseHistoryModelClass historyList = list.get(position);
        holder.titleTV.setText(historyList.getName());
        holder.priceTV.setText("₹ "+historyList.getPrice());
        holder.orderIDTV.setText("#"+historyList.getOrderid());

        Glide.with(context).load("http://" + ip +"/collegeOLX/products_tbl/uploads/" + historyList.getImage()).into(holder.itemIV);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView titleTV;
        TextView priceTV;
        TextView orderIDTV;
        ImageView itemIV;

        public MyViewHolder(@NonNull View itemview) {
            super(itemview);

            titleTV = itemview.findViewById(R.id.hItemTitleTextView);
            priceTV = itemview.findViewById(R.id.hItemPriceTextView);
            orderIDTV = itemview.findViewById(R.id.hItemOrderIDTextView);
            itemIV = itemview.findViewById(R.id.historyImageView);

        }
    }
}
