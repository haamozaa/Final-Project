package com.example.palliativecare.dialogs

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.animation.AnimationUtils
import androidx.fragment.app.DialogFragment
import com.example.palliativecare.R
import com.example.palliativecare.databinding.AlertDialogBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class AlertDialog(
    var title:String,
    var body:String
): DialogFragment(){
    lateinit var onDelete : ((Boolean) -> Unit)
    private lateinit var binding : AlertDialogBinding
    private lateinit var auth : FirebaseAuth
    @SuppressLint("UseGetLayoutInflater")
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        binding = AlertDialogBinding.inflate(LayoutInflater.from(context))
        val builder = AlertDialog.Builder(requireActivity())
        builder.setView(binding.root)
        binding.apply {
            titleD.text = title
            bodyD.text = body
        }

        binding.yes.setOnClickListener {
            onDelete.invoke(true)
            this.dismiss()
        }
        binding.no.setOnClickListener {
            onDelete.invoke(false)
            this.dismiss()
        }

        auth = Firebase.auth
        binding.card.startAnimation(AnimationUtils.loadAnimation(requireContext(), R.anim.scale_1))
        val dialog = builder.create()
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setCancelable(false)
        return dialog
    }
}