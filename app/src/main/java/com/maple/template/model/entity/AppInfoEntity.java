package com.maple.template.model.entity;

import com.maple.commonlib.http.resp.BaseResp;

public class AppInfoEntity extends BaseResp {

    public Data data;
    public static class Data {

        public AppInfo appInfo;

        public static class AppInfo {
            public String type;
            public boolean hasUpdate;
            public boolean ignorable;
            public int versionCode;
            public String diffCode;
            public String versionName;
            public String updateContent;
            public String downloadUrl;
            public String appId;
            public String createTime;
        }
    }
}
