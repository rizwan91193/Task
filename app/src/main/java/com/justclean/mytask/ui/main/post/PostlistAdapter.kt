package com.justclean.mytask.ui.main.post

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.justclean.mytask.R
import com.justclean.mytask.data.db.entity.PostData
import com.justclean.mytask.databinding.ViewPostListBinding

class PostlistAdapter:RecyclerView.Adapter<PostlistAdapter.PostlistViewHolder>(){

inner class PostlistViewHolder(val viewPostListBinding: ViewPostListBinding):RecyclerView.ViewHolder(viewPostListBinding.root){

}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostlistViewHolder =
        PostlistViewHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.context),
            R.layout.view_post_list,parent,false))


    private val differCallback = object : DiffUtil.ItemCallback<PostData>(){
        override fun areItemsTheSame(oldItem: PostData, newItem: PostData): Boolean {
            return oldItem.id == newItem.id
        }


        override fun getChangePayload(oldItem: PostData, newItem: PostData): Any? {
            return super.getChangePayload(oldItem, newItem)
        }

        override fun areContentsTheSame(oldItem: PostData, newItem: PostData): Boolean {
            return oldItem == newItem
        }

    }

    val differ = AsyncListDiffer(this,differCallback)
    override fun onBindViewHolder(holder: PostlistViewHolder, position: Int) {
        val postData =differ.currentList[position]
        holder.viewPostListBinding.titleValue.setText(postData.title)
        holder.viewPostListBinding.body.setText(postData.body)
    }


    override fun getItemCount(): Int  = differ.currentList.size
}