package com.petshop.persistence.service;

import android.content.Context;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import com.petshop.persistence.model.Favorite;
import com.petshop.persistence.oracle.OracleDB;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

public class FavoriteService extends OracleDB {

    private final Integer currentView;

    public FavoriteService(Context context, Integer currentView) {
        super(context);
        this.currentView = currentView;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void showAll(){
        getAllObjects(PRODUCTS_URL, FAVORITES_URL_USER_ID + 1, Arrays.asList("products", "favorites"), currentView, ProductService::mapJsonToProduct, FavoriteService::mapJsonToFavorite);
    }

    public Boolean insertFavorite(Favorite favorite){
        Boolean result = false;
        JSONObject json = mapFavoriteToJson(favorite);
        if(json != null){
            result = postObject(json, FAVORITES_URL, null);
        }
        return result;
    }

    public Favorite getFavoriteById(Integer id){
        JSONObject json = getObject(FAVORITES_URL_ID + id, currentView);
        return mapJsonToFavorite(json);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public Boolean deleteFavorite(Integer id){
        try {
            deleteObject(FAVORITES_URL_ID + id, null);
            return true;
        }catch (Exception ex){
            Log.e("ERROR", "FavoriteService::deleteFavorite()::" + ex.getMessage());
            return false;
        }
    }

    public static Favorite mapJsonToFavorite(JSONObject json){
        try {
            Favorite favorite = new Favorite();
            favorite.setId(json.getInt("id"));
            favorite.setProduct_id(json.getInt("product_id"));
            favorite.setUser_id(json.getInt("user_id"));
            return favorite;
        } catch (JSONException e) {
            Log.e("ERROR", "FavoriteService::mapJsonToFavorite()::" + e.getMessage());
            return null;
        }
    }

    public static JSONObject mapFavoriteToJson(Favorite favorite){
        try {
            JSONObject json = new JSONObject();
            json.put("id", favorite.getId());
            json.put("product_id", favorite.getProduct_id());
            json.put("user_id", favorite.getUser_id());
            return json;
        }catch (Exception ex){
            Log.e("ERROR", "FavoriteService::mapFavoriteToJson()::" + ex.getMessage());
            return null;
        }
    }

}
