package com.example.myfunlist.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myfunlist.Adapter.MoviesAdapter
import com.example.myfunlist.R
import com.example.myfunlist.ViewModel.MoviesViewmodel
import com.google.firebase.database.DatabaseReference

/**
 * A simple [Fragment] subclass.
 */
class MoviesFragment : Fragment() {
    private lateinit var moviesViewmodel: MoviesViewmodel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        // Create view
        val view = inflater.inflate(R.layout.fragment_movies, container, false)

        val btnSearch = view.findViewById<Button>(R.id.btn_search)
        val searchPhrase = view.findViewById<EditText>(R.id.search_phrase)
        val searchValidation = view.findViewById<TextView>(R.id.search_validation)

        // Recycler view setup
        val moviesRecyclerView: RecyclerView = view.findViewById(R.id.favourites_list)
        val llm = LinearLayoutManager(context)
        llm.orientation = LinearLayoutManager.VERTICAL
        moviesRecyclerView.layoutManager = llm

        moviesViewmodel = ViewModelProviders.of(this).get(MoviesViewmodel::class.java)
        moviesViewmodel.view = view
        moviesViewmodel.read()

        btnSearch.setOnClickListener {
            val searchPhrase: String = searchPhrase.text.toString()
            if (searchPhrase.length === 0) {
                searchValidation.text = "Search message cannot be empty."
                false
            } else {
                searchValidation.text = ""
                moviesViewmodel.search(searchPhrase)
            }
        }

        moviesViewmodel.movies.observe(this, Observer { moviesResponse ->
            if (moviesResponse !== null) {
                moviesRecyclerView.adapter =
                    MoviesAdapter(moviesViewmodel.movies.value!!.titles, context!!, moviesViewmodel)
            } else {
                println("null")
            }
        })
        return view
    }


}

