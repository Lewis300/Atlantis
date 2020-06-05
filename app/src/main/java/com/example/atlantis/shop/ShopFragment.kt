package com.example.atlantis.shop

import com.example.atlantis.R
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.example.atlantis.Product
import com.example.atlantis.databinding.FragmentShopBinding
import com.example.atlantis.databinding.ShopProductListItemBinding
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders

class ShopFragment : Fragment()
{
    companion object{
        private const val TAG = "ShopFragment"
    }

    private lateinit var textView: TextView


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
        ): View?
    {

        val binding: FragmentShopBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_shop, container, false)

        val application = requireNotNull(this.activity).application

        // Access a Cloud Firestore instance
        val productDataBase = Firebase.firestore.collection("products")

        val viewModelFactory = ShopViewModelFactory(productDataBase, application)

        val shopViewModel =
            ViewModelProviders.of(
                this, viewModelFactory).get(ShopViewModel::class.java)

        binding.shopViewModel = shopViewModel

        val adapter = ShopProductAdapter(ShopProductListener {
            shopViewModel.onShopProductClicked(it)
        })

        binding.productList.adapter = adapter

        shopViewModel.shopProducts.observe(viewLifecycleOwner, Observer {
            it?.let{
                Log.i(TAG, "GRSGRH")
                adapter.data = it
            }
        })


        binding.lifecycleOwner = this

        return binding.root
    }


}