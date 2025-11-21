package com.example.a4443finalproject_group10;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.google.android.material.appbar.MaterialToolbar;

public class MainActivity extends AppCompatActivity {

    private boolean showLogout = false;
    private MaterialToolbar toolbar;
    private NavController navController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.topAppBar);
        setSupportActionBar(toolbar);

        NavHostFragment navHostFragment =
                (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);

        if (navHostFragment != null) {
            navController = navHostFragment.getNavController();
        }

        if (navController != null) {
            navController.addOnDestinationChangedListener((controller, destination, arguments) -> {
                boolean isAuth = destination.getId() == R.id.loginFragment
                        || destination.getId() == R.id.signUpFragment;

                // Toolbar always visible; only toggle logout
                showLogout = !isAuth;
                invalidateOptionsMenu();
            });
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.top_app_bar_menu, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem logout = menu.findItem(R.id.action_logout);
        if (logout != null) logout.setVisible(showLogout);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_logout && navController != null) {
            SessionManager.logout();
            navController.navigate(R.id.loginFragment);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
