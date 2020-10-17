package com.example.foodjournal;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class JournalDetails extends AppCompatActivity {

    TextView title, date, description;
    ImageView imgView;
    Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_journal_details);
        bundle = getIntent().getExtras();
        title = (TextView) findViewById(R.id.detail_title_view);
        date = (TextView) findViewById(R.id.detail_calendar_view);
        description = (TextView) findViewById(R.id.detail_desc_view);
        imgView = (ImageView)findViewById(R.id.detail_img_view);

        title.setText(bundle.getString("title"));
        date.setText(bundle.getString("date"));
        description.setText(bundle.getString("desc"));

        byte[] byteImgArray = bundle.getByteArray("img");
        Bitmap img = BitmapFactory.decodeByteArray(byteImgArray, 0, byteImgArray.length);
        imgView.setImageBitmap(img);



    }

    public void goBack(View view) {
        Intent i = new Intent(JournalDetails.this, AllJournals.class);
        startActivity(i);
    }
}