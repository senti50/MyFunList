package com.example.myfunlist.ViewModel

import android.view.View
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myfunlist.Models.ListMovie
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class FavouritesViewmodel: ViewModel() {
    var favourites: MutableLiveData<ArrayList<ListMovie>> = MutableLiveData()
    lateinit var view: View
    var firestore = FirebaseFirestore.getInstance()

    fun fetchFavourites() {
        val collection = firestore.collection("movies")

        collection.whereEqualTo("user_id", FirebaseAuth.getInstance().currentUser!!.uid)
        val items = collection.get()

        items
            .addOnSuccessListener {
                val f = mutableListOf<ListMovie>()
                for(doc in it.documents) {
                    val movie = ListMovie(
                        doc.data!!.get("title").toString(),
                        doc.data!!.get("image").toString(),
                        doc.data!!.get("movie_id").toString(),
                        doc.data!!.get("movie_user_id").toString()
                    )

                    f.add(movie)
                }

                favourites.postValue(f as ArrayList<ListMovie>)
            }
            .addOnFailureListener {
                val toast = Toast.makeText(view.context, "Failed to fetch favourite movies", Toast.LENGTH_SHORT)
                toast.show()
            }
    }

    fun remove(movie: ListMovie) {
        val collection = firestore.collection("movies")
        val userId = FirebaseAuth.getInstance().currentUser!!.uid

        collection
            .whereEqualTo("movie_user_id", "${movie.id}_${userId}")

        val items = collection.get()

        items
            .addOnSuccessListener {
                println(it.count())
                for(doc in it.documents) {
                    collection.document(doc.id)
                        .delete()
                        .addOnSuccessListener {
                            val f = favourites.value
                            f!!.remove(movie)
                            favourites.postValue(f)
                            val toast = Toast.makeText(view.context, "Movie was deleted", Toast.LENGTH_SHORT)
                            toast.show()
                        }
                        .addOnFailureListener {
                            val toast = Toast.makeText(view.context, "Failed to delete movie", Toast.LENGTH_SHORT)
                            toast.show()
                        }
                }
            }
            .addOnFailureListener {
                val toast = Toast.makeText(view.context, "Failed to delete movie", Toast.LENGTH_SHORT)
                toast.show()
            }
    }
}