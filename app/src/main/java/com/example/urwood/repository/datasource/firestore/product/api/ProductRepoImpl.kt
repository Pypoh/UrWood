package com.example.urwood.repository.datasource.firestore.product.api

import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import com.example.urwood.repository.model.Product
import com.example.urwood.repository.model.User
import com.example.urwood.utils.viewobject.Resource
import com.google.android.gms.tasks.Continuation
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.UploadTask
import kotlinx.coroutines.tasks.await
import java.util.*
import kotlin.collections.ArrayList

class ProductRepoImpl : IProductRepo {

    private val db: FirebaseFirestore by lazy { FirebaseFirestore.getInstance() }
    private val productReference: CollectionReference by lazy { db.collection("products") }

    private var productData: Product.ProductDetail? = null
    private var productList = ArrayList<Product.ProductDetail>()

    private val mAuth: FirebaseAuth by lazy { FirebaseAuth.getInstance() }
    private val userReference: CollectionReference by lazy { db.collection("users") }
    private var userFavoriteProducts = ArrayList<String>()

    private val firebaseStore: FirebaseStorage? by lazy { FirebaseStorage.getInstance() }
    private val storageReference: StorageReference? by lazy { firebaseStore!!.reference }

    private var userData: User.UserData? = null
    private var storeObject: User.Store? = null

    override suspend fun getProduct(productId: String): Resource<Product.ProductDetail?> {
        return try {
            productReference.document(productId).get().addOnSuccessListener {
                productData = it.toObject(Product.ProductDetail::class.java)
            }.await()

            Resource.Success(productData)
        } catch (e: FirebaseFirestoreException) {
            Resource.Failure(e)
        }
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

    override suspend fun postProduct(product: Product.ProductDetail): Resource<Int> {
        return try {
            // Upload Image
            var imageUrl: String? = null
            val ref = storageReference?.child("uploads/" + UUID.randomUUID().toString())
            val uploadTask = product.imageUri?.let { ref?.putFile(it) }
            val urlTask =
                uploadTask?.continueWithTask(Continuation<UploadTask.TaskSnapshot, Task<Uri>> { task ->
                    if (!task.isSuccessful) {
                        task.exception?.let {
                            throw it
                        }
                    }
                    return@Continuation ref!!.downloadUrl
                })?.addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val downloadUri = task.result
                        imageUrl = downloadUri.toString()

                        // Get Store Data
                        val userId = mAuth.currentUser!!.uid
                        Log.d("UploadDebug", "User: $userId")
                        userReference.document(userId).collection("store").get().addOnSuccessListener {
                            for (item in it) {
                                storeObject = item.toObject(User.Store::class.java)
                                Log.d("UploadDebug", "Store: ${storeObject!!.storeId}")
                                storeObject!!.storeId = item.id
                                product.ownerId = userId
                                product.storeId = storeObject!!.storeId
                                product.image = imageUrl
                                product.imageUri = null
                                productReference.document().set(product).addOnSuccessListener {
                                    Log.d("UploadDebug", "Uploaded document")
                                }.addOnFailureListener {
                                    Log.d("UploadDebug", "Failed document")
                                }
                            }
                        }
                    } else {
                        // Handle failures
                    }
                }?.addOnFailureListener {
                    Resource.Failure(it)
                }
            Resource.Success(201)
        } catch (e: FirebaseFirestoreException) {
            Log.d("UploadDebug", e.message.toString())
            Resource.Failure(e)
        }
    }

    override suspend fun getProductStore(storeId: String, ownerId: String): Resource<User.Store?> {
        return try {
            storeObject = null
            userReference.document(ownerId).collection("store").get().addOnSuccessListener {
                for (item in it) {
                    storeObject = item.toObject(User.Store::class.java)
                    storeObject!!.storeId = item.id
                }
            }.await()

            userReference.document(ownerId).get().addOnSuccessListener {
                userData = it.toObject(User.UserData::class.java)
                storeObject!!.storePhoneNumber = userData!!.phoneNumber
            }.await()

            Resource.Success(storeObject)
        } catch (e: FirebaseFirestoreException) {
            Resource.Failure(e)
        }
    }

}