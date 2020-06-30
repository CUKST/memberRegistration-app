package com.example.bod_members.Bod;

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
import android.util.Log;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.core.app.ActivityCompat;
import com.example.bod_members.MainActivity;
import com.example.bod_members.R;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;

import static com.example.bod_members.MainActivity.*;

 public class BODLists extends AppCompatActivity {

    GridView gridView;
    ArrayList<BOD> list;
    BODListAdapter adapter = null;



    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_lists);
        gridView = (GridView) findViewById(R.id.gridView);
        this.setTitle("");
        list = new ArrayList<>();
        adapter = new BODListAdapter(this, R.layout.food_items, list);
        gridView.setAdapter(adapter);
        // get all data from sqlite
        Cursor cursor = memberDatabase.getInfo("SELECT * FROM MEMORY");
        list.clear();
        while (cursor.moveToNext()) {
            int id = cursor.getInt(0);
            String name = cursor.getString(1);
            String developer = cursor.getString(2);
            byte[] photo = cursor.getBlob(3);
            String position=cursor.getString(4);
            String year=cursor.getString(5);
            String contact=cursor.getString(6);


            list.add(new BOD(name, developer,photo,id,position,year,contact ));
        }
        adapter.notifyDataSetChanged();

        gridView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {

                CharSequence[] items = {"Update", "Delete"};
                AlertDialog.Builder dialog = new AlertDialog.Builder(BODLists.this);

                dialog.setTitle("Choose an action");
                dialog.setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int item) {
                        if (item == 0) {
                            // update
                            Cursor c = memberDatabase.getInfo("SELECT id FROM MEMORY");
                            ArrayList<Integer> arrID = new ArrayList<Integer>();
                            while (c.moveToNext()){
                                arrID.add(c.getInt(0));
                            }
                            // show dialog update at here
                            showDialogUpdate(BODLists.this, arrID.get(position));
                        } else {
                            // delete
                            Cursor c = memberDatabase.getInfo("SELECT id FROM MEMORY");
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



    ImageView imageViewFood;
    public void showDialogUpdate(Activity activity, final int position){
        final Dialog dialog = new Dialog(activity);
        dialog.setContentView(R.layout.update_food_activity);
        dialog.setTitle("Update");

        imageViewFood = (ImageView) dialog.findViewById(R.id.imageViewFood);
        final EditText edt_update_position=(EditText) dialog.findViewById(R.id.edt_update_position);
        final  EditText edt_update_year=(EditText) dialog.findViewById(R.id.edt_update_year);
        Button btnUpdate = (Button) dialog.findViewById(R.id.btnUpdate);
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
                        BODLists.this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        888
                );
            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    memberDatabase.updateInfo(
                            edt_update_position.getText().toString().trim(),
                            edt_update_year.getText().toString().trim(),
                            MainActivity.imageViewToByte(imageViewFood),
                            position);   }
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
        final AlertDialog.Builder dialogDelete = new AlertDialog.Builder(BODLists.this);
        dialogDelete.setTitle("Warning!!");
        dialogDelete.setMessage("Are you sure you want to this delete?");
        dialogDelete.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                try {
                    memberDatabase.deletInfo(idFood);
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
        Cursor cursor1 = memberDatabase.getInfo("SELECT * FROM MEMORY");
        while (cursor1.moveToNext()) {
            int id = cursor1.getInt(0);
            String name = cursor1.getString(1);
            String developer = cursor1.getString(2);
            byte[] photo = cursor1.getBlob(3);
            String position=cursor1.getString(4);
            String  year=cursor1.getString(5);
            String contact=cursor1.getString(6);
            list.add(new BOD( name, developer, photo, id,position,year,contact));
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