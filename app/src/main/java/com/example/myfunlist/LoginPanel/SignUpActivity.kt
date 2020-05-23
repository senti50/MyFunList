package com.example.myfunlist.LoginPanel

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.myfunlist.MainActivity
import com.example.myfunlist.R
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_sign_up.*

class SignUpActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var rootReference: DatabaseReference
    private lateinit var currentUserID: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        auth = FirebaseAuth.getInstance()
        rootReference= FirebaseDatabase.getInstance().reference
        activity_signup_btn.setOnClickListener {
            val email=findViewById<EditText>(R.id.singUpEmail).text.toString().trim()
            val password=findViewById<EditText>(R.id.singUpPassword).text.toString().trim()
            if(password.length<6){
                findViewById<TextView>(R.id.singUpPassword).setError("Password too short")
            }
            if(TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
                Toast.makeText(this, "Please fill all the fields", Toast.LENGTH_LONG).show()
            }else{
                auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(this,
                    OnCompleteListener { task ->
                        if(task.isSuccessful){
                            currentUserID = auth.currentUser!!.uid
                            rootReference.child("Users").child(currentUserID).setValue("")
                            Toast.makeText(this, "Successfully Registered", Toast.LENGTH_LONG).show()
                            val intent = Intent(this, MainActivity::class.java)
                            startActivity(intent)
                            finish()
                        }else {
                            Toast.makeText(this, "Registration Failed", Toast.LENGTH_LONG).show()
                        }
                    })
            }
        }
        login_btn.setOnClickListener {
            val intent=Intent(this,
                LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}
