package br.com.oliver.note.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import br.com.oliver.note.R
import br.com.oliver.note.databinding.ActivityMainBinding
import br.com.oliver.note.view.adapters.ListFragmentAdapter
import br.com.oliver.note.Application
import br.com.oliver.note.model.ListModel
import br.com.oliver.note.viewmodel.ListViewModel
import br.com.oliver.note.viewmodel.ListViewModelFactory
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    private val viewModel: ListViewModel by viewModels {
        ListViewModelFactory((application as Application).repository)
    }

    private lateinit var listModel: ListModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initComponent()
        initOnClick()
        initViewPager()
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


    /******************* methods *******************/

    private fun initComponent() {
        val toolbar = binding.toolbar
        toolbar.title = ""
        setSupportActionBar(toolbar)
    }

    private fun initOnClick() {
        binding.fabAdd.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }

        binding.imgBtMenu.setOnClickListener {
            goToMenuList()
        }

        binding.btNewList.setOnClickListener {
            goToInsert()
        }
    }

    private fun initViewPager() {

        val adapt = ListFragmentAdapter(this)

        viewModel.allLists.observe(this) {
            adapt.addFragment(it)

            TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
                tab.text = adapt.getPositionName(position)
                tab.tag = adapt.getListModel(position)

            }.attach()
        }

        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                listModel = tab.tag as ListModel
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {}
            override fun onTabReselected(tab: TabLayout.Tab) {}
        })

        binding.viewPager.adapter = adapt

    }

    private fun goToInsert() {
        val bottomSheet: InsertOrUpdateListFragment =
            InsertOrUpdateListFragment.newInstance(null, viewModel)
        bottomSheet.show(supportFragmentManager, InsertOrUpdateListFragment.TAG)
    }

    private fun goToMenuList() {
        val bottomSheet: MenuListFragment =
            MenuListFragment.newInstance(listModel, viewModel)
        bottomSheet.show(supportFragmentManager, MenuListFragment.TAG)
    }
}