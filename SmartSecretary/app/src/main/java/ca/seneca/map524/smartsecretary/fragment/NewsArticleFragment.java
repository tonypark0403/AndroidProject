package ca.seneca.map524.smartsecretary.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import ca.seneca.map524.smartsecretary.R;
import ca.seneca.map524.smartsecretary.model.News;

/**
 * Created by Wonho on 11/6/2016.
 */
public class NewsArticleFragment extends Fragment {

    private final static String TAG = "NewsArticleFragment";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_news_article, container, false);

        Bundle bundle = getArguments();
        if (bundle != null) {
            News news = getArguments().getParcelable("news");

            // headline
            TextView textHedline = (TextView)rootView.findViewById(R.id.textHeadline);
            textHedline.setText(news.getHeadline());

            // image
            ImageView imageSrc = (ImageView)rootView.findViewById(R.id.imageSrc);
            // width and height for image
            int width = rootView.getContext().getResources().getDisplayMetrics().widthPixels * 2 / 5;
            int height = width * 3 / 5;
            //int height = rootView.getContext().getResources().getDisplayMetrics().heightPixels;
            // set image with Picasso
            Picasso.with(rootView.getContext()).load(news.getImgSrc()).resize(width, height).into(imageSrc);

            // article
            TextView textArticle = (TextView)rootView.findViewById(R.id.textArticle);
            textArticle.setText(news.getArticle());
        }

        return rootView;
    }
}
