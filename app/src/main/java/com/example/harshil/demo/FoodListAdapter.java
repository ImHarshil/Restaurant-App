package com.example.harshil.demo;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CheckedTextView;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by ${harshil} on 14-12-2018.
 */
public class FoodListAdapter extends BaseAdapter {

    private  List<FoodBean> list;
    private  int layout;
    private  Context context;
    private  boolean[] cbarray;
    private ArrayList<Integer> selectfid;

    public FoodListAdapter(Context context, int layout, List list,boolean[] cbarray,ArrayList<Integer> selectfid){
        this.context=context;
        this.layout=layout;
        this.list=list;
        this.cbarray=cbarray;
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
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = layoutInflater.inflate(layout,viewGroup,false);

        ImageView imageView = view.findViewById(R.id.imageView2);
        TextView tvfname =    view.findViewById(R.id.lvfname);
        TextView tvfprice =   view.findViewById(R.id.lvprice);
        TextView tvdiscount=  view.findViewById(R.id.lvdiscount);
        final CheckBox cbselect=    view.findViewById(R.id.checkBox);
        CheckedTextView ctvmore = view.findViewById(R.id.checkedTextView);

        ctvmore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog dialog = new Dialog(context);
                dialog.setContentView(R.layout.dialog);
                TextView tvdescription = dialog.findViewById(R.id.tvdialog);
                tvdescription.setText(list.get(i).getFdescription());
                dialog.setCancelable(true);
                dialog.create();
                dialog.show();
            }
        });

        tvfname.setText(list.get(i).getFname());
        tvfprice.setText(tvfprice.getText()+""+list.get(i).getFprice());
        tvdiscount.setText(tvdiscount.getText()+""+list.get(i).fdiscount);


        cbselect.setVisibility(View.INVISIBLE);

        if(isSelected(list.get(i).getFid())){
            view.setBackgroundColor(Color.argb(255,205,249,187));
            cbselect.setVisibility(View.VISIBLE);
            cbselect.setChecked(true);
        }



        final View finalView1 = view;
        cbselect.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
               /*     toggle(list.get(i).getFid());
                    if(b==true){
                        finalView1.setBackgroundColor(Color.argb(255,205,249,187));
                        cbselect.setVisibility(View.VISIBLE);
                        cbselect.setChecked(true);
                    }
                    else {
                        finalView1.setBackgroundColor(Color.WHITE);
                        cbselect.setVisibility(View.INVISIBLE);
                        cbselect.setChecked(false);
                    } */
            }
        });

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toggle(list.get(i).fid);
                if(isSelected(list.get(i).getFid())){
                    view.setBackgroundColor(Color.argb(255,205,249,187));
                    cbselect.setVisibility(View.VISIBLE);
                    cbselect.setChecked(true);
                }
                else {
                    view.setBackgroundColor(Color.WHITE);
                    cbselect.setVisibility(View.INVISIBLE);
                    cbselect.setChecked(false);
                }
            }
        });




        return view ;
    }




    void toggle(int id){

        for (int j=0;j<selectfid.size();j++){
            if(selectfid.get(j).equals(id)){
                try {
                    selectfid.remove(j);
                    Toast.makeText(context,selectfid.toString(),Toast.LENGTH_LONG).show();
                    return;
                }
                catch (Exception e){
                    Toast.makeText(context,e.toString(),Toast.LENGTH_LONG).show();
                }
            }
        }
        selectfid.add(id);
        Toast.makeText(context,selectfid.toString(),Toast.LENGTH_LONG).show();
    }

    boolean isSelected(int id){
        for(int j=0;j<selectfid.size();j++){
            if(selectfid.get(j).equals(id))
                return true;
        }
        return false;
    }
}
