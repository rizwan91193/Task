package com.justclean.mytask.ui.main.post

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.TypedArray
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.justclean.mytask.R
import com.justclean.mytask.data.db.entity.PostData
import com.justclean.mytask.databinding.ViewPostListBinding
import com.justclean.mytask.util.RecyclerViewClickListener
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.random.Random

class PostlistAdapter(val context:Context):RecyclerView.Adapter<PostlistAdapter.PostlistViewHolder>(){

    private var recyclerViewClickListener:RecyclerViewClickListener?=null

inner class PostlistViewHolder(val viewPostListBinding: ViewPostListBinding):RecyclerView.ViewHolder(viewPostListBinding.root){

}
    fun setOnRecyclerItemClickListener(recyclerViewClickListener: RecyclerViewClickListener){
        this.recyclerViewClickListener = recyclerViewClickListener
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

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(oldItem: PostData, newItem: PostData): Boolean {
            return oldItem == newItem
        }

    }

    val differ = AsyncListDiffer(this,differCallback)
    override fun onBindViewHolder(holder: PostlistViewHolder, position: Int) {
        val postData =differ.currentList[position]
        holder.viewPostListBinding.titleValue.setText(postData.title)
        holder.viewPostListBinding.body.setText(postData.body)
        CoroutineScope(Dispatchers.Main).launch {
            Glide.with(context).load(getRandomImage()).into(holder.viewPostListBinding.postImage)

        }
       /* Thread(Runnable {

        }).start()*/
//        Glide.with(context).load(getRandomImage()).into(holder.viewPostListBinding.postImage)
//        val postImage = context.resources.getResourceEntryName()
        holder.viewPostListBinding.rootLayout.setOnClickListener{
            recyclerViewClickListener?.onItemClickListener(postData.id)
        }
    }
    @SuppressLint("Recycle")
    private suspend fun getRandomImage():Int{
        val imgs:TypedArray
        imgs = context.resources.obtainTypedArray(R.array.PostImages)
        val random:Random =Random
        val rndInd = random.nextInt(imgs.length())
        return imgs.getResourceId(rndInd,0)
    }

    override fun getItemCount(): Int  = differ.currentList.size
}