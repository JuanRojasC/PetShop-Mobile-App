package com.petshop.ui.favorites;

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
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.petshop.R;
import com.petshop.adapters.FavoritesAdapter;
import com.petshop.adapters.ProductClientAdapter;
import com.petshop.databinding.FragmentFavoritesBinding;
import com.petshop.persistence.model.Favorite;
import com.petshop.persistence.model.Product;
import com.petshop.persistence.service.FavoriteService;
import com.petshop.ui.util.LoadingFragment;

import java.util.List;

public class FavoritesFragment extends Fragment {

    private FragmentFavoritesBinding binding;

    @RequiresApi(api = Build.VERSION_CODES.O)
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentFavoritesBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        Toolbar toolbar = requireActivity().findViewById(R.id.toolbar);

        toolbar.post(() -> {
            Drawable d = getResources().getDrawable(R.drawable.ic_bar_menu);
            toolbar.setNavigationIcon(d);
        });

        GridView gridView = root.findViewById(R.id.grid_favorites);

        try{
            if(getArguments() != null){
                List<Product> products = (List<Product>) requireArguments().getSerializable("products");
                List<Favorite> favorites = (List<Favorite>) requireArguments().getSerializable("favorites");
                if(products != null)
                    gridView.setAdapter(new FavoritesAdapter(getContext(), products, favorites));
            }else{
                FavoriteService favoriteService = new FavoriteService(getContext(), R.id.nav_favorites);
                favoriteService.showAll();
                NavController navController = NavHostFragment.findNavController(this);
                LoadingFragment.startLoading(R.id.nav_favorites, navController);
            }
        }catch (Exception ex){
            Toast.makeText(getContext(), "error", Toast.LENGTH_SHORT).show();
            Log.e("ERROR", "FavoriteFragment::setAdapter()::" + ex.getMessage());
        }

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}