package com.myself.wallet.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DadosOpenHelper extends SQLiteOpenHelper {
    private static int version = 1;
    private static String name = "carteira.db";
    protected SQLiteDatabase database;
    public DadosOpenHelper(Context context ) {
        super(context, name,null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL( ScriptDLL.getCreateTableCliente());
        sqLiteDatabase.execSQL( ScriptDLL.getCreateTableCarteira());
        sqLiteDatabase.execSQL(ScriptDLL.getCreateTableDeposito());

        sqLiteDatabase.execSQL( ScriptDLL.getCreateTableGastos());
        sqLiteDatabase.execSQL(ScriptDLL.getCreateViewTotal());

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
