package com.example.android.foodbudg

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.android.foodbudg.databinding.OrderSummaryBinding

//import static com.example.android.foodbudg.MainActivity.total;
class OrderActivity : AppCompatActivity() {

    private val tag = this::class.simpleName

    private lateinit var binding: OrderSummaryBinding

    var   /*cokeVal1,*/cTC = 0

    /* fantaVal2 = MainActivity.fantaVal1,*/
    var fTC = 0

    /* milkVal2 = MainActivity.milkVal1,*/
    var mTC = 0

    @Throws(NumberFormatException::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = OrderSummaryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // collect order summary message from MainActivity
        val bundle = intent.extras
        val message3 = bundle!!.getString("summary")?.let { StringBuilder(it) }

        if (coke) {
            cTC = MainActivity.cokeVal1 * 100
            totalCost += cTC.toDouble()
            message3?.append(
                """
    ${getString(R.string.beve_3)}: ₦$cTC
    
    """.trimIndent()
            )
        } else {
            message3?.append("\n")
        }
        if (fanta) {
            fTC = MainActivity.fantaVal1 * 100
            totalCost += fTC.toDouble()
            message3?.append(
                """
    ${getString(R.string.beve_2)}: ₦$fTC
    
    """.trimIndent()
            )
        } else {
            message3?.append("\n")
        }
        if (milk) {
            mTC = MainActivity.milkVal1 * 100
            totalCost += mTC.toDouble()
            message3?.append(
                """
    ${getString(R.string.beve_1)}: ₦$mTC
    
    """.trimIndent()
            )
        } else {
            message3?.append("\n")
        }
        // ADD TOTAL OF G & Y TO OVERALL TOTAL COST
        totalCost += MainActivity.gPrice + MainActivity.yPrice
        message3?.append(
            """
    
    Total: ₦$totalCost
    """.trimIndent()
        )
        message2 = message3.toString()
        binding.orderSummaryDeets.text = message2


        // if order is to get changed
        binding.change.setOnClickListener {
            MainActivity.coke = false
            MainActivity.cokeVal1 = 0
            MainActivity.fanta = false
            MainActivity.fantaVal1 = 0
            MainActivity.milk = false
            MainActivity.milkVal1 = 0
            MainActivity.veggies = false
            MainActivity.gPrice = 0.0
            MainActivity.gQuantity = 0
            MainActivity.sauce = false
            MainActivity.yPrice = 0.0
            MainActivity.yQuantity = 0
            totalCost = 0.0

            // TODO: Find a way to start Main Activity afresh at this point of the app usage/interaction
            // Create a new intent to open the {@link OrderActivity}
            val mainIntent = Intent(this@OrderActivity, MainActivity::class.java)
            // Start the new activity
            startActivity(mainIntent)
        }
    }

    fun submitToApp(view: View?) {
        Toast.makeText(this, "Processing order...", Toast.LENGTH_SHORT).show()
        //      WHATSAPP METHOD
        val delivIntent = Intent()
        delivIntent.action = Intent.ACTION_SEND
        delivIntent.putExtra(Intent.EXTRA_TEXT, message2)
        delivIntent.type = "text/plain"
        startActivity(delivIntent)
    }

    companion object {
        var coke = MainActivity.coke
        var fanta = MainActivity.fanta
        var milk = MainActivity.milk
        var message2: String? = null
        var totalCost = 0.0
    }
}