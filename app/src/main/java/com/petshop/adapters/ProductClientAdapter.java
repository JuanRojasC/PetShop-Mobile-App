package com.petshop.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import com.petshop.R;
import com.petshop.persistence.model.Favorite;
import com.petshop.persistence.model.Product;
import com.petshop.persistence.service.FavoriteService;

import java.util.ArrayList;
import java.util.List;

public class ProductClientAdapter extends BaseAdapter {

    private final Context context;
    private final List<Product> products;
    private final List<Favorite> favorites;
    private final FavoriteService favoriteService;
    private final Integer userId;

    public ProductClientAdapter(Context context, List<Product> products, List<Favorite> favorites, Integer userId) {
        this.context = context;
        this.products = products == null? new ArrayList<>() : products;
        this.favorites = favorites == null? new ArrayList<>() : favorites;
        this.favoriteService = new FavoriteService(context, R.id.nav_products);
        this.userId = userId;
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

    @RequiresApi(api = Build.VERSION_CODES.O)
    @SuppressLint({"ViewHolder", "DefaultLocale", "InflateParams"})
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.template_product, null);

        ImageView imageProduct = view.findViewById(R.id.imgProduct);
        TextView nameProduct = view.findViewById(R.id.txtProductName);
        TextView descriptionProduct = view.findViewById(R.id.txtProductDescription);
        TextView priceProduct = view.findViewById(R.id.txtProductPrice);
        ImageButton addFavorite = view.findViewById(R.id.btnAddFavorite);

        Product product = products.get(i);
        Favorite favorite = findFavorite(product.getId());

        nameProduct.setText(product.getName());
        descriptionProduct.setText(product.getDescription());
        priceProduct.setText(String.format("Desde %.3f", product.getPrice()));
        imageProduct.setImageBitmap(BitmapFactory.decodeByteArray(product.getImage(),0, product.getImage().length));

        View btnView = view;

        if(favorite != null){
            addFavorite.setImageResource(R.drawable.ic_favorites);
            addFavorite.setOnClickListener(view1 -> {
                favoriteService.deleteFavorite(favorite.getId());
                addFavorite.setImageResource(R.drawable.ic_add_favorite);
                Toast.makeText(btnView.getContext(), "eliminado de favoritos", Toast.LENGTH_SHORT).show();
            });
        }else{
            addFavorite.setOnClickListener(view1 -> {
                favoriteService.insertFavorite(new Favorite(null, product.getId(), userId));
                addFavorite.setImageResource(R.drawable.ic_favorites);
                Toast.makeText(btnView.getContext(), "agregado a favoritos", Toast.LENGTH_SHORT).show();
            });
        }

        return view;
    }

    private Favorite findFavorite(Integer productId){
        for(Favorite f : favorites) if(f.getProduct_id().equals(productId)) return f;
        return null;
    }
}
