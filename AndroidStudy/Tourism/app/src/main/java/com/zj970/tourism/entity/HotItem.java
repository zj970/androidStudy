package com.zj970.tourism.entity;

/**
 * <p>
 *  热门景点实体类，部分标签不一定需要，采取建造者模式
 * </p>
 *
 * @author: zj970
 * @date: 2023/1/9
 */
public class HotItem {
    final String hotItemIconURL;

    public String getHotItemIconURL() {
        return hotItemIconURL;
    }

    public String getHotItemContent() {
        return hotItemContent;
    }

    public String getHotPrice() {
        return hotPrice;
    }

    public String getHotLabel1() {
        return hotLabel1;
    }

    public String getHotLabel2() {
        return hotLabel2;
    }

    public String getHotLabel3() {
        return hotLabel3;
    }

    public String getHotLabel4() {
        return hotLabel4;
    }
    final String hotItemContent;
    final String hotPrice;
    final String hotLabel1;
    final String hotLabel2;
    final String hotLabel3;
    final String hotLabel4;
    private HotItem(Builder builder){
        hotItemIconURL = builder.mHotItemIconURL;
        hotItemContent = builder.mHotItemContent;
        hotPrice = builder.mHotPrice;
        hotLabel1 = builder.mHotLabel1;
        hotLabel2 = builder.mHotLabel2;
        hotLabel3 = builder.mHotLabel3;
        hotLabel4 = builder.mHotLabel4;
    }

    public static final class Builder {
        String mHotItemIconURL,mHotItemContent, mHotPrice, mHotLabel1, mHotLabel2, mHotLabel3, mHotLabel4;

        public Builder(String mHotItemIconURL, String mHotItemContent, String mHotPrice) {
            this.mHotItemIconURL = mHotItemIconURL;
            this.mHotItemContent = mHotItemContent;
            this.mHotPrice = mHotPrice;
        }

        public Builder mHotLabel1(String label){
            mHotLabel1 = label;
            return this;
        }

        public Builder mHotLabel2(String label){
            mHotLabel2 = label;
            return this;
        }

        public Builder mHotLabel3(String label){
            mHotLabel3 = label;
            return this;
        }

        public Builder mHotLabel4(String label){
            mHotLabel4 = label;
            return this;
        }

        public HotItem build(){
            return new HotItem(this);
        }

    }
}
