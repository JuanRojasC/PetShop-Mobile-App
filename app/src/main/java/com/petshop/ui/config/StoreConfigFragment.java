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
import com.petshop.databinding.FragmentConfigStoreBinding;
import com.petshop.persistence.model.Store;
import com.petshop.persistence.service.StoreService;
import com.petshop.ui.form.store.StoreForm;

public class StoreConfigFragment extends StoreForm {

    private FragmentConfigStoreBinding binding;

    @RequiresApi(api = Build.VERSION_CODES.O)
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentConfigStoreBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        Toolbar toolbar = requireActivity().findViewById(R.id.toolbar);

        toolbar.post(() -> {
            Drawable d = ResourcesCompat.getDrawable(getResources(), R.drawable.ic_arrow_red, null);
            toolbar.setNavigationIcon(d);
        });

        StoreService storeService = new StoreService(getContext());
        Store store = (Store) requireArguments().get("store");
        Button btnUpdateStore = root.findViewById(R.id.btnUpdateStore);
        Button btnDeleteStore = root.findViewById(R.id.btnDeleteStore);

        startVariables(root);
        assignTextInputs(store);

        btnUpdateStore.setOnClickListener(view1 -> {
            Store storeToUpdate = checkInputs(store);
            if(storeToUpdate != null){
                btnUpdateStore.setText(R.string.updating_text);
                btnUpdateStore.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.round_disable_button, null));
                btnUpdateStore.setEnabled(false);
                storeService.updateStore(storeToUpdate, root);
            }
        });

        btnDeleteStore.setOnClickListener(view1 -> {
            btnDeleteStore.setText(R.string.deleting_text);
            btnDeleteStore.setBackground(ResourcesCompat.getDrawable(getResources(), R.color.main_gray, null));
            btnDeleteStore.setEnabled(false);
            storeService.deleteStore(store.getId(), root);
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}
