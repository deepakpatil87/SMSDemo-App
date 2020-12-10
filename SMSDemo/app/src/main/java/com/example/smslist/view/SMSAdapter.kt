package com.example.smslist.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import com.example.smslist.R
import com.example.smslist.model.SMSCategory
import com.example.smslist.model.SMSTableModel
import com.example.smslist.model.SMSText

import com.thoughtbot.expandablerecyclerview.ExpandableRecyclerViewAdapter
import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup

class SMSAdapter(groups: List<ExpandableGroup<*>>?) :
    ExpandableRecyclerViewAdapter<GroupViewHolder, ChildViewHolder>(groups) {
   // lateinit var  mDb:SMSRoomDatabase
    override fun onCreateGroupViewHolder(parent: ViewGroup?, viewType: Int): GroupViewHolder {
        val itemView =
            LayoutInflater.from(parent?.context).inflate(R.layout.header_layout, parent, false)

        return GroupViewHolder(itemView)
    }

    override fun onCreateChildViewHolder(parent: ViewGroup?, viewType: Int): ChildViewHolder {
        val itemView =
            LayoutInflater.from(parent?.context).inflate(R.layout.child_layout, parent, false)
        return ChildViewHolder(itemView)
    }

    override fun onBindChildViewHolder(
        holder: ChildViewHolder?,
        flatPosition: Int,
        group: ExpandableGroup<*>?,
        childIndex: Int
    ) {
        val country: SMSText = group?.items?.get(childIndex) as SMSText
        holder?.bind(country)



    }

    override fun onBindGroupViewHolder(
        holder: GroupViewHolder?,
        flatPosition: Int,
        group: ExpandableGroup<*>?
    ) {
        val continent: SMSCategory = group as SMSCategory
        holder?.bind(continent)
    }

    companion object {
        private val WORDS_COMPARATOR = object : DiffUtil.ItemCallback<SMSTableModel>() {
            override fun areItemsTheSame(oldItem: SMSTableModel, newItem: SMSTableModel): Boolean {
                return oldItem === newItem
            }

            override fun areContentsTheSame(oldItem: SMSTableModel, newItem: SMSTableModel): Boolean {
                return oldItem.senderName == newItem.senderName
            }
        }
    }
}