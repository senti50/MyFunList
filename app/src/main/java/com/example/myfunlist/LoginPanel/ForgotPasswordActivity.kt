package com.example.myfunlist.LoginPanel

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.EditText
import android.widget.Toast
import com.example.myfunlist.R
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_forgot_password.*

class ForgotPasswordActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password)

        auth = FirebaseAuth.getInstance()
        val email:String=findViewById<EditText>(R.id.email_edt_text).text.toString()
        reset_pass_btn.setOnClickListener {
            if (TextUtils.isEmpty(email)){
                Toast.makeText(this,"Please enter email",Toast.LENGTH_LONG).show()
            }else{
                auth.sendPasswordResetEmail(email).addOnCompleteListener { task ->
                    if (task.isSuccessful){
                        Toast.makeText(this,"Reset link sent to your email",Toast.LENGTH_LONG).show()
                    }else{
                        Toast.makeText(this, "Unable to send reset mail", Toast.LENGTH_LONG).show()
                    }
                }
            }
        }
    back_btn.setOnClickListener {
        finish()
    }
    }
}
