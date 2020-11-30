package com.example.urwood.ui.product.detail

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.TypedValue
import android.view.View
import android.widget.CompoundButton
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.urwood.R
import com.example.urwood.databinding.ActivityProductDetailBinding
import com.example.urwood.repository.datasource.firestore.chat.api.ChatRepoImpl
import com.example.urwood.repository.datasource.firestore.product.api.ProductRepoImpl
import com.example.urwood.repository.model.Chat
import com.example.urwood.repository.model.Product
import com.example.urwood.repository.model.User
import com.example.urwood.ui.chat.domain.ChatImpl
import com.example.urwood.ui.chat.messages.MessageActivity
import com.example.urwood.ui.main.profile.store.StoreActivity
import com.example.urwood.ui.product.detail.adapter.ProductDetailImageAdapter
import com.example.urwood.ui.product.detail.domain.ProductDetailImpl
import com.example.urwood.utils.helper.Currency
import com.example.urwood.utils.toast
import com.example.urwood.utils.viewobject.Resource
import com.google.android.material.button.MaterialButton
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.google.android.material.textview.MaterialTextView
import com.google.firebase.firestore.FieldValue
import com.google.gson.Gson
import com.smarteist.autoimageslider.SliderView
import java.lang.Exception

class ProductDetailActivity : AppCompatActivity() {

    private lateinit var productDetailDataBinding: ActivityProductDetailBinding

    private val productDetailViewModel: ProductDetailViewModel by lazy {
        ViewModelProvider(
            this,
            ProductDetailVMFactory(ProductDetailImpl(ProductRepoImpl()), ChatImpl(ChatRepoImpl()))
        ).get(ProductDetailViewModel::class.java)
    }

    private val gson = Gson()

    // Views
    private lateinit var callButton: MaterialButton
    private lateinit var chatButton: MaterialButton

    private lateinit var productImageSlider: SliderView
    private lateinit var productImageAdapter: ProductDetailImageAdapter

    private lateinit var productTitleText: MaterialTextView
    private lateinit var productPriceText: MaterialTextView
    private lateinit var productRateText: MaterialTextView
    private lateinit var productSoldText: MaterialTextView
    private lateinit var productFavoriteImage: ImageView

    private lateinit var productStar1Image: ImageView
    private lateinit var productStar2Image: ImageView
    private lateinit var productStar3Image: ImageView
    private lateinit var productStar4Image: ImageView
    private lateinit var productStar5Image: ImageView
    private lateinit var productStarList: ArrayList<ImageView>

    private lateinit var productDescriptionText: MaterialTextView

    private lateinit var variantChipGroup: ChipGroup

    private lateinit var storeLayout: ConstraintLayout
    private lateinit var storeNameText: MaterialTextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_detail)

        productDetailDataBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_product_detail)
        productDetailDataBinding.productDetailViewModel = productDetailViewModel

        setupViews(productDetailDataBinding.root)

        val objectString = intent.getStringExtra("product")
        val produkModel = gson.fromJson(objectString, Product.ProductDetail::class.java)

        produkModel.productId?.let { fetchProductDetailData(it) }


    }

    private fun setupViews(view: View) {
        callButton = view.findViewById(R.id.button_call_product_detail)
        chatButton = view.findViewById(R.id.button_chat_product_detail)

        productImageSlider = view.findViewById(R.id.image_slider_product_detail)

        productTitleText = view.findViewById(R.id.text_title_product_detail)
        productPriceText = view.findViewById(R.id.text_price_product_detail)
        productRateText = view.findViewById(R.id.text_rate_product_detail)
        productSoldText = view.findViewById(R.id.text_sold_product_detail)
//        productFavoriteImage = view.findViewById(R.id.)

        productStar1Image = view.findViewById(R.id.star_1_product_detail)
        productStar2Image = view.findViewById(R.id.star_2_product_detail)
        productStar3Image = view.findViewById(R.id.star_3_product_detail)
        productStar4Image = view.findViewById(R.id.star_4_product_detail)
        productStar5Image = view.findViewById(R.id.star_5_product_detail)

        productStarList = arrayListOf(
            productStar1Image,
            productStar2Image,
            productStar3Image,
            productStar4Image,
            productStar5Image
        )

        productDescriptionText = view.findViewById(R.id.text_description_product_detail)

        variantChipGroup = view.findViewById(R.id.chip_group_variant_product_detail)

        storeLayout = view.findViewById(R.id.layout_store_detail_product)
        storeNameText = view.findViewById(R.id.text_store_product_detail)
    }

    private fun initView(productDetail: Product.ProductDetail?, userData: User.Store) {
        // Check Data
        Log.d("ProductDetail", productDetail.toString())

        callButton.setOnClickListener {
            if (!userData.storePhoneNumber.isNullOrBlank()) {
                checkPermission(userData.storePhoneNumber!!)
//                intentToPhone(userData.storePhoneNumber!!)
            } else {
                toast("Nomor telp tidak ditemukan")
            }
        }

        // TODO: Chat Diskusi

        if (productDetail != null) {
            try {
                productImageAdapter =
                    ProductDetailImageAdapter(this, arrayListOf(productDetail.image!!))
                productImageSlider.setSliderAdapter(productImageAdapter)
            } catch (e: Exception) {
                Log.d("ProductDetail", e.message!!)
            }

            productTitleText.text = productDetail.name
            productDescriptionText.text = productDetail.description
            productPriceText.text = Currency.intToRupiah(productDetail.price!!)
            productRateText.text = productDetail.rating.toString()
            productSoldText.text = "${productDetail.sold} Terjual"

            // Set Stars
            if (productDetail.rating != null) {
                for (item in 0 until productDetail.rating!!) {
                    productStarList[item].setImageResource(R.drawable.ic_star_fill)
                }
            }

            // Set Chips
            try {
                for (variant in productDetail.variant) {
                    val variantChip =
                        this.layoutInflater.inflate(R.layout.item_chip, null, false) as Chip
                    variantChip.text = variant
                    val paddingDp = TypedValue.applyDimension(
                        TypedValue.COMPLEX_UNIT_DIP, 8F,
                        resources.displayMetrics
                    ).toInt()
                    variantChip.setPadding(paddingDp, 0, paddingDp, 0)
                    variantChip.setOnCheckedChangeListener(object :
                        CompoundButton.OnCheckedChangeListener {
                        override fun onCheckedChanged(
                            buttonView: CompoundButton?,
                            isChecked: Boolean
                        ) {
                            toast("Not Implemented Yet.")
                        }
                    })
                    variantChipGroup.addView(variantChip)
                }
            } catch (e: Exception) {
                Log.d("ProductDetail", e.message!!)
            }

            storeNameText.text = userData.storeName
            storeLayout.setOnClickListener {
                intentToStore(userData.storeId)
            }

            chatButton.setOnClickListener {
                chatButton.isEnabled = false
//                productDetailViewModel.sendMessage(null, productDetail.ownerId!!)
                productDetailViewModel.createGroup(productDetail.ownerId!!)
                productDetailViewModel.chatGroupResult.observe(this, { task ->
                    when (task) {
                        is Resource.Loading -> {
                            Log.d("ChatDebug", "Loading")
                        }

                        is Resource.Success -> {
                            Log.d("ChatDebug", "Success")
                            intentToMessage(task.data)
                        }

                        is Resource.Failure -> {
                            chatButton.isEnabled = true
                            Log.d("ChatDebug", task.throwable.message.toString())
                        }
                    }
                })
            }
        }
    }

    private fun intentToMessage(data: String) {
        val intent = Intent(this, MessageActivity::class.java)
        intent.putExtra("groupId", data)
        startActivity(intent)
//        finish()
    }

    private fun intentToStore(storeId: String?) {
        val intent = Intent(this, StoreActivity::class.java)
        intent.putExtra("storeId", storeId)
        startActivity(intent)
    }

    private fun intentToPhone(phoneNumber: String) {
        val intent = Intent(Intent.ACTION_CALL, Uri.parse("tel:$phoneNumber"))
        startActivity(intent)
    }

    private fun fetchProductDetailData(productId: String) {
        productDetailViewModel.getProductDetailData(productId)
        productDetailViewModel.productResult.observe(this, { task ->
            when (task) {
                is Resource.Loading -> {
                }

                is Resource.Success -> {
                    fetchStoreData(task.data!!.ownerId, task.data.storeId, task.data)
                }

                is Resource.Failure -> {
                }
            }
        })
    }

    private fun fetchStoreData(
        ownerId: String?,
        storeId: String?,
        productData: Product.ProductDetail
    ) {
        if (ownerId != null && storeId != null) {
            productDetailViewModel.getStoreData(storeId, ownerId)
            productDetailViewModel.storeResult.observe(this, { task ->
                when (task) {
                    is Resource.Loading -> {
                    }

                    is Resource.Success -> {
                        task.data?.let { initView(productData, it) }
                    }

                    is Resource.Failure -> {
                    }
                }
            })
        }


    }

    private fun checkPermission(phoneNumber: String) {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.CALL_PHONE)
            != PackageManager.PERMISSION_GRANTED) {

            // Permission is not granted
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.CALL_PHONE)) {
                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(this,
                    arrayOf(Manifest.permission.CALL_PHONE),
                    42)
            }
        } else {
            // Permission has already been granted
            intentToPhone(phoneNumber)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 42) {
            // If request is cancelled, the result arrays are empty.
            if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                // permission was granted, yay!
            } else {
                // permission denied, boo! Disable the
                // functionality
            }
            return
        }
    }
}