package com.example.palliativecare.ui.patientScreens.fragments.doctorTopicsPage.doctorsTopicsFragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.palliativecare.data.DataBase
import com.example.palliativecare.databinding.PatientTopicsBinding
import com.example.palliativecare.ui.adapters.TopicsAdapter

class PatientTopics : Fragment() {
    private  var _binding : PatientTopicsBinding? = null
    private val binding get() = _binding
    private lateinit var db : DataBase
    private lateinit var adapter: TopicsAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{
        if (_binding == null){
            _binding = PatientTopicsBinding.inflate(inflater,container,false)
        }
        db = DataBase(requireContext())
        val doctor = requireActivity().intent.getStringArrayListExtra("doctor")
        if (doctor?.size == 2){
            binding!!.topDoctorName.text = "مواضيع دكتور${doctor[1]}"
            db.getTopics(doctor[0])
        }
        db.onTopicsCame = {
            if (it.size > 0){
                adapter = TopicsAdapter(false,it)
                binding!!.apply{
                    topics.setHasFixedSize(true)
                    topics.layoutManager = LinearLayoutManager(requireContext())
                    topics.adapter = adapter
                }
                adapter.onShowDetails = { currentTopic ->
                    val action = PatientTopicsDirections.actionPatientTopicsToPatientTopicDetails(
                        currentTopic
                    )
                    findNavController().navigate(action)
                }
            }
        }

        return binding!!.root
    }
}