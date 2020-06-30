package com.example.bod_members.Bod;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.example.bod_members.MainActivity;
import com.example.bod_members.R;

public class B_login extends AppCompatActivity {
Button btn;
EditText name,pass;
  public static   String naming="ThawZinNyein",password="bod118110";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_b_login);
        btn=findViewById(R.id.btn_go);
        name=findViewById(R.id.login_name);
        pass=findViewById(R.id.login_pass);
            this.setTitle("");
         btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(name.getText().toString().equals(naming)&& pass.getText().toString().equals(password)){
                Intent intent=new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                    name.setText("");
                    pass.setText("");

            }else Toast.makeText(getApplicationContext(),"something went wrong ",Toast.LENGTH_SHORT).show();}
        });

    }
}
