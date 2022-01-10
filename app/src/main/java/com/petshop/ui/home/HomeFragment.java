package com.petshop.ui.home;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.petshop.R;
import com.petshop.databinding.FragmentHomeBinding;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private NavController navController;
    private LinearLayout btnProducts, btnServices, btnStores, btnFavorites, btnManagementProducts, btnManagementServices, btnManagementStores, btnManagementUsers;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentHomeBinding.inflate(inflater, container, false);
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
        btnManagementProducts = view.findViewById(R.id.btnLayoutProductsManagment);
        btnManagementServices = view.findViewById(R.id.btnLayoutServicesManagment);
        btnManagementStores = view.findViewById(R.id.btnLayoutStoresManagment);
        btnManagementUsers = view.findViewById(R.id.btnLayoutUsersManagment);

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

        btnManagementProducts.setOnClickListener(view1 -> {
            if(navController != null)
                navController.navigate(R.id.nav_managment_products);
        });

        btnManagementServices.setOnClickListener(view1 ->{
            if(navController != null){
                navController.navigate(R.id.nav_managment_services);
            }
        });

        btnManagementStores.setOnClickListener(view1 -> {
            if(navController != null){
                navController.navigate(R.id.nav_managment_stores);
            }
        });

        btnManagementUsers.setOnClickListener(view1 -> {
            Toast.makeText(getContext(), "Aun no disponible", Toast.LENGTH_SHORT).show();
        });

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
