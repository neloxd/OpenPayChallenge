package com.jesusvilla.openpay.ui

import androidx.activity.viewModels
import androidx.annotation.NavigationRes
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.jesusvilla.base.navigation.NavigationHandler
import com.jesusvilla.base.ui.BaseActivity
import com.jesusvilla.base.ui.BaseFragment
import com.jesusvilla.base.viewModel.BaseViewModel
import com.jesusvilla.openpay.R
import com.jesusvilla.openpay.databinding.ActivityMainBinding
import com.jesusvilla.openpay.service.CrashTrackerService
import com.jesusvilla.openpay.service.TaskRepository
import com.jesusvilla.openpay.utils.ConnectionUtil
import com.jesusvilla.openpay.viewModel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>(), NavigationHandler {

    @Inject
    lateinit var crashTrackerService: CrashTrackerService

    @Inject
    lateinit var taskRepository: TaskRepository

    private lateinit var navController: NavController
    private lateinit var mNetworkUtil: ConnectionUtil
    private val viewModel: MainViewModel by viewModels()
    override fun getBaseViewModel(): BaseViewModel = viewModel
    override fun getViewBinding() = ActivityMainBinding.inflate(layoutInflater)

    override fun initNavController() {
        val host: NavHostFragment = supportFragmentManager
            .findFragmentById(R.id.navHostFragment) as NavHostFragment? ?: return
        navController = host.navController
        val mainGraph = navController.navInflater.inflate(R.navigation.main_graph)
        mainGraph.setStartDestination(com.jesusvilla.profile.R.id.profile_graph)
        navController.graph = mainGraph
    }

    override fun initConnectionNetwork() {
        mNetworkUtil = ConnectionUtil(this)
        this.isAvailable = mNetworkUtil.isOnline()
        mNetworkUtil.onInternetStateListener(object : ConnectionUtil.ConnectionStateListener {
            //var firstTime: Boolean = false
            override fun onAvailable(isAvailable: Boolean) {
                Timber.i("onAvailable -> isAvailable:$isAvailable")
                this@MainActivity.isAvailable = isAvailable
            }
        })
    }

    /*private fun checkConnection(isAvailable: Boolean) {
        val host = supportFragmentManager.findFragmentById(R.id.navHostFragment) as NavHostFragment
        host.childFragmentManager.fragments.firstOrNull()?.let {
            if(it is BaseFragment<*>)
                it.connection(isAvailable)
        }
    }*/

    override fun initViews() {
        ViewCompat.setOnApplyWindowInsetsListener(binding.container) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        Timber.i("Service initialized: ${crashTrackerService.isInitialized}")
        //taskRepository.runWork()
        taskRepository.runPeriodicWork()
    }

    override fun setupNavigationListener() {
        with(binding.navBar) {
            setupWithNavController(navController)
            setOnItemSelectedListener { item ->
                return@setOnItemSelectedListener checkNavigate(item.itemId)
            }
        }
    }

    private fun checkNavigate(itemId: Int): Boolean {
        return when (itemId) {
            R.id.bottomNavigationOneId -> {
                navigate(com.jesusvilla.profile.R.navigation.profile_graph)
                true
            }
            R.id.bottomNavigationSecondId -> {
                navigate(com.jesusvilla.movies.R.navigation.movies_graph)
                true
            }
            R.id.bottomNavigationThirdId -> {
                navigate(com.jesusvilla.location.R.navigation.location_graph)
                true
            }
            R.id.bottomNavigationFourId -> {
                navigate(com.jesusvilla.media.R.navigation.media_graph)
                true
            }
            else -> false
        }
    }

    override fun navigate(@NavigationRes dest: Int) {
        val graph = navController.navInflater.inflate(dest)
        navController.graph = graph
    }
}