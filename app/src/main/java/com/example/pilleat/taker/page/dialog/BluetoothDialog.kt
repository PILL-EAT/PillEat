package com.example.pilleat.taker.page.dialog

import android.Manifest
import android.app.Activity
import android.app.Activity.RESULT_OK
import android.app.Dialog
import android.bluetooth.BluetoothAdapter
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.pilleat.databinding.ActivityMaintakerDialogBinding

class BluetoothDialog(private val activity: AppCompatActivity): Dialog(activity) {
    private lateinit var binding: ActivityMaintakerDialogBinding
    private var mBluetoothAdapter: BluetoothAdapter
    private val REQUEST_BLUETOOTH_ADMIN = 2

    init {
        // 생성자에서 초기화
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter()
    }

    // BluetoothDialog 내에서 직접 startActivityForResult를 호출하는 대신,
    // 콜백을 통해 결과를 받을 수 있는 리스너를 설정합니다.
    interface BluetoothDialogListener {
        fun onBluetoothEnabled(resultCode: Int)
    }

    // BluetoothDialog 내에서 사용할 변수 추가
    private var bluetoothDialogListener: BluetoothDialogListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMaintakerDialogBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initViews()

        binding.mainTakerDlOnBtn.setOnClickListener {
            btnOn()
        }

        binding.mainTakerDlOffBtn.setOnClickListener {
            btnOff()
        }
    }

    private fun initViews() {
        // 빈 화면 터치 시, dialog 사라지도록 하기
        setCancelable(true)
    }

    fun btnOn() {
        if (mBluetoothAdapter == null) {
            Toast.makeText(context, "블루투스가 지원되지 않는 기기입니다.", Toast.LENGTH_SHORT).show()
        } else {
            if (!mBluetoothAdapter.isEnabled) {
                Log.d("BluetoothDialog", "블루투스를 켜는 중...")
                // BLUETOOTH_ADMIN 권한 확인
                if (ContextCompat.checkSelfPermission(
                        context,
                        Manifest.permission.BLUETOOTH_ADMIN
                    ) == PackageManager.PERMISSION_GRANTED
                ) {
                    // 블루투스가 꺼져 있으면 켜기
                    val enableBluetoothIntent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
                    val launcher = activity.startActivityForResult(
                        enableBluetoothIntent,
                        REQUEST_BLUETOOTH_ADMIN
                    )
                } else {
                    mBluetoothAdapter?.disable()
                }
            }
        }
    }

    // BluetoothDialog 내의 btnOff 함수에서 비활성화 메서드 호출 수정
    fun btnOff() {
        if (mBluetoothAdapter == null) {
            Toast.makeText(context, "블루투스가 지원되지 않는 기기입니다.", Toast.LENGTH_SHORT).show()
        } else {
            if (mBluetoothAdapter.isEnabled) {
                // BLUETOOTH_ADMIN 권한 확인
                if (ContextCompat.checkSelfPermission(context, Manifest.permission.BLUETOOTH_ADMIN) == PackageManager.PERMISSION_GRANTED) {
                    // 블루투스가 켜져 있으면 블루투스 비활성화
                    mBluetoothAdapter.disable()
                } else {
                    // BLUETOOTH_ADMIN 권한이 없는 경우 요청
                    ActivityCompat.requestPermissions(context as AppCompatActivity, arrayOf(Manifest.permission.BLUETOOTH_ADMIN), REQUEST_BLUETOOTH_ADMIN)
                }
            } else {
                Toast.makeText(context, "블루투스가 이미 꺼져 있습니다.", Toast.LENGTH_SHORT).show()
            }

            // 블루투스 비활성화 작업이 완료된 후에 다이얼로그를 닫음
            dismiss()
        }
    }
}