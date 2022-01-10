package com.petshop.ui.form.product;

import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.res.ResourcesCompat;

import com.petshop.R;
import com.petshop.databinding.FragmentFormProductBinding;
import com.petshop.persistence.model.Product;
import com.petshop.persistence.service.ProductService;


public class NewProductFragment extends ProductForm {

    private FragmentFormProductBinding binding;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        binding = FragmentFormProductBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        Toolbar toolbar = requireActivity().findViewById(R.id.toolbar);

        if(toolbar != null) {
            toolbar.post(() -> {
                Drawable d = ResourcesCompat.getDrawable(getResources(), R.drawable.ic_arrow_blue, null);
                toolbar.setNavigationIcon(d);
            });
        }

        ProductService productService = new ProductService(getContext(), R.id.nav_managment_products);
        Button btnSubmit = root.findViewById(R.id.btnAddProduct);

        startVariables(root);
        startBtnChooseImage(btnChooseImage);

        btnSubmit.setOnClickListener(view1 -> {
            Product productToSave = checkInputs(null);
            if(productToSave != null){
                btnSubmit.setText(R.string.saving_text);
                btnSubmit.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.round_disable_button, null));
                btnSubmit.setEnabled(false);
                productService.insertProduct(productToSave, root);
            }

        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
