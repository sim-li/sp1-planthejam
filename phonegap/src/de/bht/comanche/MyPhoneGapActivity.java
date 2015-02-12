package de.bht.comanche;

import com.phonegap.*;
import android.os.Bundle;

public class MyPhoneGapActivity extends DroidGap {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		super.loadUrl("http://192.168.56.102");
	}
}
