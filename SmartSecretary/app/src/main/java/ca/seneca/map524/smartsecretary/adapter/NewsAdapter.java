package ca.seneca.map524.smartsecretary.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import ca.seneca.map524.smartsecretary.R;
import ca.seneca.map524.smartsecretary.model.News;

/**
 * Created by Wonho on 11/6/2016.
 */
public class NewsAdapter extends BaseAdapter {

    private final static String TAG = "NewsAdapter";

    private Context mContext;
    private List<News> mNewsList;

    public NewsAdapter(Context context, List<News> newsList) {
        mContext = context;
        mNewsList = newsList;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        LayoutInflater inflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View item = inflater.inflate(R.layout.news_list_item, null);

        // set image using Picasso library (http://square.github.io/picasso/)
        ImageView imageItem = (ImageView)item.findViewById(R.id.imageItem);
        Picasso.with(mContext).load(mNewsList.get(i).getImgSrc()).into(imageItem);

        // set text
        TextView textItem = (TextView)item.findViewById(R.id.textItem);
        textItem.setText(mNewsList.get(i).getHeadline());

        return item;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public Object getItem(int i) {
        return mNewsList.get(i);
    }

    @Override
    public int getCount() {
        return mNewsList.size();
    }
}
