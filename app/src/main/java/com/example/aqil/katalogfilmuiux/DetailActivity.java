package com.example.aqil.katalogfilmuiux;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;

public class DetailActivity extends AppCompatActivity {
    ImageView img_detail;
    TextView detaiLDescription,releaseDateDetail,titleDetail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        img_detail = findViewById(R.id.img_detail);
        detaiLDescription = findViewById(R.id.description);
        releaseDateDetail =findViewById(R.id.movie_date_time_detail);
        titleDetail=findViewById(R.id.titleDetail);
        Movie currentMovie = getIntent().getParcelableExtra(MovieAdapter.EXTRA_MOVIE);
        Picasso.get().load(currentMovie.getPosterPath()).resize(480, 680).into(img_detail);
        releaseDateDetail.setText(currentMovie.getRelease_date());
        titleDetail.setText(currentMovie.getTitle());
        detaiLDescription.setText(currentMovie.getDescription());

    }
}
