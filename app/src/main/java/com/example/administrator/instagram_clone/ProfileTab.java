package com.example.administrator.instagram_clone;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.shashank.sony.fancytoastlib.FancyToast;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileTab extends Fragment {

    private EditText edtProfileUsername, edtProfileProfession, edtProfileHobby, edtProfileGame, edtProfileBio;
    private Button btnProfileUpdate;
    private ProgressBar progressBar;

    public ProfileTab() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile_tab, container, false);
        intializeAllView(view);
        onButtonClicked();
        setProfileInfo();
        return view;
    }

    private void intializeAllView(View view) {
        edtProfileUsername = view.findViewById(R.id.profiletabNameid);
        edtProfileBio = view.findViewById(R.id.profiletabBio);
        edtProfileHobby = view.findViewById(R.id.profiletabHobbies);
        edtProfileGame = view.findViewById(R.id.profiletabGame);
        edtProfileProfession = view.findViewById(R.id.profiletabProfession);
        btnProfileUpdate = view.findViewById(R.id.profiletabUpdateBio);
        progressBar = view.findViewById(R.id.profiletabProgressBar);
    }

    private void setProfileInfo() {
        ParseUser parseUser = ParseUser.getCurrentUser();
        if (!(parseUser.get("Profile_User_Name") == null || parseUser.get("Profile_Bio") == null || parseUser.get("Profile_Hobby") == null
                || parseUser.get("Profile_Profession") == null || parseUser.get("Profile_Game") == null)) {
            edtProfileUsername.setText(parseUser.get("Profile_User_Name").toString());
            edtProfileBio.setText(parseUser.get("Profile_Bio").toString());
            edtProfileHobby.setText(parseUser.get("Profile_Hobby").toString());
            edtProfileProfession.setText(parseUser.get("Profile_Profession").toString());
            edtProfileGame.setText(parseUser.get("Profile_Game").toString());
        }
    }

    private void onButtonClicked() {
        btnProfileUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setAlpha(1);

                try {
                    ParseUser parseUser = ParseUser.getCurrentUser();
                    parseUser.put("Profile_User_Name", edtProfileUsername.getText().toString());
                    parseUser.put("Profile_Bio", edtProfileBio.getText().toString());
                    parseUser.put("Profile_Hobby", edtProfileHobby.getText().toString());
                    parseUser.put("Profile_Profession", edtProfileProfession.getText().toString());
                    parseUser.put("Profile_Game", edtProfileGame.getText().toString());

                    parseUser.saveInBackground(new SaveCallback() {
                        @Override
                        public void done(ParseException e) {
                            if (e == null) {
                                FancyToast.makeText(getContext(), "Updated Successfully", FancyToast.LENGTH_LONG,
                                        FancyToast.INFO, true).show();
                                Log.i("Working", "saveinbackground");
                            } else {
                                FancyToast.makeText(getContext(), e.getMessage(), FancyToast.LENGTH_LONG,
                                        FancyToast.ERROR, true).show();
                            }
                            progressBar.setAlpha(0);
                        }
                    });
                } catch (Exception e) {
                    FancyToast.makeText(getContext(), e.getMessage(), FancyToast.LENGTH_LONG, FancyToast.ERROR, true).show();
                    progressBar.setAlpha(0);
                    Log.i("error", e.getMessage());
                }
            }
        });
    }
}
