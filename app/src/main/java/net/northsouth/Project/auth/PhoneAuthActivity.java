package net.northsouth.Project.auth;


/*
 *
 * Samiur Rahman Prapon
 * Created at 03/01/2018
 *
 */

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.firebase.auth.FirebaseAuth;

import net.northsouth.Project.MainActivity;
import net.northsouth.Project.R;


public class PhoneAuthActivity extends AppCompatActivity {

    private Spinner mSpinner;
    private EditText mEditText;
    private Button mButtn;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_auth);

        // set country code from CountryCode Class
        mSpinner = findViewById(R.id.countryCode);
        mSpinner.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, CountryCode.countryNames));

        mEditText = findViewById(R.id.phoneET);
        mButtn = findViewById(R.id.sendOTP);

        progressDialog = new ProgressDialog(this);

        mButtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // to collect the country codes from array and set on mspinner
                String code = CountryCode.countryAreaCodes[mSpinner.getSelectedItemPosition()];

                // collect user inputed phone number
                String number = mEditText.getText().toString().trim();

                // verify user input have correct values
                if (number.isEmpty() || number.length() < 10) {
                    mEditText.setError("Please type your mobile number without Zero");
                    mEditText.requestFocus();
                    return;
                }

                // full form phone number with country code
                String phoneNumber = "+" + code + number;

                // send user to verify otp Activity
                Intent intent = new Intent(PhoneAuthActivity.this, AuthVerificationActivity.class);
                intent.putExtra("phoneNumber", phoneNumber);



                PhoneAuthActivity.this.startActivity(intent);

            }
        });

    }

    private void spendTime() {
        int progress;

        for (progress = 10; progress <= 100; progress += 10) {
            try {
                Thread.sleep(200);

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        progressDialog.setTitle("Checking...");
        progressDialog.show();

        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            Intent intent = new Intent(this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

            progressDialog.dismiss();
            startActivity(intent);
        }
        progressDialog.dismiss();
    }


}
