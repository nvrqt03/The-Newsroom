package ajmitchell.android.thenewsroom.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import ajmitchell.android.thenewsroom.ArticleDetailActivity;
import ajmitchell.android.thenewsroom.R;
import ajmitchell.android.thenewsroom.models.NewsModel;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder> {
    private List<NewsModel.Article> articleList;
    private Context context;
    public OnArticleClickListener mOnArticleClickListener;

    public NewsAdapter(Context context, List<NewsModel.Article> articleList, OnArticleClickListener onArticleClickListener) {
        this.context = context;
        this.articleList = articleList;
        this.mOnArticleClickListener = onArticleClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View itemView = inflater.inflate(R.layout.news_item, parent, false);
        ViewHolder holder = new ViewHolder(itemView, mOnArticleClickListener);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        NewsModel.Article newsArticles = articleList.get(position);

        TextView titleTextView = holder.title.findViewById(R.id.article_title);
        TextView descriptionTextView = holder.description.findViewById(R.id.description);
        TextView dateTextView = holder.date.findViewById(R.id.date);

        titleTextView.setText(newsArticles.getTitle());
        descriptionTextView.setText(newsArticles.getDescription());
        dateTextView.setText(newsArticles.getPublishedAt());

        String imageUrl = newsArticles.getUrlToImage();
        Glide.with(holder.itemView)
                .load(imageUrl)
                .into(holder.image);
    }

    @Override
    public int getItemCount() {
        if (articleList == null) {
            return 0;
        }
        return articleList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public ImageView image;
        public TextView title;
        public TextView description;
        public TextView date;
        OnArticleClickListener onArticleClickListener;

        public ViewHolder(@NonNull View itemView, OnArticleClickListener onArticleClickListener) {
            super(itemView);
            this.onArticleClickListener = onArticleClickListener;

            image = itemView.findViewById(R.id.snippet_image);
            title = itemView.findViewById(R.id.article_title);
            description = itemView.findViewById(R.id.description);
            date = itemView.findViewById(R.id.date);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            onArticleClickListener.onArticleClick(getAdapterPosition());
        }
    }

    public interface OnArticleClickListener {
        void onArticleClick(int position);
    }
}
