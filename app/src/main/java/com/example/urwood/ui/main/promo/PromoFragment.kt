package com.example.urwood.ui.main.promo

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.urwood.R
import com.example.urwood.databinding.FragmentPromoBinding
import com.example.urwood.repository.datasource.firestore.promo.PromoRepoImpl
import com.example.urwood.repository.model.Promo
import com.example.urwood.ui.main.promo.adapter.PromoAdapter
import com.example.urwood.ui.main.promo.domain.PromoImpl
import com.example.urwood.utils.viewobject.Resource

class PromoFragment : Fragment() {

    private lateinit var promoDataBinding: FragmentPromoBinding

    private val promoViewModel: PromoViewModel by lazy {
        ViewModelProvider(
            this,
            PromoVMFactory(PromoImpl(PromoRepoImpl()))
        ).get(PromoViewModel::class.java)
    }

    private lateinit var toolbar: Toolbar
    private lateinit var recyclerViewPromo: RecyclerView
    private lateinit var adaperPromo: PromoAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        promoDataBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_promo, container, false)
        promoDataBinding.promoViewModel = promoViewModel

        setupViews(promoDataBinding.root)

        if (activity is AppCompatActivity) {
            (activity as AppCompatActivity).setSupportActionBar(toolbar)
        }
        toolbar.setTitleTextColor(requireContext().resources.getColor(R.color.white))
        (activity as AppCompatActivity).supportActionBar?.setTitle("Promo")

        // Get Data
        fetchPromoData()

        // RecyclerView
        recyclerViewPromo.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        adaperPromo = PromoAdapter(requireContext(), ArrayList())
        recyclerViewPromo.adapter = adaperPromo
        adaperPromo.setOnItemClickListener(object: PromoAdapter.OnItemClickListener {
            override fun onItemClick(promoModel: Promo.Promotion) {
                // Do Something
            }

        })

        // Inflate the layout for this fragment
        return promoDataBinding.root
    }

    private fun fetchPromoData() {
        promoViewModel.getAllPromo()
        promoViewModel.promoResults.observe(viewLifecycleOwner, { task ->
            when (task) {
                is Resource.Loading -> {
                }

                is Resource.Success -> {
                    adaperPromo.setPromoData(task.data!!)
                    adaperPromo.notifyDataSetChanged()
                }

                is Resource.Failure -> {
                    Log.d("PromoDebug", task.throwable.message!!)
                }
            }
        })
    }

    private fun setupViews(view: View) {
        toolbar = view.findViewById(R.id.toolbar_promo)
        recyclerViewPromo = view.findViewById(R.id.recycler_promo)
    }

}