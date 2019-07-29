package com.example.ejemplo1_curso_kotlin.TabLayout

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.viewpager.widget.ViewPager
import com.example.ejemplo1_curso_kotlin.R
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.activity_tab_base.*

class TabBase : AppCompatActivity() {

    var viewPager: ViewPager? = null
    var tabLayout: TabLayout? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tab_base)

        viewPager = findViewById(R.id.viewPager)
        tabLayout = findViewById(R.id.tabs)

        configurarViewPager()
        tabLayout?.setupWithViewPager(viewPager)
        tabLayout?.getTabAt(0)!!.setIcon(R.drawable.icono_guardar_contacto)
        tabLayout?.getTabAt(1)!!.setIcon(R.drawable.icono_editar)
        tabLayout?.getTabAt(2)!!.setIcon(R.drawable.icono_eliminar)
    }

    fun configurarViewPager(){
        val adapter = ViewPagerAdapter(supportFragmentManager)
        adapter.addFragment(FragmentTab(), "Fragmento 1" )
        adapter.addFragment(FragmentTab(), "Fragmento 2" )
        adapter.addFragment(FragmentTab(), "Fragmento 3" )

        viewPager?.adapter = adapter
    }
}
