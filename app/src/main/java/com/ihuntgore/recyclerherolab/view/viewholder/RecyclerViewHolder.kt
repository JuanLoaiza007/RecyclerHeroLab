package com.ihuntgore.recyclerherolab.view.viewholder

import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.ihuntgore.recyclerherolab.model.SuperHero
import com.ihuntgore.recyclerherolab.databinding.ItemSuperheroBinding

// Clase que gestiona la vista de un ítem (superhéroe) en el RecyclerView
class RecyclerViewHolder(binding: ItemSuperheroBinding): RecyclerView.ViewHolder(binding.root) {
    val bindingItem = binding

    // Método para asignar la información del objeto SuperHeroe a las vistas correspondientes
    fun setHeroItem(superHero: SuperHero) {
        bindingItem.imageViewSuperHero.setImageResource(superHero.imageId)
        bindingItem.textViewSuperName.text = superHero.name
        bindingItem.textViewSuperPower.text = superHero.power

        bindingItem.cardViewSuperHeroes.setOnClickListener {
            Toast.makeText(it.context, superHero.name, Toast.LENGTH_SHORT).show()
        }
    }
}