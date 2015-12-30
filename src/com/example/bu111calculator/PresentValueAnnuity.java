package com.example.bu111calculator;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class PresentValueAnnuity extends Activity {

	Button btnCalc;
	EditText payment;
	EditText interest;
	EditText compPeriod;
	EditText years;
	EditText payPeriod;
	EditText presValAnn;
	TextView effAnnRate;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_present_value_annuity);

		payment = (EditText) findViewById(R.id.pvAnnPMT);
		interest = (EditText) findViewById(R.id.pvAnnInterest);
		compPeriod = (EditText) findViewById(R.id.pvAnnCompPer);
		years = (EditText) findViewById(R.id.pvAnnYears);
		payPeriod = (EditText) findViewById(R.id.pvAnnPayPer);
		presValAnn = (EditText) findViewById(R.id.presentValueAnnuity);

		btnCalc = (Button) findViewById(R.id.btnPvAnnCalc);
		effAnnRate = (TextView) findViewById(R.id.pvAnnEffRate);

		btnCalc.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Double finalAnswer;
				Double pmt = (Double) parseEditText(payment, "double");
				Double numInterest = (Double) parseEditText(interest, "double");
				Double presVal = (Double) parseEditText(presValAnn, "double");
				Integer numCompPeriod = (Integer) parseEditText(compPeriod, "int");
				Integer numYears = (Integer) parseEditText(years, "int");
				Integer numPayPeriod = (Integer) parseEditText(payPeriod, "int");

				ArrayList<Double> doubleValues = new ArrayList<Double>();
				ArrayList<Integer> intValues = new ArrayList<Integer>();
				doubleValues.add(pmt);
				doubleValues.add(numInterest);
				doubleValues.add(presVal);
				intValues.add(numCompPeriod);
				intValues.add(numYears);
				intValues.add(numPayPeriod);

				int zeroCounter = 0;

				for (int i = 0; i < doubleValues.size(); i++) {
					if (doubleValues.get(i) == 0.0) {
						zeroCounter++;
					}
				}
				for (int i = 0; i < intValues.size(); i++) {
					if (intValues.get(i) == 0) {
						zeroCounter++;
					}
				}

				System.out.println(zeroCounter);
				if (zeroCounter > 1) {
					effAnnRate.setText("Please Enter Valid Values");
				} else {

					double compFactor = 12 / numCompPeriod;

					if (presVal == 0) {
						// System.out.println(compFactor);
						// System.out.println(numInterest);
						// System.out.println(numPayPeriod);
						// System.out.println(Math.pow(1 + numInterest /
						// compFactor, compFactor / numPayPeriod));
						Double rate = Math.pow(1 + numInterest / compFactor, compFactor / numPayPeriod) - 1;

						presVal = pmt * (1 - Math.pow(1 + rate, -numYears * numPayPeriod)) / rate;

						effAnnRate.setText("Effective Rate for payment period: " + rate.toString());
						presValAnn.setText(presVal.toString());

					} else if (pmt == 0) {
						Double rate = Math.pow(1 + numInterest / compFactor, compFactor / numPayPeriod) - 1;
						pmt = presVal / ((1 - Math.pow(1 + rate, -numYears * numPayPeriod)) / rate);

						effAnnRate.setText("Effective Rate for payment period: " + rate.toString());
						payment.setText(pmt.toString());

					} else if (numInterest == 0) {

					} else if (numYears == 0) {

					} else if (numPayPeriod == 0) {

					} else if (numCompPeriod == 0) {

					} else {
						effAnnRate.setText("Please leave one field blank");
					}

				}

			}
		});
	}

	Number parseEditText(EditText txt, String parseType) {
		if (parseType == "double") {
			try {
				double conv = Double.parseDouble(txt.getText().toString());
				return conv;
			} catch (NumberFormatException e) {
				return (Double) 0.0;
			}
		} else {
			try {
				int conv = Integer.parseInt(txt.getText().toString());
				return conv;
			} catch (NumberFormatException e) {
				return 0;
			}
		}

	}
}