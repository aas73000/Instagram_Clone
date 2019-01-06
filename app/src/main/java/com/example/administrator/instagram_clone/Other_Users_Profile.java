package com.example.administrator.instagram_clone;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

import com.parse.FindCallback;
import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.util.List;

public class Other_Users_Profile extends AppCompatActivity {
    private LinearLayout linearLayout;
    private ProgressBar other_users_profile_ProgrssBar;
    private ScrollView other_users_profile_ScrollView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other__users__profile);
        other_users_profile_ProgrssBar = findViewById(R.id.otheruserprofileProgressBar);
        Intent intent = getIntent();
        String username = intent.getStringExtra("username");
        FancyToast.makeText(Other_Users_Profile.this, username, FancyToast.LENGTH_LONG, FancyToast.SUCCESS, true).show();
        linearLayout = findViewById(R.id.otheruserLinearLayout);
        other_users_profile_ScrollView = findViewById(R.id.otheruserScrollView);
        ParseQuery<ParseObject> parseQuery = new ParseQuery<ParseObject>("Photo");
        parseQuery.whereEqualTo("username", username);
        parseQuery.orderByDescending("picture");
        parseQuery.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                for (ParseObject post : objects) {
                    if (e == null && objects.size() > 0) {
                        final TextView postDescription = new TextView(Other_Users_Profile.this);
                        if (post.get("image_des") != null){
                            postDescription.setText(post.get("image_des").toString());
                        }else{
                            postDescription.setText(" ");
                        }
                        ParseFile postPicture = (ParseFile) post.get("picture");
                        postPicture.getDataInBackground(new GetDataCallback() {
                            @Override
                            public void done(byte[] data, ParseException e) {
                                if(data != null && e == null){
                                    Bitmap bitmap = BitmapFactory.decodeByteArray(data,0,data.length);
                                    ImageView post_Image = new ImageView(Other_Users_Profile.this);
                                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                                             ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
                                    params.setMargins(5,5,5,5);
                                    post_Image.setLayoutParams(params);
                                    post_Image.setScaleType(ImageView.ScaleType.FIT_CENTER);
                                    post_Image.setImageBitmap(bitmap);
                                    LinearLayout.LayoutParams textview_Params = new LinearLayout.LayoutParams(
                                            ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
                                    textview_Params.setMargins(15,15,15,15);
                                    postDescription.setLayoutParams(textview_Params);
                                    postDescription.setGravity(Gravity.CENTER);
                                    postDescription.setBackgroundColor(Color.BLUE);
                                    postDescription.setTextSize(30f);
                                    linearLayout.addView(post_Image);
                                    linearLayout.addView(postDescription);
                                    other_users_profile_ProgrssBar.setAlpha(0f);
                                    other_users_profile_ScrollView.setAlpha(1f);
                                }
                            }
                        });
                    }
                }
            }
        });

    }
}
