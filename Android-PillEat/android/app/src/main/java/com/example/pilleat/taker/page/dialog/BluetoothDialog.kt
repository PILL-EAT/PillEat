package com.example.pilleat.taker.page.dialog

import android.Manifest
import android.app.Dialog
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.pilleat.R
import com.example.pilleat.databinding.ActivityMaintakerDialogBinding


class BluetoothDialog(private val activity: AppCompatActivity) : Dialog(activity) {
    private lateinit var binding: ActivityMaintakerDialogBinding
    private var mBluetoothAdapter: BluetoothAdapter
    private val REQUEST_BLUETOOTH_ADMIN = 2
    private val BLUETOOTH_PERMISSION_REQUEST_CODE = 123
    var mPairedDevices: Set<BluetoothDevice>? = null
    var mListPairedDevices: List<String>? = null
    private lateinit var listView: ListView

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

        listView = binding.mainTakerDlConnectLv

        binding.mainTakerDlOnBtn.setOnClickListener {
            btnOn()
        }

        binding.mainTakerDlOffBtn.setOnClickListener {
            btnOff()
        }

        binding.mainTakerDlConnectBtn.setOnClickListener {
            connectList()
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

                // BLUETOOTH_CONNECT 권한 확인
                if (ContextCompat.checkSelfPermission(
                        context,
                        Manifest.permission.BLUETOOTH_CONNECT
                    ) == PackageManager.PERMISSION_GRANTED
                ) {
                    // 블루투스가 꺼져 있으면 켜기
                    val enableBluetoothIntent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
                    activity.startActivityForResult(
                        enableBluetoothIntent,
                        REQUEST_BLUETOOTH_ADMIN
                    )
                } else {
                    // BLUETOOTH_CONNECT 권한이 없는 경우 요청
                    ActivityCompat.requestPermissions(
                        activity as AppCompatActivity,
                        arrayOf(Manifest.permission.BLUETOOTH_CONNECT),
                        YOUR_PERMISSION_REQUEST_CODE
                    )
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
                if (ContextCompat.checkSelfPermission(
                        context,
                        Manifest.permission.BLUETOOTH_ADMIN
                    ) == PackageManager.PERMISSION_GRANTED
                ) {
                    // 블루투스가 켜져 있으면 블루투스 비활성화
                    mBluetoothAdapter.disable()
                } else {
                    // BLUETOOTH_ADMIN 권한이 없는 경우 요청
                    ActivityCompat.requestPermissions(
                        activity as AppCompatActivity,
                        arrayOf(Manifest.permission.BLUETOOTH_ADMIN),
                        REQUEST_BLUETOOTH_ADMIN
                    )
                }
            } else {
                Toast.makeText(context, "블루투스가 이미 꺼져 있습니다.", Toast.LENGTH_SHORT).show()
            }

            // 블루투스 비활성화 작업이 완료된 후에 다이얼로그를 닫음
            dismiss()
        }
    }

    fun btnConnect() {
        if (mBluetoothAdapter == null) {
            Toast.makeText(context, "블루투스가 지원되지 않는 기기입니다.", Toast.LENGTH_SHORT).show()
        } else {
            if (mBluetoothAdapter.isEnabled) {
                // BLUETOOTH_ADMIN 권한 확인
                if (ContextCompat.checkSelfPermission(
                        context,
                        Manifest.permission.BLUETOOTH_ADMIN
                    ) == PackageManager.PERMISSION_GRANTED
                ) {
                    // 블루투스 검색 가능 상태로 변경
                    val discoveryIntent = Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE)
                    activity.startActivityForResult(discoveryIntent, REQUEST_BLUETOOTH_ADMIN)

                    // 주변 기기 목록을 리스트뷰에 출력
                    connectList()
                } else {
                    // BLUETOOTH_ADMIN 권한이 없는 경우 요청
                    ActivityCompat.requestPermissions(
                        activity,
                        arrayOf(Manifest.permission.BLUETOOTH_ADMIN),
                        REQUEST_BLUETOOTH_ADMIN
                    )
                }
            } else {
                Toast.makeText(context, "블루투스가 꺼져 있습니다. 먼저 블루투스를 켜주세요.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun connectList() {
        // Check Bluetooth permissions
        if (ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.BLUETOOTH
            ) == PackageManager.PERMISSION_GRANTED &&
            ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.BLUETOOTH_ADMIN
            ) == PackageManager.PERMISSION_GRANTED &&
            ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED &&
            ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            // Bluetooth permissions are granted
            if (mBluetoothAdapter == null) {
                Toast.makeText(context, "블루투스가 지원되지 않는 기기입니다.", Toast.LENGTH_SHORT).show()
                return
            }

            if (!mBluetoothAdapter.isEnabled) {
                Toast.makeText(context, "블루투스를 먼저 켜주세요.", Toast.LENGTH_SHORT).show()
                return
            }

            // Get paired devices
            val pairedDevices = mBluetoothAdapter.bondedDevices

            if (pairedDevices.isNullOrEmpty()) {
                Toast.makeText(context, "페어링된 기기가 없습니다.", Toast.LENGTH_SHORT).show()
                return
            }

            val deviceNames = mPairedDevices?.map { device -> device.name ?: "Unknown Device" } ?: emptyList()

            val adapter = ArrayAdapter(context, R.layout.item_bluetooth, R.id.dl_bluetooth_device_tv, deviceNames)

            // Set the adapter to the ListView
            listView.adapter = adapter

            // Set a click listener for list items
            listView.setOnItemClickListener { _, _, position, _ ->
                val selectedDevice = mPairedDevices?.elementAt(position)
                // TODO: Implement Bluetooth connection logic with the selected device
                Toast.makeText(context, "Connecting to ${selectedDevice?.name}...", Toast.LENGTH_SHORT).show()
                dismiss()
            }

        } else {
            // Request Bluetooth permissions
            ActivityCompat.requestPermissions(
                context as AppCompatActivity,
                arrayOf(
                    Manifest.permission.BLUETOOTH,
                    Manifest.permission.BLUETOOTH_ADMIN,
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ),
                BLUETOOTH_PERMISSION_REQUEST_CODE
            )
        }
    }

    companion object {
        private const val YOUR_PERMISSION_REQUEST_CODE = 123
    }
}