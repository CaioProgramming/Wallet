package com.myself.wallet.Database;

public class ScriptDLL {
    public static String getCreateTableCliente(){

        StringBuilder sql = new StringBuilder();

        sql.append("CREATE TABLE IF NOT EXISTS USUARIO( ");
        sql.append(     "CODIGO INTEGER  PRIMARY KEY AUTOINCREMENT NOT NULL,");
        sql.append(     "NOME VARCHAR(200) NOT NULL DEFAULT '', ");
        sql.append(     "SENHA VARCHAR(200) NOT NULL DEFAULT '') ");


        return  sql.toString();

    }


    public static String getCreateTableCarteira() {
        StringBuilder sql = new StringBuilder();

        sql.append("CREATE TABLE IF NOT EXISTS Carteira( ");
        sql.append(     "_id INTEGER  PRIMARY KEY AUTOINCREMENT NOT NULL,");
        sql.append(     "DINHEIRO DOUBLE(5,2) DEFAULT 0)");

        return sql.toString();
    }

    public static String getCreateTableDeposito(){
        StringBuilder sql = new StringBuilder();
        sql.append("CREATE TABLE IF NOT EXISTS Depositos(_id INTEGER PRIMARY KEY AUTO_INCREMENT NOT NULL," +
                "DINHEIRO DOUBLE(5,2),_idCarteira INTEGER NOT NULL," +
                "FOREIGN KEY(_idCarteira) REFERENCES Carteira(_id),DIA DATETIME default (datetime('now','localtime')))");

        return sql.toString();


    }

    public static String getCreateViewTotal(){
        StringBuilder sql = new StringBuilder();
        sql.append("CREATE VIEW IF NOT EXISTS Carteira_view(id_carteira,total_dinheiro) " +
                " AS " +
                "SELECT " +
                "C.id_pedido, sum(D.DINHEIRO + C.DINHEIRO)," +
                "FROM  Carteira C, Depositos D where D._id = C.id_pedido" +
                "group by C._id");
        return sql.toString();


    }

    public static String getCreateTableGastos() {
        StringBuilder sql = new StringBuilder();

        sql.append("CREATE TABLE IF NOT EXISTS Gastos( ");
        sql.append(     "_id INTEGER  PRIMARY KEY AUTOINCREMENT NOT NULL,");
        sql.append(     "DINHEIRO DOUBLE(5,2) DEFAULT 0 NOT NULL," +
                "ITEM VARCHAR(200) NOT NULL, DIA DATETIME default (datetime('now','localtime'))) ");
        return sql.toString();
    }

 }
