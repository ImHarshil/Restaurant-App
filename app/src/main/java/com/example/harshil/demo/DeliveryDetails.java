package com.example.harshil.demo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class DeliveryDetails extends AppCompatActivity {
    protected EditText etphoneno,etemailid,etpincode,etaddress;
    protected Button btncontinue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery_details);

        etaddress = findViewById(R.id.etaddress);
        etemailid = findViewById(R.id.etemailid);
        etphoneno = findViewById(R.id.etphoneno);
        etpincode = findViewById(R.id.etpincode);

        etphoneno.requestFocus();

        btncontinue= findViewById(R.id.btncontinue);
        btncontinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (etphoneno.getText().toString().equals("")){
                    etphoneno.setError("phone no is compulsary");
                    etphoneno.requestFocus();
                    return;
                }
                if (etpincode.getText().toString().equals("")){
                    etpincode.setError("pincode is compulsary");
                    etpincode.requestFocus();
                    return;
                }
                if (etaddress.getText().toString().equals("")){
                    etaddress.setError("address is compulsary");
                    etaddress.requestFocus();
                    return;
                }
                Intent intent = new Intent(getApplicationContext(),PaymentMode.class);
                intent.putExtra("bill",getIntent().getDoubleExtra("bill",0));
                intent.putExtra("pincode",etpincode.getText().toString());
                intent.putExtra("phoneno",etphoneno.getText().toString());
                intent.putExtra("emailid",etemailid.getText().toString());
                intent.putExtra("address",etaddress.getText().toString());
                startActivity(intent);
            }
        });
    }
}
