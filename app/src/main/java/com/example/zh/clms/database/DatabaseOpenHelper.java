package com.example.zh.clms.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseOpenHelper extends SQLiteOpenHelper {

    //数据库名
    private static String DatabaseName = "mydb.db";
    //版本号
    private static int version = 1;

    public DatabaseOpenHelper(Context context) {
        super(context, DatabaseName, null, version);

    }

    // 当数据库创建的时候，是第一次被执行，完成对数据库的表的创建
    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "create table stu(id integer primary key autoincrement,userName varchar" +
                "" + "(64),password varchar(64),realName varchar(64),gradeClass varchar(64))";
        db.execSQL(sql);

        String sql_tea = "create table tea(id integer primary key autoincrement,userName varchar"
                + "" + "(64),password varchar(64),realName varchar(64),phoneNumber varchar(64)," +
                "roomNum varchar(64))";
        db.execSQL(sql_tea);

        String sql_admin = "create table admin(id integer primary key autoincrement,userName " +
                "varchar" + "" + "(64),password varchar(64))";
        db.execSQL(sql_admin);

//        String sql_stu_Lab_Apply = "create table stu_lab_apply(id integer primary key " +
//                "autoincrement," + "name " + "varchar" + "" + "(64),phone varchar(64),
// roomNumber " +
//                "varchar(64)," + "startTime " + "varchar" + "(64)," + "endTime " +
//                "varchar" + "" + "(64),Tag varchar(64))";
//        db.execSQL(sql_stu_Lab_Apply);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
