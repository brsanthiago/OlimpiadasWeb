package br.com.olimpiadas_web.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by bruno.santiago on 30/07/2016.
 */
public class DataUtil {
    static String formatRfc2822 = "EEE, dd MMM yyyy HH:mm:ss Z";
    static String formatLocal = "dd/MM/yyyy HH:mm:ss";
    private static String date;

    public static String getDate(Date pubDate) {
        Date date = new Date();
        date = pubDate;
        return new SimpleDateFormat(formatLocal).format(date);
    }

    private static Date getDateObj(String pubDate) throws ParseException {
        SimpleDateFormat pubDateFormat = new SimpleDateFormat(formatRfc2822, Locale.ENGLISH);
        Date date = null;
        try {
            date = pubDateFormat.parse(pubDate);
        } catch (ParseException e) {
            pubDateFormat = new SimpleDateFormat(formatLocal);
            date = pubDateFormat.parse(pubDate);
        }
        return date;
    }

}
