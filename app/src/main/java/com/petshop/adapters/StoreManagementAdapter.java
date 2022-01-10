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
import com.petshop.persistence.model.Store;

import java.util.ArrayList;
import java.util.List;

public class StoreManagementAdapter extends BaseAdapter {

    private final Context context;
    private final List<Store> stores;

    public StoreManagementAdapter(Context context, List<Store> stores) {
        this.context = context;
        this.stores = stores == null? new ArrayList<>() : stores;
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
        for(Store s : stores) if(s.getId().equals(i)) return i;
        return 0;
    }

    @SuppressLint({"ViewHolder", "InflateParams"})
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.template_management_store, null);

        LinearLayout btnLayoutStore = view.findViewById(R.id.btnLayoutManagmentStore);
        TextView cityStore = view.findViewById(R.id.txtStoreCity);
        TextView addressStore = view.findViewById(R.id.txtStoreAddress);
        ImageView imageStore = view.findViewById(R.id.imgStore);

        Store store = stores.get(i);

        cityStore.setText(store.getCity());
        addressStore.setText(store.getAddress());
        imageStore.setImageBitmap(BitmapFactory.decodeByteArray(store.getImage(), 0, store.getImage().length));

        View btnView = view;

        btnLayoutStore.setOnClickListener( view1 -> {
            Bundle bundle = new Bundle();
            bundle.putSerializable("store", store);
            NavController navController = Navigation.findNavController(btnView);
            navController.navigate(R.id.nav_config_store, bundle);
        } );

        return view;
    }
}
