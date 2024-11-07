package com.example.customlistview

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.net.toUri
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class activity_preview_product : AppCompatActivity() {

    var product:Product? = null

    private lateinit var toolbarTB: Toolbar
    private lateinit var imgIV: ImageView
    private lateinit var nameTV: TextView
    private lateinit var priceTV: TextView
    private lateinit var infoTV: TextView

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

        imgIV.setImageURI(product!!.img!!.toUri())
        nameTV.text = product!!.name
        priceTV.text = product!!.price
        infoTV.text = product!!.info
    }

    private fun init() {
        product = intent.extras?.getSerializable(Product::class.java.simpleName) as Product?

        toolbarTB = findViewById(R.id.toolbarTB)
        setSupportActionBar(toolbarTB)

        imgIV = findViewById(R.id.imgIV)
        nameTV = findViewById(R.id.nameTV)
        priceTV = findViewById(R.id.priceTV)
        infoTV = findViewById(R.id.infoTV)
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