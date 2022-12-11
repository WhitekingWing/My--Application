package com.example.myapplication.data;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.myapplication.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class HttpDataLoader {
    // 获取网页的html源代码
    @NonNull
    public String getHttpData(String path){
        String error = "1";
        try{
            URL url = new URL(path);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(100000000);
            conn.setReadTimeout(500000000);
            conn.setRequestMethod("GET");
            conn.setUseCaches(false);
            if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                InputStreamReader inputStreamReader = new InputStreamReader(conn.getInputStream());
                BufferedReader reader = new BufferedReader(inputStreamReader);
                String tempLine = null;
                StringBuffer resultBuffer = new StringBuffer();
                while ((tempLine = reader.readLine()) != null) {
                    resultBuffer.append(tempLine);
                    resultBuffer.append("\n");
                }
                return resultBuffer.toString();
            }
        }catch(Exception exception)
        {
            //Log.e("error",exception.getMessage());
            error = "error";
        }
        return error;
    }

    @NonNull
    public BookDetails ParseJsonData(String JsonText)
    {
        BookDetails book = new BookDetails(" ", 1," "," "," ",1,1," "," "," "," "," "," ",0);
        try {
            JSONObject root = new JSONObject(JsonText);
            JSONObject book_json = root.getJSONObject("data");
            String title = book_json.getString("name");
            if(title != null) {
                book.setTitle(title);
            }
            else
            {
                book.setTitle("No title");
            }
            String author = book_json.getString("author");
            if(author != null) {
                book.setAuthor(author);
            }
            else
            {
                book.setAuthor("No author");
            }
            String translator = book_json.getString("translator");
            if(translator != null) {
                book.setTranslator(translator);
            }
            else
            {
                book.setTranslator("No translator");
            }
            String publisher = book_json.getString("publishing");
            if(publisher != null) {
                book.setPublisher(publisher);
            }
            else
            {
                book.setPublisher("No publisher");
            }
            String photoUrl = book_json.getString("photoUrl");
            if(photoUrl != null) {
                book.setHyperlink(photoUrl);
            }
            else
            {
                book.setHyperlink("https://jike.xyz/img/qr/ma_wifi_code_apikey.jpg");
            }
            String notes = book_json.getString("description");
            if(notes != null)
            {
                book.setNotes(notes);
            }
            else
            {
                book.setNotes("No notes");
            }
            String pubDate = book_json.getString("published");
            String  pub[] = pubDate.split("-");
            book.setPubYear(Integer.parseInt(pub[0]));
            book.setPubMonth(Integer.parseInt(pub[1]));
            //Log.e("text",book.getTitle());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return book;
    }
}

