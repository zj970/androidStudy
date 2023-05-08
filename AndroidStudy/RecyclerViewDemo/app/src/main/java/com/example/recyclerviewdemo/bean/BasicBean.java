package com.example.recyclerviewdemo.bean;

/**
 * @auther zj970
 * @create 2023-05-05 下午4:16
 */
public class BasicBean {
    private String title;
    private String content;

    public BasicBean(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
