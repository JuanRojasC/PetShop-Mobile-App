package com.petshop.ui.managment.products;

import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import com.petshop.R;
import com.petshop.adapters.ProductManagmentAdapter;
import com.petshop.databinding.FragmentManagementProductsBinding;
import com.petshop.persistence.model.Product;
import com.petshop.persistence.service.ProductService;
import com.petshop.ui.util.LoadingFragment;

import java.util.List;

public class ProductsManagementFragment extends Fragment {

    private FragmentManagementProductsBinding binding;

    @RequiresApi(api = Build.VERSION_CODES.O)
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentManagementProductsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        Toolbar toolbar = requireActivity().findViewById(R.id.toolbar);

        toolbar.post(() -> {
            Drawable d = ResourcesCompat.getDrawable(getResources(), R.drawable.ic_bar_menu, null);
            toolbar.setNavigationIcon(d);
        });

        GridView gridView = root.findViewById(R.id.grid_managment_products);

        try{
            if(getArguments() != null){
                List<Product> products = (List<Product>) requireArguments().getSerializable("products");
                if(products != null)
                    gridView.setAdapter(new ProductManagmentAdapter(getContext(), products));
            }else{
                ProductService productService = new ProductService(getContext(), R.id.nav_managment_products);
                productService.showAll();
                NavController navController = NavHostFragment.findNavController(this);
                LoadingFragment.startLoading(R.id.nav_managment_products, navController);
            }

            Button addProduct = root.findViewById(R.id.btnAddProduct);

            addProduct.setOnClickListener(view1 -> {
                NavController navController = Navigation.findNavController(root);
                navController.navigate(R.id.nav_form_product);
            });

        }catch (Exception ex){
            Toast.makeText(getContext(), "error", Toast.LENGTH_SHORT).show();
            Log.e("ERROR", "ProductsManagementFragment::setAdapter()::" + ex.getMessage());
        }

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}
