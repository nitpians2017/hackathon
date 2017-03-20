package com.example.rajukumarsingh.bloodbank;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class Donor_signup extends AppCompatActivity {
    EditText Name,Dob,Phone,Address,Blood_group,Email,Password;
    Button sign_up;
    RequestQueue requestQueue;
    String name,dob,phone,address,blood_group,email,password;
    String url="http://rahulraj47.coolpage.biz/donor.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main5);
        Name= (EditText) findViewById(R.id.editText3);
        Dob= (EditText) findViewById(R.id.editText4);
        Phone= (EditText) findViewById(R.id.editText5);
        Address= (EditText) findViewById(R.id.editText6);
        Blood_group= (EditText) findViewById(R.id.editText7);
        Email= (EditText) findViewById(R.id.editText8);
        Password= (EditText) findViewById(R.id.editText9);
        sign_up= (Button) findViewById(R.id.button10);
        requestQueue= Volley.newRequestQueue(this);

        sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name=Name.getText().toString();
                dob=Dob.getText().toString();
                phone=Phone.getText().toString();
                address=Address.getText().toString();
                blood_group=Blood_group.getText().toString();
                email=Email.getText().toString();
                password=Password.getText().toString();
                StringRequest stringRequest=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        Toast.makeText(Donor_signup.this, ""+name, Toast.LENGTH_SHORT).show();

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Toast.makeText(Donor_signup.this, ""+volleyError, Toast.LENGTH_SHORT).show();
                    }
                })
                {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String,String> params=new HashMap<String, String>();
                        params.put("name",name);
                        params.put("dob",dob);
                        params.put("phone",phone);
                        params.put("address",address);
                        params.put("blood_group",blood_group);
                        params.put("email",email);
                        params.put("password",password);
                        return params;
                    }
                };
                requestQueue.add(stringRequest);
            }
        });
    }
}
