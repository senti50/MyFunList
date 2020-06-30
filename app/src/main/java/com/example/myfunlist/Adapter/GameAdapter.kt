package com.example.myfunlist.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myfunlist.Models.Game
import com.example.myfunlist.R

class GameAdapter(private  val dataArray:ArrayList<Game>):RecyclerView.Adapter<GameAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val inflater:LayoutInflater= LayoutInflater.from(parent.context)
        val view:View=inflater.inflate(R.layout.row_game,parent,false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return dataArray.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.titleTextView.text=dataArray[holder.adapterPosition].title
        holder.typeTextView.text=dataArray[holder.adapterPosition].type
        holder.yearTextView.text=dataArray[holder.adapterPosition].year.toString()
    }
    inner  class MyViewHolder(view: View):RecyclerView.ViewHolder(view){
        val titleTextView=view.findViewById(R.id.title_game)as TextView
        val typeTextView=view.findViewById(R.id.type_game)as TextView
        val yearTextView=view.findViewById(R.id.year_game)as TextView
    }
}