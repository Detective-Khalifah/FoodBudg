package com.example.android.foodbudg;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

//import static com.example.android.foodbudg.MainActivity.total;

public class OrderActivity extends AppCompatActivity {

    static boolean coke = MainActivity.coke, fanta = MainActivity.fanta, milk = MainActivity.milk;
    static String message2;
    static double totalCost = 0.0;
    int /*cokeVal1,*/ cTC, /* fantaVal2 = MainActivity.fantaVal1,*/
            fTC, /* milkVal2 = MainActivity.milkVal1,*/
            mTC;

    @Override
    protected void onCreate(Bundle savedInstanceState) throws NumberFormatException {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_summary);

        // collect order summary message from MainActivity
        Bundle bundle = getIntent().getExtras();
        StringBuilder message3 = new StringBuilder(bundle.getString("summary"));

        TextView deets = findViewById(R.id.order_summary_deets);

        if (coke) {
            cTC = com.example.android.foodbudg.MainActivity.cokeVal1 * 100;
            totalCost += cTC;
            message3.append(getString(R.string.beve_3) + ": ₦" + cTC + "\n");
        } else {
            message3.append("\n");
        }

        if (fanta) {
            fTC = MainActivity.fantaVal1 * 100;
            totalCost += fTC;
            message3.append(getString(R.string.beve_2) + ": ₦" + fTC + "\n");
        } else {
            message3.append("\n");
        }

        if (milk) {
            mTC = MainActivity.milkVal1 * 100;
            totalCost += mTC;
            message3.append(getString(R.string.beve_1) + ": ₦" + mTC + "\n");
        } else {
            message3.append("\n");
        }
// ADD TOTAL OF G & Y TO OVERALL TOTAL COST
        totalCost += MainActivity.gPrice + MainActivity.yPrice;

        message3.append("\nTotal: ₦" + totalCost);
        message2 = message3.toString();


        deets.setText(message2);


        //if order is to get changed
        Button changeOrder = findViewById(R.id.change);
        changeOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.coke = false; MainActivity.cokeVal1 = 0;
                MainActivity.fanta = false; MainActivity.fantaVal1 = 0;
                MainActivity.milk = false; MainActivity.milkVal1 = 0;

                MainActivity.veggies = false; MainActivity.gPrice = 0; MainActivity.gQuantity = 0;
                MainActivity.sauce = false; MainActivity.yPrice = 0; MainActivity.yQuantity = 0;

                totalCost = 0.0;

                // TODO: Find a way to start Main Activity afresh at this point of the app usage/interaction
                // Create a new intent to open the {@link OrderActivity}
                Intent mainIntent = new Intent(OrderActivity.this, MainActivity.class);
                // Start the new activity
                startActivity(mainIntent);
            }
        });

    }

    public void submitToApp(View view) {
        Toast.makeText(this, "Processing order...", Toast.LENGTH_SHORT).show();
        //      WHATSAPP METHOD
        Intent delivIntent = new Intent();
        delivIntent.setAction(Intent.ACTION_SEND);
        delivIntent.putExtra(Intent.EXTRA_TEXT, message2);
        delivIntent.setType("text/plain");
        startActivity(delivIntent);
    }
}