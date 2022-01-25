package ma.example.mobile.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class MySqliteHelper extends SQLiteOpenHelper {
    Context context;
    private static final String nomDb = "position.db";
    private static final int versionDb = 1;
    public static String COLUMN_ID="_id";

    private static final String SQL_CREATE_USER_TABLE =  "CREATE TABLE user (" +
            COLUMN_ID+ " INTEGER PRIMARY KEY AUTOINCREMENT, "+
            "user_name TEXT NOT NULL, "+
            "user_adresse TEXT NOT NULL, "+
            "user_email TEXT NOT NULL); ";

    private static final String SQL_CREATE_SMARTPHONE_TABLE =  "CREATE TABLE smartphone (" +
            COLUMN_ID+ " INTEGER PRIMARY KEY AUTOINCREMENT, "+
            "userId INTEGER NOT NULL, "+
            "phone_name TEXT NOT NULL, "+
            " FOREIGN KEY(userId) REFERENCES user(_id) ); ";

    private static final String SQL_CREATE_POSITION_TABLE =  "CREATE TABLE position (" +
            COLUMN_ID+ " INTEGER PRIMARY KEY AUTOINCREMENT, "+
            "phoneId INTEGER NOT NULL, "+
            "latitude TEXT NOT NULL, "+
            "longitude TEXT NOT NULL, "+
            " FOREIGN KEY(phoneId) REFERENCES smartphone(_id) ); ";


    public MySqliteHelper(@Nullable Context context) {
        super(context, nomDb, null, versionDb);
        this.context=context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_USER_TABLE);
        db.execSQL(SQL_CREATE_SMARTPHONE_TABLE);
        db.execSQL(SQL_CREATE_POSITION_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS user");
        db.execSQL("DROP TABLE IF EXISTS smartphone");
        db.execSQL("DROP TABLE IF EXISTS position");
        onCreate(db);
    }
}
