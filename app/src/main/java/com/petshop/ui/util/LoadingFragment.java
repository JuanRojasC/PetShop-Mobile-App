package com.petshop.ui.util;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import com.petshop.R;
import com.petshop.databinding.FgLoadingBinding;

public class LoadingFragment extends Fragment {

    private FgLoadingBinding binding;
    private static Integer currentFragmentId;
    private static NavController navController;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding = FgLoadingBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        Toolbar toolbar = getActivity().findViewById(R.id.toolbar);

        if(toolbar != null) {
            toolbar.post(() -> {
                Drawable d = getResources().getDrawable(R.drawable.ic_arrow_multicolor);
                toolbar.setNavigationIcon(d);
            });
        }

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public static void startLoading(Integer currentFgId, NavController navControllerParameter){
        navController = navControllerParameter;
        currentFragmentId = currentFgId;
        navController.navigate(R.id.nav_loading);
    }

    public static void downLoading(Integer nextView){
        navController.popBackStack(currentFragmentId, true);
        navController.navigate(nextView);
    }

    public static void downLoading(Integer nextView, Bundle bundle){
        navController.popBackStack(currentFragmentId, true);
        navController.navigate(nextView, bundle);
    }


}
