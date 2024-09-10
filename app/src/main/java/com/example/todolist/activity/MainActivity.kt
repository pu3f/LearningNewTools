package com.example.todolist.activity

import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.provider.Settings.Secure
import androidx.appcompat.app.AppCompatActivity
import com.example.todolist.fragment.ChartFragment
import com.example.todolist.fragment.HomeFragment
import com.example.todolist.fragment.SettingFragment
import com.example.todolist.listener.CheckNetwork
import com.example.todolist.R
import com.example.todolist.databinding.ActivityMainBinding
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.button.MaterialButton


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if(!CheckNetwork.isOnline(this)) {
            showNetworkDialog()
        }

        val deviceId = Secure.getString(this.contentResolver, Secure.ANDROID_ID)
        showHomeFragment(deviceId)

        binding.bottomNav.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.menu_home -> {
                    showHomeFragment(deviceId)
                }
                R.id.menu_chart -> {
                    showChartFragment()
                }
                R.id.menu_setting -> {
                    showSettingFragment()
                }
            }
            true
        }
    }

    @SuppressLint("InflateParams")
    private fun showNetworkDialog() {
        val dialog = BottomSheetDialog(this)
        val view = layoutInflater.inflate(R.layout.network_bottom_sheet_dialog, null)
        val btnOk = view.findViewById<MaterialButton>(R.id.btnOkay)
        btnOk.setOnClickListener {
            dialog.dismiss()
        }
        dialog.behavior.halfExpandedRatio = 0.5F
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setContentView(view)
        dialog.show()
    }

    private fun showHomeFragment(imei: String){
        val homeFragment = HomeFragment()
        HomeFragment.newInstance(imei)
        supportFragmentManager.beginTransaction().replace(R.id.container, homeFragment).commit()
    }

    private fun showChartFragment(){
        val chartFragment = ChartFragment()
        supportFragmentManager.beginTransaction().replace(R.id.container, chartFragment).commit()
    }

    private fun showSettingFragment(){
        val settingFragment = SettingFragment()
        supportFragmentManager.beginTransaction().replace(R.id.container, settingFragment).commit()
    }

    override fun onResume() {
        super.onResume()
        if(!CheckNetwork.isOnline(this)) {
            showNetworkDialog()
        }
    }
}