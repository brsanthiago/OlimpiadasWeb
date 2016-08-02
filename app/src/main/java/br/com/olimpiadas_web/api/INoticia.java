package br.com.olimpiadas_web.api;

import br.com.olimpiadas_web.parser.NoticiaParser;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.Url;

/**
 * Created by bruno.santiago on 30/07/2016.
 */
public interface INoticia {

    @GET("news/pageable")
    Call<NoticiaParser> getNoticias(@Query("limit") int limit);

    @GET
    Call<NoticiaParser> getNextPrevious(@Url String url);
}
