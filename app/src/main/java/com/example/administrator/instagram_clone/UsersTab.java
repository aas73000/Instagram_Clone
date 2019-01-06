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
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class UsersTab extends Fragment implements AdapterView.OnItemClickListener {
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
                    arrayAdapter = new ArrayAdapter(getContext(), android.R.layout.simple_list_item_1,arrayList);
                    listView.setAlpha(1f);
                    progressBar.setAlpha(0f);
                    listView.setAdapter(arrayAdapter);
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
}
