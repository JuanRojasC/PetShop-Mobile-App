package com.petshop.ui.util;

import android.graphics.drawable.Drawable;
import android.os.Bundle;

import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import com.petshop.R;
import com.petshop.databinding.FragmentSuccessBinding;
import com.petshop.util.ResultData;

public class SuccessFragment extends Fragment {

    private FragmentSuccessBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentSuccessBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        Toolbar toolbar = requireActivity().findViewById(R.id.toolbar);
        TextView message = root.findViewById(R.id.actionSuccess);
        NavController navController = NavHostFragment.findNavController(this);

        if(toolbar != null) {
            toolbar.post(() -> {
                Drawable d = ResourcesCompat.getDrawable(getResources(), R.drawable.ic_arrow_multicolor, null);
                toolbar.setNavigationIcon(d);
            });
        }

        if(getArguments() != null){
            String msg = getArguments().getString("message");
            int view = getArguments().getInt("current_view");
            message.setText(msg == null? "Operación Exitosa" : msg);
            new android.os.Handler(Looper.getMainLooper()).postDelayed(() -> {
                navController.popBackStack(view, true);
                navController.navigate(view);
            }, 3000);
        }

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public static void startFragment(ResultData resultData) {
        try{
            Bundle bundle = new Bundle();
            bundle.putString("message", resultData.getMessageSuccess());
            bundle.putInt("current_view", resultData.getNextViewSuccess() != null? resultData.getNextViewSuccess() : R.id.nav_home);
            Navigation.findNavController(resultData.getCurrentView()).navigate(R.id.nav_succes, bundle);
        }catch (Exception ex){
            Log.e("ERROR", "SuccessFragment::startFragment()>70::" + ex.getMessage());
        }
    }
}
