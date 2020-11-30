package com.example.urwood.ui.main.profile.store.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.urwood.R

class StoreProductFragment : Fragment() {

    fun newInstance(): StoreProductFragment {
        return StoreProductFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_store_product, container, false)
    }


}