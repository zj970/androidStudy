package com.zj970.tourism.entity;

/**
 * <p>
 *  异域风情Item实体类
 * </p>
 *
 * @author: zj970
 * @date: 2023/1/10
 */
public class ExoticItem {
    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    String country,iconURL,count;

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getIconURL() {
        return iconURL;
    }

    public void setIconURL(String iconURL) {
        this.iconURL = iconURL;
    }


    public ExoticItem(String country, String iconURL, String count) {
        this.country = country;
        this.iconURL = iconURL;
        this.count = count;
    }
}
