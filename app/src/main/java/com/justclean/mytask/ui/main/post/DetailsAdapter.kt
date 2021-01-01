package com.justclean.mytask.ui.main.post

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.justclean.mytask.R
import com.justclean.mytask.data.db.entity.DetailsData
import com.justclean.mytask.data.db.entity.PostData
import com.justclean.mytask.databinding.ViewDetailsListBinding
import com.justclean.mytask.databinding.ViewPostListBinding

class DetailsAdapter:RecyclerView.Adapter<DetailsAdapter.DetailslistViewHolder>(){

    inner class DetailslistViewHolder(val viewDetailsListBinding: ViewDetailsListBinding):RecyclerView.ViewHolder(viewDetailsListBinding.root){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetailslistViewHolder =DetailslistViewHolder(DataBindingUtil.inflate(
        LayoutInflater.from(parent.context), R.layout.view_details_list,parent,false))

    private val differCallback = object : DiffUtil.ItemCallback<DetailsData>(){
        override fun areItemsTheSame(oldItem: DetailsData, newItem: DetailsData): Boolean {
            return oldItem.id == newItem.id
        }


        override fun getChangePayload(oldItem: DetailsData, newItem: DetailsData): Any? {
            return super.getChangePayload(oldItem, newItem)
        }

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(oldItem: DetailsData, newItem: DetailsData): Boolean {
            return oldItem == newItem
        }

    }

    val differ = AsyncListDiffer(this,differCallback)

    override fun onBindViewHolder(holder: DetailslistViewHolder, position: Int) {
        val detailsData =differ.currentList[position]
        holder.viewDetailsListBinding.titleValue.setText(detailsData.name)
        holder.viewDetailsListBinding.body.setText(detailsData.body)
        holder.viewDetailsListBinding.emailValue.setText(detailsData.email)

    }

    override fun getItemCount(): Int = differ.currentList.size
}