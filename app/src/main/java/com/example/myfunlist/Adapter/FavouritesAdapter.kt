package com.example.myfunlist.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myfunlist.Models.ListMovie
import com.example.myfunlist.R
import com.example.myfunlist.ViewModel.FavouritesViewmodel
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.movies_list_item.view.*

class FavouritesAdapter(val items: ArrayList<ListMovie>, val context: Context, val favouritesViewmodel: FavouritesViewmodel): RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val moviesViewHolder = holder as ViewHolder
        moviesViewHolder.name?.text = items.get(position).title

        Picasso.get()
            .load(items.get(position).image)
            .resize(290, 400)
            .into(moviesViewHolder.image)

        moviesViewHolder.btn.text = "Remove from favourites"
        moviesViewHolder.btn.setOnClickListener {
            favouritesViewmodel.remove(items.get(position))
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.movies_list_item,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return items.size
    }

    class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val name = view.movies_title
        val image = view.movies_image
        val btn = view.favourite_btn
    }
}