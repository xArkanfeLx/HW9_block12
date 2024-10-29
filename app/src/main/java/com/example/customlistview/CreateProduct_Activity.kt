package com.example.customlistview

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.ListView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.io.IOException

class CreateProduct_Activity : AppCompatActivity() {

    var bitmap: Bitmap? = null
    val GALLERY_REQUEST: Int = 302
    val products:MutableList<Product> = mutableListOf()

    private lateinit var toolbarTB: Toolbar
    private lateinit var imgIV: ImageView
    private lateinit var nameET: EditText
    private lateinit var priceET: EditText
    private lateinit var addBTN: Button
    private lateinit var productsLV: ListView

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_create_product)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        init()

        imgIV.setOnClickListener{
            val photoPicketIntent = Intent(Intent.ACTION_PICK)
            photoPicketIntent.type = "image/*"
            startActivityForResult(photoPicketIntent,GALLERY_REQUEST)
        }

        addBTN.setOnClickListener{
            val productName = nameET.text.toString()
            val productPrice = priceET.text.toString()
            val productImg = bitmap
            val product = Product(productName,productPrice,productImg)
            products.add(product)
            val listAdapter = ListAdapter(this@CreateProduct_Activity,products)
            productsLV.adapter = listAdapter
            listAdapter.notifyDataSetChanged()
            clearProductFields()
        }
    }

    private fun clearProductFields() {
        imgIV.setImageResource(R.drawable.product_default_icon)
        nameET.text.clear()
        priceET.text.clear()
    }

    private fun init() {
        toolbarTB = findViewById(R.id.toolbarTB)
        setSupportActionBar(toolbarTB)

        imgIV = findViewById(R.id.imgIV)
        nameET = findViewById(R.id.nameET)
        priceET = findViewById(R.id.priceET)
        addBTN = findViewById(R.id.addBTN)

        productsLV = findViewById(R.id.productsLV)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        imgIV = findViewById(R.id.imgIV)
        when(requestCode) {
            GALLERY_REQUEST -> if(resultCode === RESULT_OK){
                val selectedImage: Uri? = data?.data
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(contentResolver,selectedImage)
                } catch (e: IOException){
                    e.printStackTrace()
                }
                imgIV.setImageBitmap(bitmap)
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main,menu)
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        finish()
        System.exit(0);
        return super.onOptionsItemSelected(item)
    }
}