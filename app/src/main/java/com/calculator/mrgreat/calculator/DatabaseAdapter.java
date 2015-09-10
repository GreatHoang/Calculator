package com.calculator.mrgreat.calculator;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Mr.Great on 9/6/2015.
 */
public class DatabaseAdapter {
    public static final String ID = "id";
    public static final String EXRESSION = "expression";
    public static final String RESULT = "result";
    public static final String BASE = "base";

    public static final String DATABASE_NAME = "ExpressionDB";
    public static final String DATABASE_TABLE = "tblExpression";
    public static final int DATABASE_VERSION = 2;

    static final String DATABASE_CREATE = "CREATE TABLE "
            + DATABASE_TABLE
            + "("
            + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + EXRESSION + " TEXT NOT NULL, "
            + RESULT + " TEXT NOT NULL, "
            + BASE + " INTEGER NOT NULL"
            + ");";

    final Context context;

    DatabaseHelper DBHelper;
    SQLiteDatabase db;

    /*private static DatabaseAdapter dbCreate = null;

    public DatabaseAdapter() {
    }

    public static DatabaseAdapter databaseCreate() {
        if (dbCreate == null) {
            dbCreate = new DatabaseAdapter();

        }
        return dbCreate;

    }*/

    public DatabaseAdapter(Context cxt) {
        this.context = cxt;
        DBHelper = new DatabaseHelper(context);

    }

    public DatabaseAdapter open() throws SQLException {
        db = DBHelper.getWritableDatabase();
        return this;

    }

    public void close() {
        DBHelper.close();

    }

    public synchronized long insertExpression(String expression, String result, int base) {
        ContentValues initialValues = new ContentValues();
        initialValues.put(EXRESSION, expression);
        initialValues.put(RESULT, result);
        initialValues.put(BASE, base);
        return db.insert(DATABASE_TABLE, null, initialValues);

    }

    public synchronized Cursor getAllExpression() {
        return db.query(DATABASE_TABLE, new String[]{ID, EXRESSION, RESULT, BASE}, null, null, null, null, null);

    }

    public synchronized void deleteAllExpression() {
        if (db.delete(DATABASE_TABLE, null, null) != 1) {
            Log.d("delete", "ERROR");

        }

    }

    public synchronized int deleteByID(int id){
        return db.delete(DATABASE_TABLE, ID + "=" + id, null);

    }

    private static class DatabaseHelper extends SQLiteOpenHelper {

        DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);

        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            try {
                db.execSQL(DATABASE_CREATE);

            } catch (SQLException e) {
                e.printStackTrace();

            }

        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE);
            onCreate(db);

        }
    }

}
