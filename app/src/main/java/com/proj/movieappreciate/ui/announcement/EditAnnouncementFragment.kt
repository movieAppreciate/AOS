package com.proj.movieappreciate.ui.announcement

import androidx.fragment.app.Fragment
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.fragment.app.viewModels
import com.proj.movieappreciate.R
import com.proj.movieappreciate.databinding.FragmentEditAnnouncementBinding
import com.proj.movieappreciate.ui.viewModel.AnnouncementViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class EditAnnouncementFragment: Fragment()  {


    private val announcementViewModel : AnnouncementViewModel by viewModels()

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


        //todo : 완료 버튼 클릭 시 동작  추가
        binding.doneBtn.setOnClickListener{

            // todo : 공지의 유형을 type로 넘겨주기
            val type = binding.autoCompletetv.text.toString()

            //todo : 공지의 제목을 title로 넘겨주기
            val title = binding.titleEditText.text.toString()

            //todo : 공지의 내용을 content 값으로 넘겨주기
            val content = binding.contentEditText.text.toString()

            //todo : 공지 등록 뷰 모델과 연결해주기

            Log.d("공지", title + content + type)
            announcementViewModel.registAnnouncement(title,content,type)
            Log.d("공지 내용", announcementViewModel.announcementResponse.value.toString())

        }


    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}