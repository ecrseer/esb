package com.example.myapplication

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import com.example.myapplication.databinding.ActivityMainBinding
import com.example.myapplication.model.NoteImagens
import com.example.myapplication.view.listaimageminicial.ListaImagemPesquisadaFragmentDirections

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    private lateinit var mainViewModel: MainViewModel
    private lateinit var  mh: MainHandler
    private lateinit var navController:NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


    }

    private fun populaImgs() {
        NoteImagens.imgs.forEach { img ->
            img.big = getString(R.string.imagemTeste)
        }
    }

    override fun onResume() {
        super.onResume()
        setSupportActionBar(binding.toolbar)

        navController = findNavController(R.id.Nav_hostNovo)
        appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController, appBarConfiguration)


        populaImgs()

        mainViewModel = ViewModelProvider(this,MainViewModelFactory())[MainViewModel::class.java]
        mh = MainHandler(mainViewModel.peneiraNotaPorTexto)
        binding.fab.setOnClickListener { view ->
            val action = ListaImagemPesquisadaFragmentDirections.actionCriarNotaImagem(0);
            navController.navigate(action)
        }
        supportActionBar?.setDisplayShowTitleEnabled(false)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        val searchWdgt = menu.findItem(R.id.app_bar_search)
        val actionViewPesquisa: SearchView = searchWdgt?.actionView as SearchView

        actionViewPesquisa.setOnQueryTextListener(mh)
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
        val navController = findNavController(R.id.Nav_hostNovo)
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }


}