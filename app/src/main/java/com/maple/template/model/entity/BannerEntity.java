package com.maple.template.model.entity;

import com.maple.commonlib.http.resp.BaseResp;

import java.util.List;

public class BannerEntity extends BaseResp {

    public Data data;
    public static class Data {
        public int current;
        public int total;
        public int pages;
        public int size;

        public List<Banner> list;
        public static class Banner{
            public String id;
            public String type;
            public String title;
            public String adUrl;
            public String createTime;
        }
    }
}
