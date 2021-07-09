package com.example.movieinfotest.utils

import android.content.res.ColorStateList
import android.graphics.Color
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.NavHostFragment
import com.example.movieinfotest.presentation.ui.main.MainActivityViewModel
import com.example.movieinfotest.R
import com.example.movieinfotest.presentation.ui.base.BaseFragment
import com.example.movieinfotest.presentation.ui.register.RegistrationFragment

object ToolbarMaker {
    fun makeDefaultToolbar(
        toolbar: Toolbar,
        parentViewModel: MainActivityViewModel,
        fragment: BaseFragment
    ) {
        toolbar.inflateMenu(R.menu.tool_menu)
        toolbar.menu.apply {
            getItem(0).isVisible = !FirebaseLogin.isLogin()
            getItem(1).isVisible = FirebaseLogin.isLogin()
            if (parentViewModel.isDarkMode) {
                getItem(2).icon =
                    ContextCompat.getDrawable(fragment.requireContext(), R.drawable.ic_light_mode)
            } else {
                getItem(2).icon =
                    ContextCompat.getDrawable(fragment.requireContext(), R.drawable.ic_dark_mode)
            }

            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                getItem(2)?.iconTintList = ColorStateList.valueOf(Color.WHITE)
            }
        }

        toolbar.makeMenuItemClickListener(parentViewModel, fragment)
    }

    private fun Toolbar.makeMenuItemClickListener(
        parentViewModel: MainActivityViewModel,
        fragment: BaseFragment
    ) {
        setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.dark_mode_btn -> {
                    parentViewModel.changeDarkMode()
                }

                R.id.login -> {
                    RegistrationFragment.navigate(NavHostFragment.findNavController(fragment))
                }

                R.id.logout -> {
                    parentViewModel.auth.signOut()
                    fragment.parentFragmentManager.reRunFragment(fragment)
                }
            }
            return@setOnMenuItemClickListener true
        }
    }
}
