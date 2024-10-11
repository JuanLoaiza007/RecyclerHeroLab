package com.ihuntgore.recyclerherolab.view.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ihuntgore.recyclerherolab.databinding.FragmentRecyclerBinding

class RecyclerFragment : Fragment() {

    private lateinit var binding: FragmentRecyclerBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentRecyclerBinding.inflate(inflater)
        binding.lifecycleOwner = this
        return binding.root
    }
}