package kr.thxallgrace.mvvminstagram.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import kr.thxallgrace.mvvminstagram.R
import kr.thxallgrace.mvvminstagram.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    lateinit var binding : ActivityLoginBinding
    val loginViewModel : LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)
        binding.viewModel = loginViewModel
        binding.activity = this

        setObserve()
    }
    private fun setObserve() {
        loginViewModel.showInputNumberActivity.observe(this){
            if(it){
                finish()
                startActivity(Intent(this, InputNumberActivity::class.java))
            }
        }

        loginViewModel.showFindIdActivity.observe(this){
            if(it){
                startActivity(Intent(this, FindIdActivity::class.java))
            }
        }
    }

    fun loginEmail() {
        loginViewModel.showFindIdActivity.value = true
    }

    fun findId() {
        loginViewModel.showFindIdActivity.value = true
    }
}