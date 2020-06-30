package com.example.bod_members.members;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.core.app.ActivityCompat;
import com.example.bod_members.R;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;

import static com.example.bod_members.members.MemberActivity.memberDatabase1;

public class MemberList extends AppCompatActivity {

    GridView gridView_for_member;
    ArrayList<Member> list;
    MemberListAdapter adapter = null;
EditText search;


    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_list);
        search=findViewById(R.id.search);
        gridView_for_member = (GridView) findViewById(R.id.gridView_for_member);
        list = new ArrayList<>();
        adapter = new MemberListAdapter(this, R.layout.member_items, list);
        gridView_for_member.setAdapter(adapter);
        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                filter(editable.toString());

            }
        });
        this.setTitle("");
        // get all data from sqlite
        Cursor cursor = memberDatabase1.getData("SELECT * FROM SECONDARY");
        list.clear();
        while (cursor.moveToNext()) {
            int id = cursor.getInt(0);
            String name = cursor.getString(1);
            String year = cursor.getString(2);
            byte[] image = cursor.getBlob(3);
            String roll=cursor.getString(4);
            String phone=cursor.getString(5);
            String email=cursor.getString(6);


            list.add(new Member(name, year,image,id,roll,phone,email ));
        }
        adapter.notifyDataSetChanged();

        gridView_for_member.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {

                CharSequence[] items = {"Update", "Delete"};
                AlertDialog.Builder dialog = new AlertDialog.Builder(MemberList.this);

                dialog.setTitle("Choose an action");
                dialog.setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int item) {
                        if (item == 0) {
                            // update
                            Cursor c = memberDatabase1.getData("SELECT id FROM SECONDARY");
                            ArrayList<Integer> arrID = new ArrayList<Integer>();
                            while (c.moveToNext()){
                                arrID.add(c.getInt(0));
                            }
                            // show dialog update at here
                            showDialogUpdate(MemberList.this, arrID.get(position));
                        } else {
                            // delete
                            Cursor c = memberDatabase1.getData("SELECT id FROM SECONDARY");
                            ArrayList<Integer> arrID = new ArrayList<Integer>();
                            while (c.moveToNext()){
                                arrID.add(c.getInt(0));
                            }
                            showDialogDelete(arrID.get(position));
                        }
                    }
                });
                dialog.show();
                return true;
            }
        });
    }

    private void filter(String text) {
        ArrayList<Member> filterList=new ArrayList<>();
        for(Member m:list){
            if(m.getNaming().toLowerCase().contains(text.toLowerCase())){
                filterList.add(m);
            }
        }
        adapter.filterList(filterList);
    }


    ImageView imageViewFood;
    public void showDialogUpdate(Activity activity, final int position){
        final Dialog dialog = new Dialog(activity);
        dialog.setContentView(R.layout.update_member_activity);
        dialog.setTitle("Update");

        imageViewFood = (ImageView) dialog.findViewById(R.id.imageViewFood_for_member);
        final EditText edt_update_year = (EditText) dialog.findViewById(R.id.edt_update_year_for_member);
        final EditText edt_update_roll=(EditText) dialog.findViewById(R.id.edt_update_roll_for_member);
        Button btnUpdate = (Button) dialog.findViewById(R.id.btnUpdate_for_member);
        // set width for dialog
        int width = (int) (activity.getResources().getDisplayMetrics().widthPixels * 0.95);
        // set height for dialog
        int height = (int) (activity.getResources().getDisplayMetrics().heightPixels * 0.7);
        dialog.getWindow().setLayout(width, height);
        dialog.show();

        imageViewFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // request photo library
                ActivityCompat.requestPermissions(
                        MemberList.this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        888
                );
            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    memberDatabase1.updateData(
                            edt_update_year.getText().toString().trim(),
                            edt_update_roll.getText().toString().trim(),
                            MemberActivity.imageViewToByte(imageViewFood),
                            position

                            );
                }
                catch (Exception error) {
                    Log.e("Update error", error.getMessage());
                }
                dialog.dismiss();
                Toast.makeText(getApplicationContext(), "Update successfully!!!",Toast.LENGTH_SHORT).show();
                updateFoodList();
            }
        });
    }
    public void showDialogDelete(final int idFood){
        final AlertDialog.Builder dialogDelete = new AlertDialog.Builder(MemberList.this);
        dialogDelete.setTitle("Warning!!");
        dialogDelete.setMessage("Are you sure you want to this delete?");
        dialogDelete.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                try {
                    memberDatabase1.deletData(idFood);
                    Toast.makeText(getApplicationContext(), "Delete successfully!!!", Toast.LENGTH_SHORT).show();
                } catch (Exception e){
                    Log.e("error", e.getMessage());
                }
                updateFoodList();
            }
        });
        dialogDelete.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        dialogDelete.show();
    }
    private void updateFoodList(){
        // get all data from sqlite
        list.clear();
        Cursor cursor1 = memberDatabase1.getData("SELECT * FROM SECONDARY");
        while (cursor1.moveToNext()) {
            int id = cursor1.getInt(0);
            String name = cursor1.getString(1);
            String year = cursor1.getString(2);
            byte[] image = cursor1.getBlob(3);
            String roll=cursor1.getString(4);
            String  phone=cursor1.getString(5);
            String email=cursor1.getString(6);
            list.add(new Member( name, year, image, id,roll,phone,email));
        }
        adapter.notifyDataSetChanged();
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {

        if(requestCode == 888){
            if(grantResults.length >0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, 888);
            }
            else {
                Toast.makeText(getApplicationContext(), "You don't have permission to access file location!", Toast.LENGTH_SHORT).show();
            }
            return;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if(requestCode == 888 && resultCode == RESULT_OK && data != null){
            Uri uri = data.getData();
            try {
                InputStream inputStream = getContentResolver().openInputStream(uri);
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                imageViewFood.setImageBitmap(bitmap);

            } catch (FileNotFoundException e) {
                Log.e("byte image error ",e.getMessage().toString());
            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }
}