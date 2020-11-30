package com.example.urwood.ui.product.add_product

import android.app.Activity
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Point
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.AutoCompleteTextView
import android.widget.GridLayout
import android.widget.ImageView
import androidx.appcompat.app.AlertDialog
import androidx.core.view.children
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.urwood.R
import com.example.urwood.databinding.ActivityAddProductBinding
import com.example.urwood.repository.datasource.firestore.product.api.ProductRepoImpl
import com.example.urwood.repository.model.Image
import com.example.urwood.repository.model.Product
import com.example.urwood.ui.main.MainActivity
import com.example.urwood.ui.product.add_product.adapter.PhotoAddProductAdapter
import com.example.urwood.ui.product.detail.ProductDetailVMFactory
import com.example.urwood.ui.product.detail.ProductDetailViewModel
import com.example.urwood.ui.product.detail.domain.ProductDetailImpl
import com.example.urwood.ui.product.domain.ProductImpl
import com.example.urwood.utils.toast
import com.example.urwood.utils.viewobject.Resource
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText

class AddProductActivity : AppCompatActivity() {

    private lateinit var addProductDataBinding: ActivityAddProductBinding

    private val addProductViewModel: AddProductViewModel by lazy {
        ViewModelProvider(
            this,
            AddProductVMFactory(ProductImpl(ProductRepoImpl()))
        ).get(AddProductViewModel::class.java)
    }

    private lateinit var recyclerPhoto: RecyclerView
    private lateinit var adapterPhoto: PhotoAddProductAdapter
    private lateinit var categoryDropdown: AutoCompleteTextView
    private lateinit var titleProduct: TextInputEditText
    private lateinit var categoryProduct: AutoCompleteTextView
    private lateinit var variantProduct: AutoCompleteTextView
    private lateinit var descriptionProduct: TextInputEditText
    private lateinit var priceProduct: TextInputEditText
    private lateinit var saveButton: MaterialButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_product)

        addProductDataBinding = DataBindingUtil.setContentView(this, R.layout.activity_add_product)
        addProductDataBinding.addProductViewModel = addProductViewModel

        setupViews(addProductDataBinding.root)

        val linearLayoutManager = LinearLayoutManager(this, RecyclerView.HORIZONTAL, true)
        linearLayoutManager.stackFromEnd = true
        recyclerPhoto.layoutManager = linearLayoutManager

        adapterPhoto = PhotoAddProductAdapter(this, ArrayList())
        recyclerPhoto.adapter = adapterPhoto
        adapterPhoto.addImageItem(Image.ImagePhoto(null, 0))
        adapterPhoto.setOnItemClickListener(object : PhotoAddProductAdapter.OnItemClickListener {
            override fun onItemClick(photoDetailModel: Image.ImagePhoto) {
//                adapterPhoto.addImageItem(Image.ImagePhoto(null, 0))
                // Select Image
                selectImage()
            }
        })
    }

    private fun selectImage() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, 100)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            adapterPhoto.addImageItem(Image.ImagePhoto(data!!.data, 1))
        }
    }

    private fun setupViews(view: View) {
        recyclerPhoto = view.findViewById(R.id.recycler_photo_add_product)

//        categoryDropdown = view.findViewById(R.id.)
        titleProduct = view.findViewById(R.id.iet_title_add_product)
        categoryProduct = view.findViewById(R.id.iet_category_add_product)
        variantProduct = view.findViewById(R.id.variant_tag_auto_complete_add_product)
        descriptionProduct = view.findViewById(R.id.iet_description_add_product)
        priceProduct = view.findViewById(R.id.iet_price_add_product)
        saveButton = view.findViewById(R.id.btn_save_add_product)

        saveButton.setOnClickListener {
            var productImageUri: Uri? = null
            if (adapterPhoto.itemCount > 1) {
                productImageUri = adapterPhoto.getItemUri(1)
            }
            val productData = Product.ProductDetail(
                productImageUri,
                titleProduct.text.toString(),
                categoryProduct.text.toString(),
                arrayListOf(variantProduct.text.toString()),
                descriptionProduct.text.toString(),
                priceProduct.text.toString().toInt()
            )
            postProduct(productData)
        }
    }

    private fun postProduct(productData: Product.ProductDetail) {
        addProductViewModel.postProduct(productData)
        addProductViewModel.postResult.observe(this, { task ->
            when (task) {
                is Resource.Loading -> {
                }

                is Resource.Success -> {
                    toast("Uploaded")
                    intentToMain()
                }

                is Resource.Failure -> {
                    Log.d("UploadDebug", task.throwable.message.toString())
                }

                else -> {
                    // do nothing
                }
            }
        })
    }

    private fun intentToMain() {
        val intent = Intent(this, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finish()
    }
}