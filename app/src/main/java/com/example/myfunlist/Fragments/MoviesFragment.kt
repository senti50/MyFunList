package com.example.myfunlist.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.myfunlist.R

/**
 * A simple [Fragment] subclass.
 */
class MoviesFragment : Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        val currentUserID = arguments?.getString("currentUserID")
        super.onCreate(savedInstanceState)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_movies, container, false)
    }

    companion object {
        fun newInstance(currentUserID: String): MoviesFragment {

            val args = Bundle()
            args.putString("currentUserID", currentUserID)
            val fragment = MoviesFragment()
            fragment.arguments = args
            return fragment
        }
    }
}
