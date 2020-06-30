package com.example.bod_members.members;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.core.app.ActivityCompat;
import com.example.bod_members.R;


import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class MemberActivity extends AppCompatActivity {
    EditText edt_add_name_for_member, edt_add_year_for_member,edt_add_roll_for_member ,
            edt_add_email_for_member,edt_add_phone_for_member;
    Button btnChoose_for_member, btnAdd_for_member, btnList_for_member;
    ImageView imageView_for_member;



    final int REQUEST_CODE_GALLERY = 998;

    public static MemberDatabase1 memberDatabase1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member);
        edt_add_name_for_member = (EditText) findViewById(R.id.edt_add_name_for_member);
        edt_add_year_for_member=findViewById(R.id.edt_add_year_for_member);
        edt_add_roll_for_member = (EditText)findViewById(R.id.edt_add_roNo_for_member);
        edt_add_phone_for_member=findViewById(R.id.edt_add_phone_for_member);
        edt_add_email_for_member=findViewById(R.id.edt_add_email_for_member);
        btnAdd_for_member = (Button) findViewById(R.id.btnAdd_for_member);
        btnList_for_member = (Button)findViewById(R.id.btnList_for_member);
        imageView_for_member = (ImageView)findViewById(R.id.imageView_for_member);
        this.setTitle(M_login.name);

        memberDatabase1= new MemberDatabase1(getApplicationContext(), "Secondary.sqlite", null, 2);

        memberDatabase1.QueryData("CREATE TABLE IF NOT EXISTS SECONDARY(id INTEGER PRIMARY KEY AUTOINCREMENT, naming VARCHAR, year VARCHAR, image BLOB,roll VARCHAR,phone VARCHAR,email VARCHAR )");
        imageView_for_member.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActivityCompat.requestPermissions(
                        MemberActivity.this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        REQUEST_CODE_GALLERY
                );
                Toast.makeText(getApplicationContext(),"go gallery",Toast.LENGTH_SHORT).show();

            }
        });
        try{
            btnList_for_member.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent=new Intent(getApplicationContext(), MemberList.class);
                    startActivity(intent);
                }
            });
        }catch (Exception e){
            Log.e("fuck error",e.getMessage().toString());
        }
        btnAdd_for_member.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    memberDatabase1.insertData(
                            edt_add_name_for_member.getText().toString().trim(),
                            edt_add_year_for_member.getText().toString().trim(),
                            imageViewToByte(imageView_for_member),
                            edt_add_roll_for_member.getText().toString().trim(),
                            edt_add_phone_for_member.getText().toString().trim(),
                            edt_add_email_for_member.getText().toString().trim()
                    );
                    Toast.makeText(getApplicationContext(),"Added Successfully",Toast.LENGTH_SHORT).show();
                    edt_add_name_for_member.setText("");
                    edt_add_year_for_member.setText("");
                    imageView_for_member.setImageResource(R.drawable.panel_profile);
                    edt_add_roll_for_member.setText("");
                    edt_add_phone_for_member.setText("");
                    edt_add_email_for_member.setText("");
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
                imageView_for_member.setImageBitmap(bitmap);

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

        super.onActivityResult(requestCode, resultCode, data);

    }
}
