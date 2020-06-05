package com.example.atlantis.shop

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.Drawable
import android.os.AsyncTask
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.ListAdapter
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.atlantis.Product
import com.example.atlantis.R
import com.example.atlantis.databinding.ShopProductListItemBinding
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.shop_product_list_item.view.*
import java.io.InputStream
import java.net.URL


class ShopProductAdapter(val clickListener: ShopProductListener): ListAdapter<Product, ShopProductAdapter.ViewHolder>(ShopProductDiffCallback())
{
    companion object
    {
        private const val TAG = "ShopProductAdapter"
    }

    var data = listOf<Product>()
    set(value) {
        field = value
        //Log.i(TAG, value.get(0).id)
        notifyDataSetChanged()
    }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = data[position]
        holder.bind(item, clickListener)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder
    {
        return ViewHolder.from(parent)
    }


    class ViewHolder(val binding: ShopProductListItemBinding): RecyclerView.ViewHolder(binding.root)
    {
        fun bind(item: Product, clickListener: ShopProductListener)
        {
            binding.product = item
            binding.clickListener = clickListener

            binding.productNameTextView.text = item.description
            Picasso.get().load(item.imgUrl).into(binding.productImage)
            binding.ratingImageView.setImageResource(getImageResource(item.rating)!!)

            binding.executePendingBindings()
        }

        private fun getImageResource(rating: Double): Int?
        {
            return when(rating)
            {
                0.0 -> R.drawable.ic_rating_stars00
                0.5 -> R.drawable.ic_rating_stars05
                1.0 -> R.drawable.ic_rating_stars10
                1.5 -> R.drawable.ic_rating_stars15
                2.0 -> R.drawable.ic_rating_stars20
                2.5 -> R.drawable.ic_rating_stars25
                3.0 -> R.drawable.ic_rating_stars30
                3.5 -> R.drawable.ic_rating_stars35
                4.0 -> R.drawable.ic_rating_stars40
                4.5 -> R.drawable.ic_rating_stars45
                5.0 -> R.drawable.ic_rating_stars50
                else -> null
            }

        }

        companion object{
            fun from(parent: ViewGroup): ViewHolder
            {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ShopProductListItemBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
        val productNameTextView: TextView? = binding.productNameTextView
        val ratingImageView: ImageView? = binding.ratingImageView
        val productImage: ImageView? = binding.productImage
    }




}

class ShopProductDiffCallback(): DiffUtil.ItemCallback<Product>()
{
    override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
        return oldItem == newItem
    }
}

class ShopProductListener(val clickListener: (productId: String) -> Unit)
{
    fun onClick(product: Product) = clickListener(product.id)
}
