package com.example.firstuiapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import org.w3c.dom.Text;

public class OrderActivity extends Activity {

    int gender;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_layout);

        //dark mode ON/OFF
        final boolean mode = getIntent().getBooleanExtra("mode",true); //dark mode
        if(mode) {
            makeDark();
        }
        else {
            makeLight();
        }

        //Toast msg according to gender
        gender = getIntent().getIntExtra("gender",0);
        toastMsgRadioGroup();

        //initializing TextViews
        TextView thanksTv = findViewById(R.id.thanks_id_tv);
        TextView movieNameTv = findViewById(R.id.movie_dtls_tv);
        TextView theaterNameTv = findViewById(R.id.theater_dtls_tv);
        TextView timeTv = findViewById(R.id.time_dtls_tv);
        TextView dateTv = findViewById(R.id.date_dtls_tv);
        TextView ticketsTv = findViewById(R.id.tickt_dtls_tv);

        //getting data from calling intent
        int ticketCount = getIntent().getIntExtra("tickets",0);
        String firstNameString = getIntent().getStringExtra("first_name");
        String lastNameString = getIntent().getStringExtra("last_name");

        //setting the data to this intent TextViews
        thanksTv.setText(getResources().getString(R.string.thanks)+" "+firstNameString+" "+lastNameString+"!");
        movieNameTv.setText(getIntent().getStringExtra("movie_name"));
        theaterNameTv.setText(getIntent().getStringExtra("theater_name"));
        timeTv.setText(getIntent().getStringExtra("time"));
        dateTv.setText(getIntent().getStringExtra("date"));
        ticketsTv.setText(ticketCount+"");

        //****Buttons listeners****//

        //go to rating intent
        Button rateBtn = findViewById(R.id.rate_btn);
        rateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OrderActivity.this,BillActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP); //back btn will save it
                intent.putExtra("mode",mode); //change dark mode
                startActivity(intent);
            }
        });

        //closes this intent and go back to main intent
        Button backBtn = findViewById(R.id.return_btn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { //returns to original order

                finish();
            }
        });
    }

    public void makeDark() {

        RelativeLayout relativeLayout = findViewById(R.id.order_page_layout);
        relativeLayout.setBackgroundColor(getResources().getColor(R.color.black));

        TextView orderSuccessTv = findViewById(R.id.order_confirmation_tv);
        TextView thanksTv = findViewById(R.id.thanks_id_tv);
        TextView detailsTv = findViewById(R.id.details_id_tv);
        TextView movieTv = findViewById(R.id.movie_id_tv);
        TextView theaterTv = findViewById(R.id.theater_id_tv);
        TextView timeTv = findViewById(R.id.time_id_tv);
        TextView dateTv = findViewById(R.id.date_id_tv);
        TextView ticketsTv = findViewById(R.id.tickts_id_tv);
        TextView haveFunTv = findViewById(R.id.have_fun_id);

        orderSuccessTv.setTextColor(getResources().getColor(R.color.white));
        thanksTv.setTextColor(getResources().getColor(R.color.white));
        detailsTv.setTextColor(getResources().getColor(R.color.white));
        movieTv.setTextColor(getResources().getColor(R.color.white));
        theaterTv.setTextColor(getResources().getColor(R.color.white));
        timeTv.setTextColor(getResources().getColor(R.color.white));
        dateTv.setTextColor(getResources().getColor(R.color.white));
        ticketsTv.setTextColor(getResources().getColor(R.color.white));
        haveFunTv.setTextColor(getResources().getColor(R.color.white));
    }

    public void makeLight() {
        RelativeLayout relativeLayout = findViewById(R.id.order_page_layout);
        relativeLayout.setBackgroundColor(getResources().getColor(R.color.white));

        TextView orderSuccessTv = findViewById(R.id.order_confirmation_tv);
        TextView thanksTv = findViewById(R.id.thanks_id_tv);
        TextView detailsTv = findViewById(R.id.details_id_tv);
        TextView movieTv = findViewById(R.id.movie_id_tv);
        TextView theaterTv = findViewById(R.id.theater_id_tv);
        TextView timeTv = findViewById(R.id.time_id_tv);
        TextView dateTv = findViewById(R.id.date_id_tv);
        TextView ticketsTv = findViewById(R.id.tickts_id_tv);
        TextView haveFunTv = findViewById(R.id.have_fun_id);

        orderSuccessTv.setTextColor(getResources().getColor(R.color.black));
        thanksTv.setTextColor(getResources().getColor(R.color.black));
        detailsTv.setTextColor(getResources().getColor(R.color.black));
        movieTv.setTextColor(getResources().getColor(R.color.black));
        theaterTv.setTextColor(getResources().getColor(R.color.black));
        timeTv.setTextColor(getResources().getColor(R.color.black));
        dateTv.setTextColor(getResources().getColor(R.color.black));
        ticketsTv.setTextColor(getResources().getColor(R.color.black));
        haveFunTv.setTextColor(getResources().getColor(R.color.black));
    }

    //Toast according to gender
    public void toastMsgRadioGroup() {

        if(gender==R.id.male_radio_btn){

            Toast.makeText(OrderActivity.this, R.string.good_job_man, Toast.LENGTH_SHORT).show();
        }
        else if(gender==R.id.female_radio_btn){

            Toast.makeText(OrderActivity.this, R.string.good_job_woman, Toast.LENGTH_SHORT).show();
        }
        else if(gender==R.id.other_radio_btn) {

            Toast.makeText(OrderActivity.this, R.string.good_job_other, Toast.LENGTH_SHORT).show();
        }
    }
}
