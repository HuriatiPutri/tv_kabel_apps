package com.huriati.project.tvkabel.ui;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import com.dicoding.picodiploma.mynavigationdrawer.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;

import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.huriati.project.tvkabel.ui.ui.main.SectionsPagerAdapter;

public class TestActivity extends AppCompatActivity {
    public static TextView txtId, txtNama, txtHp, txtAlamat, title;
    ImageButton ic_telp;
    public static String idPel;

    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);

        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);
        FloatingActionButton fab = findViewById(R.id.fab);

        txtId = findViewById(R.id.idPel);
        txtNama = findViewById(R.id.namaPel);
        txtHp = findViewById(R.id.noTelp);
        txtAlamat = findViewById(R.id.alamat);
        ic_telp = findViewById(R.id.ic_telp);

        idPel = getIntent().getStringExtra("idPel");
        String namaPel = getIntent().getStringExtra("namaPel");
        String hp = getIntent().getStringExtra("hp");
        String alamat = getIntent().getStringExtra("alamat");

        txtId.setText(idPel);
        txtNama.setText(namaPel);
        txtHp.setText(hp);
        txtAlamat.setText(alamat);

        fab.setVisibility(View.GONE);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    public void telpon(View view) {
        Uri number = Uri.parse("tel:"+txtHp.getText().toString());
        Intent callIntent = new Intent(Intent.ACTION_DIAL, number);
        startActivity(callIntent);
    }

    public void back(View view) {
        finish();
    }
}