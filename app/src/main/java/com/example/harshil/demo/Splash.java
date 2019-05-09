package com.example.harshil.demo;

import android.content.Intent;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

public class Splash extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

       final TextView tvname = (TextView) findViewById(R.id.tvtitle);

    //    YoYo.with(Techniques.Bounce).duration(2000).playOn(tvname);

        Intent intent =new Intent(this,MainActivity.class);
        ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(this,tvname,ViewCompat.getTransitionName(tvname));
        startActivity(intent,options.toBundle());



        /* CODE FOR PASSING MORE THAN ONE ELEMENTS

        Pair p1 = Pair.create(tvname,ViewCompat.getTransitionName(tvname));
        Pair p2= Pair.create(tvname,ViewCompat.getTransitionName(tvname));

        ActivityOptionsCompat compat = ActivityOptionsCompat.makeSceneTransitionAnimation(Splash.this,p1,p2);
        startActivity(new Intent(Splash.this,MainActivity.class),compat.toBundle());

        */
    }
}
