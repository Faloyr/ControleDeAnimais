package br.com.cotemig.exercicio4

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CadastroActivity : AppCompatActivity() {
    var isEditing = false
    lateinit var etNome: EditText
    lateinit var etIdade: EditText
    lateinit var etRaca: EditText

    var animal: AnimalModel = AnimalModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cadastro)

        var id = intent.getIntExtra("id", 0)

        var tvTitulo = findViewById<TextView>(R.id.tvTitulo)

        if (id != 0) {
            isEditing = true
            get(id)
        } else {
            tvTitulo.text = "Novo Animal"
        }

        etNome = findViewById<EditText>(R.id.etNome)
        etRaca = findViewById<EditText>(R.id.etRaca)
        etIdade = findViewById<EditText>(R.id.etIdade)

        findViewById<Button>(R.id.btSalvar).setOnClickListener {
            if (isEditing) {
                edit(id)
            }
        }
        findViewById<Button>(R.id.btExcluir).setOnClickListener{
            if (isEditing){
                excluir(id)
            }
        }
        findViewById<Button>(R.id.btCadastro).setOnClickListener {
            if (isEditing){
                cadastrar()
            }
        }

    }

    fun get(id: Int) {
        var instace = RetrofitUtil.getInstance("https://672162e498bbb4d93ca84641.mockapi.io/")
        var endpoint = instace.create(IAnimalEndpoint::class.java)

        var contexto = this

        endpoint.getById(id).enqueue(object : Callback<AnimalModel> {
            override fun onResponse(
                call: Call<AnimalModel>,
                response: Response<AnimalModel>
            ) {
                if (response.isSuccessful) {
                    if (response.body() != null) {
                        animal = response?.body()!!

                        etNome.setText(animal.nome)
                        etRaca.setText(animal.raca)
                        etIdade.setText(animal.idade)
                    }
                }
            }

            override fun onFailure(call: Call<AnimalModel>, t: Throwable) {
                Toast.makeText(contexto, "Erro na chamada", Toast.LENGTH_SHORT).show()
            }

        })
    }

    fun edit(id: Int) {
        var instace = RetrofitUtil.getInstance("https://672162e498bbb4d93ca84641.mockapi.io/")
        var endpoint = instace.create(IAnimalEndpoint::class.java)

        var contexto = this

        animal.nome = etNome.text.toString()
        animal.raca = etRaca.text.toString()
        animal.idade = etIdade.text.toString()

        endpoint.put(id, animal).enqueue(object : Callback<AnimalModel> {
            override fun onResponse(
                call: Call<AnimalModel>,
                response: Response<AnimalModel>
            ) {
                if (response.isSuccessful) {
                    Toast.makeText(contexto, "Cadastro atualizado!", Toast.LENGTH_SHORT).show()
                    finish()
                }
            }

            override fun onFailure(call: Call<AnimalModel>, t: Throwable) {
                Toast.makeText(contexto, "Erro na chamada", Toast.LENGTH_SHORT).show()
            }

        })
    }

    fun excluir(id: Int){
        val instance = RetrofitUtil.getInstance(path = "https://672162e498bbb4d93ca84641.mockapi.io/")
        val endpoint = instance.create(IAnimalEndpoint::class.java)
        val contexto = this

        endpoint.delete(id).enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) {
                    Toast.makeText(contexto, "Animal excluído com sucesso!", Toast.LENGTH_SHORT).show()
                    finish() // Close the activity upon successful deletion
                } else {
                    Toast.makeText(contexto, "Falha ao excluir o animal.", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                Toast.makeText(contexto, "Erro na chamada", Toast.LENGTH_SHORT).show()
            }
        })
    }

    fun cadastrar(){
        val instance = RetrofitUtil.getInstance("https://672162e498bbb4d93ca84641.mockapi.io/")
        val endpoint = instance.create(IAnimalEndpoint::class.java)

        val newAnimal = AnimalModel().apply {
            nome = etNome.text.toString()
            raca = etRaca.text.toString()
            idade = etIdade.text.toString()
        }

        endpoint.post(newAnimal).enqueue(object : Callback<AnimalModel> {
            override fun onResponse(call: Call<AnimalModel>, response: Response<AnimalModel>) {
                if (response.isSuccessful) {
                    Toast.makeText(this@CadastroActivity, "Animal cadastrado com sucesso!", Toast.LENGTH_SHORT).show()
                    finish()
                }
            }

            override fun onFailure(call: Call<AnimalModel>, t: Throwable) {
                Toast.makeText(this@CadastroActivity, "Erro na chamada", Toast.LENGTH_SHORT).show()
            }
        })
    }
}