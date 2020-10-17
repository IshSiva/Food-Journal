package com.example.foodjournal;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import androidx.appcompat.app.AppCompatActivity;

public class AddingFood extends AppCompatActivity {
    Button addFood;
    DatabaseHelper databaseHelper;
    EditText foodname;
    DatePicker datePicker;
    String exp_date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adding_food);

        databaseHelper = new DatabaseHelper(this);
        addFood = (Button)findViewById(R.id.add_food_btn);
        foodname = (EditText)findViewById(R.id.food_name);
        datePicker= (DatePicker) findViewById(R.id.exp_date);




        addFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean bool_added = false;
                String fname = foodname.getText().toString();

                try {
                    exp_date = process_date();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                if(fname.length()>0) {
                    bool_added = databaseHelper.addFoodDetails(fname, exp_date);
                }
                if(bool_added){
                    Toast.makeText(AddingFood.this , "Successfully inserted", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(AddingFood.this, FoodDetails.class);
                    startActivity(i);
                }
                else{
                    Toast.makeText(AddingFood.this , "Error", Toast.LENGTH_SHORT).show();
                }

            }
        });



    }
    private String process_date() throws ParseException {

        int month = Integer.valueOf(datePicker.getMonth())+1;

        String given_date =datePicker.getDayOfMonth()+"/"+month+"/"+datePicker.getYear();
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        Date date = format.parse(given_date);
        System.out.println(date);

        String format_date = format.format(date);

        return format_date;
    }
}