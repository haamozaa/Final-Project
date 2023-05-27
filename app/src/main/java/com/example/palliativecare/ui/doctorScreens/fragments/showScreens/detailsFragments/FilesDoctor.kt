package com.example.palliativecare.ui.doctorScreens.fragments.showScreens.detailsFragments

import android.app.DownloadManager
import android.content.Context
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.palliativecare.classesHelper.CheckInternet
import com.example.palliativecare.data.DataBase
import com.example.palliativecare.databinding.FilesDoctorBinding

class FilesDoctor : Fragment() {
    private lateinit var _binding : FilesDoctorBinding
    private val binding get() = _binding
    private lateinit var url : String
    private lateinit var db : DataBase
    private lateinit var name : String
    private lateinit var connected : CheckInternet
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{
        _binding = FilesDoctorBinding.inflate(inflater,container,false)
        name = requireActivity().intent.getStringExtra("topicTitle").toString()
        binding.topicName.text = name

        db = DataBase(requireContext())

        connected = CheckInternet(requireContext(),binding.root)


        connected.onConnected = {
            if (it){
                db.getTopicDetails(requireActivity().intent.getStringExtra("topicId").toString())
                db.onTopicsDetailsCame = {topic ->
                    url = topic.topicPdf.toString()
                }
            }
        }

        binding.downloadBtn.setOnClickListener{
            connected.isConnected()
            connected.onConnected = { bool ->
                if (bool){
                    db.getTopicDetails(requireActivity().intent.getStringExtra("topicId").toString())
                    db.onTopicsDetailsCame = {
                        downloadFile(it.topicPdf.toString(),name)
                    }
                }
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        connected.isConnected()
        super.onViewCreated(view, savedInstanceState)
    }
}