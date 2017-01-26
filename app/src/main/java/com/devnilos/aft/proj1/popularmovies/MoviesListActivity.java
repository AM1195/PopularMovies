package com.devnilos.aft.proj1.popularmovies;

import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.devnilos.aft.proj1.popularmovies.network.AsyncHttpCallJSONTask;
import com.devnilos.aft.proj1.popularmovies.network.AsyncHttpCallJSONTaskParams;
import com.devnilos.aft.proj1.popularmovies.network.MoviesListRequestBuilder;
import com.devnilos.aft.proj1.popularmovies.network.NetworkResponseWrapper;
import com.devnilos.aft.proj1.popularmovies.pojos.GetMoviesResponse;

import java.io.InputStream;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MoviesListActivity extends AppCompatActivity {
    public final static String MOVIE_INFO = "com.devnilos.aft.proj1.popularmovies.MOVIE_INFO";
    public final static String API_KEY = "com.devnilos.aft.proj1.popularmovies.API_KEY";

    @BindView(R.id.recyclerview_movies) RecyclerView mRecyclerView;
    @BindView(R.id.tv_error_message_display) TextView mErrorMessageDisplay;
    @BindView(R.id.pb_loading_indicator) ProgressBar mLoadingIndicator;

    private MoviesListAdapter mMoviesListAdapter;
    private String mMoviesApiKey;

    private boolean mPopularMovies;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movies_list);
        ButterKnife.bind(this);

        GridLayoutManager layoutManager = new GridLayoutManager(this, 3);
        mMoviesListAdapter = new MoviesListAdapter();

        mRecyclerView.setAdapter(mMoviesListAdapter);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);
    }

    @Override
    protected void onStart() {
        super.onStart();

        if (mMoviesApiKey == null
        || (mMoviesApiKey != null && mMoviesApiKey.isEmpty())) {
            try {
                Resources res = getResources();
                InputStream in_s = res.openRawResource(R.raw.themoviedb);

                byte[] b = new byte[in_s.available()];
                in_s.read(b);
                mMoviesApiKey = new String(b);
            } catch (Exception e) {
                e.printStackTrace();

                mErrorMessageDisplay.setText(e.getMessage());

                mLoadingIndicator.setVisibility(View.INVISIBLE);
                mRecyclerView.setVisibility(View.INVISIBLE);
                mErrorMessageDisplay.setVisibility(View.VISIBLE);
            }
        }
        mMoviesListAdapter.setmMoviesApiKey(mMoviesApiKey);
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadMoveis();
    }

    void loadMoveis() {
        if (mMoviesApiKey != null && !mMoviesApiKey.isEmpty()) {
            Log.d("activity", "loadMoveis");

            mLoadingIndicator.setVisibility(View.VISIBLE);
            mRecyclerView.setVisibility(View.INVISIBLE);
            mErrorMessageDisplay.setVisibility(View.INVISIBLE);

            String url = MoviesListRequestBuilder.getTopRatedUrl(mMoviesApiKey);
            if (mPopularMovies) {
                url = MoviesListRequestBuilder.getPopularUrl(mMoviesApiKey);
            }

            new ListedMoviesFetchTask().execute(
                    new AsyncHttpCallJSONTaskParams(
                            url,
                            GetMoviesResponse.class
                    )
            );
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.movies_list, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.action_sort_by:
                mPopularMovies = !mPopularMovies;
                if (mPopularMovies) {
                    item.setTitle(R.string.sort_by_rate);
                }
                else {
                    item.setTitle(R.string.sort_by_popularity);
                }

                break;
        }

        mMoviesListAdapter.setMoviesResponse(null);
        loadMoveis();

        return true;
    }

    class ListedMoviesFetchTask extends AsyncHttpCallJSONTask {
        @Override
        protected void onPostExecute(NetworkResponseWrapper networkResponseWrapper) {
            mLoadingIndicator.setVisibility(View.INVISIBLE);
            if (networkResponseWrapper.hasException()) {
                mErrorMessageDisplay.setVisibility(View.VISIBLE);
                mErrorMessageDisplay.setText(networkResponseWrapper.getException().getMessage());
                networkResponseWrapper.getException().printStackTrace();
            }
            else  {
                GetMoviesResponse getMoviesResponse = (GetMoviesResponse) networkResponseWrapper.getResponse();
                Log.d("activity", "no errors " + getMoviesResponse.getResults().size());

                mMoviesListAdapter.setMoviesResponse(getMoviesResponse);
                mRecyclerView.setVisibility(View.VISIBLE);
            }
        }
    }
}
