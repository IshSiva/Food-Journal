package com.example.foodjournal;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;

public class FoodDetails extends AppCompatActivity {
    public String curr_date;
    FloatingActionButton addFood;
    ListView lv;
    TextView tv;
    ArrayList<String> al;
    DatabaseHelper databaseHelper;
    Button view_foods;
    FloatingActionButton back_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_details);

        addFood = (FloatingActionButton)findViewById(R.id.add_food);
        lv = (ListView) findViewById(R.id.expiring_foods);
        tv = (TextView)findViewById(R.id.no_foods);
        databaseHelper = new DatabaseHelper(this);
        view_foods = (Button)findViewById(R.id.view_food);
        back_btn = (FloatingActionButton)findViewById(R.id.go_home);

        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(FoodDetails.this, HomeScreen.class);
                startActivity(i);
            }
        });

        view_foods.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(FoodDetails.this, AvailFoods.class);
                startActivity(i);
            }
        });

        al = databaseHelper.getExpiryFoods();

        if(al.size()==0){
            tv.setVisibility(View.VISIBLE);
            lv.setVisibility(View.INVISIBLE);
        }
        else{
            lv.setVisibility(View.VISIBLE);
            ArrayAdapter<String>adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, al);
            lv.setAdapter(adapter);
        }



        addFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(FoodDetails.this, AddingFood.class);
                startActivity(i);
            }
        });




    }
}