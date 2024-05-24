package com.nextgen.collegeolx;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ProductDetailsActivity extends AppCompatActivity {

    private static String TAG ="ProductDetailsActivity";

    TextView pNameTV;
    TextView pDescTV;
    TextView pPriceTV;
    ImageView productIV;
    ImageView backIV;
    Button addCartBT;

    private GlobalPreference globalPreference;
    private String ip,uid;
    private String pid,price;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);

        globalPreference = new GlobalPreference(this);
        ip = globalPreference.getIP();
        uid = globalPreference.getID();

        pid = getIntent().getStringExtra("pid");


        pNameTV = findViewById(R.id.dProductTitleTextView);
        pDescTV = findViewById(R.id.dProductDescTextView);
        productIV = findViewById(R.id.dProductImageView);
        pPriceTV = findViewById(R.id.dProductPriceTextView);
        addCartBT = findViewById(R.id.dAddCartButton);
        backIV = findViewById(R.id.productBackImageView);

        loadProductDetails();

        addCartBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addtoCart();
            }
        });

        backIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(ProductDetailsActivity.this,ProductActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void addtoCart() {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://"+ ip +"/collegeOLX/api/addCart.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Toast.makeText(ProductDetailsActivity.this, "Added to cart", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(ProductDetailsActivity.this,HomeActivity.class);
                startActivity(intent);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ProductDetailsActivity.this, ""+error, Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            @Nullable
            protected Map<String,String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("uid",uid);
                params.put("pid",pid);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(ProductDetailsActivity.this);
        requestQueue.add(stringRequest);

    }

    private void loadProductDetails() {

        StringRequest stringRequest = new StringRequest(Request.Method.GET, "http://"+ ip +"/collegeOlX/api/productDetails.php?pid="+pid, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.d(TAG, "onResponse: "+response);

                if(response.equals("failed")){
                    Toast.makeText(ProductDetailsActivity.this, ""+response, Toast.LENGTH_SHORT).show();
                }
                else{
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        JSONArray jsonArray = jsonObject.getJSONArray("data");
                        for(int i = 0; i < jsonArray.length(); i++) {
                            JSONObject object = jsonArray.getJSONObject(i);
                            String id = object.getString("id");
                            String pName = object.getString("pname");
                            String description = object.getString("description");
                            price = object.getString("price");
                            String image = object.getString("image");

                            if (!image.equals("")) {
                                Glide.with(getApplicationContext())
                                        .load("http://" + ip + "/collegeOlX/products_tbl/uploads/" + image)
                                        .into(productIV);
                            }

                            pNameTV.setText(pName);
                            pDescTV.setText(description);
                            pPriceTV.setText("₹ "+price);


                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, "onErrorResponse: "+error);
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }
}