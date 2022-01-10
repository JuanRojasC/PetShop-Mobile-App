package com.petshop.adapters;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.petshop.R;
import com.petshop.persistence.model.Favorite;
import com.petshop.persistence.model.Product;
import com.petshop.persistence.service.FavoriteService;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class FavoritesAdapter extends BaseAdapter {

    private final Context context;
    private final FavoriteService favoriteService;
    private final List<Favorite> favorites;
    private final List<Product> products;

    public FavoritesAdapter(Context context, List<Product> products ,List<Favorite> favorites) {
        this.context = context;
        this.favorites = favorites == null? new ArrayList<>() : favorites;
        this.products = products == null? new ArrayList<>() : products;
        this.favoriteService = new FavoriteService(context, R.id.nav_favorites);
    }

    @Override
    public int getCount() {
        return favorites.size();
    }

    @Override
    public Object getItem(int i) {
        for(Favorite f : favorites) if(f.getId().equals(i)) return f;
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @SuppressLint({"ViewHolder", "DefaultLocale", "InflateParams", "WrongConstant"})
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.template_favorite, null);

        ImageView imageProduct = view.findViewById(R.id.imgProduct);
        TextView nameProduct = view.findViewById(R.id.txtProductName);
        TextView descriptionProduct = view.findViewById(R.id.txtProductDescription);
        TextView priceProduct = view.findViewById(R.id.txtProductPrice);
        Button deleteFavorite = view.findViewById(R.id.btnDeleteFavorite);

        Favorite favorite = favorites.get(i);
        Product product = findProduct(favorite.getProduct_id());

        nameProduct.setText(product.getName());
        descriptionProduct.setText(product.getDescription());
        priceProduct.setText(String.format("Desde %.3f", product.getPrice()));
        imageProduct.setImageBitmap(BitmapFactory.decodeByteArray(product.getImage(),0, product.getImage().length));

        View btnView = view;

        deleteFavorite.setOnClickListener(view1 -> {
            Toast.makeText(btnView.getContext(), "eliminado de favoritos", Toast.LENGTH_SHORT).show();
            deleteFavoriteProduct(favorite, product);
            NavController navController = Navigation.findNavController((Activity) context, R.id.nav_host_fragment_content_main);
            Bundle bundle = new Bundle();
            bundle.putSerializable("products", (Serializable) this.products);
            bundle.putSerializable("favorites", (Serializable) this.favorites);
            navController.navigate(R.id.nav_favorites, bundle);
        });

        return view;
    }

    private Product findProduct(Integer id){
        for(Product p : products)
            if(p.getId().equals(id)) return p;
        return null;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void deleteFavoriteProduct(Favorite favorite, Product product){
        favoriteService.deleteFavorite(favorite.getId());
        this.favorites.remove(favorite);
        this.products.remove(product);
    }

}
