package com.example.myfunlist.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myfunlist.Models.Book
import com.example.myfunlist.R

class BookAdapter(private  val dataArray:ArrayList<Book>): RecyclerView.Adapter<BookAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val inflater: LayoutInflater = LayoutInflater.from(parent.context)
        val view: View =inflater.inflate(R.layout.row_book,parent,false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return dataArray.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.titleTextView.text=dataArray[holder.adapterPosition].title
        holder.authorTextView.text=dataArray[holder.adapterPosition].author
        holder.publisherTextView.text=dataArray[holder.adapterPosition].publisher
        holder.languageTextView.text=dataArray[holder.adapterPosition].language
    }
    inner  class MyViewHolder(view: View): RecyclerView.ViewHolder(view){
        val titleTextView=view.findViewById(R.id.title_book)as TextView
        val authorTextView=view.findViewById(R.id.author_book)as TextView
        val publisherTextView=view.findViewById(R.id.publisher_book)as TextView
        val languageTextView=view.findViewById(R.id.language_book)as TextView
    }
}