package kr.thxallgrace.mvvminstagram.login

import android.app.Application
import android.content.Context
import android.view.View
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import kr.thxallgrace.mvvminstagram.R

class LoginViewModel(application: Application) : AndroidViewModel(application) {
    private val auth = FirebaseAuth.getInstance()
    var id : MutableLiveData<String> = MutableLiveData("")
    var password : MutableLiveData<String> = MutableLiveData("")

    var showInputNumberActivity : MutableLiveData<Boolean> = MutableLiveData(false)
    var showFindIdActivity : MutableLiveData<Boolean> = MutableLiveData(false)
    val context = getApplication<Application>().applicationContext

    var googleSignInClient : GoogleSignInClient

    init {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(context.getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(context, gso)
    }
    fun loginWithSignupEmail() {
        println("loginWithSignupEmail")
        auth.createUserWithEmailAndPassword(id.value.toString(), password.value.toString()).addOnCompleteListener {
            if(it.isSuccessful){
                showFindIdActivity.value = true
            }else{
                //아이디가 중복인경우?
            }
        }
    }

    fun loginGoogle(view: View) {
        val i = googleSignInClient.signInIntent
        (view.context as? LoginActivity)?.googleLoginResult?.launch(i)
    }
    fun firebaseAuthWithGoogle(idToken: String?) {
        println("idToken : $idToken")
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential).addOnCompleteListener {
            println("isSuccess? ${it.isSuccessful}")
            if(it.isSuccessful){
                showFindIdActivity.value = true
            }else{
                //아이디가 중복인경우?
            }
        }
    }

}