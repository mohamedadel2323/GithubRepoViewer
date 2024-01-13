package com.example.githubrepoviewer.repos.presentation

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.githubrepoviewer.R
import com.example.githubrepoviewer.databinding.FragmentReposBinding
import com.example.githubrepoviewer.repos.presentation.models.RepoUiModel
import com.example.githubrepoviewer.utils.NetworkConnectivityObserver
import com.example.githubrepoviewer.utils.Status
import com.example.githubrepoviewer.utils.collectLatestLifeCycleFlow
import com.example.githubrepoviewer.utils.collectLifeCycleFlow
import com.example.githubrepoviewer.utils.visibleIf
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import javax.inject.Inject

@AndroidEntryPoint
class ReposFragment : Fragment() {
    private var _binding: FragmentReposBinding? = null
    private val binding get() = _binding!!
    private val reposViewModel by viewModels<ReposViewModel>()
    private val navController by lazy { findNavController() }
    private lateinit var reposAdapter: ReposAdapter

    @Inject
    lateinit var networkConnectivityObserver: NetworkConnectivityObserver
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_repos, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setListeners()
        setReposRecycler()
        observeScreenState()
        observeNetworkState()
    }

    private fun setListeners() {
        binding.searchFab.setOnClickListener { navigateToSearchScreen() }
    }

    private fun observeNetworkState() {
        collectLatestLifeCycleFlow(networkConnectivityObserver.observe()) {
            delay(200)
            binding.searchFab.visibleIf(it == Status.Available)
        }
    }

    private fun setReposRecycler() {
        reposAdapter = ReposAdapter(
            {
                navigateToDetailsScreen(it)
            },
            {
                reposViewModel.updateRepo(it.repoOwner, it.repoName)
            }
        )
        binding.reposRv.adapter = reposAdapter.withLoadStateFooter(ReposLoadStateAdapter())
    }


    private fun navigateToDetailsScreen(it: RepoUiModel) {
        navController.navigate(ReposFragmentDirections.actionReposFragmentToRepoDetailsFragment(it.repoOwner, it.repoName))
    }

    private fun navigateToSearchScreen() {
        navController.navigate(ReposFragmentDirections.actionReposFragmentToSearchFragment())
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private fun observeScreenState() {
        collectLifeCycleFlow(reposViewModel.reposState) { reposScreenState ->
            reposScreenState.repos?.let {
                reposAdapter.submitData(reposScreenState.repos)
            }
            binding.progressBar visibleIf reposScreenState.isReposLoading
            if (reposScreenState.errorMessage.isNotEmpty()) {
                Toast.makeText(requireContext(), reposScreenState.errorMessage, Toast.LENGTH_SHORT)
                    .show()
                reposViewModel.errorMessageShown()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}