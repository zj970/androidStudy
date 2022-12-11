package com.zj970.materialtest.entity;

/**
 * <p>
 * fruit_entity
 * </p>
 *
 * @author: zj970
 * @date: 2022/12/11
 */
public class Fruit {
    private String name;
    private int imageId;

    public Fruit(String name, int imageId) {
        this.name = name;
        this.imageId = imageId;
    }

    public String getName() {
        return name;
    }

    public int getImageId() {
        return imageId;
    }
}
