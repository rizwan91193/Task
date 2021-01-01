package com.justclean.mytask.ui.main.favorites

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.justclean.mytask.R
import com.justclean.mytask.data.db.entity.FavoritesData
import com.justclean.mytask.data.db.entity.PostData
import com.justclean.mytask.databinding.ViewFavoritesListBinding
import com.justclean.mytask.databinding.ViewPostListBinding

class FavoritesAdapter:RecyclerView.Adapter<FavoritesAdapter.FavoritesListViewHolder>(){
    inner class FavoritesListViewHolder(val viewFavoritesListBinding: ViewFavoritesListBinding):RecyclerView.ViewHolder(viewFavoritesListBinding.root){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoritesListViewHolder =FavoritesListViewHolder(DataBindingUtil.inflate(
        LayoutInflater.from(parent.context), R.layout.view_favorites_list,parent,false))
    private val differCallback = object : DiffUtil.ItemCallback<FavoritesData>(){
        override fun areItemsTheSame(oldItem: FavoritesData, newItem: FavoritesData): Boolean {
            return oldItem.id == newItem.id
        }


        override fun getChangePayload(oldItem: FavoritesData, newItem: FavoritesData): Any? {
            return super.getChangePayload(oldItem, newItem)
        }

        override fun areContentsTheSame(oldItem: FavoritesData, newItem: FavoritesData): Boolean {
            return oldItem == newItem
        }

    }

    val differ = AsyncListDiffer(this,differCallback)
    override fun onBindViewHolder(holder: FavoritesListViewHolder, position: Int) {
        val favData =differ.currentList[position]
        holder.viewFavoritesListBinding.titleValue.setText(favData.title)
        holder.viewFavoritesListBinding.body.setText(favData.body)
    }

    override fun getItemCount(): Int = differ.currentList.size
}