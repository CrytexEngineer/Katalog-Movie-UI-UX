package com.example.aqil.katalogfilmuiux;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import static android.content.ContentValues.TAG;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder> {

    final static String EXTRA_MOVIE = "movie";
    ArrayList<Movie> listMovie = new ArrayList<>();

    public MovieAdapter(Context context) {
        this.context = context;
    }

    Context context;

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemRow = LayoutInflater.from(parent.getContext()).inflate(R.layout.catalog_layout, parent, false);
        return new ViewHolder(itemRow);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {


        Glide.with(context).load(listMovie.get(position).getThumbnailPath()).override(640, 480).into(holder.poster);
        holder.title.setText(listMovie.get(position).getTitle());
        holder.releaseDate.setText(listMovie.get(position).getRelease_date());
        holder.remarks.setText(listMovie.get(position).getOverview());
        holder.btnMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DetailActivity.class);
                intent.putExtra(EXTRA_MOVIE, listMovie.get(position));
            context.startActivity(intent);

            }
        });

    }

    @Override
    public int getItemCount() {
        Log.d(TAG, "getItemCount: " + listMovie.size());
        return listMovie.size();

    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView poster;
        TextView title, remarks, releaseDate;
        Button btnMore;


        public ViewHolder(View itemView) {
            super(itemView);
            poster = itemView.findViewById(R.id.movie_poster);
            title = itemView.findViewById(R.id.movie_title);
            remarks = itemView.findViewById(R.id.movie_remarks);
            releaseDate = itemView.findViewById(R.id.movie_date_time);
            btnMore=itemView.findViewById(R.id.btn_more);

        }
    }

    public ArrayList<Movie> getListMovie() {
        return listMovie;
    }

    public void setListMovie(ArrayList<Movie> listMovie) {
        this.listMovie = listMovie;
    }
}
