package com.example.foodjournal;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String TABLE_NAME = "journal_experiences";
    private static final String COL1 = "id";
    private static final String COL2 = "title";
    private static final String COL3 = "description";
    private static final String COL4 = "date";
    private static final String COL5 = "img";

    private static final String FOOD_TABLE_NAME = "food_details";
    private static final String F_COL1 = "id";
    private static final String F_COL2 = "food";
    private static final String F_COL3 = "expiry_date";
    private static final String F_COL4 = "status";


    private ByteArrayOutputStream byteArrayOutputStream;
    private  byte[] byteArray;

    public DatabaseHelper(Context context) {
        super(context, TABLE_NAME, null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String createTable = "CREATE TABLE "+TABLE_NAME+"(ID INTEGER PRIMARY KEY AUTOINCREMENT, "+COL2+
                " TEXT, "+COL3+" TEXT, "+COL4+" TEXT, "+COL5 +" BLOB);  ";
        sqLiteDatabase.execSQL(createTable);

        String createFoodTable = "CREATE TABLE " + FOOD_TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                F_COL2 + " TEXT, " + F_COL3 + " TEXT, " + F_COL4 +" TEXT)";
        sqLiteDatabase.execSQL(createFoodTable);


    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+FOOD_TABLE_NAME);
        onCreate(sqLiteDatabase);

    }

    public  boolean addData(String title, String desc, String date, Bitmap img){
        SQLiteDatabase db = this.getWritableDatabase();

        byteArrayOutputStream = new ByteArrayOutputStream();
        img.compress(Bitmap.CompressFormat.JPEG, 10, byteArrayOutputStream);
        byteArray = byteArrayOutputStream.toByteArray();


        ContentValues cv = new ContentValues();
        cv.put(COL2, title);
        cv.put(COL3, desc);
        cv.put(COL4, date);
        cv.put(COL5, byteArray);

        long result = db.insert(TABLE_NAME, null, cv);
        if(result==-1){
            return false;
        }
        else{
            db.close();
            return true;
        }

    }


    public ArrayList<Experience> getAll(){
        SQLiteDatabase database = this.getWritableDatabase();
        String query = "SELECT * FROM "+TABLE_NAME+";";
        ArrayList<Experience> new_al = new ArrayList<>();

        Cursor cursor = database.rawQuery(query, null);

        while (cursor.moveToNext()){
            String title = cursor.getString(1);
            String date = cursor.getString(3);
            String desc = cursor.getString(2);
            byte[] imgByteArray = cursor.getBlob(4);
            Bitmap img = BitmapFactory.decodeByteArray(imgByteArray, 0, imgByteArray.length);
            new_al.add(new Experience(title, desc, date, img));
        }




        return new_al;
    }

    public boolean addFoodDetails(String foodName, String expiryDate){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues cv = new ContentValues();
        String status = "active";
        cv.put(F_COL2, foodName);
        cv.put(F_COL3, expiryDate);
        cv.put(F_COL4, status);

        long result = db.insert(FOOD_TABLE_NAME, null, cv);
        return result != -1;


    }

    public ArrayList<HashMap<String, String>> getFoodDetails(){
        ArrayList<HashMap<String, String>> al = new ArrayList<>();


        return al;
    }

    public ArrayList<String> getExpiryFoods(){
        ArrayList<String> expiry_al = new ArrayList<>();
        SQLiteDatabase database = this.getReadableDatabase();
        String curr_date = new SimpleDateFormat("dd/MM/YYYY", Locale.getDefault()).format(new Date());

        String query = "SELECT food from "+FOOD_TABLE_NAME+" where expiry_date= '"+curr_date+"'";
        Cursor cursor = database.rawQuery(query, null);

        while(cursor.moveToNext()){
            expiry_al.add(cursor.getString(0));
        }


        return expiry_al;

    }
    public ArrayList<HashMap<String, String>> getAvailableFoods(){
        ArrayList <HashMap<String, String>> al = new ArrayList<>();
        SQLiteDatabase database = this.getReadableDatabase();
        String query = "SELECT food,expiry_date from "+FOOD_TABLE_NAME+" where status='active'";
        Cursor cursor = database.rawQuery(query, null);
        HashMap<String, String> hm;

        while(cursor.moveToNext()){
            hm = new HashMap<>();
            hm.put("food",cursor.getString(0) );
            hm.put("exp", cursor.getString(1));
            al.add(hm);
        }


        return al;
    }

    public void markAsEaten(String foodName){
        SQLiteDatabase database = this.getWritableDatabase();
        String query= "DELETE FROM "+FOOD_TABLE_NAME+" WHERE food='"+foodName+"'";
        Cursor cursor = database.rawQuery(query, null);

    }




}
