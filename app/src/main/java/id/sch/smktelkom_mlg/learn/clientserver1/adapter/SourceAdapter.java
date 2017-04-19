package id.sch.smktelkom_mlg.learn.clientserver1.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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
    
    public SourceAdapter(Context context, ArrayList<Source> list)
    {
        this.list = list;
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
        holder.tvName.setText(source.name);
        holder.tvDesc.setText(source.description);
        holder.itemView.setBackgroundColor(source.color);
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
        void showArticles(String id, String name, String sortBy);
    }
    
    public class ViewHolder extends RecyclerView.ViewHolder
    {
        TextView tvName;
        TextView tvDesc;
        
        public ViewHolder(View itemView)
        {
            super(itemView);
            tvName = (TextView) itemView.findViewById(R.id.textViewName);
            tvDesc = (TextView) itemView.findViewById(R.id.textViewDesc);
            itemView.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    Source source = list.get(getAdapterPosition());
                    List<String> sort = source.sortBysAvailable;
                    mISourceAdapter.showArticles(source.id, source.name, sort.get(sort.size() - 1));
                }
            });
        }
    }
}
