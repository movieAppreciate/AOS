package com.proj.movieappreciate.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListAdapter
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.proj.movieappreciate.R
import com.proj.movieappreciate.data.dataSource.model.Report
import com.proj.movieappreciate.databinding.FragmentAnnouncementBinding
import com.proj.movieappreciate.databinding.FragmentSearchBinding
import com.proj.movieappreciate.databinding.MovieCommentaryItemBinding
import com.proj.movieappreciate.ui.custom.ReportAdapter
import com.proj.movieappreciate.ui.viewModel.SearchFragmentViewModel
import dagger.hilt.android.AndroidEntryPoint

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

/**
 * A simple [Fragment] subclass.
 * Use the [SearchFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
@AndroidEntryPoint
class SearchFragment : androidx.fragment.app.Fragment() {
    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!
    private val viewModel: SearchFragmentViewModel by viewModels()
    lateinit var adapter: ReportAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentSearchBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d("rla124", "Fragment onViewCreated")
        adapter = ReportAdapter(requireContext()).apply {
            itemClickListener = object : ReportAdapter.ItemClickListener {
                override fun onClick(
                    binding: MovieCommentaryItemBinding,
                    position: Int,
                    data: Report
                ) {
                    // 다음 페이지(상세 페이지)로 넘어가게 구현 하면 됨
                    TODO("Not yet implemented")
                }

            }
        }
        init()
        viewModel.getAllReports()
    }

    fun init() {
        binding.reportRecycerView.adapter = adapter
        binding.reportRecycerView.layoutManager = LinearLayoutManager(requireContext())

        viewModel.reportList.observe(viewLifecycleOwner) {
            val data = it.getOrNull()
            if (data.isNullOrEmpty()) {
                Toast.makeText(requireContext(), "불러올 감상문이 없습니다.", Toast.LENGTH_SHORT).show()
            } else {
                adapter.submitList(data)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
