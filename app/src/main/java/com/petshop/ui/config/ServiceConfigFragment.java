package com.petshop.ui.config;

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
import com.petshop.databinding.FragmentConfigServiceBinding;
import com.petshop.persistence.model.Service;
import com.petshop.persistence.service.ServiceService;
import com.petshop.ui.form.service.ServiceForm;

public class ServiceConfigFragment extends ServiceForm {

    private FragmentConfigServiceBinding binding;

    @RequiresApi(api = Build.VERSION_CODES.O)
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentConfigServiceBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        Toolbar toolbar = requireActivity().findViewById(R.id.toolbar);

        toolbar.post(() -> {
            Drawable d = ResourcesCompat.getDrawable(getResources(), R.drawable.ic_arrow_yellow, null);
            toolbar.setNavigationIcon(d);
        });

        Service service = (Service) requireArguments().get("service");
        ServiceService serviceService = new ServiceService(getContext());
        Button btnUpdateService = root.findViewById(R.id.btnUpdateService);
        Button btnDeleteService = root.findViewById(R.id.btnDeleteService);

        startVariables(root);
        assingTextInputs(service);

        btnUpdateService.setOnClickListener(view1 -> {
            Service serviceToUpdate = checkInputs(service);
            if(serviceToUpdate != null){
                btnUpdateService.setText(R.string.updating_text);
                btnUpdateService.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.round_disable_button, null));
                btnUpdateService.setEnabled(false);
                serviceService.updateService(serviceToUpdate, root);
            }
        });

        btnDeleteService.setOnClickListener(view1 -> {
            btnDeleteService.setText(R.string.deleting_text);
            btnDeleteService.setBackground(ResourcesCompat.getDrawable(getResources(), R.color.main_gray, null));
            btnDeleteService.setEnabled(false);
            serviceService.deleteService(service.getId(), root);
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}
