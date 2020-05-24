package com.example.myfunlist.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myfunlist.*
import com.example.myfunlist.Adapter.BookAdapter
import com.example.myfunlist.Models.Book
import com.example.myfunlist.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.fragment_books.*

/**
 * A simple [Fragment] subclass.
 */
class BooksFragment : Fragment() {
    private lateinit var currentUserID: String
    private lateinit var databaseReference: DatabaseReference
    private lateinit var auth: FirebaseAuth
    private lateinit var listofBooks: ArrayList<Book>
    private lateinit var recyclerView: RecyclerView
    private lateinit var bookMenager: RecyclerView.LayoutManager
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_books, container, false)
        bookMenager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        val fireBase = FirebaseDatabase.getInstance()
        auth = FirebaseAuth.getInstance()
        currentUserID = auth.currentUser!!.uid
        databaseReference = fireBase.getReference("Users").child(currentUserID).child("Books")
        recyclerView = view.findViewById(R.id.recycle_View_Book)

        recyclerView.layoutManager = bookMenager

        loadBook()


        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        add_floatingActionButtonBooks.setOnClickListener {
            activity!!.getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragmentContainer, AddBook()).addToBackStack(null).commit()
        }
    }

    fun loadBook() {
        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                TODO("Not yet implemented")
            }

            override fun onDataChange(p0: DataSnapshot) {
                listofBooks = ArrayList()
                if (p0.exists()) {
                    for (h in p0.children) {
                        val book = h.getValue(Book::class.java)
                        listofBooks.add(book!!)
                    }
                    val readBookAdapter =
                        BookAdapter(listofBooks)

                    recyclerView.adapter = readBookAdapter

                }
            }
        })

    }
}
