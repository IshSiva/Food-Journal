package com.example.foodjournal;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    DatePicker date_picker;
    DatabaseHelper databaseHelper;
    ImageView uploaded_img;

    private static final int  PICK_IMG_REQUEST = 100;
    private Uri imgFilePath;
    private Bitmap upl_img;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final EditText title, description;
        Button submit_exp;

        title = (EditText)findViewById(R.id.title);
        description = (EditText)findViewById(R.id.description);
        submit_exp = (Button)findViewById(R.id.submit_experience);
        databaseHelper = new DatabaseHelper(this);
        date_picker = (DatePicker)findViewById(R.id.exp_date);
        uploaded_img = (ImageView) findViewById(R.id.upload_img);

        submit_exp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String inp_desc = description.getText().toString();
                String inp_title = title.getText().toString();
                String inp_date = process_date();

                addData(inp_title, inp_desc, inp_date, upl_img);
                Toast.makeText(MainActivity.this, "Successfully written", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(MainActivity.this, HomeScreen.class);
                startActivity(i);

            }
        });

    }
    public void chooseImage(View view) {
        try{
            Intent img_intent = new Intent();
            img_intent.setType("image/*");

            img_intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(img_intent, PICK_IMG_REQUEST);

        }catch (Exception e){
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        try{
            super.onActivityResult(requestCode, resultCode, data);
            if(requestCode==PICK_IMG_REQUEST && resultCode==RESULT_OK && data!=null && data.getData()!=null){
                imgFilePath = data.getData();
                upl_img = MediaStore.Images.Media.getBitmap(getContentResolver(), imgFilePath);
                uploaded_img.setImageBitmap(upl_img);



            }

        }catch (Exception e){
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }

    public void addData(String title, String desc, String date, Bitmap img){
        boolean insertData = false;
        if(uploaded_img.getDrawable()!= null && img!=null){
            insertData = databaseHelper.addData(title, desc, date, img);
        }
        else{
            Toast.makeText(this , "All fields not filled", Toast.LENGTH_SHORT).show();
        }

        if(insertData){
            Toast.makeText(this , "Successfully inserted", Toast.LENGTH_SHORT).show();
        }
    }

    private String process_date() {
        return date_picker.getDayOfMonth()+"/"+date_picker.getMonth()+"/"+date_picker.getYear();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}