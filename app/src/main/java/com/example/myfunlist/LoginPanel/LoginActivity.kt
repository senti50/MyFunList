package com.example.myfunlist.LoginPanel

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.DocumentsContract
import android.text.TextUtils
import android.widget.TextView
import android.widget.Toast
import com.example.myfunlist.MainActivity
import com.example.myfunlist.R
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {
    private lateinit var auth:FirebaseAuth

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


                    Toast.makeText(this,"Successfully Logged In",Toast.LENGTH_LONG).show()
                    val intent = Intent(this, MainActivity::class.java)
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
}
