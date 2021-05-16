package com.example.mobil_projesi;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DB_NAME = "MobilUygulamaDatabase1";
    public static final String TABLE_NAME = "Users";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_USER_NAME = "username";
    public static final String COLUMN_FIRST_NAME = "firstname";
    public static final String COLUMN_LAST_NAME = "LastName";
    public static final String COLUMN_PASSWORD = "Password";
    public static final String COLUMN_EMAIL = "Email";
    public static final String COLUMN_ADDRESS = "Address";
    public static final String COLUMN_USER_ROLE = "UserRole";
    public static final String COLUMN_USER_WALLET ="Wallet";

    /*-----------------------------------------------------ROLES TABLE----------------------------------------------*/


    public static final int DB_VERSION = 2;

    public DatabaseHelper(Context context){
        super(context, DB_NAME , null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_NAME + " ("+COLUMN_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+COLUMN_USER_NAME+" VARCHAR, " +COLUMN_FIRST_NAME+" VARCHAR, " +COLUMN_LAST_NAME+ " VARCHAR, " +COLUMN_PASSWORD+ " VARCHAR,"
                +COLUMN_EMAIL+" VARCHAR, "+COLUMN_ADDRESS+" VARCHAR, "+COLUMN_USER_ROLE+" VARCHAR, "+COLUMN_USER_WALLET+" FLOAT )";
      db.execSQL(query);
      String query2 = "create table Role (ID INTEGER PRIMARY KEY AUTOINCREMENT, UserType VARCHAR)";
      db.execSQL(query2);
      String query3 = "create table UserRoles (RoleId INTEGER , UserId INTEGER, FOREIGN KEY(UserId) REFERENCES Users(id),FOREIGN KEY(RoleId) REFERENCES Role(ID))";
      db.execSQL(query3);


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String query = "DROP TABLE IF EXISTS " + TABLE_NAME;
        db.execSQL(query);
        onCreate(db);
    }


    public boolean addUser(String name,  String firstName , String lastName , String password, String email, String address, String role, float wallet){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_USER_NAME, name);
        values.put(COLUMN_FIRST_NAME , firstName );
        values.put(COLUMN_LAST_NAME, lastName );
        values.put(COLUMN_PASSWORD, password );
        values.put( COLUMN_EMAIL, email);
        values.put(COLUMN_ADDRESS, address);
        values.put(COLUMN_USER_ROLE, role);
        values.put(COLUMN_USER_WALLET, wallet);
        return sqLiteDatabase.insert(TABLE_NAME, null, values) != -1;

    }
    public boolean checkUsername  (String username){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from " +TABLE_NAME+" where " +COLUMN_USER_NAME+" = ? ", new String[] {username});
        if(cursor.getCount() >0){
            return true;
        }
        else{
            return false;
        }
    }
    public boolean checkUsernamePassword(String username, String password){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from " +TABLE_NAME+" where "+COLUMN_USER_NAME+" = ? and "+COLUMN_PASSWORD+" = ? ", new String[] {username,password});
        if(cursor.getCount() > 0){
            return true;
        }
        else{
            return false;
        }
    }
}
