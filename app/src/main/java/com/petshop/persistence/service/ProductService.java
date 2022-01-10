package com.petshop.persistence.service;

import android.content.Context;
import android.os.Build;
import android.util.Log;
import android.view.View;

import androidx.annotation.RequiresApi;

import com.petshop.R;
import com.petshop.persistence.model.Product;
import com.petshop.persistence.oracle.OracleDB;
import com.petshop.util.ResultData;
import com.petshop.util.Util;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

public class ProductService extends OracleDB {

    private final Integer currentView;

    @RequiresApi(api = Build.VERSION_CODES.O)
    public ProductService(Context context, Integer currentView) {
        super(context);
        this.currentView = currentView;
    }

    public ProductService(Context context) {
        super(context);
        this.currentView = null;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void showAll(){
        getAllObjects(PRODUCTS_URL, FAVORITES_URL, Arrays.asList("products", "favorites"), currentView, ProductService::mapJsonToProduct, FavoriteService::mapJsonToFavorite);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public Boolean insertProduct(Product product, View view){
        Boolean result = false;
        Log.e("DATA", String.valueOf(currentView));
        JSONObject json = mapProductToJson(product);
        if(json != null){
            ResultData resultData = new ResultData(view, R.id.nav_managment_products, currentView, "Producto Guardado", "Error al guardar producto");
            result = postObject(json, PRODUCTS_URL, resultData);
        }
        return result;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public Boolean updateProduct(Product product, View view){
        if(product.getId() == null) return false;
        Boolean result = false;
        JSONObject json = mapProductToJson(product);
        if(json != null){
            ResultData resultData = new ResultData(view, R.id.nav_managment_products, currentView, "Producto Actualizado", "Error al actualizar producto");
            result = putObject(json, PRODUCTS_URL, resultData);
        }
        return result;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public Product findProductById(Integer id){
        JSONObject json = getObject(PRODUCTS_URL_ID + id, currentView);
        if(json != null) return mapJsonToProduct(json);
        return null;
    }

    public Boolean deleteProduct(Integer productId, View view){
        ResultData resultData = new ResultData(view, R.id.nav_managment_products, currentView, "Producto Eliminado", "Error al eliminar producto");
        return deleteObject(PRODUCTS_URL_ID + productId, resultData);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static Product mapJsonToProduct(JSONObject json){
        try {
            Product product = new Product();
            product.setId(json.isNull("id")? null : json.getInt("id"));
            product.setName(json.isNull("name")? null : json.getString("name"));
            product.setDescription(json.isNull("description")? null : json.getString("description"));
            product.setPrice(json.isNull("price")? null : json.getDouble("price"));
            product.setQuantity(json.isNull("quantity")? null : json.getDouble("quantity"));
            product.setImage(Util.stringToByte(json.isNull("image")? null : json.getString("image")));
            return product;
        } catch (JSONException e) {
            Log.e("ERROR", "ProductService::mapJsonToProduct()::" + e.getMessage());
            return null;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private static JSONObject mapProductToJson(Product product){
        try {
            JSONObject json = new JSONObject();
            json.put("id", product.getId());
//            json.put("name", product.getName());
            json.put("description", product.getDescription());
            json.put("price", product.getPrice());
            json.put("quantity", product.getQuantity());
            json.put("image", Util.convertByteToString(product.getImage()));
            return json;
        }catch (Exception ex){
            Log.e("ERROR", "ProductService::mapProductToJson()::" + ex.getMessage());
            return null;
        }
    }

}
