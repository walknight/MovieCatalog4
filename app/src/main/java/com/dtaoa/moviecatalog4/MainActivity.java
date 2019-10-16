package com.dtaoa.moviecatalog4;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.dtaoa.moviecatalog4.Fragment.FavoriteFragment;
import com.dtaoa.moviecatalog4.Fragment.MovieFragment;
import com.dtaoa.moviecatalog4.Fragment.TvFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.Locale;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    final Fragment fragmentMovie = new MovieFragment();
    final Fragment fragmentTv = new TvFragment();
    final Fragment fragmentFavorite = new FavoriteFragment();
    final FragmentManager fm = getSupportFragmentManager();
    Fragment active = fragmentMovie;

    AlertDialog dialogLang;
    int checkedItem = 0;
    CharSequence[] options = {"Bahasa", "English"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String Lang = Locale.getDefault().getLanguage();
        loadLanguage(Lang);
        setContentView(R.layout.activity_main);

        BottomNavigationView navigationBottom = findViewById(R.id.btm_navigation);
        navigationBottom.setOnNavigationItemSelectedListener(this);

        fm.beginTransaction().add(R.id.container_layout, fragmentFavorite, "3").hide(fragmentFavorite).commit();
        fm.beginTransaction().add(R.id.container_layout, fragmentTv, "2").hide(fragmentTv).commit();
        fm.beginTransaction().add(R.id.container_layout, fragmentMovie, "1").commit();

        if(savedInstanceState == null){
            navigationBottom.setSelectedItemId(R.id.navigation_movie);
        }

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        switch (menuItem.getItemId()){
            case R.id.navigation_movie:
                //loadFragment(new MovieFragment());
                fm.beginTransaction().hide(active).show(fragmentMovie).commit();
                active = fragmentMovie;
                setTitle(R.string.app_name);
                return true;

            case R.id.navigation_tv:
                //loadFragment(new TvFragment());
                fm.beginTransaction().hide(active).show(fragmentTv).commit();
                active = fragmentTv;
                setTitle(R.string.app_name);
                return true;

            case R.id.navigation_favorite:
                //loadFragment(new FavoriteFragment());
                fm.beginTransaction().hide(active).show(fragmentFavorite).commit();
                active = fragmentFavorite;
                setTitle(R.string.title_favorite);
                return true;
        }
        return false;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_change_lang, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        if (item.getItemId() == R.id.bt_ic_lang){
            CreateDialogLang();
        }

        return super.onOptionsItemSelected(item);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //Log.d("RESULT CODE", String.valueOf(requestCode));
        if (resultCode == RESULT_OK) {
            //String stredittext = data.getStringExtra("id");
            //Log.d("RESULT MAIN", stredittext);
            loadFragment(new FavoriteFragment());
        }
    }

        //load fragment
    public void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container_layout, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    //create dialog
    public void CreateDialogLang(){
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

        builder.setTitle(R.string.dialog_title);

        builder.setSingleChoiceItems(options, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {

                checkedItem = item;
            }
        });
        builder.setPositiveButton("Done", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //Toast.makeText(MainActivity.this, options[checkedItem], Toast.LENGTH_SHORT).show();
                switch (checkedItem){
                    case 0:
                        //change to bahasa indonesia
                        changeLang("id");
                        break;
                    case 1:
                        //change to bahasa inggris
                        changeLang("en");
                        break;
                }
                Toast.makeText(MainActivity.this, options[checkedItem], Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });
        dialogLang = builder.create();
        dialogLang.show();

    }

    public void changeLang(String lang){
        Resources resources = getResources();
        DisplayMetrics dm = resources.getDisplayMetrics();
        Configuration config = resources.getConfiguration();
        Locale mLang = new Locale(lang);
        Locale.setDefault(mLang);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1){
            config.setLocale(mLang);
        } else {
            config.locale = mLang;
        }
        resources.updateConfiguration(config, dm);

        recreate();
    }

    public void loadLanguage(String language){
        Locale locale = new Locale(language);
        locale.setDefault(locale);
        Configuration config = new Configuration();
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1){
            config.setLocale(locale);
        } else {
            config.locale = locale;
        }
        getResources().updateConfiguration(config, getResources().getDisplayMetrics());
    }

}
