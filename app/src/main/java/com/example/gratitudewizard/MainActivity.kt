package com.example.gratitudewizard

import android.nfc.Tag
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.EditText
import android.widget.SeekBar
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.viewinterop.AndroidView
import com.example.gratitudewizard.ui.theme.GratitudeWizardTheme


private const val TAG = "MainActivity"
private const val initialTipPercent = 15
class MainActivity : ComponentActivity() {
    private lateinit var baseAmt: EditText
    private lateinit var seekBarTip: SeekBar
    private lateinit var tipPercentLabel: TextView
    private lateinit var tipAmt: TextView
    private lateinit var totalAmt: TextView



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        baseAmt = findViewById(R.id.baseAmt)
        seekBarTip = findViewById(R.id.seekBarTip)
        tipPercentLabel = findViewById(R.id.tipPercentLabel)
        tipAmt = findViewById(R.id.tipAmt)
        totalAmt = findViewById(R.id.totalAmt)

        seekBarTip.progress = initialTipPercent
        tipPercentLabel.text = "$initialTipPercent%"
        seekBarTip.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
                Log.i(TAG, "On Progress Changed $p1")
                tipPercentLabel.text = "$p1%"
                computeTipAndTotal()
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {}

            override fun onStopTrackingTouch(p0: SeekBar?) {}


        })

        baseAmt.addTextChangedListener(object: TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun afterTextChanged(p0: Editable?) {
                computeTipAndTotal()
            }

        })

        }

    private fun computeTipAndTotal() {
        if (baseAmt.text.isEmpty()) {
            tipAmt.text = ""
            totalAmt.text = ""
            return
        }
        val baseAmount = baseAmt.text.toString().toDouble()
        val tipPercent = seekBarTip.progress

        val tipAmount = (baseAmount*tipPercent) / 100
        val totalAmount = tipAmount + baseAmount

        tipAmt.text = "%.2f".format(tipAmount)
        totalAmt.text = "%.2f".format(totalAmount)
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    GratitudeWizardTheme {
        Greeting("Android")
    }
}