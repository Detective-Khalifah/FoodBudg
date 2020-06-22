package com.example.android.foodbudg;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Serializable;


public class MainActivity extends AppCompatActivity /*implements AdapterView.OnItemSelectedListener */ {

    static int cokeVal1, fantaVal1, milkVal1, gQuantity = 0, yQuantity = 0;
    private static String message1, name, dept, fac;
    static boolean coke, fanta, milk, sauce, veggies;
    static double gPrice = 0.0, yPrice = 0.0;
    CheckBox cokeBox, fantaBox, milkBox, topping2_Box, sauce_box;

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toast.makeText(this, "Language translation exists, but was simply removed for the fun of it. Enjoy!", Toast.LENGTH_LONG).show();

        cokeBox = findViewById(R.id.coke_box);
        fantaBox = findViewById(R.id.fanta_box);
        milkBox = findViewById(R.id.kunu_box);

        topping2_Box = findViewById(R.id.topping2Box);
        sauce_box = findViewById(R.id.sauce_box);

//        //BEVERAGE COUNT
//        //String pos = beve1.getItemAtPosition(position).toString();
        Spinner beve1 = findViewById(R.id.coke_spin);
        beve1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected (AdapterView<?> parent, View view, int position, long id) {
                cokeVal1 = new Integer(Integer.parseInt(parent.getItemAtPosition(position).toString()));
                if (cokeVal1 >= 1) {
                    coke = true;
                    cokeBox.setChecked(true);
                } else {
                    coke = false;
                    cokeBox.setChecked(false);
                }
            }

            @Override
            public void onNothingSelected (AdapterView<?> parent) {
            }
        });

        Spinner beve2 = findViewById(R.id.fanta_spin);
        beve2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected (AdapterView<?> parent, View view, int position, long id) {
                fantaVal1 = new Integer(Integer.parseInt(parent.getItemAtPosition(position).toString()));
                if (fantaVal1 >= 1) {
                    fanta = true;
                    fantaBox.setChecked(true);
                } else {
                    fanta = false;
                    fantaBox.setChecked(false);
                }
            }

            @Override
            public void onNothingSelected (AdapterView<?> parent) {
            }
        });

        Spinner beve3 = findViewById(R.id.milk_spin);
        beve3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected (AdapterView<?> parent, View view, int position, long id) {
                milkVal1 = new Integer(Integer.parseInt(parent.getItemAtPosition(position).toString()));
                if (milkVal1 > 0) {
                    milk = true;
                    milkBox.setChecked(true);
                } else {
                    milk = false;
                    milkBox.setChecked(false);
                }
            }

            @Override
            public void onNothingSelected (AdapterView<?> parent) {
            }
        });

        // Find the View that shows the order summary
        Button orderSummary = findViewById(R.id.order_summary_b);

        final EditText clientName = findViewById(R.id.client_name);
        final EditText deptET = findViewById(R.id.dept);
        final EditText facultyET = findViewById(R.id.faculty);


        // Set a click listener on the order summary Button View
        orderSummary.setOnClickListener(new View.OnClickListener() {
            // The code in this function/method/section will be executed when the order summary button is clicked on.

            @Override
            public void onClick (View view) {
                try {
                    //get editText data
                    if (clientName.getText() != null)
                        name = clientName.getText().toString();
                    else {
                        clientName.setHintTextColor(Color.RED);
                        clientName.setHint("Enter your name!");
                    }

                    if (deptET.getText() != null)
                        dept = deptET.getText().toString();
                    else {
                        deptET.setHintTextColor(Color.RED);
                        deptET.setHint("What is your department?!");
                    }

                    if (facultyET.getText() != null)
                        fac = facultyET.getText().toString();
                    else {
                        facultyET.setHintTextColor(Color.RED);
                        facultyET.setHint("And faculty?!");
                    }

                    try {
                        message1 = name + " of " + dept + " department in the " + fac + " faculty. " + getString(R.string.order_summary_text) + "\n";
                        StringBuilder mssg1 = new StringBuilder(message1);
                        mssg1.append((gQuantity >= 3 ? getString(R.string.fast_food_1) + ": ₦" + gPrice + "\n" : "\n") + (yQuantity >= 1 ? getString(R.string.fast_food_2) + ": ₦" + yPrice + "\n" : "\n"));

                        // Create a new intent to open the {@link FamilyActivity}
                        Intent orderSIntent = new Intent(MainActivity.this, OrderActivity.class);
                        orderSIntent.putExtra("summary", (Serializable) mssg1);
                        Log.v("MainActivity", "Checkboxes: " + coke + fanta + milk + veggies + sauce);

                        // Start the order activity
                        startActivity(orderSIntent);
                    } catch (NullPointerException npe1b) {
                        Log.v(MainActivity.class.getName(), "Can't start OrderActivity");
                    }
                } catch (NullPointerException npe) {
                    //
                }

            }

        });

    }

    // Checkboxes checking/testing
    public void box (View view) {
        // Is the view now checked?
        boolean checked = ((CheckBox) view).isChecked();

        // Check which checkbox was clicked and its current state
        switch (view.getId()) {
            case R.id.kunu_box:
                milk = checked;
                break;
            case R.id.coke_box:
                coke = checked;
                break;
            case R.id.fanta_box:
                fanta = checked;
                break;
        }
    }

    public void incrementGurasa (View view) {
        // check clicked button and tickbox, then compute accordingly
        if (topping2_Box.isChecked()) {
            if (gQuantity == 30) {
                Toast.makeText(this, getString(R.string.max_gurasa), Toast.LENGTH_SHORT).show();
                return;
            }
            gQuantity += 3;
            gPrice = (gQuantity / 3) * 60;
        } else {
            if (gQuantity == 30) {
                Toast.makeText(this, getString(R.string.max_gurasa), Toast.LENGTH_SHORT).show();
                return;
            }
            gQuantity += 3;
            gPrice = (gQuantity / 3) * 50;
        }
        displayG(gPrice, gQuantity);
    }

    public void decrementGurasa (View view) {
        if (topping2_Box.isChecked()) {
            if (gQuantity == 0) {
                Toast.makeText(this, getString(R.string.min_gurasa), Toast.LENGTH_SHORT).show();
                return;
            }
            gQuantity -= 3;
            gPrice = (gQuantity / 3) * 60;
        } else {
            if (gQuantity == 0) {
                Toast.makeText(this, getString(R.string.min_gurasa), Toast.LENGTH_SHORT).show();
                return;
            }
            gQuantity -= 3;
            gPrice = (gQuantity / 3) * 50;
        }
        displayG(gPrice, gQuantity);
    }

    public void incrementYam (View view) {
        if (sauce_box.isChecked()) {
            if (yQuantity >= 10) {
                Toast.makeText(this, getString(R.string.max_yam), Toast.LENGTH_LONG).show();
                return;
            }
            yQuantity++;
            yPrice = yQuantity * 100;
        } else {
            if (yQuantity >= 10) {
                Toast.makeText(this, getString(R.string.max_yam), Toast.LENGTH_LONG).show();
                return;
            }
            yQuantity++;
            yPrice = yQuantity * 80;
        }
        displayY(yPrice, yQuantity);
    }

    public void decrementYam (View view) {
        if (sauce_box.isChecked()) {
            if (yQuantity == 0) {
                Toast.makeText(this, getString(R.string.min_yam), Toast.LENGTH_LONG).show();
                return;
            }
            yQuantity--;
            yPrice = yQuantity * 100;
        } else {
            if (yQuantity == 0) {
                Toast.makeText(this, getString(R.string.min_yam), Toast.LENGTH_LONG).show();
                return;
            }
            yQuantity--;
            yPrice = yQuantity * 80;
        }
        displayY(yPrice, yQuantity);
    }

    public void displayG (double gP, int gQ) {
        TextView myGuOrder = findViewById(R.id.gQuantity);
        myGuOrder.setText("" + gQ + " @₦" + gP);
    }

    public void displayY (double yP, int yQ) {
        TextView myYSOrder = findViewById(R.id.yQuantity);
        myYSOrder.setText(yQ + "( @₦ " + yP + ")");
    }


}

/*
* *** AMATEUR ***

//        String message = (R.string.initial_plates_of_yam).toString;
//        message += yQ;
//        message += "(₦ " + yP + ")";
//        String message = (, %);

//    public void makeItHappen(){
//        //check orders here:
//        //convert to singleton later
//        Log.v("MainActivity", "Total: " + total);
//        String message1 = name + " of " + dept + " in the " + fac + " faculty. " + R.string.order_summary_text;
//        message1 += "\n" + R.string.fast_food_1 + ":  ₦" + gPrice;
//        message1 += "\n" + R.string.fast_food_2 + ": ₦" + yPrice;
//        message1 += "\n" + R.string.beve_3 + ": ₦" + cokeVal1 + "\n" + R.string.beve_2 + ": ₦" + fantaVal1 + "\n" + R.string.beve_1 + ": ₦" + milkVal1;
//        message1 += "\nTotal: " + total;
//        OrderActivity orderInfo = new OrderActivity();
//        orderInfo.orderExec(message1);
////        orderInfo.orderExec(gPrice, gQuantity, yPrice, yQuantity, cokeVal1, fantaVal1, milkVal1, total, name, dept, fac);
//    }
*/