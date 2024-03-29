package com.dtaoa.moviecatalog4.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.dtaoa.moviecatalog4.R;
import com.dtaoa.moviecatalog4.ViewModel.DataModel;

import java.util.ArrayList;

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.FavoriteViewHolder> {

    private ArrayList<DataModel> listFavorite = new ArrayList<>();
    private Activity activity;
    private OnItemClickCallback onItemClickCallback;

    public FavoriteAdapter(Activity activity){
        this.activity = activity;
    }

    public ArrayList<DataModel> getListFavorite(){ return listFavorite; }

    public void setListFavorite(ArrayList<DataModel> listFav){
        if(listFav.size() > 0){
            this.listFavorite.clear();
        }
        this.listFavorite.addAll(listFav);
        notifyDataSetChanged();
    }

    public void addItem(DataModel favorite){
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
    public void onBindViewHolder(@NonNull final FavoriteViewHolder holder, final int position) {
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
                onItemClickCallback.onItemClicked(listFavorite.get(holder.getAdapterPosition()));
            }
        });

    }

    public void setOnItemClickCallback(OnItemClickCallback onItemClickCallback){
        this.onItemClickCallback = onItemClickCallback;
    }

    public interface OnItemClickCallback{
        void onItemClicked(DataModel data);
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
