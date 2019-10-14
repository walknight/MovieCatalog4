package com.dtaoa.moviecatalog4.Adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.dtaoa.moviecatalog4.R;
import com.dtaoa.moviecatalog4.entity.Favorite;

import java.util.ArrayList;

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.FavoriteViewHolder> {

    private ArrayList<Favorite> listFavorite = new ArrayList<>();
    private Activity activity;

    public FavoriteAdapter(Activity activity){
        this.activity = activity;
    }

    public ArrayList<Favorite> getListFavorite(){ return listFavorite; }

    public void setListFavorite(ArrayList<Favorite> listFav){
        if(listFav.size() > 0){
            this.listFavorite.clear();
        }
        this.listFavorite.addAll(listFav);
        notifyDataSetChanged();
    }

    public void addItem(Favorite favorite){
        this.listFavorite.add(favorite);
        notifyItemInserted(listFavorite.size() - 1);
    }

    public void removeItem(int position){
        this.listFavorite.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, listFavorite.size());
    }

    @NonNull
    @Override
    public FavoriteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_items, parent, false);
        return new FavoriteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FavoriteViewHolder holder, final int position) {
        String year = listFavorite.get(position).getYear().split("-")[0];

        holder.textTitle.setText(listFavorite.get(position).getTitle());
        holder.textYear.setText(year);
        holder.textRating.setText(listFavorite.get(position).getRatings());
        holder.textSinopsis.setText(listFavorite.get(position).getSinopsis());
        Glide.with(holder.itemView.getContext())
                .load(listFavorite.get(position).getImageThumbnail())
                .apply(new RequestOptions().override(100, 140))
                .into(holder.imgThumbnail);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(activity, listFavorite.get(position).getTitle(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return listFavorite.size();
    }

    public class FavoriteViewHolder extends RecyclerView.ViewHolder {

        TextView textTitle, textYear, textRating, textSinopsis;
        ImageView imgThumbnail;

        public FavoriteViewHolder(@NonNull View itemView) {
            super(itemView);

            textTitle = itemView.findViewById(R.id.txt_title);
            textYear = itemView.findViewById(R.id.txt_year);
            textRating = itemView.findViewById(R.id.txt_rating);
            imgThumbnail = itemView.findViewById(R.id.img_thumbnail);
            textSinopsis = itemView.findViewById(R.id.txt_sinopsis);

        }
    }
}
