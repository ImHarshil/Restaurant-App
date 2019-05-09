package com.example.harshil.demo;

import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class BillActivity extends AppCompatActivity {
    protected ArrayList<Integer> listfid;
    protected ListView listView;
    protected Button btnpay;
    protected TextView tvbill;
    protected String query;
    protected ArrayList<FoodBean> list;
    protected double bill =0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill);



            try {
            listfid = (ArrayList<Integer>) getIntent().getSerializableExtra("fid");
            Toast.makeText(getApplicationContext(), listfid.toString(), Toast.LENGTH_LONG).show();
            list= new ArrayList<FoodBean>();

            tvbill = findViewById(R.id.tvbil);
            listView = findViewById(R.id.lvbill);
            btnpay = findViewById(R.id.btnpay);

             query = "select * from foodmaster where fid in (";
            for (int i = 0; i < listfid.size(); i++) {
                query += listfid.get(i) + ",";
            }
            query = query + ")";
            query = query.replace(",)", ")");
            Toast.makeText(getApplicationContext(), query, Toast.LENGTH_LONG).show();
            btnpay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getApplicationContext(),DeliveryDetails.class);
                    intent.putExtra("bill",bill);
                    startActivity(intent);
                }
            });

            load();
        }
        catch (Exception e){
            Toast.makeText(getApplicationContext(),e.toString()+e.getLocalizedMessage(),Toast.LENGTH_LONG).show();
        }
        finally {
         //   finish();
        }




    }

    public void load(){
        AndroidNetworking.initialize(getApplicationContext());
        AndroidNetworking.post(Commons.SERVER_URL)
                .addBodyParameter("qry",query)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jrr=response.getJSONArray("data");
                            for (int i=0;i<jrr.length();i++) {
                                FoodBean bean = new FoodBean();
                                bean.setFid(jrr.getJSONObject(i).getInt("fid"));
                                bean.setFname(jrr.getJSONObject(i).getString("fname"));
                                bean.setFprice(jrr.getJSONObject(i).getInt("fprice"));
                                bean.setFdiscount(jrr.getJSONObject(i).getInt("fdiscount"));

                                int price = jrr.getJSONObject(i).getInt("fprice");
                                int discount = jrr.getJSONObject(i).getInt("fdiscount");
                                bill += price - price*discount/100;
                                list.add(bean);
                            }
                            listView.setAdapter(new BillAdapter(getApplicationContext(),R.layout.bill_list_layout,list,listfid));
                            tvbill.setText("Bill = "+bill+"Rs");

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {

                    }
                });

    }
}
