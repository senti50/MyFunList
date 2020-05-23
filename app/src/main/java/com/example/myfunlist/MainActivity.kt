package com.example.myfunlist

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.myfunlist.Fragments.BooksFragment
import com.example.myfunlist.Fragments.GamesFragment
import com.example.myfunlist.Fragments.MoviesFragment
import com.example.myfunlist.LoginPanel.LoginActivity
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        auth=FirebaseAuth.getInstance()


//load first fragment by default
        loadFragment(MoviesFragment())


        bottomAppBar.setOnNavigationItemSelectedListener {
            menu: MenuItem ->
            when{
                menu.itemId==R.id.navigationMovie ->{
                    loadFragment(MoviesFragment())
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
                Toast.makeText(this,"settings",Toast.LENGTH_SHORT).show()

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




    private  fun loadFragment(fragment: Fragment){
        supportFragmentManager.beginTransaction().also { fragmentTransaction ->
            fragmentTransaction.replace(R.id.fragmentContainer,fragment)
            fragmentTransaction.commit()
        }
    }
}


