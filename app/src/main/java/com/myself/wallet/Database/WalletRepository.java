package com.myself.wallet.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.myself.wallet.Beans.Wallet;

public class WalletRepository {

    private static final String nome_banco = "agenda.db";
    private SQLiteDatabase banco;
    private DadosOpenHelper bancoListaOpenHelper;
    private String tblname = "Gastos";
    public WalletRepository(Context context) {
        bancoListaOpenHelper = new DadosOpenHelper(context);
    }

    public void abrir() throws SQLException {
        banco = bancoListaOpenHelper.getWritableDatabase();
    }

    public void fecha() {
        if (banco != null) banco.close();
    }

    public void inserirgasto(String nome, Double dinheiro){

        ContentValues novoEvento = new ContentValues();

        novoEvento.put("item",nome);
        novoEvento.put("dinheiro", dinheiro);
         abrir();
        banco.insert(tblname,null,novoEvento);
        fecha();
    }

    public void inseridinheiro(Double dinheiro){

        ContentValues novoEvento = new ContentValues();


        novoEvento.put("Dinheiro", dinheiro);
        novoEvento.put("_idCarteira",1);

        abrir();
        banco.insert("Depositos",null,novoEvento);
        fecha();
    }


    public void alteraDinheiro(long id,Double dinheiro){
        ContentValues produtoAlterado = new ContentValues();
        produtoAlterado.put("item",dinheiro);
        abrir();
        banco.update("Carteira",produtoAlterado,"_id = "+ id,null);
        fecha();
    }

    public void zeraDinheiro(long id){
        ContentValues produtoAlterado = new ContentValues();
        produtoAlterado.put("item",0);
        abrir();
        banco.update("Carteira",produtoAlterado,"_id = "+ id,null);
        fecha();
    }


    public void apagarcarteira(long id){
        abrir();
        banco.delete("Carteira", "_id = " +id, null);
        fecha();



    }



    public Cursor obterDepositos(){
        return banco.query("Depositos",null,null,null,null,null,"DIA");
    }

    public Cursor obtergastos(){
        return banco.query(tblname,null,null,null,null,null,"DIA");
    }

    public Cursor obtercarteira(){
        return banco.query("Carteira",null,null,null,null,null,null);
    }



    public Cursor obterUmGasto(long id){
        return banco.query(tblname,null,"_id = "+id,null,null,null,"ITEM");
    }

    public int contarGastos(){
        abrir();
        Cursor mCount= banco.rawQuery("select count(*) from Gastos" , null);
        mCount.moveToFirst();
        int count= mCount.getInt(0);
        mCount.close();
        fecha();
         return count;
    }

    public Wallet criagastos(Cursor resultado){

        Wallet events = new Wallet();
        if(resultado.getCount() == 0){


            return null;
        }

        Double dinheiro= resultado.getDouble(resultado.getColumnIndexOrThrow("DINHEIRO"));
         String item = resultado.getString(resultado.getColumnIndexOrThrow("ITEM"));
        String dia = resultado.getString(resultado.getColumnIndexOrThrow("DIA"));
        int id = resultado.getInt(resultado.getColumnIndexOrThrow("_id"));

        events.setId(id);
        events.setData(dia);
        events.setItem(item);
        events.setDinheiro(dinheiro);


        return events;



    }

    public Wallet criadeposito(Cursor resultado){
        Wallet events = new Wallet();
        if(resultado.getCount() == 0){
            return null;
        }
        Double evento = resultado.getDouble(resultado.getColumnIndexOrThrow("Dinheiro"));
       String eventodata = resultado.getString(resultado.getColumnIndexOrThrow("Dia"));
        events.setDinheiro(evento);
        events.setData(eventodata);
        return events;
    }

    public Wallet criacarteira(){

        Wallet wallet = new Wallet();
        Cursor slct = banco.rawQuery("select * from Carteira_view",null,null);

        Double dinheiro= slct.getDouble(slct.getColumnIndexOrThrow("total_dinheiro"));

        int id = slct.getInt(slct.getColumnIndexOrThrow("_id"));

        wallet.setId(id);
        wallet.setDinheiro(dinheiro);


        return wallet;



    }



}
