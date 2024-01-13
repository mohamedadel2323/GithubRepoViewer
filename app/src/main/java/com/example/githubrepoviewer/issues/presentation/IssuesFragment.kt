package com.example.githubrepoviewer.issues.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubrepoviewer.R
import com.example.githubrepoviewer.databinding.FragmentIssuesBinding
import com.example.githubrepoviewer.utils.collectLifeCycleFlow
import com.example.githubrepoviewer.utils.visibleIf
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class IssuesFragment : Fragment() {

    private var _binding: FragmentIssuesBinding? = null
    private val binding get() = _binding!!
    private val issuesViewModel by viewModels<IssuesViewModel>()
    private val navController by lazy { findNavController() }
    private val args by navArgs<IssuesFragmentArgs>()
    private lateinit var issuesAdapter: IssuesAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_issues, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setListeners()
        setReposRecycler()
        observeScreenState()
    }

    private fun setListeners() {
        binding.backButton.setOnClickListener { navController.navigateUp() }
    }

    private fun setReposRecycler() {
        issuesAdapter = IssuesAdapter()
        val issuesLayoutManager = LinearLayoutManager(requireContext())
        issuesLayoutManager.orientation = LinearLayoutManager.VERTICAL
        binding.issuesRv.apply {
            layoutManager = issuesLayoutManager
            adapter = issuesAdapter
        }
    }


    private fun observeScreenState() {
        collectLifeCycleFlow(issuesViewModel.issuesState) { screenState ->
            if (screenState.networkCallCreated.not()) {
                issuesViewModel.getRepoIssues(args.ownerName, args.repoName)
            }
            if (screenState.issues.isNotEmpty()) {
                issuesAdapter.submitList(screenState.issues)
                Timber.e(screenState.issues.toString())
            }
            if (screenState.errorMessage.isNotEmpty()) {
                Toast.makeText(requireContext(), screenState.errorMessage, Toast.LENGTH_SHORT)
                    .show()
                issuesViewModel.errorMessageShown()
            }
            binding.progressBar visibleIf screenState.isLoading
        }
    }
}