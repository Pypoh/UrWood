package com.example.urwood.repository.datasource.firestore.promo

import com.example.urwood.repository.model.Promo
import com.example.urwood.utils.viewobject.Resource

interface IPromoRepo {
    suspend fun getAllPromo() : Resource<List<Promo.Promotion>?>

}