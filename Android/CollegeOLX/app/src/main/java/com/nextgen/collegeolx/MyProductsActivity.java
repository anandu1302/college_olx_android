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
import com.nextgen.collegeolx.Adapter.MyProductsAdapter;
import com.nextgen.collegeolx.Adapter.ProductsAdapter;
import com.nextgen.collegeolx.ModelClass.MyProductsModelClass;
import com.nextgen.collegeolx.ModelClass.ProductsModelClass;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MyProductsActivity extends AppCompatActivity {

    private static String TAG ="MyProductsActivity";

    View view;
    RecyclerView myProductsRV;
    ArrayList<MyProductsModelClass> list;

    private GlobalPreference globalPreference;
    private String ip,uid;
    private ImageView backIV;
    private TextView titleTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_products);

        globalPreference = new GlobalPreference(this);
        ip = globalPreference.getIP();
        uid = globalPreference.getID();

        backIV = findViewById(R.id.BackImageButton);
        titleTV = findViewById(R.id.appBarTitleTextView);

        titleTV.setText("My Products");
        backIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MyProductsActivity.this,HomeActivity.class);
                startActivity(intent);
            }
        });

        myProductsRV = findViewById(R.id.myProductsRecyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        myProductsRV.setLayoutManager(layoutManager);

        getProducts();
    }

    private void getProducts() {

        list = new ArrayList<>();

        StringRequest stringRequest = new StringRequest(Request.Method.GET, "http://"+ ip +"/collegeOLX/api/userProducts.php?uid="+uid, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.d(TAG, "onResponse: "+response);

                if (response.equals("failed")){
                    Toast.makeText(MyProductsActivity.this, "No Requests Available", Toast.LENGTH_SHORT).show();
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

                            list.add(new MyProductsModelClass(id,pname,desc,image,price));

                        }

                        MyProductsAdapter adapter = new MyProductsAdapter(list,MyProductsActivity.this);
                        myProductsRV.setAdapter(adapter);

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