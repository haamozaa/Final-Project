package com.example.palliativecare.ui.doctorScreens.fragments.homeFragments

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.palliativecare.R
import com.example.palliativecare.classesHelper.CheckInternet
import com.example.palliativecare.data.DataBase
import com.example.palliativecare.databinding.AddTopicBinding
import com.example.palliativecare.ui.doctorScreens.fragments.DoctorHomeDirections
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class AddTopic : BottomSheetDialogFragment(){
    private lateinit var _binding : AddTopicBinding
    private val binding get() = _binding
    private val rCImage = 1000
    private val rCFile = 2000
    private var imagePath : Uri? = null
    private lateinit var db : DataBase
    private var pdfPath: Uri? = null
    private val args by navArgs<AddTopicArgs>()
    private lateinit var bytesPdf : ByteArray
    private lateinit var newTitle : String
    private lateinit var newTopic : String
    private lateinit var categoryId : String
    private lateinit var checkInternet : CheckInternet
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{
        _binding = AddTopicBinding.inflate(inflater,container,false)
        db = DataBase(requireContext())
        db.getCategoryId()
        if (args.myTopic != null){
            args.myTopic!!.apply {
                binding.topicTitle.setText(topicTitle)
                binding.topic.setText(topic)
                binding.addImage.text = "رفع صورة جديدة"
                binding.addImage.text = "رفع ملف جديد"
                binding.saveTopic.text = "حفظ البيانات الجديدة"
                binding.addFile.isVisible = false
                binding.addImage.isVisible = false
            }
        }

        binding.addImage.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(intent,rCImage)
        }
        binding.addFile.setOnClickListener {
            val intent = Intent(Intent.ACTION_GET_CONTENT)
            intent.type = "application/pdf"
            startActivityForResult(intent, rCFile)
        }
        db.onCategoryIdCame = {
            categoryId = it
        }
        binding.saveTopic.setOnClickListener {
            if (checkInternet.isConnectedBool()){
                if (args.myTopic != null){
                    if (binding.topicTitle.text.toString() != args.myTopic!!.topicTitle || binding.topic.text.toString() != args.myTopic!!.topic){
                        binding.motion.transitionToEnd()
                        binding.topic.isEnabled = false
                        binding.topicTitle.isEnabled = false
                        newTitle = binding.topicTitle.text.toString()
                        newTopic = binding.topic.text.toString()
                        db.updateTopic(args.myTopic!!.topicId!!,newTitle,newTopic)
                    }
                }else{
                    if (binding.topicTitle.text!!.isNotEmpty() && binding.topic.text!!.isNotEmpty()&& pdfPath != null && imagePath != null && categoryId.isNotEmpty()){
                        binding.motion.transitionToEnd()
                        db.saveTopic(imagePath.toString(),pdfPath.toString(),
                            title = binding.topicTitle.text.toString(), topic = binding.topic.text.toString(),categoryId)
                    }

                }
            }


        }
        db.onSavedTopic = {
            binding.motion.transitionToStart()
            if (it){
                Toast.makeText(requireContext(),"Saved Success",Toast.LENGTH_LONG).show()
                findNavController().navigate(R.id.doctorHome)
            }else{
                Toast.makeText(requireContext(),"Saved Failed",Toast.LENGTH_LONG).show()
            }
        }
        db.onSavedUpdatedTopic = {
            binding.motion.transitionToStart()
            binding.topic.isEnabled = true
            binding.topicTitle.isEnabled = true
            if (it){
                Toast.makeText(requireContext(),"Updated Success",Toast.LENGTH_LONG).show()
                args.myTopic!!.topic = newTopic
                args.myTopic!!.topicTitle = newTitle
                findNavController().navigate(R.id.doctorHome)
            }else{
                Toast.makeText(requireContext(),"Updated Failed",Toast.LENGTH_LONG).show()
            }
        }


        return binding.root
    }

    @SuppressLint("Recycle")
    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == rCImage){
            imagePath = data!!.data!!
            val bitmap = MediaStore.Images.Media.getBitmap(requireActivity().contentResolver,imagePath)
            binding.uploadedImage.setImageBitmap(bitmap)
        }else if (requestCode == rCFile){
            pdfPath = data!!.data!!
            val inputStream = requireActivity().contentResolver.openInputStream(pdfPath!!)
            bytesPdf = inputStream!!.readBytes()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        checkInternet = CheckInternet(requireContext(),binding.root)
        super.onViewCreated(view, savedInstanceState)
    }
}