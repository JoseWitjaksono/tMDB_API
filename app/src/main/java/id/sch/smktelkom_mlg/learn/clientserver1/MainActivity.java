package id.sch.smktelkom_mlg.learn.clientserver1;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import id.sch.smktelkom_mlg.learn.clientserver1.adapter.SourceAdapter;
import id.sch.smktelkom_mlg.learn.clientserver1.model.Source;
import id.sch.smktelkom_mlg.learn.clientserver1.model.SourcesResponse;
import id.sch.smktelkom_mlg.learn.clientserver1.service.GsonGetRequest;
import id.sch.smktelkom_mlg.learn.clientserver1.service.VolleySingleton;

public class MainActivity extends AppCompatActivity implements SourceAdapter.ISourceAdapter
{
    public static final String SOURCEID = "sourceId";
    public static final String SOURCESORTBY = "sourceSortBy";
    public static final String SOURCENAME = "sourceName";
    ArrayList<Source> mList = new ArrayList<>();
    SourceAdapter mAdapter;
    
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        StaggeredGridLayoutManager layoutManager =
                new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        mAdapter = new SourceAdapter(this, mList);
        recyclerView.setAdapter(mAdapter);
    
        setTitle("Popular Movies");
    
        downloadDataSources();
    }
    
    private void downloadDataSources()
    {
        String url = "https://api.themoviedb.org/3/movie/popular?api_key=ba8049249923de75707c1086afd6172d&language=en-US&page=1";
        
        GsonGetRequest<SourcesResponse> myRequest = new GsonGetRequest<SourcesResponse>
                (url, SourcesResponse.class, null, new Response.Listener<SourcesResponse>()
                {
                    
                    @Override
                    public void onResponse(SourcesResponse response)
                    {
                        Log.d("FLOW", "onResponse: " + (new Gson().toJson(response)));
                        if (response.page.equals("1"))
                        {
                            fillColor(response.results);
                            mList.addAll(response.results);
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
    
    private void fillColor(List<Source> sources)
    {
        for (int i = 0; i < sources.size(); i++)
            sources.get(i).color = ColorUtil.getRandomColor();
    }
    
    @Override
    public void showArticles(String id, String name)
    {
        Intent intent = new Intent(this, ArticlesActivity.class);
        intent.putExtra(SOURCEID, id);
        intent.putExtra(SOURCENAME, name);
        startActivity(intent);
    }
}
