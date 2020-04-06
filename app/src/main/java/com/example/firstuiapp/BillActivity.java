package com.example.firstuiapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import org.w3c.dom.Text;

public class BillActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bill_layout);

        //gets dark mode state from previous intent and change accordingly
        boolean mode = getIntent().getBooleanExtra("mode",true);

        if(mode) {
            RelativeLayout relativeLayout = findViewById(R.id.main_bill_layout);
            relativeLayout.setBackgroundColor(getResources().getColor(R.color.black));
        }
        else {
            RelativeLayout relativeLayout = findViewById(R.id.main_bill_layout);
            relativeLayout.setBackgroundColor(getResources().getColor(R.color.white));
        }

        final ImageButton star1 = findViewById(R.id.star_1);
        final ImageButton star2 = findViewById(R.id.star_2);
        final ImageButton star3 = findViewById(R.id.star_3);
        final ImageButton star4 = findViewById(R.id.star_4);
        final ImageButton star5 = findViewById(R.id.star_5);

        //**********Rating Click Listeners*************//
        star1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                star1.setImageResource(R.drawable.classic_star);
                star2.setImageResource(R.drawable.empty_star);
                star3.setImageResource(R.drawable.empty_star);
                star4.setImageResource(R.drawable.empty_star);
                star5.setImageResource(R.drawable.empty_star);
                Toast.makeText(BillActivity.this, R.string.thanks, Toast.LENGTH_SHORT).show();
            }
        });
        star2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                star1.setImageResource(R.drawable.classic_star);
                star2.setImageResource(R.drawable.classic_star);
                star3.setImageResource(R.drawable.empty_star);
                star4.setImageResource(R.drawable.empty_star);
                star5.setImageResource(R.drawable.empty_star);
                Toast.makeText(BillActivity.this, R.string.thanks, Toast.LENGTH_SHORT).show();
            }
        });
        star3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                star1.setImageResource(R.drawable.classic_star);
                star2.setImageResource(R.drawable.classic_star);
                star3.setImageResource(R.drawable.classic_star);
                star4.setImageResource(R.drawable.empty_star);
                star5.setImageResource(R.drawable.empty_star);
                Toast.makeText(BillActivity.this, R.string.thanks, Toast.LENGTH_SHORT).show();
            }
        });
        star4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                star1.setImageResource(R.drawable.classic_star);
                star2.setImageResource(R.drawable.classic_star);
                star3.setImageResource(R.drawable.classic_star);
                star4.setImageResource(R.drawable.classic_star);
                star5.setImageResource(R.drawable.empty_star);
                Toast.makeText(BillActivity.this, R.string.thanks, Toast.LENGTH_SHORT).show();
            }
        });
        star5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                star1.setImageResource(R.drawable.classic_star);
                star2.setImageResource(R.drawable.classic_star);
                star3.setImageResource(R.drawable.classic_star);
                star4.setImageResource(R.drawable.classic_star);
                star5.setImageResource(R.drawable.classic_star);
                Toast.makeText(BillActivity.this, R.string.thanks, Toast.LENGTH_SHORT).show();
            }
        });

        Button backBtn = findViewById(R.id.back_btn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //close current intent to "save" the inputs of the mainActivity
                finish();
            }
        });
    }
}



