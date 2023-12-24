package com.proj.movieappreciate.ui.announcement

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton
import com.proj.movieappreciate.R
import com.proj.movieappreciate.databinding.FragmentAnnouncementBinding
import com.proj.movieappreciate.ui.LoginActivity

class AnnouncementFragment : Fragment() {

    private var _binding:FragmentAnnouncementBinding? = null
    private val binding get() = _binding!!
    private var listAnnouncement = ArrayList<Announcement>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentAnnouncementBinding.inflate(layoutInflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.editBtn.setOnClickListener{
            val fragmentManager = requireActivity().supportFragmentManager
            val fragmentTransaction = fragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.fragmentContainerView,EditAnnouncementFragment())
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.commit()
        }

        val list = Announcement(
            "2023.12.24",
            "공지",
            "개인정보처리방침 변경 사전 안내",
            "여기에 긴 내용이 들어갑니다"
        )

        val list1 = Announcement(
            "2023.12.24",
            "공지",
            "개인정보처리방침 변경 사전 안내",
            "여기에 긴 내용이 들어갑니다"
        )

        listAnnouncement.add(list)
        listAnnouncement.add(list1)

        binding.itemList.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = AnnouncementAdapter(listAnnouncement)
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}