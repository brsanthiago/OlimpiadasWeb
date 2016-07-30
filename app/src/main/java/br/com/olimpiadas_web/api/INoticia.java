package br.com.olimpiadas_web.api;

import java.util.List;

import br.com.olimpiadas_web.model.Noticia;
import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by bruno.santiago on 30/07/2016.
 */
public interface INoticia {

    @GET("news")
    Call<List<Noticia>> getNoticias();
}
