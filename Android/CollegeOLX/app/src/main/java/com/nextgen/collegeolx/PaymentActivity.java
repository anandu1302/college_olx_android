package com.nextgen.collegeolx;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class PaymentActivity extends AppCompatActivity {

    private static final String TAG = "PaymentActivity";

    private RadioButton cardRadioButton;
    private TextView paymentTVButton,amountTextView;
    EditText accountNoET;
    EditText pinET;
    LinearLayout accountDetailsLL;


    private String uid;
    private String ip;
    private String ptype;
    private Intent intent;
    private TextView title;

    private ImageView BackButton;

    private GlobalPreference globalPreference;
    private String amount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        globalPreference = new GlobalPreference(this);
        ip = globalPreference.getIP();
        uid = globalPreference.getID();

        amount = getIntent().getStringExtra("amount");

        // Toast.makeText(this, "cash "+amount, Toast.LENGTH_SHORT).show();

        title = findViewById(R.id.appBarTitleTextView);
        title.setText("Payment Checkout");

        BackButton = (ImageView) findViewById(R.id.BackImageButton);
        BackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        iniit();

        amountTextView.setText("₹ "+amount);

        cardRadioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(cardRadioButton.isChecked()) {
                    ptype = "Upi Payment";
                    accountDetailsLL.setVisibility(View.VISIBLE);

                }
                else{
                    cardRadioButton.setChecked(true);
                }
            }
        });

        paymentTVButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: "+ptype);

                if(!cardRadioButton.isChecked())
                {
                    Toast.makeText(PaymentActivity.this, "Please select a payment method ", Toast.LENGTH_SHORT).show();
                }
                else if(cardRadioButton.isChecked() && accountNoET.getText().toString().equals("") ||
                        cardRadioButton.isChecked() && pinET.getText().toString().equals(""))
                {
                    Toast.makeText(PaymentActivity.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                }

                else {
                    paynow();
                }

            }
        });


    }

    private void paynow() {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://"+ ip +"/collegeOLX/api/payment.php" , new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.d(TAG,"onResponse: "+response);

                if (response.equals("failed")){
                    Toast.makeText(PaymentActivity.this,"Your Account Does not have Enough Balance", Toast.LENGTH_LONG).show();
                }else if (response.equals("accerror")){
                    Toast.makeText(PaymentActivity.this,"Account Does not exist", Toast.LENGTH_LONG).show();
                }else if (response.equals("pin")){
                    Toast.makeText(PaymentActivity.this, "Incorrect Pin", Toast.LENGTH_LONG).show();
                }else if (response.equals("success")){
                    Toast.makeText(PaymentActivity.this, "Payment Success", Toast.LENGTH_LONG).show();

                    Intent intent = new Intent(PaymentActivity.this,HomeActivity.class);
                    startActivity(intent);
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Log.d(TAG, "onErrorResponse: "+error);

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("uid",uid);
                params.put("accountNo",accountNoET.getText().toString());
                params.put("pin",pinET.getText().toString());
                params.put("amount",amount);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }

    private void iniit() {

        cardRadioButton = findViewById(R.id.pRadioCard);
        paymentTVButton = findViewById(R.id.pPaymentTextViewButton);
        amountTextView = findViewById(R.id.pAmountTextView);
        accountDetailsLL = findViewById(R.id.pAccountDetailsLL);
        accountNoET = findViewById(R.id.pAccountNumberEditText);
        pinET = findViewById(R.id.pPinNumberEditText);

    }
}