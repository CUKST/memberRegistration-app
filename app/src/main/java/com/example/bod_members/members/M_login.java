package com.example.bod_members.members;

import android.content.Intent;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.example.bod_members.R;

public class M_login extends AppCompatActivity {
          public static String name="HtetAungKyaw";String password="member118110";
           EditText edtName,edtPass;
           Button btn_login;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_m_login);
        edtName=findViewById(R.id.login_name_for_m);
        edtPass=findViewById(R.id.login_pass_for_m);
        btn_login=findViewById(R.id.btn_go_for_m);
        this.setTitle("");

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(edtName.getText().toString().trim().equals(name)&&edtPass.getText().toString().trim().equals(password)){
                    Intent intent=new Intent(getApplicationContext(),MemberActivity.class);
                    startActivity(intent);
                    edtName.setText("");
                    edtPass.setText("");
                }
                else Toast.makeText(getApplicationContext(),"something went wrong",Toast.LENGTH_SHORT).show();
            }
        });

    }
}
