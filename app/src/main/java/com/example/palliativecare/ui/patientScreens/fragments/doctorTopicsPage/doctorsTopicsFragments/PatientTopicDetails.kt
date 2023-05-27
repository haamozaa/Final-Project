package com.example.palliativecare.ui.patientScreens.fragments.doctorTopicsPage.doctorsTopicsFragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.palliativecare.databinding.PatientTopicDetailsBinding
import com.example.palliativecare.model.Topic
import com.squareup.picasso.Picasso

class PatientTopicDetails : Fragment() {
    private var _binding : PatientTopicDetailsBinding? = null
    private val binding get() = _binding
    private lateinit var myTopic : Topic
    private val args by navArgs<PatientTopicDetailsArgs>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{
        if (_binding == null){
            _binding = PatientTopicDetailsBinding.inflate(inflater,container,false)
        }
        if (args.myTopicP.topicId != null){
            myTopic = args.myTopicP
            binding!!.apply {
                topicTitle.text = myTopic.topicTitle
                topic.text = myTopic.topic
                Picasso.get().load(myTopic.topicImage).into(topicImage)
            }
        }
        binding!!.fileBtn.setOnClickListener {
            val action =
                PatientTopicDetailsDirections.actionPatientTopicDetailsToFilesPatient(myTopic)
            findNavController().navigate(action)
        }
        return binding!!.root
    }
}