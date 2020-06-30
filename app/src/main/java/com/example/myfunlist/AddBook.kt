package com.example.myfunlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.myfunlist.Models.Book
import com.example.myfunlist.Models.Game
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.dialog_add_book.*
import kotlinx.android.synthetic.main.dialog_add_game.*
import java.util.*

class AddBook : DialogFragment() {
    private lateinit var databaseReference: DatabaseReference
    private lateinit var auth: FirebaseAuth

    private lateinit var currentUserID: String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root=inflater.inflate(R.layout.dialog_add_book, container, false)






        val fireBase= FirebaseDatabase.getInstance()
        auth = FirebaseAuth.getInstance()
        currentUserID = auth.currentUser!!.uid
        databaseReference=fireBase.getReference("Users").child(currentUserID).child("Books")

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        action_ok_book.setOnClickListener {
            val title=inputTitle_book.text.toString()
            val type=inputAuthor_book.text.toString()
            val publisher=inputPublisher_book.text.toString()
            val year=inputLanguage.text.toString()

            val firebaseInput= Book(title,type,publisher,year)
            databaseReference.child("${Date().time}").setValue(firebaseInput)


            activity?.supportFragmentManager?.popBackStack()


        }

        action_cancel_book.setOnClickListener {
            activity?.supportFragmentManager?.popBackStack()
        }
    }

}