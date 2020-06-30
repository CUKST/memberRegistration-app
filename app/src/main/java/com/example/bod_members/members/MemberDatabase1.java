package com.example.bod_members.members;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;
import androidx.annotation.Nullable;

public class MemberDatabase1 extends SQLiteOpenHelper {
   public MemberDatabase1(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, null, 2);
    }
    public void QueryData(String sql){
        SQLiteDatabase sqLiteDatabase=getReadableDatabase();
        sqLiteDatabase.execSQL(sql);


    }
     public void insertData(String naming ,String year,byte[] image,String
                            roll,String phone,String email){
        SQLiteDatabase database=this.getWritableDatabase();
        String sql="INSERT INTO SECONDARY VALUES (NULL,?,?,?,?,?,?)";
         SQLiteStatement sqLiteStatement=database.compileStatement(sql);
         sqLiteStatement.clearBindings();
         sqLiteStatement.bindString(1,naming);
         sqLiteStatement.bindString(2,year);
         sqLiteStatement.bindBlob(3,image);
         sqLiteStatement.bindString(4,roll);
         sqLiteStatement.bindString(5,phone);
         sqLiteStatement.bindString(6,email);
         sqLiteStatement.executeInsert();

     }
     public Cursor getData(String sql){
        SQLiteDatabase database=this.getReadableDatabase();
        return database.rawQuery(sql,null);
     }
    public void  deletData(int id){
        SQLiteDatabase database=this.getWritableDatabase();
        String sql="DELETE FROM SECONDARY WHERE id =?";
        SQLiteStatement statement=database.compileStatement(sql);
        statement.clearBindings();
        statement.bindDouble(1,(double)id);
        statement.execute();
        database.close();
    }
     public void updateData(String naming,String year,byte[] image,
                            int id){
        SQLiteDatabase database=this.getWritableDatabase();
        try{
            String sql="UPDATE SECONDARY SET year =?,roll =?,image =? WHERE id=?";
            SQLiteStatement statement=database.compileStatement(sql);
            statement.bindString(1,naming);
            statement.bindString(2,year);
            statement.bindBlob(3,image);
            statement.bindDouble(4,(double)id);
            statement.execute();
             database.close();
        }catch (Exception e){
            Log.e("update column error",e.getMessage().toString());
        }

     }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
    }
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int i1) {
    }
}
