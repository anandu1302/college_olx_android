package com.nextgen.collegeolx;

import android.content.Intent;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.nextgen.collegeolx.Adapter.ProductHomeAdapter;
import com.nextgen.collegeolx.ModelClass.ProductsModelClass;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
   UserFragment
 */
public class UserFragment extends Fragment {

   View view;
   CardView sellCV;
   CardView myProductsCV;

   RecyclerView productsRV;
    ArrayList<ProductsModelClass> list;
    private GlobalPreference globalPreference;
    String ip;

    public UserFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static UserFragment newInstance(String param1, String param2) {
        UserFragment fragment = new UserFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        globalPreference = new GlobalPreference(getContext());
        ip = globalPreference.getIP();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_user, container, false);

        productsRV = view.findViewById(R.id.productsRecyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false);
        productsRV.setLayoutManager(layoutManager);

        sellCV = view.findViewById(R.id.card_sell);
        myProductsCV = view.findViewById(R.id.card_myproducts);

        getProducts();

        sellCV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(),AddProductsActivity.class);
                startActivity(intent);
            }
        });

        myProductsCV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(),MyProductsActivity.class);
                startActivity(intent);
            }
        });
        return view;
    }

    private void getProducts() {

        list = new ArrayList<>();

        StringRequest stringRequest = new StringRequest(Request.Method.GET, "http://"+ ip +"/collegeOLX/api/getProducts.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {



                if (response.equals("failed")){
                    Toast.makeText(getContext(), "No Products Available", Toast.LENGTH_SHORT).show();
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

                        ProductHomeAdapter adapter = new ProductHomeAdapter(list,getContext());
                        productsRV.setAdapter(adapter);

                    } catch(JSONException e){
                        e.printStackTrace();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);
    }
}