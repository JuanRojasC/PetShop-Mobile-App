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
import com.petshop.databinding.FragmentErrorBinding;
import com.petshop.util.ResultData;


public class ErrorFragment extends Fragment {

    private FragmentErrorBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentErrorBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        Toolbar toolbar = requireActivity().findViewById(R.id.toolbar);
        TextView message = root.findViewById(R.id.actionError);
        message.setText(R.string.error_message);

        toolbar.post(() -> {
            Drawable d = ResourcesCompat.getDrawable(getResources(), R.drawable.ic_arrow_red, null);
            toolbar.setNavigationIcon(d);
        });

        NavController navController = NavHostFragment.findNavController(this);

        if(getArguments() != null){
            String msg = getArguments().getString("message");
            int view = getArguments().getInt("current_view");
            message.setText(msg == null? "Ha habido un error" : msg);
            new android.os.Handler(Looper.getMainLooper()).postDelayed(() -> {
                navController.navigate(view);
                navController.popBackStack(R.id.nav_error, true);
            }, 3000);
        }

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public static void startWarning(ResultData resultData){
        try{
            Bundle bundle = new Bundle();
            bundle.putString("message", resultData.getMessageError());
            bundle.putInt("current_view", resultData.getNextViewError() != null? resultData.getNextViewError() : R.id.nav_home);
            Navigation.findNavController(resultData.getCurrentView()).navigate(R.id.nav_error, bundle);
        }catch (Exception ex){
            Log.e("ERROR", "ErrorFragment::startWarning()>80::" + ex.getMessage());
        }
    }

}
