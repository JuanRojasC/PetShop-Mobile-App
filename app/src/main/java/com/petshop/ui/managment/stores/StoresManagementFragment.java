package com.petshop.ui.managment.stores;

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
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import com.petshop.R;
import com.petshop.adapters.StoreClientAdapter;
import com.petshop.adapters.StoreManagementAdapter;
import com.petshop.databinding.FragmentManagementStoresBinding;
import com.petshop.persistence.model.Store;
import com.petshop.persistence.service.StoreService;
import com.petshop.ui.util.LoadingFragment;

import java.util.List;

public class StoresManagementFragment extends Fragment {

    private FragmentManagementStoresBinding binding;

    @RequiresApi(api = Build.VERSION_CODES.O)
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentManagementStoresBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        Toolbar toolbar = requireActivity().findViewById(R.id.toolbar);

        toolbar.post(() -> {
            Drawable d = getResources().getDrawable(R.drawable.ic_bar_menu);
            toolbar.setNavigationIcon(d);
        });

        GridView gridView = root.findViewById(R.id.grid_management_stores);

        try {
            if(getArguments() != null){
                List<Store> stores = (List<Store>) requireArguments().getSerializable("stores");
                if(stores != null)
                    gridView.setAdapter(new StoreManagementAdapter(getContext(), stores));
            }else{
                StoreService storeService = new StoreService(getContext(), R.id.nav_managment_stores);
                storeService.showAll();
                NavController navController = NavHostFragment.findNavController(this);
                LoadingFragment.startLoading(R.id.nav_managment_stores, navController);
            }
            Button addStore = root.findViewById(R.id.btnAddStore);

            addStore.setOnClickListener(view1 -> {
                NavController navController = Navigation.findNavController(root);
                navController.navigate(R.id.nav_form_store);
            });

        }catch (Exception ex){
            Toast.makeText(getContext(), "error", Toast.LENGTH_SHORT).show();
            Log.e("ERROR", "StoresManagementFragment::setAdapter()::" + ex.getMessage());
        }

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}
