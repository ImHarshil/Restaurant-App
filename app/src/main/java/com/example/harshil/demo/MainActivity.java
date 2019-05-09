package com.example.harshil.demo;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.WindowDecorActionBar;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.getbase.floatingactionbutton.FloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionsMenu;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class MainActivity extends AppCompatActivity {
    protected SearchView searchView;
    protected Spinner spinner;
    protected ListView listView;
    protected Button btncart;
    protected FloatingActionButton fab1;
    protected FloatingActionButton fab2;
    protected FloatingActionsMenu  fabmenu;
    protected String[] menu={"snack","main course","dessert"};
    protected List<FoodBean> list;
    protected ArrayList<Integer> selectfid;
    protected boolean[] cbarray;
    protected int currentmenu ;
    protected android.app.AlertDialog alertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        searchView = (SearchView)findViewById(R.id.search_view);
        btncart = (Button)findViewById(R.id.btncart);
        spinner = (Spinner)findViewById(R.id.spinner);
        listView = (ListView)findViewById(R.id.listview);
        fab1 = findViewById(R.id.fab1);
        fab2 = findViewById(R.id.fab2);
        fabmenu = findViewById(R.id.fabmenu);


        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                String query = "select * from foodmaster where fname like '%"+s+"%'";
                load(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                String query = "select * from foodmaster where fname like '%"+s+"%'";
                load(query);
                return false;
            }
        });
        searchView.setQueryHint("food name");

        ArrayAdapter aa = new ArrayAdapter(getApplicationContext(),R.layout.spinnner,menu);
        aa.setDropDownViewResource(R.layout.spinnner);
        spinner.setAdapter(aa);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                YoYo.with(Techniques.Flash).duration(1000).playOn((TextView)findViewById(R.id.textView));
                String query = FoodBean.getFoodByCategory(menu[i]);currentmenu=i;
                load(query);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        fab1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // selectfid.clear();
                String query = "select fid from foodmaster where fcategory = '"+menu[currentmenu]+"'";
                AndroidNetworking.initialize(getApplicationContext());
                AndroidNetworking.post(Commons.SERVER_URL)
                        .addBodyParameter("qry",query)
                        .build()
                        .getAsJSONObject(new JSONObjectRequestListener() {
                            @Override
                            public void onResponse(JSONObject response) {
                                try {
                                    JSONArray jrr = response.getJSONArray("data");
                                    for(int m=0;m<jrr.length();m++)
                                        selectfid.add(jrr.getJSONObject(m).getInt("fid"));

                                    load(FoodBean.getFoodByCategory(menu[currentmenu]));
                                    fabmenu.collapse();
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }

                            @Override
                            public void onError(ANError anError) {

                            }
                        });
            }
        });
        fab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectfid.clear();
                String query = FoodBean.getFoodByCategory(menu[0]);
                load(query);
                fabmenu.collapse();
            }
        });

        selectfid = new ArrayList<Integer>();
        load(FoodBean.getFoodByCategory("snack"));

        btncart.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               if(selectfid.size()>0) {
                   Intent intent = new Intent(getApplicationContext(),BillActivity.class);
                   intent.putExtra("fid", selectfid);
                   startActivity(intent);
               }
               else {
                   final Snackbar snackbar = Snackbar.make(btncart,"please select a item",Snackbar.LENGTH_INDEFINITE);
                           snackbar.setAction("ok", new View.OnClickListener() {
                       @Override
                       public void onClick(View view) {
                           snackbar.dismiss();
                       }
                   });
                           snackbar.show();
               }
           }
       });


    }
    void load(String query){
        AndroidNetworking.initialize(getApplicationContext());
        AndroidNetworking.post(Commons.SERVER_URL)
                .addBodyParameter("qry",query)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            list = new ArrayList<FoodBean>();
                            JSONArray jrr = response.getJSONArray("data");
                            for (int i=0;i<jrr.length();i++){
                                FoodBean foodBean = new FoodBean();
                                foodBean.setFid(jrr.getJSONObject(i).getInt("fid"));
                                foodBean.setFname(jrr.getJSONObject(i).getString("fname"));
                                foodBean.setFprice(jrr.getJSONObject(i).getInt("fprice"));
                                foodBean.setFdiscount(jrr.getJSONObject(i).getInt("fdiscount"));
                                foodBean.setFdescription(jrr.getJSONObject(i).getString("fdescription"));

                                list.add(foodBean);
                            }
                            cbarray = new boolean[list.size()];
                            listView.setAdapter(new FoodListAdapter(MainActivity.this,R.layout.food_list_layout,list,cbarray,selectfid));
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(),response.toString()+"",Toast.LENGTH_LONG
                            ).show();                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        Toast.makeText(getApplicationContext(),anError.toString()+"",Toast.LENGTH_LONG
                        ).show();
                    }
                });


    }

    @Override
    public void onBackPressed() {
        try{

            android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this,android.R.style.Theme_Material_Light_Dialog_NoActionBar);
            builder.setMessage("Are you sure to exit ?");
            builder.setCancelable(true);
            builder.setTitle("Exit");

            builder.setPositiveButton("no", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.cancel();
                }
            });
            builder.setNegativeButton("yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    finish();

                }
            });
            alertDialog = builder.create();
            alertDialog.show();
        }
        catch (Exception e){
            String msg= e.toString();
            Toast.makeText(getApplicationContext(),e.toString(),Toast.LENGTH_LONG).show();
        }
        finally {
            onStart();
        }
    }
}
