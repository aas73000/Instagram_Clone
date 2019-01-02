package com.example.administrator.instagram_clone;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.shashank.sony.fancytoastlib.FancyToast;

public class SignUpActivity extends AppCompatActivity {
    private Button loginButton,signupButton;
    private EditText txtUserName,txtPassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        initializeAllView();
        buttonClicked();
    }
    private void initializeAllView(){
        loginButton = findViewById(R.id.loginId2);
        signupButton = findViewById(R.id.signupId2);
        txtUserName = findViewById(R.id.usernameId2);
        txtPassword = findViewById(R.id.passwordId2);
    }
    private void buttonClicked(){
        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignUpActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ParseUser.logInInBackground(txtUserName.getText().toString(), txtPassword.getText().toString(), new LogInCallback() {
                    @Override
                    public void done(ParseUser user, ParseException e) {
                        Log.i("Working","Function");
                        if(user != null && e == null){
                            FancyToast.makeText(SignUpActivity.this,"Login successfully",FancyToast.LENGTH_LONG,
                                    FancyToast.SUCCESS,true).show();
                        }else {
                            FancyToast.makeText(SignUpActivity.this,"error login",FancyToast.LENGTH_LONG,
                                    FancyToast.ERROR,true).show();
                        }
                    }
                });
            }
        });
    }
}
