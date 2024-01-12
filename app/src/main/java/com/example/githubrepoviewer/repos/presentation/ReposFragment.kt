package com.example.githubrepoviewer.repos.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubrepoviewer.R
import com.example.githubrepoviewer.databinding.FragmentReposBinding
import com.example.githubrepoviewer.utils.collectLifeCycleFlow
import com.example.githubrepoviewer.utils.visibleIf
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ReposFragment : Fragment() {
    private var _binding: FragmentReposBinding? = null
    private val binding get() = _binding!!
    private val reposViewModel by viewModels<ReposViewModel>()
    private val navController by lazy { findNavController() }
    private lateinit var reposAdapter: ReposAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_repos, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setReposRecycler()
        observeScreenState()
    }

    private fun setReposRecycler() {
        reposAdapter = ReposAdapter(
            {
                navController.navigate(ReposFragmentDirections.actionReposFragmentToRepoDetailsFragment(it.repoOwner, it.repoName))
            },
            { repo ->
                reposViewModel.updateRepo(repo.repoOwner, repo.repoName)
            }
        )
        val reposLayoutManager = LinearLayoutManager(requireContext())
        reposLayoutManager.orientation = LinearLayoutManager.VERTICAL
        binding.reposRv.apply {
            layoutManager = reposLayoutManager
            adapter = reposAdapter
        }
    }

    private fun observeScreenState() {
        collectLifeCycleFlow(reposViewModel.reposState) {
            if (it.repos.isNotEmpty()) {
                reposAdapter.submitList(it.repos)
            }
            binding.progressBar visibleIf it.isReposLoading
            if (it.errorMessage.isNotEmpty()) {
                Toast.makeText(requireContext(), it.errorMessage, Toast.LENGTH_SHORT).show()
                reposViewModel.errorMessageShown()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}