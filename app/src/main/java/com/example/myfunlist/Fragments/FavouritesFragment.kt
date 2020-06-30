package com.example.myfunlist.Fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myfunlist.Adapter.FavouritesAdapter
import com.example.myfunlist.Models.ListMovie
import com.example.myfunlist.R
import com.example.myfunlist.ViewModel.FavouritesViewmodel

class FavouritesFragment: Fragment() {
    private lateinit var favouritesViewmodel: FavouritesViewmodel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreate(savedInstanceState)

        // Create view
        val view = inflater.inflate(R.layout.fragment_favourites, container, false)

        favouritesViewmodel = ViewModelProviders.of(this).get(FavouritesViewmodel::class.java)
        favouritesViewmodel.view = view

        val moviesRecyclerView: RecyclerView = view.findViewById(R.id.favourites_list)

        val llm = LinearLayoutManager(context)
        llm.orientation = LinearLayoutManager.VERTICAL
        moviesRecyclerView.layoutManager = llm

        favouritesViewmodel.favourites.observe(this, Observer{ moviesResponse ->
            if(moviesResponse !== null) {
                moviesRecyclerView.adapter = FavouritesAdapter((favouritesViewmodel.favourites.value) as ArrayList<ListMovie>, context!!, favouritesViewmodel)
            } else {
                println("null")
            }
        })

        favouritesViewmodel.fetchFavourites()

        return view
    }


}