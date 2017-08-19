package com.ruslanchepizhko.tastyomelet;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity {
    static final String TAG = "myLogs";
    public static final String defoultUrl = "http://recipepuppy.com/api/?i=onions,garlic&q=omelet&p=3";
    public static final String searchUrl = "http://www.recipepuppy.com/api/?q=БЛЮДО";
    public Elements elementsTitle;
    public Elements elementsImg;
    public Elements elementsHref;
    public Elements elementsIngredients;
    public ArrayList<String> titleList = new ArrayList<String>();
    public ArrayList<String> ingredientsList = new ArrayList<String>();
    public ArrayList<String> imgList = new ArrayList<String>();
    public ArrayList<String> hrefList = new ArrayList<String>();
    public ArrayAdapter<String> adapter;
    private ListView listView;
    String url = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        listView = (ListView)findViewById(R.id.listView);
        url = defoultUrl;
        new NewAsyncTask().execute();
    }
    private class NewAsyncTask extends AsyncTask<String,String,String>{

        @Override
        protected String doInBackground(String... arg0) {
            Document doc;
            try {
                doc = Jsoup.connect(url).get();
                String title = doc.title();
                elementsTitle = doc.select("title");
                for(Element link : elementsTitle){
                    titleList.add(doc.title());
                    Log.v(TAG, "------------------------------------------------------------"+title);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            adapter = new ArrayAdapter<String>(SearchActivity.this, android.R.layout.simple_list_item_1, titleList);
            listView.setAdapter(adapter);
        }
    }
}
