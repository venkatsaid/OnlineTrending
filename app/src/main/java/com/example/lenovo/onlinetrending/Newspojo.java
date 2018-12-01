
package com.example.lenovo.onlinetrending;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Newspojo implements Parcelable {
    public String status;
    public int totalResults;
    public ArrayList<Article> articles=null;
public Newspojo()
{

}
    public Newspojo(String status, int totalResults, ArrayList<Article> articles) {
        this.status = status;
        this.totalResults = totalResults;
        this.articles = articles;
    }

    protected Newspojo(Parcel in) {
        status = in.readString();
        totalResults = in.readInt();
        articles = in.createTypedArrayList(Article.CREATOR);
    }

    public static final Creator<Newspojo> CREATOR = new Creator<Newspojo>() {
        @Override
        public Newspojo createFromParcel(Parcel in) {
            return new Newspojo(in);
        }

        @Override
        public Newspojo[] newArray(int size) {
            return new Newspojo[size];
        }
    };

    public void setStatus(String status) {
        this.status = status;
    }

    public void setTotalResults(int totalResults) {
        this.totalResults = totalResults;
    }

    public void setArticles(ArrayList<Article> articles) {
        this.articles = articles;
    }

    public String getStatus() {

        return status;
    }

    public int getTotalResults() {
        return totalResults;
    }

    public ArrayList<Article> getArticles() {
        return articles;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(status);
        dest.writeInt(totalResults);
        dest.writeTypedList(articles);
    }


    /*

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("totalResults")
    @Expose
    private int totalResults;
    @SerializedName("articles")
    @Expose
    private List<Article> articles = null;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(Integer totalResults) {
        this.totalResults = totalResults;
    }

    public List<Article> getArticles() {
        return articles;
    }

    public void setArticles(List<Article> articles) {
        this.articles = articles;
    }
*/

}
