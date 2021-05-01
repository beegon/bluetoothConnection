package com.example.bluetoothkotlin

import android.app.Activity
import android.bluetooth.BluetoothAdapter
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private val REQUEST_CODE_ENABLE_BT: Int = 1;

    //bluetooth adapter
    lateinit var bAdapter: BluetoothAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //init bluetooth adapter
        bAdapter = BluetoothAdapter.getDefaultAdapter()
        //check if bluetooth is available or not
        if (bAdapter == null) {
            bluetoothStatusTv.text = "Bluetooth is not available"
        } else {
            bluetoothStatusTv.text = "Bluetooth is available"
        }

        //Set image according to whether bluetooth is on or off
        if (bAdapter.isEnabled) {
            //bluetooth is on
            bluetoothIv.setImageResource(R.drawable.ic_bluetooth_on)
        } else {
            //bluetooth is off
            bluetoothIv.setImageResource(R.drawable.ic_bluetooth_off)
        }
        //turn on bluetooth
        turnOnBtn.setOnClickListener {
            if (bAdapter.isEnabled) {
                //already enabled
                Toast.makeText(this, "Already on", Toast.LENGTH_LONG).show()
            } else {
                val intent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
                startActivityForResult(intent, REQUEST_CODE_ENABLE_BT);
            }
        }
        //turn off bluetooth
        turnOffBtn.setOnClickListener {
            if (!bAdapter.isEnabled) {
                //already enabled
                Toast.makeText(this, "Already off", Toast.LENGTH_LONG).show()
            } else {
                bAdapter.disable()
                bluetoothIv.setImageResource(R.drawable.ic_bluetooth_off)
                Toast.makeText(this, "Bluetooth turned off", Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when (requestCode) {
            REQUEST_CODE_ENABLE_BT ->
                if (resultCode == Activity.RESULT_OK) {
                    bluetoothIv.setImageResource(R.drawable.ic_bluetooth_on)
                    Toast.makeText(this, "Bluetooth is on", Toast.LENGTH_LONG).show()
                }else {
                    //User denied to turn on bluetooth from confirmation dialogue
                    Toast.makeText(this, "Could not turn on", Toast.LENGTH_LONG).show()
                }
        }
        super.onActivityResult(requestCode, resultCode, data)
        }
}
