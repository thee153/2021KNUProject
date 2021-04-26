package com.cookandroid.registerlogin;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class FindIdActivity extends AppCompatActivity {

    private TextView et_findname, et_findnum,et_findemail;
    private Button btn_findid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_findid);

        et_findname = findViewById(R.id. et_findname);
        et_findnum= findViewById(R.id.et_findnum);
        et_findemail = findViewById(R.id.et_findemail);




        btn_findid = findViewById(R.id.btn_findid);
            btn_findid.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    String userName = et_findname.getText().toString();
                    String userNum = et_findnum.getText().toString();
                    String userEmail = et_findemail.getText().toString();

                    if(userName.equals("") ||userNum.equals("")||userEmail.equals(""))
                    {
                        Toast.makeText(getApplicationContext(), "값을 입력해 주세요", Toast.LENGTH_SHORT).show();
                    }
                    else {

                        Response.Listener<String> responseListener =new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {


                                try {
                                    JSONObject jasonObject = new JSONObject(response);
                                    boolean success = jasonObject.getBoolean("success");
                                    if(success){
                                        String userID = jasonObject.getString("userID");
                                        Intent intent = new Intent(FindIdActivity.this, FindResultPage.class);
                                        intent.putExtra("userID",userID);
                                        startActivity(intent);
                                    }else{ //실패한 경우
                                        Toast.makeText(getApplicationContext(),"일치하는 정보가 없습니다.",Toast.LENGTH_SHORT).show();
                                        return;
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }


                            }
                        };
                        FindIdRequest findIdrequest = new FindIdRequest(userName, userEmail,Integer.parseInt(userNum), responseListener);
                        RequestQueue queue = Volley.newRequestQueue(FindIdActivity.this);
                        queue.add(findIdrequest);
                    }
                }
            });




    }
}
