package com.example.customlistview

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView

class ListAdapter(context:Context,productList:MutableList<Product>) :
    ArrayAdapter<Product>(context,R.layout.product_list_item,productList){

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var view = convertView
        val product = getItem(position)
        if(view==null) view = LayoutInflater.from(context).inflate(R.layout.product_list_item,parent,false)

        val imgIV = view?.findViewById<ImageView>(R.id.imgIV)
        val nameTV = view?.findViewById<TextView>(R.id.nameTV)
        val priceTV = view?.findViewById<TextView>(R.id.priceTV)

        imgIV?.setImageBitmap(product?.img)
        nameTV?.text = product?.name
        priceTV?.text = product?.price

        return view!!
    }
}