package uz.agr.agrsdksample;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import uz.agr.agrsdk.AGRBilling;
import uz.agr.agrsdk.interfaces.IAGRBillingHandler;

public class MainActivity extends AppCompatActivity implements IAGRBillingHandler {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final EditText vendorIDtext = (EditText) findViewById(R.id.vendorIDtext);

        final EditText accountIDtext = (EditText) findViewById(R.id.accountIDtext);

        final EditText amountText = (EditText) findViewById(R.id.amountText);

        Button doneBtn = (Button) findViewById(R.id.doneBtn);
        doneBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (vendorIDtext.getText().toString().equals(""))
                    Toast.makeText(MainActivity.this, "Enter vendor ID", Toast.LENGTH_SHORT).show();
                else if (accountIDtext.getText().toString().equals(""))
                    Toast.makeText(MainActivity.this, "Enter account ID", Toast.LENGTH_SHORT).show();
                else {
                    makePay(Integer.parseInt(vendorIDtext.getText().toString()), accountIDtext.getText().toString(), amountText.getText().toString());
                }
            }
        });

    }

    private void makePay(int vendorID, String accountID, String amount) {
        AGRBilling agr = new AGRBilling();

        if (amount.equals("")) {
            agr.preparePayment(this,
                    vendorID,
                    accountID);
        } else {
            agr.preparePayment(this,
                    vendorID,
                    accountID,
                    Integer.parseInt(amount));
        }
    }

    @Override
    public void onAGRPaymentSuccess() {
        Toast.makeText(this, "onAGRPaymentSuccess", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onAGRPaymentFailed(String errorMessage) {
        Toast.makeText(this, "onAGRPaymentFailed " + errorMessage, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onAGRPaymentCanceled() {
        Toast.makeText(this, "onAGRPaymentCanceled", Toast.LENGTH_SHORT).show();
    }

    @Override
    public Activity getActivity() {
        return this;
    }
}
