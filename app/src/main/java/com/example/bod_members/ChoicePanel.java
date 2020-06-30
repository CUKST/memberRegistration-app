package com.example.bod_members;

import android.content.Intent;
import android.net.wifi.WifiManager;
import android.view.View;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.example.bod_members.Bod.B_login;
import com.example.bod_members.members.M_login;


public class ChoicePanel extends AppCompatActivity  implements View.OnClickListener {
ImageView bod,member,app;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choice_panel);
        bod=findViewById(R.id.imageView_for_bod_admin);
        member=findViewById(R.id.imageView_for_member_admin);
        app=findViewById(R.id.imageView_for_appUser_admin);
        bod.setOnClickListener(this);
        member.setOnClickListener(this);
        app.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.imageView_for_bod_admin:
                Intent intent=new Intent(getApplicationContext(), B_login.class);
                startActivity(intent);
                break;
            case  R.id.imageView_for_member_admin:
                Intent intent1=new Intent(getApplicationContext(), M_login.class);
                startActivity(intent1);
        }
    }
}
