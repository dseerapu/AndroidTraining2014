package com.pcs.fragmentsexample;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class FragmentiOS extends Fragment {
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		/***
		 * Returns the layout of iosFragment
		 */
		return inflater.inflate(R.layout.fragment_ios, container, false);
	}
}
