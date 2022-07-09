package com.maple.template.ui.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.util.MutableBoolean
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.maple.baselib.ext.layoutInflater
import com.maple.baselib.utils.LogUtils
import com.maple.template.R
import com.maple.template.db.UserInfo
import com.maple.template.model.entity.BannerEntity
import com.maple.template.model.entity.RecordPageEntity
import com.zhpan.bannerview.BannerViewPager

@SuppressLint("NotifyDataSetChanged")
class RecordAdapter(val context: Context): RecyclerView.Adapter<RecyclerView.ViewHolder>(){


    companion object {
        const val TYPE_BANNER: Int = 0
        const val TYPE_LIST: Int = 1
    }

    private var listener: OnClickListener? = null

    private val list: MutableList<RecordPageEntity.Data.RecordList> = mutableListOf()
    private val banner: MutableList<BannerEntity.Data.Banner> = mutableListOf()

    fun setListener(listener: OnClickListener?) {
        this.listener = listener
    }

    fun setList(l: List<RecordPageEntity.Data.RecordList>?) {
        if(!l.isNullOrEmpty()) {
            this.list.clear()
            this.list.addAll(l)
            this.notifyDataSetChanged()
        }
    }

    fun upDataList(l: List<RecordPageEntity.Data.RecordList>?) {
        if(!l.isNullOrEmpty()) {
            this.list.addAll(l)
            this.notifyDataSetChanged()
        }
    }

    fun clearList() {
        if(!list.isNullOrEmpty()) {
            list.clear()
            this.notifyDataSetChanged()
        }
    }

    fun setBanner(l: List<BannerEntity.Data.Banner>?) {
        if(!l.isNullOrEmpty()) {
            banner.clear()
            banner.addAll(l)
            this.notifyItemChanged(0)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if(viewType == TYPE_BANNER) {
            val bannerView: View = context.layoutInflater.inflate(R.layout.layout_banner,parent,false)
            val bannerViewHolder = BannerViewHolder(bannerView)
            bannerViewHolder.bannerView?.setOnClickListener {
                listener?.onBannerItemClick(bannerViewHolder.bindingAdapterPosition,banner.get(bannerViewHolder.bindingAdapterPosition))
            }
            return bannerViewHolder
        }
        val listView: View = context.layoutInflater.inflate(R.layout.item_record,parent,false)
        val listViewHolder = ListViewHolder(listView)
        listViewHolder.itemRoot.setOnClickListener {
            listener?.onListItemClick(listViewHolder.bindingAdapterPosition,list.get(listViewHolder.bindingAdapterPosition))
        }
        return listViewHolder
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if ((holder is BannerViewHolder)) {
            holder.setData(banner)
        } else if ((holder is ListViewHolder)){
            holder.setData(list.get(position - 1))
        }
    }

    override fun getItemCount(): Int {
        return list.size + 1
    }

    override fun getItemViewType(position: Int): Int {
        if(position == 0) {
            return TYPE_BANNER
        }
        return TYPE_LIST
    }

    inner class ListViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val itemRoot: CardView = itemView.findViewById(R.id.item_root)
        private val tvUsername: TextView = itemView.findViewById(R.id.tv_username)
        private val tvPhone: TextView = itemView.findViewById(R.id.tv_phone)
        private val tvIp: TextView = itemView.findViewById(R.id.tv_ip)
        private val tvTime: TextView = itemView.findViewById(R.id.tv_time)
        fun setData(data: RecordPageEntity.Data.RecordList?) {
            data?.let {
                tvUsername.text = it.username
                tvPhone.text = it.phone
                tvIp.text = it.ip
                tvTime.text = it.createTime
            }
        }
    }


    inner class BannerViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){

        val bannerView: BannerViewPager<BannerEntity.Data.Banner>? = itemView.findViewById(R.id.banner)

        fun setData(bannerList: List<BannerEntity.Data.Banner>) {
            LogUtils.logGGQ("====BannerViewHolder==>>${bannerList.size}")
            bannerView?.apply {
                adapter = BannerAdapter()
                setOnPageClickListener { clickedView, position ->
                    listener?.onBannerItemClick(position, bannerList.get(position))
                }
            }?.create(bannerList)
        }
    }


    interface OnClickListener {
        fun onBannerItemClick(pos: Int, item: BannerEntity.Data.Banner?)
        fun onListItemClick(pos: Int, item: RecordPageEntity.Data.RecordList?)
    }
}