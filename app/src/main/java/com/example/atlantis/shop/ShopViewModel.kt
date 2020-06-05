package com.example.atlantis.shop

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.atlantis.Product
import com.google.firebase.firestore.CollectionReference
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ShopViewModel(val productDatabase: CollectionReference, application: Application): AndroidViewModel(application)
{
    companion object{
        private const val TAG = "ShopViewModel"
    }

    init {
        getProductsFromDatabase()
    }

    val shopProducts: MutableLiveData<List<Product>> = MutableLiveData()

    fun onShopProductClicked(id: String)
    {
        Log.i(TAG, "Item clicked")
    }

    private fun getProductsFromDatabase()
    {
        var prodList = ArrayList<Product>()
        productDatabase.whereGreaterThan("inventory", 0).get()
        .addOnSuccessListener { documents ->
            for (document in documents) {
                Log.i(TAG, "${document.id} => ${document.data}")
                prodList.add(firestoreDocumentDataToProduct(document.data))
                shopProducts.value = prodList.toList()
            }
        }
        .addOnFailureListener { exception ->
            Log.w(TAG, "Error getting documents: ", exception)

        }
    }

    private fun firestoreDocumentDataToProduct(data: Map<String, Any>): Product
    {
        return Product(data.get("id") as String,
            data.get("description") as String,
            data.get("inventory") as Long,
            data.get("price") as String,
            data.get("productImage") as String,
            data.get("rating") as Double)
    }


}