package com.devnilos.aft.proj1.popularmovies.network;

import android.net.Uri;

/**
 * Created by Nilos on 23-Jan-17.
 */

public class MoviesListRequestBuilder {
    private static final String BASE_URL = "https://api.themoviedb.org/3/";
    private static final String IMAGE_URL = "http://image.tmdb.org/t/p/";

    private static final String PATH_TOP_RATED = "movie/top_rated";
    private static final String PATH_POPULAR = "movie/popular";
    private static final String PATH_IMAGE_WIDTH = "w342/";
    private static final String PATH_IMAGE_SMALL_WIDTH = "w154/";

    private static final String PARAM_API_KEY = "api_key";

    private static String buildBaseUrl(String path, String apikey) {
        Uri uri = Uri.parse(BASE_URL + path).buildUpon()
                .appendQueryParameter(PARAM_API_KEY, apikey)
                .build();

        return uri.toString();
    }

    public static String getTopRatedUrl(String apikey) {
        return buildBaseUrl(PATH_TOP_RATED, apikey);
    }

    public static String getPopularUrl(String apikey) {
        return buildBaseUrl(PATH_POPULAR, apikey);
    }

    public static String getImageUrl(String path, String apikey) {
        Uri uri = Uri.parse(IMAGE_URL + PATH_IMAGE_WIDTH + path).buildUpon()
                .appendQueryParameter(PARAM_API_KEY, apikey)
                .build();

        return uri.toString();
    }

    public static String getSmallImageUrl(String path, String apikey) {
        Uri uri = Uri.parse(IMAGE_URL + PATH_IMAGE_SMALL_WIDTH + path).buildUpon()
                .appendQueryParameter(PARAM_API_KEY, apikey)
                .build();

        return uri.toString();
    }
}
