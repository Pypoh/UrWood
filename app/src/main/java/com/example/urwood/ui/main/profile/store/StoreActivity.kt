package com.example.urwood.ui.main.profile.store

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.urwood.R
import com.example.urwood.databinding.ActivityStoreBinding
import com.example.urwood.repository.datasource.firestore.product.api.ProductRepoImpl
import com.example.urwood.repository.datasource.firestore.store.api.StoreRepoImpl
import com.example.urwood.repository.model.Product
import com.example.urwood.repository.model.User
import com.example.urwood.ui.main.home.adapter.ProdukAdapter
import com.example.urwood.ui.main.home.adapter.ProdukItemDecoration
import com.example.urwood.ui.main.profile.store.domain.StoreImpl
import com.example.urwood.ui.main.profile.utils.ProfileEditActivity
import com.example.urwood.ui.product.detail.ProductDetailActivity
import com.example.urwood.ui.product.domain.ProductImpl
import com.example.urwood.utils.viewobject.Resource
import com.google.android.material.textview.MaterialTextView
import com.google.gson.Gson
import de.hdodenhof.circleimageview.CircleImageView

class StoreActivity : FragmentActivity() {

    private lateinit var storeDataBinding: ActivityStoreBinding

    private val storeViewModel: StoreViewModel by lazy {
        ViewModelProvider(
            this,
            StoreVMFactory(StoreImpl(StoreRepoImpl()), ProductImpl(ProductRepoImpl()))
        ).get(StoreViewModel::class.java)
    }

    private val gson = Gson()

    private lateinit var editImage: ImageView
    private lateinit var profileImageStore: CircleImageView
    private lateinit var textNameStore: MaterialTextView
    private lateinit var textDescStore: MaterialTextView

    private lateinit var phoneButton: ImageView
    private lateinit var chatButton: ImageView
    private lateinit var phoneText: MaterialTextView
    private lateinit var chatText: MaterialTextView

    private lateinit var productRecycler: RecyclerView
    private lateinit var productAdapter: ProdukAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        storeDataBinding = DataBindingUtil.setContentView(this, R.layout.activity_store)
        storeDataBinding.storeViewModel = storeViewModel
        setContentView(storeDataBinding.root)

        setupViews(storeDataBinding.root)

        val storeId = intent.getStringExtra("storeId")

        fetchData(storeId)


    }

    private fun fetchData(storeId: String?) {
        Log.d("StoreDebug", "Store ID : $storeId")
        storeId?.let { storeViewModel.getStoreData(it) }
        storeViewModel.storeResult.observe(this, { task ->
            when (task) {
                is Resource.Loading -> {
                }

                is Resource.Success -> {
                    Log.d("StoreDebug", task.data.toString())
                    fetchProductData(storeId, task.data)
                }

                is Resource.Failure -> {

                }
            }
        })

    }

    private fun fetchProductData(storeId: String?, data: User.Store) {
        storeViewModel.getStoreProducts(storeId!!)
        storeViewModel.storeProductsResult.observe(this, { task ->
            when (task) {
                is Resource.Loading -> {
                }

                is Resource.Success -> {
                    data.storeId = storeId
                    updateUI(task.data, data)
                }

                is Resource.Failure -> {
                }
            }
        })
    }

    private fun updateUI(productsData: List<Product.ProductDetail>?, storeData: User.Store) {
        if (!storeData.loggedInUserStore) {
            phoneButton.visibility = View.VISIBLE
            phoneText.visibility = View.VISIBLE
            chatButton.visibility = View.VISIBLE
            chatText.visibility = View.VISIBLE
        }

        textNameStore.text = storeData.storeName
        textDescStore.text = storeData.storeDescription

        if (productsData != null) {
            productAdapter.setProductData(productsData)
            productAdapter.notifyDataSetChanged()
        }

        editImage.setOnClickListener {
            intentToEdit(storeData)
        }
    }

    private fun setupViews(view: View) {
        editImage = view.findViewById(R.id.image_edit_store)
        profileImageStore = view.findViewById(R.id.profile_picture_store)
        textNameStore = view.findViewById(R.id.store_name_store)
        textDescStore = view.findViewById(R.id.store_desc_store)

        phoneButton = view.findViewById(R.id.image_phone_call_store)
        chatButton = view.findViewById(R.id.image_message_store)
        phoneText = view.findViewById(R.id.text_hubungi_store)
        chatText = view.findViewById(R.id.text_chat_store)

        productRecycler = view.findViewById(R.id.product_store_recycler)
        productRecycler.layoutManager = GridLayoutManager(this, 2)
        productRecycler.addItemDecoration(ProdukItemDecoration(16, -1))
        productAdapter = ProdukAdapter(this, ArrayList())
        productRecycler.adapter = productAdapter
        productAdapter.setOnItemClickListener(object : ProdukAdapter.OnItemClickListener {
            override fun onItemClick(productDetailModel: Product.ProductDetail) {
                // Intent to Detail Page
                intentToDetail(productDetailModel)
            }
        })

    }

    private fun intentToEdit(storeData: User.Store) {
        val intent = Intent(this, ProfileEditActivity::class.java)
        intent.putExtra("storeData", gson.toJson(storeData))
        startActivityForResult(intent, 101)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (resultCode) {
            Activity.RESULT_OK -> {
                val storeObject = data!!.getStringExtra("storeData")
                val storeData = gson.fromJson(storeObject, User.Store::class.java)

                updateUI(null, storeData)
            }
        }
    }

    private fun intentToDetail(productDetailModel: Product.ProductDetail) {
        val intent = Intent(this, ProductDetailActivity::class.java)
        intent.putExtra(
            "product",
            gson.toJson(productDetailModel, Product.ProductDetail::class.java)
        )
        startActivity(intent)
    }

}