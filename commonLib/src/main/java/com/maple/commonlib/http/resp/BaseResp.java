package com.maple.commonlib.http.resp;


import com.maple.commonlib.http.error.ERROR;

public class BaseResp {
    public String code = ERROR.UNKNOWN.getKey();
    public String msg = ERROR.UNKNOWN.getValue();

}
