package com.example.sub4.Adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.sub4.DetailFavorite;
import com.example.sub4.DetailMovies;
import com.example.sub4.Entity.Favorite;
import com.example.sub4.Favorite.FavoriteMoviesFragment;
import com.example.sub4.Favorite.FavoriteTvShowFragment;
import com.example.sub4.R;

import java.util.ArrayList;

public class FavoriteAdapater extends RecyclerView.Adapter<FavoriteAdapater.FavoriteViewHolder> {

    private ArrayList<Favorite> listFavorite = new ArrayList<>();
    private OnItemClickcallback onItemClickcallback;
    private FavoriteMoviesFragment mActivity;
    private FavoriteTvShowFragment aActivity;
    private DetailMovies nActivity;
    private DetailFavorite bActivity;

    public void setOnItemClickcallback(OnItemClickcallback onItemClickcallback) {
        this.onItemClickcallback = onItemClickcallback;
    }

    public FavoriteAdapater(DetailFavorite activity) {
        this.bActivity = activity;
    }

    public FavoriteAdapater(FavoriteMoviesFragment activity) {
        this.mActivity = activity;
    }

    public FavoriteAdapater(DetailMovies activity){
        this.nActivity = activity;
    }

    public ArrayList<Favorite> getListFavorite() {
        return listFavorite;
    }

    public void setListFavorite(ArrayList<Favorite> listFavorite) {
//        if (listFavorite.size()>=0){
//
//        }
        this.listFavorite.clear();

        this.listFavorite.addAll(listFavorite);
        notifyDataSetChanged();
    }

    public void addItem(Favorite favorite){
        this.listFavorite.add(favorite);
        notifyItemInserted(listFavorite.size());
    }

    public void updateItem(int position, Favorite favorite){
        this.listFavorite.set(position, favorite);
        notifyItemChanged(position, favorite);

    }

    public void removeItem(int position){
        this.listFavorite.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position,listFavorite.size());
    }

    @NonNull
    @Override
    public FavoriteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.items_row_favorite,parent,false);
        return new FavoriteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final FavoriteViewHolder holder, int position) {
        Glide.with(holder.itemView.getContext())
                .load(listFavorite.get(position).getPoster())
                .apply(new RequestOptions().override(300,300))
                .into(holder.ivPoster);
        holder.tvTitle.setText(listFavorite.get(position).getTitle());
        holder.tvDescription.setText(listFavorite.get(position).getDescription());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickcallback.onItemClicked(listFavorite.get(holder.getAdapterPosition()));
            }
        });
    }

    @Override
    public int getItemCount() {
        return listFavorite.size();
    }

    public class FavoriteViewHolder extends RecyclerView.ViewHolder {
        final ImageView ivPoster;
        final TextView tvTitle, tvDescription;
        public FavoriteViewHolder(@NonNull View itemView) {
            super(itemView);
            ivPoster = itemView.findViewById(R.id.iv_poster_favorite);
            tvTitle = itemView.findViewById(R.id.tv_title_favorite);
            tvDescription = itemView.findViewById(R.id.tv_description_favorite);
        }
    }

    public interface OnItemClickcallback{
        void onItemClicked(Favorite favorite);
    }
}
