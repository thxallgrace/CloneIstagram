package kr.thxallgrace.mvvminstagram.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.common.api.ApiException
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
        binding.lifecycleOwner = this

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

    fun findId() {
        loginViewModel.showFindIdActivity.value = true
    }


    var googleLoginResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
        result ->
        println("result:${result.toString()}")
        val data = result.data
        val task = GoogleSignIn.getSignedInAccountFromIntent(data)
        val account = task.getResult(ApiException::class.java)
        println("account:${account}")
        loginViewModel.firebaseAuthWithGoogle(account.idToken)  // 로그인한 사용자 정보를 암호화한 값
    }

}