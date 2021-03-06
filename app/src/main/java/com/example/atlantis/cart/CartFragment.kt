package com.example.atlantis.cart

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.atlantis.R

class CartFragment: Fragment()
{
    private lateinit var textView: TextView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
        ): View?
    {

        val layout = inflater.inflate(R.layout.fragment_cart, container, false)!!
        textView = layout.findViewById(R.id.cart_text)

        return layout
    }
}