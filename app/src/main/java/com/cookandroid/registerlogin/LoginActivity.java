package com.cookandroid.registerlogin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {
    private EditText et_id, et_pass;
    private Button btn_login,btn_register,btn_findid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        et_id = findViewById(R.id.et_id);
        et_pass = findViewById(R.id.et_pass);
        btn_login = findViewById(R.id.btn_login);
        btn_register = findViewById(R.id.btn_register);
        btn_findid = findViewById(R.id.btn_findid);

        //회원가입 버튼 클릭시 수행
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

        btn_findid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, FindIdActivity.class);
                startActivity(intent);
            }
        });



        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // edittext에 현재 입력되어 있는 값을 Get해준다
                String userID = et_id.getText().toString();
                String userPass = et_pass.getText().toString();

              Response.Listener<String> responseListener =new Response.Listener<String>() {
                  @Override
                  public void onResponse(String response) {

                      try {
                          JSONObject jasonObject = new JSONObject(response);
                          boolean success = jasonObject.getBoolean("success");
                          if(success){
                              String userID = jasonObject.getString("userID");
                              String userPass = jasonObject.getString("userPassword");
                              String userName = jasonObject.getString("userName");
                              String userNum = jasonObject.getString("userNum");
                              String userEmail = jasonObject.getString("userEmail");

                              Toast.makeText(getApplicationContext(),"로그인에 성공하였습니다.",Toast.LENGTH_SHORT).show();
                              Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                              intent.putExtra("userID",userID);
                              intent.putExtra("userPass",userPass);
                              intent.putExtra("userName",userName);
                              intent.putExtra("userNum",userNum);
                              intent.putExtra("userEmail",userEmail);
                              startActivity(intent);
                          }else{ //실패한 경우
                              Toast.makeText(getApplicationContext(),"로그인에 실패하였습니다.",Toast.LENGTH_SHORT).show();
                              return;
                          }
                      } catch (JSONException e) {
                          e.printStackTrace();
                      }


                  }
              };
              LoginRequest loginRequest = new LoginRequest(userID, userPass, responseListener);
              RequestQueue queue = Volley.newRequestQueue(LoginActivity.this);
              queue.add(loginRequest);

            }
        });
    }
}
