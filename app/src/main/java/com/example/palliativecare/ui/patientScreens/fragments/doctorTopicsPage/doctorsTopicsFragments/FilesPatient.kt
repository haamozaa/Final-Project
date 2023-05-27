package com.example.palliativecare.ui.patientScreens.fragments.doctorTopicsPage.doctorsTopicsFragments

import android.app.DownloadManager
import android.content.Context
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.navigation.fragment.navArgs
import com.example.palliativecare.R
import com.example.palliativecare.classesHelper.CheckInternet
import com.example.palliativecare.databinding.FilesPatientBinding
import com.example.palliativecare.model.Topic

class FilesPatient : Fragment() {
    private lateinit var _binding : FilesPatientBinding
    private val binding get() = _binding
    private val args by navArgs<FilesPatientArgs>()
    private lateinit var checkInternet: CheckInternet
    private lateinit var topic : Topic
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{
        _binding = FilesPatientBinding.inflate(inflater,container,false)

        checkInternet = CheckInternet(requireContext(),binding.root)
        if (args.myTopicB.topicId != null){
            topic = args.myTopicB
            binding.topicName.text = topic.topicTitle
        }
        binding.downloadBtn.setOnClickListener {
            if (topic.topicPdf != null && checkInternet.isConnectedBool()){
                downloadFile(topic.topicPdf!!,topic.topicTitle!!)
            }
        }
        return binding.root
    }
    private fun downloadFile(url : String,nameSc:String){
        val request = DownloadManager.Request(Uri.parse(url))
            .setTitle(nameSc)
            .setDescription("$nameSc Downloading..")
            .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
            .setAllowedOverMetered(true)
        val dm = requireActivity().getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
        dm.enqueue(request)
    }

}