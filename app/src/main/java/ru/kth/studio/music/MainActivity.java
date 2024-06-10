package ru.kth.studio.music;

import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.*;
import android.app.*;
import android.net.ConnectivityManager;
import android.os.*;
import android.util.TypedValue;
import android.view.*;
import android.widget.*;
import android.content.*;
import android.text.*;
import java.io.IOException;
import java.util.*;
import java.util.ArrayList;
import java.util.HashMap;
import android.widget.TextView;
import android.widget.ListView;
import android.widget.BaseAdapter;
import android.content.Intent;
import android.net.Uri;
import android.graphics.Typeface;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.bumptech.glide.Glide;
import androidx.appcompat.app.AppCompatDelegate;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ChildEventListener;

public class MainActivity extends AppCompatActivity {
	private TextView name, artist, album;
	private ImageView back, more;
	private final FirebaseDatabase _firebase = FirebaseDatabase.getInstance();
	private String site;
	private String latest_version;
	private String latest_code;
	private String description;
	private String link;
	private final String current_version = "2.2", current_code = "1178";
	private ArrayList < HashMap < String, Object >> map, map1 = new ArrayList < > ();
	private ListView listview1;
	private SharedPreferences sp;
	private final Intent i = new Intent();
	private final Intent upd = new Intent();
	private final Intent settings = new Intent();
	private final DatabaseReference Ver = _firebase.getReference("version");
	public static String language;

	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);
		setContentView(R.layout.main);
		initialize();
		initializeLogic();}

	@Override
	public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
		super.onRequestPermissionsResult(requestCode, permissions, grantResults);
		if (requestCode == 1000) { initializeLogic();}}

	private void initialize() {
		back = findViewById(R.id.back);
		more = findViewById(R.id.more);
		listview1 = findViewById(R.id.listview1);
		name = findViewById(R.id.name);
		artist = findViewById(R.id.artist);
		album = findViewById(R.id.album);
		sp = getSharedPreferences("sp", Activity.MODE_PRIVATE);
		_check("language", "default");
		_check("theme", "default");
		_check("explicit", "no");
		_check("exp_song", "no");
		_check("animation", "no");
		_check("quality", "no");

		more.setOnClickListener(v -> {
            settings.setClass(getApplicationContext(), SettingsActivity.class);
            startActivity(settings);});

		listview1.setOnItemClickListener((_param1, _param2, _param3, _param4) -> {
			if (map.get(_param3).containsKey("link")) {
				android.net.ConnectivityManager connMgr = (android.net.ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
				android.net.NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
				if (networkInfo != null && networkInfo.isConnected()) {
				i.setClass(getApplicationContext(), ArtistActivity.class);
				i.putExtra("link", getString(R.string.site).concat(Objects.requireNonNull(map.get(_param3).get("url")).toString()).concat(getString(R.string.link_json)));
				startActivity(i);} else {Snackbar.make(listview1, R.string.internet_lost, Snackbar.LENGTH_INDEFINITE).setAction(R.string.retry, _view -> {_internet1(connMgr, _param3);}).show();}
			} else { Snackbar.make(listview1, R.string.artist_will_be_added_soon, Snackbar.LENGTH_SHORT).setAction("Ok", _view -> {}).show();}});

		ChildEventListener _Ver_child_listener = new ChildEventListener() {
			@Override
			public void onChildAdded(@NonNull DataSnapshot _param1, String _param2) {_fire();}

			@Override
			public void onChildChanged(@NonNull DataSnapshot _param1, String _param2) {_fire();}

			@Override
			public void onChildMoved(@NonNull DataSnapshot _param1, String _param2) {}

			@Override
			public void onChildRemoved(@NonNull DataSnapshot _param1) {}

			@Override
			public void onCancelled(@NonNull DatabaseError _param1) {}};
		Ver.addChildEventListener(_Ver_child_listener);}

	private void initializeLogic() {
		artist.setVisibility(View.GONE);
		album.setVisibility(View.GONE);
		back.setVisibility(View.INVISIBLE);
		more.setImageResource(R.drawable.ic_settings);
		android.net.ConnectivityManager connMgr = (android.net.ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		android.net.NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
		if (networkInfo != null && networkInfo.isConnected()) {
			site = getString(R.string.site).concat("Music").concat(getString(R.string.link_json));
			new BackTask().execute(site);
		} else { Snackbar.make(listview1, R.string.internet_lost, Snackbar.LENGTH_INDEFINITE).setAction(R.string.retry, _view -> { _internet(connMgr); }).show();}
		switch (sp.getString("theme", "")) {
			case "system": AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM); break;
			case "dark": AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES); break;
			case "light": AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO); break;}
		name.setText(R.string.app_name);
		typeface(name);
		language = Locale.getDefault().getDisplayLanguage();
		switch (language) {
			case "Deutsch": sp.edit().putString("prefix", "de").apply(); break;
			case "Русский": case "русский": sp.edit().putString("prefix", "ru").apply(); break;
			default: sp.edit().putString("prefix", "en").apply(); break;}
		listview1.setVisibility(View.INVISIBLE);}

	public void _fire() {
		Ver.addListenerForSingleValueEvent(new ValueEventListener() {
			@Override
			public void onDataChange(@NonNull DataSnapshot _dataSnapshot) {
				map1 = new ArrayList < > ();
				try {
					GenericTypeIndicator < HashMap < String, Object >> _ind = new GenericTypeIndicator < HashMap < String, Object >> () {};
					for (DataSnapshot _data: _dataSnapshot.getChildren()) {
						HashMap < String, Object > _map = _data.getValue(_ind);
						map1.add(_map);}
				} catch (Exception _e) { _e.printStackTrace();}
				latest_version = Objects.requireNonNull(map1.get(0).get("version")).toString();
				latest_code = Objects.requireNonNull(map1.get(0).get("code")).toString();
				description = Objects.requireNonNull(map1.get(0).get("description_".concat(sp.getString("prefix", "")))).toString();
				link = Objects.requireNonNull(map1.get(0).get("link")).toString();
				if (Double.parseDouble(latest_code) > Double.parseDouble(current_code)) { _bottom();
				} else { if (Double.parseDouble(current_code) > Double.parseDouble(latest_code)) {
					Ver.child("Music").child("code").setValue(current_code);
					Ver.child("Music").child("version").setValue(current_version);}}}

			@Override
			public void onCancelled(@NonNull DatabaseError _databaseError) {}});}

	public void _internet(ConnectivityManager connMgr) {
		android.net.NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
		if (networkInfo != null && networkInfo.isConnected()) {
			Snackbar.make(listview1, R.string.restored, Snackbar.LENGTH_SHORT).setAction("Ok", _view -> {}).show();
			site = getString(R.string.site).concat("Music").concat(getString(R.string.link_json));
			new BackTask().execute(site);
		} else { Snackbar.make(listview1, R.string.internet_lost, Snackbar.LENGTH_INDEFINITE).setAction(R.string.retry, _view -> { _internet(connMgr); }).show();}}

	public void _internet1(ConnectivityManager connMgr, int _param3) {
		android.net.NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
		if (networkInfo != null && networkInfo.isConnected()) {
			i.setClass(getApplicationContext(), ArtistActivity.class);
			i.putExtra("link", getString(R.string.site).concat(Objects.requireNonNull(map.get(_param3).get("url")).toString()).concat(getString(R.string.link_json)));
			startActivity(i);} else {Snackbar.make(listview1, R.string.internet_lost, Snackbar.LENGTH_INDEFINITE).setAction(R.string.retry, _view -> {_internet1(connMgr, _param3);}).show();}}

	public void _bottom(){
		Snackbar.make(listview1, R.string.update, Snackbar.LENGTH_INDEFINITE).setAction(R.string.check_button, _view -> {
			final com.google.android.material.bottomsheet.BottomSheetDialog bs_base = new com.google.android.material.bottomsheet.BottomSheetDialog(MainActivity.this);
			bs_base.setCancelable(true);
			View layBase = getLayoutInflater().inflate(R.layout.bottom, null);
			bs_base.setContentView(layBase);
			Button Update = layBase.findViewById(R.id.button);
			Update.setOnClickListener(v -> {
				upd.setAction(Intent.ACTION_VIEW);
				upd.setData(Uri.parse(link));
				startActivity(upd);});
			TextView text = layBase.findViewById(R.id.text);
			TextView latest = layBase.findViewById(R.id.artist);
			TextView about = layBase.findViewById(R.id.about);
			text.setText(description);
			latest.setText(getString(R.string.current_version).concat(current_version).concat(" (").concat(current_code).concat(")"));
			about.setText(getString(R.string.latest_version).concat(latest_version).concat(" (").concat(latest_code).concat(")"));
			Update.setText(getString(R.string.update_button));
			typeface(about);
			typeface(text);
			typeface(latest);
			typeface(Update);
			bs_base.show(); }).show();}

    private void _ViewSetHeight(final View _view, final double _num) { _view.getLayoutParams().height = (int)(_num);}
    private void typeface(TextView _txt) { _txt.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/raleway.ttf"), Typeface.BOLD);}
    private float getDip(Context _context) { return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 69, _context.getResources().getDisplayMetrics());}
    private void _marquee(final TextView _textview, final String _text) {
        _textview.setText(_text);
        _textview.setSingleLine(true);
        _textview.setEllipsize(TextUtils.TruncateAt.MARQUEE);
        _textview.setSelected(true);}
	private void _check(String _key, String _value) {
		if (sp.getString(_key, "").equals("")) { sp.edit().putString(_key, _value).apply();}}

	private class BackTask extends AsyncTask < String, Integer, String > {
		@Override
		protected void onPreExecute() {}
		protected String doInBackground(String...address) {
			String output = "";
			try {
				java.net.URL url = new java.net.URL(address[0]);
				java.io.BufferedReader in = new java.io.BufferedReader(new java.io.InputStreamReader(url.openStream()));
				String line;
				while ((line = in.readLine()) != null) { output += line;}
				in.close();
			} catch (IOException e) { output = e.getMessage();
			} catch (Exception e) { output = e.toString();}
			return output;}
		protected void onProgressUpdate(Integer...values) {}
		protected void onPostExecute(String s) {
			map = new Gson().fromJson(s, new TypeToken < ArrayList < HashMap < String, Object >>> () {}.getType());
			listview1.setAdapter(new Listview1Adapter(map));
			_ViewSetHeight(listview1, map.size() * getDip(getApplicationContext()));
			((BaseAdapter) listview1.getAdapter()).notifyDataSetChanged();
			listview1.setVisibility(View.VISIBLE);}}

	public class Listview1Adapter extends BaseAdapter {
		ArrayList < HashMap < String, Object >> _data;
		public Listview1Adapter(ArrayList < HashMap < String, Object >> _arr) { _data = _arr;}

		@Override
		public int getCount() { return _data.size();}

		@Override
		public HashMap < String, Object > getItem(int _index) { return _data.get(_index);}

		@Override
		public long getItemId(int _index) {return _index;}

		@Override
		public View getView(final int _position, View _v, ViewGroup _container) {
			LayoutInflater _inflater = (LayoutInflater) getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			View _view = _v;
			if (_view == null) { _view = _inflater.inflate(R.layout.artists, null);}
			final de.hdodenhof.circleimageview.CircleImageView imageview2 = _view.findViewById(R.id.imageview2);
			final TextView textview2 = _view.findViewById(R.id.textview2);
			final TextView textview3 = _view.findViewById(R.id.textview3);
			_marquee(textview2, Objects.requireNonNull(map.get(_position).get("name")).toString());
			textview3.setVisibility(View.GONE);
			if (!map.get(_position).containsKey("link")) {
				imageview2.setAlpha((float) 0.5d);
				textview2.setAlpha((float) 0.5d);}
			if (map.get(_position).containsKey("image")) { Glide.with(getApplicationContext()).load(Uri.parse(getString(R.string.site).concat(map.get(_position).get("url").toString()).concat(getString(R.string.image_jpg)))).into(imageview2);}
			typeface(textview2);
			typeface(textview3);
			return _view;}}}