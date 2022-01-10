package com.petshop.ui.start;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import com.petshop.R;
import com.petshop.databinding.FragmentStartBinding;

public class StartFragment extends Fragment {

    private FragmentStartBinding binding;
    private NavController navController;
    private LinearLayout btnProducts, btnServices, btnStores, btnFavorites, btnManagmentProducts, btnManagmentServices, btnManagmentStores;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentStartBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        Toolbar toolbar = getActivity().findViewById(R.id.toolbar);

        if(toolbar != null) {
            toolbar.post(() -> {
                Drawable d = getResources().getDrawable(R.drawable.ic_bar_menu);
                toolbar.setNavigationIcon(d); });
        }

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        navController = Navigation.findNavController(view);
        btnProducts = view.findViewById(R.id.btnLayoutProducts);
        btnServices = view.findViewById(R.id.btnLayoutServices);
        btnStores = view.findViewById(R.id.btnLayoutStores);
        btnFavorites = view.findViewById(R.id.btnLayoutFavorites);
        btnManagmentProducts = view.findViewById(R.id.btnLayoutProductsManagment);
        btnManagmentServices = view.findViewById(R.id.btnLayoutServicesManagment);
        btnManagmentStores = view.findViewById(R.id.btnLayoutStoresManagment);

        btnProducts.setOnClickListener(view1 -> {
            if(navController != null)
                navController.navigate(R.id.nav_products);
        });

        btnServices.setOnClickListener(view1 -> {
            if(navController != null)
                navController.navigate(R.id.nav_services);
        });

        btnStores.setOnClickListener(view1 -> {
            if(navController != null)
                navController.navigate(R.id.nav_stores);
        });

        btnFavorites.setOnClickListener(view1 -> {
            if(navController != null)
                navController.navigate(R.id.nav_favorites);
        });

        btnManagmentProducts.setOnClickListener(view1 -> {
            if(navController != null)
                navController.navigate(R.id.nav_managment_products);
        });

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}
