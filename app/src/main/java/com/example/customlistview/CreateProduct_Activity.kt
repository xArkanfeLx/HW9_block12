package com.example.customlistview

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.Menu
import android.view.MenuItem
import android.widget.AdapterView
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.ListView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.io.IOException

class CreateProduct_Activity : AppCompatActivity() {

    var photoUri: Uri? = null
    val GALLERY_REQUEST: Int = 302
    var products:MutableList<Product> = mutableListOf()

    private lateinit var listAdapter : ListAdapter

    private lateinit var toolbarTB: Toolbar
    private lateinit var imgIV: ImageView
    private lateinit var nameET: EditText
    private lateinit var priceET: EditText
    private lateinit var infoET: EditText
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
            val productImg = photoUri.toString()
            val info = infoET.text.toString()
            val product = Product(productName,productPrice,productImg,info)
            products.add(product)
            newListAdapter()
            clearProductFields()
            photoUri=null
        }
    }

    fun newListAdapter(){
        listAdapter = ListAdapter(this@CreateProduct_Activity,products)
        productsLV.adapter = listAdapter
        listAdapter.notifyDataSetChanged()
    }

    private fun clearProductFields() {
        imgIV.setImageResource(R.drawable.product_default_icon)
        nameET.text.clear()
        priceET.text.clear()
        infoET.text.clear()
    }

    override fun onResume() {
        super.onResume()
        val check = intent.extras?.getBoolean(("newCheck")) ?: false
        if(check) {
            products = intent.getSerializableExtra("list") as MutableList<Product>
            newListAdapter()
        }
    }

    private fun init() {
        toolbarTB = findViewById(R.id.toolbarTB)
        setSupportActionBar(toolbarTB)

        imgIV = findViewById(R.id.imgIV)
        nameET = findViewById(R.id.nameET)
        priceET = findViewById(R.id.priceET)
        infoET = findViewById(R.id.infoET)
        addBTN = findViewById(R.id.addBTN)

        productsLV = findViewById(R.id.productsLV)

        productsLV.onItemClickListener = AdapterView.OnItemClickListener { adapterView, view, position, id ->
            val index = id.toInt()
            val product = products[index]
            intent = Intent(this, activity_preview_product::class.java)
            intent.putExtra("list", products as ArrayList<Product>)
            intent.putExtra("indexProduct", index)
            startActivity(intent)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        imgIV = findViewById(R.id.imgIV)
        when(requestCode) {
            GALLERY_REQUEST -> if(resultCode === RESULT_OK){
                photoUri = data?.data
                imgIV.setImageURI(photoUri)
            }
        }
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main,menu)
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        finishAffinity()
        val toast = Toast.makeText(applicationContext,"Закрыли приложение",Toast.LENGTH_SHORT).show()
        return super.onOptionsItemSelected(item)
    }
}