package com.example.harshil.demo;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.harshil.demo.FoodBean;

import java.util.ArrayList;

/**
 * Created by ${harshil} on 18-12-2018.
 */
public class BillAdapter extends BaseAdapter {
    private final ArrayList<Integer> selectfid;
    private  ArrayList<FoodBean> list;
    private  int layout;
    private  Context context;


    public BillAdapter(Context context, int layout, ArrayList<FoodBean> list,ArrayList<Integer> selectfid){
        this.context=context;
        this.layout = layout;
        this.list = list;
        this.selectfid = selectfid;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        view = layoutInflater.inflate(layout,viewGroup,false);

        TextView tvname = view.findViewById(R.id.blvtvname);
        TextView tvid = view.findViewById(R.id.blvtvfid);
        TextView tvprice = view.findViewById(R.id.blvtvprice);
        Button btncancel = view.findViewById(R.id.btnlvcancel);

        tvid.setText(list.get(i).getFid()+"");
        tvname.setText(list.get(i).getFname());
        double price = list.get(i).fprice ;
        double discount = list.get(i).getFdiscount();
        double ap = price - price*discount/100;
        tvprice.setText(ap+"Rs");

        btncancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectfid.remove(i);
                Intent intent = new Intent(context,BillActivity.class).putExtra("fid",selectfid);
                context.startActivity(intent);
                return;
            }
        });
        return view;
    }
}