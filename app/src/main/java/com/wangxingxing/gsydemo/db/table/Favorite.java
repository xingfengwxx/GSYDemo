package com.wangxingxing.gsydemo.db.table;

import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;

@Entity
public class Favorite {
    @Id
    public long id;

    @Override
    public String toString() {
        return "Favorite{" +
                "id=" + id +
                '}';
    }
}
