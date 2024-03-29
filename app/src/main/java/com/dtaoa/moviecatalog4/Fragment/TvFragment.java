package com.dtaoa.moviecatalog4.Fragment;


import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.dtaoa.moviecatalog4.adapter.DataAdapter;
import com.dtaoa.moviecatalog4.DetailActivity;
import com.dtaoa.moviecatalog4.R;
import com.dtaoa.moviecatalog4.ViewModel.DataModel;
import com.dtaoa.moviecatalog4.ViewModel.MainViewModel;

import java.util.ArrayList;
import java.util.Locale;


public class TvFragment extends Fragment {
    private RecyclerView rvTv;
    private ArrayList<DataModel> tvShows = new ArrayList<>();
    private DataAdapter adapter;
    private ProgressBar progressBar;
    private String Lang;

    private MainViewModel mainViewModel;

    public TvFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tv, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        adapter = new DataAdapter();
        adapter.notifyDataSetChanged();
        progressBar = view.findViewById(R.id.progressBar);
        rvTv = view.findViewById(R.id.rv_fragment_tv);
        rvTv.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvTv.setAdapter(adapter);

        showLoading(true);
        Lang = Locale.getDefault().getLanguage().toLowerCase();

        mainViewModel = new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(MainViewModel.class);
        mainViewModel.setListData(getActivity(), Lang, "tv");
        mainViewModel.getMovies().observe(this, new Observer<ArrayList<DataModel>>() {
            @Override
            public void onChanged(@Nullable ArrayList<DataModel> tvShows) {
                if(tvShows != null){
                    adapter.setData(tvShows);
                    showLoading(false);
                }
            }
        });

        adapter.setOnItemClickCallback(new DataAdapter.OnItemClickCallback() {
            @Override
            public void onItemClicked(DataModel data) {
                showDetail(data);
            }
        });
    }

    private void showLoading(Boolean state){
        if(state){
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.GONE);
        }
    }

    private void showDetail(DataModel data){
        //Toast.makeText(getContext(), "Anda Memilih Judul " + data.getTitle(), Toast.LENGTH_SHORT).show();
        Intent detailIntent = new Intent(getActivity(), DetailActivity.class);
        detailIntent.putExtra(DetailActivity.EXTRA_DATA, data);
        detailIntent.putExtra(DetailActivity.EXTRA_TYPE, "tv");
        detailIntent.putExtra(DetailActivity.EXTRA_FAVORITE, "N");
        startActivity(detailIntent);
    }
}
