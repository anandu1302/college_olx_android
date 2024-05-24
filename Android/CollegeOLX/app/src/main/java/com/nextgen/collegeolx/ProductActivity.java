package com.nextgen.collegeolx;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.nextgen.collegeolx.Adapter.ProductsAdapter;
import com.nextgen.collegeolx.ModelClass.ProductsModelClass;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ProductActivity extends AppCompatActivity {

    private static String TAG ="ProductActivity";

    View view;
    RecyclerView productRV;
    ArrayList<ProductsModelClass> list;

    private GlobalPreference globalPreference;
    private String ip;

    private ImageView backIV;
    private TextView titleTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

        globalPreference = new GlobalPreference(this);
        ip = globalPreference.getIP();

        productRV = findViewById(R.id.productListRecyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        productRV.setLayoutManager(layoutManager);

        getProducts();

        backIV = findViewById(R.id.BackImageButton);
        titleTV = findViewById(R.id.appBarTitleTextView);

        titleTV.setText("Products");
        backIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(ProductActivity.this,HomeActivity.class);
                startActivity(intent);
            }
        });
    }

    private void getProducts() {

        list = new ArrayList<>();

        StringRequest stringRequest = new StringRequest(Request.Method.GET, "http://"+ ip +"/collegeOLX/api/getProducts.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.d(TAG, "onResponse: "+response);

                if (response.equals("failed")){
                    Toast.makeText(ProductActivity.this, "No Requests Available", Toast.LENGTH_SHORT).show();
                }
                else{
                    try{
                        JSONObject jsonObject = new JSONObject(response);
                        JSONArray jsonArray = jsonObject.getJSONArray("data");
                        for (int i=0; i< jsonArray.length();i++){
                            JSONObject object = jsonArray.getJSONObject(i);
                            String id = object.getString("id");
                            String pname = object.getString("pname");
                            String desc = object.getString("description");
                            String image = object.getString("image");
                            String price = object.getString("price");

                            list.add(new ProductsModelClass(id,pname,desc,image,price));

                        }

                        ProductsAdapter adapter = new ProductsAdapter(list,ProductActivity.this);
                        productRV.setAdapter(adapter);

                    } catch(JSONException e){
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
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }
}