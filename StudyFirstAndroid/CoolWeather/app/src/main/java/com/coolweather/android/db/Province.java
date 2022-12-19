package com.coolweather.android.db;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.litepal.crud.LitePalSupport;

/**
 * <p>
 * 省份实体类
 * </p>
 *
 * @author: zj970
 * @date: 2022/12/19
 */
@Getter
@Setter
public class Province extends LitePalSupport {
    private int id;
    /**
     *省的名字
     */
    private String provinceName;
    /**
     * 省的代号
     */
    private int provinceCode;
}
