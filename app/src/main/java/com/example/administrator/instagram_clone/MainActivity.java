package com.example.administrator.instagram_clone;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.parse.ParseException;
import com.parse.ParseInstallation;
import com.parse.ParseUser;
import com.parse.SignUpCallback;
import com.shashank.sony.fancytoastlib.FancyToast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button buttonLogIn,buttonSignUp;
    private EditText txtEmail,txtUserName,txtPassword;
    private ProgressBar progressBar;
    private CharSequence title = "Sign Up";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ParseInstallation.getCurrentInstallation().saveInBackground();
        setActionBarTitle();
        initializeAllViews();
        buttonClicked();
    }
    public void setActionBarTitle(){
        Toolbar myToolbar = findViewById(R.id.toolbarSignUp);
        setSupportActionBar(myToolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(title);
    }
    private void initializeAllViews(){
        buttonLogIn = findViewById(R.id.loginId);
        buttonSignUp = findViewById(R.id.signupId);
        txtEmail = findViewById(R.id.emailId);
        txtUserName = findViewById(R.id.usernameId);
        txtPassword = findViewById(R.id.passwordId);
        progressBar = findViewById(R.id.indeterminateBar);
    }
    private void buttonClicked(){
        buttonLogIn.setOnClickListener(MainActivity.this);
        buttonSignUp.setOnClickListener(MainActivity.this);


        txtPassword.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if(keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN){
                    onClick(buttonSignUp);
                    return true;
                }
                return false;
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.loginId:
                Intent intent = new Intent(MainActivity.this,SignUpActivity.class);
                startActivity(intent);
                break;
            case R.id.signupId:
                progressBar.setAlpha(1);
                final ParseUser parseUser = new ParseUser();
                if(txtEmail.getText().toString().equals("") || txtUserName.getText().toString().equals("") || txtPassword.getText().toString().equals("")){
                    FancyToast.makeText(MainActivity.this,"Fill all the entries",
                            FancyToast.LENGTH_LONG,FancyToast.INFO,true).show();;
                    progressBar.setAlpha(0);
                    return;
                }
                parseUser.setEmail(txtEmail.getText().toString());
                parseUser.setUsername(txtUserName.getText().toString());
                parseUser.setPassword(txtPassword.getText().toString());
                parseUser.signUpInBackground(new SignUpCallback() {
                    @Override
                    public void done(ParseException e) {
                        if(e == null){
                            FancyToast.makeText(MainActivity.this,"Sign up successfully with "+parseUser.getUsername(),
                                    FancyToast.LENGTH_LONG,FancyToast.SUCCESS,true).show();
                            transitionToSocialMediaActivity();
                        }else{
                            FancyToast.makeText(MainActivity.this,e.getMessage(),
                                    FancyToast.LENGTH_LONG,FancyToast.ERROR,true).show();;
                        }
                        progressBar.setAlpha(0);
                    }
                });
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
        Intent intent = new Intent(MainActivity.this, Social_media_Activity.class);
        startActivity(intent);
    }
}
