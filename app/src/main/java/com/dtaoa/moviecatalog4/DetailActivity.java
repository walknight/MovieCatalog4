package com.dtaoa.moviecatalog4;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.dtaoa.moviecatalog4.Db.FavoriteHelper;
import com.dtaoa.moviecatalog4.ViewModel.DataModel;

import static com.dtaoa.moviecatalog4.Db.DatabaseContract.FavoriteColumn.GENRE;
import static com.dtaoa.moviecatalog4.Db.DatabaseContract.FavoriteColumn.POSTER;
import static com.dtaoa.moviecatalog4.Db.DatabaseContract.FavoriteColumn.RATINGS;
import static com.dtaoa.moviecatalog4.Db.DatabaseContract.FavoriteColumn.SINOPSIS;
import static com.dtaoa.moviecatalog4.Db.DatabaseContract.FavoriteColumn.THUMBNAIL;
import static com.dtaoa.moviecatalog4.Db.DatabaseContract.FavoriteColumn.TITLE;
import static com.dtaoa.moviecatalog4.Db.DatabaseContract.FavoriteColumn.TYPE;
import static com.dtaoa.moviecatalog4.Db.DatabaseContract.FavoriteColumn.YEAR;

public class DetailActivity extends AppCompatActivity {

    ImageView imgPoster, imgThumbnail;
    TextView txtTitle, txtSinopsis, txtReleaseYear, txtRatings;
    FavoriteHelper favoriteHelper;
    DataModel selectedData;

    public static final String EXTRA_DATA = "extra_move";
    public static final String EXTRA_TYPE = "type";
    public static final String EXTRA_FAVORITE = "Y";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        String [] releaseDate;
        MenuItem itemBtn;
        Menu menu;

        selectedData = getIntent().getParcelableExtra(EXTRA_DATA);

        favoriteHelper = FavoriteHelper.getInstance(getApplicationContext());
        favoriteHelper.open();

        if(EXTRA_FAVORITE.equals("Y")){
            //setting Delete
            
        } else {
            //setting icon favorite
        }

        if(selectedData != null){

            txtTitle = findViewById(R.id.txt_title_detail);
            txtTitle.setText(selectedData.getTitle());

            txtSinopsis = findViewById(R.id.txt_sinopsis_detail);
            txtSinopsis.setText(selectedData.getSinopsis());

            txtRatings = findViewById(R.id.txt_rating_detail);
            txtRatings.setText(selectedData.getRatings());

            releaseDate = selectedData.getYear().split("-");
            txtReleaseYear = findViewById(R.id.txt_year_detail);
            txtReleaseYear.setText(releaseDate[0]);

            imgThumbnail = findViewById(R.id.img_thumbnail_detail);
            Glide.with(this)
                    .load(selectedData.getImageThumbnail())
                    .apply(new RequestOptions().override(100,140))
                    .into(imgThumbnail);

            imgPoster = findViewById(R.id.img_poster_detail);
            Glide.with(this)
                    .load(selectedData.getImagePoster())
                    .into(imgPoster);
        }

        if(getSupportActionBar() != null)
        {
            getSupportActionBar().setTitle(selectedData.getTitle());
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_favorite, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){

        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            case R.id.bt_ic_fav:
                saveFavorite();
                break;
            case R.id.bt_ic_delete:
                deleteFavorite();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void deleteFavorite(){

    }

    private void saveFavorite(){
        selectedData = getIntent().getParcelableExtra(EXTRA_DATA);
        String type = getIntent().getStringExtra(EXTRA_TYPE);

        ContentValues values = new ContentValues();
        values.put(TITLE, selectedData.getTitle() );
        values.put(SINOPSIS, selectedData.getSinopsis());
        values.put(GENRE, selectedData.getGenre());
        values.put(YEAR, selectedData.getYear());
        values.put(RATINGS, selectedData.getRatings());
        values.put(THUMBNAIL, selectedData.getImageThumbnail());
        values.put(POSTER, selectedData.getImagePoster());
        values.put(TYPE, type);
        long result = FavoriteHelper.insert(values);

        if(result > 0){
            Toast.makeText(this, selectedData.getTitle() + " telah ditambahkan kedalam favorit", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Gagal menambah favorit", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        favoriteHelper.close();
    }
}
