package com.github.app.repo

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.github.R
import com.github.app.BaseActivity
import com.github.app.homepage.model.entity.event.Repository
import com.google.android.material.tabs.TabLayoutMediator

/**
 * Repository Detail Activity
 * 
 * Display Info, Code, Commits, and Releases for a given repository.
 */
class RepositoryActivity : BaseActivity() {

    private lateinit var mOwner: String
    private lateinit var mRepoName: String
    private lateinit var mViewModel: RepoDetailViewModel

    companion object {
        private const val EXTRA_OWNER = "owner"
        private const val EXTRA_REPO = "repo"

        @JvmStatic
        fun start(context: Context, owner: String, repo: String) {
            val intent = Intent(context, RepositoryActivity::class.java).apply {
                putExtra(EXTRA_OWNER, owner)
                putExtra(EXTRA_REPO, repo)
            }
            context.startActivity(intent)
        }
    }

    override fun getContentView(): Int = R.layout.activity_repository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        mOwner = intent.getStringExtra(EXTRA_OWNER) ?: ""
        mRepoName = intent.getStringExtra(EXTRA_REPO) ?: ""

        initToolbar(R.id.toolbar)
        supportActionBar?.title = mRepoName
        supportActionBar?.subtitle = mOwner

        mViewModel = ViewModelProvider(this)[RepoDetailViewModel::class.java]
        mViewModel.repository.observe(this) {
            updateUI(it)
        }

        initViewPager()
        
        mViewModel.loadRepo(mOwner, mRepoName)
    }

    private fun updateUI(repo: Repository) {
        supportActionBar?.title = repo.name
        supportActionBar?.subtitle = repo.owner?.login
    }

    private fun initViewPager() {
        val viewPager = findViewById<androidx.viewpager2.widget.ViewPager2>(R.id.view_pager)
        val tabLayout = findViewById<com.google.android.material.tabs.TabLayout>(R.id.tab_layout)

        val tabs = listOf("Info", "Code", "Commits", "Releases")
        
        viewPager.adapter = object : FragmentStateAdapter(this) {
            override fun getItemCount(): Int = tabs.size

            override fun createFragment(position: Int): Fragment {
                return RepoPlaceholderFragment.newInstance(tabs[position])
            }
        }

        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = tabs[position]
        }.attach()
    }
}
