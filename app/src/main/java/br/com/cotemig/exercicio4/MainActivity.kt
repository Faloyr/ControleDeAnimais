package br.com.cotemig.exercicio4

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import android.widget.Toast
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onResume() {
        super.onResume()
        //Implementar aqui o código para preencher a lista
        getAnimais()
    }

    fun getAnimais() {
        var instace = RetrofitUtil.getInstance("https://672162e498bbb4d93ca84641.mockapi.io/")
        var endpoint = instace.create(IAnimalEndpoint::class.java)

        var contexto = this

        endpoint.get().enqueue(object : Callback<ArrayList<AnimalModel>> {
            override fun onResponse(
                call: Call<ArrayList<AnimalModel>>,
                response: Response<ArrayList<AnimalModel>>
            ) {
                if (response.isSuccessful) {
                    if (response.body() != null) {
                        var retorno: ArrayList<AnimalModel> = response?.body()!!

                        var recyclerviw = findViewById<RecyclerView>(R.id.rvAnimais)
                        recyclerviw.adapter = AnimalAdapter(contexto, retorno)
                        recyclerviw.layoutManager = LinearLayoutManager(contexto)
                        recyclerviw.itemAnimator = DefaultItemAnimator()
                    }
                }
            }

            override fun onFailure(call: Call<ArrayList<AnimalModel>>, t: Throwable) {
                Toast.makeText(contexto, "Erro na chamada", Toast.LENGTH_SHORT).show()
            }

        })
    }
}