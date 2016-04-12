package com.maple.model.ui.fragment.menu;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.maple.model.R;

/**
 * 左侧菜单栏
 */
public class MenuLeftFragment extends Fragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.menu_layout_left, container, false);
	}
}
