package com.example.palliativecare.ui.doctorScreens.fragments.showScreens.detailsFragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.palliativecare.R
import com.example.palliativecare.classesHelper.CheckInternet
import com.example.palliativecare.data.DataBase
import com.example.palliativecare.databinding.DetailsDoctorBinding
import com.squareup.picasso.Picasso

class DetailsDoctor : Fragment() {
    private var _binding : DetailsDoctorBinding? = null
    private val binding get() = _binding
    private lateinit var db : DataBase
    private lateinit var connection : CheckInternet
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{
        if (_binding == null){
            _binding = DetailsDoctorBinding.inflate(inflater,container,false)
        }
        db = DataBase(requireContext())
        connection = CheckInternet(requireContext(),binding!!.root)
        connection.onConnected = {
            if (it){
                db.getTopicDetails(requireActivity().intent.getStringExtra("topicId").toString())
                db.onTopicsDetailsCame = {
                    binding!!.apply {
                        Picasso.get().load(it.topicImage.toString()).into(topicImage)
                        topicTitle.text = it.topicTitle
                        topic.text = it.topic
                    }
                }
            }
        }


        binding!!.fileBtn.setOnClickListener {
            findNavController().navigate(R.id.action_detailsDoctor_to_filesDoctor)
        }

        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?){
        connection.isConnected()
        super.onViewCreated(view, savedInstanceState)
    }
}