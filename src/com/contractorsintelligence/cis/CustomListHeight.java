package com.contractorsintelligence.cis;

import android.app.ActionBar.LayoutParams;
import android.util.Log;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;

public class CustomListHeight {
	static int addheight = 0;

	public static void setListViewHeightBasedOnChildren(ListView listView) {
		ListAdapter listAdapter = listView.getAdapter();
		if (listAdapter == null)
			return;

		int desiredWidth = MeasureSpec.makeMeasureSpec(listView.getWidth(),
				MeasureSpec.UNSPECIFIED);
		int totalHeight = 0;
		View view = null;
		for (int i = 0; i < listAdapter.getCount(); i++) {
			view = listAdapter.getView(i, view, listView);
			if (i == 0)
				view.setLayoutParams(new ViewGroup.LayoutParams(desiredWidth,
						LayoutParams.WRAP_CONTENT));

			view.measure(desiredWidth, MeasureSpec.UNSPECIFIED);
			totalHeight += view.getMeasuredHeight();
		}
		Log.e("total H", "H:-" + totalHeight);
		ViewGroup.LayoutParams params = listView.getLayoutParams();
		if (totalHeight > 350 && totalHeight < 450) {

			addheight = 280;
			Log.e("In H" + addheight, "H In1:-" + addheight);
		} else if (totalHeight > 400 && totalHeight < 600) {

			addheight = 480;
			Log.e("In H" + addheight, "H In2:-" + addheight);
		} else if (totalHeight > 1000 && totalHeight < 3000) {
			addheight = 520;
			Log.e("In H" + addheight, "H In3:-" + addheight);
		}
		params.height = totalHeight
				+ (listView.getDividerHeight() * (listAdapter.getCount() - 1));
		listView.setLayoutParams(params);
		listView.requestLayout();
	}

}
