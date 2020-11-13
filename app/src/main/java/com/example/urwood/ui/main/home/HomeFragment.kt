package com.example.urwood.ui.main.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.urwood.R
import com.example.urwood.databinding.FragmentHomeBinding
import com.example.urwood.repository.datasource.main.home.HomeRepoImpl
import com.example.urwood.repository.model.Home
import com.example.urwood.ui.main.home.adapter.KategoriAdapter
import com.example.urwood.ui.main.home.adapter.NewProdukItemDecoration
import com.example.urwood.ui.main.home.adapter.ProdukAdapter
import com.example.urwood.ui.main.home.adapter.ProdukItemDecoration
import com.example.urwood.ui.main.home.domain.HomeImpl
import com.example.urwood.utils.log

class HomeFragment : Fragment() {

    private lateinit var homeDataBinding: FragmentHomeBinding

    private val homeViewModel: HomeViewModel by lazy {
        ViewModelProvider(
            this,
            HomeVMFactory(HomeImpl(HomeRepoImpl()))
        ).get(HomeViewModel::class.java)
    }

    private lateinit var toolbar: Toolbar
    private lateinit var recyclerViewKategori: RecyclerView
    private lateinit var recyclerViewProduk: RecyclerView

    private var dummyKategori = ArrayList<Home.Kategori>()
    private var dummyProduk = ArrayList<Home.Produk>()
    private var adapterKategori: KategoriAdapter? = null
    private var adapterProduk: ProdukAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeDataBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        homeDataBinding.homeViewModel = homeViewModel

        setupViewBinding(homeDataBinding.root)

        if (activity is AppCompatActivity) {
            (activity as AppCompatActivity).setSupportActionBar(toolbar)
            requireContext().log("HomeViewDebug", "Toolbar Setup Complete")
        }
        toolbar.setTitleTextColor(requireContext().resources.getColor(R.color.white))
        (activity as AppCompatActivity).supportActionBar?.setTitle(R.string.location_dummy)

        // Add Dummy Data
        addDummyData()

        // RecyclerView Kategori
        recyclerViewKategori.layoutManager =
            LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)
        adapterKategori = KategoriAdapter(requireContext(), dummyKategori)
        recyclerViewKategori.adapter = adapterKategori

        // RecyclerView Produk
        recyclerViewProduk.layoutManager = GridLayoutManager(requireContext(), 2)
        val spacingInPixels: Int =
            resources.getDimensionPixelSize(R.dimen.recyclerview_margin)
//        recyclerViewProduk.addItemDecoration(ProdukItemDecoration(2, spacingInPixels, false, 0))
        recyclerViewProduk.addItemDecoration(NewProdukItemDecoration(16, -1))
        adapterProduk = ProdukAdapter(requireContext(), dummyProduk)
        recyclerViewProduk.adapter = adapterProduk

        return homeDataBinding.root
    }

    private fun addDummyData() {
        dummyKategori.add(Home.Kategori("Kasur", "Gambar"))
        dummyKategori.add(Home.Kategori("Meja", "Gambar"))
        dummyKategori.add(Home.Kategori("Kursi", "Gambar"))
        dummyKategori.add(Home.Kategori("Lemari", "Gambar"))
        dummyKategori.add(Home.Kategori("Hiasan", "Gambar"))

        dummyProduk.add((Home.Produk("Gambar", "Lemari", 100000, false)))
        dummyProduk.add((Home.Produk("Gambar", "Kursi", 100000, false)))
        dummyProduk.add((Home.Produk("Gambar", "Meja", 100000, false)))
    }

    fun setupViewBinding(view: View) {
        toolbar = view.findViewById(R.id.toolbar)
        recyclerViewKategori = view.findViewById(R.id.recycler_kategori)
        recyclerViewProduk = view.findViewById(R.id.recycler_produk)
    }
}