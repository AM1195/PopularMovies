package com.devnilos.aft.proj1.popularmovies;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.devnilos.aft.proj1.popularmovies.network.MoviesListRequestBuilder;
import com.devnilos.aft.proj1.popularmovies.pojos.Result;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.devnilos.aft.proj1.popularmovies.MoviesListActivity.API_KEY;
import static com.devnilos.aft.proj1.popularmovies.MoviesListActivity.MOVIE_INFO;

public class MovieDetailedActivity extends AppCompatActivity {
    @BindView(R.id.toolbar) Toolbar mToolbar;
    @BindView(R.id.iv_detailed_movie_poster) ImageView mPoster;
    @BindView(R.id.tv_detailed_movie_year) TextView mMovieYear;
    @BindView(R.id.tv_detailed_movie_title) TextView mMovieTitle;
    @BindView(R.id.tv_detailed_movie_rating) TextView mMovieRating;
    @BindView(R.id.tv_detailed_movie_overview) TextView mMovieOverview;

    private Result mMovieResult;
    private String mMoviesApiKey;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detailed);
        ButterKnife.bind(this);

        setSupportActionBar(mToolbar);

        Intent intent = getIntent();

        String json = intent.getStringExtra(MOVIE_INFO);
        mMovieResult = new Gson().fromJson(json, Result.class);

        mMoviesApiKey = intent.getStringExtra(API_KEY);

        setTitle(mMovieResult.getOriginalTitle());

        mMovieYear.setText(mMovieResult.getReleaseDate());
        mMovieRating.setText(mMovieResult.getVoteAverage() + "");
        mMovieTitle.setText(mMovieResult.getTitle());
        mMovieOverview.setText(mMovieResult.getOverview());

        Picasso.with(this)
                .load(
                    MoviesListRequestBuilder.getSmallImageUrl(
                            mMovieResult.getPosterPath(), mMoviesApiKey))
                .into(mPoster);
    }
}
