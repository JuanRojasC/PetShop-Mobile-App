package com.petshop.adapters;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.petshop.R;
import com.petshop.persistence.model.Store;

import java.util.List;

public class StoreClientAdapter extends BaseAdapter {

    private final Context context;
    private final List<Store> stores;

    public StoreClientAdapter(Context context, List<Store> stores){
        this.context = context;
        this.stores = stores;
    }

    @Override
    public int getCount() {
        return stores.size();
    }

    @Override
    public Object getItem(int i) {
        for(Store s : stores) if(s.getId().equals(i)) return s;
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.template_stores, null);

        TextView cityStore = view.findViewById(R.id.txtStoreCity);
        TextView addressStore = view.findViewById(R.id.txtStoreAddress);
        TextView descriptionStore = view.findViewById(R.id.txtStoreDescription);
        ImageView imageStore = view.findViewById(R.id.imgStore);
        Button btnVisitStore = view.findViewById(R.id.btnVisitStore);

        Store store = stores.get(i);

        cityStore.setText(store.getCity());
        addressStore.setText(store.getAddress());
        descriptionStore.setText(store.getDescription());
        imageStore.setImageBitmap(BitmapFactory.decodeByteArray(store.getImage(), 0, store.getImage().length));

        View btnView = view;

        btnVisitStore.setOnClickListener(view1 -> {
            Bundle bundle = new Bundle();
            bundle.putDouble("latitude", store.getLatitude());
            bundle.putDouble("longitude", store.getLongitude());
            NavController navController = Navigation.findNavController(btnView);
            navController.navigate(R.id.nav_mapbox, bundle);
        });

        return view;
    }
}
