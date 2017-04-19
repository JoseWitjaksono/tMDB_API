package id.sch.smktelkom_mlg.learn.clientserver1;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.Gson;

import java.util.ArrayList;

import id.sch.smktelkom_mlg.learn.clientserver1.adapter.ArticleAdapter;
import id.sch.smktelkom_mlg.learn.clientserver1.model.Article;
import id.sch.smktelkom_mlg.learn.clientserver1.model.ArticlesResponse;
import id.sch.smktelkom_mlg.learn.clientserver1.service.GsonGetRequest;
import id.sch.smktelkom_mlg.learn.clientserver1.service.VolleySingleton;

public class ArticlesActivity extends AppCompatActivity implements ArticleAdapter.IArticleAdapter
{
    ArrayList<Article> mList = new ArrayList<>();
    ArticleAdapter mAdapter;
    
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        mAdapter = new ArticleAdapter(this, mList);
        recyclerView.setAdapter(mAdapter);
        
        setTitle(getIntent().getStringExtra(MainActivity.SOURCENAME));
        
        downloadDataArticles();
    }
    
    private void downloadDataArticles()
    {
        String id = getIntent().getStringExtra(MainActivity.SOURCEID);
        String sortby = getIntent().getStringExtra(MainActivity.SOURCESORTBY);
        String url = "https://newsapi.org/v1/articles?source=" + id
                + "&sortBy=" + sortby + "&apiKey=d66e77860d7d4fa29be1832f09f8f996";
        
        GsonGetRequest<ArticlesResponse> myRequest = new GsonGetRequest<ArticlesResponse>
                (url, ArticlesResponse.class, null, new Response.Listener<ArticlesResponse>()
                {
                    
                    @Override
                    public void onResponse(ArticlesResponse response)
                    {
                        Log.d("FLOW", "onResponse: " + (new Gson().toJson(response)));
                        if (response.status.equals("ok"))
                        {
                            mList.addAll(response.articles);
                            mAdapter.notifyDataSetChanged();
                        }
                    }
                    
                }, new Response.ErrorListener()
                {
                    
                    @Override
                    public void onErrorResponse(VolleyError error)
                    {
                        Log.e("FLOW", "onErrorResponse: ", error);
                    }
                });
        VolleySingleton.getInstance(this).addToRequestQueue(myRequest);
    }
    
    @Override
    public void showDetail(String url)
    {
        Uri webpage = Uri.parse(url);
        Intent intent = new Intent(Intent.ACTION_VIEW, webpage);
        if (intent.resolveActivity(getPackageManager()) != null)
            startActivity(intent);
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        if (item.getItemId() == android.R.id.home)
        {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
