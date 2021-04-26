package com.cookandroid.registerlogin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class RegisterActivity extends AppCompatActivity {

    private EditText et_id, et_pass,et_name,et_num,et_pass2,et_email;
    private Button btn_register,btn_ValidateID,btn_ValidateEmail;
    private boolean validate = false;
    private boolean validate2 = false;
    private AlertDialog dialog;
    private AlertDialog dialog2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //아이디 값 찾아주기
        et_id = findViewById(R.id.et_findID);
        et_pass = findViewById(R.id.et_findEmail);
        et_pass2 = findViewById(R.id.et_pass2);
        et_name = findViewById(R.id.et_name);
        et_num = findViewById(R.id.et_num);
        et_email = findViewById(R.id.et_email);


        btn_ValidateID = findViewById(R.id.btn_ValidateID);
        btn_ValidateID.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                String UserID = et_id.getText().toString();
                if (UserID.length() < 5) {
                    Toast.makeText(getApplicationContext(), "아이디 길이가 짧습니다.", Toast.LENGTH_SHORT).show();
                } else {
                    if (validate) {
                        return; //검증 완료
                    }

                    if (UserID.equals("")) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                        dialog = builder.setMessage("아이디를 입력하세요.").setPositiveButton("확인", null).create();
                        dialog.show();
                        return;
                    }

                    Response.Listener<String> responseListener = new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {

                                JSONObject jsonResponse = new JSONObject(response);
                                boolean success = jsonResponse.getBoolean("success");

                                if (success) {
                                    AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                                    dialog = builder.setMessage("사용할 수 있는 아이디입니다.").setPositiveButton("확인", null).create();
                                    dialog.show();
                                    et_id.setEnabled(false); //아이디값 고정
                                    validate = true; //검증 완료
                                } else {
                                    AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                                    dialog = builder.setMessage("이미 존재하는 아이디입니다.").setNegativeButton("확인", null).create();
                                    dialog.show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    };
                    ValidateRequestID validateRequest = new ValidateRequestID(UserID, responseListener);
                    RequestQueue queue = Volley.newRequestQueue(RegisterActivity.this);
                    queue.add(validateRequest);
                }
            }
        });


        btn_ValidateEmail = findViewById(R.id.btn_ValidateEmail);
        btn_ValidateEmail.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                String UserEmail = et_email.getText().toString();
                if (validate2) {
                    return; //검증 완료
                }

                if (UserEmail.equals("")) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                    dialog2 = builder.setMessage("이메일을 입력하세요.").setPositiveButton("확인", null).create();
                    dialog2.show();
                    return;
                }

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {

                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");

                            if (success) {
                                AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                                dialog2 = builder.setMessage("사용할 수 있는 이메일입니다.").setPositiveButton("확인", null).create();
                                dialog2.show();
                                et_id.setEnabled(false); //아이디값 고정
                                validate2 = true; //검증 완료
                            }
                            else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                                dialog2 = builder.setMessage("이미 존재하는 이메일입니다.").setNegativeButton("확인", null).create();
                                dialog2.show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };
                ValidateRequestEmail validateRequest = new ValidateRequestEmail(UserEmail, responseListener);
                RequestQueue queue = Volley.newRequestQueue(RegisterActivity.this);
                queue.add(validateRequest);
            }
        });




        //회원가입 버튼 클릭 시 수행

        btn_register = findViewById(R.id.btn_register);
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // edittext에 현재 입력되어 있는 값을 Get해준다
                String userID = et_id.getText().toString();
                String userPass = et_pass.getText().toString();
                String userPass2 = et_pass2.getText().toString();
                String userName = et_name.getText().toString();
                String userEmail = et_email.getText().toString();
                String userNum = et_num.getText().toString();

                if (userName.equals("") || userNum.equals("") || userEmail.equals("") || userID.equals("") || userPass.equals("") || userPass2.equals("")) {
                    Toast.makeText(getApplicationContext(), "값을 입력해 주세요", Toast.LENGTH_SHORT).show();
                }
                else {
                    if (validate == true && validate2 == true && userPass.length() > 5) {

                    if (userPass.equals(userPass2)) {
                        Response.Listener<String> responseListener = new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    JSONObject jasonObject = new JSONObject(response);
                                    boolean success = jasonObject.getBoolean("success");
                                    if (success) {
                                        Toast.makeText(getApplicationContext(), "회원등록에 성공하였습니다.", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                                        startActivity(intent);
                                    } else { //실패한 경우
                                        Toast.makeText(getApplicationContext(), "회원등록에 실패하였습니다.", Toast.LENGTH_SHORT).show();
                                        return;
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            }
                        };
                        //서버로 Volley를 이용해서 요청을 함
                        RegisterRequest registerRequest = new RegisterRequest(userID, userPass, userName, userEmail,Integer.parseInt(userNum), responseListener);
                        RequestQueue queue = Volley.newRequestQueue(RegisterActivity.this);
                        queue.add(registerRequest);
                    } else {
                        Toast.makeText(getApplicationContext(), "비밀번호가 일치하지 않습니다", Toast.LENGTH_SHORT).show();
                    }

                } else if (userPass.length() > 5) {
                    Toast.makeText(getApplicationContext(), "아이디 또는 이메일이 사용중입니다.", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "비밀번호 길이가 짧습니다.", Toast.LENGTH_SHORT).show();
                }
            }

            }
        });

    }
}
