package com.example.foodjournal;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class AllJournals extends AppCompatActivity {
    ListView journals_lv;
    HashMap<String, String>indiv_hm;
    ArrayList<Experience> arrayList;
    RecyclerView recyclerView;
    RVAdapter rv;

    Bundle bundle = new Bundle();
    ByteArrayOutputStream byteArrayOutputStream;
    byte[] byteArray;

    DatabaseHelper databaseHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_journals);
        recyclerView = (RecyclerView)findViewById(R.id.recycler_view);

        databaseHelper = new DatabaseHelper(this);

        arrayList = databaseHelper.getAll();

        rv = new RVAdapter(arrayList);
        recyclerView.setHasFixedSize(true);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        rv.setOnItemClickListener(new RVAdapter.onItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Intent i = new Intent(AllJournals.this, JournalDetails.class);
                Experience curr_clicked = arrayList.get(position);



                byteArrayOutputStream = new ByteArrayOutputStream();
                curr_clicked.getImg().compress(Bitmap.CompressFormat.JPEG, 10, byteArrayOutputStream);
                byteArray = byteArrayOutputStream.toByteArray();



                bundle.putString("title", curr_clicked.getTitle());
                bundle.putString("date", curr_clicked.getDate());
                bundle.putString("desc", curr_clicked.getDescription());
                bundle.putByteArray("img", byteArray);

                i.putExtras(bundle);
                startActivity(i);

            }
        });


        recyclerView.setAdapter(rv);





    }


    public void goBackToJournalHome(View view) {
        Intent i = new Intent(AllJournals.this, HomeScreen.class);
        startActivity(i);
    }
}