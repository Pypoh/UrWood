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
import com.example.urwood.ui.main.home.adapter.*
import com.example.urwood.ui.main.home.domain.HomeImpl
import com.example.urwood.utils.log
import com.smarteist.autoimageslider.SliderView

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
    private lateinit var recyclerViewToko: RecyclerView
    private lateinit var recyclerViewProduk: RecyclerView
    private lateinit var imageSliderAds: SliderView

    private var dummyKategori = ArrayList<Home.CircleIcon>()
    private var dummyToko = ArrayList<Home.CircleIcon>()
    private var dummyProduk = ArrayList<Home.Produk>()
    private var dummyImageSlider = ArrayList<Home.Advertisement>()

    private var adapterKategori: CircleIconAdapter? = null
    private var adapterToko: CircleIconAdapter? = null
    private var adapterProduk: ProdukAdapter? = null
    private var adapterAds: AdsSliderAdapter? = null

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
        recyclerViewKategori.addItemDecoration(ProdukItemDecoration(16, 0))
        adapterKategori = CircleIconAdapter(requireContext(), dummyKategori)
        recyclerViewKategori.adapter = adapterKategori

        // RecyclerView Toko
        recyclerViewToko.layoutManager =
            LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)
        recyclerViewToko.addItemDecoration(ProdukItemDecoration(16, 0))
        adapterToko = CircleIconAdapter(requireContext(), dummyToko)
        recyclerViewToko.adapter = adapterToko

        // RecyclerView Produk
        recyclerViewProduk.layoutManager = GridLayoutManager(requireContext(), 2)
        recyclerViewProduk.addItemDecoration(ProdukItemDecoration(16, -1))
        adapterProduk = ProdukAdapter(requireContext(), dummyProduk)
        recyclerViewProduk.adapter = adapterProduk

        // Image Slider
        adapterAds = AdsSliderAdapter(requireContext(), dummyImageSlider)
        imageSliderAds.setSliderAdapter(adapterAds!!)
        imageSliderAds.startAutoCycle()

        return homeDataBinding.root
    }

    private fun addDummyData() {
        dummyKategori.add(Home.CircleIcon("Kasur", R.drawable.ic_kasur, 0))
        dummyKategori.add(Home.CircleIcon("Meja", R.drawable.ic_table, 0))
        dummyKategori.add(Home.CircleIcon("Kursi", R.drawable.ic_kursi, 0))
        dummyKategori.add(Home.CircleIcon("Lemari", R.drawable.ic_lemari, 0))
        dummyKategori.add(Home.CircleIcon("Hiasan", R.drawable.ic_hiasan, 0))

        dummyToko.add(Home.CircleIcon("Ikea", R.drawable.ikea_logo, 1))
        dummyToko.add(Home.CircleIcon("Kusen Jaya", null, 1))
        dummyToko.add(Home.CircleIcon("Kayuku", null, 1))

        dummyProduk.add((Home.Produk("Gambar", "Lemari", 100000, false)))
        dummyProduk.add((Home.Produk("Gambar", "Kursi", 100000, false)))
        dummyProduk.add((Home.Produk("Gambar", "Meja", 100000, false)))

        dummyImageSlider.add(Home.Advertisement(R.drawable.image_slider_example_1))
        dummyImageSlider.add(Home.Advertisement(R.drawable.image_slider_example_2))
    }

    fun setupViewBinding(view: View) {
        toolbar = view.findViewById(R.id.toolbar)
        recyclerViewKategori = view.findViewById(R.id.recycler_kategori)
        recyclerViewToko = view.findViewById(R.id.recycler_toko)
        recyclerViewProduk = view.findViewById(R.id.recycler_produk)
        imageSliderAds = view.findViewById(R.id.ads_banner_slider)
    }
}