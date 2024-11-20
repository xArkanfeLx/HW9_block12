package com.example.customlistview

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.net.toUri
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class activity_preview_product : AppCompatActivity() {

    var photoUri: Uri? = null
    val GALLERY_REQUEST: Int = 302
    var products:MutableList<Product> = mutableListOf()
    var indexProd:Int?=null

    private lateinit var toolbarTB: Toolbar
    private lateinit var imgIV: ImageView
    private lateinit var nameET: EditText
    private lateinit var priceET: EditText
    private lateinit var infoET: EditText
    private lateinit var changeBTN: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_preview_product)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        init()

        imgIV.setImageURI(products[indexProd!!].img!!.toUri())
        nameET.setText(products[indexProd!!].name)
        priceET.setText(products[indexProd!!].price)
        infoET.setText(products[indexProd!!].info)
    }

    private fun init() {
        products = intent.extras?.getSerializable("list") as MutableList<Product>
        indexProd =  intent.extras?.getInt("indexProduct")

        toolbarTB = findViewById(R.id.toolbarTB)
        setSupportActionBar(toolbarTB)

        imgIV = findViewById(R.id.imgIV)
        nameET = findViewById(R.id.nameTV)
        priceET = findViewById(R.id.priceTV)
        infoET = findViewById(R.id.infoTV)
        changeBTN = findViewById(R.id.changeBTN)

        imgIV.setOnClickListener{
            val photoPicketIntent = Intent(Intent.ACTION_PICK)
            photoPicketIntent.type = "image/*"
            startActivityForResult(photoPicketIntent,GALLERY_REQUEST)
        }

        changeBTN.setOnClickListener{
            val name = nameET.text.toString()
            val price = priceET.text.toString()
            var img = products[indexProd!!].img
            if(photoUri!=null) img=photoUri.toString()
            val info = infoET.text.toString()
            val newProduct = Product(name,price,img,info)

            intent = Intent(this, CreateProduct_Activity::class.java)
            intent.putExtra(Product::class.java.simpleName, newProduct)
            intent.putExtra("newCheck", true)
            products[indexProd!!] = newProduct
            intent.putExtra("list", products as ArrayList<Product>)
            startActivity(intent)
            finish()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        imgIV = findViewById(R.id.imgIV)
        when(requestCode) {
            GALLERY_REQUEST -> if(resultCode === RESULT_OK){
                photoUri = data?.data
                val toast = Toast.makeText(this,photoUri.toString(),Toast.LENGTH_LONG).show()
                imgIV.setImageURI(photoUri)
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main,menu)
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        finish()
        return super.onOptionsItemSelected(item)
    }
}