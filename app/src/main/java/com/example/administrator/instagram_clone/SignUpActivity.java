package com.example.administrator.instagram_clone;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.shashank.sony.fancytoastlib.FancyToast;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {
    private Button loginButton, signupButton;
    private EditText txtUserName, txtPassword;
    private CharSequence title = "Log In";
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbarSignUp);
        setSupportActionBar(myToolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(title);
        initializeAllView();
        buttonClicked();
    }

    private void initializeAllView() {
        loginButton = findViewById(R.id.loginId2);
        signupButton = findViewById(R.id.signupId2);
        txtUserName = findViewById(R.id.usernameId2);
        txtPassword = findViewById(R.id.passwordId2);
        progressBar = findViewById(R.id.indeterminateBar2);
    }

    private void buttonClicked() {
        signupButton.setOnClickListener(SignUpActivity.this);
        loginButton.setOnClickListener(SignUpActivity.this);
        txtPassword.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN) {
                    onClick(loginButton);
                    return true;

                }
                return false;
            }
        });
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.loginId2:
                progressBar.setAlpha(1);
                ParseUser.logInInBackground(txtUserName.getText().toString(), txtPassword.getText().toString(), new LogInCallback() {
                    @Override
                    public void done(ParseUser user, ParseException e) {
                        Log.i("Working", "Function");
                        if (user != null && e == null) {
                            FancyToast.makeText(SignUpActivity.this, "Login successfully", FancyToast.LENGTH_LONG,
                                    FancyToast.SUCCESS, true).show();
                            transitionToSocialMediaActivity();
                        } else {
                            FancyToast.makeText(SignUpActivity.this, "error login", FancyToast.LENGTH_LONG,
                                    FancyToast.ERROR, true).show();

                        }
                        progressBar.setAlpha(0);

                    }
                });
                break;
            case R.id.signupId2:
                Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
                startActivity(intent);
                break;
        }
    }
    public void onLayoutTapped(View view){
        try{
        InputMethodManager inputMethodManager = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),0);}
        catch (Exception e){
            e.getStackTrace();
        }
    }

    private void transitionToSocialMediaActivity() {
        Intent intent = new Intent(SignUpActivity.this, Social_media_Activity.class);
        startActivity(intent);
        finish();
    }
}
