/**
 * IMPORTANT: Make sure you are using the correct package name.
 * This example uses the package name:
 * package com.example.android.justjava
 * If you get an error when copying this code into Android studio, update it to match teh package name found
 * in the project's AndroidManifest.xml file.
 **/

package com.exemplo.android.justjava;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;


/**
 * This app displays an order form to order coffee.
 */

public class MainActivity extends AppCompatActivity {
    private int quantity = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        CheckBox whippedCreamCheckBox = findViewById(R.id.whipped_cream_checkbox);
        CheckBox chocolateCheckBox = findViewById(R.id.chocolate_checkbox);
        EditText nameEditText = findViewById(R.id.name_edit_text);

        boolean hasWhippedCream = whippedCreamCheckBox.isChecked();
        boolean hasChocolate = chocolateCheckBox.isChecked();
        String name = nameEditText.getText().toString();


        int price = calculatePrice(hasWhippedCream, hasChocolate);

        String message = createOrderSumarry(price, hasWhippedCream, hasChocolate, name);

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); //Apenas apps de email pode manipular isso
        intent.putExtra(Intent.EXTRA_SUBJECT, getResources().getString(R.string.order_summary_email_subject));
        intent.putExtra(Intent.EXTRA_EMAIL, "ph123rm@gmail.com");
        intent.putExtra(Intent.EXTRA_TEXT, message);

        if (intent.resolveActivity( getPackageManager() ) != null){
            startActivity(intent);
        }


    }

    private int calculatePrice(boolean hasWhippedCream, boolean hasChocolate) {
        int coffeePrice = 5;
        int price = 0;

        if (hasWhippedCream) {
            coffeePrice += 1;
        }

        if (hasChocolate) {
            coffeePrice += 2;
        }

        price = quantity * coffeePrice;

        return price;
    }

    public void incrementQuantity(View view) {
        if (quantity >= 100) {
            Toast
                    .makeText(
                            this,
                            "A quantidade não pode ser maior que 100",
                            Toast.LENGTH_LONG
                    ).show();

            return;
        }

        quantity += 1;
        displayQuantity(quantity);
    }

    public void decrementQuantity(View view) {
        if (quantity <= 1) {
            Toast
                    .makeText(
                            this,
                            "A Quantidade não pode ser menor que 1",
                            Toast.LENGTH_LONG
                    )
                    .show();

            return;
        }

        quantity -= 1;

        displayQuantity(quantity);
    }

    public String createOrderSumarry(int number, boolean hasWhippedCream, boolean hasChocolate, String name) {

        String message = getString(R.string.order_summary_name, name);
        String numberFormat = NumberFormat.getCurrencyInstance().format(number);
        message += "\n" + getString(R.string.order_summary_whipped_cream, hasWhippedCream);
        message += "\n" + getString(R.string.order_summary_chocolate, hasChocolate);
        message += "\n" + getString(R.string.order_summary_quantity, quantity);
        message += "\n" + getString(R.string.order_summary_price, numberFormat);
        message += "\n" + getString(R.string.thank_you);

        return message;
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void displayQuantity(int number) {
        TextView quantityTextView = findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }



}
