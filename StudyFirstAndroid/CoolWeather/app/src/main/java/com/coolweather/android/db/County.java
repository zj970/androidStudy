package com.coolweather.android.db;

import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 城市实体类
 * </p>
 *
 * @author: zj970
 * @date: 2022/12/19
 */
@Getter
@Setter
public class County {
    private int id;
    /**
     * 县名
     */
    private String countyName;
    /**
     * 县所对应的天气id
     */
    private String weatherId;
    /**
     * 当前县所属市的id值
     */
    private int cityId;
}
