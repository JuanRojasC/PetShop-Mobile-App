package com.petshop.persistence.service;

import android.content.Context;
import android.os.Build;
import android.util.Log;
import android.view.View;

import androidx.annotation.RequiresApi;

import com.petshop.R;
import com.petshop.persistence.model.Store;
import com.petshop.persistence.oracle.OracleDB;
import com.petshop.util.ResultData;
import com.petshop.util.Util;

import org.json.JSONException;
import org.json.JSONObject;

public class StoreService extends OracleDB {

    private final Integer currentView;

    public StoreService(Context context) {
        super(context);
        this.currentView = null;
    }

    public StoreService(Context context, Integer currentView) {
        super(context);
        this.currentView = currentView;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void showAll(){
        getAllObjects(STORES_URL, "stores", currentView, StoreService::mapJsonToStore);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public Boolean insertStore(Store store, View view){
        Boolean result = false;
        JSONObject json = mapStoreToJson(store);
        if(json != null){
            ResultData resultData = new ResultData(view, R.id.nav_managment_stores, currentView, "Tienda Agregada", "Error al agregar tienda");
            result = postObject(json, STORES_URL, resultData);
        }
        return result;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public Boolean updateStore(Store store, View view){
        if(store.getId() == null) return false;
        Boolean result = false;
        JSONObject json = mapStoreToJson(store);
        if(json != null){
            ResultData resultData = new ResultData(view, R.id.nav_managment_stores, currentView, "Tienda Actualizada", "Error al actualizar tienda");
            result = putObject(json, STORES_URL, resultData);
        }
        return result;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public Store getStoreById(Integer id){
        JSONObject json = getObject(STORES_URL_ID + id, currentView);
        if(json != null) return mapJsonToStore(json);
        return null;
    }

    public Boolean deleteStore(Integer id, View view){
        ResultData resultData = new ResultData(view, R.id.nav_managment_stores, currentView, "Tienda Eliminada", "Error al eliminar tienda");
        return deleteObject(STORES_URL_ID + id, resultData);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private static JSONObject mapStoreToJson(Store store){
        try{
            JSONObject json = new JSONObject();
            json.put("id", store.getId());
            json.put("address", store.getAddress());
            json.put("description", store.getDescription());
            json.put("city", store.getCity());
            json.put("state", store.getState());
            json.put("latitude", store.getLatitude());
            json.put("longitude", store.getLongitude());
            json.put("image", Util.convertByteToString(store.getImage()));
            return json;
        }catch (Exception ex){
            Log.e("ERROR", "storeService::mapJsonToStore()::" + ex.getMessage());
            return null;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private static Store mapJsonToStore(JSONObject json){
        try{
            Store store = new Store();
            store.setId(json.isNull("id")? null : json.getInt("id"));
            store.setAddress(json.isNull("address")? null : json.getString("address"));
            store.setDescription(json.isNull("description")? null : json.getString("description"));
            store.setCity(json.isNull("city")? null : json.getString("city"));
            store.setState(json.isNull("state")? null : json.getString("state"));
            store.setLatitude(json.isNull("latitude")? null : json.getDouble("latitude"));
            store.setLongitude(json.isNull("longitude")? null : json.getDouble("longitude"));
            store.setImage(Util.stringToByte(json.isNull("image")? null : json.getString("image")));
            return store;
        }catch (JSONException ex){
            Log.e("ERROR", "storeService::mapStoreToJson()::" + ex.getMessage());
            return null;
        }
    }
}
