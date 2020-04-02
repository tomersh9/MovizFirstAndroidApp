package com.example.firstuiapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.Calendar;
import java.util.Timer;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, TimePickerDialog.OnTimeSetListener {

    //ImageView & TextView arrays
    int[] imgArray = {R.drawable.jango_best,R.drawable.incep_small,R.drawable.lotrone,R.drawable.lotr2,
            R.drawable.lotr3,R.drawable.spirited,R.drawable.pulp,R.drawable.potter,
            R.drawable.troy_medium,R.drawable.whiplash};

    int[] txtArray = {R.string.django,R.string.inception,R.string.lotr_1,R.string.lotr_2,R.string.lotr_3,
            R.string.spirited_away,R.string.pulp,R.string.potter,R.string.troy,R.string.whiplash};

    static int count = 0; //for the movie select

    //Picking movie views
    TextView movieName;
    ImageView currImg;
    ImageButton next;
    ImageButton prev;
    Spinner stadiumSpinner;

    //Name pick EditText
    EditText firstName;
    EditText lastName;

    // objects for date and time pick
    int dayOfMonth;
    int month;
    int year;
    Calendar calendar;
    DatePickerDialog datePickerDialog;
    Button dateBtn;
    Button timeBtn;
    TextView dateTv;
    TextView timeTv;

    //Final Button instances
    Button finishBtn;
    EditText seatsEt;
    Button billBtn;


    //must for finish order
    RadioGroup radioGroup;
    CheckBox legalCheckBox;
    LinearLayout detailsLayout;

    //********************MAIN_DIALOG********************************//
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //SPLASH SCREEN INTENT


        //ArrayAdapter need params: this, string array in res,default layout of spinner
        stadiumSpinner = findViewById(R.id.theater_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.theater_array,R.layout.custom_spinner); //custom settings
        adapter.setDropDownViewResource(R.layout.custom_spinner_dropdown); //custom settings
        stadiumSpinner.setAdapter(adapter); //connecting the adapter we made
        stadiumSpinner.setOnItemSelectedListener(this); //what happen when you choose option. also implement interface

        //movie pick
        movieName = findViewById(R.id.movie_name_txt_view);
        currImg = findViewById(R.id.movie_img);
        next = findViewById(R.id.next_arrow);
        prev = findViewById(R.id.prev_arrow);

        //date & time pick
        dateBtn = findViewById(R.id.pick_date_btn);
        dateTv = findViewById(R.id.date_txt_view);
        timeBtn = findViewById(R.id.pick_time_btn);
        timeTv = findViewById(R.id.timer_txt_view);

        //name input
        firstName = findViewById(R.id.first_name_edit_text);
        lastName = findViewById(R.id.last_name_edit_text);

        //finish button dynamic actions and layout
        finishBtn = findViewById(R.id.finish_btn);
        seatsEt = findViewById(R.id.seats_edit_text);
        billBtn = findViewById(R.id.bill_btn);

        //gender and legal age pick
        radioGroup = findViewById(R.id.radio_group);
        legalCheckBox = findViewById(R.id.legal_btn);

        billBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String string = firstName.getText().toString();
                Intent intent = new Intent(MainActivity.this,BillActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP); //back btn will save it
                startActivity(intent);
            }
        });

        //place order button - sums up all details
        finishBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //first, check if all fields are filled
                boolean flag = false;
                if(isValid()) {
                    flag = true;
                }
                else return;

                //save input of tickets from user to String
                String numOfSeats = seatsEt.getText().toString();
                int btnCount; //buttons count int

                if(numOfSeats.matches("")) {
                    Toast.makeText(MainActivity.this, R.string.pls_buy_tickets_error, Toast.LENGTH_SHORT).show();
                    return;
                }

                //prevents app from crashing with no input
                if(numOfSeats.matches("[0-9]+")) {
                    btnCount = Integer.parseInt(numOfSeats); //casting input to Integer
                }
                else return; //don't crash

                if(btnCount==1 && !legalCheckBox.isChecked()) {
                    Toast.makeText(MainActivity.this, R.string.legal_2_tickets_toast, Toast.LENGTH_SHORT).show();
                    return;
                }

                if(!flag) { //if user didn't fill a field
                    return;
                }
                else { //Main work layout

                    //shows details of order
                    makeVisible();

                    //applies final details to TextViews that are now visible
                    setFinalDetailsTxtViews();

                    //***Toast according to radio button pick***//
                    toastMsgRadioGroup();

                    //***Create Dynamic Layout and Buttons***//
                    createDynamicBtnsLayout(btnCount);
                }
            }
        });

        //Time Picker Dialog popup when pressing timeBtn
        timeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DialogFragment timePicker = new TimePickerFragment(); //new TimePicker instance
                timePicker.show(getSupportFragmentManager(),"timer picker"); //we implement on MainActivity OnTimeSetListener
            }
        });

        //Date Dialog popup when pressing dateBtn
        dateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                calendar = Calendar.getInstance(); //ref to calendar
                year = calendar.get(calendar.YEAR); //current year
                month = calendar.get(calendar.MONTH); //current month
                dayOfMonth = calendar.get(calendar.DAY_OF_MONTH); //current day

                datePickerDialog = new DatePickerDialog(MainActivity.this,
                        new DatePickerDialog.OnDateSetListener() { //setting date
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                dateTv.setText(dayOfMonth + "/" + (month+1) + "/" + year); //in TextView result
                            }
                        }, year,month,dayOfMonth); //current date

                datePickerDialog.show(); // calendar shows up in screen
            }
        });

        //*****ARROW METHODS*****//
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                count++;
                if(count==txtArray.length) count = 0;

                currImg.setImageResource(imgArray[count]);
                movieName.setText(txtArray[count]);
            }
        });

        prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                count--;
                if(count==-1) count = txtArray.length-1;

                currImg.setImageResource(imgArray[count]);
                movieName.setText(txtArray[count]);
            }
        });

    }

    public boolean isValid(){

        //must fill fields
        if(currImg.getDrawable().getConstantState() == getResources().getDrawable(R.drawable.academy).getConstantState()) {
            Toast.makeText(MainActivity.this, R.string.pls_enter_movie_error, Toast.LENGTH_SHORT).show();
            return false;
        }
        if(dateTv.getText().toString().matches("")) {
            Toast.makeText(MainActivity.this, R.string.pls_enter_date_error, Toast.LENGTH_SHORT).show();
            return false;
        }
        if(timeTv.getText().toString().matches("")) {
            Toast.makeText(MainActivity.this, R.string.pls_enter_time_error, Toast.LENGTH_SHORT).show();
            return false;
        }
        if(firstName.getText().toString().matches("")) {
            Toast.makeText(MainActivity.this, R.string.pls_enter_first_name_error, Toast.LENGTH_SHORT).show();
            return false;
        }
        if(lastName.getText().toString().matches("")) {
            Toast.makeText(MainActivity.this, R.string.pls_enter_last_name_error, Toast.LENGTH_SHORT).show();
            return false;
        }
        if(radioGroup.getCheckedRadioButtonId()==-1) {
            Toast.makeText(MainActivity.this, R.string.pls_select_gender_error, Toast.LENGTH_SHORT).show();
            return false;
        }

        return true; //passed all tests
    }

    //*********************SPINNER******************************************************//
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        String msg = parent.getItemAtPosition(position).toString(); //saving the value of option in a String
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
    //spinner method
    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        //nothing
    }

    //*********************TIME_PICKER************************************//
    //OnTimeSetListener implement must Override this method !!!
    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

        if(hourOfDay<10){
            if(minute<10){
                timeTv.setText("0"+hourOfDay+":0"+minute);
            }
            else timeTv.setText("0"+hourOfDay+":"+minute);
        }
        else if(minute<10){
            timeTv.setText(hourOfDay+":0"+minute);
        }
        else timeTv.setText(hourOfDay + ":" + minute);
    }

    //Toast according to gender
    public void toastMsgRadioGroup() {

        if(radioGroup.getCheckedRadioButtonId()==R.id.male_radio_btn){

            Toast.makeText(MainActivity.this, R.string.good_job_man, Toast.LENGTH_SHORT).show();
        }
        else if(radioGroup.getCheckedRadioButtonId()==R.id.female_radio_btn){

            Toast.makeText(MainActivity.this, R.string.good_job_woman, Toast.LENGTH_SHORT).show();
        }
        else if(radioGroup.getCheckedRadioButtonId()==R.id.other_radio_btn) {

            //the right way to check current drawable
            if(currImg.getDrawable().getConstantState() == getResources().getDrawable(R.drawable.lotr2).getConstantState()) {

                Toast.makeText(MainActivity.this, R.string.sagiv, Toast.LENGTH_SHORT).show();
            }
            else Toast.makeText(MainActivity.this, R.string.good_job_other, Toast.LENGTH_SHORT).show();
        }
    }

    //*********Create Dynamic Layout and Buttons***********//
    public void createDynamicBtnsLayout(final int btnCount) {

        LinearLayout seatsLayout = findViewById(R.id.seats_btns_dynamic_layout); //ref to specific layout
        seatsLayout.removeAllViews(); //remove previous buttons in layout

        for (int i = 0; i < btnCount; i++) { //creating new buttons

            //we are now inner class and want to access outer class
            //so we call the name of the class.this - MainActivity.this
            ImageButton btn = new ImageButton(MainActivity.this); //each new button

            //choosing the Layout according to parent of the Button in the activity_main
            //creating parameters to the Layout itself
            LinearLayout.LayoutParams btnLayoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);

            btnLayoutParams.setMarginStart(20);
            btnLayoutParams.setMarginEnd(20);
            btn.setLayoutParams(btnLayoutParams); //applying parameters to button

            final int currIndex = i+1; //toast for seats
            btn.setLayoutParams(new ViewGroup.LayoutParams(260,260)); //imgBtn size
            btn.setBackground(getResources().getDrawable(R.drawable.seat_png)); //seat png

            btn.setOnClickListener(new View.OnClickListener() { //toast #seat
                @Override
                public void onClick(View v) {
                    Toast.makeText(MainActivity.this, "seat # "+currIndex, Toast.LENGTH_SHORT).show();
                }
            });

            seatsLayout.addView(btn); //adding the button to the layout

        } //END OF LOOP
    }

    //make final layout visible
    public void makeVisible() {
        RelativeLayout billLayout = findViewById(R.id.bill_layout);
        detailsLayout = findViewById(R.id.details_layout);
        detailsLayout.setVisibility(View.VISIBLE);
        billLayout.setVisibility(View.VISIBLE);
    }

    public void setFinalDetailsTxtViews() {
        //setting TextView for the details layout to chain it with strings later
        TextView thanksFinishTv = findViewById(R.id.thanks_id);
        TextView movieFinishTv = findViewById(R.id.movie_detail_txt_view);
        TextView theaterFinishTv = findViewById(R.id.theater_detail_txt_view);
        TextView timeFinishTv = findViewById(R.id.time_detail_txt_view);
        TextView dateFinishTv = findViewById(R.id.date_detail_txt_view);


        //get inputs of strings to chain to textViews
        String firstNameString = firstName.getText().toString();
        String lastNameString = lastName.getText().toString();
        String movieString = movieName.getText().toString();
        String timerString = timeTv.getText().toString();
        String dateString = dateTv.getText().toString();
        String theaterString = stadiumSpinner.getSelectedItem().toString(); //current value of spinner

        //chaining strings
        thanksFinishTv.setText(getResources().getString(R.string.thanks)+" "+firstNameString+" "+lastNameString+"!");
        movieFinishTv.setText(movieString);
        theaterFinishTv.setText(theaterString);
        timeFinishTv.setText(timerString);
        dateFinishTv.setText(dateString);
    }
}
