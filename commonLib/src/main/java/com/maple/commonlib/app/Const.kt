package com.maple.commonlib.app

class Const {

    object Path {
        val IMEI_PATH: String = android.os.Environment.getExternalStorageDirectory().toString() + "/android/dev-template-imei.text"
    }

    object SaveInfoKey {

        const val HAS_APP_FIRST = "hasFirst"

        const val ACCESS_TOKEN = "accessToken"
    }

    object BundleKey {
        const val EXTRA_URL = "url"
        const val EXTRA_OBJ = "obj"
        const val EXTRA_INDEX = "index"
        const val EXTRA_ID = "id"
        const val EXTRA_TYPE = "type"
        const val EXTRA_MODULE = "module"

        const val EXTRA_DATA = "data"
    }


    object EventCode {
        const val CODE_LOGIN = 101
    }


    object RoleKey {
        // 超级管理员
        const val KEY_ADMIN = "admin"
        // 普通用户
        const val KEY_USER = "user"
    }
}

