package ca.seneca.map524.smartsecretary.fragment;

import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.io.InputStreamReader;
import java.util.List;

import ca.seneca.map524.smartsecretary.adapter.NewsAdapter;
import ca.seneca.map524.smartsecretary.R;
import ca.seneca.map524.smartsecretary.model.News;
import ca.seneca.map524.smartsecretary.util.NewsHttpClient;
import ca.seneca.map524.smartsecretary.util.NewsXmlParser;

/**
 * Created by Wonho on 11/5/2016.
 */
public class NewsHeadlineFragment extends Fragment {

    private final static String TAG = "NewsHeadlineFragment";
    private final static String NEWS_URL = "http://www.cbc.ca/cmlink/rss-topstories";

    private View mRootView;
    private List<News> mNewsList;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        mRootView = inflater.inflate(R.layout.fragment_news_headline, container, false);

        TextView textView = (TextView)mRootView.findViewById(R.id.textView);
        textView.setText("CBC | Top Stories News");

        // excute NewsTask to get news from CBC
        new NewsTask().execute(NEWS_URL);

        return mRootView;
    }

    /**
     * update view to set images and texts
     */
    public void updateView() {
        if (mNewsList.size() > 0) {
            ListView listView = (ListView)mRootView.findViewById(R.id.listView);
            NewsAdapter adapter = new NewsAdapter(mRootView.getContext(), mNewsList);
            listView.setAdapter(adapter);

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    displayNewsArticle(i);
                }
            });
        }
    }

    /**
     * display the news article on NewsArticleFragment
     * @param i
     */
    public void displayNewsArticle(int i) {
        NewsArticleFragment newsArticleFragment = new NewsArticleFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable("news", mNewsList.get(i));
        newsArticleFragment.setArguments(bundle);
        getFragmentManager().beginTransaction().replace(R.id.container, newsArticleFragment).commit();
    }

    /**
     * AsyncTask to get news from CBC
     */
    public class NewsTask extends AsyncTask<String, Void, List<News>> {
        @Override
        protected List<News> doInBackground(String... strings) {

            InputStreamReader in = new NewsHttpClient().getNewsData(NEWS_URL);
            List<News> newsList = new NewsXmlParser().getNews(in);

            return newsList;
        }

        @Override
        protected void onPostExecute(List<News> newsList) {
            super.onPostExecute(newsList);

            mNewsList = newsList;
            updateView();
        }
    }
}
