package com.haidev.tixtestnfc.ui.main.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.recyclerview.widget.RecyclerView
import com.haidev.tixtestnfc.R
import com.haidev.tixtestnfc.data.local.entity.NFCEntity
import com.haidev.tixtestnfc.databinding.ItemRowNfcBinding


class ItemNFCAdapter : RecyclerView.Adapter<ItemNFCAdapter.ViewHolder>() {
    private var list = mutableListOf<NFCEntity>()

    private var itemClickListener: ItemClickListener? = null

    interface ItemClickListener {
        fun onItemClickDetail(view: View, data: NFCEntity)
    }

    fun setItemClickListener(itemClickListener: ItemClickListener?) {
        this.itemClickListener = itemClickListener
    }

    fun setData(list: List<NFCEntity>) {
        this.list.clear()
        this.list.addAll(list)
        notifyDataSetChanged()
    }

    inner class ViewHolder(private val binding: ItemRowNfcBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindItem(
            data: NFCEntity
        ) {
            binding.root.setOnClickListener {
                itemClickListener?.onItemClickDetail(it, data)
            }

            binding.tvNfcMessage.text = data.message
            binding.tvNfcSerialNumber.text = data.serialNumber
            binding.tvActionMenu.setOnClickListener {
                val popup = PopupMenu(it.context, it)
                popup.inflate(R.menu.nfc_option_menu)
                popup.setOnMenuItemClickListener { item ->
                    when (item.itemId) {
                        R.id.menuDelete -> {}
                        R.id.menuUpdate -> {}
                        R.id.menuSync -> {}
                    }
                    false
                }
                popup.show()

            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int
    ) = ViewHolder(
        ItemRowNfcBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
    )

    override fun onBindViewHolder(holder: ItemNFCAdapter.ViewHolder, position: Int) {
        if (list.isNotEmpty()) {
            holder.bindItem(list[position])
        }
    }

    override fun getItemCount() = list.size
}