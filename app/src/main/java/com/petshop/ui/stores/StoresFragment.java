package com.petshop.ui.stores;

import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.petshop.R;
import com.petshop.adapters.StoreClientAdapter;
import com.petshop.databinding.FragmentStoresBinding;
import com.petshop.persistence.model.Store;
import com.petshop.persistence.service.StoreService;
import com.petshop.ui.util.LoadingFragment;

import java.util.List;

public class StoresFragment extends Fragment {

    private FragmentStoresBinding binding;

    @RequiresApi(api = Build.VERSION_CODES.O)
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentStoresBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        Toolbar toolbar = requireActivity().findViewById(R.id.toolbar);

        toolbar.post(() -> {
            Drawable d = ResourcesCompat.getDrawable(getResources(), R.drawable.ic_bar_menu, null);
            toolbar.setNavigationIcon(d);
        });

        GridView gridView = root.findViewById(R.id.grid_stores);

        try{
            if(getArguments() != null){
                List<Store> stores = (List<Store>) requireArguments().getSerializable("stores");
                if(stores != null)
                    gridView.setAdapter(new StoreClientAdapter(getContext(), stores));
            }else{
                StoreService storeService = new StoreService(getContext(), R.id.nav_stores);
                storeService.showAll();
                NavController navController = NavHostFragment.findNavController(this);
                LoadingFragment.startLoading(R.id.nav_stores, navController);
            }
        }catch (Exception ex){
            Toast.makeText(getContext(), "error", Toast.LENGTH_SHORT).show();
            Log.e("ERROR", "StoresFragment::setAdapter()::" + ex.getMessage());
        }

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}