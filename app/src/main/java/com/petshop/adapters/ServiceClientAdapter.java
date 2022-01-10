package com.petshop.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.petshop.R;
import com.petshop.persistence.model.Service;

import java.util.ArrayList;
import java.util.List;

public class ServiceClientAdapter extends BaseAdapter {

    private final Context context;
    private final List<Service> services;

    public ServiceClientAdapter(Context context, List<Service> services) {
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
        return 0;
    }

    @SuppressLint({"ViewHolder", "InflateParams"})
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.template_service, null);

        TextView nameService = view.findViewById(R.id.txtServiceName);
        TextView descriptionService = view.findViewById(R.id.txtServiceDescription);
        TextView priceService = view.findViewById(R.id.txtServicePrice);
        ImageView imageService = view.findViewById(R.id.imgService);

        Service service = services.get(i);
        String servicePrice = String.format("Desde %.3f" + (service.getMode().equals("null")? "" : "/" + service.getMode()), service.getPrice());

        nameService.setText(service.getName());
        descriptionService.setText(service.getDescription());
        priceService.setText(servicePrice);
        imageService.setImageBitmap(BitmapFactory.decodeByteArray(service.getImage(),0, service.getImage().length));

        return view;
    }
}
