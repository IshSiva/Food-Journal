package com.example.foodjournal;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.HashMap;

import androidx.appcompat.app.AppCompatActivity;

public class AvailFoods extends AppCompatActivity {
    TextView tv;
    ListView lv;
    ArrayList<HashMap<String, String>>al;
    DatabaseHelper databaseHelper;
    FloatingActionButton back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_avail_foods);

        databaseHelper = new DatabaseHelper(this);
        tv = (TextView) findViewById(R.id.no_avail_foods);
        lv= (ListView)findViewById(R.id.avail_foods);
        back = (FloatingActionButton)findViewById(R.id.back_home_foods);


        al = databaseHelper.getAvailableFoods();

        if(al.size()==0){
            tv.setVisibility(View.VISIBLE);
        }
        else{
            lv.setVisibility(View.VISIBLE);
            SimpleAdapter adapter = new SimpleAdapter(this, al,R.layout.food_list_layout,
                                                       new String[] {"food", "exp"} ,
                                                       new int[] {R.id.food_list_name, R.id.food_list_date}
                                                    ) ;
            lv.setAdapter(adapter);
        }

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i= new Intent(AvailFoods.this, FoodDetails.class);
                startActivity(i);
            }
        });




    }


}