package com.maple.commonlib.utils

import android.text.TextUtils
import com.blankj.utilcode.util.RegexUtils as RUtil

class RegexUtils {
    companion object{
        //是否是手机号
        fun isPhone(phone:String?):Boolean{
            if(TextUtils.isEmpty(phone)){
                return false
            }

            if(!RUtil.isMobileSimple(phone)){
                return false
            }
            return true
        }

        //是否是有效的密码
        //必须是 数字或字母或下划线 且6-18位
        private const val REGEX_PWD = "^[0-9A-Za-z_]{6,18}$"
        fun isPassword(pwd:String?):Boolean{
            if(TextUtils.isEmpty(pwd)){
                return false
            }
            if(!RUtil.isMatch(REGEX_PWD,pwd)){
                return false
            }
            return true
        }

        //是否是身份证号 (包括15位,18位,末尾带X,x)
        fun isIDCard(idCard:String?):Boolean{
            if(TextUtils.isEmpty(idCard)){
                return false
            }

            if(idCard?.length?:0 < 15 || idCard?.length?:0 > 18){
                return false
            }

            if(!RUtil.isIDCard15(idCard) && !RUtil.isIDCard18(idCard)){
                return false
            }

            return true
        }


        // 是否是邮箱
        fun isEmail(email:String?):Boolean{
            if(TextUtils.isEmpty(email)){
                return false
            }
            if(email?.length?:0 < 3){
                return false
            }

            if(!RUtil.isEmail(email)){
                return false
            }
            return true
        }
    }
}