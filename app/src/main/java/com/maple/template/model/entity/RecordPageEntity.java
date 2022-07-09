package com.maple.template.model.entity;

import com.maple.commonlib.http.resp.BaseResp;

import java.util.List;


public class RecordPageEntity extends BaseResp {

    public Data data;

    public static class Data {

        public int current;
        public int total;
        public int pages;
        public int size;
        public List<RecordList> list;

        public static class RecordList {
            public String id;
            public String userId;
            public String username;
            public String phone;
            public String ip;
            public String createTime;
        }
    }

}
