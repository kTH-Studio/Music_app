package ru.kartofan.theme.music.app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.*;
import android.app.*;
import android.os.*;
import android.view.*;
import android.widget.*;
import android.content.*;
import android.text.*;
import android.util.*;
import java.util.*;
import java.util.ArrayList;
import java.util.HashMap;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ListView;
import android.widget.BaseAdapter;
import android.content.Intent;
import android.net.Uri;
import android.graphics.Typeface;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.FirebaseApp;
import com.google.firebase.appcheck.FirebaseAppCheck;
import com.google.firebase.appcheck.playintegrity.PlayIntegrityAppCheckProviderFactory;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.bumptech.glide.Glide;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.android.gms.tasks.OnCompleteListener;

public class MainActivity extends AppCompatActivity {
	private LinearLayout mini_player, linear1;
	private ImageView player_image, player_explicit, play;
	private TextView player_name, player_artist, textview1, textview2, textview4, textview5, textview6, toolbar_title, toolbar_subtitle;
	private final FirebaseDatabase _firebase = FirebaseDatabase.getInstance();
	private String str, language, site, latest_version, description, link, package_name = "";
	private final String current_version = "2.2";
	private final String error = "";
	private HashMap < String, Object > update = new HashMap < > ();
	private ArrayList < HashMap < String, Object >> map, map1 = new ArrayList < > ();
	private ListView listview1;
	private SharedPreferences sp;
	private final Intent i = new Intent();
	private final Intent upd = new Intent();
	private final Intent settings = new Intent();
	private final DatabaseReference user = _firebase.getReference("users");
	private final DatabaseReference Ver = _firebase.getReference("version");
	private ChildEventListener _Ver_child_listener;
	private FirebaseAuth fauth;
	private OnCompleteListener<Void> fauth_updateEmailListener;
	private OnCompleteListener<Void> fauth_updatePasswordListener;
	private OnCompleteListener<Void> fauth_emailVerificationSentListener;
	private OnCompleteListener<Void> fauth_deleteUserListener;
	private OnCompleteListener<Void> fauth_updateProfileListener;
	private OnCompleteListener<AuthResult> fauth_phoneAuthListener;
	private OnCompleteListener<AuthResult> fauth_googleSignInListener;
	private OnCompleteListener<AuthResult> _fauth_create_user_listener;
	private OnCompleteListener<AuthResult> _fauth_sign_in_listener;
	private OnCompleteListener<Void> _fauth_reset_password_listener;
	private OnCompleteListener cm_onCompleteListener;

	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);
		setContentView(R.layout.main);
		initialize();
		FirebaseApp.initializeApp(this);
		FirebaseAppCheck firebaseAppCheck = FirebaseAppCheck.getInstance();
		firebaseAppCheck.installAppCheckProviderFactory(PlayIntegrityAppCheckProviderFactory.getInstance());
		com.google.firebase.FirebaseApp.initializeApp(this);
		initializeLogic();
	}

	@Override
	public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
		super.onRequestPermissionsResult(requestCode, permissions, grantResults);
		if (requestCode == 1000) {
			initializeLogic();
		}
	}

	private void initialize() {
		mini_player = findViewById(R.id.mini_player);
		player_image = findViewById(R.id.player_image);
		player_name = findViewById(R.id.player_name);
		player_artist = findViewById(R.id.player_artist);
		play = findViewById(R.id.play);
		linear1 = findViewById(R.id.linear1);
		textview1 = findViewById(R.id.textview1);
		listview1 = findViewById(R.id.listview1);
		textview2 = findViewById(R.id.textview2);
		textview5 = findViewById(R.id.textview5);
		toolbar_title = findViewById(R.id.toolbar_title);
		toolbar_subtitle = findViewById(R.id.toolbar_subtitle);
		sp = getSharedPreferences("sp", Activity.MODE_PRIVATE);
		sp = getSharedPreferences("sp", Activity.MODE_PRIVATE);
		if (sp.getString("language", "").equals("")) {
			sp.edit().putString("language", "default").apply();}
		if (sp.getString("theme", "").equals("")) {
			sp.edit().putString("theme", "default").apply();}
		if (sp.getString("explicit", "").equals("")) {
			sp.edit().putString("explicit", "no").apply();}
		if (sp.getString("animation", "").equals("")) {
			sp.edit().putString("animation", "no").apply();}
		if (sp.getString("quality", "").equals("")) {
			sp.edit().putString("quality", "no").apply();}
		if (sp.getString("player", "").equals("")) {
			sp.edit().putString("player", "no").apply();}
		fauth = FirebaseAuth.getInstance();

		fauth_updateEmailListener = _param1 -> {
			final boolean _success = _param1.isSuccessful();
			final String _errorMessage = _param1.getException() != null ? _param1.getException().getMessage() : "";
		};

		fauth_updatePasswordListener = _param1 -> {
			final boolean _success = _param1.isSuccessful();
			final String _errorMessage = _param1.getException() != null ? _param1.getException().getMessage() : "";
		};

		fauth_emailVerificationSentListener = _param1 -> {
			final boolean _success = _param1.isSuccessful();
			final String _errorMessage = _param1.getException() != null ? _param1.getException().getMessage() : "";
		};

		fauth_deleteUserListener = _param1 -> {
			final boolean _success = _param1.isSuccessful();
			final String _errorMessage = _param1.getException() != null ? _param1.getException().getMessage() : "";
		};

		fauth_phoneAuthListener = task -> {
			final boolean _success = task.isSuccessful();
			final String _errorMessage = task.getException() != null ? task.getException().getMessage() : "";
		};

		fauth_updateProfileListener = _param1 -> {
			final boolean _success = _param1.isSuccessful();
			final String _errorMessage = _param1.getException() != null ? _param1.getException().getMessage() : "";
		};

		fauth_googleSignInListener = task -> {
			final boolean _success = task.isSuccessful();
			final String _errorMessage = task.getException() != null ? task.getException().getMessage() : "";
		};

		_fauth_create_user_listener = _param1 -> {
			final boolean _success = _param1.isSuccessful();
			final String _errorMessage = _param1.getException() != null ? _param1.getException().getMessage() : "";
		};

		_fauth_sign_in_listener = _param1 -> {
			final boolean _success = _param1.isSuccessful();
			final String _errorMessage = _param1.getException() != null ? _param1.getException().getMessage() : "";
		};

		_fauth_reset_password_listener = _param1 -> {
			final boolean _success = _param1.isSuccessful();
		};

		listview1.setOnItemClickListener((_param1, _param2, _param3, _param4) -> {
			if (map.get(_param3).containsKey("link")) {
				i.setClass(getApplicationContext(), ArtistActivity.class);
				i.putExtra("link", map.get(_param3).get("link").toString());
				startActivity(i);
			} else {
				Snackbar.make(listview1, R.string.artist_will_be_added_soon, Snackbar.LENGTH_SHORT).setAction("Ok", _view -> {}).show();
			}
		});

		listview1.setOnItemLongClickListener((_param1, _param2, _param3, _param4) -> true);

		_Ver_child_listener = new ChildEventListener() {
			@Override
			public void onChildAdded(DataSnapshot _param1, String _param2) {
				_fire(_param1);
			}

			@Override
			public void onChildChanged(DataSnapshot _param1, String _param2) {
				_fire(_param1);
			}

			@Override
			public void onChildMoved(DataSnapshot _param1, String _param2) {}

			@Override
			public void onChildRemoved(DataSnapshot _param1) {
				GenericTypeIndicator < HashMap < String, Object >> _ind = new GenericTypeIndicator < HashMap < String, Object >> () {};
				final String _childKey = _param1.getKey();
				final HashMap < String, Object > _childValue = _param1.getValue(_ind);
			}

			@Override
			public void onCancelled(DatabaseError _param1) {
				final int _errorCode = _param1.getCode();
				final String _errorMessage = _param1.getMessage();
			}};
		Ver.addChildEventListener(_Ver_child_listener);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(@NonNull MenuItem item) {
		if (item.getItemId() == R.id.settings) {
			settings.setClass(getApplicationContext(), SettingsActivity.class);
			startActivity(settings);
		}
		return super.onOptionsItemSelected(item);
	}

	private void initializeLogic() {
		if (sp.getString("theme", "").equals("system")){
			AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
		} else if (sp.getString("theme", "").equals("battery")){
			AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_AUTO_BATTERY);
		} else if (sp.getString("theme", "").equals("dark")){
			AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
		} else if (sp.getString("theme", "").equals("light")){
			AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);}
		if ((FirebaseAuth.getInstance().getCurrentUser() != null)) {
		} else {
			i.setClass(getApplicationContext(), RegisterActivity.class);
			startActivity(i);
			finishAffinity();}
		package_name = "ru.kartofan.theme.music.app";
		Toolbar toolbar = findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);
		getSupportActionBar().setDisplayShowHomeEnabled(false);
		getSupportActionBar().setDisplayHomeAsUpEnabled(false);
		toolbar_title.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/moscow.ttf"), Typeface.NORMAL);
		toolbar_subtitle.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/moscow.ttf"), Typeface.NORMAL);
		DatabaseReference rootRef = _firebase.getReference();
		rootRef.child("version").addListenerForSingleValueEvent(new ValueEventListener() {
			@Override
			public void onDataChange(DataSnapshot snapshot) {
				if (snapshot.exists()) {} else {
					update = new HashMap < > ();
					update.put("version", current_version);
					update.put("description_en", "-");
					update.put("description_de", "-");
					update.put("description_ru", "-");
					update.put("link", "-");
					Ver.child("Music").updateChildren(update);
					update.clear();
				}
			}
			@Override
			public void onCancelled(DatabaseError _error) {}
		});
		_marquee(toolbar_title, getString(R.string.new_in_music));
		_marquee(toolbar_subtitle, getString(R.string.version));
		language = Locale.getDefault().getDisplayLanguage();
		if (language.equals("Deutsch")) {
			sp.edit().putString("prefix", "de").apply();
		} else if (language.equals("Русский") || language.equals("русский")) {
			sp.edit().putString("prefix", "ru").apply();
		} else {
			sp.edit().putString("prefix", "en").apply();}
		android.net.ConnectivityManager connMgr = (android.net.ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		android.net.NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
		if (networkInfo != null && networkInfo.isConnected()) {
			site = "https://kth-studio.github.io/Music/music.json";
			new BackTask().execute(site);
		} else {
			com.google.android.material.snackbar.Snackbar.make(listview1, R.string.internet_lost, Snackbar.LENGTH_INDEFINITE).setAction("Ok", _view -> {}).show();}
		listview1.setVisibility(View.INVISIBLE);
	}

	public void _marquee(final TextView _textview, final String _text) {
		_textview.setText(_text);
		_textview.setSingleLine(true);
		_textview.setEllipsize(TextUtils.TruncateAt.MARQUEE);
		_textview.setSelected(true);
	}

	public void _fire(DataSnapshot _param1) {
		GenericTypeIndicator < HashMap < String, Object >> _ind = new GenericTypeIndicator < HashMap < String, Object >> () {};
		final String _childKey = _param1.getKey();
		final HashMap < String, Object > _childValue = _param1.getValue(_ind);
		Ver.addListenerForSingleValueEvent(new ValueEventListener() {
			@Override
			public void onDataChange(DataSnapshot _dataSnapshot) {
				map1 = new ArrayList < > ();
				try {
					GenericTypeIndicator < HashMap < String, Object >> _ind = new GenericTypeIndicator < HashMap < String, Object >> () {};
					for (DataSnapshot _data: _dataSnapshot.getChildren()) {
						HashMap < String, Object > _map = _data.getValue(_ind);
						map1.add(_map);
					}
				} catch (Exception _e) {
					_e.printStackTrace();
				}
				latest_version = map1.get(0).get("version").toString();
				description = map1.get(0).get("description_".concat(sp.getString("prefix", ""))).toString();
				link = map1.get(0).get("link").toString();
				if (Double.parseDouble(latest_version) > Double.parseDouble(current_version)) {
					_bottom();
				} else {
					if (Double.parseDouble(current_version) > Double.parseDouble(latest_version)) {
						Ver.child("Music").child("version").setValue(current_version);
					}
				}
			}
			@Override
			public void onCancelled(DatabaseError _databaseError) {}
		});
	}

	public void _bottom(){
		com.google.android.material.snackbar.Snackbar.make(listview1, R.string.update, Snackbar.LENGTH_INDEFINITE).setAction(R.string.check_button, _view -> {
			final com.google.android.material.bottomsheet.BottomSheetDialog bs_base = new com.google.android.material.bottomsheet.BottomSheetDialog(MainActivity.this);
			bs_base.setCancelable(true);
			View layBase = getLayoutInflater().inflate(R.layout.update, null);
			bs_base.setContentView(layBase);
			Button Later = layBase.findViewById(R.id.later);
			Button Update = layBase.findViewById(R.id.update);
			Later.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/moscow.ttf"), Typeface.NORMAL);
			Update.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/moscow.ttf"), Typeface.NORMAL);
			Later.setOnClickListener(v -> bs_base.cancel());
			Update.setOnClickListener(v -> {
				upd.setAction(Intent.ACTION_VIEW);
				upd.setData(Uri.parse(link));
				startActivity(upd);
			});
			TextView text = layBase.findViewById(R.id.text);
			text.setText(description);
			text.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/moscow.ttf"), Typeface.NORMAL);
			TextView about = layBase.findViewById(R.id.current_version);
			about.setText(getString(R.string.current_version).concat(current_version));
			about.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/moscow.ttf"), Typeface.NORMAL);
			TextView latest = layBase.findViewById(R.id.latest_version);
			latest.setText(getString(R.string.latest_version).concat(latest_version));
			latest.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/moscow.ttf"), Typeface.NORMAL);
			bs_base.show();
		}).show();
	}

	private class BackTask extends AsyncTask < String, Integer, String > {
		@Override
		protected void onPreExecute() {}
		protected String doInBackground(String...address) {
			String output = "";
			try {
				java.net.URL url = new java.net.URL(address[0]);
				java.io.BufferedReader in = new java.io.BufferedReader(new java.io.InputStreamReader(url.openStream()));
				String line;
				while ((line = in.readLine()) != null) {
					output += line;
				}
				in.close();
			} catch (java.net.MalformedURLException e) {
				output = e.getMessage();
			} catch (java.io.IOException e) {
				output = e.getMessage();
			} catch (Exception e) {
				output = e.toString();
			}
			return output;
		}
		protected void onProgressUpdate(Integer...values) {}
		protected void onPostExecute(String s) {
			str = s;
			map = new Gson().fromJson(str, new TypeToken < ArrayList < HashMap < String, Object >>> () {}.getType());
			listview1.setAdapter(new Listview1Adapter(map));
			_ViewSetHeight(listview1, map.size() * kTHUtil.getDip(getApplicationContext(), 69));
			((BaseAdapter) listview1.getAdapter()).notifyDataSetChanged();
			listview1.setVisibility(View.VISIBLE);
		}
	}

	private void _ViewSetHeight(final View _view, final double _num) {
		_view.getLayoutParams().height = (int)(_num);
	}

	public class Listview1Adapter extends BaseAdapter {
		ArrayList < HashMap < String, Object >> _data;
		public Listview1Adapter(ArrayList < HashMap < String, Object >> _arr) {
			_data = _arr;
		}

		@Override
		public int getCount() {
			return _data.size();
		}

		@Override
		public HashMap < String, Object > getItem(int _index) {
			return _data.get(_index);
		}

		@Override
		public long getItemId(int _index) {
			return _index;
		}
		@Override
		public View getView(final int _position, View _v, ViewGroup _container) {
			LayoutInflater _inflater = (LayoutInflater) getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			View _view = _v;
			if (_view == null) {
				_view = _inflater.inflate(R.layout.artists, null);
			}
			final de.hdodenhof.circleimageview.CircleImageView imageview2 = _view.findViewById(R.id.imageview2);
			final TextView textview2 = _view.findViewById(R.id.textview2);
			final TextView textview3 = _view.findViewById(R.id.textview3);
			_marquee(textview2, map.get(_position).get("name").toString());
			_marquee(textview3, map.get(_position).get("info_".concat(sp.getString("prefix", ""))).toString());
			if (map.get(_position).containsKey("link")) {
				imageview2.setAlpha((float) 1);
				textview2.setAlpha((float) 1);
				textview3.setAlpha((float) 1);
			} else {
				imageview2.setAlpha((float) 0.5d);
				textview2.setAlpha((float) 0.5d);
				textview3.setAlpha((float) 0.5d);
			}
			if (map.get(_position).containsKey("image")) {
				Glide.with(getApplicationContext()).load(Uri.parse(map.get(_position).get("image").toString())).into(imageview2);
			}
			textview2.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/moscow.ttf"), Typeface.NORMAL);
			textview3.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/moscow.ttf"), Typeface.NORMAL);
			return _view;
		}
	}

	@Deprecated
	public void showMessage(String _s) {
		Toast.makeText(getApplicationContext(), _s, Toast.LENGTH_SHORT).show();
	}

	@Deprecated
	public int getLocationX(View _v) {
		int[] _location = new int[2];
		_v.getLocationInWindow(_location);
		return _location[0];
	}

	@Deprecated
	public int getLocationY(View _v) {
		int[] _location = new int[2];
		_v.getLocationInWindow(_location);
		return _location[1];
	}

	@Deprecated
	public int getRandom(int _min, int _max) {
		Random random = new Random();
		return random.nextInt(_max - _min + 1) + _min;
	}

	@Deprecated
	public ArrayList < Double > getCheckedItemPositionsToArray(ListView _list) {
		ArrayList < Double > _result = new ArrayList < Double > ();
		SparseBooleanArray _arr = _list.getCheckedItemPositions();
		for (int _iIdx = 0; _iIdx < _arr.size(); _iIdx++) {
			if (_arr.valueAt(_iIdx))
				_result.add((double) _arr.keyAt(_iIdx));
		}
		return _result;
	}

	@Deprecated
	public float getDip(int _input) {
		return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, _input, getResources().getDisplayMetrics());
	}

	@Deprecated
	public int getDisplayWidthPixels() {
		return getResources().getDisplayMetrics().widthPixels;
	}

	@Deprecated
	public int getDisplayHeightPixels() {
		return getResources().getDisplayMetrics().heightPixels;
	}
}