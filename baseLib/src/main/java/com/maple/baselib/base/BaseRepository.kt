package com.maple.baselib.base

import java.util.*

abstract class BaseRepository {

    abstract fun getPublicParams(): WeakHashMap<String, Any>

}