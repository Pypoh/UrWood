package com.example.urwood.repository.datasource.firestore.promo

import android.util.Log
import com.example.urwood.repository.model.Promo
import com.example.urwood.utils.viewobject.Resource
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import kotlinx.coroutines.tasks.await

class PromoRepoImpl : IPromoRepo {

    private val db: FirebaseFirestore by lazy { FirebaseFirestore.getInstance() }
    private val promoReference: CollectionReference by lazy { db.collection("promo") }

    private var promoList = ArrayList<Promo.Promotion>()

    override suspend fun getAllPromo(): Resource<List<Promo.Promotion>?> {
        return try {
            promoReference.get().addOnSuccessListener {
                for (promo in it) {
                    val promoObject = promo.toObject(Promo.Promotion::class.java)
                    promoList.add(promoObject)
                }
            }.await()

            Resource.Success(promoList)
        } catch (e: FirebaseFirestoreException) {
            Log.d("PromoDebug", e.message!!)
            Resource.Failure(e)
        }
    }
}