package com.cookandroid.registerlogin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView tv_id, tv_pass,tv_name,tv_Num,tv_Email;
    private Button btn_logout;

     @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv_id = findViewById(R.id. tv_id);
        tv_pass = findViewById(R.id.tv_pass);
        tv_name = findViewById(R.id.tv_name);
        tv_Num = findViewById(R.id.tv_Num);
        tv_Email = findViewById(R.id.tv_Email);

         btn_logout = findViewById(R.id.btn_logout);

         btn_logout.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
                 startActivity(intent);
             }
         });
         Intent intent = getIntent();
         String userID = intent.getStringExtra("userID");
         String userPass = intent.getStringExtra("userPass");
         String userName = intent.getStringExtra("userName");
         String userEmail = intent.getStringExtra("userEmail");
         String userNum = intent.getStringExtra("userNum");





        tv_id.setText(userID);
        tv_pass.setText(userPass);
        tv_name.setText(userName);
        tv_Num.setText(userNum);
        tv_Email.setText(userEmail);




    }

}
