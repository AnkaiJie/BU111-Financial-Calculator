package com.example.bu111calculator;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class PresentValueAnnuity extends Activity {

	Button btnCalc;
	EditText interest;
	EditText compPeriod;
	EditText payPeriod;
	TextView finalAnswer;
	TextView effFinalRate;
	RadioGroup varType;
	TableRow miss1;
	TableRow miss2;

	private int radioTracker;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_present_value_annuity);

		interest = (EditText) findViewById(R.id.pvAnnInterest);
		compPeriod = (EditText) findViewById(R.id.pvAnnCompPer);
		payPeriod = (EditText) findViewById(R.id.pvAnnPayPer);
		finalAnswer = (TextView) findViewById(R.id.finalAnswer);
		effFinalRate = (TextView) findViewById(R.id.effRate);

		btnCalc = (Button) findViewById(R.id.btnPvAnnCalc);
		varType = (RadioGroup) findViewById(R.id.varSelector);

		miss1 = (TableRow) findViewById(R.id.missing1);
		miss2 = (TableRow) findViewById(R.id.missing2);

		final LayoutInflater lay = (LayoutInflater) getApplicationContext()
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View v = lay.inflate(R.layout.pvpartials, null);

		EditText initialYrs = (EditText) v.findViewById(R.id.pvAnnYears);
		EditText initialPmt = (EditText) v.findViewById(R.id.pvAnnPMT);
		((ViewGroup) initialPmt.getParent()).removeAllViews();
		miss1.addView(initialPmt);
		miss2.addView(initialYrs);

		varType.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {

				View v = lay.inflate(R.layout.pvpartials, null);

				EditText missingPv = (EditText) v.findViewById(R.id.presentValueAnnuity);
				EditText missingYrs = (EditText) v.findViewById(R.id.pvAnnYears);
				EditText missingPmt = (EditText) v.findViewById(R.id.pvAnnPMT);
				((ViewGroup) missingPv.getParent()).removeAllViews();

				System.out.println(missingPv.getHint().toString());

				if (checkedId == R.id.radioSelectPv) {
					System.out.println("a");
					miss1.removeAllViews();
					miss2.removeAllViews();
					miss1.addView(missingPmt);
					miss2.addView(missingYrs);
					radioTracker = 1;
				} else if (checkedId == R.id.radioSelectPmt) {
					System.out.println("b");
					miss1.removeAllViews();
					miss2.removeAllViews();
					miss1.addView(missingPv);
					miss2.addView(missingYrs);
					radioTracker = 2;
				} else {
					System.out.println("c");
					miss1.removeAllViews();
					miss2.removeAllViews();
					miss1.addView(missingPmt);
					miss2.addView(missingPv);
					radioTracker = 3;
				}

			}

		});

		btnCalc.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Double numFinalAns;
				Double numInterest = (Double) parseEditText(interest, "double");
				Integer numCompPeriod = (Integer) parseEditText(compPeriod, "int");
				Double compFactor = 12.0 / numCompPeriod;
				Integer numPayPeriod = (Integer) parseEditText(payPeriod, "int");
				System.out.println(numInterest);
				System.out.println(numCompPeriod);
				System.out.println(compFactor);
				System.out.println(numPayPeriod);

				Double effRate = Math.pow(1 + numInterest / compFactor, compFactor / numPayPeriod) - 1;
				System.out.println(effRate);
				effFinalRate.setText("Effective Regular Rate: " + effRate.toString());

				Double numPmt = 0.0;
				Double numPv = 0.0;
				Integer numYrs = 0;
				if (radioTracker == 1 | radioTracker == 0) {
					numPmt = (Double) parseEditText((EditText) miss1.findViewById(R.id.pvAnnPMT), "double");
					numYrs = (Integer) parseEditText((EditText) miss2.findViewById(R.id.pvAnnYears), "int");
					Double pv = numPmt * (1 - Math.pow(1 + effRate, -numYrs * numPayPeriod)) / effRate;
					System.out.println(pv);

					finalAnswer.setText("Present Value: " + pv.toString());

				} else if (radioTracker == 2) {
					numPv = (Double) parseEditText((EditText) miss1.findViewById(R.id.presentValueAnnuity), "double");
					numYrs = (Integer) parseEditText((EditText) miss2.findViewById(R.id.pvAnnYears), "int");
					Double pmt = numPv * effRate / (1 - Math.pow(1 + effRate, -numYrs * numPayPeriod));
					finalAnswer.setText("Regular Payment Amount: " + pmt.toString());

				} else {
					numPmt = (Double) parseEditText((EditText) miss1.findViewById(R.id.pvAnnPMT), "double");
					numPv = (Double) parseEditText((EditText) miss2.findViewById(R.id.presentValueAnnuity), "double");
					Double yrs = Math.log(1 - numPv * effRate / numPmt) / (-numPayPeriod * Math.log(1 + effRate));
					finalAnswer.setText("Years Total: " + yrs.toString());
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