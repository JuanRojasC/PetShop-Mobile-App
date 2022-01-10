package com.petshop.ui.products;

import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.Toast;;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.petshop.R;
import com.petshop.adapters.ProductClientAdapter;
import com.petshop.databinding.FragmentProductsBinding;
import com.petshop.persistence.model.Favorite;
import com.petshop.persistence.model.Product;
import com.petshop.persistence.service.ProductService;
import com.petshop.ui.util.LoadingFragment;

import java.util.List;

public class ProductsFragment extends Fragment {

    private FragmentProductsBinding binding;

    @RequiresApi(api = Build.VERSION_CODES.O)
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentProductsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        Toolbar toolbar = requireActivity().findViewById(R.id.toolbar);

        toolbar.post(() -> {
            Drawable d = ResourcesCompat.getDrawable(getResources(), R.drawable.ic_bar_menu, null);
            toolbar.setNavigationIcon(d);
        });

        GridView gridView = root.findViewById(R.id.grid_products);

        try{
            if(getArguments() != null){
                List<Product> products = (List<Product>) requireArguments().getSerializable("products");
                List<Favorite> favorites = (List<Favorite>) requireArguments().getSerializable("favorites");
                if(products != null)
                    gridView.setAdapter(new ProductClientAdapter(getContext(), products, favorites, 1));
            }else{
                ProductService productService = new ProductService(getContext(), R.id.nav_products);
                productService.showAll();
                NavController navController = NavHostFragment.findNavController(this);
                LoadingFragment.startLoading(R.id.nav_products, navController);
            }
        }catch (Exception ex){
            Toast.makeText(getContext(), "error", Toast.LENGTH_SHORT).show();
            Log.e("ERROR", "ProductsFragment::setAdapter()::" + ex.getMessage());
        }

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}