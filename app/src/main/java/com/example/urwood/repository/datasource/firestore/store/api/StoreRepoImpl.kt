package com.example.urwood.repository.datasource.firestore.store.api

import com.example.urwood.repository.model.Product
import com.example.urwood.repository.model.User
import com.example.urwood.utils.viewobject.Resource
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import kotlinx.coroutines.tasks.await

class StoreRepoImpl : IStoreRepo {

    private val db: FirebaseFirestore by lazy { FirebaseFirestore.getInstance() }
    private val userReference: CollectionReference by lazy { db.collection("users") }
    private val productReference: CollectionReference by lazy { db.collection("products") }

    private var storeObject: User.Store? = User.Store()
    private var ownerId: String? = ""

    private var productList = ArrayList<Product.ProductDetail>()

    private val mAuth: FirebaseAuth by lazy { FirebaseAuth.getInstance() }

    override suspend fun getStoreData(storeId: String): Resource<User.Store> {
        return try {
            userReference.whereEqualTo("storeId", storeId).get().addOnSuccessListener {
                for (item in it) {
                    ownerId = item.id
                }
            }.await()

            userReference.document(ownerId!!).collection("store").get().addOnSuccessListener {
                for (item in it) {
                    storeObject = item.toObject(User.Store::class.java)
                    if (ownerId == mAuth.currentUser!!.uid) {
                        storeObject!!.loggedInUserStore = true
                    }
                }
            }.await()

            // Check owner


            Resource.Success(storeObject!!)
        } catch (e: FirebaseFirestoreException) {
            Resource.Failure(e)
        }
    }

    override suspend fun getStoreProduct(storeId: String): Resource<List<Product.ProductDetail>?> {
        return try {
            productReference.whereEqualTo("storeId", storeId).get().addOnSuccessListener {
                for (item in it) {
                    val productObject = item.toObject(Product.ProductDetail::class.java)
                    val extractedProduct = Product.ProductDetail(
                        productObject.image,
                        productObject.name,
                        productObject.price,
                        false,
                        item.id
                    )
                    productList.add(extractedProduct)
                }
            }.await()

            Resource.Success(productList)
        } catch (e: FirebaseFirestoreException) {
            Resource.Failure(e)
        }
    }


}