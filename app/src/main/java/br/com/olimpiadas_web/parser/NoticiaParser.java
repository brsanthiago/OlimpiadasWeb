package br.com.olimpiadas_web.parser;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

import br.com.olimpiadas_web.model.Noticia;

/**
 * Created by bruno.santiago on 01/08/2016.
 */
public class NoticiaParser {
    private String previous;
    private String next;
    @SerializedName("data")
    private ArrayList<Noticia> noticias;

    public String getPrevious() {
        return previous;
    }

    public void setPrevious(String previous) {
        this.previous = previous;
    }

    public String getNext() {
        return next;
    }

    public void setNext(String next) {
        this.next = next;
    }

    public ArrayList<Noticia> getNoticias() {
        return noticias;
    }

    public void setNoticias(ArrayList<Noticia> noticias) {
        this.noticias = noticias;
    }
}
