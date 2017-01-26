package com.devnilos.aft.proj1.popularmovies;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.devnilos.aft.proj1.popularmovies.network.MoviesListRequestBuilder;
import com.devnilos.aft.proj1.popularmovies.pojos.GetMoviesResponse;
import com.devnilos.aft.proj1.popularmovies.pojos.Result;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.devnilos.aft.proj1.popularmovies.MoviesListActivity.API_KEY;
import static com.devnilos.aft.proj1.popularmovies.MoviesListActivity.MOVIE_INFO;

/**
 * Created by Nilos on 23-Jan-17.
 */

public class MoviesListAdapter extends RecyclerView.Adapter<MoviesListAdapter.MoviesListViewHolder> {
    private GetMoviesResponse moviesResponse;
    private String mMoviesApiKey;

    @Override
    public MoviesListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutIdForListItem = R.layout.movies_list_item;

        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, parent, shouldAttachToParentImmediately);

        return new MoviesListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MoviesListViewHolder holder, int position) {
        Result result = moviesResponse.getResults().get(position);
        String url = MoviesListRequestBuilder.getImageUrl(result.getPosterPath(), mMoviesApiKey);
        Log.d("adapter", position + ". url: " + url);

        holder.imageView.setTag(position);
        holder.imageView.setOnClickListener(oclImageView);

        Picasso.with(holder.imageView.getContext())
                .load(url)
                .into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        int n = 0;

        if (moviesResponse != null) {
            n = moviesResponse.getResults().size();
        }

        return n;
    }

    public void setMoviesResponse(GetMoviesResponse moviesResponse) {
        this.moviesResponse = moviesResponse;
        notifyDataSetChanged();
    }

    public void setmMoviesApiKey(String mMoviesApiKey) {
        this.mMoviesApiKey = mMoviesApiKey;
    }

    private final View.OnClickListener oclImageView = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int pos = (int) v.getTag();
            Result result = moviesResponse.getResults().get(pos);
            Log.d("adapter", pos + ". clicked " + result.getOriginalTitle());

            Intent intent = new Intent(v.getContext(), MovieDetailedActivity.class);
            intent.putExtra(MOVIE_INFO, new Gson().toJson(result));
            intent.putExtra(API_KEY, mMoviesApiKey);
            v.getContext().startActivity(intent);
        }
    };

    public class MoviesListViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_movies_list_title) public ImageView imageView;

        public MoviesListViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
