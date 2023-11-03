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
import android.app.AlertDialog;
import java.util.Timer;
import java.util.TimerTask;
import android.widget.AdapterView;
import android.graphics.Typeface;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.bumptech.glide.Glide;
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
import com.google.android.gms.tasks.Task;

public class MainActivity extends AppCompatActivity {

	private FirebaseDatabase _firebase = FirebaseDatabase.getInstance();
	private final Timer _timer = new Timer();
	private String str = "";
	private String language = "";
	private String site = "";
	private String latest_version = "";
	private String current_version = "1.28";
	private String description = "";
	private String link = "";
	private String package_name = "";
	private final String error = "";
	private HashMap < String, Object > update = new HashMap < > ();
	private ArrayList < HashMap < String, Object >> map = new ArrayList < > ();
	private ArrayList < HashMap < String, Object >> map1 = new ArrayList < > ();
	private LinearLayout linear1;
	private TextView textview1;
	private ListView listview1;
	private TextView textview2;
	private TextView textview4;
	private TextView textview5;
	private TextView textview6;
	private TextView toolbar_title;
	private TextView toolbar_subtitle;
	private SharedPreferences sp;
	private final Intent i = new Intent();
	private final Intent upd = new Intent();
	private final Intent settings = new Intent();
	private AlertDialog.Builder d;
	private TimerTask t;
	private DatabaseReference user = _firebase.getReference("users");
	private DatabaseReference Ver = _firebase.getReference("version");
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
		initialize(_savedInstanceState);
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

	private void initialize(Bundle _savedInstanceState) {
		linear1 = (LinearLayout) findViewById(R.id.linear1);
		textview1 = (TextView) findViewById(R.id.textview1);
		listview1 = (ListView) findViewById(R.id.listview1);
		textview2 = (TextView) findViewById(R.id.textview2);
		textview5 = (TextView) findViewById(R.id.textview5);
		toolbar_title = (TextView) findViewById(R.id.toolbar_title);
		toolbar_subtitle = (TextView) findViewById(R.id.toolbar_subtitle);
		d = new AlertDialog.Builder(this);
		sp = getSharedPreferences("sp", Activity.MODE_PRIVATE);
		if (sp.getString("language", "").equals("")) {
			sp.edit().putString("language", "default").commit();
		}
		if (sp.getString("theme", "").equals("")) {
			sp.edit().putString("theme", "default").commit();
		}
		if (sp.getString("restrictions", "").equals("")) {
			sp.edit().putString("restrictions", "no").commit();
		}
		if (sp.getString("explicit", "").equals("")) {
			sp.edit().putString("explicit", "no").commit();
		}
		if (sp.getString("animation", "").equals("")) {
			sp.edit().putString("animation", "no").commit();
		}
		if (sp.getString("quality", "").equals("")) {
			sp.edit().putString("quality", "no").commit();
		}

		fauth = FirebaseAuth.getInstance();

		fauth_updateEmailListener = new OnCompleteListener<Void>() {
			@Override
			public void onComplete(Task<Void> _param1) {
				final boolean _success = _param1.isSuccessful();
				final String _errorMessage = _param1.getException() != null ? _param1.getException().getMessage() : "";

			}
		};

		fauth_updatePasswordListener = new OnCompleteListener<Void>() {
			@Override
			public void onComplete(Task<Void> _param1) {
				final boolean _success = _param1.isSuccessful();
				final String _errorMessage = _param1.getException() != null ? _param1.getException().getMessage() : "";

			}
		};

		fauth_emailVerificationSentListener = new OnCompleteListener<Void>() {
			@Override
			public void onComplete(Task<Void> _param1) {
				final boolean _success = _param1.isSuccessful();
				final String _errorMessage = _param1.getException() != null ? _param1.getException().getMessage() : "";

			}
		};

		fauth_deleteUserListener = new OnCompleteListener<Void>() {
			@Override
			public void onComplete(Task<Void> _param1) {
				final boolean _success = _param1.isSuccessful();
				final String _errorMessage = _param1.getException() != null ? _param1.getException().getMessage() : "";

			}
		};

		fauth_phoneAuthListener = new OnCompleteListener<AuthResult>() {
			@Override
			public void onComplete(Task<AuthResult> task){
				final boolean _success = task.isSuccessful();
				final String _errorMessage = task.getException() != null ? task.getException().getMessage() : "";

			}
		};

		fauth_updateProfileListener = new OnCompleteListener<Void>() {
			@Override
			public void onComplete(Task<Void> _param1) {
				final boolean _success = _param1.isSuccessful();
				final String _errorMessage = _param1.getException() != null ? _param1.getException().getMessage() : "";

			}
		};

		fauth_googleSignInListener = new OnCompleteListener<AuthResult>() {
			@Override
			public void onComplete(Task<AuthResult> task){
				final boolean _success = task.isSuccessful();
				final String _errorMessage = task.getException() != null ? task.getException().getMessage() : "";

			}
		};

		_fauth_create_user_listener = new OnCompleteListener<AuthResult>() {
			@Override
			public void onComplete(Task<AuthResult> _param1) {
				final boolean _success = _param1.isSuccessful();
				final String _errorMessage = _param1.getException() != null ? _param1.getException().getMessage() : "";

			}
		};

		_fauth_sign_in_listener = new OnCompleteListener<AuthResult>() {
			@Override
			public void onComplete(Task<AuthResult> _param1) {
				final boolean _success = _param1.isSuccessful();
				final String _errorMessage = _param1.getException() != null ? _param1.getException().getMessage() : "";

			}
		};

		_fauth_reset_password_listener = new OnCompleteListener<Void>() {
			@Override
			public void onComplete(Task<Void> _param1) {
				final boolean _success = _param1.isSuccessful();

			}
		};

		listview1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView < ? > _param1, View _param2, int _param3, long _param4) {
				final int _position = _param3;
				if (map.get((int) _position).containsKey("link")) {
					i.setClass(getApplicationContext(), ArtistActivity.class);
					i.putExtra("link", map.get((int) _position).get("link").toString());
					startActivity(i);
				} else {
					com.google.android.material.snackbar.Snackbar.make(listview1, R.string.artist_will_be_added_soon, com.google.android.material.snackbar.Snackbar.LENGTH_SHORT).setAction("Ok", new View.OnClickListener() {
						@Override
						public void onClick(View _view) {}
					}).show();
				}
			}
		});

		listview1.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
			@Override
			public boolean onItemLongClick(AdapterView < ? > _param1, View _param2, int _param3, long _param4) {
				final int _position = _param3;
				return true;
			}
		});

		_Ver_child_listener = new ChildEventListener() {
			@Override
			public void onChildAdded(DataSnapshot _param1, String _param2) {
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
						latest_version = map1.get((int) 0).get("version").toString();
						description = map1.get((int) 0).get("description_".concat(sp.getString("prefix", ""))).toString();
						link = map1.get((int) 0).get("link").toString();
						if (Double.parseDouble(latest_version) > Double.parseDouble(current_version)) {
							com.google.android.material.snackbar.Snackbar.make(listview1, R.string.update, Snackbar.LENGTH_INDEFINITE).setAction(R.string.check_button, new View.OnClickListener() {
								@Override
								public void onClick(View _view) {
									final com.google.android.material.bottomsheet.BottomSheetDialog bs_base = new com.google.android.material.bottomsheet.BottomSheetDialog(MainActivity.this);
									bs_base.setCancelable(true);
									View layBase = getLayoutInflater().inflate(R.layout.update, null);
									bs_base.setContentView(layBase);
									Button Later = (Button) layBase.findViewById(R.id.later);
									Button Update = (Button) layBase.findViewById(R.id.update);
									Later.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/moscow.ttf"), Typeface.NORMAL);
									Update.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/moscow.ttf"), Typeface.NORMAL);
									Later.setOnClickListener(new View.OnClickListener() {
										@Override
										public void onClick(View v) {
											bs_base.cancel();
										}
									});
									Update.setOnClickListener(new View.OnClickListener() {
										@Override
										public void onClick(View v) {
											upd.setAction(Intent.ACTION_VIEW);
											upd.setData(Uri.parse(link));
											startActivity(upd);
										}
									});
									TextView text = (TextView) layBase.findViewById(R.id.text);
									text.setText(description);
									text.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/moscow.ttf"), Typeface.NORMAL);
									TextView about = (TextView) layBase.findViewById(R.id.current_version);
									about.setText(getString(R.string.current_version).concat(current_version));
									about.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/moscow.ttf"), Typeface.NORMAL);
									TextView latest = (TextView) layBase.findViewById(R.id.latest_version);
									latest.setText(getString(R.string.latest_version).concat(latest_version));
									latest.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/moscow.ttf"), Typeface.NORMAL);
									bs_base.show();
								}
							}).show();
						} else {
							if (Double.parseDouble(current_version) > Double.parseDouble(latest_version)) {
								Ver.child("Music").child("version").setValue(current_version);
							} else {

							}
						}
					}
					@Override
					public void onCancelled(DatabaseError _databaseError) {}
				});
			}

			@Override
			public void onChildChanged(DataSnapshot _param1, String _param2) {
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
						latest_version = map1.get((int) 0).get("version").toString();
						description = map1.get((int) 0).get("description_".concat(sp.getString("prefix", ""))).toString();
						link = map1.get((int) 0).get("link").toString();
						if (Double.parseDouble(latest_version) > Double.parseDouble(current_version)) {
							com.google.android.material.snackbar.Snackbar.make(listview1, R.string.update, Snackbar.LENGTH_INDEFINITE).setAction(R.string.check_button, new View.OnClickListener() {
								@Override
								public void onClick(View _view) {
									final com.google.android.material.bottomsheet.BottomSheetDialog bs_base = new com.google.android.material.bottomsheet.BottomSheetDialog(MainActivity.this);
									bs_base.setCancelable(true);
									View layBase = getLayoutInflater().inflate(R.layout.update, null);
									bs_base.setContentView(layBase);
									Button Later = (Button) layBase.findViewById(R.id.later);
									Button Update = (Button) layBase.findViewById(R.id.update);
									Later.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/moscow.ttf"), Typeface.NORMAL);
									Update.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/moscow.ttf"), Typeface.NORMAL);
									Later.setOnClickListener(new View.OnClickListener() {
										@Override
										public void onClick(View v) {
											bs_base.cancel();
										}
									});
									Update.setOnClickListener(new View.OnClickListener() {
										@Override
										public void onClick(View v) {
											upd.setAction(Intent.ACTION_VIEW);
											upd.setData(Uri.parse(link));
											startActivity(upd);
										}
									});
									TextView text = (TextView) layBase.findViewById(R.id.text);
									text.setText(description);
									text.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/moscow.ttf"), Typeface.NORMAL);
									TextView about = (TextView) layBase.findViewById(R.id.current_version);
									about.setText(getString(R.string.current_version).concat(current_version));
									about.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/moscow.ttf"), Typeface.NORMAL);
									TextView latest = (TextView) layBase.findViewById(R.id.latest_version);
									latest.setText(getString(R.string.latest_version).concat(latest_version));
									latest.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/moscow.ttf"), Typeface.NORMAL);
									bs_base.show();
								}
							}).show();
						} else {
							if (Double.parseDouble(current_version) > Double.parseDouble(latest_version)) {
								Ver.child("Music").child("version").setValue(current_version);
							} else {

							}
						}
					}
					@Override
					public void onCancelled(DatabaseError _databaseError) {}
				});
			}

			@Override
			public void onChildMoved(DataSnapshot _param1, String _param2) {

			}

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

			}
		};
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
		if ((FirebaseAuth.getInstance().getCurrentUser() != null)) {
			//if () {

			//}
		} else {
			i.setClass(getApplicationContext(), RegisterActivity.class);
			startActivity(i);
			finishAffinity();
		}
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
			sp.edit().putString("prefix", "de").commit();
		} else if (language.equals("Pусский") || language.equals("русский")) {
			sp.edit().putString("prefix", "ru").commit();
		} else {
			sp.edit().putString("prefix", "en").commit();
		}
		android.net.ConnectivityManager connMgr = (android.net.ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		android.net.NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
		if (networkInfo != null && networkInfo.isConnected()) {
			site = "https://kth-studio.github.io/Music/music.json";
			new BackTask().execute(site);
		} else {
			com.google.android.material.snackbar.Snackbar.make(listview1, R.string.internet_lost, Snackbar.LENGTH_INDEFINITE).setAction("Ok", new View.OnClickListener() {
				@Override
				public void onClick(View _view) {}
			}).show();
		}
		listview1.setVisibility(View.INVISIBLE);
	}

	@Override
	protected void onActivityResult(int _requestCode, int _resultCode, Intent _data) {
		super.onActivityResult(_requestCode, _resultCode, _data);
		switch (_requestCode) {
			default:
				break;
		}
	}

	public void _marquee(final TextView _textview, final String _text) {
		_textview.setText(_text);
		_textview.setSingleLine(true);
		_textview.setEllipsize(TextUtils.TruncateAt.MARQUEE);
		_textview.setSelected(true);
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
			_ViewSetHeight(listview1, map.size() * SketchwareUtil.getDip(getApplicationContext(), (int)(69)));
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

			final de.hdodenhof.circleimageview.CircleImageView imageview2 = (de.hdodenhof.circleimageview.CircleImageView) _view.findViewById(R.id.imageview2);
			final TextView textview2 = (TextView) _view.findViewById(R.id.textview2);
			final TextView textview3 = (TextView) _view.findViewById(R.id.textview3);
			_marquee(textview2, map.get((int) _position).get("name").toString());
			_marquee(textview3, map.get((int) _position).get("info_".concat(sp.getString("prefix", ""))).toString());
			if (map.get((int)_position).containsKey("image")) {
				Glide.with(getApplicationContext()).load(Uri.parse(map.get((int) _position).get("image").toString())).into(imageview2);
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