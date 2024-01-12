package com.example.githubrepoviewer.issues.presentation

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.githubrepoviewer.databinding.IssueListItemBinding
import com.example.githubrepoviewer.issues.presentation.models.IssueUiModel

class IssuesAdapter : ListAdapter<IssueUiModel, IssuesAdapter.IssueViewHolder>(RecyclerDiffUtilIssueUiModel()) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IssueViewHolder {
        val inflater: LayoutInflater =
            parent.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val binding = IssueListItemBinding.inflate(inflater, parent, false)
        return IssueViewHolder(binding)
    }


    override fun onBindViewHolder(holder: IssueViewHolder, position: Int) {
        val currentItem = getItem(position)
        holder.onBind(currentItem)
    }

    inner class IssueViewHolder(private val binding: IssueListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(currentItem: IssueUiModel) {
            binding.issueModel = currentItem
        }
    }
}

class RecyclerDiffUtilIssueUiModel : DiffUtil.ItemCallback<IssueUiModel>() {
    override fun areItemsTheSame(oldItem: IssueUiModel, newItem: IssueUiModel): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: IssueUiModel, newItem: IssueUiModel): Boolean {
        return oldItem == newItem
    }
}