package ma.example.mobile.database;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;

import java.util.ArrayList;

import ma.example.mobile.models.PositionModel;
import ma.example.mobile.models.SmartphoneModel;
import ma.example.mobile.models.UserModel;

public class dbhelper {
    private SQLiteDatabase db;
    private MySqliteHelper dbHelper;
    Context context;
    public dbhelper(Context context) {
        dbHelper = new MySqliteHelper(context);
        open();
        this.context=context;
    }
    public void open() throws SQLException
    {
        db = dbHelper.getWritableDatabase();
    }
    public void close() {
        dbHelper.close();
    }

    //insert
    public long Insert (ContentValues values, String nameTable) {
        long ID= db.insert(nameTable,null,values);
        return ID;
    }


    public Cursor query  (String[] projection, String selection, String[] selectionArgs, String nameTable) {

        SQLiteQueryBuilder qb= new SQLiteQueryBuilder();
        qb.setTables(nameTable);

        Cursor cursor=qb.query(db,projection,selection,selectionArgs,null,null,null);

        return cursor;

    }


    @SuppressLint("Range")
    public ArrayList<UserModel> getAllUsers() {

        ArrayList<UserModel> mDataList= new ArrayList<>();
        Cursor cursor=null;
        cursor=query(null,null,   null,"user");

        if(cursor.moveToFirst()){

            do {

                UserModel user= new UserModel();
                user.setUser_id(cursor.getInt(cursor.getColumnIndex("_id")));
                user.setUser_email(cursor.getString(cursor.getColumnIndex("user_email")));
                user.setUser_adresse(cursor.getString(cursor.getColumnIndex("user_adresse")));
                user.setUser_name(cursor.getString(cursor.getColumnIndex("user_name")));

                mDataList.add(user);

            }

            while(cursor.moveToNext()); }
        return mDataList;

    }

    @SuppressLint("Range")
    public ArrayList<SmartphoneModel> getAllSmartphoneByUserId(int User_id) {

        ArrayList<SmartphoneModel> mDataList= new ArrayList<>();
        Cursor cursor=null;
        cursor=query(null,"userId"+" = "+"?",   new String[] { String.valueOf(User_id) },"smartphone");

        if(cursor.moveToFirst()){

            do {

                SmartphoneModel smartphoneModel= new SmartphoneModel();
                smartphoneModel.setPhone_id(cursor.getInt(cursor.getColumnIndex("_id")));
                smartphoneModel.setUser_id(cursor.getInt(cursor.getColumnIndex("userId")));
                smartphoneModel.setPhone_name(cursor.getString(cursor.getColumnIndex("phone_name")));


                mDataList.add(smartphoneModel);

            }

            while(cursor.moveToNext()); }
        return mDataList;

    }

    @SuppressLint("Range")
    public ArrayList<PositionModel> getAllPositionsBySmartphone(int smartphone_id) {

        ArrayList<PositionModel> mDataList= new ArrayList<>();
        Cursor cursor=null;

        cursor=query(null,"phoneId"+" = "+"?",   new String[] { String.valueOf(smartphone_id) },"position");

        if(cursor.moveToFirst()){

            do {

                PositionModel positionModel= new PositionModel();

                positionModel.setPhone_id(cursor.getInt(cursor.getColumnIndex("_id")));
                positionModel.setPhone_id(cursor.getInt(cursor.getColumnIndex("phoneId")));
                positionModel.setLatitude(cursor.getString(cursor.getColumnIndex("latitude")));
                positionModel.setLongitude(cursor.getString(cursor.getColumnIndex("longitude")));


                mDataList.add(positionModel);

            }

            while(cursor.moveToNext()); }
        return mDataList;

    }


    public void deleteUser(int userId) {
        db.delete("user",
                "_id" + " = ?",
                new String[]{String.valueOf(userId)});
    }
    public void deleteSmartphone( int phoneId) {
        db.delete("smartphone",
                "_id" + " = ?",
                new String[]{String.valueOf(phoneId)});
    }
    public void deletePosition(int positionId) {
        db.delete("position",
                "_id" + " = ?",
                new String[]{String.valueOf(positionId)});
    }



}
