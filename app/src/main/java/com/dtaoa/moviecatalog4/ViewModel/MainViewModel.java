package com.dtaoa.moviecatalog4.ViewModel;

import android.content.Context;
import android.provider.ContactsContract;
import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.dtaoa.moviecatalog4.BuildConfig;
import com.dtaoa.moviecatalog4.R;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class MainViewModel extends ViewModel {
    private MutableLiveData<ArrayList<DataModel>> listData = new MutableLiveData<>();

    public void setListData(final Context context, String language, final String type){
        AsyncHttpClient client = new AsyncHttpClient();
        final ArrayList<DataModel> listItems = new ArrayList<>();
        final String API_KEY = BuildConfig.API_KEY;
        String URL;
        if(language.equals("in") || language.equals("in_ID")){
            language = "id";
        }
        if(type.equals("movie")){
            URL = context.getResources().getString(R.string.URL_MOVIE) + API_KEY + "&language=" + language;
        } else {
            URL = context.getResources().getString(R.string.URL_TV) + API_KEY + "&language=" + language;
        }

        client.get(URL, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    String result = new String(responseBody);
                    String title;
                    String release;
                    JSONObject responseObject = new JSONObject(result);
                    JSONArray list = responseObject.getJSONArray("results");

                    for(int i = 0; i < list.length(); i++){
                        JSONObject items = list.getJSONObject(i);
                        DataModel itemData = new DataModel();
                        int id = items.getInt("id");
                        if(type.equals("movie")){
                            title = items.getString("title");
                            release = items.getString("release_date");
                        } else {
                            release = items.getString("first_air_date");
                            title = items.getString("name");
                        }
                        itemData.setId(id);
                        itemData.setTitle(title);
                        itemData.setGenre(items.getString("genre_ids"));
                        itemData.setYear(release);
                        itemData.setRatings(items.getString("vote_average"));
                        itemData.setSinopsis(items.getString("overview"));
                        itemData.setImageThumbnail(context.getResources().getString(R.string.path_thumbnail) + items.getString("poster_path"));
                        itemData.setImagePoster(context.getResources().getString(R.string.path_poster) + items.getString("backdrop_path"));
                        listItems.add(itemData);
                    }
                    listData.postValue(listItems);

                } catch (Exception e){
                    Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public LiveData<ArrayList<DataModel>> getMovies(){
        return listData;
    }
}
