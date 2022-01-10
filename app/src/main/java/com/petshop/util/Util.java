package com.petshop.util;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.PorterDuff;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.core.content.res.ResourcesCompat;

import com.petshop.R;

import java.io.ByteArrayOutputStream;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.regex.Pattern;

public class Util {

    public static final String ERROR_500 = "Ha habido un error";

    public static Map<EditText, String> checkInputs(Map<EditText, InputForm> inputs){
        Map<EditText, String> response = new HashMap<>();
        for(EditText editText : inputs.keySet()){
            Boolean nullable = inputs.get(editText).getNullable();
            String inputData = editText.getText().toString().trim();
            String inputType = inputs.get(editText).getTypeData();
            if(!nullable && inputData.length() == 0){
                response.put(editText, "campo obligatorio");
                continue;
            }

            switch (inputType){
                case InputForm.TEXT:
                    Pattern pattern = Pattern.compile("[;:ñ.][^a-z0-9,;:.]", Pattern.CASE_INSENSITIVE);
                    if(pattern.matcher(inputData).find()) {
                        response.put(editText, "No use caracteres especiales");
                        continue;
                    }
                    break;
                case InputForm.DECIMAL:
                    try{
                        Double.parseDouble(inputData);
                    }catch (Exception ex){
                        response.put(editText, "Solo numeros");
                        continue;
                    }
                    break;
                case InputForm.INTEGER:
                    try{
                        Integer.parseInt(inputData);
                    }catch (Exception ex){
                        response.put(editText, "Solo numeros");
                        continue;
                    }
                    break;
                default:
                    break;
            }

            editText.setText(inputData);
        }

        return response;
    }

    public static byte[] imageViewToByte(ImageView imageView){
        Bitmap bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        return stream.toByteArray();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static String imageVIewToString(ImageView imageView){
        return Base64.getEncoder().encodeToString(imageViewToByte(imageView));
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static String convertByteToString(byte[] image){
        try{
            return Base64.getEncoder().encodeToString(image);
        }catch (Exception ex){
            Log.e("ERROR", "convertByteToString::" + ex.getMessage());
            return null;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static byte[] stringToByte(String image){
        try{
            return Base64.getDecoder().decode(image);
        }catch (Exception ex){
            Log.e("ERROR", "convertStringToByte::" + ex.getMessage());
            return null;
        }
    }


    public static void launchWarnings(TextView globalWarning,
                                      Map<EditText, InputForm> inputs, Map<EditText, String> inputsChecked, Resources resources, Integer validBorder, Integer invalidBorder, Integer inputsRequired){
        Integer globalWarningSetted = 0;
        for(EditText entry : inputs.keySet()){
            entry.setBackground(ResourcesCompat.getDrawable(resources, validBorder, null));
            globalWarning.setVisibility(View.GONE);
            Objects.requireNonNull(inputs.get(entry)).hideWarning();
        }
        for(EditText input : inputsChecked.keySet()){
            if(inputsChecked.keySet().size() == inputsRequired && globalWarningSetted.equals(0)){
                globalWarning.setText(R.string.global_warning);
                globalWarning.setVisibility(View.VISIBLE);
                globalWarningSetted = 1;
            }else if(globalWarningSetted.equals(0)){
                Objects.requireNonNull(inputs.get(input)).showWarning(inputsChecked.get(input));
            }
            input.setBackground(ResourcesCompat.getDrawable(resources, invalidBorder, null));
        }
    }

    public static void waitingForResult(CompletableFuture completableFuture){
        int load = 0;
        while (!completableFuture.isDone()){
            if(load == 0){
                Log.d("LOADING", "...");
                load = 1;
            }
        }
    }

}
