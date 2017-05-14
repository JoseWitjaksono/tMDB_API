package id.sch.smktelkom_mlg.learn.clientserver1.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import id.sch.smktelkom_mlg.learn.clientserver1.R;
import id.sch.smktelkom_mlg.learn.clientserver1.model.Source;

/**
 * Created by hyuam on 12/10/2016.
 */

public class SourceAdapter extends RecyclerView.Adapter<SourceAdapter.ViewHolder>
{
    ArrayList<Source> list;
    ISourceAdapter mISourceAdapter;
    Context context;
    
    public SourceAdapter(Context context, ArrayList<Source> list)
    {
        this.list = list;
        this.context = context;
        mISourceAdapter = (ISourceAdapter) context;
    }
    
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.source_list, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }
    
    @Override
    public void onBindViewHolder(ViewHolder holder, int position)
    {
        Source source = list.get(position);
        String iurl = "http://image.tmdb.org/t/p/w500/"+source.backdrop_path;
        holder.tvName.setText(source.title);
        holder.tvDesc.setText(source.overview);
        holder.itemView.setBackgroundColor(source.color);
        Glide.with(context).load(iurl)
                .crossFade()
                .centerCrop()
                .placeholder(R.mipmap.ic_launcher_round)
                .error(R.mipmap.ic_launcher)
                .into(holder.ivSource);
    }
    
    @Override
    public int getItemCount()
    {
        if (list != null)
            return list.size();
        return 0;
    }
    
    public interface ISourceAdapter
    {
        void showArticles(String id, String title);
    }
    
    public class ViewHolder extends RecyclerView.ViewHolder
    {
        TextView tvName;
        TextView tvDesc;
        ImageView ivSource;
        
        public ViewHolder(View itemView)
        {
            super(itemView);
            tvName = (TextView) itemView.findViewById(R.id.textViewName);
            tvDesc = (TextView) itemView.findViewById(R.id.textViewDesc);
            ivSource = (ImageView) itemView.findViewById(R.id.imageViewSource);
            itemView.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    Source source = list.get(getAdapterPosition());
                    mISourceAdapter.showArticles(source.id, source.title);
                }
            });
        }
    }
}
