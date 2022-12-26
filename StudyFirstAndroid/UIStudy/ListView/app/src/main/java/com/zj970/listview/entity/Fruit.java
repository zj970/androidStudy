package com.zj970.listview.entity;

/**
 * 水果实体类
 */
public class Fruit {
    /**
     * 水果名字
     */
    private String name;

    /**
     * 水果图片id
     */
    private int imageId;

    /**
     * 构造水果实体类的有参构造----默认的构造器
     * @param name 水果名
     * @param imageId 水果图片id
     */
    public Fruit(String name,int imageId){
        this.name = name;
        this.imageId = imageId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }
}
