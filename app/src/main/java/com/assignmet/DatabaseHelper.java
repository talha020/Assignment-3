package com.assignmet;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;



import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "assignmnt_db";

    // User table name
    private static final String TABLE_USER = "user";

    private static final String KEY_ID = "id";
    private static final String NAME = "name";
    private static final String AGE = "age";
    private static final String HOBBIES = "hobbies";
    private static final String GENDER = "gender";
    private static final String DOB = "dob";
    private static final String IMAGE = "image";




    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_USER_TABLE = "CREATE TABLE " + TABLE_USER + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + NAME + " TEXT,"+ AGE + " TEXT,"
                + GENDER + " TEXT," + HOBBIES + " TEXT,"+ DOB + " TEXT," + IMAGE + " TEXT" + ")";

        db.execSQL(CREATE_USER_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
        onCreate(db);
    }


    public boolean addNewUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(NAME, user.getName());
        values.put(AGE, user.getAge());
        values.put(GENDER, user.getGender());
        values.put(HOBBIES, user.getHobbies());
        values.put(DOB, user.getDOB());
        values.put(IMAGE, user.getImage());
        // inserting this record
        db.insert(TABLE_USER, null, values);
        db.close(); // Closing database connection
      return  true;
    }







   /* public User getUserByEmail(String email_add){
        SQLiteDatabase db = getReadableDatabase();
        String countQuery = "SELECT * FROM "+TABLE_USER + " where "+EMAIL+ "='"+email_add+"'" ;
        Cursor cursor = db.rawQuery(countQuery, null);
        User user= new User();
        if(cursor.getCount()!=0) {
            if (cursor.moveToFirst()) {
                do {
                    user.setFirst_name(cursor.getString(cursor.getColumnIndex(KEY_FIRST_NAME)));
                    user.setLast_name(cursor.getString(cursor.getColumnIndex(KEY_LAST_NAME)));
                    user.setEmail_address(cursor.getString(cursor.getColumnIndex(EMAIL)));
                    user.setPassword(cursor.getString(cursor.getColumnIndex(PASSWORD)));
                    user.setImage(cursor.getString(cursor.getColumnIndex(IMAGE)));

                } while (cursor.moveToNext());
            }
            cursor.close();
        }
        return user;
    }*/
    public void deleteFriend(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_USER, KEY_ID + " = ?", new String[]{String.valueOf(user.getUser_id())});
        db.close();
    }

    public int getContactsCount() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor dataCount = db.rawQuery("select " + KEY_ID + " from " + TABLE_USER, null);

        int count = dataCount.getCount();
        dataCount.close();
        db.close();

        return count;
    }


   /* public boolean insertAllUsers(List<User> users) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        for(int i= 0;i<users.size();i++){

            values.put(KEY_FIRST_NAME, users.get(i).getFirst_name());
            values.put(KEY_LAST_NAME, users.get(i).getLast_name());
            values.put(EMAIL, users.get(i).getEmail_address());
            values.put(PASSWORD, users.get(i).getPassword());
            values.put(IMAGE, users.get(i).getImage());
            db.insert(TABLE_USER, null, values);
        }

        db.close();
        return true;
    }*/



    public List<User> getUsersList(){
        SQLiteDatabase db = getReadableDatabase();
        String countQuery = "SELECT * FROM "+TABLE_USER + " order by "+KEY_ID ;
        Cursor cursor = db.rawQuery(countQuery, null);
        List<User> users = new ArrayList<>();
        if(cursor.getCount()!=0) {
            if (cursor.moveToFirst()) {
                while (!cursor.isAfterLast())
                {
                    User user = new User();
                    user.setUser_id(cursor.getInt(cursor.getColumnIndex(KEY_ID)));
                    user.setName(cursor.getString(cursor.getColumnIndex(NAME)));
                    user.setAge(cursor.getString(cursor.getColumnIndex(AGE)));
                    user.setGender(cursor.getString(cursor.getColumnIndex(GENDER)));
                    user.setHobbies(cursor.getString(cursor.getColumnIndex(HOBBIES)));
                    user.setDOB(cursor.getString(cursor.getColumnIndex(DOB)));
                    user.setImage(cursor.getString(cursor.getColumnIndex(IMAGE)));

                    users.add(user);
                    cursor.moveToNext();
                }
            }
            cursor.close();
        }
        return users;
    }
}