package com.wifi.yilong.yilongwifi.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.wifi.yilong.yilongwifi.Model.User;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2017/1/23.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String FILE_NAME = "YiLongWiFiDataBase";
    private static final int DATABASE_VERSION = 2;
    private static final String TABLE_USER = "users";
    private static final String KEY_ID = "id";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_NAME = "name";
    private static final String KEY_EXPIRATION = "expiration";
    private static final String KEY_TOKEN = "token";

    //create table statements
    private String CREATE_USER_TABLE = "CREATE TABLE " + TABLE_USER + "(" + KEY_ID +
            " CHARACTER(32) PRIMARY KEY , " + KEY_NAME + " VARCHAR(64),"
            + KEY_EMAIL + " VARCHAR(128)," + KEY_TOKEN + " TEXT,"+ KEY_EXPIRATION + " INTEGER" + ")";
    private String DELETE_USER_TABLE = "DROP TABLE IF EXISTS " + TABLE_USER;

    public static DatabaseHelper dbHelper;
    public static DatabaseHelper get(Context c){
        if(dbHelper == null){
            dbHelper = new DatabaseHelper(c);
        }

        return dbHelper;
    }


    public DatabaseHelper(Context context){
        super(context , FILE_NAME , null , DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_USER_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DELETE_USER_TABLE);
        onCreate(db);
    }

    public void addUser(User user){
        SQLiteDatabase db = this.getWritableDatabase();

        //verify if user exists;
        if(IsUserExist(user.getId())){
            updateUser(user);
            return;
        }

        ContentValues vs = new ContentValues();
        vs.put(KEY_ID , user.getId());
        vs.put(KEY_NAME , user.getName());
        vs.put(KEY_EMAIL , user.getEmail());
        vs.put(KEY_EXPIRATION , user.getExpiration().getTime());
        vs.put(KEY_TOKEN , user.getToken());

        db.insert(TABLE_USER,null,vs);
        db.close();

    }

    public boolean IsUserExist(String id) {
        boolean isExist = false;
        SQLiteDatabase db = this.getReadableDatabase();
        String Query = "Select * from " + TABLE_USER + " where " + KEY_ID + " = " + "\'" + id + "\'";
        Cursor cursor = db.rawQuery(Query, null);
        if (cursor.getCount() <= 0) {
            cursor.close();
            return false;
        }
        isExist = cursor.getCount() > 0 ;
        cursor.close();
        return isExist;
    }

    public User getUser(String id){
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_USER , new String[]{
                KEY_ID,KEY_NAME,KEY_EMAIL,KEY_EXPIRATION,KEY_TOKEN
        } , KEY_ID + "=?" , new String[]{String.valueOf(id)} , null,null,null,null);

        if(cursor != null){
            cursor.moveToFirst();
        }
        else{
            return null;
        }
        User user = new User(
                cursor.getString(0) ,
                cursor.getString(1) ,
                cursor.getString(2) ,
                new Date(Integer.parseInt(cursor.getString(3))) ,
                cursor.getString(4));

        return  user;
    }

    public List<User> getAllUsers(){
        List<User> users = new ArrayList<User>();
        String query = "SELECT * FROM " + TABLE_USER;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query , null);

        if(cursor.moveToFirst()){
            do{
                User user = new User(
                        cursor.getString(0) ,
                        cursor.getString(1) ,
                        cursor.getString(2) ,
                        new Date(Integer.parseInt(cursor.getString(3))) ,
                        cursor.getString(4));
                users.add(user);

            }while(cursor.moveToNext());
        }

        return users;
    }

    public Cursor getAllUsersWithCursor(){
        List<User> users = new ArrayList<User>();
        String query = "SELECT * FROM " + TABLE_USER;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query , null);

        return cursor;
    }

    public int updateUser(User user){

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues vs = new ContentValues();
        vs.put(KEY_EXPIRATION , user.getExpiration().getTime());
        vs.put(KEY_TOKEN , user.getToken());

        return db.update(TABLE_USER , vs , KEY_ID + " = ?" , new String[]{String.valueOf(user.getId())});
    }

    public void deleteUser(User user){

        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_USER , KEY_ID + " = ?" , new String[]{String.valueOf(user.getId())});
        db.close();;
    }
}
