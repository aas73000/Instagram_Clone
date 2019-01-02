package com.example.administrator.instagram_clone;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.parse.ParseException;
import com.parse.ParseInstallation;
import com.parse.ParseUser;
import com.parse.SignUpCallback;
import com.shashank.sony.fancytoastlib.FancyToast;

public class MainActivity extends AppCompatActivity {
    private Button buttonLogIn,buttonSignUp;
    private EditText txtEmail,txtUserName,txtPassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ParseInstallation.getCurrentInstallation().saveInBackground();
        initializeAllViews();
        buttonClicked();
    }
    private void initializeAllViews(){
        buttonLogIn = findViewById(R.id.loginId);
        buttonSignUp = findViewById(R.id.signupId);
        txtEmail = findViewById(R.id.emailId);
        txtUserName = findViewById(R.id.usernameId);
        txtPassword = findViewById(R.id.passwordId);
    }
    private void buttonClicked(){
        buttonLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,SignUpActivity.class);
                startActivity(intent);
            }
        });
        buttonSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ParseUser parseUser = new ParseUser();
                parseUser.setEmail(txtEmail.getText().toString());
                parseUser.setUsername(txtUserName.getText().toString());
                parseUser.setPassword(txtPassword.getText().toString());
                parseUser.signUpInBackground(new SignUpCallback() {
                    @Override
                    public void done(ParseException e) {
                        if(e == null){
                            FancyToast.makeText(MainActivity.this,"Sign up successfully with "+parseUser.getUsername(),
                                    FancyToast.LENGTH_LONG,FancyToast.SUCCESS,true).show();
                        }else{
                            FancyToast.makeText(MainActivity.this,e.getMessage(),
                                    FancyToast.LENGTH_LONG,FancyToast.SUCCESS,true).show();;
                        }
                    }
                });
            }
        });
    }
}
