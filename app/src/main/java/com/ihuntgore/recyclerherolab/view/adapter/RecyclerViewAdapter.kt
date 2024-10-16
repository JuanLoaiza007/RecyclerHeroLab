package com.ihuntgore.recyclerherolab.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ihuntgore.recyclerherolab.databinding.ItemSuperheroBinding
import com.ihuntgore.recyclerherolab.model.SuperHero
import com.ihuntgore.recyclerherolab.view.viewholder.RecyclerViewHolder

// Este es un adaptador personalizado para el RecyclerView, toma una lista mutable de héroes (SuperHero).
// El adaptador se encarga de vincular los datos (los héroes) con las vistas (cada ítem en la lista) que se mostrarán en pantalla.
class RecyclerViewAdapter(private val heroesList: MutableList<SuperHero>) :
    RecyclerView.Adapter<RecyclerViewHolder>() {

    // Método que se llama cuando el RecyclerView necesita crear un nuevo ViewHolder (cuando no hay suficientes "cajas" para reciclar).
    // Este método crea la vista para un ítem de la lista usando el diseño definido (item_superhero.xml).
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolder {

        // Inflamos el layout de un ítem de la lista usando el binding.
        // El Item empieza como una entidad independiente pero incomunicada, se infla para que
        // pueda adaptarse a su entorno y colaborar con otros elementos de la app.
        val binding = ItemSuperheroBinding.inflate(
            LayoutInflater.from(parent.context), // Obtiene el contexto del padre (RecyclerView) para imitar sus caracteristicas y entorno, así encaja en la app.
            parent, // El ViewGroup al que pertenece esta vista (el recyclerView), esto para indicar quien es el dueño de esta vista
            false // No queremos que el ítem se adjunte de inmediato al RecyclerView, le cedemos la responsabilidad al Recycler de invocar cuando lo desea adjuntar.
        )

        return RecyclerViewHolder(binding) // Retornamos el ViewHolder con los nuevos atributos
    }

    // Sobrescribe la funcion con la que el RecyclerView sabe cuantos items tiene.
    override fun getItemCount(): Int {
        return heroesList.size // El tamaño de la lista determina la cantidad de items.
    }

    // Sobre escribe la funcion que vincula la interfaz con la lógica mientras el usuario desplaza la lista.
    // Recibe un holder (que contendrá el item) y una posición para saber que elemento de la lista se cargará en el holder.
    override fun onBindViewHolder(holder: RecyclerViewHolder, position: Int) {
        val hero = heroesList[position] // Obtiene un heroe en la posicion actual
        holder.setHeroItem(hero) // Asigna el heroe al holder, la vista se actualizará para mostrar su informacion
    }
}