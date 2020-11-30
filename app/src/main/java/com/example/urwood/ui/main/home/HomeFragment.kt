package com.example.urwood.ui.main.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.urwood.R
import com.example.urwood.databinding.FragmentHomeBinding
import com.example.urwood.repository.datasource.firestore.main.home.HomeRepoImpl
import com.example.urwood.repository.datasource.firestore.product.api.ProductRepoImpl
import com.example.urwood.repository.model.Home
import com.example.urwood.repository.model.Product
import com.example.urwood.ui.chat.discussion.DiscussionActivity
import com.example.urwood.ui.main.home.adapter.*
import com.example.urwood.ui.main.home.domain.HomeImpl
import com.example.urwood.ui.product.detail.ProductDetailActivity
import com.example.urwood.ui.product.domain.ProductImpl
import com.example.urwood.utils.viewobject.Resource
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.google.gson.Gson
import com.smarteist.autoimageslider.SliderView

class HomeFragment : Fragment() {

    private lateinit var homeDataBinding: FragmentHomeBinding

    private val homeViewModel: HomeViewModel by lazy {
        ViewModelProvider(
            this,
            HomeVMFactory(HomeImpl(HomeRepoImpl()), ProductImpl(ProductRepoImpl()))
        ).get(HomeViewModel::class.java)
    }

    private val gson = Gson()

    private lateinit var toolbar: Toolbar
    private lateinit var searchInputLayout: TextInputLayout
    private lateinit var searchInputText: TextInputEditText
    private lateinit var recyclerViewKategori: RecyclerView
    private lateinit var recyclerViewToko: RecyclerView
    private lateinit var recyclerViewProduk: RecyclerView
    private lateinit var imageSliderAds: SliderView
    private lateinit var toChatButton: ImageView

    private var dummyKategori = ArrayList<Home.CircleIcon>()
    private var dummyToko = ArrayList<Home.CircleIcon>()
    private var dummyProduk = ArrayList<Product.ProductDetail>()
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
//        adapterProduk = ProdukAdapter(requireContext(), dummyProduk)
        adapterProduk = ProdukAdapter(requireContext(), ArrayList())
        recyclerViewProduk.adapter = adapterProduk
        adapterProduk!!.setOnItemClickListener(object : ProdukAdapter.OnItemClickListener {
            override fun onItemClick(productDetailModel: Product.ProductDetail) {
                // Intent to Detail Page
                intentToDetail(productDetailModel)
            }
        })

        // Image Slider
        adapterAds = AdsSliderAdapter(requireContext(), dummyImageSlider)
        imageSliderAds.setSliderAdapter(adapterAds!!)
        imageSliderAds.startAutoCycle()

        // Fetch Data
        fetchProductData()

        return homeDataBinding.root
    }

    private fun intentToDetail(productDetailModel: Product.ProductDetail) {
        val intent = Intent(this.context, ProductDetailActivity::class.java)
        intent.putExtra(
            "product",
            gson.toJson(productDetailModel, Product.ProductDetail::class.java)
        )
        startActivity(intent)
    }

    private fun addDummyData() {
        dummyKategori.add(Home.CircleIcon("Kasur", R.drawable.ic_kasur, 0))
        dummyKategori.add(Home.CircleIcon("Meja", R.drawable.ic_table, 0))
        dummyKategori.add(Home.CircleIcon("Kursi", R.drawable.ic_kursi, 0))
        dummyKategori.add(Home.CircleIcon("Lemari", R.drawable.ic_lemari, 0))
        dummyKategori.add(Home.CircleIcon("Hiasan", R.drawable.ic_hiasan, 0))

        dummyToko.add(Home.CircleIcon("Mebel Kreasi", R.drawable.logo_mebel, 1))
        dummyToko.add(Home.CircleIcon("Kusen Jaya", null, 1))
        dummyToko.add(Home.CircleIcon("Kayuku", null, 1))

//        dummyProduk.add((Product.ProductDetail("R.drawable.image_example_kursi", "Lemari", 100000, false)))
//        dummyProduk.add((Product.ProductDetail("R.drawable.image_example_lampu", "Kursi", 100000, false)))
//        dummyProduk.add((Product.ProductDetail("R.drawable.image_example_lemari", "Meja", 100000, false)))
//        dummyProduk.add((Product.ProductDetail("R.drawable.image_example_meja", "Meja", 100000, false)))

        dummyImageSlider.add(Home.Advertisement(R.drawable.image_slider_example_1))
        dummyImageSlider.add(Home.Advertisement(R.drawable.image_slider_example_2))
    }

    private fun fetchProductData() {
        homeViewModel.getProducts()
        homeViewModel.productResult.observe(viewLifecycleOwner, Observer { task ->
            when (task) {
                is Resource.Loading -> {
                }

                is Resource.Success -> {
                    adapterProduk?.setProductData(task.data!!)
                    adapterProduk?.notifyDataSetChanged()
                }

                is Resource.Failure -> {

                }
            }
        })
    }

    private fun setupViewBinding(view: View) {
        toolbar = view.findViewById(R.id.toolbar)
        recyclerViewKategori = view.findViewById(R.id.recycler_kategori)
        recyclerViewToko = view.findViewById(R.id.recycler_toko)
        recyclerViewProduk = view.findViewById(R.id.recycler_produk)
        imageSliderAds = view.findViewById(R.id.ads_banner_slider)
        searchInputLayout = view.findViewById(R.id.search_product_text_input_home)
        searchInputLayout.clearFocus()
        searchInputText = view.findViewById(R.id.iet_search_product_home)
        searchInputText.clearFocus()

        toChatButton = view.findViewById(R.id.icon_chat_home)
        toChatButton.setOnClickListener {
            intentToDiscussion()
        }

    }

    private fun intentToDiscussion() {
        val intent = Intent(this.context, DiscussionActivity::class.java)
        startActivity(intent)
    }
}