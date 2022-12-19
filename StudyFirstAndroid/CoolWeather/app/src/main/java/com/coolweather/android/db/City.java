package com.coolweather.android.db;

import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 市实体类
 * </p>
 *
 * @author: zj970
 * @date: 2022/12/19
 */
@Setter
@Getter
public class City {
    private int id;
    /**
     * 市的名字
     */
    private String cityName;
    /**
     * 市的代号
     */
    private int cityCode;
    /**
     * 当前市所属省份的id
     */
    private int provinceId;
}
