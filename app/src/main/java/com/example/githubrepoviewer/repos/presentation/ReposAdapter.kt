    package com.example.githubrepoviewer.repos.presentation

    import android.content.Context
    import android.view.LayoutInflater
    import android.view.ViewGroup
    import androidx.paging.PagingDataAdapter
    import androidx.recyclerview.widget.DiffUtil
    import androidx.recyclerview.widget.RecyclerView
    import com.example.githubrepoviewer.databinding.RepoListItemBinding
    import com.example.githubrepoviewer.repos.presentation.models.RepoUiModel

    class ReposAdapter(
        private val selectRepo: (RepoUiModel) -> Unit,
        private val showStars: (RepoUiModel) -> Unit
    ) : PagingDataAdapter<RepoUiModel, ReposAdapter.RepoViewHolder>(RecyclerDiffUtilRepoUiModel()) {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepoViewHolder {
            val inflater: LayoutInflater =
                parent.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            val binding = RepoListItemBinding.inflate(inflater, parent, false)
            return RepoViewHolder(binding)
        }


        override fun onBindViewHolder(holder: RepoViewHolder, position: Int) {
            getItem(position)?.let {
                holder.onBind(it)
            }
        }

        inner class RepoViewHolder(private val binding: RepoListItemBinding) :
            RecyclerView.ViewHolder(binding.root) {
            fun onBind(currentItem: RepoUiModel) {
                binding.repoModel = currentItem
                binding.showStarsBtn.setOnClickListener {
                    showStars(currentItem)
                }
                binding.root.setOnClickListener { selectRepo(currentItem) }
            }
        }
    }

    class RecyclerDiffUtilRepoUiModel : DiffUtil.ItemCallback<RepoUiModel>() {
        override fun areItemsTheSame(oldItem: RepoUiModel, newItem: RepoUiModel): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: RepoUiModel, newItem: RepoUiModel): Boolean {
            return oldItem == newItem
        }
    }