package com.example.myfunlist.LoginPanel

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.DocumentsContract
import android.text.TextUtils
import android.view.MotionEvent
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import android.widget.Toast
import com.example.myfunlist.MainActivity
import com.example.myfunlist.R
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {
    private lateinit var auth:FirebaseAuth
    private lateinit var currentUserID: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        auth=FirebaseAuth.getInstance()


        //logowanie
        buttonLogin.setOnClickListener {
            val email:String=findViewById<TextView>(R.id.inputEmail).text.toString()
            val password:String=findViewById<TextView>(R.id.inputPassword).text.toString()
            if(TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
                Toast.makeText(this, "Please fill all the fields", Toast.LENGTH_LONG).show()
            } else{
        auth.signInWithEmailAndPassword(email,password).addOnCompleteListener(this,
            OnCompleteListener { task ->
                if (task.isSuccessful){

                    currentUserID = auth.currentUser!!.uid
                    Toast.makeText(this,"Successfully Logged In",Toast.LENGTH_LONG).show()
                    val intent = Intent(this, MainActivity::class.java)
                    intent.putExtra("userID", currentUserID)
                    startActivity(intent)
                    finish()
                }
                else{
                    Toast.makeText(this,"Login Failed",Toast.LENGTH_LONG).show()
                }
            })
        }
        }
        // tworzenie konta
        sign_in_button.setOnClickListener {
            val intent=Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }
        //resetowanie hasła
        reset_password.setOnClickListener {
            val intent=Intent(this,
                ForgotPasswordActivity::class.java)

            startActivity(intent)
        }


    }

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        if (currentFocus != null) {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(currentFocus!!.windowToken, 0)
        }
        return super.dispatchTouchEvent(ev)
    }
}
