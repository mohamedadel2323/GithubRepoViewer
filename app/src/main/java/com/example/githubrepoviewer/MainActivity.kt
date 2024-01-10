package com.example.githubrepoviewer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.example.githubrepoviewer.databinding.ActivityMainBinding
import com.example.githubrepoviewer.utils.NetworkConnectivityObserver
import com.example.githubrepoviewer.utils.Status
import com.example.githubrepoviewer.utils.collectLatestLifeCycleFlow
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var networkConnectivityObserver: NetworkConnectivityObserver
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        observeNetworkState()
    }

    private fun observeNetworkState() {
        collectLatestLifeCycleFlow(networkConnectivityObserver.observe()){
            delay(200)
            if (it == Status.Lost || it == Status.Unavailable)
                Toast.makeText(this, "No Connection", Toast.LENGTH_SHORT).show()
        }
    }
}