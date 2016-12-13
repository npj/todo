package io.npj.todo;

import java.sql.Date;

/**
 * Created by npj on 12/12/16.
 */
abstract class BaseModel {
    private int id = 0;
    private Date createdAt = null;
    private Date updatedAt = null;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(java.util.Date createdAt) {
        this.createdAt = new Date(createdAt.getTime());
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(java.util.Date updatedAt) {
        this.updatedAt = new Date(updatedAt.getTime());
    }
}
