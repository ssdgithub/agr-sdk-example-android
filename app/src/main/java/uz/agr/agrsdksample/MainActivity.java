package uz.agr.agrsdksample;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import uz.agr.agrsdk.AGRBilling;
import uz.agr.agrsdk.interfaces.IAGRBillingHandler;

public class MainActivity extends AppCompatActivity implements IAGRBillingHandler {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final EditText vendorIDtext = (EditText) findViewById(R.id.vendorIDtext);

        final EditText vendorSecretkey = (EditText) findViewById(R.id.vendorSecretKeyText);

        final EditText accountIDtext = (EditText) findViewById(R.id.accountIDtext);

        final EditText amountText = (EditText) findViewById(R.id.amountText);

        final CheckBox transDataCheck = (CheckBox) findViewById(R.id.transDataCheck);

        Button doneBtn = (Button) findViewById(R.id.doneBtn);
        doneBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (vendorIDtext.getText().toString().equals(""))
                    Toast.makeText(MainActivity.this, "Enter vendor ID", Toast.LENGTH_SHORT).show();
                else if (vendorSecretkey.getText().toString().equals(""))
                    Toast.makeText(MainActivity.this, "Enter vendor secret key", Toast.LENGTH_SHORT).show();
                else if (accountIDtext.getText().toString().equals(""))
                    Toast.makeText(MainActivity.this, "Enter account ID", Toast.LENGTH_SHORT).show();
                else {
                    makePay(Integer.parseInt(vendorIDtext.getText().toString()), vendorSecretkey.getText().toString(), accountIDtext.getText().toString(), amountText.getText().toString(), transDataCheck.isChecked());
                }
            }
        });

    }

    private void makePay(int vendorID, String vendorSecretKey, String accountID, String amount, Boolean sendTransData) {
        AGRBilling agr = new AGRBilling();

        JSONObject jsonObject = new JSONObject();

        if(sendTransData)
        {
            try {
                jsonObject.put("testField", "testValue");
            } catch (JSONException e) {
                e.printStackTrace();
                jsonObject = null;
            }
        }

        if (amount.equals("")) {
            if(!sendTransData) {
                agr.preparePayment(this,
                        vendorID,
                        vendorSecretKey,
                        accountID);
            } else {
                agr.preparePayment(this,
                        vendorID,
                        vendorSecretKey,
                        accountID,
                        jsonObject);
            }
        } else {
            if(!sendTransData) {
                agr.preparePayment(this,
                        vendorID,
                        vendorSecretKey,
                        accountID,
                        Integer.parseInt(amount));
            } else {
                agr.preparePayment(this,
                        vendorID,
                        vendorSecretKey,
                        accountID,
                        Integer.parseInt(amount),
                        jsonObject);
            }
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
