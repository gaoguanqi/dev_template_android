package com.maple.template.ui.adapter

import android.widget.ImageView
import android.widget.TextView
import com.maple.commonlib.ext.load
import com.maple.template.R
import com.maple.template.model.entity.BannerEntity
import com.zhpan.bannerview.BaseBannerAdapter
import com.zhpan.bannerview.BaseViewHolder

class BannerAdapter: BaseBannerAdapter<BannerEntity.Data.Banner>() {

    override fun bindData(
        holder: BaseViewHolder<BannerEntity.Data.Banner>?,
        data: BannerEntity.Data.Banner?,
        position: Int,
        pageSize: Int) {

        if(holder != null && data != null) {
            val tvTitle = holder.findViewById<TextView>(R.id.tv_title)
            val ivBanner = holder.findViewById<ImageView>(R.id.iv_banner)
            tvTitle.text = data.title
            ivBanner.load(data.adUrl)
        }
    }

    override fun getLayoutId(viewType: Int): Int = R.layout.item_banner
}