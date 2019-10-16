package com.dtaoa.moviecatalog4.Fragment;


import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.dtaoa.moviecatalog4.Adapter.FavoriteAdapter;
import com.dtaoa.moviecatalog4.Db.FavoriteHelper;
import com.dtaoa.moviecatalog4.DetailActivity;
import com.dtaoa.moviecatalog4.Helper.MappingHelper;
import com.dtaoa.moviecatalog4.R;
import com.dtaoa.moviecatalog4.ViewModel.DataModel;
import com.google.android.material.snackbar.Snackbar;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class FavTvFragment extends Fragment implements LoadDataTvCallback {


    private RecyclerView rvFvTv;
    private ArrayList<DataModel> dataTv;
    private FavoriteAdapter adapter;
    private ProgressBar favProgressBar;
    private static String FRAGMENT_TAG = null;

    private FavoriteHelper favHelper;

    public FavTvFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_fav_tv, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        favProgressBar = view.findViewById(R.id.progressBarFavTv);
        rvFvTv = view.findViewById(R.id.rv_fragment_fav_tv);
        rvFvTv.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new FavoriteAdapter(getActivity());
        adapter.notifyDataSetChanged();
        rvFvTv.setAdapter(adapter);

        showLoading(true);
        favHelper = FavoriteHelper.getInstance(getActivity().getApplicationContext());
        favHelper.open();

        new LoadDataTvAsync(favHelper, this).execute();

        adapter.setOnItemClickCallback(new FavoriteAdapter.OnItemClickCallback() {
            @Override
            public void onItemClicked(DataModel data) {
                showDetail(data);
            }
        });

    }

    private void showDetail(DataModel data){
        //Toast.makeText(getContext(), "Anda Memilih Judul " + data.getTitle(), Toast.LENGTH_SHORT).show();
        Intent detailIntent = new Intent(getActivity(), DetailActivity.class);
        detailIntent.putExtra(DetailActivity.EXTRA_DATA, data);
        detailIntent.putExtra(DetailActivity.EXTRA_TYPE, "movie");
        detailIntent.putExtra(DetailActivity.EXTRA_FAVORITE, "Y");
        detailIntent.putExtra(DetailActivity.EXTRA_POSITION, FRAGMENT_TAG);
        startActivityForResult(detailIntent, 1);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            if (resultCode == DetailActivity.RESULT_DELETE) {}
            int position = data.getIntExtra(DetailActivity.EXTRA_POSITION, 0);
            adapter.removeItem(position);
        }
    }

    private static class LoadDataTvAsync extends AsyncTask<Void, Void, ArrayList<DataModel>> {

        private final WeakReference<FavoriteHelper> weakFavHelper;
        private final WeakReference<FavTvFragment> weakCallback;

        private LoadDataTvAsync(FavoriteHelper favoriteHelper, FavTvFragment callback) {
            weakFavHelper = new WeakReference<>(favoriteHelper);
            weakCallback = new WeakReference<>(callback);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            weakCallback.get().preExecute();
        }

        @Override
        protected ArrayList<DataModel> doInBackground(Void... voids) {
            Cursor dataCursor = weakFavHelper.get().getFavorite("tv");
            return MappingHelper.mapCursorToArrayList(dataCursor);
        }

        @Override
        protected void onPostExecute(ArrayList<DataModel> itemModels) {
            super.onPostExecute(itemModels);
            weakCallback.get().postExecute(itemModels);
        }

    }

    private void showLoading(Boolean state){
        if(state){
            favProgressBar.setVisibility(View.VISIBLE);
        } else {
            favProgressBar.setVisibility(View.GONE);
        }
    }

    @Override
    public void preExecute() {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                favProgressBar.setVisibility(View.VISIBLE);
            }
        });
    }

    @Override
    public void postExecute(ArrayList<DataModel> favModels) {
        favProgressBar.setVisibility(View.INVISIBLE);
        if (favModels.size() > 0) {
            adapter.setListFavorite(favModels);
        } else {
            adapter.setListFavorite(new ArrayList<DataModel>());
        }
    }

}

interface LoadDataTvCallback {
    void preExecute();
    void postExecute(ArrayList<DataModel> favModels);
}