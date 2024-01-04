package br.com.oliver.note.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import br.com.oliver.note.R
import br.com.oliver.note.databinding.ActivityMainBinding
import br.com.oliver.note.view.adapters.CategoryFragmentAdapter
import br.com.oliver.note.Application
import br.com.oliver.note.viewmodel.CategoryViewModel
import br.com.oliver.note.viewmodel.CategoryViewModelFactory
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    private val viewModal : CategoryViewModel by viewModels {
        CategoryViewModelFactory((application as Application).repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        startComponent()
        initViewPager()
    }

    private fun startComponent() {
        val toolbar = binding.toolbar
        toolbar.title = ""
        setSupportActionBar(toolbar)

    }

    override fun onStart() {
        super.onStart()
    }

    private fun initViewPager() {

        val adapt = CategoryFragmentAdapter(this)

        viewModal.allCategory.observe(this) {
            adapt.addFragment(it)

            TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
                tab.text = adapt.getPositionName(position)

            }.attach()
//            binding.tabLayout.addTab(binding.tabLayout.newTab().setText(R.string.novaLista).setIcon(R.drawable.ic_round_add_24))

        }

        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
//                nomeTable = tab.text.toString()
//
//                if (nomeTable == getString(R.string.novaLista)) {
//
//                    binding.tabLayout.selectTab(binding.tabLayout.getTabAt(position))
//
//                    val modalBottomSheet : BDFragmentInserir =
//                        BDFragmentInserir.newInstance("")
//                    modalBottomSheet.show(supportFragmentManager, BDFragmentInserir.TAG)
//
//                }else {
//                    position = tab.position
//                }

            }

            override fun onTabUnselected(tab: TabLayout.Tab) {}
            override fun onTabReselected(tab: TabLayout.Tab) {}
        })

        binding.viewPager.adapter = adapt

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }
}