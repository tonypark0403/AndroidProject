package ca.seneca.map524.smartsecretary.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Wonho on 11/6/2016.
 */
public class News implements Parcelable {
    private String imgSrc;      // image source url
    private String headline;    // title
    private String article;     // description


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(imgSrc);
        parcel.writeString(headline);
        parcel.writeString(article);
    }

    Parcelable.Creator<News> CREATOR = new Creator<News>() {
        @Override
        public News createFromParcel(Parcel parcel) {
            News news = new News();

            // match saved order
            news.setImgSrc(parcel.readString());
            news.setHeadline(parcel.readString());
            news.setArticle(parcel.readString());

            return news;
        }

        @Override
        public News[] newArray(int i) {
            return new News[0];
        }
    };

    public void setImgSrc(String imgSrc) {
        this.imgSrc = imgSrc;
    }

    public String getImgSrc() {
        return imgSrc;
    }

    public void setHeadline(String headline) {
        this.headline = headline;
    }

    public String getHeadline() {
        return headline;
    }

    public void setArticle(String article) {
        this.article = article;
    }

    public String getArticle() {
        return article;
    }
}
