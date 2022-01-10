package com.petshop.ui.util;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.petshop.MainActivity;

import java.io.IOException;
import java.io.InputStream;

public class FragmentPermission extends Fragment {

    protected ImageView imageSelected;

    @SuppressLint({"IntentReset", "QueryPermissionsNeeded"})
    protected void startBtnChooseImage(Button btn){
        btn.setOnClickListener(view1 -> {
            boolean permission = ActivityCompat.checkSelfPermission(requireActivity(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED;
            if(permission)
                ActivityCompat.requestPermissions(requireActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, MainActivity.REQUEST_CODE_GALLERY);
            else{
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                intent.setType("image/*");
                boolean activity = intent.resolveActivity(requireActivity().getPackageManager()) != null;
                if(activity)
                    startActivityForResult(intent, MainActivity.REQUEST_CODE_GALLERY);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(resultCode == Activity.RESULT_OK && requestCode == MainActivity.REQUEST_CODE_GALLERY && data != null){
            try {
                Uri uri = data.getData();
                InputStream inputStream = requireActivity().getContentResolver().openInputStream(uri);
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                this.imageSelected.setImageBitmap(bitmap);
            } catch (IOException e) {
                Log.e("ERROR", e.getMessage());
            }
        }
    }

}
