package com.ican.vo;

import com.ican.domain.Blog;

public class BlogVO {
    private int id;
    private int userId;
    private int schoolId;
    private String name;
    private String headshot;
    private String gmtCreate;
    private String content;
    private String image;
    private int commentCount;
    private int likeCount;
    private int hits;

    public BlogVO() {

    }

    public BlogVO(Blog blog) {
        this.id = blog.getId();
        this.userId = blog.getUserId();
        this.content = blog.getContent();
        this.gmtCreate = blog.getGmtCreate();
        this.likeCount = blog.getLikeCount();
        this.hits = blog.getHits();
        this.image = blog.getImage();
        this.commentCount = blog.getCommentCount();
    }

    public int getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(int schoolId) {
        this.schoolId = schoolId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHeadshot() {
        return headshot;
    }

    public void setHeadshot(String headshot) {
        this.headshot = headshot;
    }

    public String getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(String gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(int commentCount) {
        this.commentCount = commentCount;
    }

    public int getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(int likeCount) {
        this.likeCount = likeCount;
    }

    public int getHits() {
        return hits;
    }

    public void setHits(int hits) {
        this.hits = hits;
    }
}
