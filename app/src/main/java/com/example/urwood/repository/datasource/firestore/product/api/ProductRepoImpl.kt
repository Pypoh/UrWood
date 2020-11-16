package com.example.urwood.repository.datasource.firestore.product.api

import android.util.Log
import com.example.urwood.repository.model.Product
import com.example.urwood.utils.viewobject.Resource
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import kotlinx.coroutines.tasks.await

class ProductRepoImpl : IProductRepo {

    private val db: FirebaseFirestore by lazy { FirebaseFirestore.getInstance() }
    private val productReference: CollectionReference by lazy { db.collection("products") }
    private var productModel: Product.ProductDetail? = null
    private var productList = ArrayList<Product.ProductDetail>()
    private val userReference: CollectionReference by lazy { db.collection("users") }
    private var userFavoriteProducts = ArrayList<String>()

    override suspend fun getProduct(productId: String): Resource<Product.ProductDetail?> {
        return Resource.Success(null)
    }

    override suspend fun getProductUserList(uid: String): Resource<List<Product.ProductDetail>?> {
        return try {

            // TODO: User Favorite
//            userReference.document(uid)

            productReference.get().addOnSuccessListener {
                for (product in it) {
                    val productObject = product.toObject(Product.ProductDetail::class.java)
                    val extractedProduct = Product.ProductDetail(
                        productObject.image,
                        productObject.name,
                        productObject.price,
                        false,
                        product.id
                    )
                    Log.d("ProductDebug", product.id + "")
                    productList.add(extractedProduct)
                }
            }.await()
            Resource.Success(productList)
        } catch (e: FirebaseFirestoreException) {
            Resource.Failure(e)
        }
    }

    override suspend fun getAllProducts(): Resource<List<Product.ProductDetail>?> {
        return try {
            productReference.get().addOnSuccessListener {
                for (product in it) {
                    val productObject = product.toObject(Product.ProductDetail::class.java)
                    productList.add(productObject)
                }
            }.await()
            Resource.Success(productList)
        } catch (e: FirebaseFirestoreException) {
            Resource.Failure(e)
        }
    }

    override suspend fun postProduct(product: Product.ProductDetail) {
        TODO("Not yet implemented")
    }

}