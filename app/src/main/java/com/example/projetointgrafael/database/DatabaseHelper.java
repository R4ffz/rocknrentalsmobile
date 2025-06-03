package com.example.projetointgrafael.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "rocknrollrentals.db";
    public static final int DATABASE_VERSION = 1;

    public static final String TABLE_USUARIO = "Usuario";

    public static final String COL_ID = "id";
    public static final String COL_NOME = "nome";
    public static final String COL_EMAIL = "email";
    public static final String COL_SENHA = "senha";
    public static final String COL_NASCIMENTO = "nascimento";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE_USUARIO = "CREATE TABLE " + TABLE_USUARIO + " (" +
                COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_NOME + " TEXT NOT NULL, " +
                COL_EMAIL + " TEXT NOT NULL UNIQUE, " +
                COL_SENHA + " TEXT NOT NULL, " +
                COL_NASCIMENTO + " TEXT NOT NULL)";
        db.execSQL(CREATE_TABLE_USUARIO);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USUARIO);
        onCreate(db);
    }
}
