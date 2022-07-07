package com.maple.template.model.entity;

import com.maple.commonlib.http.resp.BaseResp;

public class LoginEntity extends BaseResp {

    public Data data;

    public static class Data {
        public User user;

        public static class User {
            public String userId;
            public String username;
            public String nickname;
            public String phone;
            public String gender;
            public String avatarUrl;
            public String token;
            public String openid;
            public String email;
            public String lastIp;
        }
    }



}
