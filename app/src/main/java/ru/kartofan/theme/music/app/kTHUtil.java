package ru.kartofan.theme.music.app;

import android.content.*;
import android.widget.*;
import android.util.*;
import java.util.*;
import android.view.inputmethod.*;

public class kTHUtil {

	public static void hideKeyboard(Context c) {
		InputMethodManager imm = (InputMethodManager)
				c.getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
	}

	public static ArrayList < Double > getCheckedItemPositionsToArray(ListView _list) {
		ArrayList < Double > _result = new ArrayList < Double > ();
		SparseBooleanArray _arr = _list.getCheckedItemPositions();
		for (int _iIdx = 0; _iIdx < _arr.size(); _iIdx++) {
			if (_arr.valueAt(_iIdx))
				_result.add((double) _arr.keyAt(_iIdx));
		}
		return _result;
	}

	public static float getDip(Context _context, int _input) {
		return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, _input, _context.getResources().getDisplayMetrics());
	}
}