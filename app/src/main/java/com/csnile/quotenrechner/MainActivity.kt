package com.csnile.quotenrechner

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.DigitsKeyListener
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import java.math.BigDecimal
import java.math.RoundingMode
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.text.NumberFormat
import java.util.*
import kotlin.math.*
import org.apache.commons.lang3.StringUtils

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        val f = NumberFormat.getInstance(Locale.getDefault())
        if (f is DecimalFormat) {
            (f as DecimalFormat).isDecimalSeparatorAlwaysShown = true
        }

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



        val einsatz = findViewById(R.id.einsatz) as EditText
        val quote = findViewById(R.id.quote) as EditText
        val ausgabe = findViewById(R.id.ausgabe) as TextView
        val button = findViewById(R.id.button) as Button
        val zero = "0"
        val zerozero = "00"
        val string2 = "."

        val separator: Char = DecimalFormatSymbols.getInstance().getDecimalSeparator()


        einsatz.setKeyListener(DigitsKeyListener.getInstance("0123456789.," + separator));
        quote.setKeyListener(DigitsKeyListener.getInstance("0123456789.," + separator));
      //  BigDecimal(einsatz.toString().toDouble()).setScale(2,  BigDecimal.ROUND_HALF_UP).toDouble()

        val count = string2.count{ einsatz.toString().contains("..") }
        val count2 = string2.count{ quote.toString().contains("..") }

        fun Double.round(decimals: Int): Double {
            var multiplier = 1.0
            repeat(decimals) { multiplier *= 10 }
            return round(this * multiplier) / multiplier
        }

        button.setOnClickListener {


            if (einsatz.text.toString().isNullOrEmpty() || quote.text.toString().isNullOrEmpty() || StringUtils.countMatches(einsatz.text.toString().replace(',', '.'), ".") >1 || StringUtils.countMatches(quote.text.toString().replace(',', '.'), ".") >1) {

                var val1: Double = 0.0
                var val2: Double = 0.0
                ausgabe.setText("Eingabe \nUngültig")
            } else {

                var val1 = einsatz.text.toString().replace(',', '.').toDouble()
                var val2 = quote.text.toString().replace(',', '.').toDouble()

                var gewinn = val1 * val2;
                var gewinn2 = gewinn.toString().toDouble().round(2)


                while ("%.2f".format(gewinn2).toString().endsWith(zerozero) == false) {

                    val1 = val1 + 0.01;

                    gewinn = val1 * val2;
                    gewinn2 = gewinn.toString().toDouble().round(2)

                }

                ausgabe.setText("%.2f".format(val1).toString() + "€ Einsatz\n" + "%.0f".format(gewinn2).toString() + "€ Gewinn")
            }
        }
        //button.setBackgroundColor(Color.WHITE);
    }
}