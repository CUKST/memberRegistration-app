package com.example.bod_members.Bod;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;
import androidx.annotation.Nullable;
public class MemberDatabase extends SQLiteOpenHelper {
   public MemberDatabase(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, null, 2);
    }
    public void QueryInfo(String sql){
        SQLiteDatabase sqLiteDatabase=getReadableDatabase();
        sqLiteDatabase.execSQL(sql);


    }
     public void insertInfo(String naming ,String developer,byte[] photo,String
                            position,String year,String contact){
        SQLiteDatabase database=this.getWritableDatabase();
        String sql="INSERT INTO MEMORY VALUES (NULL,?,?,?,?,?,?)";
         SQLiteStatement sqLiteStatement=database.compileStatement(sql);
         sqLiteStatement.clearBindings();
         sqLiteStatement.bindString(1,naming);
         sqLiteStatement.bindString(2,developer);
         sqLiteStatement.bindBlob(3,photo);
         sqLiteStatement.bindString(4,position);
         sqLiteStatement.bindString(5,year);
         sqLiteStatement.bindString(6,contact);
         sqLiteStatement.executeInsert();

     }
     public Cursor getInfo(String sql){
        SQLiteDatabase database=this.getReadableDatabase();
        return database.rawQuery(sql,null);
     }
     public void  deletInfo(int id){
        SQLiteDatabase database=this.getWritableDatabase();
        String sql="DELETE FROM MEMORY WHERE id =?";
        SQLiteStatement statement=database.compileStatement(sql);
        statement.clearBindings();
        statement.bindDouble(1,(double)id);
        statement.execute();
        database.close();
     }
     public void updateInfo(String naming,String developer,byte[] photo,
                            int id){
        SQLiteDatabase database=this.getWritableDatabase();
        try{
            String sql="UPDATE MEMORY SET position =?,year =?,photo =? WHERE id=?";
            SQLiteStatement statement=database.compileStatement(sql);
            statement.bindString(1,naming);
            statement.bindString(2,developer);
            statement.bindBlob(3,photo);
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
