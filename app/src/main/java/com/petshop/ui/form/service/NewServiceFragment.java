package com.petshop.ui.form.service;

import android.annotation.SuppressLint;
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
import com.petshop.databinding.FragmentFormServiceBinding;
import com.petshop.persistence.db.DBHelper;
import com.petshop.persistence.model.Service;
import com.petshop.persistence.service.ServiceService;


public class NewServiceFragment extends ServiceForm {

    private FragmentFormServiceBinding binding;

    @SuppressLint({"IntentReset", "QueryPermissionsNeeded"})
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        binding = FragmentFormServiceBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        Toolbar toolbar = requireActivity().findViewById(R.id.toolbar);

        if(toolbar != null) {
            toolbar.post(() -> {
                Drawable d = ResourcesCompat.getDrawable(getResources(), R.drawable.ic_arrow_yellow, null);
                toolbar.setNavigationIcon(d);
            });
        }

        ServiceService serviceService = new ServiceService(getContext(), R.id.nav_managment_services);
        Button btnSubmit = root.findViewById(R.id.btnAddService);

        startVariables(root);
        startBtnChooseImage(btnChooseImage);

        btnSubmit.setOnClickListener(view1 -> {
            Service service = checkInputs(null);
            if(service != null){
                btnSubmit.setText(R.string.saving_text);
                btnSubmit.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.round_disable_button, null));
                btnSubmit.setEnabled(false);
                serviceService.insertService(service, root);
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
