package com.example.myfunlist.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.myfunlist.R
import kotlinx.android.synthetic.main.fragment_games.*


class GamesFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        add_floatingActionButton.setOnClickListener {
        }
        return inflater.inflate(R.layout.fragment_games, container, false)


    }





}
