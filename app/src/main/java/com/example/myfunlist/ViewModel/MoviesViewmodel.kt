package com.example.myfunlist.ViewModel

import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myfunlist.Models.ListMovie
import com.example.myfunlist.Models.SearchResponse
import com.example.myfunlist.network.RapidApiService
import com.example.myfunlist.network.RapidClient
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

class MoviesViewmodel: ViewModel() {

    private var retrofit: Retrofit
    private var database: FirebaseFirestore
    private var favourites: MutableList<ListMovie> = mutableListOf()
    lateinit var view: View
    var movies: MutableLiveData<SearchResponse?> = MutableLiveData()

    init {
        retrofit = RapidClient.getInstace()
        database = FirebaseFirestore.getInstance()
    }

    fun insert(movie: ListMovie, btn: Button) {
        val userId = FirebaseAuth.getInstance().currentUser!!.uid
        val newMovie = hashMapOf(
            "title" to movie.title,
            "image" to movie.image,
            "movie_id" to movie.id,
            "user_id" to FirebaseAuth.getInstance().currentUser!!.uid,
            "movie_user_id" to "${movie.id}_${userId}"
        )

        database.collection("movies")
            .add(newMovie.toMap())
            .addOnSuccessListener {
                btn.text = "Remove from favourites"
                btn.setOnClickListener({
                    delete(movie, btn)
                })
                val toast = Toast.makeText(view.context, "Movie added to favourites", Toast.LENGTH_SHORT)
                toast.show()
            }
            .addOnFailureListener {
                val toast = Toast.makeText(view.context, "Failed to add movie to favourites", Toast.LENGTH_SHORT)
                toast.show()
            }
    }

    fun delete(movie: ListMovie, btn: Button) {
        val collection = FirebaseFirestore.getInstance().collection("movies")
        val userId = FirebaseAuth.getInstance().currentUser!!.uid
        val query = collection
            .whereEqualTo("movie_user_id", "${movie.id}_${userId}")
            .get()

        query.addOnSuccessListener {
            for(d in it.documents) {
                collection.document(d.id).delete()
            }
            btn.text = "Add to favourites"
            btn.setOnClickListener({
                insert(movie, btn)
            })
            val toast = Toast.makeText(view.context, "Movie was deleted from favourites", Toast.LENGTH_SHORT)
            toast.show()
        }
            .addOnFailureListener {
                val toast = Toast.makeText(view.context, "Failed to delete movie from favourites", Toast.LENGTH_SHORT)
                toast.show()
            }
    }

    fun isPresent(movie: ListMovie): Boolean {
        println(movie.id)
        for(favourite in favourites) {
            println(favourite.id)
            if(favourite.id.equals(movie.id)) {
                return true
            }
        }

        return false
    }

    fun read() {
        val collection = FirebaseFirestore.getInstance().collection("movies")

        collection.whereEqualTo("user_id", FirebaseAuth.getInstance().currentUser!!.uid)
        val items = collection.get()

        items.addOnSuccessListener {
            for(doc in it.documents) {
                val movie = ListMovie(
                    doc.data!!.get("title").toString(),
                    doc.data!!.get("image").toString(),
                    doc.data!!.get("movie_id").toString(),
                    doc.data!!.get("movie_user_id").toString()
                )

                favourites.add(movie)
            }
        }
            .addOnFailureListener {
                val toast = Toast.makeText(view.context, "Failed to fetch favourite movies", Toast.LENGTH_SHORT)
                toast.show()
            }
    }

    fun search(phrase: String) {

        val getDataService = RapidClient.getInstace().create(RapidApiService::class.java)
        val call = getDataService.search(phrase)

        call.enqueue(object: Callback<SearchResponse> {
            override fun onFailure(call: Call<SearchResponse>, t: Throwable) {
                val toast = Toast.makeText(view.context, "Failed to search for provided movie name", Toast.LENGTH_SHORT)
                toast.show()
            }

            override fun onResponse(call: Call<SearchResponse>, response: Response<SearchResponse>) {
                movies.postValue(response.body())
            }
        })
    }
}