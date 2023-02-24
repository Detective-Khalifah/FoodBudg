package com.example.android.foodbudg

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.android.foodbudg.databinding.ActivityMainBinding
import java.io.Serializable

class MainActivity : AppCompatActivity() /*implements AdapterView.OnItemSelectedListener */ {

    private val tag = this::class.simpleName
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Toast.makeText(
            this,
            "Language translation exists, but was simply removed for the fun of it. Enjoy!",
            Toast.LENGTH_LONG
        ).show()

//        //BEVERAGE COUNT
//        //String pos = beve1.getItemAtPosition(position).toString();
        binding.cokeSpin.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View,
                position: Int,
                id: Long
            ) {
                cokeVal1 = parent.getItemAtPosition(position).toString().toInt()
                if (cokeVal1 >= 1) {
                    coke = true
                    binding.cokeBox.isChecked = true
                } else {
                    coke = false
                    binding.cokeBox.isChecked = false
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
        binding.fantaSpin.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View,
                position: Int,
                id: Long
            ) {
                fantaVal1 = parent.getItemAtPosition(position).toString().toInt()
                if (fantaVal1 >= 1) {
                    fanta = true
                    binding.fantaBox.isChecked = true
                } else {
                    fanta = false
                    binding.fantaBox.isChecked = false
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
        binding.milkSpin.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View,
                position: Int,
                id: Long
            ) {
                milkVal1 = parent.getItemAtPosition(position).toString().toInt()
                if (milkVal1 > 0) {
                    milk = true
                    binding.kunuBox.isChecked = true
                } else {
                    milk = false
                    binding.kunuBox.isChecked = false
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        // Set a click listener on the order summary Button View
        binding.orderSummaryB.setOnClickListener {
            try {
                //get editText data
                if (binding.clientName.text != null) name =
                    binding.clientName.text.toString() else {
                    binding.clientName.setHintTextColor(Color.RED)
                    binding.clientName.hint = "Enter your name!"
                }
                if (binding.dept.text != null) dept = binding.dept.text.toString() else {
                    binding.dept.setHintTextColor(Color.RED)
                    binding.dept.hint = "What is your department?!"
                }
                if (binding.faculty.text != null) fac = binding.faculty.text.toString() else {
                    binding.faculty.setHintTextColor(Color.RED)
                    binding.faculty.hint = "And faculty?!"
                }
                try {
                    message1 =
                        """$name of $dept department in the $fac faculty. ${getString(R.string.order_summary_text)}"""
                    val mssg1 = message1?.let { it1 -> StringBuilder(it1) }
                    mssg1?.append(
                        (if (gQuantity >= 3) """
                                     ${getString(R.string.fast_food_1)}: ₦$gPrice
                                     
                                     """.trimIndent() else "\n") + if (yQuantity >= 1) """
                                     ${getString(R.string.fast_food_2)}: ₦$yPrice
                                     
                                     """.trimIndent() else "\n"
                    )

                    // Create a new intent to open the {@link FamilyActivity}
                    val orderSIntent = Intent(this@MainActivity, OrderActivity::class.java)
                    orderSIntent.putExtra("summary", mssg1 as Serializable)
                    Log.v(tag, "Checkboxes: " + coke + fanta + milk + veggies + sauce)

                    // Start the order activity
                    startActivity(orderSIntent)
                } catch (npe1b: NullPointerException) {
                    Log.v(tag, "Can't start OrderActivity")
                }
            } catch (npe: NullPointerException) {
                //
            }
        }
    }

    // Checkboxes checking/testing
    fun box(view: View) {
        // Is the view now checked?
        val checked = (view as CheckBox).isChecked
        when (view.getId()) {
            R.id.kunu_box -> milk = checked
            R.id.coke_box -> coke = checked
            R.id.fanta_box -> fanta = checked
        }
    }

    fun incrementGurasa(view: View?) {
        // check clicked button and checkbox, then compute accordingly
        if (binding.topping2Box.isChecked) {
            if (gQuantity == 30) {
                Toast.makeText(this, getString(R.string.max_gurasa), Toast.LENGTH_SHORT).show()
                return
            }
            gQuantity += 3
            gPrice = (gQuantity / 3 * 60).toDouble()
        } else {
            if (gQuantity == 30) {
                Toast.makeText(this, getString(R.string.max_gurasa), Toast.LENGTH_SHORT).show()
                return
            }
            gQuantity += 3
            gPrice = (gQuantity / 3 * 50).toDouble()
        }
        displayG(gPrice, gQuantity)
    }

    fun decrementGurasa(view: View?) {
        if (binding.topping2Box.isChecked) {
            if (gQuantity == 0) {
                Toast.makeText(this, getString(R.string.min_gurasa), Toast.LENGTH_SHORT).show()
                return
            }
            gQuantity -= 3
            gPrice = (gQuantity / 3 * 60).toDouble()
        } else {
            if (gQuantity == 0) {
                Toast.makeText(this, getString(R.string.min_gurasa), Toast.LENGTH_SHORT).show()
                return
            }
            gQuantity -= 3
            gPrice = (gQuantity / 3 * 50).toDouble()
        }
        displayG(gPrice, gQuantity)
    }

    fun incrementYam(view: View?) {
        if (binding.sauceBox.isChecked) {
            if (yQuantity >= 10) {
                Toast.makeText(this, getString(R.string.max_yam), Toast.LENGTH_LONG).show()
                return
            }
            yQuantity++
            yPrice = (yQuantity * 100).toDouble()
        } else {
            if (yQuantity >= 10) {
                Toast.makeText(this, getString(R.string.max_yam), Toast.LENGTH_LONG).show()
                return
            }
            yQuantity++
            yPrice = (yQuantity * 80).toDouble()
        }
        displayY(yPrice, yQuantity)
    }

    fun decrementYam(view: View?) {
        if (binding.sauceBox.isChecked) {
            if (yQuantity == 0) {
                Toast.makeText(this, getString(R.string.min_yam), Toast.LENGTH_LONG).show()
                return
            }
            yQuantity--
            yPrice = (yQuantity * 100).toDouble()
        } else {
            if (yQuantity == 0) {
                Toast.makeText(this, getString(R.string.min_yam), Toast.LENGTH_LONG).show()
                return
            }
            yQuantity--
            yPrice = (yQuantity * 80).toDouble()
        }
        displayY(yPrice, yQuantity)
    }

    fun displayG(gP: Double, gQ: Int) {
        val myGuOrder = findViewById<TextView>(R.id.gQuantity)
        myGuOrder.text = "$gQ @₦$gP"
    }

    fun displayY(yP: Double, yQ: Int) {
        val myYSOrder = findViewById<TextView>(R.id.yQuantity)
        myYSOrder.text = "$yQ( @₦ $yP)"
    }

    companion object {
        @JvmField
        var cokeVal1 = 0

        @JvmField
        var fantaVal1 = 0

        @JvmField
        var milkVal1 = 0

        @JvmField
        var gQuantity = 0

        @JvmField
        var yQuantity = 0
        private var message1: String? = null
        private var name: String? = null
        private var dept: String? = null
        private var fac: String? = null

        @JvmField
        var coke = false

        @JvmField
        var fanta = false

        @JvmField
        var milk = false

        @JvmField
        var sauce = false

        @JvmField
        var veggies = false

        @JvmField
        var gPrice = 0.0

        @JvmField
        var yPrice = 0.0
    }
}