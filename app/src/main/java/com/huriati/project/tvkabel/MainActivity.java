package com.huriati.project.tvkabel;

import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.bumptech.glide.Glide;
import com.dicoding.picodiploma.mynavigationdrawer.R;
import com.google.android.material.navigation.NavigationView;
import com.huriati.project.tvkabel.api.BaseApiService;
import com.huriati.project.tvkabel.api.UtilsApi;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    SharedPrefManager sharedPrefManager;
    CircleImageView profileCircleImageView;
    Context mContext;
    BaseApiService mApiService;

    public static String TOKEN = "";

    TextView name, email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sharedPrefManager = new SharedPrefManager(this);
        TOKEN = sharedPrefManager.getAuth();
        mContext = this;
        mApiService = UtilsApi.getAPIService(sharedPrefManager.getAuth());
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navView = findViewById(R.id.nav_view);

        profileCircleImageView = navView.getHeaderView(0).findViewById(R.id.imageView);
        name = navView.getHeaderView(0).findViewById(R.id.name);
        email = navView.getHeaderView(0).findViewById(R.id.email);

        name.setText(sharedPrefManager.getName());
        email.setText(sharedPrefManager.getSPEmail());

//        getNama();
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_logout)
                .setDrawerLayout(drawerLayout)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);


    }

//    private void getNama() {
//        mApiService.profileRequest().enqueue(new Callback<ProfileModel>() {
//            @Override
//            public void onResponse(Call<ProfileModel> call, Response<ProfileModel> response) {
//                if(response.isSuccessful()){
//                    name.setText(response.body().getName());
//                    if(response.body().getSex().equals("1")) {
//                        Glide.with(MainActivity.this)
//                                .load(R.drawable.ic_lk)
//                                .into(profileCircleImageView);
//                    }else{
//                        Glide.with(MainActivity.this)
//                                .load(R.drawable.ic_pr)
//                                .into(profileCircleImageView);
//                    }
//
//                }
//            }
//
//            @Override
//            public void onFailure(Call<ProfileModel> call, Throwable t) {
//
//            }
//        });
//    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }



}
