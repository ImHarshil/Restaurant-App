package com.example.harshil.demo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class PaymentMode extends AppCompatActivity {
protected RadioGroup radioGroup;
protected RadioButton rbhome,rbgpay,rbcredit;
protected Button btndone;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_mode);
        radioGroup = findViewById(R.id.radioGroup);
        rbcredit = findViewById(R.id.rbcredit);
        rbgpay = findViewById(R.id.rbgpay);
        rbhome = findViewById(R.id.rbhome);
        btndone =findViewById(R.id.btndone);



    }
}
