package com.example.palliativecare.ui.registration.signing

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.palliativecare.R
import com.example.palliativecare.data.DataBase
import com.example.palliativecare.databinding.SignUpBinding
import com.example.palliativecare.model.User
import com.example.palliativecare.ui.doctorScreens.DoctorContainer
import com.example.palliativecare.ui.patientScreens.PatientContainer
import java.util.*
import kotlin.collections.ArrayList


class SignUp : Fragment() {
    private lateinit var _binding : SignUpBinding
    private val binding get() = _binding
    private var datePickerDialog: DatePickerDialog? = null
    private var type = "طبيب"
    lateinit var db:DataBase
    private var category = ""
    private lateinit var categoriseV : ArrayList<String>
    private lateinit var categoriseK : ArrayList<String>
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{
        _binding = SignUpBinding.inflate(inflater,container,false)
        db = DataBase(requireContext())

        db.getCategorise()
        initDatePicker()
        categoriseV = arrayListOf()
        categoriseK = arrayListOf()

        val dateButton = binding.date
        dateButton.setText(getTodaysDate())
        binding.date.setOnFocusChangeListener { view, b ->
            if (view.isFocused){
                datePickerDialog!!.show()
            }
        }

        binding.type.addOnButtonCheckedListener { group, checkedId, isChecked ->
            if (isChecked){
                when(checkedId){
                    binding.doctor.id ->{
                        type = "طبيب"
                        Toast.makeText(requireContext(),type,Toast.LENGTH_LONG).show()

                    }
                    binding.patient.id ->{
                        type = "مريض"
                        Toast.makeText(requireContext(),type,Toast.LENGTH_LONG).show()
                    }
                }
            }
        }
        binding.sign.setOnClickListener {
            binding.apply {
                if (fiName.text.toString().isNotEmpty() &&
                    password.text.toString().length >= 8 &&
                    rePassword.text.toString() == password.text.toString() &&
                    seName.text.toString().isNotEmpty() &&
                    theName.text.toString().isNotEmpty() &&
                    email.text.toString().isNotEmpty() &&
                    nomber.text.toString().isNotEmpty() &&
                    date.text.toString().isNotEmpty() &&
                    address.text.toString().isNotEmpty() && category != ""){
                    fiName.isEnabled = false
                    seName.isEnabled = false
                    theName.isEnabled = false
                    email.isEnabled = false
                    nomber.isEnabled = false
                    date.isEnabled = false
                    address.isEnabled = false
                    password.isEnabled = false
                    rePassword.isEnabled = false
                    categorise.isEnabled = false
                    val user = User(fiName.text.toString(),seName.text.toString(),theName.text.toString(),email.text.toString(),nomber.text.toString(),date.text.toString(),address.text.toString(),category)
                    signUp(user,binding.password.text.toString())
                }
            }
        }
        binding.password.doOnTextChanged { text, _, _, _ ->
            binding.rePassCon.isEnabled = text?.length!! >7
        }
        db.onSavedUser = {
            when(it){
                true -> {
                    if (type == "طبيب"){
                        startActivity(Intent(requireContext(),DoctorContainer::class.java))
                    }else{
                        startActivity(Intent(requireContext(),PatientContainer::class.java))
                    }
                    requireActivity().finish()
                }
                false -> {

                    Toast.makeText(requireContext(),"SOMETHING WENT WRONG !",Toast.LENGTH_LONG).show()
                    binding.apply {
                        fiName.isEnabled = true
                        seName.isEnabled = true
                        theName.isEnabled = true
                        email.isEnabled = true
                        nomber.isEnabled = true
                        date.isEnabled = true
                        address.isEnabled = true
                        password.isEnabled = true
                        rePassword.isEnabled = true
                        categorise.isEnabled = true
                    }
                }
            }
        }
        db.onCategoryCame = { categorise ->
            if (categorise.isNotEmpty()){
                categorise.forEach { item ->
                    categoriseV.add(item.value)
                    categoriseK.add(item.key)
                }
                val arrayAdapter = ArrayAdapter(requireContext(),R.layout.drop_down_menu,categoriseV)
                binding.categorise.setAdapter(arrayAdapter)

            }
        }
        binding.categorise.setOnItemClickListener { adapterView, view, i, l ->
            category = categoriseK[i]
        }
        binding.btnGoLogin.setOnClickListener {
            findNavController().navigate(R.id.action_signUp_to_logIn)
        }

        Handler().postDelayed({
            binding.signUpScreen.transitionToEnd()
        },100)

        return binding.root
    }

    private fun signUp(user:User,password:String){
        db.signUpUser(user,password,type)
    }

    private fun getTodaysDate(): String {
        val cal: Calendar = Calendar.getInstance()
        val year: Int = cal.get(Calendar.YEAR)
        var month: Int = cal.get(Calendar.MONTH)
        month += 1
        val day: Int = cal.get(Calendar.DAY_OF_MONTH)
        return makeDateString(day, month, year)
    }

    private fun initDatePicker() {
        val dateSetListener =
            OnDateSetListener { datePicker, year, month, day ->
                var month = month
                month += 1
                val date: String = makeDateString(day, month, year)
                binding.date.setText(date)
            }

        val cal = Calendar.getInstance()
        val year = cal[Calendar.YEAR]
        val month = cal[Calendar.MONTH]
        val day = cal[Calendar.DAY_OF_MONTH]

        val style: Int = AlertDialog.THEME_HOLO_LIGHT

        datePickerDialog = DatePickerDialog(requireContext(), style, dateSetListener, year, month, day)
    }

    private fun makeDateString(day: Int, month: Int, year: Int) : String {
        return getMonthFormat(month) + " " + day + " " + year
    }

    private fun getMonthFormat(month: Int): String {
        if(month == 1)
            return "يناير";
        if(month == 2)
            return "فبراير";
        if(month == 3)
            return "مارس";
        if(month == 4)
            return "ابريل";
        if(month == 5)
            return "مايو";
        if(month == 6)
            return "يونيو";
        if(month == 7)
            return "يوليو";
        if(month == 8)
            return "أغسطس";
        if(month == 9)
            return "سبتمبر";
        if(month == 10)
            return "أكتوبر";
        if(month == 11)
            return "نوفمبر";
        if(month == 12)
            return "ديسمبر";

        //default should never happen
        return "يونيو";
    }
}