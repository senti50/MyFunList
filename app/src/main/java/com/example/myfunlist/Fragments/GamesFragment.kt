package com.example.myfunlist.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myfunlist.AddGame
import com.example.myfunlist.Adapter.GameAdapter
import com.example.myfunlist.Models.Game
import com.example.myfunlist.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.fragment_games.*


class GamesFragment : Fragment() {
    private lateinit var currentUserID: String
    private lateinit var databaseReference: DatabaseReference
    private lateinit var auth: FirebaseAuth
    private lateinit var listofGames: ArrayList<Game>
    private lateinit var recyclerView: RecyclerView
    private lateinit var gameMenager: RecyclerView.LayoutManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_games, container, false)
        gameMenager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        val fireBase = FirebaseDatabase.getInstance()
        auth = FirebaseAuth.getInstance()
        currentUserID = auth.currentUser!!.uid
        databaseReference = fireBase.getReference("Users").child(currentUserID).child("Games")
        recyclerView = view.findViewById(R.id.recycle_View_Games)

        recyclerView.layoutManager = gameMenager

        loadGame()
        //Toast.makeText(activity!!, currentUserID, Toast.LENGTH_SHORT).show()

        return view


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        add_floatingActionButton.setOnClickListener {
            activity!!.getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragmentContainer, AddGame()).addToBackStack(null).commit()
        }
    }


    fun loadGame() {
        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                TODO("Not yet implemented")
            }

            override fun onDataChange(p0: DataSnapshot) {
                listofGames = ArrayList()
                if (p0.exists()) {
                    for (h in p0.children) {
                        val game = h.getValue(Game::class.java)
                        listofGames.add(game!!)
                    }
                    val readBookAdapter =
                        GameAdapter(listofGames)

                    recyclerView.adapter = readBookAdapter

                }
            }
        })

    }
}
