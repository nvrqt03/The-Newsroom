package ajmitchell.android.thenewsroom;

import java.util.List;

import ajmitchell.android.thenewsroom.models.NewsModel;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface NewsApi {

    @GET("top-headlines?country=us")
    Call<NewsModel> getTopHeadlines (
        @Query("apiKey") String apiKey
    );

    @GET("top-headlines?country=us")
    Call<List<NewsModel.Article>> getTopCategory (
            @Query("category") String category,
            @Query("apiKey") String apikey
    );

    @GET("everything?{search_item}&sortby=publishedAt")
    Call<List<NewsModel.Article>> getTopSearch (
            @Path("search_item") String searchItem,
            @Query("apiKey") String apiKey
    );
}
