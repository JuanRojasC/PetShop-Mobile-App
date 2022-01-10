package com.petshop.persistence.service;

import android.content.Context;
import android.os.Build;
import android.util.Log;
import android.view.View;

import androidx.annotation.RequiresApi;

import com.petshop.R;
import com.petshop.persistence.model.Service;
import com.petshop.persistence.oracle.OracleDB;
import com.petshop.util.ResultData;
import com.petshop.util.Util;

import org.json.JSONException;
import org.json.JSONObject;

public class ServiceService extends OracleDB {

    private final Integer currentView;

    public ServiceService(Context context) {
        super(context);
        this.currentView = null;
    }

    public ServiceService(Context context, Integer currentView) {
        super(context);
        this.currentView = currentView;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void showAll(){
        getAllObjects(SERVICES_URL, "services", currentView, ServiceService::mapJsonToService);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public Boolean insertService(Service service, View view){
        Boolean result = false;
        JSONObject json = mapServiceToJson(service);
        if(json != null){
            ResultData resultData = new ResultData(view, R.id.nav_managment_services, currentView, "Servicio Guardado", "Error al guardar servicio");
            result = postObject(json, SERVICES_URL, resultData);
        }
        return result;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public Boolean updateService(Service service, View view){
        if(service.getId() == null) return false;
        Boolean result = false;
        JSONObject json = mapServiceToJson(service);
        if(json != null){
            ResultData resultData = new ResultData(view, R.id.nav_managment_services, currentView, "Servicio Actualizado", "Error al actualizar servicio");
            result = putObject(json, SERVICES_URL, resultData);
        }
        return result;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public Service getServiceById(Integer id){
        JSONObject json = getObject(SERVICES_URL_ID + id, currentView);
        if(json != null) return mapJsonToService(json);
        return null;
    }

    public Boolean deleteService(Integer id, View view){
        ResultData resultData = new ResultData(view, R.id.nav_managment_services, currentView, "Servicio Eliminado", "Error al eliminar servicio");
        return deleteObject(SERVICES_URL_ID + id, resultData);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private static JSONObject mapServiceToJson(Service service){
        try{
            JSONObject json = new JSONObject();
            json.put("id", service.getId());
            json.put("name", service.getName());
            json.put("description", service.getDescription());
            json.put("price", service.getPrice());
            json.put("mode", service.getMode());
            json.put("image", Util.convertByteToString(service.getImage()));
            return json;
        }catch (Exception ex){
            Log.e("ERROR", "serviceService::mapJsonToService()::" + ex.getMessage());
            return null;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private static Service mapJsonToService(JSONObject json){
        try{
            Service service = new Service();
            service.setId(json.isNull("id")? null : json.getInt("id"));
            service.setName(json.isNull("name")? null : json.getString("name"));
            service.setDescription(json.isNull("description")? null : json.getString("description"));
            service.setPrice(json.isNull("price")? null : json.getDouble("price"));
            service.setMode(json.isNull("mode_service")? null : json.getString("mode_service"));
            service.setImage(Util.stringToByte(json.isNull("image")? null : json.getString("image")));
            return service;
        }catch (JSONException ex){
            Log.e("ERROR", "serviceService::mapServiceToJson()::" + ex.getMessage());
            return null;
        }
    }
}
