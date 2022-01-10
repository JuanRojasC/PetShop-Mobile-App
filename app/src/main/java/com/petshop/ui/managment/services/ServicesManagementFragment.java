package com.petshop.ui.managment.services;

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
import com.petshop.adapters.ServiceManagmentAdapter;
import com.petshop.databinding.FragmentManagementServicesBinding;
import com.petshop.persistence.model.Service;
import com.petshop.persistence.service.ServiceService;
import com.petshop.ui.util.LoadingFragment;

import java.util.List;

public class ServicesManagementFragment extends Fragment {

    private FragmentManagementServicesBinding binding;

    @RequiresApi(api = Build.VERSION_CODES.O)
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentManagementServicesBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        Toolbar toolbar = requireActivity().findViewById(R.id.toolbar);

        toolbar.post(() -> {
            Drawable d = ResourcesCompat.getDrawable(getResources(), R.drawable.ic_bar_menu, null);
            toolbar.setNavigationIcon(d);
        });

        GridView gridView = root.findViewById(R.id.grid_managment_services);

        try{
            if(getArguments() != null){
                List<Service> services = (List<Service>) requireArguments().getSerializable("services");
                if(services != null)
                    gridView.setAdapter(new ServiceManagmentAdapter(getContext(), services));
            }else{
                ServiceService serviceService = new ServiceService(getContext(), R.id.nav_managment_services);
                serviceService.showAll();
                NavController navController = NavHostFragment.findNavController(this);
                LoadingFragment.startLoading(R.id.nav_managment_services, navController);
            }

            Button addService = root.findViewById(R.id.btnAddService);

            addService.setOnClickListener(view1 -> {
                NavController navController = Navigation.findNavController(root);
                navController.navigate(R.id.nav_form_service);
            });

        }catch (Exception ex){
            Toast.makeText(getContext(), "error", Toast.LENGTH_SHORT).show();
            Log.e("ERROR", "ServicesManagementFragment::setAdapter()::" + ex.getMessage());
        }

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}
