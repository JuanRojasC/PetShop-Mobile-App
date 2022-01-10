package com.petshop.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.petshop.R;
import com.petshop.persistence.model.Service;

import java.util.ArrayList;
import java.util.List;

public class ServiceManagmentAdapter extends BaseAdapter {

    private final Context context;
    private final List<Service> services;

    public ServiceManagmentAdapter(Context context, List<Service> services) {
        this.context = context;
        this.services = services == null? new ArrayList<>() : services;
    }

    @Override
    public int getCount() {
        return services.size();
    }

    @Override
    public Object getItem(int i) {
        for(Service s : services) if(s.getId().equals(i)) return s;
        return null;
    }

    @Override
    public long getItemId(int i) {
        for(Service s : services) if(s.getId().equals(i)) return i;
        return 0L;
    }

    @SuppressLint({"ViewHolder", "InflateParams"})
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.template_management_service, null);

        LinearLayout btnLayoutService = view.findViewById(R.id.btnLayoutManagmentService);
        TextView nameService = view.findViewById(R.id.txtServiceName);
        ImageView imageService = view.findViewById(R.id.imgService);

        Service service = services.get(i);

        nameService.setText(service.getName());
        imageService.setImageBitmap(BitmapFactory.decodeByteArray(service.getImage(),0, service.getImage().length));

        View btnView = view;

        btnLayoutService.setOnClickListener(view1 -> {
            Bundle bundle = new Bundle();
            bundle.putSerializable("service", service);

            NavController navController = Navigation.findNavController(btnView);
            navController.navigate(R.id.nav_config_service, bundle);
        });

        return view;
    }

}
