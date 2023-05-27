package com.example.palliativecare.ui.doctorScreens.fragments

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.palliativecare.R
import com.example.palliativecare.data.DataBase
import com.example.palliativecare.databinding.DoctorHomeBinding
import com.example.palliativecare.dialogs.AlertDialog
import com.example.palliativecare.ui.adapters.TopicsAdapter
import com.example.palliativecare.ui.doctorScreens.fragments.homeFragments.AddTopicArgs
import com.example.palliativecare.ui.doctorScreens.fragments.showScreens.DetailsScreenDoctor
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.RemoteMessage


class DoctorHome : Fragment() {
    private lateinit var _binding : DoctorHomeBinding
    private val binding get() = _binding
    private lateinit var db : DataBase
    private lateinit var recycler : RecyclerView
    private val args by navArgs<AddTopicArgs>()
    @SuppressLint("NotifyDataSetChanged")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{
        _binding = DoctorHomeBinding.inflate(inflater,container,false)


        db = DataBase(requireContext())
        db.getTopics(null)

        db.onTopicsCame ={
            if (it.size > 0){
                recycler.layoutManager = LinearLayoutManager(requireContext())
                recycler.setHasFixedSize(true)
                var adapter = TopicsAdapter(true,it)
                binding.topicsRecycler.adapter = adapter
                adapter.onShowDetails = {topic->
                    val i = Intent(requireContext(), DetailsScreenDoctor::class.java)
                    i.putExtra("topicId",topic.topicId)
                    i.putExtra("topicTitle",topic.topicTitle)
                    startActivity(i)
                }
                adapter.onEdit = {topic ->
                    val action = DoctorHomeDirections.addTopicAction(topic)
                    findNavController().navigate(action)
                }
                adapter.onDelete = {topic ->
                            val myDialog = AlertDialog("حذف ${topic.topicTitle}","هل تود حذف موضوع ${topic.topicTitle} بجميع ملفاته")
                            myDialog.show(parentFragmentManager,"TAG")
                            myDialog.onDelete = { delete ->
                                if (delete){
                                    db.deleteTopic(topic)
                                    db.onDeleteTopic = { deleted ->
                                        if (deleted){
                                            Toast.makeText(requireContext(),"Deleted Successfully :)",Toast.LENGTH_LONG).show()
                                            adapter.notifyDataSetChanged()
                                        }
                                    }
                                }
                            }
                }
            }

        }
        binding.addTopic.setOnClickListener {
            findNavController().navigate(R.id.addTopicAction)
        }
        recycler = binding.topicsRecycler
        return binding.root
    }
}