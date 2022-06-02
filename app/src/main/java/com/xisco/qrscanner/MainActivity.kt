package com.xisco.qrscanner

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.budiyev.android.codescanner.CodeScanner


private const val  CAMERA_REQUEST_CODE = 14607
class MainActivity : AppCompatActivity() {
            private lateinit var codeScannerVar: CodeScanner
            override fun onCreate(savedInstanceState: Bundle?) {
                        super.onCreate(savedInstanceState)
                        setContentView(R.layout.activity_main)

                        codeScannerFun()
            }


            private fun codeScannerFun (){
    codeScannerVar = CodeScanner(this,)
            }
}