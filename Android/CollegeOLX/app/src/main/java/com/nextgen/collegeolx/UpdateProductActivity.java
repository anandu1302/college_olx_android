package com.nextgen.collegeolx;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

import java.util.HashMap;
import java.util.Map;

public class UpdateProductActivity extends AppCompatActivity {

    private static String TAG = "UpdateProductActivity";

    TextView productNameTV;
    EditText productPriceET;
    ImageView productIV;
    Button updateBT;

    private ImageView backIV;
    private TextView appBarTV;
    String pid,productName,productPrice, productImage;


    private GlobalPreference globalPreference;
    private String ip,uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_product);

        globalPreference = new GlobalPreference(this);
        ip = globalPreference.getIP();
        uid = globalPreference.getID();

        backIV = findViewById(R.id.BackImageButton);
        appBarTV = findViewById(R.id.appBarTitleTextView);
        appBarTV.setText("Update Product");

        backIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(UpdateProductActivity.this,HomeActivity.class);
                startActivity(intent);
                finish();
            }
        });

        pid = getIntent().getStringExtra("pid");
        productName = getIntent().getStringExtra("pName");
        productPrice = getIntent().getStringExtra("pPrice");
        productImage = getIntent().getStringExtra("pImage");

        productNameTV = findViewById(R.id.uProductNameTextView);
        productPriceET = findViewById(R.id.uProductPriceEditText);
        productIV = findViewById(R.id.uProductImageView);
        updateBT = findViewById(R.id.updateButton);

        productNameTV.setText(productName);
        productPriceET.setText(productPrice);


        if (!productImage.equals("")){

            Glide.with(getApplicationContext())
                    .load("http://" + ip +"/collegeOLX/products_tbl/uploads/" + productImage)
                    .into(productIV);
        }

        updateBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateProduct();
            }
        });
    }

    private void updateProduct() {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://"+ ip +"/collegeOLX/api/updateProduct.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                if (response.equals("success")){

                    Toast.makeText(UpdateProductActivity.this, "Updated Successfully", Toast.LENGTH_LONG).show();

                    Intent intent = new Intent(UpdateProductActivity.this,HomeActivity.class);
                    startActivity(intent);
                }
                else{
                    Toast.makeText(UpdateProductActivity.this,""+response,Toast.LENGTH_SHORT).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Log.d(TAG,"onErrorResponse:"+error);
            }
        }){
            @Override
            @Nullable
            protected Map<String,String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("productId",pid);
                params.put("productPrice",productPriceET.getText().toString());
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(UpdateProductActivity.this);
        requestQueue.add(stringRequest);
    }
}