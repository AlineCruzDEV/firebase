package com.digitalhouse.firebaseauth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.digitalhouse.firebaseauth.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getValuesPutExtra()
        binding.btnLogout.setOnClickListener{
            logout()
        }

    }

    fun getValuesPutExtra(){
        val extra = intent.extras
        if(extra != null) {
            val key = extra.getString("key")
            val email = extra.getString("email")
            Toast.makeText(this, "chave: $key email: $email", Toast.LENGTH_SHORT).show()
        }

    }
    private fun logout() {
        FirebaseAuth.getInstance().signOut()
        this.callLogin()
    }

    private fun callLogin() {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
    }

}