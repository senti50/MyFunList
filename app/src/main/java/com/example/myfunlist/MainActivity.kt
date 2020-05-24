package com.example.myfunlist

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.MotionEvent
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import com.example.myfunlist.Fragments.BooksFragment
import com.example.myfunlist.Fragments.FavouritesFragment
import com.example.myfunlist.Fragments.GamesFragment
import com.example.myfunlist.Fragments.MoviesFragment
import com.example.myfunlist.LoginPanel.LoginActivity
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_forgot_password.*
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var currentUserID: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        auth=FirebaseAuth.getInstance()
        val bundle: Bundle? = intent.extras
        currentUserID = bundle?.getString("userID").toString()

//load first fragment by default
        loadFragment(MoviesFragment())


        bottomAppBar.setOnNavigationItemSelectedListener {
            menu: MenuItem ->
            when{
                menu.itemId==R.id.navigationMovie ->{
                    loadFragment(MoviesFragment())
                    return@setOnNavigationItemSelectedListener true
                }
                menu.itemId == R.id.favourites -> {
                    loadFragment(FavouritesFragment())
                    return@setOnNavigationItemSelectedListener true
                }
                menu.itemId==R.id.navigationBooks ->{
                    loadFragment(BooksFragment())
                    return@setOnNavigationItemSelectedListener true
                }
                menu.itemId==R.id.navigationGames ->{
                    loadFragment(GamesFragment())
                    return@setOnNavigationItemSelectedListener true
                }
                else ->{
                    return@setOnNavigationItemSelectedListener false
                }
            }
        }



    }
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        super.onCreateOptionsMenu(menu)
        menuInflater.inflate(R.menu.options_menu, menu)
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem):Boolean {
        when (item.itemId) {
            R.id.menu_settings -> {
                Toast.makeText(this, currentUserID, Toast.LENGTH_SHORT).show()

            }
            R.id.menu_logout -> {
                auth.signOut()
                val intent = Intent(
                    this,
                    LoginActivity::class.java
                )
                startActivity(intent)
                finish()

            }
        }
            return super.onOptionsItemSelected(item)


    }


    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        if (currentFocus != null) {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(currentFocus!!.windowToken, 0)
        }
        return super.dispatchTouchEvent(ev)
    }

    private  fun loadFragment(fragment: Fragment){
        supportFragmentManager.beginTransaction().also { fragmentTransaction ->
            fragmentTransaction.replace(R.id.fragmentContainer,fragment)
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.commit()
        }
    }
}




