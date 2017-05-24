package com.github.zdongcoding.jsoup.demo;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

import com.github.zdongcoding.jsoup.annotation.Select;


/**
 * Created by zoudong on 2017/3/3.
 */
@Select(select = "body > div")
public class MediaInfoBean  implements Parcelable {
    public String pic_url;    //数据需要拼接所以使用  set 方法
    @Select(select = "span.tip", text = true)
    public String tip;
    @Select(select = "span[alt]", attr = "alt")
    public String media_type;
    @Select(select = "a span.poster_score", text = true)
    public String poster_score;
    @Select(select ="a[href]", attr ="href")
    public String url;
    @Select(select ="h3 > a[href]", text=true)
    public String title;

    @Select(select = "img[_src]", attr = "_src")
    public void setPic_url(String pic_url) {
        if (!TextUtils.isEmpty(pic_url)) {
            this.pic_url = pic_url;
        }
    }

//    @Select(select ="a[href]", attr ="href")
//    public void setUrl(String pic_url) {
//        if (!TextUtils.isEmpty(pic_url)) {
//            this.url = BeanUtils.getUrl(pic_url);
//        }
//    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.url);
        dest.writeString(this.pic_url);
        dest.writeString(this.title);
        dest.writeString(this.tip);
        dest.writeString(this.media_type);
        dest.writeString(this.poster_score);
    }

    public MediaInfoBean() {
    }

    protected MediaInfoBean(Parcel in) {
        this.url = in.readString();
        this.pic_url = in.readString();
        this.title = in.readString();
        this.tip = in.readString();
        this.media_type = in.readString();
        this.poster_score = in.readString();
    }

    public static final Creator<MediaInfoBean> CREATOR = new Creator<MediaInfoBean>() {
        @Override
        public MediaInfoBean createFromParcel(Parcel source) {
            return new MediaInfoBean(source);
        }

        @Override
        public MediaInfoBean[] newArray(int size) {
            return new MediaInfoBean[size];
        }
    };


    @Override
    public String toString() {
        return "MediaInfoBean{" +
                "url='" + url + '\'' +
                ", pic_url='" + pic_url + '\'' +
                ", title='" + title + '\'' +
                ", tip='" + tip + '\'' +
                ", media_type='" + media_type + '\'' +
                '}';
    }
}
