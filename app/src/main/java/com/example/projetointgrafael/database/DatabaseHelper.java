package com.example.projetointgrafael.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "rocknrollrentals.db";
    public static final int DATABASE_VERSION = 2;

    public static final String TABLE_USUARIO = "Usuario";
    public static final String COL_ID = "id";
    public static final String COL_NOME = "nome";
    public static final String COL_EMAIL = "email";
    public static final String COL_SENHA = "senha";
    public static final String COL_NASCIMENTO = "nascimento";

    public static final String TABLE_INSTRUMENTOS = "instrumentos";
    public static final String COL_ID_INSTRUMENTO = "id";
    public static final String COL_CATEGORIA = "categoria";
    public static final String COL_MARCA = "marca";
    public static final String COL_PRAZO = "prazo";
    public static final String COL_VALOR = "valor";

    public static final String TABLE_ALUGUEIS = "alugueis";
    public static final String COL_ITEM = "item";
    public static final String COL_ENDERECO = "endereco";

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

        String CREATE_TABLE_INSTRUMENTOS = "CREATE TABLE " + TABLE_INSTRUMENTOS + " (" +
                COL_ID_INSTRUMENTO + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_CATEGORIA + " TEXT NOT NULL, " +
                COL_MARCA + " TEXT NOT NULL, " +
                COL_PRAZO + " INTEGER NOT NULL, " +
                COL_VALOR + " REAL NOT NULL)";
        db.execSQL(CREATE_TABLE_INSTRUMENTOS);

        String CREATE_TABLE_ALUGUEIS = "CREATE TABLE " + TABLE_ALUGUEIS + " (" +
                COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_ITEM + " TEXT NOT NULL, " +
                COL_ENDERECO + " TEXT NOT NULL, " +
                COL_PRAZO + " TEXT NOT NULL, " +
                COL_VALOR + " TEXT NOT NULL)";
        db.execSQL(CREATE_TABLE_ALUGUEIS);

        inserirInstrumentosPadrao(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USUARIO);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_INSTRUMENTOS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ALUGUEIS);
        onCreate(db);
    }

    private void inserirInstrumentosPadrao(SQLiteDatabase db) {
        db.execSQL("INSERT INTO " + TABLE_INSTRUMENTOS + " (categoria, marca, prazo, valor) VALUES " +
                "('Guitarra', 'Fender Stratocaster', 30, 300)," +
                "('Guitarra', 'Gibson Les Paul', 30, 300)," +
                "('Guitarra', 'Ibanez RG', 30, 300)," +
                "('Guitarra', 'Jackson', 30, 300)," +

                "('Amplificador', 'Fender', 30, 200)," +
                "('Amplificador', 'Marshall', 30, 200)," +
                "('Amplificador', 'Peavey', 30, 200)," +
                "('Amplificador', 'Orange', 30, 200)," +

                "('Pedal', 'Boss DS-1', 30, 200)," +
                "('Pedal', 'Fuzz', 30, 200)," +
                "('Pedal', 'Tube Screamer', 30, 200)," +

                "('Pedaleira', 'Zoom G1X Four', 30, 250)," +
                "('Pedaleira', 'Quad Cortex', 30, 250)");
    }
}
