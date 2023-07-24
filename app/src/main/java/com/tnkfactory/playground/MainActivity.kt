package com.tnkfactory.playground

import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import android.view.Menu
import android.view.MenuItem
import com.tnkfactory.ad.rwdplus.TnkRwdPlus
import com.tnkfactory.playground.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        val navController = findNavController(R.id.nav_host_fragment_content_main)
        appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController, appBarConfiguration)

        // 객체 초기화
        val rwdPlus = TnkRwdPlus(this@MainActivity)
        binding.fab.setOnClickListener { view ->
            // 유저 식별용 id입니다.
            rwdPlus.setUserId("test_user_id")
            // COAAPA 설정 미국 어린이 온라인 개인정보 보호법(https://www.ftc.gov/business-guidance/privacy-security/childrens-privacy)
            // true - 13세 미만 아동을 대상으로 한 서비스 일경우 사용
            // false - OFF
            rwdPlus.setCOPPA(false)
            // 매체 구분용 app id입니다. TNK 홈페이지 로그인 후 아래의 링크에서 확인 가능합니다.
            // APP ID (https://ads.tnkad.net/tnk/ko/ppi.apps.user?action=getAppInfo&app_id=122289)
            rwdPlus.setApplicationId("e0300060-60f1-5889-9885-1f040802080d")
            // shop이용시 사용되는 전화번호 입니다.
            rwdPlus.setPhoneNumber("01023456789")
            // 오퍼월 화면을 출력합니다.
            rwdPlus.showOfferwall(this@MainActivity)
        }
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
