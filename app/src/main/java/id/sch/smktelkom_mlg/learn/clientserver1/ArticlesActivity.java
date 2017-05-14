package id.sch.smktelkom_mlg.learn.clientserver1;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;

import java.util.ArrayList;

import id.sch.smktelkom_mlg.learn.clientserver1.model.ArticlesResponse;
import id.sch.smktelkom_mlg.learn.clientserver1.service.GsonGetRequest;
import id.sch.smktelkom_mlg.learn.clientserver1.service.VolleySingleton;

public class ArticlesActivity extends AppCompatActivity
{
    Context context;
    ImageView ivArticle;
    TextView tvTitle, tvDate, tvDesc;
    String iurl;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.article_list);

        ivArticle = (ImageView) findViewById(R.id.imageViewArticle);
        tvTitle = (TextView) findViewById(R.id.textViewTitle);
        tvDate = (TextView) findViewById(R.id.textViewDate);
        tvDesc = (TextView) findViewById(R.id.textViewDesc);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        context = getApplicationContext();

        setTitle(getIntent().getStringExtra(MainActivity.SOURCENAME));
        
        downloadDataArticles();
    }
    
    private void downloadDataArticles()
    {
        String id = getIntent().getStringExtra(MainActivity.SOURCEID);
        String url = "https://api.themoviedb.org/3/movie/"+id+"?api_key=ba8049249923de75707c1086afd6172d&language=en-US";
        
        GsonGetRequest<ArticlesResponse> myRequest = new GsonGetRequest<ArticlesResponse>
                (url, ArticlesResponse.class, null, new Response.Listener<ArticlesResponse>()
                {
                    
                    @Override
                    public void onResponse(ArticlesResponse response)
                    {
                        Log.d("FLOW", "onResponse: " + (new Gson().toJson(response)));
                        if (response.status.equals("Released"))
                        {
                            iurl = "http://image.tmdb.org/t/p/w500/" + response.poster_path;
                            tvTitle.setText(response.original_title);
                            tvDate.setText("IMDB Ratings : "+response.vote_average);
                            tvDesc.setText(response.overview);
                            Glide.with(context).load(iurl)
                                    .crossFade()
                                    .centerCrop()
                                    .placeholder(R.mipmap.ic_launcher_round)
                                    .error(R.mipmap.ic_launcher)
                                    .into(ivArticle);
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
