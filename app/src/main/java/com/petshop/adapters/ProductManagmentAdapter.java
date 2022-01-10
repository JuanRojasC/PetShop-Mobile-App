package com.petshop.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.FragmentActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.petshop.R;
import com.petshop.persistence.db.DBHelper;
import com.petshop.persistence.model.Product;
import com.petshop.persistence.service.ProductService;
import com.petshop.ui.config.ProductConfigFragment;

import java.util.ArrayList;
import java.util.List;

public class ProductManagmentAdapter extends BaseAdapter {

    private final Context context;
    private final List<Product> products;

    public ProductManagmentAdapter(Context context, List<Product> products) {
        this.context = context;
        this.products = products == null? new ArrayList<>() : products;
    }

    @Override
    public int getCount() {
        return products.size();
    }

    @Override
    public Object getItem(int i) {
        for(Product p: products) if(p.getId().equals(i)) return p;
        return null;
    }

    @Override
    public long getItemId(int i) {
        for(Product p : products) if(p.getId().equals(i)) return i;
        return 0L;
    }

    @SuppressLint({"ViewHolder", "InflateParams"})
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.template_managment_product, null);

        LinearLayout btnLayoutProduct = view.findViewById(R.id.btnLayoutManagmentProduct);
        ImageView imageProduct = view.findViewById(R.id.imgProduct);
        TextView nameProduct = view.findViewById(R.id.txtProductName);

        Product product = products.get(i);

        View btnView = view;

        nameProduct.setText(product.getName());
        imageProduct.setImageBitmap(BitmapFactory.decodeByteArray(product.getImage(),0, product.getImage().length));

        btnLayoutProduct.setOnClickListener(view1 -> {
            Bundle bundle = new Bundle();
            bundle.putSerializable("product", product);

            NavController navController = Navigation.findNavController(btnView);
            navController.navigate(R.id.nav_config_product, bundle);
        });

        return view;
    }

    public ProductManagmentAdapter fillData(){
        this.products.addAll(new ArrayList<>());
        return this;
    }
}
