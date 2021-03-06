package com.example.bu111calculator;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class FutureValueSingle extends Activity {
	
	Button btnCalc;
	EditText initial;
	EditText interest;
	EditText compPeriod;
	EditText years;
	TextView answer;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_future_value_single);
		
		initial = (EditText) findViewById(R.id.fvSingGiven);
		interest = (EditText) findViewById(R.id.fvSingInterest);
		compPeriod = (EditText) findViewById(R.id.fvSingCompPer);
		years = (EditText) findViewById(R.id.fvSingYears);

		btnCalc = (Button) findViewById(R.id.btnFvSingCalc);
		answer = (TextView) findViewById(R.id.fvSingleAns);

		btnCalc.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Double finalAnswer;
				double numInitial = (Double) parseEditText(initial, "double");
				double numInterest = (Double) parseEditText(interest, "double");
				int numCompPeriod = (Integer) parseEditText(compPeriod, "int");
				int numYears = (Integer) parseEditText(years, "int");

				try {
					System.out.println(numInitial);
					System.out.println(numInterest);
					System.out.println(numCompPeriod);
					System.out.println(numYears);
					int multFactor = 12 / numCompPeriod;
					finalAnswer = numInitial * Math.pow(1 + numInterest / multFactor, numYears * multFactor);
					answer.setText(finalAnswer.toString());
				} catch (ArithmeticException e) {
					answer.setText("Please Enter Valid Values");
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
				return (double) 0;
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
