package ajmitchell.android.thenewsroom;

import java.util.List;

import ajmitchell.android.thenewsroom.utils.Constants;
import retrofit2.Retrofit;
import ajmitchell.android.thenewsroom.models.NewsModel;
import retrofit2.converter.gson.GsonConverterFactory;

public class NewsRetrofit {

    List<NewsModel.Article> articleList;
    private static NewsApi newsApi;

    public static NewsApi getNewsApi() {
        if (newsApi == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(Constants.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            newsApi = retrofit.create(NewsApi.class);
        }
        return newsApi;
    }
}
