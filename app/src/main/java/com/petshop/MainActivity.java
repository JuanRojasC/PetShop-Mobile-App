package com.petshop;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import com.petshop.databinding.ActivityMainBinding;
import com.petshop.persistence.db.DBHelper;
import com.petshop.persistence.db.DBQueries;
import com.petshop.ui.util.SuccessFragment;

public class MainActivity extends AppCompatActivity {

    public static final Integer REQUEST_CODE_GALLERY = 5632156;
    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;
    private Toolbar toolbar;
    private DBHelper db;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(binding.appBarMain.toolbar);

        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(R.id.nav_home, R.id.nav_products, R.id.nav_services, R.id.nav_stores, R.id.nav_favorites, R.id.nav_managment_products, R.id.nav_managment_services ,R.id.nav_managment_stores).setOpenableLayout(drawer).build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        toolbar.post(() -> {
            Drawable d = getResources().getDrawable(R.drawable.ic_bar_menu);
            toolbar.setNavigationIcon(d);
        });

        DBQueries dbQueries = DBQueries.getInstance(getApplicationContext());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        Drawable drawable = ContextCompat.getDrawable(getApplicationContext(), R.drawable.icon_toolbar_menu);
        toolbar.setOverflowIcon(drawable);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);

        switch (item.getItemId()){
            case R.id.action_favorites:
                navController.navigate(R.id.nav_favorites);
                break;
            case R.id.action_add_product:
                navController.navigate(R.id.nav_form_product);
                break;
            case R.id.action_add_service:
                navController.navigate(R.id.nav_form_service);
                break;
            case R.id.action_add_store:
                navController.navigate(R.id.nav_form_store);
                break;
            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration) || super.onSupportNavigateUp();
    }

    @Override
    public void onBackPressed() {
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment_content_main);
        if(!(fragment instanceof SuccessFragment))
            super.onBackPressed();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.clear();
    }
}