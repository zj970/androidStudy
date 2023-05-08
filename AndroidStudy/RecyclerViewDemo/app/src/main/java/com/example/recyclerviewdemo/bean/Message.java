package com.example.recyclerviewdemo.bean;

/**
 * @auther zj970
 * @create 2023-05-08 下午2:05
 */
public class Message {
    /**
     * 消息类型 0别人、1自己
     */
    private int type;

    private String content;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Message(int type, String content) {
        this.type = type;
        this.content = content;
    }
}
