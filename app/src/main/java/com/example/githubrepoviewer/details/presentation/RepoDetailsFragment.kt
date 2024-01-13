package com.example.githubrepoviewer.details.presentation

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
import com.example.githubrepoviewer.R
import com.example.githubrepoviewer.databinding.FragmentRepoDetailsBinding
import com.example.githubrepoviewer.utils.collectLifeCycleFlow
import com.example.githubrepoviewer.utils.visibleIf
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RepoDetailsFragment : Fragment() {

    private var _binding: FragmentRepoDetailsBinding? = null
    private val binding get() = _binding!!
    private val detailsViewModel by viewModels<DetailsViewModel>()
    private val navController by lazy { findNavController() }
    private val args by navArgs<RepoDetailsFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_repo_details, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setListeners()
        observeScreenState()
    }

    private fun setListeners() {
        binding.issuesBtn.setOnClickListener {
            navController.navigate(RepoDetailsFragmentDirections.actionRepoDetailsFragmentToIssuesFragment(args.ownerName, args.repoName))
        }
        binding.backButton.setOnClickListener { navController.navigateUp() }
    }

    private fun observeScreenState() {
        collectLifeCycleFlow(detailsViewModel.reposState){screenState->
            if (screenState.networkCallCreated.not()) {
                detailsViewModel.getRepo(args.ownerName, args.repoName)
            }
            screenState.repo?.let { binding.repoDetails = it }
            binding.progressBar visibleIf screenState.isReposLoading
            if (screenState.errorMessage.isNotEmpty()) {
                Toast.makeText(requireContext(), screenState.errorMessage, Toast.LENGTH_SHORT).show()
                detailsViewModel.errorMessageShown()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}