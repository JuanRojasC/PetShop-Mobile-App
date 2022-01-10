package com.petshop.ui.config;

import android.annotation.SuppressLint;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.res.ResourcesCompat;

import com.petshop.R;
import com.petshop.databinding.FragmentConfigProductBinding;
import com.petshop.persistence.model.Product;
import com.petshop.persistence.service.ProductService;
import com.petshop.ui.form.product.ProductForm;


public class ProductConfigFragment extends ProductForm {

    private FragmentConfigProductBinding binding;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @SuppressLint("SetTextI18n")
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentConfigProductBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        Toolbar toolbar = requireActivity().findViewById(R.id.toolbar);

        toolbar.post(() -> {
            Drawable d = ResourcesCompat.getDrawable(getResources(), R.drawable.ic_arrow_blue, null);
            toolbar.setNavigationIcon(d);
        });

        Product product = (Product) requireArguments().get("product");
        ProductService productService = new ProductService(getContext());
        Button btnUpdateProduct = root.findViewById(R.id.btnUpdateProduct);
        Button btnDeleteProduct = root.findViewById(R.id.btnDeleteProduct);

        startVariables(root);
        startBtnChooseImage(btnChooseImage);
        assingTextInputs(product);

        btnUpdateProduct.setOnClickListener(view1 -> {
            Product productToUpdate = checkInputs(product);
            if(productToUpdate != null){
                btnUpdateProduct.setText(R.string.updating_text);
                btnUpdateProduct.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.round_disable_button, null));
                btnUpdateProduct.setEnabled(false);
                productService.updateProduct(productToUpdate, root);
            }
        });

        btnDeleteProduct.setOnClickListener(view1 -> {
            btnDeleteProduct.setText(R.string.deleting_text);
            btnDeleteProduct.setBackground(ResourcesCompat.getDrawable(getResources(), R.color.main_gray, null));
            btnDeleteProduct.setEnabled(false);
            productService.deleteProduct(product.getId(), root);
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}
