package com.example.githubrepoviewer.search.presentation

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.githubrepoviewer.databinding.RepoListItemBinding
import com.example.githubrepoviewer.repos.presentation.models.RepoUiModel

class SearchAdapter(
    private val selectRepo: (RepoUiModel) -> Unit,
) :
    ListAdapter<RepoUiModel, SearchAdapter.SearchRepoViewHolder>(DiffUtilRepoUiModel()) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchRepoViewHolder {
        val inflater: LayoutInflater =
            parent.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val binding = RepoListItemBinding.inflate(inflater, parent, false)
        return SearchRepoViewHolder(binding)
    }


    override fun onBindViewHolder(holder: SearchRepoViewHolder, position: Int) {
        getItem(position)?.let {
            holder.onBind(it)
        }
    }

    inner class SearchRepoViewHolder(private val binding: RepoListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(currentItem: RepoUiModel) {
            binding.repoModel = currentItem
            binding.root.setOnClickListener { selectRepo(currentItem) }
        }
    }
}

class DiffUtilRepoUiModel : DiffUtil.ItemCallback<RepoUiModel>() {
    override fun areItemsTheSame(oldItem: RepoUiModel, newItem: RepoUiModel): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: RepoUiModel, newItem: RepoUiModel): Boolean {
        return oldItem == newItem
    }
}