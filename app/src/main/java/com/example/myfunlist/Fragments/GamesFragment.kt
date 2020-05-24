package com.example.myfunlist.Fragments

import android.database.DatabaseErrorHandler
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.myfunlist.AddGame
import com.example.myfunlist.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.fragment_games.*
import kotlinx.android.synthetic.main.fragment_games.view.*
import java.lang.Error


class GamesFragment : Fragment() {
    private lateinit var currentUserID: String
    private lateinit var databaseReference: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        currentUserID = arguments!!.getString("currentUserID").toString()
        val fireBase = FirebaseDatabase.getInstance()
        databaseReference = fireBase.getReference("Users").child(currentUserID).child("Games")

        super.onCreate(savedInstanceState)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_games, container, false)

        Toast.makeText(activity!!, currentUserID, Toast.LENGTH_SHORT).show()

        return view


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        add_floatingActionButton.setOnClickListener {
            activity!!.getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragmentContainer, AddGame()).commit()
        }
    }

    companion object {
        fun newInstance(currentUserID: String): GamesFragment {

            val args = Bundle()
            args.putString("currentUserID", currentUserID)
            val fragment = GamesFragment()
            fragment.arguments = args
            return fragment
        }
    }
}
