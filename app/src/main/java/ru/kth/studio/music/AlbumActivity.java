package ru.kth.studio.music;

import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.*;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.os.*;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.util.TypedValue;
import android.view.*;
import android.content.*;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ListView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.content.Intent;
import android.net.Uri;
import android.app.Activity;
import android.content.SharedPreferences;
import android.view.View;
import com.bumptech.glide.Glide;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.palette.graphics.Palette;

public class AlbumActivity extends AppCompatActivity {
	private int bg = 0, txt = 0, txt1 = 0;
	private CollapsingToolbarLayout abl;
	private Toolbar toolbar;
	private ImageView imageview1, back, more_i;
	private String alb, month, time1, str, date1, language,  songs1, about, talestr, titlestr, playstr, otherstr, morestr, infostr, lyrics, str1 = "";
	private double myster, num, myst = 0;
	private ArrayList < HashMap < String, Object >> map, play, other, more, artists, uri, map1, play1 = new ArrayList < > ();
	private LinearLayout test, other_versions_linear, more_by_linear, featured_artists_linear;
	private TextView t1, t2, t3, t4, t5, t6, tale, album, name, artist, date, time, copyright, other_versions_text, more_by_text, featured_artists_text;
	private ListView listview1, more_by_list, featured_artists_list, other_versions_list;
	private final Intent i = new Intent();
	private final Intent p = new Intent();
	private SharedPreferences sp;
	private Bitmap btm;

	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);
		setContentView(R.layout.album);
		initialize();
		initializeLogic();}

	private void initialize() {
		back = findViewById(R.id.back);
		more_i = findViewById(R.id.more);
		abl = findViewById(R.id.toolbar_layout);
		toolbar = findViewById(R.id.toolbar);
		t1 = findViewById(R.id.t1);
		t2 = findViewById(R.id.t2);
		t3 = findViewById(R.id.t3);
		t4 = findViewById(R.id.t4);
		t5 = findViewById(R.id.t5);
		t6 = findViewById(R.id.t6);
		test = findViewById(R.id.test);
		tale = findViewById(R.id.tale);
		listview1 = findViewById(R.id.listview1);
		imageview1 = findViewById(R.id.imageview1);
		name = findViewById(R.id.name);
		artist = findViewById(R.id.artist);
		date = findViewById(R.id.date);
		time = findViewById(R.id.time);
		album = findViewById(R.id.album);
		copyright = findViewById(R.id.copyright);
		other_versions_linear = findViewById(R.id.other_versions_linear);
		other_versions_text = findViewById(R.id.other_versions_text);
		other_versions_list = findViewById(R.id.other_versions_list);
		more_by_linear = findViewById(R.id.more_by_linear);
		more_by_text = findViewById(R.id.more_by_text);
		more_by_list = findViewById(R.id.more_by_list);
		featured_artists_linear = findViewById(R.id.featured_artists_linear);
		featured_artists_text = findViewById(R.id.featured_artists_text);
		featured_artists_list = findViewById(R.id.featured_artists_list);
		sp = getSharedPreferences("sp", Activity.MODE_PRIVATE);

		back.setOnClickListener(v -> onBackPressed());
		tale.setOnClickListener(_view -> {
			final com.google.android.material.bottomsheet.BottomSheetDialog bs_base = new com.google.android.material.bottomsheet.BottomSheetDialog(AlbumActivity.this);
			bs_base.setCancelable(true);
			View layBase = getLayoutInflater().inflate(R.layout.bottom, null);
			bs_base.setContentView(layBase);
			TextView text = layBase.findViewById(R.id.text);
			text.setText(map.get(0).get("tale_".concat(sp.getString("prefix", ""))).toString());
			typeface(text);
			TextView about = layBase.findViewById(R.id.about);
			about.setVisibility(View.VISIBLE);
			talestr = map.get(0).get("name").toString();
			if (map.get(0).containsKey("prefix")) { talestr = talestr.concat(" ").concat(map.get(0).get("prefix").toString());}
			span(talestr, about, map, 0, ContextCompat.getColor(AlbumActivity.this, R.color.text2));
			typeface(about);
			bs_base.show();});

		listview1.setOnItemLongClickListener((_param1, _param2, _param3, _param4) -> { if (play.get(_param3).containsKey("link")) {
			new BackTask1().execute(getString(R.string.site).concat(play.get(_param3).get("link").toString()));}
			return true;});

		listview1.setOnItemClickListener((_param1, _param2, _param3, _param4) -> { if (!play.get(_param3).containsKey("mystery")){
				if (sp.getString("explicit", "").equals("no")) {
					if (play.get(_param3).get("explicit").toString().equals("yes")) { Snackbar.make(listview1, R.string.not_for_children, Snackbar.LENGTH_SHORT).setAction("Ok", _view -> {}).show();
					} else { _list(play, _param3, listview1, R.string.song_will_be_added_soon, MusicActivity.class);
					}} else { _list(play, _param3, listview1, R.string.song_will_be_added_soon, MusicActivity.class);}}});
		other_versions_list.setOnItemClickListener((_param1, _param2, _param3, _param4) -> _list(other, _param3, other_versions_list, R.string.album_will_be_added_soon, AlbumActivity.class));
		more_by_list.setOnItemClickListener((_param1, _param2, _param3, _param4) -> _list(more, _param3, more_by_list, R.string.album_will_be_added_soon, AlbumActivity.class));
		featured_artists_list.setOnItemClickListener((_param1, _param2, _param3, _param4) -> _list(artists, _param3, featured_artists_list, R.string.artist_will_be_added_soon, ArtistActivity.class));

		other_versions_text.setOnClickListener(v -> _extra(other, R.string.other_versions_1));
		featured_artists_text.setOnClickListener(v -> _extra(artists, R.string.featured_artists_1));
		more_by_text.setOnClickListener(v -> {p.setClass(getApplicationContext(), FullActivity.class);
			p.putExtra("data", new Gson().toJson(more));
			p.putExtra("artist", map.get(0).get("artist").toString());
			p.putExtra("title", getString(R.string.more_by).concat(" ").concat(map.get(0).get("artist").toString()));
			startActivity(p);});

		artist.setOnClickListener(v -> {
			if (uri.size() > 1) {
				i.setClass(getApplicationContext(), FullActivity.class);
				i.putExtra("data", new Gson().toJson(uri));
				i.putExtra("artist", map.get(0).get("artist").toString());
				i.putExtra("title", getString(R.string.featured_artists_1));
				startActivity(i);
			} else {
				i.setClass(getApplicationContext(), ArtistActivity.class);
				i.putExtra("link", getString(R.string.site).concat(uri.get(0).get("link").toString()));
				startActivity(i);}
		});

		imageview1.setOnClickListener(_view -> {
			if (map.get(0).containsKey("image4k")) {
				if (sp.getString("quality", "").equals("yes")) {
					_image("image4k");
				} else {_image("image");
				}} else {_image("image");
			}});}

	private void initializeLogic() {
		more_i.setVisibility(View.INVISIBLE);
		test.setVisibility(View.GONE);
		switch (sp.getString("theme", "")) {
			case "system": AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM); break;
			case "dark": AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES); break;
			case "light": AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO); break;}
		new BackTask().execute(getIntent().getStringExtra("link"));
		other_versions_linear.setVisibility(View.GONE);
		more_by_linear.setVisibility(View.GONE);
		featured_artists_linear.setVisibility(View.GONE);
		typeface(other_versions_text);
		typeface(more_by_text);
		typeface(featured_artists_text);}

	@Override
	public void onBackPressed() { finish(); super.onBackPressed();}

	private Bitmap getBitmapFromURL(String src) {
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
		StrictMode.setThreadPolicy(policy);
		try {
			URL url = new URL(src);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setDoInput(true);
			connection.connect();
			InputStream input = connection.getInputStream();
			Bitmap myBitmap = BitmapFactory.decodeStream(input);
			btm = myBitmap;
			return myBitmap;
		} catch (IOException e) { return null;}}

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
			} catch (java.net.MalformedURLException e) { output = e.getMessage();
			} catch (java.io.IOException e) { output = e.getMessage();
			} catch (Exception e) { output = e.toString();}
			return output;}
		protected void onProgressUpdate(Integer...values) {}
		protected void onPostExecute(String s) {
			str = s;
			map = new Gson().fromJson(str, new TypeToken < ArrayList < HashMap < String, Object >>> () {}.getType());
			getBitmapFromURL(getString(R.string.site).concat(map.get(0).get("image").toString()));
			play = new Gson().fromJson(map.get(0).get("songs").toString(), new TypeToken < ArrayList < HashMap < String, Object >>> () {}.getType());
			uri = new Gson().fromJson(map.get(0).get("artist_uri").toString(), new TypeToken<ArrayList<HashMap<String, Object>>>() {}.getType());
			listview1.setAdapter(new Listview1Adapter(play));
			num = 0;
			for (int _repeat21 = 0; _repeat21 < play.size(); _repeat21++) { if (play.get((int) num).containsKey("mystery")) { myst++;} num++;}
			_ViewSetHeight(listview1, (play.size() * getDip(getApplicationContext(), 51)) - (myst * getDip(getApplicationContext(), 26)));
			((BaseAdapter) listview1.getAdapter()).notifyDataSetChanged();
			Palette.from(btm).generate(palette -> {
				Palette.Swatch vibrantSwatch1 = palette.getLightVibrantSwatch();
				Palette.Swatch vibrantSwatch2 = palette.getVibrantSwatch();
				Palette.Swatch vibrantSwatch3 = palette.getDarkVibrantSwatch();
				Palette.Swatch vibrantSwatch4 = palette.getLightMutedSwatch();
				Palette.Swatch vibrantSwatch5 = palette.getMutedSwatch();
				Palette.Swatch vibrantSwatch6 = palette.getDarkMutedSwatch();
				switch (map.get(0).get("color").toString()) { case "1": if (vibrantSwatch1 != null) {
					bg = vibrantSwatch1.getRgb();
					txt = vibrantSwatch1.getBodyTextColor();
					txt1 = vibrantSwatch1.getTitleTextColor();}
					_title();
					break;
					case "2": if (vibrantSwatch2 != null) {
						bg = vibrantSwatch2.getRgb();
						txt = vibrantSwatch2.getBodyTextColor();
						txt1 = vibrantSwatch2.getTitleTextColor();}
						_title();
						break;
					case "3": if (vibrantSwatch3 != null) {
						bg = vibrantSwatch3.getRgb();
						txt = vibrantSwatch3.getBodyTextColor();
						txt1 = vibrantSwatch3.getTitleTextColor();}
						_title();
						break;
					case "4": if (vibrantSwatch4 != null) {
						bg = vibrantSwatch4.getRgb();
						txt = vibrantSwatch4.getBodyTextColor();
						txt1 = vibrantSwatch4.getTitleTextColor();}
						_title();
						break;
					case "5": if (vibrantSwatch5 != null) {
						bg = vibrantSwatch5.getRgb();
						txt = vibrantSwatch5.getBodyTextColor();
						txt1 = vibrantSwatch5.getTitleTextColor();}
						_title();
						break;
					case "6": if (vibrantSwatch6 != null) {
						bg = vibrantSwatch6.getRgb();
						txt = vibrantSwatch6.getBodyTextColor();
						txt1 = vibrantSwatch6.getTitleTextColor();}
						_title();
						break;
					case "0": _test_features(); break;}});
			_text();
			if (map.get(0).containsKey("other")) {
				other_versions_linear.setVisibility(View.VISIBLE);
				other = new Gson().fromJson(map.get(0).get("other").toString(), new TypeToken < ArrayList < HashMap < String, Object >>> () {}.getType());
				if (other.size() < 5) { _ViewSetHeight(other_versions_list, other.size() * getDip(getApplicationContext(), 77));
				} else { _ViewSetHeight(other_versions_list, 5 * getDip(getApplicationContext(), 77));}
				other_versions_text.setText(getString(R.string.other_versions_1).concat(" (").concat(String.valueOf(other.size())).concat(") >"));
				other_versions_list.setAdapter(new Other_listAdapter(other));
				((BaseAdapter) other_versions_list.getAdapter()).notifyDataSetChanged();}
			if (map.get(0).containsKey("more")) {
				more_by_linear.setVisibility(View.VISIBLE);
				more = new Gson().fromJson(map.get(0).get("more").toString(), new TypeToken < ArrayList < HashMap < String, Object >>> () {}.getType());
				if (more.size() < 5) { _ViewSetHeight(more_by_list, more.size() * getDip(getApplicationContext(), 77));
				} else { _ViewSetHeight(more_by_list, 5 * getDip(getApplicationContext(), 77)); }
				more_by_text.setText(getString(R.string.more_by).concat(" ").concat(map.get(0).get("artist").toString().concat(" (").concat(String.valueOf(more.size())).concat(") >")));
				more_by_list.setAdapter(new More_listAdapter(more));
				((BaseAdapter) more_by_list.getAdapter()).notifyDataSetChanged();}
			if (map.get(0).containsKey("artists")) {
				featured_artists_linear.setVisibility(View.VISIBLE);
				artists = new Gson().fromJson(map.get(0).get("artists").toString(), new TypeToken < ArrayList < HashMap < String, Object >>> () {}.getType());
				if (artists.size() < 5) { _ViewSetHeight(featured_artists_list, artists.size() * getDip(getApplicationContext(), 64));
				} else { _ViewSetHeight(featured_artists_list, 5 * getDip(getApplicationContext(), 64));
					featured_artists_text.setText(getString(R.string.featured_artists_1).concat(" (").concat(String.valueOf(artists.size())).concat(") >"));}
				featured_artists_list.setAdapter(new ArtistsAdapter(artists));
				((BaseAdapter) featured_artists_list.getAdapter()).notifyDataSetChanged();}}}

	private class BackTask1 extends AsyncTask < String, Integer, String > {
		@Override
		protected void onPreExecute() {}
		protected String doInBackground(String...address) {
			String output = "";
			try {
				java.net.URL url = new java.net.URL(address[0]);
				java.io.BufferedReader in = new java.io.BufferedReader(new java.io.InputStreamReader(url.openStream()));
				String line;
				while ((line = in.readLine()) != null) { output += line; }
				in.close();
			} catch (java.net.MalformedURLException e) { output = e.getMessage();
			} catch (java.io.IOException e) { output = e.getMessage();
			} catch (Exception e) { output = e.toString();}
			return output;}
		protected void onProgressUpdate(Integer...values) {}
		protected void onPostExecute(String s) {
			str1 = s;
			map1 = new Gson().fromJson(str1, new TypeToken < ArrayList < HashMap < String, Object >>> () {}.getType());
			if (map1.get(0).containsKey("text")) { play1 = new Gson().fromJson(map1.get(0).get("text").toString(), new TypeToken<ArrayList<HashMap<String, Object>>>() {}.getType());}
			uri = new Gson().fromJson(map1.get(0).get("artist_uri").toString(), new TypeToken<ArrayList<HashMap<String, Object>>>() {}.getType());
			_bottom();}}

	public void _bottom() {
		final com.google.android.material.bottomsheet.BottomSheetDialog bs_base = new com.google.android.material.bottomsheet.BottomSheetDialog(AlbumActivity.this);
		bs_base.setCancelable(true);
		View layBase = getLayoutInflater().inflate(R.layout.info, null);
		bs_base.setContentView(layBase);
		ImageView image = layBase.findViewById(R.id.image);
		ImageView artist_image = layBase.findViewById(R.id.artist_image);
		ImageView album_image = layBase.findViewById(R.id.album_image);
		ImageView lyrics_image = layBase.findViewById(R.id.lyrics_image);
		ImageView info_image = layBase.findViewById(R.id.info_image);
		TextView name = layBase.findViewById(R.id.name);
		TextView album = layBase.findViewById(R.id.album);
		TextView artist = layBase.findViewById(R.id.artist);
		TextView artist_text = layBase.findViewById(R.id.artist_text);
		TextView album_text = layBase.findViewById(R.id.album_text);
		TextView lyrics_text = layBase.findViewById(R.id.lyrics_text);
		TextView info_text = layBase.findViewById(R.id.info_text);
		LinearLayout artist_linear = layBase.findViewById(R.id.artist_linear);
		LinearLayout album_linear = layBase.findViewById(R.id.album_linear);
		LinearLayout lyrics_linear = layBase.findViewById(R.id.lyrics_linear);
		LinearLayout info_linear = layBase.findViewById(R.id.info_linear);
		typeface(name);
		typeface(album);
		typeface(artist);
		typeface(artist_text);
		typeface(album_text);
		typeface(lyrics_text);
		typeface(info_text);
		if (!map1.get(0).containsKey("info")) {
			info_image.setAlpha((float) 0.5d);
			info_linear.setAlpha((float) 0.5d);
			info_text.setAlpha((float) 0.5d);}
		if (uri.size() > 1) {
			artist_image.setImageResource(R.drawable.ic_people);
			artist_text.setText(getString(R.string.go_to_artists));
		} else {
			artist_image.setImageResource(R.drawable.ic_timer_auto);
			artist_text.setText(getString(R.string.go_to_artist));}
		if (map1.get(0).containsKey("text")) { lyrics_linear.setVisibility(View.VISIBLE);
		} else { lyrics_linear.setVisibility(View.GONE);}
		image.setOnClickListener(v -> {
			if (map1.get(0).containsKey("image4k")) {
				if (sp.getString("quality", "").equals("mobile")) {
					i.setClass(getApplicationContext(), ImageActivity.class);
					i.putExtra("image_q", map1.get(0).get("image4k").toString());
					i.putExtra("name", name.getText().toString().concat(" - ").concat(artist.getText().toString()));
					i.putExtra("artist", album.getText().toString());
					startActivity(i);
				} else {
					i.setClass(getApplicationContext(), ImageActivity.class);
					i.putExtra("image_q", getString(R.string.site).concat(map1.get(0).get("image").toString()));
					i.putExtra("name", name.getText().toString().concat(" - ").concat(artist.getText().toString()));
					i.putExtra("artist", album.getText().toString());
					startActivity(i);}
			} else {
				i.setClass(getApplicationContext(), ImageActivity.class);
				i.putExtra("image_q", getString(R.string.site).concat(map1.get(0).get("image").toString()));
				i.putExtra("name", name.getText().toString().concat(" - ").concat(artist.getText().toString()));
				i.putExtra("artist", album.getText().toString());
				startActivity(i);}});
		artist_text.setOnClickListener(v -> _artist());
		artist_image.setOnClickListener(v -> { bs_base.cancel(); _artist();});
		artist_linear.setOnClickListener(v -> { bs_base.cancel(); _artist();});
		album_text.setOnClickListener(v -> { bs_base.cancel(); _album();});
		album_image.setOnClickListener(v -> { bs_base.cancel(); _album();});
		album_linear.setOnClickListener(v -> { bs_base.cancel(); _album();});
		lyrics_text.setOnClickListener(v -> _lyrics());
		lyrics_image.setOnClickListener(v -> _lyrics());
		lyrics_linear.setOnClickListener(v -> _lyrics());
		info_text.setOnClickListener(v -> { if (map1.get(0).containsKey("info")) {_info();}});
		info_image.setOnClickListener(v -> { if (map1.get(0).containsKey("info")) {_info();}});
		info_linear.setOnClickListener(v -> { if (map1.get(0).containsKey("info")) {_info();}});
		name(infostr, 0, map1, name, 0);
		alb = map1.get(0).get("album").toString();
		if (map1.get(0).containsKey("additional")) {
			if (map1.get(0).get("additional").equals("single")) {
				alb = alb.concat(" • ").concat(getString(R.string.single));
			} else { alb = alb.concat(" • ").concat(getString(R.string.ep));}}
		marquee(album, alb);
		artist.setText(map1.get(0).get("artist").toString());
		Glide.with(getApplicationContext()).load(Uri.parse(getString(R.string.site).concat(map1.get(0).get("image").toString()))).into(image);
		bs_base.show();}

	public void _artist() {
		if (uri.size() > 1) {
			i.setClass(getApplicationContext(), FullActivity.class);
			i.putExtra("data", new Gson().toJson(uri));
			i.putExtra("artist", map1.get(0).get("artist").toString());
			i.putExtra("title", getString(R.string.featured_artists_1));
			startActivity(i);
		} else {
			if (uri.get(0).containsKey("link")) {
				i.setClass(getApplicationContext(), ArtistActivity.class);
				i.putExtra("link", getString(R.string.site).concat(uri.get(0).get("link").toString()));
				startActivity(i);
			} else { Snackbar.make(listview1, R.string.artist_will_be_added_soon, Snackbar.LENGTH_INDEFINITE).setAction("Ok", _view -> {}).show();}}}

	public void _album() {
		i.setClass(getApplicationContext(), AlbumActivity.class);
		i.putExtra("link", getString(R.string.site).concat(map1.get(0).get("album_uri").toString()));
		startActivity(i);}

	public void _info() {
		if (map1.get(0).containsKey("info")) {
			i.setClass(getApplicationContext(), DetailsActivity.class);
			i.putExtra("info", getString(R.string.site).concat(map1.get(0).get("info").toString()));
			startActivity(i);}}

	public void _lyrics() {
		final com.google.android.material.bottomsheet.BottomSheetDialog bs_base = new com.google.android.material.bottomsheet.BottomSheetDialog(AlbumActivity.this);
		bs_base.setCancelable(true);
		View layBase = getLayoutInflater().inflate(R.layout.bottom, null);
		bs_base.setContentView(layBase);
		TextView text = layBase.findViewById(R.id.text);
		if (play1.get(0).containsKey("written")) { text.setText(play1.get(0).get("text").toString().concat(getString(R.string.written_by)).concat(play1.get(0).get("written").toString()));
		} else { text.setText(play1.get(0).get("text").toString());}
		TextView about = layBase.findViewById(R.id.about);
		about.setVisibility(View.VISIBLE);
		name(lyrics, 0, map1, about, 1);
		typeface(text);
		typeface(about);
		bs_base.show();}

	private void _title() {
		toolbar.setBackgroundColor(bg);
		abl.setContentScrimColor(bg);
		name.setTextColor(txt);
		artist.setTextColor(txt);
		album.setTextColor(txt1);
		titlestr =map.get(0).get("name").toString();
		if (map.get(0).containsKey("prefix")) { titlestr = titlestr.concat(" ").concat(map.get(0).get("prefix").toString());}
		if (map.get(0).containsKey("additional")) {
			if (map.get(0).get("additional").equals("single")) { titlestr = titlestr.concat(" • ").concat(getString(R.string.single));}
			if (map.get(0).get("additional").equals("ep")) { titlestr = titlestr.concat(" • ").concat(getString(R.string.ep));}}
		if (map.get(0).get("explicit").equals("yes")) { titlestr = titlestr.concat(" 🅴");}
		span(titlestr, name, map, 0, txt1);
	}

	private void _test_features() {
			test.setVisibility(View.VISIBLE);
			Palette.from(btm).generate(palette -> {
				Palette.Swatch vibrantSwatch1 = palette.getLightVibrantSwatch();
				Palette.Swatch vibrantSwatch2 = palette.getVibrantSwatch();
				Palette.Swatch vibrantSwatch3 = palette.getDarkVibrantSwatch();
				Palette.Swatch vibrantSwatch4 = palette.getLightMutedSwatch();
				Palette.Swatch vibrantSwatch5 = palette.getMutedSwatch();
				Palette.Swatch vibrantSwatch6 = palette.getDarkMutedSwatch();
				int bg1 = 0, bg2 = 0, bg3 = 0, bg4 = 0, bg5 = 0, bg6 = 0;
				int txt1 = 0, txt2 = 0, txt3 = 0, txt4 = 0, txt5 = 0, txt6 = 0;
				if(vibrantSwatch1 != null) { bg1 = vibrantSwatch1.getRgb();
					txt1 = vibrantSwatch1.getTitleTextColor();}
				if(vibrantSwatch2 != null) { bg2 = vibrantSwatch2.getRgb();
					txt2 = vibrantSwatch2.getTitleTextColor();}
				if(vibrantSwatch3 != null) { bg3 = vibrantSwatch3.getRgb();
					txt3 = vibrantSwatch3.getTitleTextColor();}
				if(vibrantSwatch4 != null) { bg4 = vibrantSwatch4.getRgb();
					txt4 = vibrantSwatch4.getTitleTextColor();}
				if(vibrantSwatch5 != null) { bg5 = vibrantSwatch5.getRgb();
					txt5 = vibrantSwatch5.getTitleTextColor();}
				if(vibrantSwatch6 != null) { bg6 = vibrantSwatch6.getRgb();
					txt6 = vibrantSwatch6.getTitleTextColor();}
				t1.setBackgroundColor(bg1);
				t1.setTextColor(txt1);
				t2.setBackgroundColor(bg2);
				t2.setTextColor(txt2);
				t3.setBackgroundColor(bg3);
				t3.setTextColor(txt3);
				t4.setBackgroundColor(bg4);
				t4.setTextColor(txt4);
				t5.setBackgroundColor(bg5);
				t5.setTextColor(txt5);
				t6.setBackgroundColor(bg6);
				t6.setTextColor(txt6);});}

	public void _text() {
		if (map.get(0).containsKey("tale_".concat(sp.getString("prefix", "")))) {
			tale.setVisibility(View.VISIBLE);
			about = map.get(0).get("tale_".concat(sp.getString("prefix", ""))).toString();
			marquee1(tale, map.get(0).get("tale_".concat(sp.getString("prefix", ""))).toString());
			typeface(tale);
		} else { tale.setVisibility(View.GONE);}
		marquee(artist, map.get(0).get("artist").toString());
		marquee(album, map.get(0).get("genre_".concat(sp.getString("prefix", ""))).toString().concat(" • ".concat(map.get(0).get("release").toString())));
		sp.edit().putString("artist", map.get(0).get("artist").toString()).apply();
		date(map, 0);
		time(map, 0);
		songs(map, 0);
		date.setText(date1);
		time.setText(songs1.concat(" • ".concat(time1)));
		copyright.setText(map.get(0).get("copyright").toString());
		Glide.with(getApplicationContext()).load(Uri.parse(getString(R.string.site).concat(map.get(0).get("image").toString()))).into(imageview1);
		if (sp.getString("quality", "").equals("yes")) { if (map.get(0).containsKey("image4k")) { _quality("image4k", R.drawable.ic_4k);}
		} else { _quality("image", R.drawable.ic_hd);}
		if (sp.getString("animation", "").equals("yes")) { if (map.get(0).containsKey("gif")) { _quality("gif", R.drawable.ic_gif);}}
		typeface(name);
		typeface(artist);
		typeface(album);
		typeface(date);
		typeface(copyright);
		typeface(time);}

	private void span(String _str, TextView _txt, ArrayList<HashMap<String, Object>> _map, int _n, int color) {
		SpannableStringBuilder ssb = new SpannableStringBuilder(_str);
		ForegroundColorSpan fcsRed = new ForegroundColorSpan(color);
		ssb.setSpan(fcsRed, _map.get(_n).get("name").toString().length(), _str.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
		marque(_txt, ssb);}
	private void language() { language = Locale.getDefault().getDisplayLanguage();}
	private void date(ArrayList<HashMap<String, Object>> _map, int _position) {
		switch (_map.get(_position).get("month").toString()) {
			case "Jan": month = getString(R.string.Jan); break;
			case "Feb": month = getString(R.string.Feb); break;
			case "Mar": month = getString(R.string.Mar); break;
			case "Apr": month = getString(R.string.Apr); break;
			case "May": month = getString(R.string.May); break;
			case "Jun": month = getString(R.string.Jun); break;
			case "Jul": month = getString(R.string.Jul); break;
			case "Aug": month = getString(R.string.Aug); break;
			case "Sep": month = getString(R.string.Sep); break;
			case "Oct": month = getString(R.string.Oct); break;
			case "Nov": month = getString(R.string.Nov); break;
			case "Dec": month = getString(R.string.Dec); break;}
		language();
		switch (language) {
			default: date1 = month.concat(" ").concat(_map.get(_position).get("date").toString()).concat(", ").concat(_map.get(_position).get("year").toString()); break;
			case "Русский": date1 = _map.get(_position).get("date").toString().concat(" ").concat(month).concat(", ").concat(_map.get(_position).get("year").toString()); break;
			case "русский": date1 = _map.get(_position).get("date").toString().concat(month).concat(", ").concat(_map.get(_position).get("year").toString()); break;
			case "Deutsch": date1 = _map.get(_position).get("date").toString().concat(".").concat(month).concat(" ").concat(_map.get(_position).get("year").toString()); break;}}
	private void name(String _str, int _position, ArrayList<HashMap<String, Object>> _map, TextView Name, int i) {
		_str = _map.get(_position).get("name").toString();
		if (_map.get(_position).containsKey("prefix")) { _str = _str.concat(" ").concat(_map.get(_position).get("prefix").toString());}
		if (_map.get(_position).get("explicit").equals("yes")) { _str = _str.concat(" 🅴");}
		if (i == 1) { _str = _str.concat(" \n").concat(_map.get(_position).get("artist").toString()); }
		span(_str, Name, _map, _position, ResourcesCompat.getColor(getResources(), R.color.text1, null));}
	private void time(ArrayList<HashMap<String, Object>> _map, int _position) {
		switch (_map.get(_position).get("sec_add").toString()) {
			case "1": time1 = _map.get(_position).get("sec").toString().concat(getString(R.string.sec1)); break;
			case "2": time1 = _map.get(_position).get("sec").toString().concat(getString(R.string.sec2)); break;
			case "3": time1 = _map.get(_position).get("sec").toString().concat(getString(R.string.sec3)); break;
			case "4": time1 = _map.get(_position).get("sec").toString().concat(getString(R.string.sec4)); break;}
		if (_map.get(_position).containsKey("min") && _map.get(_position).containsKey("min_add")) {
			switch (_map.get(_position).get("min_add").toString()) {
				case "1": time1 = _map.get(_position).get("min").toString().concat(getString(R.string.min1)).concat(" ").concat(time1); break;
				case "2": time1 = _map.get(_position).get("min").toString().concat(getString(R.string.min2)).concat(" ").concat(time1); break;
				case "3": time1 = _map.get(_position).get("min").toString().concat(getString(R.string.min3)).concat(" ").concat(time1); break;
				case "4": time1 = _map.get(_position).get("min").toString().concat(getString(R.string.min4)).concat(" ").concat(time1); break;}}
		if (_map.get(_position).containsKey("hour") && _map.get(_position).containsKey("hour_add")) {
			switch (_map.get(_position).get("hour_add").toString()) {
				case "1": time1 = _map.get(_position).get("hour").toString().concat(getString(R.string.hour1)).concat(" ").concat(time1); break;
				case "2": time1 = _map.get(_position).get("hour").toString().concat(getString(R.string.hour2)).concat(" ").concat(time1); break;
				case "3": time1 = _map.get(_position).get("hour").toString().concat(getString(R.string.hour3)).concat(" ").concat(time1); break;
				case "4": time1 = _map.get(_position).get("hour").toString().concat(getString(R.string.hour4)).concat(" ").concat(time1); break;}}}
	private void songs(ArrayList<HashMap<String, Object>> _map, int _position) {
		songs1 = _map.get(_position).get("songs_num").toString();
		switch (_map.get(_position).get("songs_add").toString()) {
			case "1": songs1 = songs1.concat(getString(R.string.song1)); break;
			case "2": songs1 = songs1.concat(getString(R.string.song2)); break;
			case "3": songs1 = songs1.concat(getString(R.string.song3)); break;
			case "4": songs1 = songs1.concat(getString(R.string.song4)); break;}}
	private float getDip(Context _context, int _input) { return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, _input, _context.getResources().getDisplayMetrics());}
	public static void marque(final TextView _textview, final SpannableStringBuilder _text) {
		_textview.setText(_text);
		_textview.setSingleLine(true);
		_textview.setEllipsize(TextUtils.TruncateAt.MARQUEE);
		_textview.setSelected(true);}
	private void marquee(final TextView _textview, final String _text) {
		_textview.setText(_text);
		_textview.setSingleLine(true);
		_textview.setEllipsize(TextUtils.TruncateAt.MARQUEE);
		_textview.setSelected(true);}
	private void marquee1(final TextView _textview, final String _text) {
		_textview.setText(_text);
		_textview.setMaxLines(2);
		_textview.setEllipsize(TextUtils.TruncateAt.END);
		_textview.setSelected(true);}
	private void _ViewSetHeight(final View _view, final double _num) { _view.getLayoutParams().height = (int)(_num);}
	private void typeface(TextView _txt) { _txt.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/raleway.ttf"), Typeface.BOLD);}
	private void _list(ArrayList<HashMap<String, Object>> _map, int _param3, ListView _list, int _str, Class _act) {
		if (_map.get(_param3).containsKey("link")) {
			i.setClass(getApplicationContext(), _act);
			i.putExtra("link", getString(R.string.site).concat(_map.get(_param3).get("link").toString()));
			startActivity(i);
		} else { Snackbar.make(_list, _str, Snackbar.LENGTH_SHORT).setAction("Ok", _view -> {}).show();}}
	private void _extra(ArrayList<HashMap<String, Object>> _map, int _str) {
		p.setClass(getApplicationContext(), FullActivity.class);
		p.putExtra("data", new Gson().toJson(_map));
		p.putExtra("artist", map.get(0).get("artist").toString());
		p.putExtra("title", getString(_str));
		startActivity(p);}
	private void _image(String _key) {
		i.setClass(getApplicationContext(), ImageActivity.class);
		i.putExtra("image_q", getString(R.string.site).concat(map.get(0).get(_key).toString()));
		i.putExtra("name", name.getText().toString());
		i.putExtra("artist", artist.getText().toString());
		i.putExtra("album", album.getText().toString());
		i.putExtra("color", map.get(0).get("color").toString());
		startActivity(i);}
	private void _quality(String _key, int _str) { Glide.with(getApplicationContext()).load(Uri.parse(getString(R.string.site).concat(map.get(0).get(_key).toString()))).into(imageview1);}

	public class Listview1Adapter extends BaseAdapter {
		ArrayList < HashMap < String, Object >> _data;
		public Listview1Adapter(ArrayList < HashMap < String, Object >> _arr) { _data = _arr;}

		@Override
		public int getCount() { return _data.size();}

		@Override
		public HashMap < String, Object > getItem(int _index) { return _data.get(_index);}

		@Override
		public long getItemId(int _index) { return _index; }

		@Override
		public View getView(final int _position, View _v, ViewGroup _container) {
			LayoutInflater _inflater = (LayoutInflater) getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			View _view = _v;
			if (_view == null) { _view = _inflater.inflate(R.layout.songs, null);}
			final ImageView image = _view.findViewById(R.id.image);
			final LinearLayout tale = _view.findViewById(R.id.tale);
			final LinearLayout linear1 = _view.findViewById(R.id.linear1);
			final ImageView imageview1 = _view.findViewById(R.id.imageview1);
			final TextView number = _view.findViewById(R.id.number);
			final TextView mystery = _view.findViewById(R.id.mystery);
			final TextView time = _view.findViewById(R.id.time);
			final TextView name = _view.findViewById(R.id.name);
			final TextView artist = _view.findViewById(R.id.artist);
			final ImageView more = _view.findViewById(R.id.more);
			image.setVisibility(View.GONE);
			more.setOnClickListener(v -> new BackTask1().execute(getString(R.string.site).concat(play.get(_position).get("link").toString())));
			if (!play.get(_position).containsKey("link")) {
				number.setAlpha((float) 0.5d);
				name.setAlpha((float) 0.5d);
				artist.setAlpha((float) 0.5d);
				time.setAlpha((float) 0.5d);
				imageview1.setAlpha((float) 0.5d);
				more.setAlpha((float) 0.5d);}
			if (play.get(_position).containsKey("mystery")) {
				linear1.setVisibility(View.GONE);
				tale.setVisibility(View.VISIBLE);
				mystery.setText(play.get(_position).get("mystery").toString());
				typeface(mystery);
				myster = myster + 1;
				sp.edit().putString("myster", String.valueOf((long)(myster))).apply();
			} else {
				linear1.setVisibility(View.VISIBLE);
				tale.setVisibility(View.GONE);
				number.setText(play.get(_position).get("number").toString());
				time.setText(play.get(_position).get("time").toString());
				playstr = play.get(_position).get("name").toString();
				if (play.get(_position).containsKey("prefix")) { playstr = playstr.concat(" ").concat(play.get(_position).get("prefix").toString());}
				if (play.get(_position).get("explicit").equals("yes")) { playstr = playstr.concat(" 🅴");}
				span(playstr, name, play, _position, ContextCompat.getColor(AlbumActivity.this, R.color.text2));
				marquee(artist, play.get(_position).get("artist").toString());
				if (!play.get(_position).get("star").toString().equals("yes")) { imageview1.setVisibility(View.GONE);}
				if (play.get(_position).get("artist").toString().equals(sp.getString("artist", ""))) { artist.setVisibility(View.GONE);
				} else { artist.setVisibility(View.VISIBLE);}
				if (sp.getString("explicit", "").equals("no")) {
					if (play.get(_position).get("explicit").toString().equals("yes")) {
						number.setAlpha((float) 0.5d);
						name.setAlpha((float) 0.5d);
						artist.setAlpha((float) 0.5d);
						time.setAlpha((float) 0.5d);
						imageview1.setAlpha((float) 0.5d);
						more.setAlpha((float) 0.5d);}}
				typeface(number);
				typeface(time);
				typeface(name);
				typeface(artist);}
			_ViewSetHeight(listview1, (play.size() * getDip(getApplicationContext(), 51)) - (myster * getDip(getApplicationContext(), 18)));
			((BaseAdapter) listview1.getAdapter()).notifyDataSetChanged();
			return _view;}}

	public class Other_listAdapter extends BaseAdapter {
		ArrayList < HashMap < String, Object >> _data;
		public Other_listAdapter(ArrayList < HashMap < String, Object >> _arr) { _data = _arr;}

		@Override
		public int getCount() { return _data.size();}

		@Override
		public HashMap < String, Object > getItem(int _index) { return _data.get(_index);}

		@Override
		public long getItemId(int _index) { return _index;}

		@Override
		public View getView(final int _position, View _v, ViewGroup _container) {
			LayoutInflater _inflater = (LayoutInflater) getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			View _view = _v;
			if (_view == null) { _view = _inflater.inflate(R.layout.playlists, null);}
			final LinearLayout linear1 = _view.findViewById(R.id.linear1);
			final ImageView imageview1 = _view.findViewById(R.id.imageview1);
			final TextView Name = _view.findViewById(R.id.Name);
			final TextView album = _view.findViewById(R.id.album);
			final TextView Release = _view.findViewById(R.id.Release);
			if (!other.get(_position).containsKey("link")) {
				Release.setAlpha((float) 0.5d);
				album.setAlpha((float) 0.5d);
				imageview1.setAlpha((float) 0.5d);
				Name.setAlpha((float) 0.5d);}
			if (_position < 5) {
				otherstr = other.get(_position).get("name").toString();
				if (other.get(_position).containsKey("prefix")) { otherstr = otherstr.concat(" ").concat(other.get(_position).get("prefix").toString());}
				if (other.get(_position).get("explicit").equals("yes")) { otherstr = otherstr.concat(" 🅴");}
				span(otherstr, Name, other, _position, ContextCompat.getColor(AlbumActivity.this, R.color.text2));
				if (other.get(_position).containsKey("image")) { Glide.with(getApplicationContext()).load(Uri.parse(getString(R.string.site).concat(other.get(_position).get("image").toString()))).into(imageview1);}
				if (other.get(0).get("artist").toString().equals(map.get(0).get("artist").toString())) { album.setVisibility(View.GONE);
				} else {
					album.setVisibility(View.VISIBLE);
					marquee(album, other.get(0).get("artist").toString());}
				if (other.get(_position).containsKey("release_".concat(sp.getString("prefix", ""))) && other.get(_position).containsKey("time_".concat(sp.getString("prefix", "")))) { marquee(Release, other.get(_position).get("release_".concat(sp.getString("prefix", ""))).toString().concat(" • ".concat(other.get(_position).get("time_".concat(sp.getString("prefix", ""))).toString())));}
				typeface(Name);
				typeface(album);
				typeface(Release);
			} else { linear1.setVisibility(View.GONE);}
			return _view;}}

	public class More_listAdapter extends BaseAdapter {
		ArrayList < HashMap < String, Object >> _data;
		public More_listAdapter(ArrayList < HashMap < String, Object >> _arr) { _data = _arr;}

		@Override
		public int getCount() { return _data.size();}

		@Override
		public HashMap < String, Object > getItem(int _index) { return _data.get(_index);}

		@Override
		public long getItemId(int _index) { return _index;}

		@Override
		public View getView(final int _position, View _v, ViewGroup _container) {
			LayoutInflater _inflater = (LayoutInflater) getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			View _view = _v;
			if (_view == null) { _view = _inflater.inflate(R.layout.playlists, null);}
			final LinearLayout linear1 = _view.findViewById(R.id.linear1);
			final ImageView imageview1 = _view.findViewById(R.id.imageview1);
			final TextView Name = _view.findViewById(R.id.Name);
			final TextView album = _view.findViewById(R.id.album);
			final TextView Release = _view.findViewById(R.id.Release);
			if (!more.get(_position).containsKey("link")) {
				Release.setAlpha((float) 0.5d);
				album.setAlpha((float) 0.5d);
				imageview1.setAlpha((float) 0.5d);
				Name.setAlpha((float) 0.5d);}
			if (_position < 5) {
				morestr = more.get(_position).get("name").toString();
				if (more.get(_position).containsKey("prefix")) { morestr = morestr.concat(" ").concat(more.get(_position).get("prefix").toString());}
				if (more.get(_position).get("explicit").equals("yes")) { morestr = morestr.concat(" 🅴");}
				span(morestr, Name, more, _position, ContextCompat.getColor(AlbumActivity.this, R.color.text2));
				if (more.get(_position).containsKey("image")) { Glide.with(getApplicationContext()).load(Uri.parse(getString(R.string.site).concat(more.get(_position).get("image").toString()))).into(imageview1);}
				if (more.get(_position).get("artist").toString().equals(map.get(0).get("artist").toString())) {album.setVisibility(View.GONE);
				} else {
					album.setVisibility(View.VISIBLE);
					marquee(album, more.get(_position).get("artist").toString());}
				if (more.get(_position).containsKey("release_".concat(sp.getString("prefix", ""))) && more.get(_position).containsKey("time_".concat(sp.getString("prefix", "")))) { marquee(Release, more.get(_position).get("release_".concat(sp.getString("prefix", ""))).toString().concat(" • ".concat(more.get(_position).get("time_".concat(sp.getString("prefix", ""))).toString())));}
				typeface(Name);
				typeface(album);
				typeface(Release);
			} else { linear1.setVisibility(View.GONE);}
			return _view;}}

	public class ArtistsAdapter extends BaseAdapter {
		ArrayList < HashMap < String, Object >> _data;

		public ArtistsAdapter(ArrayList < HashMap < String, Object >> _arr) { _data = _arr;}

		@Override
		public int getCount() { return _data.size();}

		@Override
		public HashMap < String, Object > getItem(int _index) { return _data.get(_index);}

		@Override
		public long getItemId(int _index) { return _index;}

		@Override
		public View getView(final int _position, View _v, ViewGroup _container) {
			LayoutInflater _inflater = (LayoutInflater) getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			View _view = _v;
			if (_view == null) { _view = _inflater.inflate(R.layout.artists, null);}
			final de.hdodenhof.circleimageview.CircleImageView imageview2 = _view.findViewById(R.id.imageview2);
			final TextView textview2 = _view.findViewById(R.id.textview2);
			final TextView textview3 = _view.findViewById(R.id.textview3);
			if (!artists.get(_position).containsKey("link")) {
				textview3.setAlpha((float) 0.5d);
				textview2.setAlpha((float) 0.5d);
				imageview2.setAlpha((float) 0.5d);}
			if (artists.get(_position).containsKey("image")) { Glide.with(getApplicationContext()).load(Uri.parse(getString(R.string.site).concat(artists.get(_position).get("image").toString()))).into(imageview2);}
			marquee(textview2, artists.get(_position).get("name").toString());
			textview3.setVisibility(View.GONE);
			typeface(textview3);
			typeface(textview2);
			return _view;}}}