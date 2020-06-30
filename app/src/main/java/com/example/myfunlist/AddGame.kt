package com.example.myfunlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.myfunlist.Models.Game
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.dialog_add_game.*
import java.util.*

class AddGame:DialogFragment() {
    private lateinit var databaseReference: DatabaseReference
    private lateinit var auth: FirebaseAuth

    private lateinit var currentUserID: String

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root=inflater.inflate(R.layout.dialog_add_game, container, false)






        val fireBase=FirebaseDatabase.getInstance()
        auth = FirebaseAuth.getInstance()
        currentUserID = auth.currentUser!!.uid
        databaseReference=fireBase.getReference("Users").child(currentUserID).child("Games")

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        action_ok.setOnClickListener {
            val title=inputTitle.text.toString()
            val type=inputType.text.toString()
            val publisher=inputPublisher.text.toString()
            val year=inputYear.text.toString().toInt()

            val firebaseInput=Game(title,type,publisher,year)
            databaseReference.child("${Date().time}").setValue(firebaseInput)
            activity?.supportFragmentManager?.popBackStack()


        }

        action_cancel.setOnClickListener {
            activity?.supportFragmentManager?.popBackStack()
        }
    }

}