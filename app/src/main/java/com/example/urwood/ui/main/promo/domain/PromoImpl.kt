package com.example.urwood.ui.main.promo.domain

import com.example.urwood.repository.datasource.firestore.promo.IPromoRepo
import com.example.urwood.repository.model.Promo
import com.example.urwood.utils.viewobject.Resource

class PromoImpl(private val promoRepository: IPromoRepo) : IPromo {
    override suspend fun getAllPromo(): Resource<List<Promo.Promotion>?> =
        promoRepository.getAllPromo()
}