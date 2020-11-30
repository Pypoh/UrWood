package com.example.urwood.ui.main

import android.content.Intent
import android.os.Bundle
import android.view.Window
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.urwood.R
import com.example.urwood.repository.model.Product
import com.example.urwood.ui.product.add_product.AddProductActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.firestore.FirebaseFirestore

class MainActivity : AppCompatActivity() {

    private lateinit var navAddButton: FloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.activity_main)
        val navView: BottomNavigationView = findViewById(R.id.nav_view)

//        // Dummy Testing
        navAddButton = findViewById(R.id.nav_button_add)
        navAddButton.setOnClickListener {
            intentToAddProduct()
        }
//        navAddButton.setOnClickListener {
//            val dummyItems = ArrayList<Product.ProductDetail>()
//            // Kursi
//            dummyItems.add(Product.ProductDetail(
//                "https://firebasestorage.googleapis.com/v0/b/urwood-38635.appspot.com/o/image_example_kursi.png?alt=media&token=f1e33d42-8340-4f57-80b6-cea5ffe77b5f",
//                "Kursi",
//                100000,
//                2,
//                "Kursi",
//                "Spesifikasi: ",
//                "toko1",
//                4,
//                23,
//                arrayListOf("Hitam", "Putih", "Coklat"),
//                null
//            ))
//
//            // Lampu
//            dummyItems.add(Product.ProductDetail(
//                "https://firebasestorage.googleapis.com/v0/b/urwood-38635.appspot.com/o/image_example_lampu.png?alt=media&token=867b072b-9d61-45cb-b665-d6e351fa680e",
//                "Lampu",
//                250000,
//                2,
//                "Lampu",
//                "Spesifikasi: ",
//                "toko1",
//                4,
//                23,
//                arrayListOf("Hitam", "Putih", "Coklat", "Hitam", "Putih", "Coklat"),
//                null
//            ))
//
//            // Lemari
//            dummyItems.add(Product.ProductDetail(
//                "https://firebasestorage.googleapis.com/v0/b/urwood-38635.appspot.com/o/image_example_lemari.png?alt=media&token=902158e1-74c3-4db7-8bab-dd1d4723ae2a",
//                "Lemari",
//                1250000,
//                2,
//                "Lemari",
//                "Spesifikasi: ",
//                "toko2",
//                4,
//                23,
//                arrayListOf("Hitam"),
//                null
//            ))
//
//            // Meja
//            dummyItems.add(Product.ProductDetail(
//                "https://firebasestorage.googleapis.com/v0/b/urwood-38635.appspot.com/o/image_example_meja.png?alt=media&token=6ad82ff2-22d0-418a-882d-c6c6ad097e8f",
//                "Meja",
//                80000,
//                2,
//                "Kursi",
//                "Spesifikasi: ",
//                "toko2",
//                4,
//                23,
//                ArrayList(),
//                "qDF416Nvn6OVXiGBBhgQPDLEkvu1",
//                null,
//                null
//            ))
//
//            val db = FirebaseFirestore.getInstance()
//            val productReference = db.collection("products")
//
//            for (item in dummyItems) {
//                productReference.document().set(item)
//            }
//
//        }

        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(setOf(
            R.id.navigation_home, R.id.navigation_promo, R.id.navigation_add, R.id.navigation_blog, R.id.navigation_profile
        ))
//        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }

    private fun intentToAddProduct() {
        val intent = Intent(this, AddProductActivity::class.java)
        startActivity(intent)
    }
}