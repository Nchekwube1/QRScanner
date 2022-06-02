package com.xisco.qrscanner

import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.budiyev.android.codescanner.AutoFocusMode
import com.budiyev.android.codescanner.CodeScanner
import com.budiyev.android.codescanner.DecodeCallback
import com.budiyev.android.codescanner.ErrorCallback
import com.budiyev.android.codescanner.ScanMode
import com.xisco.qrscanner.databinding.ActivityMainBinding


private const val CAMERA_REQUEST_CODE = 14607

class MainActivity : AppCompatActivity() {


            private lateinit var codeScannerVar: CodeScanner
            private lateinit var binding: ActivityMainBinding

            override fun onCreate(savedInstanceState: Bundle?) {
                        super.onCreate(savedInstanceState)
               setTheme(R.style.Theme_QRScanner)
                        binding = ActivityMainBinding.inflate(layoutInflater)
                        val view = binding.root
                        setContentView(view)
                        setupPermissions()
                        codeScannerFun()
            }


            private fun codeScannerFun() {
                        codeScannerVar = CodeScanner(this, binding.codeScannerView)
                        codeScannerVar.apply {
                                    camera = CodeScanner.CAMERA_BACK
                                    formats = CodeScanner.ALL_FORMATS
                                    autoFocusMode = AutoFocusMode.SAFE
                                    scanMode = ScanMode.CONTINUOUS
                                    isAutoFocusEnabled = true
                                    isFlashEnabled = false

                                    decodeCallback = DecodeCallback {
                                                runOnUiThread {
                                                            binding.tvText.text = it.text

                                                }
                                    }

                                    errorCallback = ErrorCallback {
                                                runOnUiThread {
                                                            Log.e(
                                                                        "MainActivity",
                                                                        "Camera initialization error : ${it.message}"
                                                            )
                                                }
                                    }
                        }
            }

            override fun onResume() {
                        super.onResume()
                        codeScannerVar.startPreview()
            }

            override fun onPause() {
                        super.onPause()
                        codeScannerVar.releaseResources()
            }

            private fun setupPermissions() {
                        val permission = ContextCompat.checkSelfPermission(
                                    this,
                                    android.Manifest.permission.CAMERA
                        )
                        if (permission != PackageManager.PERMISSION_GRANTED) {
                                    makePermissionRequest()
                        }
            }

            private fun makePermissionRequest() {
                        ActivityCompat.requestPermissions(
                                    this, arrayOf(android.Manifest.permission.CAMERA),
                                    CAMERA_REQUEST_CODE
                        )
            }

            override fun onRequestPermissionsResult(
                        requestCode: Int,
                        permissions: Array<out String>,
                        grantResults: IntArray
            ) {
                        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
                        when (requestCode) {
                                    CAMERA_REQUEST_CODE -> {
                                                if (grantResults.isEmpty() || grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                                                            Toast.makeText(
                                                                        this,
                                                                        "You need to allow camera permission",
                                                                        Toast.LENGTH_SHORT
                                                            ).show()
                                                } else {
                                                            // success
                                                }
                                    }
                        }
            }
}