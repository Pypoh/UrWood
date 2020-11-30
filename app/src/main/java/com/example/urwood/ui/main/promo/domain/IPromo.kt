package com.example.urwood.ui.main.promo.domain

import com.example.urwood.repository.model.Promo
import com.example.urwood.utils.viewobject.Resource

interface IPromo {
    suspend fun getAllPromo() : Resource<List<Promo.Promotion>?>

}