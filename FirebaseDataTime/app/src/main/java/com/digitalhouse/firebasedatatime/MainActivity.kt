package com.digitalhouse.firebasedatatime

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.digitalhouse.firebasedatatime.databinding.ActivityMainBinding
import com.google.firebase.database.*
import com.google.gson.Gson


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var database: FirebaseDatabase
    private lateinit var reference: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        conectDB()
        showData()

        binding.btnCreate.setOnClickListener {
            var prod = getProduct("CAneta Azul")
            var res = sendProdBD("1", prod)
            Log.i("Main", res)

            var prod2 = getProduct("Carro Prata")
            var res2 = sendProdBD("2", prod2)
            Log.i("Main", res2)
        }

        binding.btnUpdate.setOnClickListener {
            var prod = getProduct("Carro Verde")
            var res = updateData("1", prod)
            Log.i("Main", res)
        }

        binding.btnDelete.setOnClickListener {
            deleteData("2")
        }
    }

    fun conectDB() {
        database = FirebaseDatabase.getInstance()
        reference = database.getReference("products")
    }

    fun getProduct(name: String): Prod {
        return Prod(name, 1, 2.0)
    }

    fun sendProdBD(id: String, prod: Prod): String {
        var res = reference
            .child(id)
            .setValue(prod)
        return res.toString()
    }

    private fun updateData(id: String, prod: Prod): String {
        var res = reference
            .child(id)
            .setValue(prod)
        return res.toString()
    }

    private fun deleteData(id: String): String {
        var res = reference
            .child(id)
            .removeValue()
        return res.toString()
    }

    private fun showData(){

        //var listProd = arrayListOf<Prod>()

        reference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                dataSnapshot.children.forEach{
                    //var prod = Gson().fromJson(it.value.toString(), Prod::class.java)
                    //listProd.add(prod)
                    Log.i("Prod", it.toString())
                }
            }

            override fun onCancelled(error: DatabaseError) {

                Log.w("Erro", error.toString())
            }
        })

    }
}