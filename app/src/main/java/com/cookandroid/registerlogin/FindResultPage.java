package com.cookandroid.registerlogin;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;


public class FindResultPage extends AppCompatActivity {

    private TextView tv_result;
    private Button btn_idresult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_findidresult);

        tv_result = findViewById(R.id. tv_result);
        btn_idresult = findViewById(R.id.btn_idresult);

        btn_idresult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
                startActivity(intent);

            }
        });

        Intent intent = getIntent();
        String userID = intent.getStringExtra("userID");
        //아이디 2~4자리 가리기

        char a = userID.charAt(0);
        char b = userID.charAt(1);
        char c = userID.charAt(2);
        char d = userID.charAt(3);
        char[] ary = {a,'*','*','*'};
        String arrayString = String.valueOf(ary);
        char[] array = userID.toCharArray();
        for (int j = 4; j < userID.length(); j++) {
            arrayString+= Character.toString(array[j]);
        }
        tv_result.setText(arrayString);


    }
}
