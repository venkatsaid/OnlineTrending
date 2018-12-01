package com.example.lenovo.onlinetrending;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by Lenovo on 24-06-2018.
 */

public class Myasynctask extends AsyncTask<Void,Void,Void> {
    Context context;
    String urls;
    URL url;
    ArrayList<Article> arrayList=new ArrayList<Article>();
    public Myasynctask(Context context,String url) {
        this.context=context;
        this.urls=url;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected Void doInBackground(Void... voids) {
        url=urlformation(urls);
        try {
            String res=httpconnection(url);
            arrayList = jsonresponse(res);

        } catch (JSONException e1) {
            e1.printStackTrace();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        LinearLayoutManager lm=new LinearLayoutManager(context);

        onlinetrending.rv.setLayoutManager(lm);
        onlinetrending.rv.setAdapter(new NewsAdapter( arrayList,context));

    }
    public static URL urlformation(String s) {
        URL url = null;
        String BASE =s;
        Uri builtUri = Uri.parse(BASE);
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return url;
    }

   public static String httpconnection(URL url) throws IOException {
        String res="";
        HttpURLConnection urlConn = (HttpURLConnection) url.openConnection();
        InputStream in = urlConn.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        while(true) {
            String line = reader.readLine();
            if(line == null) {
                break;
            }
            res=res+line;;
        }
        Log.i("trailerarraylist",res);
        return res;
    }
    public static ArrayList<Article> jsonresponse(String res) throws JSONException {
        ArrayList<Article> arrayList1 = new ArrayList<>();
        Newspojo news=new Newspojo();
        JSONObject jsonObject;
        jsonObject = new JSONObject(res);
        news.setStatus(jsonObject.getString("status"));
        Log.i("data",jsonObject.getString("status"));
        news.setTotalResults(jsonObject.getInt("totalResults"));
        JSONArray articles = jsonObject.getJSONArray("articles");
        Log.i("data",Integer.toString(articles.length()));
        for (int i = 0; i < articles.length(); i++) {
            JSONObject jsonObject1 = articles.getJSONObject(i);
           // Log.i("data",jsonObject.toString());
            JSONObject sourceobj=jsonObject1.getJSONObject("source");
            Source source=new Source(sourceobj.getString("id"),sourceobj.getString("name"));
            String author=jsonObject1.getString("author");
            Log.i("data",sourceobj.getString("id"));
            Log.i("data",author);
            String title=jsonObject1.getString("title");
            Log.i("data",title);
            String description=jsonObject1.getString("description");
            Log.i("data",description);
            String  url=jsonObject1.getString("url");
            Log.i("data",url);
            String urlToImage=jsonObject1.getString("urlToImage");
            String publishedAt=jsonObject1.getString("publishedAt");
            Article article = new Article(source,author,title,description,url,urlToImage,publishedAt);
            arrayList1.add(article);
        }
        news.setArticles(arrayList1);
        return arrayList1;
    }
}
