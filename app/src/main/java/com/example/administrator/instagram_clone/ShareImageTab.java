package com.example.administrator.instagram_clone;


import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.io.ByteArrayOutputStream;


/**
 * A simple {@link Fragment} subclass.
 */
public class ShareImageTab extends Fragment {

    private ImageView shareImageView;
    private Button shareImageButton;
    private TextView shareImageDescriotion;
    private Bitmap receivedImageBitmap;
    private ProgressBar progressBar;

    public ShareImageTab() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_share_image_tab, container, false);
        shareImageView = view.findViewById(R.id.shareimagetabImageView);
        shareImageButton = view.findViewById(R.id.shareImageTabButton);
        shareImageDescriotion = view.findViewById(R.id.shareImageTabDescription);
        progressBar = view.findViewById(R.id.shareImageTabProgressBar);
        onButtonClickeed();
        return view;

    }

    private void onButtonClickeed() {
        shareImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M &&
                        ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.READ_EXTERNAL_STORAGE)
                                != PackageManager.PERMISSION_GRANTED) {
                    requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                            1000);
                } else {
                    getShareImage();
                }
            }
        });
        shareImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setAlpha(1);
                if (receivedImageBitmap != null) {
                    if (shareImageDescriotion.getText().toString().equals("")) {
                        FancyToast.makeText(getContext(), "You must specify description", FancyToast.LENGTH_LONG
                                , FancyToast.ERROR, true).show();
                    } else {
                        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                        receivedImageBitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
                        byte[] bytes = byteArrayOutputStream.toByteArray();
                        ParseFile parseFile = new ParseFile("img.png", bytes);
                        ParseObject parseObject = new ParseObject("Photo");
                        parseObject.put("picture", parseFile);
                        parseObject.put("image_des", shareImageDescriotion.getText().toString());
                        parseObject.put("username", ParseUser.getCurrentUser().getUsername());
                        parseObject.saveInBackground(new SaveCallback() {
                            @Override
                            public void done(ParseException e) {
                                if (e == null) {
                                    FancyToast.makeText(getContext(), "Successfully uploaded image",
                                            FancyToast.LENGTH_LONG, FancyToast.SUCCESS, true).show();
                                } else {
                                    FancyToast.makeText(getContext(), "Failed uploaded image",
                                            FancyToast.LENGTH_LONG, FancyToast.ERROR, true).show();
                                }
                                progressBar.setAlpha(0);
                            }
                        });
                    }
                }
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED && requestCode == 1000) {
            getShareImage();

        }
    }

    private void getShareImage() {
        FancyToast.makeText(getContext(), "Permission granted to access image", FancyToast.LENGTH_LONG,
                FancyToast.SUCCESS, true).show();
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, 2000);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 2000 && resultCode == Activity.RESULT_OK) {
            try {
                Uri selectedImage = data.getData();
                String[] filePathColumn = {MediaStore.Images.Media.DATA};
                Cursor cursor = getActivity().getContentResolver().query(selectedImage, filePathColumn, null, null, null);
                cursor.moveToFirst();
                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                String picturePath = cursor.getString(columnIndex);
                cursor.close();
                receivedImageBitmap = BitmapFactory.decodeFile(picturePath);
                shareImageView.setImageBitmap(receivedImageBitmap);
            } catch (Exception e) {
                FancyToast.makeText(getContext(), e.getMessage(), FancyToast.LENGTH_LONG,
                        FancyToast.ERROR, true).show();
            }
        }
    }

    public TextView getShareImageDescriotion() {
        return shareImageDescriotion;
    }
}
