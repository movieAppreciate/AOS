package com.proj.movieappreciate.ui.announcement

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.proj.movieappreciate.R
import com.proj.movieappreciate.databinding.FragmentEditAnnouncementBinding

class EditAnnouncementFragment : Fragment() {

    private var _binding: FragmentEditAnnouncementBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentEditAnnouncementBinding.inflate(layoutInflater,container,false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val typeList = resources.getStringArray(R.array.type)
        val arrayAdapter = ArrayAdapter(requireContext(),R.layout.type_list_item,typeList)
        binding.autoCompletetv.setAdapter(arrayAdapter)

        binding.backBtn.setOnClickListener{
            val fragmentManager = requireActivity().supportFragmentManager
            val fragmentTransaction = fragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.fragmentContainerView,AnnouncementFragment())
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.commit()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}