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

import id.sch.smktelkom_mlg.learn.clientserver1.R;
import id.sch.smktelkom_mlg.learn.clientserver1.model.Article;

/**
 * Created by hyuam on 12/10/2016.
 */

public class ArticleAdapter extends RecyclerView.Adapter<ArticleAdapter.ViewHolder>
{
    ArrayList<Article> list;
    IArticleAdapter mIArticleAdapter;
    Context context;
    
    public ArticleAdapter(Context context, ArrayList<Article> list)
    {
        this.list = list;
        this.context = context;
        mIArticleAdapter = (IArticleAdapter) context;
    }
    
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.article_list, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }
    
    @Override
    public void onBindViewHolder(final ViewHolder holder, int position)
    {
        Article article = list.get(position);
        holder.tvTitle.setText(article.title);
        holder.tvDate.setText(article.publishedAt);
        holder.tvDesc.setText(article.description);
        Glide.with(context).load(article.urlToImage)
                .crossFade()
                .centerCrop()
                .placeholder(R.mipmap.ic_launcher_round)
                .error(R.mipmap.ic_launcher)
                .into(holder.ivArticle);
    }
    
    @Override
    public int getItemCount()
    {
        if (list != null)
            return list.size();
        return 0;
    }
    
    public interface IArticleAdapter
    {
        void showDetail(String url);
    }
    
    public class ViewHolder extends RecyclerView.ViewHolder
    {
        ImageView ivArticle;
        TextView tvTitle;
        TextView tvDate;
        TextView tvDesc;
        
        public ViewHolder(View itemView)
        {
            super(itemView);
            ivArticle = (ImageView) itemView.findViewById(R.id.imageViewArticle);
            tvTitle = (TextView) itemView.findViewById(R.id.textViewTitle);
            tvDate = (TextView) itemView.findViewById(R.id.textViewDate);
            tvDesc = (TextView) itemView.findViewById(R.id.textViewDesc);
            itemView.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    mIArticleAdapter.showDetail(list.get(getAdapterPosition()).url);
                }
            });
        }
    }
}
