package ru.kth.studio.music;

import androidx.appcompat.app.AppCompatActivity;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PorterDuff;
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
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Objects;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.BaseAdapter;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import com.bumptech.glide.Glide;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.palette.graphics.Palette;

public class ArtistActivity extends AppCompatActivity {
	private int bg = 0;
	private int txt = 0;
	private CollapsingToolbarLayout abl;
	private Toolbar toolbar;
	private ImageView imageview1, back, more;
	private String time1;
	private String date1;
	private String language;
	private String info_str;
	private String lyrics;
	private String latest_str;
	private String top_str;
	private String albums_str;
	private String singles_str;
	private String live_str;
	private String compilations_str;
	private String appears_str;
	private String month;
	private String date;
	private String time;
	private ArrayList < HashMap < String, Object >> map, map1, uri, play1, latest, top, albums, singles, live, compilations, appears, links, info = new ArrayList < > ();
	private LinearLayout test, about_segment, links_segment, about_line, hometown_line, born_line, genre_line, segment, segment_more, latest_release_linear, top_songs_linear, albums_linear, singles_linear, live_linear, compilations_linear, appear_on_linear;
	private TextView t1, t2, t3, t4, t5, t6, t11, t21, t31, t41, t51, t61, name, artist, album, about_hint, about_text, hometown_hint, hometown_text, born_hint, born_text, genre_hint, genre_text, links_hint, links_more, albums_hint, latest_release_hint, top_songs_hint, singles_hint, live_hint, compilations_hint, appears_on_hint;
	private double visibility;
	private ListView albums_list, links_list, latest_release_list, top_songs_list, singles_list, live_list, compilations_list, appears_on_list;
	private SharedPreferences sp;
	private Bitmap btm;
	private final Intent i = new Intent();
	private final Intent p = new Intent();
	private final Intent v = new Intent();

	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);
		setContentView(R.layout.artist);
		initialize();
		initializeLogic();}

	private void initialize() {
		toolbar = findViewById(R.id.toolbar);
		back = findViewById(R.id.back);
		more = findViewById(R.id.more);
		abl = findViewById(R.id.toolbar_layout);
		t1 = findViewById(R.id.t1);
		t2 = findViewById(R.id.t2);
		t3 = findViewById(R.id.t3);
		t4 = findViewById(R.id.t4);
		t5 = findViewById(R.id.t5);
		t6 = findViewById(R.id.t6);
        t11 = findViewById(R.id.t11);
        t21 = findViewById(R.id.t21);
        t31 = findViewById(R.id.t31);
        t41 = findViewById(R.id.t41);
        t51 = findViewById(R.id.t51);
        t61 = findViewById(R.id.t61);
		test = findViewById(R.id.test);
		imageview1 = findViewById(R.id.imageview1);
		name = findViewById(R.id.name);
		artist = findViewById(R.id.artist);
		album = findViewById(R.id.album);
		about_segment = findViewById(R.id.about_segment);
		links_segment = findViewById(R.id.links_segment);
		about_line = findViewById(R.id.about_line);
		hometown_line = findViewById(R.id.hometown_line);
		born_line = findViewById(R.id.born_line);
		genre_line = findViewById(R.id.genre_line);
		about_hint = findViewById(R.id.about_hint);
		about_text = findViewById(R.id.about_text);
		hometown_hint = findViewById(R.id.hometown_hint);
		hometown_text = findViewById(R.id.hometown_text);
		born_hint = findViewById(R.id.born_hint);
		born_text = findViewById(R.id.born_text);
		genre_hint = findViewById(R.id.genre_hint);
		genre_text = findViewById(R.id.genre_text);
		links_hint = findViewById(R.id.links_hint);
		links_list = findViewById(R.id.links_list);
		segment = findViewById(R.id.segment);
		segment_more = findViewById(R.id.segment_more);
		links_more = findViewById(R.id.links_more);
		albums_hint = findViewById(R.id.albums_hint);
		latest_release_hint = findViewById(R.id.latest_release_hint);
		latest_release_linear = findViewById(R.id.latest_release_linear);
		latest_release_list = findViewById(R.id.latest_release_list);
		top_songs_linear = findViewById(R.id.top_songs_linear);
		top_songs_hint = findViewById(R.id.top_songs_hint);
		top_songs_list = findViewById(R.id.top_songs_list);
		albums_linear = findViewById(R.id.albums_linear);
		albums_list = findViewById(R.id.albums_list);
		singles_linear = findViewById(R.id.singles_linear);
		singles_hint = findViewById(R.id.singles_hint);
		singles_list = findViewById(R.id.singles_list);
		live_linear = findViewById(R.id.live_linear);
		live_hint = findViewById(R.id.live_hint);
		live_list = findViewById(R.id.live_list);
		compilations_linear = findViewById(R.id.compilations_linear);
		compilations_hint = findViewById(R.id.compilations_hint);
		compilations_list = findViewById(R.id.compilations_list);
		appear_on_linear = findViewById(R.id.appears_on_linear);
		appears_on_hint = findViewById(R.id.appears_on_hint);
		appears_on_list = findViewById(R.id.appears_on_list);
		sp = getSharedPreferences("sp", Activity.MODE_PRIVATE);

		//top_songs_hint.setOnClickListener(v -> _hint(top, getString(R.string.top_songs_1)));
		albums_hint.setOnClickListener(v -> _hint(albums, getString(R.string.albums_1)));
		singles_hint.setOnClickListener(v -> _hint(singles, getString(R.string.singles_1)));
		live_hint.setOnClickListener(v -> _hint(live, getString(R.string.live_1)));
		compilations_hint.setOnClickListener(v -> _hint(compilations, getString(R.string.compilations_1)));
		appears_on_hint.setOnClickListener(v -> _hint(appears, getString(R.string.appears_on_1)));

		back.setOnClickListener(v -> onBackPressed());
		segment_more.setOnClickListener(v -> _more());
		links_hint.setOnClickListener(v -> _more());
		segment_more.setOnClickListener(v -> _more());

		imageview1.setOnClickListener(_view -> {
			if (map.get(0).containsKey("image4k")) {
				switch (sp.getString("quality", "")) {
					case "yes": _image("image4k"); break;
					case "no": _image("image"); break;
			}} else {_image("image");}});

		top_songs_list.setOnItemLongClickListener((_param1, _param2, _param3, _param4) -> {
			if (top.get(_param3).containsKey("link")) {
				new BackTask1().execute(getString(R.string.site).concat(Objects.requireNonNull(top.get(_param3).get("link")).toString()));
			} return true;});

		latest_release_list.setOnItemClickListener((_param1, _param2, _param3, _param4) -> _list(latest, _param3, latest_release_list, R.string.album_will_be_added_soon, AlbumActivity.class));
		top_songs_list.setOnItemClickListener((_param1, _param2, _param3, _param4) -> {
			switch (sp.getString("explicit", "")) {
				case "yes": _list(top, _param3, top_songs_list, R.string.song_will_be_added_soon, MusicActivity.class); break;
				case "no": switch (Objects.requireNonNull(top.get(_param3).get("explicit")).toString()) {
						case "yes": Snackbar.make(top_songs_list, R.string.not_for_children, Snackbar.LENGTH_SHORT).setAction("Ok", _view -> {}).show(); break;
						case "no": _list(top, _param3, top_songs_list, R.string.song_will_be_added_soon, MusicActivity.class); break;} break;}});
		albums_list.setOnItemClickListener((_param1, _param2, _param3, _param4) -> _list(albums, _param3, albums_list, R.string.album_will_be_added_soon, AlbumActivity.class));
		singles_list.setOnItemClickListener((_param1, _param2, _param3, _param4) -> _list(singles, _param3, singles_list, R.string.song_will_be_added_soon, AlbumActivity.class));
		live_list.setOnItemClickListener((_param1, _param2, _param3, _param4) -> _list(live, _param3, compilations_list, R.string.album_will_be_added_soon, AlbumActivity.class));
		compilations_list.setOnItemClickListener((_param1, _param2, _param3, _param4) -> _list(compilations, _param3, compilations_list, R.string.album_will_be_added_soon, AlbumActivity.class));
		appears_on_list.setOnItemClickListener((_param1, _param2, _param3, _param4) -> _list(appears, _param3, appears_on_list, R.string.album_will_be_added_soon, AlbumActivity.class));

		links_list.setOnItemClickListener((_param1, _param2, _param3, _param4) -> {
			v.setAction(Intent.ACTION_VIEW);
			v.setData(Uri.parse(Objects.requireNonNull(links.get(_param3).get("link")).toString()));
			startActivity(v);});

		about_text.setOnClickListener(_view -> {
			final com.google.android.material.bottomsheet.BottomSheetDialog bs_base = new com.google.android.material.bottomsheet.BottomSheetDialog(ArtistActivity.this);
			bs_base.setCancelable(true);
			View layBase = getLayoutInflater().inflate(R.layout.bottom, null);
			bs_base.setContentView(layBase);
			TextView text = layBase.findViewById(R.id.text);
			TextView about = layBase.findViewById(R.id.about);
			TextView artist = layBase.findViewById(R.id.artist);
			Button update = layBase.findViewById(R.id.button);
			text.setText(Objects.requireNonNull(info.get(0).get("tale_".concat(sp.getString("prefix", "")))).toString());
			about.setText(Objects.requireNonNull(map.get(0).get("name")).toString());
			artist.setVisibility(View.GONE);
			update.setVisibility(View.GONE);
			typeface(text);
			typeface(about);
			bs_base.show();});}

	private void initializeLogic() {
		album.setVisibility(View.GONE);
		artist.setVisibility(View.GONE);
		test.setVisibility(View.GONE);
		abl.setVisibility(View.GONE);
		switch (sp.getString("theme", "")) {
			case "system": AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM); break;
			case "dark": AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES); break;
			case "light": AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO); break;}
		typeface(latest_release_hint);
		typeface(top_songs_hint);
		typeface(albums_hint);
		typeface(singles_hint);
		typeface(live_hint);
		typeface(compilations_hint);
		typeface(appears_on_hint);
		about_segment.setVisibility(View.GONE);
		latest_release_linear.setVisibility(View.GONE);
		top_songs_linear.setVisibility(View.GONE);
		albums_linear.setVisibility(View.GONE);
		singles_linear.setVisibility(View.GONE);
		live_linear.setVisibility(View.GONE);
		compilations_linear.setVisibility(View.GONE);
		appear_on_linear.setVisibility(View.GONE);
		new BackTask().execute(getIntent().getStringExtra("link"));}

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
            if (map.get(0).containsKey("image")) { getBitmapFromURL(getString(R.string.site).concat(Objects.requireNonNull(map.get(0).get("image")).toString()));}
			if (map.get(0).containsKey("latest")) {
                latest_release_linear.setVisibility(View.VISIBLE);
                latest = new Gson().fromJson(Objects.requireNonNull(map.get(0).get("latest")).toString(), new TypeToken < ArrayList < HashMap < String, Object >>> () {}.getType());
                latest_release_list.setAdapter(new Latest_release_listAdapter(latest));
                if (latest.size() < 5) { _ViewSetHeight(latest_release_list, latest.size() * getDip(getApplicationContext(), 77));
                } else { _ViewSetHeight(latest_release_list, 5 * getDip(getApplicationContext(),  77));}
                latest_release_hint.setText(getString(R.string.latest_release_1));
                ((BaseAdapter) latest_release_list.getAdapter()).notifyDataSetChanged();
            } else { latest_release_linear.setVisibility(View.GONE);}
            if (map.get(0).containsKey("top")) {
                top_songs_linear.setVisibility(View.VISIBLE);
                top = new Gson().fromJson(Objects.requireNonNull(map.get(0).get("top")).toString(), new TypeToken < ArrayList < HashMap < String, Object >>> () {}.getType());
                top_songs_list.setAdapter(new Top_songs_listAdapter(top));
                if (top.size() < 10) { _ViewSetHeight(top_songs_list, top.size() * getDip(getApplicationContext(), 55));
                } else { _ViewSetHeight(top_songs_list, 10 * getDip(getApplicationContext(),  55));}
                top_songs_hint.setText(getString(R.string.top_songs_1).concat(" (").concat(String.valueOf(top.size())).concat(") >"));
                ((BaseAdapter) top_songs_list.getAdapter()).notifyDataSetChanged();
            } else { top_songs_linear.setVisibility(View.GONE);}
            if (map.get(0).containsKey("albums")) {
                albums_linear.setVisibility(View.VISIBLE);
                albums = new Gson().fromJson(Objects.requireNonNull(map.get(0).get("albums")).toString(), new TypeToken < ArrayList < HashMap < String, Object >>> () {}.getType());
                albums_list.setAdapter(new Albums_listAdapter(albums));
                if (albums.size() < 5) { _ViewSetHeight(albums_list, albums.size() * getDip(getApplicationContext(), 77));
                } else { _ViewSetHeight(albums_list, 5 * getDip(getApplicationContext(),  77));}
                albums_hint.setText(getString(R.string.albums_1).concat(" (").concat(String.valueOf(albums.size())).concat(") >"));
                ((BaseAdapter) albums_list.getAdapter()).notifyDataSetChanged();
            } else { albums_linear.setVisibility(View.GONE);}
            if (map.get(0).containsKey("singles")) {
                singles_linear.setVisibility(View.VISIBLE);
                singles = new Gson().fromJson(Objects.requireNonNull(map.get(0).get("singles")).toString(), new TypeToken < ArrayList < HashMap < String, Object >>> () {}.getType());
                singles_list.setAdapter(new Singles_listAdapter(singles));
                if (singles.size() < 5) { _ViewSetHeight(singles_list, singles.size() * getDip(getApplicationContext(), 77));
                } else { _ViewSetHeight(singles_list, 5 * getDip(getApplicationContext(), 77));}
                singles_hint.setText(getString(R.string.singles_1).concat(" (").concat(String.valueOf(singles.size())).concat(") >"));
                ((BaseAdapter) singles_list.getAdapter()).notifyDataSetChanged();
            } else { singles_linear.setVisibility(View.GONE);}
            if (map.get(0).containsKey("live")) {
                live_linear.setVisibility(View.VISIBLE);
                live = new Gson().fromJson(Objects.requireNonNull(map.get(0).get("live")).toString(), new TypeToken < ArrayList < HashMap < String, Object >>> () {}.getType());
                live_list.setAdapter(new Live_listAdapter(live));
                if (live.size() < 5) { _ViewSetHeight(live_list, live.size() * getDip(getApplicationContext(), 77));
                } else { _ViewSetHeight(live_list, 5 * getDip(getApplicationContext(), 77)); }
                live_hint.setText(getString(R.string.live_1).concat(" (").concat(String.valueOf(live.size())).concat(") >"));
                ((BaseAdapter) live_list.getAdapter()).notifyDataSetChanged();
            } else { live_linear.setVisibility(View.GONE);}
            if (map.get(0).containsKey("compilations")) {
                compilations_linear.setVisibility(View.VISIBLE);
                compilations = new Gson().fromJson(Objects.requireNonNull(map.get(0).get("compilations")).toString(), new TypeToken < ArrayList < HashMap < String, Object >>> () {}.getType());
                compilations_list.setAdapter(new Compilations_listAdapter(compilations));
                if (compilations.size() < 5) { _ViewSetHeight(compilations_list, compilations.size() * getDip(getApplicationContext(), 77));
                } else { _ViewSetHeight(compilations_list, 5 * getDip(getApplicationContext(), 77));}
                compilations_hint.setText(getString(R.string.compilations_1).concat(" (").concat(String.valueOf(compilations.size())).concat(") >"));
                ((BaseAdapter) compilations_list.getAdapter()).notifyDataSetChanged();
            } else { compilations_linear.setVisibility(View.GONE);}
            if (map.get(0).containsKey("appears")) {
                appear_on_linear.setVisibility(View.VISIBLE);
                appears = new Gson().fromJson(Objects.requireNonNull(map.get(0).get("appears")).toString(), new TypeToken < ArrayList < HashMap < String, Object >>> () {}.getType());
                appears_on_list.setAdapter(new Appears_listAdapter(appears));
                if (appears.size() < 5) { _ViewSetHeight(appears_on_list, appears.size() * getDip(getApplicationContext(), 77));
                } else { _ViewSetHeight(appears_on_list, 5 * getDip(getApplicationContext(), 77));}
                appears_on_hint.setText(getString(R.string.appears_on_1).concat(" (").concat(String.valueOf(appears.size())).concat(") >"));
                ((BaseAdapter) appears_on_list.getAdapter()).notifyDataSetChanged();
            } else { appear_on_linear.setVisibility(View.GONE);}
            if (map.get(0).containsKey("about")) {
				info = new Gson().fromJson(Objects.requireNonNull(map.get(0).get("about")).toString(), new TypeToken < ArrayList < HashMap < String, Object >>> () {}.getType());}
			if (map.get(0).containsKey("links")) {
				links = new Gson().fromJson(Objects.requireNonNull(map.get(0).get("links")).toString(), new TypeToken < ArrayList < HashMap < String, Object >>> () {}.getType());
				links_list.setAdapter(new Links_listAdapter(links));
				_ViewSetHeight(links_list, links.size() * getDip(getApplicationContext(), 50));
				((BaseAdapter) links_list.getAdapter()).notifyDataSetChanged();
				if (map.get(0).containsKey("about")) {
					links_list.setVisibility(View.GONE);
					segment.setVisibility(View.GONE);
					links_more.setVisibility(View.VISIBLE);
					typeface(links_more);
				} else {
					links_list.setVisibility(View.VISIBLE);
					segment.setVisibility(View.GONE);
					links_more.setVisibility(View.GONE);}
			} _text();}}

	private void getBitmapFromURL(String src) {
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
		StrictMode.setThreadPolicy(policy);
		try {
			URL url = new URL(src);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setDoInput(true);
			connection.connect();
			InputStream input = connection.getInputStream();
			btm = BitmapFactory.decodeStream(input);
		} catch (IOException e) {}}

	public void _about_segment() {
		if (map.get(0).containsKey("about")) {
			about_segment.setVisibility(View.VISIBLE);
			_about("tale", about_hint, about_text, about_line);
			_about("genre", genre_hint, genre_text, genre_line);
			_about1("hometown", "from", hometown_hint, hometown_text, hometown_line, R.string.hometown, R.string.from);
			_about1("born", "formed", born_hint, born_text, born_line, R.string.born, R.string.formed);
		} else { about_segment.setVisibility(View.GONE);}}

	public void _about(String _str, TextView _hint, TextView _text, LinearLayout _line) {
		if (info.get(0).containsKey(_str.concat("_").concat(sp.getString("prefix", "")))) {
			marquee(_text, Objects.requireNonNull(info.get(0).get(_str.concat("_").concat(sp.getString("prefix", "")))).toString());
			_line.setVisibility(View.VISIBLE);
			typeface(_hint);
			typeface(_text);
		} else { _line.setVisibility(View.GONE);}}

	public void _about1(String _str, String _str1, TextView _hint, TextView _text, LinearLayout _line, int s, int s1) {
		if (info.get(0).containsKey(_str.concat("_").concat(sp.getString("prefix", ""))) || info.get(0).containsKey(_str1.concat("_").concat(sp.getString("prefix", "")))) {
			if (info.get(0).containsKey(_str.concat("_").concat(sp.getString("prefix", "")))) {
				_hint.setText(s);
				marquee(_text, Objects.requireNonNull(info.get(0).get(_str.concat("_").concat(sp.getString("prefix", "")))).toString());
			} else {
				_hint.setText(s1);
				marquee(_text, Objects.requireNonNull(info.get(0).get(_str1.concat("_").concat(sp.getString("prefix", "")))).toString());}
			_line.setVisibility(View.VISIBLE);
			typeface(_hint);
			typeface(_text);
		} else { _line.setVisibility(View.GONE);}}

	public void _text() {
		if (!map.get(0).containsKey("visibility")) { abl.setVisibility(View.VISIBLE);
			Glide.with(getApplicationContext()).load(Uri.parse(getString(R.string.site).concat(Objects.requireNonNull(map.get(0).get("image")).toString()))).into(imageview1);}
		switch (sp.getString("quality", "")) {
			case "yes": if (map.get(0).containsKey("image4k")) { _quality("image4k");} break;
			case "no": _quality("image"); break;}
		if (sp.getString("animation", "").equals("yes")) {
			if (map.get(0).containsKey("gif")) { _quality("gif");}}
		marquee(name, Objects.requireNonNull(map.get(0).get("name")).toString());
		typeface(name);
		if (!map.get(0).containsKey("visibility")) {
		Palette.from(btm).generate(palette -> {
			assert palette != null;
			Palette.Swatch vibrantSwatch1 = palette.getLightVibrantSwatch();
			Palette.Swatch vibrantSwatch2 = palette.getVibrantSwatch();
			Palette.Swatch vibrantSwatch3 = palette.getDarkVibrantSwatch();
			Palette.Swatch vibrantSwatch4 = palette.getLightMutedSwatch();
			Palette.Swatch vibrantSwatch5 = palette.getMutedSwatch();
			Palette.Swatch vibrantSwatch6 = palette.getDarkMutedSwatch();
			switch (Objects.requireNonNull(map.get(0).get("color")).toString()) {
				case "1": if (vibrantSwatch1 != null) {
				bg = vibrantSwatch1.getRgb();
				txt = vibrantSwatch1.getBodyTextColor();}
				_title(); break;
				case "2": if (vibrantSwatch2 != null) {
					bg = vibrantSwatch2.getRgb();
					txt = vibrantSwatch2.getBodyTextColor();}
					_title(); break;
				case "3": if (vibrantSwatch3 != null) {
					bg = vibrantSwatch3.getRgb();
					txt = vibrantSwatch3.getBodyTextColor();}
					_title(); break;
				case "4": if (vibrantSwatch4 != null) {
					bg = vibrantSwatch4.getRgb();
					txt = vibrantSwatch4.getBodyTextColor();}
					_title(); break;
				case "5": if (vibrantSwatch5 != null) {
					bg = vibrantSwatch5.getRgb();
					txt = vibrantSwatch5.getBodyTextColor();}
					_title(); break;
				case "6": if (vibrantSwatch6 != null) {
					bg = vibrantSwatch6.getRgb();
					txt = vibrantSwatch6.getBodyTextColor();}
					_title(); break;
				case "0": _test_features(); break;}
		});}
		_about_segment();
		if (map.get(0).containsKey("links")) {
			typeface(links_hint);
			links_segment.setVisibility(View.VISIBLE);
		} else { links_segment.setVisibility(View.GONE);}}

	private void _title() {
		toolbar.setBackgroundColor(bg);
		abl.setContentScrimColor(bg);
		back.setColorFilter(txt, PorterDuff.Mode.SRC_IN);
		more.setColorFilter(txt, PorterDuff.Mode.SRC_IN);
		name.setTextColor(txt);}

	private void _test_features() {
		if (sp.getString("test_features", "").equals("yes")) {
			test.setVisibility(View.VISIBLE);
			Palette.from(btm).generate(palette -> {
				assert palette != null;
				Palette.Swatch vibrantSwatch1 = palette.getLightVibrantSwatch();
				Palette.Swatch vibrantSwatch2 = palette.getVibrantSwatch();
				Palette.Swatch vibrantSwatch3 = palette.getDarkVibrantSwatch();
				Palette.Swatch vibrantSwatch4 = palette.getLightMutedSwatch();
				Palette.Swatch vibrantSwatch5 = palette.getMutedSwatch();
				Palette.Swatch vibrantSwatch6 = palette.getDarkMutedSwatch();
				int bg1 = 0, bg2 = 0, bg3 = 0, bg4 = 0, bg5 = 0, bg6 = 0;
				int txt1 = 0, txt2 = 0, txt3 = 0, txt4 = 0, txt5 = 0, txt6 = 0;
			if(vibrantSwatch1 != null) { bg1 = vibrantSwatch1.getRgb();
				txt1 = vibrantSwatch1.getBodyTextColor();}
			if(vibrantSwatch2 != null) { bg2 = vibrantSwatch2.getRgb();
				txt2 = vibrantSwatch2.getBodyTextColor();}
			if(vibrantSwatch3 != null) { bg3 = vibrantSwatch3.getRgb();
				txt3 = vibrantSwatch3.getBodyTextColor();}
			if(vibrantSwatch4 != null) { bg4 = vibrantSwatch4.getRgb();
				txt4 = vibrantSwatch4.getBodyTextColor();}
			if(vibrantSwatch5 != null) { bg5 = vibrantSwatch5.getRgb();
				txt5 = vibrantSwatch5.getBodyTextColor();}
			if(vibrantSwatch6 != null) { bg6 = vibrantSwatch6.getRgb();
				txt6 = vibrantSwatch6.getBodyTextColor();}
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
		else { test.setVisibility(View.GONE);}}

	public void _more() {
		if (map.get(0).containsKey("about")) {
			if (visibility == 0) {
				links_more.setText(R.string.hide);
				segment.setVisibility(View.VISIBLE);
				links_list.setVisibility(View.VISIBLE);
				visibility++;
			} else {
				links_more.setText(R.string.show);
				segment.setVisibility(View.GONE);
				links_list.setVisibility(View.GONE);
				visibility = 0;}}}

	public void _hint(ArrayList<HashMap<String, Object>> _map, String _str) {
		p.setClass(getApplicationContext(), FullActivity.class);
		p.putExtra("data", new Gson().toJson(_map));
		p.putExtra("artist", Objects.requireNonNull(map.get(0).get("name")).toString());
		p.putExtra("title", _str);
		startActivity(p);}

	public void _image(String _img) {
		i.setClass(getApplicationContext(), ImageActivity.class);
		i.putExtra("image_q", getString(R.string.site).concat(Objects.requireNonNull(map.get(0).get(_img)).toString()));
		i.putExtra("name", Objects.requireNonNull(map.get(0).get("name")).toString());
		i.putExtra("artist", "");
		i.putExtra("album", "");
		i.putExtra("color", Objects.requireNonNull(map.get(0).get("color")).toString());
		startActivity(i);}

	public void _list(ArrayList<HashMap<String, Object>> _map, int _param3, ListView _list, int _str, Class _act) {
		if (_map.get(_param3).containsKey("link")) {
			i.setClass(getApplicationContext(), _act);
			i.putExtra("link", getString(R.string.site).concat(Objects.requireNonNull(_map.get(_param3).get("link")).toString()));
			startActivity(i);
		} else { Snackbar.make(_list, _str, Snackbar.LENGTH_SHORT).setAction("Ok", _view -> {}).show();}}

	public void _quality(String _key) {Glide.with(getApplicationContext()).load(Uri.parse(getString(R.string.site).concat(Objects.requireNonNull(map.get(0).get(_key)).toString()))).into(imageview1);}

	public void _adapter(View _view, int _position, ArrayList<HashMap<String, Object>> _map, String _str, int _num) {
		final LinearLayout linear1 = _view.findViewById(R.id.linear1);
		final ImageView imageview1 = _view.findViewById(R.id.imageview1);
		final TextView Name = _view.findViewById(R.id.Name);
		final TextView album = _view.findViewById(R.id.album);
		final TextView Release = _view.findViewById(R.id.Release);
		final ImageView more = _view.findViewById(R.id.more);
		if (!_map.get(_position).containsKey("link")) {
			Release.setAlpha((float) 0.5d);
			album.setAlpha((float) 0.5d);
			imageview1.setAlpha((float) 0.5d);
			Name.setAlpha((float) 0.5d);
			more.setAlpha((float) 0.5d);}
		if (_position < _num) {
			name(_position, _map, Name);
			date(_map, _position);
			time(_map, _position);
			if (_map.get(_position).containsKey("image")) { Glide.with(getApplicationContext()).load(Uri.parse(getString(R.string.site).concat(Objects.requireNonNull(_map.get(_position).get("image")).toString()))).into(imageview1);}
			if (Objects.requireNonNull(_map.get(_position).get("artist")).toString().equals(Objects.requireNonNull(map.get(0).get("name")).toString())) {
				marquee(album, date1);
				marquee(Release, time1);
			} else {
				marquee(album, Objects.requireNonNull(_map.get(_position).get("artist")).toString());
				marquee(Release, date1.concat(" • ").concat(time1));}
			typeface(Name);
			typeface(album);
			typeface(Release);
		} else { linear1.setVisibility(View.GONE);}}

	private class BackTask1 extends AsyncTask < String, Integer, String > {
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
			map1 = new Gson().fromJson(s, new TypeToken < ArrayList < HashMap < String, Object >>> () {}.getType());
			if (map1.get(0).containsKey("text")) { play1 = new Gson().fromJson(Objects.requireNonNull(map1.get(0).get("text")).toString(), new TypeToken<ArrayList<HashMap<String, Object>>>() {}.getType());}
			uri = new Gson().fromJson(Objects.requireNonNull(map1.get(0).get("artist_uri")).toString(), new TypeToken<ArrayList<HashMap<String, Object>>>() {}.getType());
			_bottom();}}

	public void _bottom() {
		final com.google.android.material.bottomsheet.BottomSheetDialog bs_base = new com.google.android.material.bottomsheet.BottomSheetDialog(ArtistActivity.this);
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
				switch (sp.getString("quality", "")) {
					case "yes":
						i.setClass(getApplicationContext(), ImageActivity.class);
						i.putExtra("image_q", Objects.requireNonNull(map1.get(0).get("image4k")).toString());
						i.putExtra("name", name.getText().toString().concat(" - ").concat(artist.getText().toString()));
						i.putExtra("artist", album.getText().toString());
						startActivity(i); break;
					case "no":
						i.setClass(getApplicationContext(), ImageActivity.class);
						i.putExtra("image_q", getString(R.string.site).concat(Objects.requireNonNull(map1.get(0).get("image")).toString()));
						i.putExtra("name", name.getText().toString().concat(" - ").concat(artist.getText().toString()));
						i.putExtra("artist", album.getText().toString());
						startActivity(i); break;}}});
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
		name(0, map1, name);
		String alb = Objects.requireNonNull(map1.get(0).get("album")).toString();
		if (map1.get(0).containsKey("additional")) {
            Object additional = map1.get(0).get("additional");
            Object o = Objects.requireNonNull(additional);
            if (o.equals("single")) { alb = alb.concat(" • ").concat(getString(R.string.single));
            } else if (o.equals("EP")) { alb = alb.concat(" • ").concat(getString(R.string.ep));}}
		marquee(album, alb);
		artist.setText(Objects.requireNonNull(map1.get(0).get("artist")).toString());
		Glide.with(getApplicationContext()).load(Uri.parse(getString(R.string.site).concat(Objects.requireNonNull(map1.get(0).get("image")).toString()))).into(image);
		bs_base.show();}

	private void span(String _str, TextView _txt, ArrayList<HashMap<String, Object>> _map, int _n, int color) {
		SpannableStringBuilder ssb = new SpannableStringBuilder(_str);
		ForegroundColorSpan fcsRed = new ForegroundColorSpan(color);
		ssb.setSpan(fcsRed, Objects.requireNonNull(_map.get(_n).get("name")).toString().length(), _str.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
		marque(_txt, ssb);}
	private void language() { language = Locale.getDefault().getDisplayLanguage();}
	private void date(ArrayList<HashMap<String, Object>> _map, int _position) {
		switch (Objects.requireNonNull(_map.get(_position).get("month")).toString()) {
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
			default: date1 = month.concat(" ").concat(Objects.requireNonNull(_map.get(_position).get("date")).toString()).concat(", ").concat(Objects.requireNonNull(_map.get(_position).get("year")).toString()); break;
			case "Русский": case "русский": date1 = Objects.requireNonNull(_map.get(_position).get("date")).toString().concat(" ").concat(month).concat(", ").concat(Objects.requireNonNull(_map.get(_position).get("year")).toString()); break;
			case "Deutsch": date1 = Objects.requireNonNull(_map.get(_position).get("date")).toString().concat(".").concat(month).concat(" ").concat(Objects.requireNonNull(_map.get(_position).get("year")).toString()); break;}}
	private void name(int _position, ArrayList<HashMap<String, Object>> _map, TextView Name) {
		String _str = Objects.requireNonNull(_map.get(_position).get("name")).toString();
		if (_map.get(_position).containsKey("prefix")) { _str = _str.concat(" ").concat(Objects.requireNonNull(_map.get(_position).get("prefix")).toString());}
		if (Objects.requireNonNull(_map.get(_position).get("explicit")).equals("yes")) { _str = _str.concat(" 🅴");}
		span(_str, Name, _map, _position, ContextCompat.getColor(ArtistActivity.this, R.color.text2));}
	private void time(ArrayList<HashMap<String, Object>> _map, int _position) {
		switch (Objects.requireNonNull(_map.get(_position).get("sec_add")).toString()) {
			case "1": time1 = Objects.requireNonNull(_map.get(_position).get("sec")).toString().concat(getString(R.string.sec1)); break;
			case "2": time1 = Objects.requireNonNull(_map.get(_position).get("sec")).toString().concat(getString(R.string.sec2)); break;
			case "3": time1 = Objects.requireNonNull(_map.get(_position).get("sec")).toString().concat(getString(R.string.sec3)); break;
			case "4": time1 = Objects.requireNonNull(_map.get(_position).get("sec")).toString().concat(getString(R.string.sec4)); break;}
		if (_map.get(_position).containsKey("min") && _map.get(_position).containsKey("min_add")) {
			switch (Objects.requireNonNull(_map.get(_position).get("min_add")).toString()) {
				case "1": time1 = Objects.requireNonNull(_map.get(_position).get("min")).toString().concat(getString(R.string.min1)).concat(" ").concat(time1); break;
				case "2": time1 = Objects.requireNonNull(_map.get(_position).get("min")).toString().concat(getString(R.string.min2)).concat(" ").concat(time1); break;
				case "3": time1 = Objects.requireNonNull(_map.get(_position).get("min")).toString().concat(getString(R.string.min3)).concat(" ").concat(time1); break;
				case "4": time1 = Objects.requireNonNull(_map.get(_position).get("min")).toString().concat(getString(R.string.min4)).concat(" ").concat(time1); break;}}
		if (_map.get(_position).containsKey("hour") && _map.get(_position).containsKey("hour_add")) {
			switch (Objects.requireNonNull(_map.get(_position).get("hour_add")).toString()) {
				case "1": time1 = Objects.requireNonNull(_map.get(_position).get("hour")).toString().concat(getString(R.string.hour1)).concat(" ").concat(time1); break;
				case "2": time1 = Objects.requireNonNull(_map.get(_position).get("hour")).toString().concat(getString(R.string.hour2)).concat(" ").concat(time1); break;
				case "3": time1 = Objects.requireNonNull(_map.get(_position).get("hour")).toString().concat(getString(R.string.hour3)).concat(" ").concat(time1); break;
				case "4": time1 = Objects.requireNonNull(_map.get(_position).get("hour")).toString().concat(getString(R.string.hour4)).concat(" ").concat(time1); break;}}}
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
	private void _ViewSetHeight(final View _view, final double _num) { _view.getLayoutParams().height = (int)(_num);}
	private void typeface(TextView _txt) { _txt.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/raleway.ttf"), Typeface.BOLD);}

	public void _artist() {
		if (uri.size() > 1) {
			i.setClass(getApplicationContext(), FullActivity.class);
			i.putExtra("data", new Gson().toJson(uri));
			i.putExtra("artist", Objects.requireNonNull(map1.get(0).get("artist")).toString());
			i.putExtra("title", getString(R.string.featured_artists_1));
			startActivity(i);
		} else {
			if (uri.get(0).containsKey("link")) {
				i.setClass(getApplicationContext(), ArtistActivity.class);
				i.putExtra("link", getString(R.string.site).concat(Objects.requireNonNull(uri.get(0).get("link")).toString()));
				startActivity(i);
			} else { Snackbar.make(latest_release_linear, R.string.artist_will_be_added_soon, Snackbar.LENGTH_INDEFINITE).setAction("Ok", _view -> {}).show();}}}

	public void _album() {
		i.setClass(getApplicationContext(), AlbumActivity.class);
		i.putExtra("link", getString(R.string.site).concat(Objects.requireNonNull(map1.get(0).get("album_uri")).toString()));
		startActivity(i);}

	public void _info() {
		if (map1.get(0).containsKey("info")) {
			i.setClass(getApplicationContext(), DetailsActivity.class);
			i.putExtra("info", getString(R.string.site).concat(Objects.requireNonNull(map1.get(0).get("info")).toString()));
			startActivity(i);}}

	public void _lyrics() {
		final com.google.android.material.bottomsheet.BottomSheetDialog bs_base = new com.google.android.material.bottomsheet.BottomSheetDialog(ArtistActivity.this);
		bs_base.setCancelable(true);
		View layBase = getLayoutInflater().inflate(R.layout.bottom, null);
		bs_base.setContentView(layBase);
		TextView text = layBase.findViewById(R.id.text);
		if (play1.get(0).containsKey("written")) { text.setText(Objects.requireNonNull(play1.get(0).get("text")).toString().concat(getString(R.string.written_by)).concat(Objects.requireNonNull(play1.get(0).get("written")).toString()));
		} else { text.setText(Objects.requireNonNull(play1.get(0).get("text")).toString());}
		TextView about = layBase.findViewById(R.id.about);
		about.setVisibility(View.VISIBLE);
		name(0, map1, about);
		typeface(text);
		typeface(about);
		bs_base.show();}

    public class Latest_release_listAdapter extends BaseAdapter {
        ArrayList < HashMap < String, Object >> _data;
        public Latest_release_listAdapter(ArrayList < HashMap < String, Object >> _arr) { _data = _arr;}

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
            _adapter(_view, _position, latest, latest_str, 1);
            return _view;}}

    public class Top_songs_listAdapter extends BaseAdapter {
        ArrayList < HashMap < String, Object >> _data;
        public Top_songs_listAdapter(ArrayList < HashMap < String, Object >> _arr) { _data = _arr;}

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
            if (_view == null) { _view = _inflater.inflate(R.layout.songs, null);}
            final ImageView image = _view.findViewById(R.id.image);
            final LinearLayout num = _view.findViewById(R.id.num);
            final LinearLayout tale = _view.findViewById(R.id.tale);
            final ImageView imageview1 = _view.findViewById(R.id.imageview1);
            final TextView number = _view.findViewById(R.id.number);
            final TextView time = _view.findViewById(R.id.time);
            final TextView name = _view.findViewById(R.id.name);
            final TextView artist = _view.findViewById(R.id.artist);
            final ImageView more = _view.findViewById(R.id.more);

            num.setVisibility(View.GONE);
            tale.setVisibility(View.GONE);
            name(_position, top, name);
			marquee(artist, Objects.requireNonNull(top.get(_position).get("album")).toString().concat(" • ").concat(Objects.requireNonNull(top.get(_position).get("year")).toString()));
            Glide.with(getApplicationContext()).load(Uri.parse(getString(R.string.site).concat(Objects.requireNonNull(top.get(_position).get("image")).toString()))).into(image);
            more.setOnClickListener(v -> new BackTask1().execute(getString(R.string.site).concat(Objects.requireNonNull(top.get(_position).get("link")).toString())));
            if (!top.get(_position).containsKey("link")) {
                number.setAlpha((float) 0.5d);
                name.setAlpha((float) 0.5d);
                artist.setAlpha((float) 0.5d);
                time.setAlpha((float) 0.5d);
                imageview1.setAlpha((float) 0.5d);
                more.setAlpha((float) 0.5d);}
            typeface(name);
            typeface(artist);
            return _view;}}

    public class Albums_listAdapter extends BaseAdapter {
        ArrayList < HashMap < String, Object >> _data;
        public Albums_listAdapter(ArrayList < HashMap < String, Object >> _arr) { _data = _arr;}

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
            _adapter(_view, _position, albums, albums_str, 5);
            return _view;}}

    public class Singles_listAdapter extends BaseAdapter {
        ArrayList < HashMap < String, Object >> _data;
        public Singles_listAdapter(ArrayList < HashMap < String, Object >> _arr) { _data = _arr;}

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
            _adapter(_view, _position, singles, singles_str, 5);
            return _view;}}

    public class Live_listAdapter extends BaseAdapter {
        ArrayList < HashMap < String, Object >> _data;
        public Live_listAdapter(ArrayList < HashMap < String, Object >> _arr) {_data = _arr;}

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
            _adapter(_view, _position, live, live_str, 5);
            return _view;}}

    public class Compilations_listAdapter extends BaseAdapter {
        ArrayList < HashMap < String, Object >> _data;
        public Compilations_listAdapter(ArrayList < HashMap < String, Object >> _arr) { _data = _arr;}

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
            _adapter(_view, _position, compilations, compilations_str, 5);
            return _view;}}

    public class Appears_listAdapter extends BaseAdapter {
        ArrayList < HashMap < String, Object >> _data;
        public Appears_listAdapter(ArrayList < HashMap < String, Object >> _arr) { _data = _arr; }

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
            _adapter(_view, _position, appears, appears_str, 5);
            return _view;}}

    public class Links_listAdapter extends BaseAdapter {
        ArrayList < HashMap < String, Object >> _data;
        public Links_listAdapter(ArrayList < HashMap < String, Object >> _arr) { _data = _arr;}

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
            if (_view == null) { _view = _inflater.inflate(R.layout.links, null);}
            final ImageView link_image = _view.findViewById(R.id.link_image);
            final TextView link_text = _view.findViewById(R.id.link_text);
            final TextView link_adress = _view.findViewById(R.id.link_adress);
            Glide.with(getApplicationContext()).load(Uri.parse(getString(R.string.site).concat(Objects.requireNonNull(links.get(_position).get("image")).toString()))).into(link_image);
            link_text.setText(Objects.requireNonNull(links.get(_position).get("text_".concat(sp.getString("prefix", "")))).toString());
            marquee(link_adress, Objects.requireNonNull(links.get(_position).get("link")).toString());
            typeface(link_text);
            typeface(link_adress);
            return _view;}}}