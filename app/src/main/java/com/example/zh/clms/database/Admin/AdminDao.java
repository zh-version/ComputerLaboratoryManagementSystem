package com.example.zh.clms.database.Admin;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.zh.clms.database.DatabaseOpenHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AdminDao implements AdminService {

    private DatabaseOpenHelper databaseOpenHelper = null;

    public AdminDao(Context context) {
        databaseOpenHelper = new DatabaseOpenHelper(context);
    }

    @Override
    public boolean addAdmin(Admin admin) {
        boolean flag = false;
        SQLiteDatabase database = null;
        try {
            Object[] params = {admin.getUserName(), admin.getPassword()};
            String sql = "insert into admin (userName,password) values (?,?)";
            database = databaseOpenHelper.getWritableDatabase();
            database.execSQL(sql, params);
            flag = true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (database != null) {
                database.close();
            }
        }
        return flag;
    }

    @Override
    public boolean deleteAdmin(Admin admin) {
        boolean flag = false;
        SQLiteDatabase database = null;
        try {
            Object[] params = {admin.getUserName()};
            String sql = "delete from admin where userName=?";
            database = databaseOpenHelper.getWritableDatabase();
            database.execSQL(sql, params);
            flag = true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (database != null) {
                database.close();
            }
        }
        return flag;
    }

    @Override
    public boolean updateAdmin(Admin admin, Object[] params) {
        boolean flag = false;
        SQLiteDatabase database = null;
        try {
            String sql = "update admin set password=? where userName = ?";
            database = databaseOpenHelper.getWritableDatabase();
            database.execSQL(sql, params);
            flag = true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (database != null) {
                database.close();
            }
        }
        return flag;
    }

    @Override
    public Map<String, String> viewAdmin(Admin admin) {
        Map<String, String> map = new HashMap<String, String>();
        SQLiteDatabase database = null;
        try {
            String[] selectionArgs = {admin.getUserName()};
            String sql = "select userName,password from admin where userName = ?";
            database = databaseOpenHelper.getReadableDatabase();
            Cursor cursor = database.rawQuery(sql, selectionArgs);
            // 获得数据库的列的个数
            int colums = cursor.getColumnCount();
            while (cursor.moveToNext()) {
                for (int i = 0; i < colums; i++) {
                    String cols_name = cursor.getColumnName(i);
                    String cols_value = cursor.getString(cursor.getColumnIndex(cols_name));
                    if (cols_name == null) {
                        cols_name = "";
                    }
                    map.put(cols_name, cols_value);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (database != null) {
                database.close();
            }
        }
        return map;
    }

    @Override
    public List<Map<String, String>> listAdminMaps() {
        boolean flag = false;
        List<Map<String, String>> list = new ArrayList<Map<String, String>>();
        String sql = "select userName,password from admin";
        SQLiteDatabase database = null;
        try {
            database = databaseOpenHelper.getReadableDatabase();
            Cursor cursor = database.rawQuery(sql, null);
            int colnum = cursor.getColumnCount();
            while (cursor.moveToNext()) {
                Map<String, String> map = new HashMap<String, String>();
                for (int i = 0; i < colnum; i++) {
                    String cols_name = cursor.getColumnName(i);
                    String cols_values = cursor.getString(cursor.getColumnIndex(cols_name));
                    if (cols_values == null) {
                        cols_values = "";
                    }
                    map.put(cols_name, cols_values);
                }
                list.add(map);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (database != null) {
                database.close();
            }
        }
        return list;
    }
}

