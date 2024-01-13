package com.example.githubrepoviewer.search.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.githubrepoviewer.R
import com.example.githubrepoviewer.databinding.FragmentSearchBinding
import com.example.githubrepoviewer.repos.presentation.models.RepoUiModel
import com.example.githubrepoviewer.utils.collectLifeCycleFlow
import com.example.githubrepoviewer.utils.visibleIf
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchFragment : Fragment() {

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!
    private val searchViewModel by viewModels<SearchViewModel>()
    private lateinit var searchAdapter: SearchAdapter
    private val navController by lazy { findNavController() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_search, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setListeners()
        setSearchRecycler()
        observeScreenState()
    }

    private fun setListeners() {
        binding.searchView.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                searchByNameOrOwner(binding.searchView.text.toString().trim())
                true
            } else {
                false
            }
        }
        binding.backButton.setOnClickListener { navController.navigateUp() }
    }

    private fun observeScreenState() {
        collectLifeCycleFlow(searchViewModel.reposState) { screenState ->
            screenState.searchRepos?.let { searchAdapter.submitList(it) }
            binding.progressBar visibleIf screenState.isReposLoading
            if (screenState.errorMessage.isNotEmpty()) {
                Toast.makeText(requireContext(), screenState.errorMessage, Toast.LENGTH_SHORT)
                    .show()
                searchViewModel.errorMessageShown()
            }
        }
    }

    private fun setSearchRecycler() {
        searchAdapter = SearchAdapter {
            navigateToDetailsScreen(it)
        }
        binding.searchRv.adapter = searchAdapter
    }

    private fun navigateToDetailsScreen(it: RepoUiModel) {
        navController.navigate(
            SearchFragmentDirections.actionSearchFragmentToRepoDetailsFragment(
                it.repoOwner,
                it.repoName
            )
        )
    }

    private fun searchByNameOrOwner(query: String) {
        if (query.isNotEmpty()) {
            searchViewModel.searchForRepo(query)
        } else {
            searchViewModel.resetList()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}