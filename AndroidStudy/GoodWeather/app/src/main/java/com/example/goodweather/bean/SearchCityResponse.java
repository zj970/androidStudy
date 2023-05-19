package com.example.goodweather.bean;

import java.util.List;

/**
 * @auther zj970
 * @create 2023-05-19 下午10:05
 */
public class SearchCityResponse {
    /**
     * 返回的响应代码 "code":"200",
     */
    private String code;

    private ReferBean referBean;
    private List<LocationBean> locationBeanList;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public ReferBean getReferBean() {
        return referBean;
    }

    public void setReferBean(ReferBean referBean) {
        this.referBean = referBean;
    }

    public List<LocationBean> getLocationBeanList() {
        return locationBeanList;
    }

    public void setLocationBeanList(List<LocationBean> locationBeanList) {
        this.locationBeanList = locationBeanList;
    }

    /**
     *  *     "refer":{
     *  *         "sources":[
     *  *             "QWeather"
     *  *         ],
     *  *         "license":[
     *  *             "QWeather Developers License"
     *  *         ]
     *  *     }
     */
    public static class ReferBean{
        private List<String> sources;
        private List<String> license;

        public List<String> getSources() {
            return sources;
        }

        public void setSources(List<String> sources) {
            this.sources = sources;
        }

        public List<String> getLicense() {
            return license;
        }

        public void setLicense(List<String> license) {
            this.license = license;
        }
    }

    /**
     *  *             "name":"南山",
     *  *             "id":"101280604",
     *  *             "lat":"22.53122",
     *  *             "lon":"113.92942",
     *  *             "adm2":"深圳",
     *  *             "adm1":"广东省",
     *  *             "country":"中国",
     *  *             "tz":"Asia/Shanghai",
     *  *             "utcOffset":"+08:00",
     *  *             "isDst":"0",
     *  *             "type":"city",
     *  *             "rank":"25",
     *  *             "fxLink":"http://hfx.link/1u0z1"
     */
    public static class LocationBean{
        private String name;
        private String id;
        private String lat;
        private String lon;
        private String adm2;
        private String adm1;
        private String country;
        private String tz;
        private String utcOffset;
        private String isDst;
        private String type;
        private String rank;
        private String fxLink;


        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getLat() {
            return lat;
        }

        public void setLat(String lat) {
            this.lat = lat;
        }

        public String getLon() {
            return lon;
        }

        public void setLon(String lon) {
            this.lon = lon;
        }

        public String getAdm2() {
            return adm2;
        }

        public void setAdm2(String adm2) {
            this.adm2 = adm2;
        }

        public String getAdm1() {
            return adm1;
        }

        public void setAdm1(String adm1) {
            this.adm1 = adm1;
        }

        public String getCountry() {
            return country;
        }

        public void setCountry(String country) {
            this.country = country;
        }

        public String getTz() {
            return tz;
        }

        public void setTz(String tz) {
            this.tz = tz;
        }

        public String getUtcOffset() {
            return utcOffset;
        }

        public void setUtcOffset(String utcOffset) {
            this.utcOffset = utcOffset;
        }

        public String getIsDst() {
            return isDst;
        }

        public void setIsDst(String isDst) {
            this.isDst = isDst;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getRank() {
            return rank;
        }

        public void setRank(String rank) {
            this.rank = rank;
        }

        public String getFxLink() {
            return fxLink;
        }

        public void setFxLink(String fxLink) {
            this.fxLink = fxLink;
        }
    }
}

/**
 * 返回数据格式
 *
 * {
 *     "code":"200",
 *     "location":[
 *         {
 *             "name":"南山",
 *             "id":"101280604",
 *             "lat":"22.53122",
 *             "lon":"113.92942",
 *             "adm2":"深圳",
 *             "adm1":"广东省",
 *             "country":"中国",
 *             "tz":"Asia/Shanghai",
 *             "utcOffset":"+08:00",
 *             "isDst":"0",
 *             "type":"city",
 *             "rank":"25",
 *             "fxLink":"http://hfx.link/1u0z1"
 *         },
 *         {
 *             "name":"南山",
 *             "id":"101051206",
 *             "lat":"47.31324",
 *             "lon":"130.27552",
 *             "adm2":"鹤岗",
 *             "adm1":"黑龙江省",
 *             "country":"中国",
 *             "tz":"Asia/Shanghai",
 *             "utcOffset":"+08:00",
 *             "isDst":"0",
 *             "type":"city",
 *             "rank":"45",
 *             "fxLink":"http://hfx.link/1tlo1"
 *         }
 *     ],
 *     "refer":{
 *         "sources":[
 *             "QWeather"
 *         ],
 *         "license":[
 *             "QWeather Developers License"
 *         ]
 *     }
 * }
 */