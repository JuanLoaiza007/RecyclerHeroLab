package com.ihuntgore.recyclerherolab.view.fragments

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import com.ihuntgore.recyclerherolab.R
import com.ihuntgore.recyclerherolab.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding

    // Guarda el resultado de una solicitud de permiso, por defecto
    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                openCamera()
            } else {
                // Si el permiso fue denegado y se puede pedir racionalmente entonces solo avisar
                if (shouldShowRequestPermissionRationale(android.Manifest.permission.CAMERA)) {
                    Toast.makeText(
                        context,
                        "Necesita habilidar los permisos de camara",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                // Si el permiso fue denegado y no se puede pedir racionalmente entonces sugerir activarlo manualmente
                else {
                    cameraPermitRequestOnSettings()
                }
            }
        }


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
        binding = FragmentHomeBinding.inflate(inflater)
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navigateToRecycler()
        addListenerCameraPermissions()
    }

    private fun navigateToRecycler() {
        binding.buttonFragmentRecycler.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_recyclerFragment)
        }
    }

    private fun addListenerCameraPermissions() {
        binding.buttonCamera.setOnClickListener {
            cameraPermitRequest()
        }
    }

    private fun openCamera() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(intent, 0)
    }

    private fun cameraPermitRequestOnSettings(){
        AlertDialog.Builder(requireContext())
            .setTitle("Permisos de Cámara Denegados")
            .setMessage("El permiso de la cámara ha sido denegado. ¿Desea habilitarlo manualmente desde la configuración?")

            // Si el usuario nos dice que esta dispuesto, lo redirigiremos a la configuracion de Android
            .setPositiveButton("Ir a la configuración") { _, _ ->
                val intent = Intent(android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS) // señal para abrir en Android la configuración de la app
                intent.data = Uri.parse("package:${requireContext().packageName}") // El nombre del paquete que usará la señal
                startActivity(intent) // Invocar la señal ya que está configurada
            }

            // Si el usuario no esta dispuesto pues ni modo
            .setNegativeButton("Cancelar") { dialog, _ -> dialog.dismiss() }.show()
    }

    private fun cameraPermitRequest() {
        // Para versiones de Android anteriores a 6.0 Marshmallow (API 23) no es necesario pedir este permiso
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            openCamera() // Abre la camara y ya está
        }
        // Para versiones de Android 6.0 Marshmallow (API 23) en adelante se debe solicitar el permiso
        else {
            when {

                // Condición si ya se tiene el permiso
                // checkSelfPermission(): Este método de la clase ContextCompat se usa para comprobar si se ha concedido un permiso específico a la aplicación.
                // PackageManager.PERMISSION_GRANTED/PERMISSION_DENIED son valores para verificar si el permiso es concedido o no
                ContextCompat.checkSelfPermission(requireContext(), android.Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED -> {
                    openCamera()
                }

                // Condición si el permiso ha sido denegado previamente para volverlo a pedir explicando su importancia
                // A esto se le llama hacer la solicitud racionalmente, algunos fabricantes y versiones de android solo permiten hacerla una vez
                shouldShowRequestPermissionRationale(android.Manifest.permission.CAMERA) -> {

                    // Manualmente usaremos un AlertDialog antes de decirle al sistema que pida el permiso
                    AlertDialog.Builder(requireContext())
                        .setTitle("Permisos de Cámara")
                        .setMessage("Los permisos de la camara son necesarios para que la aplicación funcione, esta dispuesto a acetarlos?")

                        // Si el usuario nos dice que esta dispuesto, invocamos la funcion del sistema
                        .setPositiveButton("Vamos!") { _, _ ->
                            requestPermissionLauncher.launch(android.Manifest.permission.CAMERA)
                        }

                        // Si el usuario no esta dispuesto entonces no hacemos nada
                        .setNegativeButton("Mejor no") { _, _ -> }.show()
                }

                // Condicion por defecto, pedir los permisos sin explicar pa que o que.
                // Se ejecuta cuando la aplicación pide los permisos por primera vez porque no hay referencias de
                // si el usuario lo ha aceptado o denegado.
                else -> requestPermissionLauncher.launch(android.Manifest.permission.CAMERA)
            }
        }
    }
}