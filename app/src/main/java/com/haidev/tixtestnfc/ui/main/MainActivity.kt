package com.haidev.tixtestnfc.ui.main

import android.app.PendingIntent
import android.content.Intent
import android.content.IntentFilter
import android.nfc.NfcAdapter
import android.nfc.Tag
import android.nfc.tech.MifareClassic
import android.nfc.tech.Ndef
import android.nfc.tech.NfcF
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.haidev.tixtestnfc.R
import com.haidev.tixtestnfc.data.local.entity.NFCEntity
import com.haidev.tixtestnfc.databinding.ActivityMainBinding
import com.haidev.tixtestnfc.ui.main.adapter.ItemNFCAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val mainViewModel: MainViewModel by viewModels<MainViewModel>()

    private var intentFiltersArray: Array<IntentFilter>? = null
    private val techListsArray = arrayOf(arrayOf(NfcF::class.java.name))
    private val nfcAdapter: NfcAdapter? by lazy {
        NfcAdapter.getDefaultAdapter(this)
    }
    private var pendingIntent: PendingIntent? = null

    private lateinit var itemNFCAdapter: ItemNFCAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initUI()

        //Check if NFC is available on device
        checkNFC()
    }

    private fun initUI() {
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view: View = binding.getRoot()
        setContentView(view)

        binding.btnSave.setOnClickListener {
            mainViewModel.insertNFC(
                NFCEntity(
                    serialNumber = binding.etSerialNumber.text.toString(),
                    message = binding.etMessage.text.toString()
                )
            )
        }

        binding.rvNfcMessages.apply {
            layoutManager =
                LinearLayoutManager(this@MainActivity, LinearLayoutManager.VERTICAL, false)
            setHasFixedSize(true)
            itemNFCAdapter = ItemNFCAdapter()
            adapter = itemNFCAdapter
        }

        mainViewModel.getAllNFC().observe(this) {
            itemNFCAdapter.setData(it)
        }
    }

    private fun checkNFC() {
        try {
            //nfc process start
            pendingIntent =
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.S) PendingIntent.getActivity(
                    this,
                    0,
                    Intent(this, javaClass).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP),
                    PendingIntent.FLAG_MUTABLE
                )
                else
                    PendingIntent.getActivity(
                        this,
                        0,
                        Intent(this, javaClass).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP),
                        PendingIntent.FLAG_ONE_SHOT or PendingIntent.FLAG_IMMUTABLE
                    )

            intentFiltersArray = arrayOf(
                IntentFilter(NfcAdapter.ACTION_TAG_DISCOVERED)
            )

            if (nfcAdapter == null) {
                val builder = AlertDialog.Builder(this@MainActivity, R.style.MyAlertDialogStyle)
                builder.setMessage("This device doesn't support NFC.")
                builder.setPositiveButton("Cancel") { _, _ -> finish() }
                val myDialog = builder.create()
                myDialog.setCanceledOnTouchOutside(false)
                myDialog.show()

            } else if (!nfcAdapter!!.isEnabled) {
                val builder = AlertDialog.Builder(this@MainActivity, R.style.MyAlertDialogStyle)
                builder.setTitle("NFC Disabled")
                builder.setMessage("Plesae Enable NFC")

                builder.setPositiveButton("Settings") { _, _ -> startActivity(Intent(Settings.ACTION_NFC_SETTINGS)) }
                builder.setNegativeButton("Cancel") { _, _ -> finish() }
                val myDialog = builder.create()
                myDialog.setCanceledOnTouchOutside(false)
                myDialog.show()
            }
        } catch (ex: Exception) {
            Toast.makeText(applicationContext, ex.message, Toast.LENGTH_SHORT).show()
        }
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)

        val action = intent?.action
        if (action == NfcAdapter.ACTION_TAG_DISCOVERED) {
            val tag = if (android.os.Build.VERSION.SDK_INT >= 33) intent.getParcelableExtra(
                NfcAdapter.EXTRA_TAG,
                Tag::class.java
            ) else intent.getParcelableExtra<Tag>(NfcAdapter.EXTRA_TAG) ?: return

            //Read the tech list of the tag
            val techList = tag?.techList
            if (techList?.isEmpty() == true) return
            if (techList != null) {
                for (tech in techList) {
                    when (tech) {
                        //Check if the tag is Mifare Classic
                        MifareClassic::class.java.name -> {
                            val mfc = MifareClassic.get(tag)
                            val uid = mfc.tag.id
                            val serialNumber =
                                uid.joinToString(":") { byte -> String.format("%02X", byte) }
                            binding.etSerialNumber.setText(serialNumber)
                            binding.etMessage.setText("")
                            binding.etMessage.isEnabled = true
                            binding.btnSave.isEnabled = true
                        }
                        //Check if the tag contains data NDEF
                        Ndef::class.java.name -> {
                            val ndef = Ndef.get(tag)
                            val message = ndef.cachedNdefMessage
                            val record = message.records.first()
                            val text = String(record.payload)
                            binding.etMessage.setText(text.drop(3))
                        }
                        //Check if the tag is not recognized
                        else -> {
                            Log.d("NFC", "Tag not recognized")
                        }
                    }
                }
            }
        } else {
            Toast.makeText(this, "Tag not recognized", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onResume() {
        super.onResume()
        nfcAdapter?.enableForegroundDispatch(
            this,
            pendingIntent,
            intentFiltersArray,
            techListsArray
        )
    }

    override fun onPause() {
        if (this.isFinishing) {
            nfcAdapter?.disableForegroundDispatch(this)
        }
        super.onPause()
    }
}