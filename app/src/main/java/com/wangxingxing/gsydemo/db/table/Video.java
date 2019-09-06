package com.wangxingxing.gsydemo.db.table;

import java.util.Date;

import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;
import io.objectbox.relation.ToOne;

@Entity
public class Video {
    @Id
    public long id;
    public String name;
    public Date date;

    public ToOne<Favorite> favorite;
    public ToOne<History> history;

    public Video() {
    }

    public Video(long id, String name, Date date) {
        this.id = id;
        this.name = name;
        this.date = date;
    }

    @Override
    public String toString() {
        return "Video{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", date=" + date +
                ", favorite=" + favorite +
                ", history=" + history +
                '}';
    }
}
