package com.example.atlantis

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.example.atlantis.cart.CartFragment
import com.example.atlantis.favourites.FavouritesFragment
import com.example.atlantis.shop.ShopFragment
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.fragment_main.*

class MainFragment(): Fragment()
{
    companion object
    {
        private const val TAG = "MainFragment"
        const val FAVOURITES_TAB_INDEX = 0
        const val SHOP_TAB_INDEX = 1
        const val CART_TAB_INDEX = 2
    }

    private lateinit var viewPager: ViewPager
    private lateinit var pagerAdapter: MainFragmentPagerAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle? ): View?
    {
        // Inflate the layout for this fragment
        val layout = inflater.inflate(R.layout.fragment_main, container, false)
        // Get viewpager and create adapter
        viewPager = layout.findViewById(R.id.viewPager)
        pagerAdapter = MainFragmentPagerAdapter.Builder(requireActivity().supportFragmentManager)
            .setFavouritesFragment(FavouritesFragment())
            .setShopFragment(ShopFragment())
            .setCartFragment(CartFragment())
            .build()

        // Set <viewPager>'s adapter to the created FragmentPagerAdapter
        viewPager.adapter = pagerAdapter


        // So for some reason when I setup <navigationBar> with a view pager
        // the text properties set on the individual tabs are not used, so I have to set them here.
        val tabLayout = layout.findViewById<TabLayout>(R.id.navigationBar)
        tabLayout.setupWithViewPager(viewPager)
        tabLayout.getTabAt(FAVOURITES_TAB_INDEX)!!.text = "Favourites"
        tabLayout.getTabAt(SHOP_TAB_INDEX)!!.text = "Shop"
        tabLayout.getTabAt(CART_TAB_INDEX)!!.text = "Cart"

        return layout
    }


}