package com.yxf.vehiclehj.view.activity

import android.Manifest
import android.app.Activity
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.FragmentActivity
import com.permissionx.guolindev.PermissionX
import com.yxf.vehiclehj.databinding.ActivityMainBinding
import com.yxf.vehiclehj.utils.isShouldHideKeyboard
import com.yxf.vehiclehj.utils.showShortToast
import com.yxf.vehicleinspection.base.BaseBindingActivity

/**
 * 宿主activity 所有fragment的容器
 */
class MainActivity : BaseBindingActivity<ActivityMainBinding>() {

    override fun init() {
        /**
         * 动态请求权限
         */
        PermissionX.init(this)
            .permissions(
                Manifest.permission.CAMERA,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,)
            .request { allGranted, _, deniedList ->
                if (!allGranted) {
                    showShortToast(this,
                        "未获取到: $deniedList")
                }
            }
    }

    /**
     * 点击非键盘区域收起键盘
     */
    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        if (ev?.action == MotionEvent.ACTION_DOWN){
            val view = currentFocus ?: return super.dispatchTouchEvent(ev)
            if (isShouldHideKeyboard(view,ev)){
                val inputMethodManager : InputMethodManager? = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
                inputMethodManager?.hideSoftInputFromWindow(view.windowToken,0)
            }
        }
        return super.dispatchTouchEvent(ev)
    }


    fun setLoadStatus(visibility : Int){
        binding.progressBar.visibility = visibility
    }
}