package com.example.bod_members;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.core.app.ActivityCompat;
import com.example.bod_members.Bod.B_login;
import com.example.bod_members.Bod.BODLists;
import com.example.bod_members.Bod.MemberDatabase;


import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class MainActivity extends AppCompatActivity {
    EditText edt_add_name, edt_add_developer,edt_add_position ,edt_add_year,edt_add_phone;
    Button btnChoose, btnAdd, btnList;
    ImageView imageView;



    final int REQUEST_CODE_GALLERY = 999;

    public static MemberDatabase memberDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        edt_add_name = (EditText) findViewById(R.id.edt_add_name);
        edt_add_developer=findViewById(R.id.edt_add_developer);
       edt_add_phone = (EditText)findViewById(R.id.edt_add_phone);
       edt_add_position=findViewById(R.id.edt_add_position);
       edt_add_year=findViewById(R.id.edt_add_year);
        btnAdd = (Button) findViewById(R.id.btnAdd);
        btnList = (Button)findViewById(R.id.btnList);
        imageView = (ImageView)findViewById(R.id.imageView);
        this.setTitle(B_login.naming);

        memberDatabase= new MemberDatabase(getApplicationContext(), "Memory.sqlite", null, 2);

        memberDatabase.QueryInfo("CREATE TABLE IF NOT EXISTS MEMORY(id INTEGER PRIMARY KEY AUTOINCREMENT, naming VARCHAR, developer VARCHAR, photo BLOB,position VARCHAR,year VARCHAR,contact VARCHAR )");
       imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActivityCompat.requestPermissions(
                      MainActivity.this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        REQUEST_CODE_GALLERY
                );
                Toast.makeText(getApplicationContext(),"go gallery",Toast.LENGTH_SHORT).show();

            }
        });
       try{
           btnList.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View view) {
                   Intent intent=new Intent(getApplicationContext(), BODLists.class);
                   startActivity(intent);
               }
           });
       }catch (Exception e){
           Log.e("fuck error",e.getMessage().toString());
       }
       btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    memberDatabase.insertInfo(
                            edt_add_name.getText().toString().trim(),
                            edt_add_developer.getText().toString().trim(),
                            imageViewToByte(imageView),
                            edt_add_position.getText().toString().trim(),
                            edt_add_year.getText().toString().trim(),
                            edt_add_phone.getText().toString().trim()
                             );
          Toast.makeText(getApplicationContext(),"Added Successfully",Toast.LENGTH_SHORT).show();
                    edt_add_name.setText("");
                    edt_add_developer.setText("");
                    imageView.setImageResource(R.drawable.panel_profile);
                    edt_add_position.setText("");
                    edt_add_year.setText("");
                    edt_add_phone.setText(""); 
                }
                catch (Exception e){
                    e.printStackTrace();
                    Log.e("lee error",e.getMessage().toString());
                }

            }
        });

    }
    public static byte[] imageViewToByte(ImageView image) {
        Bitmap bitmap = ((BitmapDrawable)image.getDrawable()).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        return byteArray;
    }
    @Override
    public void onRequestPermissionsResult(int requestCode,  String[] permissions, int[] grantResults) {

        if(requestCode == REQUEST_CODE_GALLERY){
            if(grantResults.length >0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, REQUEST_CODE_GALLERY);
            }
            else {
                Toast.makeText(getApplicationContext(), "You don't have permission to access file location!", Toast.LENGTH_SHORT).show();
            }
            return;
        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if(requestCode == REQUEST_CODE_GALLERY && resultCode == RESULT_OK && data != null){
            Uri uri = data.getData();

            try {
                InputStream inputStream =getContentResolver().openInputStream(uri);

                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                imageView.setImageBitmap(bitmap);

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

        super.onActivityResult(requestCode, resultCode, data);

    }
}
