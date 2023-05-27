package com.example.palliativecare.ui.doctorScreens.fragments.doctorChatPages.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.palliativecare.data.DataBase
import com.example.palliativecare.databinding.ChatPageDoctorSideBinding
import com.example.palliativecare.model.Doctor
import com.example.palliativecare.model.Message
import com.example.palliativecare.ui.adapters.MessagesAdapter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import java.util.Date


class ChatPageDoctorSide : Fragment() {
   private lateinit var _binding : ChatPageDoctorSideBinding
   private val binding get() = _binding

    private lateinit var db : DataBase
    private lateinit var messages : ArrayList<Message>
    private lateinit var adapter: MessagesAdapter
    private lateinit var senderRoom : String
    private lateinit var receiverRoom : String
    private lateinit var senderId : String
    private lateinit var receiverId : String
    private lateinit var auth : FirebaseAuth
    private lateinit var receiver : Doctor
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{
        _binding = ChatPageDoctorSideBinding.inflate(inflater,container,false)
        db = DataBase(requireContext())
        messages = arrayListOf()
        auth = Firebase.auth
        senderId = auth.currentUser!!.uid

        if (requireActivity().intent.getStringExtra("id") != null){
            receiver = Doctor(requireActivity().intent.getStringExtra("name").toString(),requireActivity().intent.getStringExtra("id").toString(),requireActivity().intent.getStringExtra("token").toString())
            binding.name.text = receiver.name
            receiverId = receiver.id
            senderRoom = senderId + receiverId
            receiverRoom = receiverId + senderId
        }

        if (receiverId != ""){
            db.getChatUser(senderRoom)

        }
        db.onMessagesCame = {cameMessages ->
            messages = cameMessages
            if (messages.size > 0){
                adapter = MessagesAdapter(requireContext(),messages,senderRoom,receiverRoom)
                val linearLayoutManager = LinearLayoutManager(requireContext())
                linearLayoutManager.stackFromEnd = true
                binding.messages.layoutManager = linearLayoutManager
                binding.messages.adapter = adapter
            }
        }
        binding.sendMessage.setOnClickListener {
            if (binding.message.text.toString().isNotEmpty()){
                val messageText = binding.message.text.toString()
                val date = Date()
                val message = Message(messageText,senderId,date.time)
                db.sendMessage(message,date,senderRoom,receiverRoom,receiver.name,receiver.token)
                binding.message.setText("")
            }
        }

        binding.exclamation.setOnClickListener{
            val action = ChatPageDoctorSideDirections.actionChatPageDoctorSideToPatientProfileDoctorSide(receiver)
            findNavController().navigate(action)
        }
        binding.backIcon.setOnClickListener{
            requireActivity().finish()
        }

        return binding.root
    }

}