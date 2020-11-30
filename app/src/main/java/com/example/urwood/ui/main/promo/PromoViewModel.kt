package com.example.urwood.ui.main.promo

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.urwood.repository.model.Promo
import com.example.urwood.ui.main.promo.domain.IPromo
import com.example.urwood.utils.viewobject.Resource
import kotlinx.coroutines.Dispatchers
import java.lang.Exception

class PromoViewModel(private val useCase: IPromo) : ViewModel() {

    lateinit var promoResults: LiveData<Resource<List<Promo.Promotion>?>>

    fun getAllPromo() {
        promoResults = liveData(Dispatchers.IO) {
            emit(Resource.Loading())
            try {
                val data = useCase.getAllPromo()
                emit(data)
            } catch (e: Exception) {
                emit(Resource.Failure(e))
                Log.d("PromoDebug", e.message!!)
            }
        }
    }
}