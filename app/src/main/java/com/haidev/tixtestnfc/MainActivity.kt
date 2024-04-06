package com.haidev.tixtestnfc

import android.app.PendingIntent
import android.content.Intent
import android.content.IntentFilter
import android.nfc.NfcAdapter
import android.nfc.tech.NfcF
import android.os.Bundle
import android.provider.Settings
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    private var intentFiltersArray: Array<IntentFilter>? = null
    private val techListsArray = arrayOf(arrayOf(NfcF::class.java.name))
    private val nfcAdapter: NfcAdapter? by lazy {
        NfcAdapter.getDefaultAdapter(this)
    }
    private var pendingIntent: PendingIntent? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        //Check if NFC is available on device
        checkNFC()
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
                val builder = AlertDialog.Builder(this@MainActivity, R.style.Base_Theme_TixTestNFC)
                builder.setMessage("This device doesn't support NFC.")
                builder.setPositiveButton("Cancel") { _, _ -> finish() }
                val myDialog = builder.create()
                myDialog.setCanceledOnTouchOutside(false)
                myDialog.show()

            } else if (!nfcAdapter!!.isEnabled) {
                val builder = AlertDialog.Builder(this@MainActivity, R.style.Base_Theme_TixTestNFC)
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
}