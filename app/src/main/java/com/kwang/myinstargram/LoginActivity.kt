package com.kwang.myinstargram

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth        // Firebase 인증 header
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {
    var auth : FirebaseAuth? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        auth = FirebaseAuth.getInstance()
        email_login_button.setOnClickListener {
            createAndLoginEmail()
        }
    }

    fun createAndLoginEmail(){
        auth?.createUserWithEmailAndPassword(email_edittext.text.toString(),password_edittext.text.toString())?.addOnCompleteListener {  //비밀번호는 6자리 이상 밑의 코드는 아이디를만들었을 때 확인하기 위한것.
            task ->
            // java에선 Task<AuthRest> : task으로 넘어오지만 kotlin에서는 task: Task<AuthResult>로 넘어와서 람다식으로 바꾸어 task-> 로 쓰임
            if (task.isSuccessful) {
                moveMainPage(auth?.currentUser)
                //Toast.makeText(this, "아이디 생성 성공", Toast.LENGTH_LONG).show()
            } else if (task.exception?.message.isNullOrEmpty()) {  //  task.exception?.message != null 사용해도 상관없음. {
                Toast.makeText(this, task.exception?.message, Toast.LENGTH_LONG).show()
            } else {
                signinEmail()
            }
        }
    }

    fun signinEmail(){
        auth?.signInWithEmailAndPassword(email_edittext.text.toString(),password_edittext.text.toString())?.addOnCompleteListener { task ->
            if(task.isSuccessful){
                moveMainPage(auth?.currentUser)
                //Toast.makeText(this,"로그인이 성공했습니다.",Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(this,task.exception?.message,Toast.LENGTH_LONG).show()
            }
        }
    }

    fun moveMainPage(user : FirebaseUser?){ // kotlin에서 '?'는 nullSafety인데 java에서의 nullpointExcpetion을 막기위해 생긴것.
        if(user != null){
            startActivity(Intent(this,MainActivity::class.java))
            finish()
        }
    }

}
