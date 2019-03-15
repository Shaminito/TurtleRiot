package com.turtleriot;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.mikhaellopez.circularimageview.CircularImageView;

public class PortadaManuActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private TextView tvBienUser;

    private String nombre;

    //ATRIBUTOS DE NAVIGATIONVIEW
    private CircularImageView civFotoUser;
    private TextView tvNombreUser;

    //TABLAYOUTS
    private ViewPager content;
    private TabLayout tabLayout;
    private SectionsPagerAdapter mSectionsPagerAdapter;
    private RelativeLayout rlFragmentContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_portada_manu);

        nombre = ((UsuarioApplication) getApplicationContext()).getUsuario().getUser();

        tvBienUser = findViewById(R.id.tvBienUser);

        tvBienUser.setText(nombre);

        //fdb = new FireDataBase();

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //TABLAYOUTS
        rlFragmentContent = findViewById(R.id.rlFragmentContent);

        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        content = findViewById(R.id.container);
        content.setAdapter(mSectionsPagerAdapter);

        tabLayout = findViewById(R.id.tab);

        content.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(content) {

            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                super.onTabSelected(tab);
                opcionseleccionado(tab, R.color.color_hint);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                super.onTabUnselected(tab);
                opcionseleccionado(tab, R.color.textColor_Whiite);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                super.onTabReselected(tab);
                opcionseleccionado(tab, R.color.color_hint);
            }
        });

        content.setVisibility(View.GONE);

        //NAVIGATION_VIEW
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        View v = navigationView.getHeaderView(0);

        civFotoUser = v.findViewById(R.id.civFotoUser);
        tvNombreUser = v.findViewById(R.id.tvNombreUser);
        tvNombreUser.setText(nombre);
        navigationView.setNavigationItemSelectedListener(this);

    }

    public void opcionseleccionado(TabLayout.Tab tab, int color){
        int tabIconColor = ContextCompat.getColor(this, color);
        tab.getIcon().setColorFilter(tabIconColor, PorterDuff.Mode.SRC_IN);
        tabLayout.setTabTextColors(getResources().getColor(R.color.textColor_Whiite), getResources().getColor(R.color.color_hint));
        rlFragmentContent.setVisibility(View.GONE);
        content.setVisibility(View.VISIBLE);

    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            Fragment fragment = null;
            if(position == 0){
                fragment = new AccionesFragment();
            }
            else if(position == 1){
                fragment = new AdvertenciaFragment();
            }
            else if(position == 2){
                fragment = new PlayasFragment();
            }
            else if(position == 3){
                fragment = new IrFragment();
            }

            return fragment;
        }

        @Override
        public int getCount() {
            return 4;
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);

        if(item.getItemId() == R.id.nav_fotos){
            Toast.makeText(getApplicationContext(),"Fotos",Toast.LENGTH_LONG).show();
        }
        else if(item.getItemId() == R.id.nav_config){
            Intent i = new Intent(PortadaManuActivity.this,ConfigUserActivity.class);
            startActivity(i);
        }
        else if(item.getItemId() == R.id.nav_compa){
            Toast.makeText(getApplicationContext(),"Compartir",Toast.LENGTH_LONG).show();
        }
        else if(item.getItemId() == R.id.nav_send){
            Toast.makeText(getApplicationContext(),"Enviar",Toast.LENGTH_LONG).show();
        }

        return true;
    }
}