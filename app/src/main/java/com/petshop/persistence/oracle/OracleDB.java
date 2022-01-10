package com.petshop.persistence.oracle;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.RequiresApi;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.petshop.ui.util.ErrorFragment;
import com.petshop.ui.util.LoadingFragment;
import com.petshop.ui.util.SuccessFragment;
import com.petshop.util.ResultData;
import com.petshop.util.Util;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;

public abstract class OracleDB{

    protected final RequestQueue queue;
    protected final Context context;
    protected final String PRODUCTS_URL;
    protected final String PRODUCTS_URL_ID;
    protected final String SERVICES_URL;
    protected final String SERVICES_URL_ID;
    protected final String STORES_URL;
    protected final String STORES_URL_ID;
    protected final String FAVORITES_URL;
    protected final String FAVORITES_URL_ID;
    protected final String FAVORITES_URL_USER_ID;
    protected final Integer REQUEST_TIMEOUT;

    protected OracleDB(Context context) {
        this.context = context;
        this.queue = Volley.newRequestQueue(context);
        this.PRODUCTS_URL = "https://g9acbb495f01cb2-petshopdb.adb.sa-saopaulo-1.oraclecloudapps.com/ords/admin/petshop/products/";
        this.PRODUCTS_URL_ID = "https://g9acbb495f01cb2-petshopdb.adb.sa-saopaulo-1.oraclecloudapps.com/ords/admin/petshop/products/id/";
        this.SERVICES_URL = "https://g9acbb495f01cb2-petshopdb.adb.sa-saopaulo-1.oraclecloudapps.com/ords/admin/petshop/services/";
        this.SERVICES_URL_ID = "https://g9acbb495f01cb2-petshopdb.adb.sa-saopaulo-1.oraclecloudapps.com/ords/admin/petshop/services/id/";
        this.STORES_URL = "https://g9acbb495f01cb2-petshopdb.adb.sa-saopaulo-1.oraclecloudapps.com/ords/admin/petshop/stores/";
        this.STORES_URL_ID = "https://g9acbb495f01cb2-petshopdb.adb.sa-saopaulo-1.oraclecloudapps.com/ords/admin/petshop/stores/id/";
        this.FAVORITES_URL = "https://g9acbb495f01cb2-petshopdb.adb.sa-saopaulo-1.oraclecloudapps.com/ords/admin/petshop/favorites/";
        this.FAVORITES_URL_ID = "https://g9acbb495f01cb2-petshopdb.adb.sa-saopaulo-1.oraclecloudapps.com/ords/admin/petshop/favorites/id/";
        this.FAVORITES_URL_USER_ID = "https://g9acbb495f01cb2-petshopdb.adb.sa-saopaulo-1.oraclecloudapps.com/ords/admin/petshop/favorites/user/";
        this.REQUEST_TIMEOUT = 5;
    }

    protected Boolean postObject(JSONObject json, String url, ResultData resultData){
        Boolean[] result = {false};

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, json, response -> {
            SuccessFragment.startFragment(resultData);
            result[0] = true;
            Log.i("INFO", "OracleDB::postObject()>60::object saved on ORACLE");
        }, error -> {
            Log.e("ERROR", "OracleDB::postObject()>60::" + error.getMessage());
            if(resultData != null && resultData.getCurrentView() != null)
                ErrorFragment.startWarning(resultData);
        }){
            @Override
            protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
                if (response.data.length == 0) {
                    byte[] responseData = "{}".getBytes(StandardCharsets.UTF_8);
                    response = new NetworkResponse(response.statusCode, responseData, response.notModified, response.networkTimeMs, response.allHeaders);
                }
                return super.parseNetworkResponse(response);
            }
        };

        queue.add(jsonObjectRequest);

        return result[0];
    }

    protected Boolean putObject(JSONObject json, String url, ResultData resultData){
        Boolean[] result = {false};

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.PUT, url, json, response -> {
            SuccessFragment.startFragment(resultData);
            Log.i("INFO", "product updated on ORACLE");
            result[0] = true;
        }, error -> {
            Log.e("ERROR", "OracleDB::postObject()>60::" + error.getMessage());
            if(resultData != null && resultData.getCurrentView() != null)
                ErrorFragment.startWarning(resultData);
        }){
            @Override
            protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
                if (response.data.length == 0) {
                    byte[] responseData = "{}".getBytes(StandardCharsets.UTF_8);
                    response = new NetworkResponse(response.statusCode, responseData, response.notModified, response.networkTimeMs, response.allHeaders);
                }
                return super.parseNetworkResponse(response);
            }
        };

        queue.add(jsonObjectRequest);

        return result[0];
    }


    protected JSONObject getObject(String url, Integer view){

        JSONObject[] result = {null};

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, response -> {
            try {
                JSONObject object = response.getJSONArray("items").getJSONObject(0);
                result[0] = object;
                Log.i("INFO", "product obtained from ORACLE");
            } catch (JSONException e) {
                Log.e("ERROR", "OracleDB::getObject()>113::" + e.getMessage());
            }
        }, error -> Log.e("ERROR", error.getMessage())){
            @Override
            protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
                if (response.data.length == 0) {
                    byte[] responseData = "{}".getBytes(StandardCharsets.UTF_8);
                    response = new NetworkResponse(response.statusCode, responseData, response.notModified, response.networkTimeMs, response.allHeaders);
                }
                return super.parseNetworkResponse(response);
            }
        };

        queue.add(jsonObjectRequest);

        return result[0];
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    protected void getAllObjects(String url, String key, Integer view, Function<JSONObject, Object> callback) {

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, response -> {
            try {
                JSONArray json = response.getJSONArray("items");
                List<Object> objects = new ArrayList<>();
                for(int i = 0; i < json.length(); i++)
                    objects.add(callback.apply(json.getJSONObject(i)));
                Bundle bundle = new Bundle();
                bundle.putSerializable(key, (Serializable) objects);
                LoadingFragment.downLoading(view, bundle);
            } catch (JSONException e) {
                Log.e("ERROR", "OracleDB::getAllObjects()>142::" + e.getMessage());
            }
            Log.i("INFO", "all objects obtained from ORACLE");
        }, error -> Log.e("ERROR", "OracleDB::getAllObjects()>142::" + error.getMessage())){
            @Override
            protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
                if (response.data.length == 0) {
                    byte[] responseData = "{}".getBytes(StandardCharsets.UTF_8);
                    response = new NetworkResponse(response.statusCode, responseData, response.notModified, response.networkTimeMs, response.allHeaders);
                }
                return super.parseNetworkResponse(response);
            }
        };

        CompletableFuture<Request<JSONObject>> future = CompletableFuture.supplyAsync(() -> queue.add(jsonObjectRequest));
        Util.waitingForResult(future);

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    protected void getAllObjects(String url, String url_2, List<String> keys, Integer view, Function<JSONObject, Object> callback_url, Function<JSONObject, Object> callback_url_2) {

        JsonObjectRequest request_1 = new JsonObjectRequest(Request.Method.GET, url, null, responseProducts -> {
            JsonObjectRequest request_2 = new JsonObjectRequest(Request.Method.GET, url_2, null, responseFavorites -> {
                try {
                    JSONArray array_1 = responseProducts.getJSONArray("items");
                    JSONArray array_2 = responseFavorites.getJSONArray("items");
                    List<Object> list_1 = new ArrayList<>();
                    List<Object> list_2 = new ArrayList<>();
                    for(int i = 0; i < array_1.length(); i++)
                        list_1.add(callback_url.apply(array_1.getJSONObject(i)));
                    for(int i = 0; i < array_2.length(); i++)
                        list_2.add(callback_url_2.apply(array_2.getJSONObject(i)));
                    Bundle bundle = new Bundle();
                    bundle.putSerializable(keys.get(0), (Serializable) list_1);
                    bundle.putSerializable(keys.get(1), (Serializable) list_2);
                    LoadingFragment.downLoading(view, bundle);
                } catch (JSONException e) {
                    Log.e("ERROR", "OracleDB::getAllObjects()>182::" + e.getMessage());
                }
            }, error -> Log.e("ERROR", "OracleDB::getAllObjects()>182::" + error.getMessage())){
                @Override
                protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
                    if (response.data.length == 0) {
                        byte[] responseData = "{}".getBytes(StandardCharsets.UTF_8);
                        response = new NetworkResponse(response.statusCode, responseData, response.notModified, response.networkTimeMs, response.allHeaders);
                    }
                    return super.parseNetworkResponse(response);
                }
            };

            CompletableFuture<Request<JSONObject>> future = CompletableFuture.supplyAsync(() -> queue.add(request_2));
            Util.waitingForResult(future);

            Log.i("INFO", "OracleDB::getAllObjects()>199::all objects obtained from ORACLE");
        }, error -> Log.e("ERROR", "OracleDB::getAllObjects()>199::" + error.getMessage())){
            @Override
            protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
                if (response.data.length == 0) {
                    byte[] responseData = "{}".getBytes(StandardCharsets.UTF_8);
                    response = new NetworkResponse(response.statusCode, responseData, response.notModified, response.networkTimeMs, response.allHeaders);
                }
                return super.parseNetworkResponse(response);
            }
        };

        CompletableFuture<Request<JSONObject>> future = CompletableFuture.supplyAsync(() -> queue.add(request_1));
        Util.waitingForResult(future);


    }


    protected Boolean deleteObject(String url, ResultData resultData){
        Boolean[] result = {false};

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.DELETE, url, null, response -> {
            SuccessFragment.startFragment(resultData);
            Log.i("INFO", "product deleted from ORACLE");
            result[0] = true;
        }, error -> {
            Log.e("ERROR", "OracleDB::postObject()>60::" + error.getMessage());
            if(resultData != null && resultData.getCurrentView() != null)
                ErrorFragment.startWarning(resultData);
        }){
            @Override
            protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
                if (response.data.length == 0) {
                    byte[] responseData = "{}".getBytes(StandardCharsets.UTF_8);
                    response = new NetworkResponse(response.statusCode, responseData, response.notModified, response.networkTimeMs, response.allHeaders);
                }
                return super.parseNetworkResponse(response);
            }
        };

        queue.add(jsonObjectRequest);

        return result[0];
    }

}
