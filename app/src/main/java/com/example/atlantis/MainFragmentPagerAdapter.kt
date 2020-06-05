package com.example.atlantis

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.atlantis.cart.CartFragment
import com.example.atlantis.favourites.FavouritesFragment
import com.example.atlantis.shop.ShopFragment

class MainFragmentPagerAdapter(fm: FragmentManager): FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT)
{
    private lateinit var shopFragment: ShopFragment
    private lateinit var favouritesFragment: FavouritesFragment
    private lateinit var cartFragment: CartFragment

    override fun getItem(position: Int): Fragment
    {
        return if(position == MainFragment.FAVOURITES_TAB_INDEX)
        {
            favouritesFragment
        }
        else if(position == MainFragment.SHOP_TAB_INDEX)
        {

            shopFragment
        }
        else
        {
            cartFragment
        }

    }

    override fun getCount(): Int
    {
        return 3
    }



    class Builder(fm: FragmentManager)
    {
        private var adapter = MainFragmentPagerAdapter(fm)

        fun setShopFragment(sf: ShopFragment): Builder
        {
            adapter.shopFragment = sf
            return this
        }

        fun setCartFragment(cf: CartFragment): Builder
        {
            adapter.cartFragment = cf
            return this
        }

        fun setFavouritesFragment(ff: FavouritesFragment): Builder
        {
            adapter.favouritesFragment = ff
            return this
        }

        fun build(): MainFragmentPagerAdapter
        {
            return adapter
        }
    }
}