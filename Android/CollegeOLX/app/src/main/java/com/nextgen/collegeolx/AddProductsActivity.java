package com.nextgen.collegeolx;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
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

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class AddProductsActivity extends AppCompatActivity {

    EditText productNameET;
    EditText productDescET;
    EditText productPriceET;
    ImageView productIV;
    Button addProductBT;

    private GlobalPreference globalPreference;
    private String ip,uid;

    private boolean iset = false;
    private String image = "";
    private Bitmap bitmap;

    private ImageView backIV;
    private TextView titleTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_products);

        globalPreference = new GlobalPreference(this);
        ip = globalPreference.getIP();
        uid = globalPreference.getID();

        productNameET = findViewById(R.id.productNameEditText);
        productDescET = findViewById(R.id.productDescEditText);
        productPriceET = findViewById(R.id.productPriceEditText);
        addProductBT = findViewById(R.id.addProductButton);
        productIV = findViewById(R.id.addProductImageView);

        backIV = findViewById(R.id.BackImageButton);
        titleTV = findViewById(R.id.appBarTitleTextView);

        titleTV.setText("Sell Products");
        backIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(AddProductsActivity.this,HomeActivity.class);
                startActivity(intent);
            }
        });

        productIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), 100);
            }
        });

        addProductBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (iset == true){
                    image = getStringImage(bitmap);
                    addProducts();
                }
                else{
                    Toast.makeText(AddProductsActivity.this, "Please Select Image", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public String getStringImage(Bitmap bmp) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 100 && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri filePath = data.getData();
            try {
                //Getting the Bitmap from Gallery
                bitmap = MediaStore.Images.Media.getBitmap(getApplication().getContentResolver(), filePath);
                //Setting the Bitmap to ImageView
                productIV.setImageBitmap(bitmap);
                iset = true;

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void addProducts() {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://"+ ip +"/collegeOLX/api/addProduct.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                if (response.equals("success")){
                    Intent intent = new Intent(AddProductsActivity.this,HomeActivity.class);
                    startActivity(intent);
                }
                else{
                    Toast.makeText(AddProductsActivity.this,""+response,Toast.LENGTH_SHORT).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(AddProductsActivity.this,""+error,Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            @Nullable
            protected Map<String,String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("uid",uid);
                params.put("productName",productNameET.getText().toString());
                params.put("productDesc",productDescET.getText().toString());
                params.put("productPrice",productPriceET.getText().toString());
                params.put("image",image);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(AddProductsActivity.this);
        requestQueue.add(stringRequest);
    }
}