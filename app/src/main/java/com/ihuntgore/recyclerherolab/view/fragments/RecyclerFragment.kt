package com.ihuntgore.recyclerherolab.view.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.ihuntgore.recyclerherolab.R
import com.ihuntgore.recyclerherolab.databinding.FragmentRecyclerBinding
import com.ihuntgore.recyclerherolab.model.SuperHero
import com.ihuntgore.recyclerherolab.view.adapter.RecyclerViewAdapter

class RecyclerFragment : Fragment() {

    private lateinit var binding: FragmentRecyclerBinding

    // Este método se llama cuando el Fragment debe crear su interfaz de usuario.
    // Aquí inflamos el archivo XML del Fragment en una vista que se pueda mostrar en pantalla.
    override fun onCreateView(
        inflater: LayoutInflater, // Un layout en xml
        container: ViewGroup?, // Objeto padre (opcional)
        savedInstanceState: Bundle? // Información guardada del estado anterior (puede estar vacío).
    ): View? {
        binding = FragmentRecyclerBinding.inflate(inflater) // Esto crea y enlaza las vistas con el código
        binding.lifecycleOwner = this // las vistas estarán conscientes del ciclo de vida de este Fragment.
        return binding.root // Retornamos la raiz del contenedor inflado
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recycler() // Que inicialice el recycler
    }

    fun recycler(){
        // Lista quemada (predefinida) de superheroes
        var heroesList = mutableListOf(
            SuperHero("SuperMan","Superman es conocido por su increíble fuerza física.", R.drawable.superman),
            SuperHero("Hulk", "Radica en su capacidad para transformarse de un físico débil en un gigante verde y musculoso", R.drawable.hulk),
            SuperHero("Batman", "El superhéroe Batman confía en su inteligencia, entrenamiento físico y habilidades técnicas.", R.drawable.batman),
            SuperHero("Capitan América","Un hombre que ha sido mejorado físicamente a través de un suero de súper soldado", R.drawable.capitan),
            SuperHero("Pantera Negra","Sentidos Agudizados, Agilidad y Velocidad Mejoradas, Maestría en Artes Marciales", R.drawable.pantera),
            SuperHero("Spider-Man", "Sentido arácnido, agilidad", R.drawable.spiderman)
        )

        val recycler = binding.recyclerview // Obtenemos el recyclerView del XML asociado al binding
        recycler.layoutManager = LinearLayoutManager(context) // Asignamos un layoutManager de tipo LinearLayout para organizar los elementos (por defecto en vertical)

        val adapter = RecyclerViewAdapter(heroesList) // Instanciamos un adaptador con la lista de heroes quemada
        recycler.adapter = adapter // Asociamos el adaptador al recyclerView

        adapter.notifyDataSetChanged() // Enviamos la señal de que los datos han sido modificados y es necesario actualizar la vista
    }
}