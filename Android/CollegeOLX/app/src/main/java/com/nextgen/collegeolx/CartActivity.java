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
import com.nextgen.collegeolx.Adapter.CartAdapter;
import com.nextgen.collegeolx.Adapter.ProductsAdapter;
import com.nextgen.collegeolx.ModelClass.CartModelClass;
import com.nextgen.collegeolx.ModelClass.ProductsModelClass;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class CartActivity extends AppCompatActivity {

    private static String TAG ="CartActivity";
    RecyclerView cartRV;
    ArrayList<CartModelClass> list;

    TextView cartSubTotalTV;
    TextView cartTotalTV;
    TextView checkOutTV;

    private GlobalPreference globalPreference;
    private String ip,uid;

    private ImageView backIV;
    private TextView titleTV;

    int total;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        globalPreference = new GlobalPreference(this);
        ip = globalPreference.getIP();
        uid = globalPreference.getID();

        cartRV = findViewById(R.id.cartRecyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        cartRV.setLayoutManager(layoutManager);

        backIV = findViewById(R.id.BackImageButton);
        titleTV = findViewById(R.id.appBarTitleTextView);

        getCart();

        titleTV.setText("Cart");
        backIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(CartActivity.this,HomeActivity.class);
                startActivity(intent);
            }
        });

        cartSubTotalTV = findViewById(R.id.cartSubTotalTextView);
        cartTotalTV = findViewById(R.id.cartTotalTextView);
        checkOutTV = findViewById(R.id.cartCheckoutButton);

        checkOutTV = findViewById(R.id.cartCheckoutButton);
        checkOutTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(cartTotalTV.getText().toString().equals("₹ 00.00"))
                {
                    Toast.makeText(CartActivity.this, "Please Add Products to Cart then Checkout", Toast.LENGTH_SHORT).show();
                }
                else {

                    Intent intent = new Intent(CartActivity.this,PaymentActivity.class);
                    intent.putExtra("amount",String.valueOf(total));
                    startActivity(intent);
                    finish();

                }
            }
        });
    }

    private void getCart() {

        list = new ArrayList<>();

        StringRequest stringRequest = new StringRequest(Request.Method.GET, "http://"+ ip +"/collegeOLX/api/getCart.php?uid="+uid, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.d(TAG, "onResponse: "+response);

                if (response.equals("failed")){
                    Toast.makeText(CartActivity.this, "No Items in Cart", Toast.LENGTH_SHORT).show();
                }
                else{
                    try{
                        JSONObject jsonObject = new JSONObject(response);
                        JSONArray jsonArray = jsonObject.getJSONArray("data");
                        for (int i=0; i< jsonArray.length();i++){
                            JSONObject object = jsonArray.getJSONObject(i);
                            String id = object.getString("id");
                            String pname = object.getString("pname");
                            String image = object.getString("image");
                            String price = object.getString("price");

                            total += Integer.parseInt(price);

                            list.add(new CartModelClass(id,pname,image,price));

                        }

                        CartAdapter adapter = new CartAdapter(list,CartActivity.this);
                        cartRV.setAdapter(adapter);
                        cartSubTotalTV.setText("₹ "+total);
                        cartTotalTV.setText("₹ "+total);

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