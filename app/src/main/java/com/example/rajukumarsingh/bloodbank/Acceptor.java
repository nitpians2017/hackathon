package com.example.rajukumarsingh.bloodbank;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class Acceptor extends AppCompatActivity {
 Button log_in,Sign_up;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        Sign_up= (Button) findViewById(R.id.button);
        log_in= (Button) findViewById(R.id.button2);
        Sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Acceptor.this,Donor_signup.class);
                startActivity(intent);
            }
        });
        log_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Acceptor.this,Donor_login.class);
                startActivity(intent);
            }
        });
    }

}
