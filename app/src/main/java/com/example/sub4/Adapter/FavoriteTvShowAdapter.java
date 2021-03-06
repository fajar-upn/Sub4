package com.example.sub4.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.sub4.Entity.Favorite;
import com.example.sub4.Entity.Favorite1;
import com.example.sub4.R;

import java.util.ArrayList;

public class FavoriteTvShowAdapter extends RecyclerView.Adapter<FavoriteTvShowAdapter.FavoriteViewHolder> {

    private ArrayList<Favorite1> listFavorite = new ArrayList<>();
    private OnItemCallback1 onItemClickCallback1;

    public void setOnItemClickCallback1(OnItemCallback1 onItemClickCallback1) {
        this.onItemClickCallback1 = onItemClickCallback1;
    }

    public ArrayList<Favorite1> getListFavorite() {
        return listFavorite;
    }

    public void setListFavorite(ArrayList<Favorite1> listFavorite) {
        this.listFavorite.clear();
        this.listFavorite = listFavorite;
        notifyDataSetChanged();
    }

    public void addItem1(Favorite1 favorite1){
        this.listFavorite.add(favorite1);
        notifyItemInserted(listFavorite.size());
    }

    public void removeItem1(int position){
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
                onItemClickCallback1.onItemClicked(listFavorite.get(holder.getAdapterPosition()));
            }
        });
    }

    @Override
    public int getItemCount() {
        return listFavorite.size();
    }

    public class FavoriteViewHolder extends RecyclerView.ViewHolder {
        ImageView ivPoster;
        TextView tvTitle,tvDescription;
        public FavoriteViewHolder(@NonNull View itemView) {
            super(itemView);
            ivPoster = itemView.findViewById(R.id.iv_poster_favorite);
            tvTitle = itemView.findViewById(R.id.tv_title_favorite);
            tvDescription = itemView.findViewById(R.id.tv_description_favorite);
        }
    }

    public interface OnItemCallback1{
        void onItemClicked(Favorite1 favorite1);
    }
}
