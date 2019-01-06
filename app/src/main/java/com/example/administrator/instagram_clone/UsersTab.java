package com.example.administrator.instagram_clone;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.util.ArrayList;
import java.util.List;

import libs.mjn.prettydialog.PrettyDialog;
import libs.mjn.prettydialog.PrettyDialogCallback;


/**
 * A simple {@link Fragment} subclass.
 */
public class UsersTab extends Fragment implements AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener {
    private ArrayList<String> arrayList;
    private ListView listView;
    private ArrayAdapter arrayAdapter;
    private ProgressBar progressBar;

    public UsersTab() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_users_tab, container, false);
        listView = view.findViewById(R.id.userstabListView);
        progressBar = view.findViewById(R.id.progressBarUser);
        arrayList = new ArrayList<String>();
        try {

            ParseQuery<ParseUser> parseUserParseQuery = ParseUser.getQuery();
            parseUserParseQuery.whereNotEqualTo("username", ParseUser.getCurrentUser().getUsername());
            parseUserParseQuery.findInBackground(new FindCallback<ParseUser>() {
                @Override
                public void done(List<ParseUser> objects, ParseException e) {

                    if (e == null && objects.size() > 0) {
                        for (ParseUser parseUser : objects) {
                            arrayList.add(parseUser.getUsername());
                            Log.i("Working", arrayList + "");
                        }
                    } else {
                        FancyToast.makeText(getContext(), e.getMessage(), FancyToast.LENGTH_LONG, FancyToast.ERROR, true)
                                .show();
                    }
                    arrayAdapter = new ArrayAdapter(getContext(), android.R.layout.simple_list_item_1, arrayList);
                    listView.setAlpha(1f);
                    progressBar.setAlpha(0f);
                    listView.setAdapter(arrayAdapter);
                    listView.setOnItemLongClickListener(UsersTab.this);
                }
            });
            listView.setOnItemClickListener(UsersTab.this);
            FancyToast.makeText(getContext(), "Users data updated", FancyToast.LENGTH_LONG, FancyToast.CONFUSING, true)
                    .show();
        } catch (Exception e) {
            FancyToast.makeText(getContext(), e.getMessage(), FancyToast.LENGTH_LONG, FancyToast.ERROR, true)
                    .show();
        }
        return view;
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(getContext(), Other_Users_Profile.class);
        intent.putExtra("username", arrayList.get(position).toString());
        startActivity(intent);
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        ParseQuery<ParseUser> parseQuery = ParseUser.getQuery();
        parseQuery.whereEqualTo("username", arrayList.get(position));
        parseQuery.getFirstInBackground(new GetCallback<ParseUser>() {
            @Override
            public void done(ParseUser object, ParseException e) {
                if (e == null && object != null) {
                    final PrettyDialog prettyDialognew = new PrettyDialog(getContext());
                    prettyDialognew.setTitle(object.get("username").toString())
                            .setMessage(object.get("Profile_User_Name").toString() + "\n" +
                                    object.get("Profile_Profession") + "\n" +
                                    object.get("Profile_Bio") + "\n" +
                                    object.get("Profile_Hobby") + "\n" +
                                    object.get("Profile_Game"))
                            .setIcon(R.drawable.ic_person_black_24dp)
                            .setIconTint(R.color.craneRed)
                            .setIconCallback(new PrettyDialogCallback() {
                                @Override
                                public void onClick() {
                                    prettyDialognew.dismiss();
                                }
                            }).show();

                }
            }
        });
        return true;
    }
}
