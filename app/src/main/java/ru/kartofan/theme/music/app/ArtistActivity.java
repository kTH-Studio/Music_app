package ru.kartofan.theme.music.app;

import androidx.appcompat.app.AppCompatActivity;
import android.app.Activity;
import android.os.*;
import android.text.style.ForegroundColorSpan;
import android.view.*;
import android.widget.*;
import android.content.*;
import android.text.*;
import android.util.*;
import java.util.*;
import java.util.HashMap;
import java.util.ArrayList;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.BaseAdapter;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.graphics.Typeface;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

public class ArtistActivity extends AppCompatActivity {
	private ImageView image_quality, imageview1;
	private String str, lateststr, topstr, albumsstr, singlesstr, livestr, compilationsstr, appearsstr;
	private ArrayList < HashMap < String, Object >> map, latest, top, albums, singles, live, compilations, appears, links, info = new ArrayList < > ();
	private LinearLayout about_segment, links_segment, about_line, hometown_line, born_line, genre_line, segment, segment_more, latest_release_linear, top_songs_linear, albums_linear, singles_linear, live_linear, compilations_linear, appear_on_linear;
	private TextView textview1, about_hint, about_text, hometown_hint, hometown_text, born_hint, born_text, genre_hint, genre_text, links_hint, links_more, albums_hint, latest_release_hint, top_songs_hint, singles_hint, live_hint, compilations_hint, appears_on_hint;
	private double visibility = 0;
	private ListView albums_list, links_list, latest_release_list, top_songs_list, singles_list, live_list, compilations_list, appears_on_list;
	private SharedPreferences sp;
	private final Intent i = new Intent();
	private final Intent p = new Intent();
	private final Intent l = new Intent();
	private final Intent v = new Intent();

	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);
		setContentView(R.layout.artist);
		initialize();
		initializeLogic();
	}

	private void initialize() {
		image_quality = findViewById(R.id.image_quality);
		imageview1 = findViewById(R.id.imageview1);
		textview1 = findViewById(R.id.textview1);
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

		latest_release_hint.setOnClickListener(v -> {_hint(latest, R.string.latest_release_1);});
		albums_hint.setOnClickListener(v -> {_hint(albums, R.string.albums_1);});
		singles_hint.setOnClickListener(v -> {_hint(singles, R.string.singles_1);});
		live_hint.setOnClickListener(v -> {_hint(live, R.string.live_1);});
		compilations_hint.setOnClickListener(v -> {_hint(compilations, R.string.compilations_1);});
		appears_on_hint.setOnClickListener(v -> {_hint(appears, R.string.appears_on_1);});

		segment_more.setOnClickListener(v -> {_more();});
		links_hint.setOnClickListener(v -> {_more();});
		segment_more.setOnClickListener(v -> {_more();});

		imageview1.setOnClickListener(_view -> {
			if (map.get(0).containsKey("image4k")) {
				if (sp.getString("quality", "").equals("yes")) {
					_image("image4k");
				} else {_image("image");
				}} else {_image("image");
			}});

		latest_release_list.setOnItemClickListener((_param1, _param2, _param3, _param4) -> {
			_list(latest, _param3, R.string.album_will_be_added_soon, latest_release_list);});
		top_songs_list.setOnItemClickListener((_param1, _param2, _param3, _param4) -> {
			_list(top, _param3, R.string.song_will_be_added_soon, top_songs_list);});
		albums_list.setOnItemClickListener((_param1, _param2, _param3, _param4) -> {
			_list(albums, _param3, R.string.album_will_be_added_soon, albums_list);});
		singles_list.setOnItemClickListener((_param1, _param2, _param3, _param4) -> {
			_list(singles, _param3, R.string.song_will_be_added_soon, singles_list);});
		live_list.setOnItemClickListener((_param1, _param2, _param3, _param4) -> {
			_list(live, _param3, R.string.album_will_be_added_soon, compilations_list);});
		compilations_list.setOnItemClickListener((_param1, _param2, _param3, _param4) -> {
			_list(compilations, _param3, R.string.album_will_be_added_soon, compilations_list);});
		appears_on_list.setOnItemClickListener((_param1, _param2, _param3, _param4) -> {
			_list(appears, _param3, R.string.album_will_be_added_soon, appears_on_list);
		});

		links_list.setOnItemClickListener((_param1, _param2, _param3, _param4) -> {
			v.setAction(Intent.ACTION_VIEW);
			v.setData(Uri.parse(links.get(_param3).get("link").toString()));
			startActivity(v);
		});

		about_text.setOnClickListener(_view -> {
			final com.google.android.material.bottomsheet.BottomSheetDialog bs_base = new com.google.android.material.bottomsheet.BottomSheetDialog(ArtistActivity.this);
			bs_base.setCancelable(true);
			View layBase = getLayoutInflater().inflate(R.layout.bottom, null);
			bs_base.setContentView(layBase);
			TextView text = layBase.findViewById(R.id.text);
			text.setText(info.get(0).get("tale_".concat(sp.getString("prefix", ""))).toString());
			_typeface(text);
			TextView about = layBase.findViewById(R.id.about);
			about.setVisibility(View.GONE);
			_typeface(about);
			bs_base.show();
		});
	}

	private void initializeLogic() {
		if (sp.getString("theme", "").equals("system")){
			AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
		} else if (sp.getString("theme", "").equals("battery")){
			AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_AUTO_BATTERY);
		} else if (sp.getString("theme", "").equals("dark")){
			AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
		} else if (sp.getString("theme", "").equals("light")){
			AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
		}
		_typeface(latest_release_hint);
		_typeface(top_songs_hint);
		_typeface(albums_hint);
		_typeface(singles_hint);
		_typeface(live_hint);
		_typeface(compilations_hint);
		_typeface(appears_on_hint);
		about_segment.setVisibility(View.GONE);
		latest_release_linear.setVisibility(View.GONE);
		top_songs_linear.setVisibility(View.GONE);
		albums_linear.setVisibility(View.GONE);
		singles_linear.setVisibility(View.GONE);
		live_linear.setVisibility(View.GONE);
		compilations_linear.setVisibility(View.GONE);
		appear_on_linear.setVisibility(View.GONE);
		image_quality.setVisibility(View.GONE);
		new BackTask().execute(getIntent().getStringExtra("link"));
	}

	@Override
	public void onBackPressed() {
		finish();
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
			if (map.get(0).containsKey("albums")) {
				albums_linear.setVisibility(View.VISIBLE);
				albums = new Gson().fromJson(map.get(0).get("albums").toString(), new TypeToken < ArrayList < HashMap < String, Object >>> () {}.getType());
				albums_list.setAdapter(new Albums_listAdapter(albums));
				if (albums.size() < 5) {
					_ViewSetHeight(albums_list, albums.size() * kTHUtil.getDip(getApplicationContext(), 77));
				} else {
					_ViewSetHeight(albums_list, 5 * kTHUtil.getDip(getApplicationContext(),  77));
				}
				albums_hint.setText(getString(R.string.albums_1).concat(" (").concat(String.valueOf(albums.size())).concat(") >"));
				((BaseAdapter) albums_list.getAdapter()).notifyDataSetChanged();
			} else {
				albums_linear.setVisibility(View.GONE);
			}
			if (map.get(0).containsKey("singles")) {
				singles_linear.setVisibility(View.VISIBLE);
				singles = new Gson().fromJson(map.get(0).get("singles").toString(), new TypeToken < ArrayList < HashMap < String, Object >>> () {}.getType());
				singles_list.setAdapter(new Singles_listAdapter(singles));
				if (singles.size() < 5) {
					_ViewSetHeight(singles_list, singles.size() * kTHUtil.getDip(getApplicationContext(), 77));
				} else {
					_ViewSetHeight(singles_list, 5 * kTHUtil.getDip(getApplicationContext(), 77));
				}
				singles_hint.setText(getString(R.string.singles_1).concat(" (").concat(String.valueOf(singles.size())).concat(") >"));
				((BaseAdapter) singles_list.getAdapter()).notifyDataSetChanged();
			} else {
				singles_linear.setVisibility(View.GONE);
			}
			if (map.get(0).containsKey("live")) {
				live_linear.setVisibility(View.VISIBLE);
				live = new Gson().fromJson(map.get(0).get("live").toString(), new TypeToken < ArrayList < HashMap < String, Object >>> () {}.getType());
				live_list.setAdapter(new Live_listAdapter(live));
				if (live.size() < 5) {
					_ViewSetHeight(live_list, live.size() * kTHUtil.getDip(getApplicationContext(), 77));
				} else {
					_ViewSetHeight(live_list, 5 * kTHUtil.getDip(getApplicationContext(), 77));
				}
				live_hint.setText(getString(R.string.live_1).concat(" (").concat(String.valueOf(live.size())).concat(") >"));
				((BaseAdapter) live_list.getAdapter()).notifyDataSetChanged();
			} else {
				live_linear.setVisibility(View.GONE);
			}
			if (map.get(0).containsKey("compilations")) {
				compilations_linear.setVisibility(View.VISIBLE);
				compilations = new Gson().fromJson(map.get(0).get("compilations").toString(), new TypeToken < ArrayList < HashMap < String, Object >>> () {}.getType());
				compilations_list.setAdapter(new Compilations_listAdapter(compilations));
				if (compilations.size() < 5) {
					_ViewSetHeight(compilations_list, compilations.size() * kTHUtil.getDip(getApplicationContext(), 77));
				} else {
					_ViewSetHeight(compilations_list, 5 * kTHUtil.getDip(getApplicationContext(), 77));
				}
				compilations_hint.setText(getString(R.string.compilations_1).concat(" (").concat(String.valueOf(compilations.size())).concat(") >"));
				((BaseAdapter) compilations_list.getAdapter()).notifyDataSetChanged();
			} else {
				compilations_linear.setVisibility(View.GONE);
			}
			if (map.get(0).containsKey("appears")) {
				appear_on_linear.setVisibility(View.VISIBLE);
				appears = new Gson().fromJson(map.get(0).get("appear").toString(), new TypeToken < ArrayList < HashMap < String, Object >>> () {}.getType());
				appears_on_list.setAdapter(new Appears_listAdapter(appears));
				if (appears.size() < 5) {
					_ViewSetHeight(appears_on_list, appears.size() * kTHUtil.getDip(getApplicationContext(), 77));
				} else {
					_ViewSetHeight(appears_on_list, 5 * kTHUtil.getDip(getApplicationContext(), 77));
				}
				appears_on_hint.setText(getString(R.string.appears_on_1).concat(" (").concat(String.valueOf(appears.size())).concat(") >"));
				((BaseAdapter) appears_on_list.getAdapter()).notifyDataSetChanged();
			} else {
				appear_on_linear.setVisibility(View.GONE);
			}
			if (map.get(0).containsKey("about")) {
				info = new Gson().fromJson(map.get(0).get("about").toString(), new TypeToken < ArrayList < HashMap < String, Object >>> () {}.getType());
			}
			if (map.get(0).containsKey("links")) {
				links = new Gson().fromJson(map.get(0).get("links").toString(), new TypeToken < ArrayList < HashMap < String, Object >>> () {}.getType());
				links_list.setAdapter(new Links_listAdapter(links));
				_ViewSetHeight(links_list, links.size() * kTHUtil.getDip(getApplicationContext(), 50));
				((BaseAdapter) links_list.getAdapter()).notifyDataSetChanged();
				if (map.get(0).containsKey("about")) {
					links_list.setVisibility(View.GONE);
					segment.setVisibility(View.GONE);
					links_more.setVisibility(View.VISIBLE);
					_typeface(links_more);
				} else {
					links_list.setVisibility(View.VISIBLE);
					segment.setVisibility(View.GONE);
					links_more.setVisibility(View.GONE);
				}
			}
			_text();
		}
	}

	private void _ViewSetHeight(final View _view, final double _num) {
		_view.getLayoutParams().height = (int)(_num);
	}

	public void _text() {
		Glide.with(getApplicationContext()).load(Uri.parse(map.get(0).get("image").toString())).into(imageview1);
		if (sp.getString("quality", "").equals("yes")) {
			if (map.get(0).containsKey("image4k")) {
				_quality("image4k", R.drawable.ic_4k);
			}
		} else {
			_quality("image", R.drawable.ic_hd);
		}
		if (sp.getString("animation", "").equals("yes")) {
			if (map.get(0).containsKey("gif")) {
				_quality("gif", R.drawable.ic_gif);
			}
		}
		_marquee(textview1, map.get(0).get("name").toString());
		_typeface(textview1);
		Toolbar toolbar = findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back);
		if (map.get(0).containsKey("about")) {
			about_segment.setVisibility(View.VISIBLE);
			if (info.get(0).containsKey("tale_".concat(sp.getString("prefix", "")))) {
				_marquee1(about_text, info.get(0).get("tale_".concat(sp.getString("prefix", ""))).toString());
				about_line.setVisibility(View.VISIBLE);
				_typeface(about_hint);
				_typeface(about_text);
			} else {
				about_line.setVisibility(View.GONE);
			}
			if (info.get(0).containsKey("hometown_".concat(sp.getString("prefix", ""))) || info.get(0).containsKey("from_".concat(sp.getString("prefix", "")))) {
				if (info.get(0).containsKey("hometown_".concat(sp.getString("prefix", "")))) {
					hometown_hint.setText(R.string.hometown);
					_marquee(hometown_text, info.get(0).get("hometown_".concat(sp.getString("prefix", ""))).toString());
				} else {
					hometown_hint.setText(R.string.from);
					_marquee(hometown_text, info.get(0).get("from_".concat(sp.getString("prefix", ""))).toString());
				}
				hometown_line.setVisibility(View.VISIBLE);
				_typeface(hometown_hint);
				_typeface(hometown_text);
			} else {
				hometown_line.setVisibility(View.GONE);
			}
			if (info.get(0).containsKey("born_".concat(sp.getString("prefix", ""))) || info.get(0).containsKey("formed_".concat(sp.getString("prefix", "")))) {
				if (info.get(0).containsKey("born_".concat(sp.getString("prefix", "")))) {
					born_hint.setText(R.string.born);
					born_text.setText(info.get(0).get("born_".concat(sp.getString("prefix", ""))).toString());
				} else {
					born_hint.setText(R.string.formed);
					born_text.setText(info.get(0).get("formed_".concat(sp.getString("prefix", ""))).toString());
				}
				born_line.setVisibility(View.VISIBLE);
				_typeface(born_hint);
				_typeface(born_text);
			} else {
				born_line.setVisibility(View.GONE);
			}
			if (info.get(0).containsKey("genre_".concat(sp.getString("prefix", "")))) {
				genre_text.setText(info.get(0).get("genre_".concat(sp.getString("prefix", ""))).toString());
				genre_line.setVisibility(View.VISIBLE);
				_typeface(genre_hint);
				_typeface(genre_text);
			} else {
				genre_line.setVisibility(View.GONE);
			}
		} else {
			about_segment.setVisibility(View.GONE);
		}
		if (map.get(0).containsKey("links")) {
			_typeface(links_hint);
			links_segment.setVisibility(View.VISIBLE);
		} else {
			links_segment.setVisibility(View.GONE);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.artist, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == android.R.id.home) {
			onBackPressed();
		}
		return super.onOptionsItemSelected(item);
	}

	public void _marque(final TextView _textview, final SpannableStringBuilder _text) {
		_textview.setText(_text);
		_textview.setSingleLine(true);
		_textview.setEllipsize(TextUtils.TruncateAt.MARQUEE);
		_textview.setSelected(true);
	}

	public void _marquee(final TextView _textview, final String _text) {
		_textview.setText(_text);
		_textview.setSingleLine(true);
		_textview.setEllipsize(TextUtils.TruncateAt.MARQUEE);
		_textview.setSelected(true);
	}

	public void _marquee1(final TextView _textview, final String _text) {
		_textview.setText(_text);
		_textview.setMaxLines(2);
		_textview.setEllipsize(TextUtils.TruncateAt.END);
		_textview.setSelected(true);
	}

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
				visibility = 0;
			}
		}
	}

	public void _hint(ArrayList<HashMap<String, Object>> _map, int _str) {
		p.setClass(getApplicationContext(), FullActivity.class);
		p.putExtra("data", new Gson().toJson(_map));
		p.putExtra("artist", map.get(0).get("name").toString());
		p.putExtra("title", _str);
		startActivity(p);
	}

	public void _image(String _img) {
		i.setClass(getApplicationContext(), ImageActivity.class);
		i.putExtra("imageq", map.get(0).get(_img).toString());
		i.putExtra("name", map.get(0).get("name").toString());
		i.putExtra("artist", "");
		startActivity(i);
	}

	public void _list(ArrayList<HashMap<String, Object>> _map, Number _param3, int _str, ListView _list) {
		if (_map.get((int) _param3).containsKey("link")) {
			l.setClass(getApplicationContext(), AlbumActivity.class);
			l.putExtra("link", _map.get((int) _param3).get("link").toString());
			startActivity(l);
		} else {
			com.google.android.material.snackbar.Snackbar.make(_list, _str, com.google.android.material.snackbar.Snackbar.LENGTH_SHORT).setAction("ok", _view -> {}).show();
		}
	}

	public void _quality(String _key, int _res) {
		Glide.with(getApplicationContext()).load(Uri.parse(map.get(0).get(_key).toString())).into(imageview1);
		image_quality.setImageResource(_res);
		image_quality.setVisibility(View.VISIBLE);
	}

	public void _typeface(TextView _txt) {
		_txt.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/moscow.ttf"), Typeface.NORMAL);
	}

	public class Latest_release_listAdapter extends BaseAdapter {
		ArrayList < HashMap < String, Object >> _data;
		public Latest_release_listAdapter(ArrayList < HashMap < String, Object >> _arr) {
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
				_view = _inflater.inflate(R.layout.playlists, null);
			}
			final LinearLayout linear1 = _view.findViewById(R.id.linear1);
			final ImageView imageview1 = _view.findViewById(R.id.imageview1);
			final TextView Name = _view.findViewById(R.id.Name);
			final TextView album = _view.findViewById(R.id.album);
			final TextView Release = _view.findViewById(R.id.Release);
			final ImageView more = _view.findViewById(R.id.more);
			if (!latest.get(_position).containsKey("link")) {
				Release.setAlpha((float) 0.5d);
				album.setAlpha((float) 0.5d);
				imageview1.setAlpha((float) 0.5d);
				Name.setAlpha((float) 0.5d);
				more.setAlpha((float) 0.5d);
			}
			if (_position < 1) {
				if (latest.get(_position).get("explicit").toString().equals("yes")) {
					if (latest.get(_position).containsKey("prefix")) {
						lateststr = latest.get(_position).get("name").toString().concat(" ").concat(latest.get(_position).get("prefix").toString()).concat(" 🅴");
					} else {
						lateststr = latest.get(_position).get("name").toString().concat(" 🅴");
					}
				} else {
					if (latest.get(_position).containsKey("prefix")) {
						lateststr = latest.get(_position).get("name").toString().concat(" ").concat(latest.get(_position).get("prefix").toString());
					} else {
						lateststr = latest.get(_position).get("name").toString();
					}
				}
				SpannableStringBuilder ssb = new SpannableStringBuilder(lateststr);
				int color = ContextCompat.getColor(ArtistActivity.this, R.color.text2);
				ForegroundColorSpan fcsRed = new ForegroundColorSpan(color);
				ssb.setSpan(fcsRed, latest.get(_position).get("name").toString().length(), lateststr.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
				_marque(Name, ssb);
				if (latest.get(_position).containsKey("image")) {
					Glide.with(getApplicationContext()).load(Uri.parse(latest.get(_position).get("image").toString())).into(imageview1);
				}
				if (latest.get(_position).get("artist").toString().equals(map.get(0).get("name").toString())) {
					album.setVisibility(View.GONE);
				} else {
					album.setVisibility(View.VISIBLE);
					_marquee(album, latest.get(_position).get("artist").toString());
				}
				if (latest.get(_position).containsKey("release_".concat(sp.getString("prefix", ""))) && latest.get(_position).containsKey("time_".concat(sp.getString("prefix", "")))) {
					_marquee(Release, latest.get(_position).get("release_".concat(sp.getString("prefix", ""))).toString().concat(" • ".concat(latest.get(_position).get("time_".concat(sp.getString("prefix", ""))).toString())));
				}
				_typeface(Name);
				_typeface(album);
				_typeface(Release);
			} else {
				linear1.setVisibility(View.GONE);
			}
			return _view;
		}
	}

	public class Top_songs_listAdapter extends BaseAdapter {
		ArrayList < HashMap < String, Object >> _data;
		public Top_songs_listAdapter(ArrayList < HashMap < String, Object >> _arr) {
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
				_view = _inflater.inflate(R.layout.playlists, null);
			}
			final ImageView imageview1 = _view.findViewById(R.id.imageview1);
			final TextView Name = _view.findViewById(R.id.Name);
			final TextView album = _view.findViewById(R.id.album);
			final TextView Release = _view.findViewById(R.id.Release);
			if (top.get(_position).get("explicit").toString().equals("yes")) {
				if (top.get(_position).containsKey("prefix")) {
					topstr = top.get(_position).get("name").toString().concat(" ").concat(top.get(_position).get("prefix").toString()).concat(" 🅴");
				} else {
					topstr = top.get(_position).get("name").toString().concat(" 🅴");
				}
			} else {
				if (top.get(_position).containsKey("prefix")) {
					topstr = top.get(_position).get("name").toString().concat(" ").concat(top.get(_position).get("prefix").toString());
				} else {
					topstr = top.get(_position).get("name").toString();
				}
			}
			SpannableStringBuilder ssb = new SpannableStringBuilder(topstr);
			int color = ContextCompat.getColor(ArtistActivity.this, R.color.text2);
			ForegroundColorSpan fcsRed = new ForegroundColorSpan(color);
			ssb.setSpan(fcsRed, top.get(_position).get("name").toString().length(), topstr.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
			_marque(Name, ssb);
			if (top.get(_position).containsKey("image")) {
				Glide.with(getApplicationContext()).load(Uri.parse(top.get(_position).get("image").toString())).into(imageview1);
			}
			if (top.get(_position).get("artist").toString().equals(map.get(0).get("name").toString())) {
				album.setVisibility(View.GONE);
			} else {
				album.setVisibility(View.VISIBLE);
				_marquee(album, top.get(_position).get("artist").toString());
			}
			if (top.get(_position).containsKey("release_".concat(sp.getString("prefix", ""))) && top.get(_position).containsKey("time_".concat(sp.getString("prefix", "")))) {
				_marquee(Release, top.get(_position).get("release_".concat(sp.getString("prefix", ""))).toString().concat(" • ".concat(top.get(_position).get("time_".concat(sp.getString("prefix", ""))).toString())));
			}
			_typeface(Name);
			_typeface(album);
			_typeface(Release);
			return _view;
		}
	}

	public class Albums_listAdapter extends BaseAdapter {
		ArrayList < HashMap < String, Object >> _data;
		public Albums_listAdapter(ArrayList < HashMap < String, Object >> _arr) {
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
				_view = _inflater.inflate(R.layout.playlists, null);
			}
			final LinearLayout linear1 = _view.findViewById(R.id.linear1);
			final ImageView imageview1 = _view.findViewById(R.id.imageview1);
			final TextView Name = _view.findViewById(R.id.Name);
			final TextView album = _view.findViewById(R.id.album);
			final TextView Release = _view.findViewById(R.id.Release);
			final ImageView more = _view.findViewById(R.id.more);
			if (!albums.get(_position).containsKey("link")) {
				Release.setAlpha((float) 0.5d);
				album.setAlpha((float) 0.5d);
				imageview1.setAlpha((float) 0.5d);
				Name.setAlpha((float) 0.5d);
				more.setAlpha((float) 0.5d);
			}
			if (_position < 5) {
				if (albums.get(_position).get("explicit").toString().equals("yes")) {
					if (albums.get(_position).containsKey("prefix")) {
						albumsstr = albums.get(_position).get("name").toString().concat(" ").concat(albums.get(_position).get("prefix").toString()).concat(" 🅴");
					} else {
						albumsstr = albums.get(_position).get("name").toString().concat(" 🅴");
					}
				} else {
					if (albums.get(_position).containsKey("prefix")) {
						albumsstr = albums.get(_position).get("name").toString().concat(" ").concat(albums.get(_position).get("prefix").toString());
					} else {
						albumsstr = albums.get(_position).get("name").toString();
					}
				}
				SpannableStringBuilder ssb = new SpannableStringBuilder(albumsstr);
				int color = ContextCompat.getColor(ArtistActivity.this, R.color.text2);
				ForegroundColorSpan fcsRed = new ForegroundColorSpan(color);
				ssb.setSpan(fcsRed, albums.get(_position).get("name").toString().length(), albumsstr.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
				_marque(Name, ssb);
				if (albums.get(_position).containsKey("image")) {
					Glide.with(getApplicationContext()).load(Uri.parse(albums.get(_position).get("image").toString())).into(imageview1);
				}
				if (albums.get(_position).get("artist").toString().equals(map.get(0).get("name").toString())) {
					album.setVisibility(View.GONE);
				} else {
					album.setVisibility(View.VISIBLE);
					_marquee(album, albums.get(_position).get("artist").toString());
				}
				if (albums.get(_position).containsKey("release_".concat(sp.getString("prefix", ""))) && albums.get(_position).containsKey("time_".concat(sp.getString("prefix", "")))) {
					_marquee(Release, albums.get(_position).get("release_".concat(sp.getString("prefix", ""))).toString().concat(" • ".concat(albums.get(_position).get("time_".concat(sp.getString("prefix", ""))).toString())));
				}
				_typeface(Name);
				_typeface(album);
				_typeface(Release);
			} else {
				linear1.setVisibility(View.GONE);
			}
			return _view;
		}
	}

	public class Singles_listAdapter extends BaseAdapter {
		ArrayList < HashMap < String, Object >> _data;
		public Singles_listAdapter(ArrayList < HashMap < String, Object >> _arr) {
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
				_view = _inflater.inflate(R.layout.playlists, null);
			}
			final LinearLayout linear1 = _view.findViewById(R.id.linear1);
			final ImageView imageview1 = _view.findViewById(R.id.imageview1);
			final TextView Name = _view.findViewById(R.id.Name);
			final TextView album = _view.findViewById(R.id.album);
			final TextView Release = _view.findViewById(R.id.Release);
			final ImageView more = _view.findViewById(R.id.more);
			if (!singles.get(_position).containsKey("link")) {
				Release.setAlpha((float) 0.5d);
				album.setAlpha((float) 0.5d);
				imageview1.setAlpha((float) 0.5d);
				Name.setAlpha((float) 0.5d);
				more.setAlpha((float) 0.5d);
			}
			if (_position < 5) {
				if (singles.get(_position).get("explicit").toString().equals("yes")) {
					if (singles.get(_position).containsKey("prefix")) {
						singlesstr = singles.get(_position).get("name").toString().concat(" ").concat(singles.get(_position).get("prefix").toString()).concat(" 🅴");
					} else {
						singlesstr = singles.get(_position).get("name").toString().concat(" 🅴");
					}
				} else {
					if (singles.get(_position).containsKey("prefix")) {
						singlesstr = singles.get(_position).get("name").toString().concat(" ").concat(singles.get(_position).get("prefix").toString());
					} else {
						singlesstr = singles.get(_position).get("name").toString();
					}
				}
				SpannableStringBuilder ssb = new SpannableStringBuilder(singlesstr);
				int color = ContextCompat.getColor(ArtistActivity.this, R.color.text2);
				ForegroundColorSpan fcsRed = new ForegroundColorSpan(color);
				ssb.setSpan(fcsRed, singles.get(_position).get("name").toString().length(), singlesstr.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
				_marque(Name, ssb);
				if (singles.get(_position).containsKey("image")) {
					Glide.with(getApplicationContext()).load(Uri.parse(singles.get(_position).get("image").toString())).into(imageview1);
				}
				if (singles.get(_position).get("artist").toString().equals(map.get(0).get("name").toString())) {
					album.setVisibility(View.GONE);
				} else {
					album.setVisibility(View.VISIBLE);
					_marquee(album, singles.get(_position).get("artist").toString());
				}
				if (singles.get(_position).containsKey("release_".concat(sp.getString("prefix", ""))) && singles.get(_position).containsKey("time_".concat(sp.getString("prefix", "")))) {
					_marquee(Release, singles.get(_position).get("release_".concat(sp.getString("prefix", ""))).toString().concat(" • ".concat(singles.get(_position).get("time_".concat(sp.getString("prefix", ""))).toString())));
				}
				_typeface(Name);
				_typeface(album);
				_typeface(Release);
			} else {
				linear1.setVisibility(View.GONE);
			}
			return _view;
		}
	}

	public class Live_listAdapter extends BaseAdapter {
		ArrayList < HashMap < String, Object >> _data;
		public Live_listAdapter(ArrayList < HashMap < String, Object >> _arr) {
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
				_view = _inflater.inflate(R.layout.playlists, null);
			}
			final LinearLayout linear1 = _view.findViewById(R.id.linear1);
			final ImageView imageview1 = _view.findViewById(R.id.imageview1);
			final TextView Name = _view.findViewById(R.id.Name);
			final TextView album = _view.findViewById(R.id.album);
			final TextView Release = _view.findViewById(R.id.Release);
			final ImageView more = _view.findViewById(R.id.more);
			if (!live.get(_position).containsKey("link")) {
				Release.setAlpha((float) 0.5d);
				album.setAlpha((float) 0.5d);
				imageview1.setAlpha((float) 0.5d);
				Name.setAlpha((float) 0.5d);
				more.setAlpha((float) 0.5d);
			}
			if (_position < 5) {
				if (live.get(_position).get("explicit").toString().equals("yes")) {
					if (live.get(_position).containsKey("prefix")) {
						livestr = live.get(_position).get("name").toString().concat(" ").concat(live.get(_position).get("prefix").toString()).concat(" 🅴");
					} else {
						livestr = live.get(_position).get("name").toString().concat(" 🅴");
					}
				} else {
					if (live.get(_position).containsKey("prefix")) {
						livestr = live.get(_position).get("name").toString().concat(" ").concat(live.get(_position).get("prefix").toString());
					} else {
						livestr = live.get(_position).get("name").toString();
					}
				}
				SpannableStringBuilder ssb = new SpannableStringBuilder(livestr);
				int color = ContextCompat.getColor(ArtistActivity.this, R.color.text2);
				ForegroundColorSpan fcsRed = new ForegroundColorSpan(color);
				ssb.setSpan(fcsRed, live.get(_position).get("name").toString().length(), livestr.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
				_marque(Name, ssb);
				if (live.get(_position).containsKey("image")) {
					Glide.with(getApplicationContext()).load(Uri.parse(live.get(_position).get("image").toString())).into(imageview1);
				}
				if (live.get(_position).get("artist").toString().equals(map.get(0).get("name").toString())) {
					album.setVisibility(View.GONE);
				} else {
					album.setVisibility(View.VISIBLE);
					_marquee(album, live.get(_position).get("artist").toString());
				}
				if (live.get(_position).containsKey("release_".concat(sp.getString("prefix", ""))) && live.get(_position).containsKey("time_".concat(sp.getString("prefix", "")))) {
					_marquee(Release, live.get(_position).get("release_".concat(sp.getString("prefix", ""))).toString().concat(" • ".concat(live.get(_position).get("time_".concat(sp.getString("prefix", ""))).toString())));
				}
				_typeface(Name);
				_typeface(album);
				_typeface(Release);
			} else {
				linear1.setVisibility(View.GONE);
			}
			return _view;
		}
	}

	public class Compilations_listAdapter extends BaseAdapter {
		ArrayList < HashMap < String, Object >> _data;
		public Compilations_listAdapter(ArrayList < HashMap < String, Object >> _arr) {
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
				_view = _inflater.inflate(R.layout.playlists, null);
			}
			final LinearLayout linear1 = _view.findViewById(R.id.linear1);
			final ImageView imageview1 = _view.findViewById(R.id.imageview1);
			final TextView Name = _view.findViewById(R.id.Name);
			final TextView album = _view.findViewById(R.id.album);
			final TextView Release = _view.findViewById(R.id.Release);
			final ImageView more = _view.findViewById(R.id.more);
			if (!compilations.get(_position).containsKey("link")) {
				Release.setAlpha((float) 0.5d);
				album.setAlpha((float) 0.5d);
				imageview1.setAlpha((float) 0.5d);
				Name.setAlpha((float) 0.5d);
				more.setAlpha((float) 0.5d);
			}
			if (_position < 5) {
				if (compilations.get(_position).get("explicit").toString().equals("yes")) {
					if (compilations.get(_position).containsKey("prefix")) {
						compilationsstr = compilations.get(_position).get("name").toString().concat(" ").concat(compilations.get(_position).get("prefix").toString()).concat(" 🅴");
					} else {
						compilationsstr = compilations.get(_position).get("name").toString().concat(" 🅴");
					}
				} else {
					if (compilations.get(_position).containsKey("prefix")) {
						compilationsstr = compilations.get(_position).get("name").toString().concat(" ").concat(compilations.get(_position).get("prefix").toString());
					} else {
						compilationsstr = compilations.get(_position).get("name").toString();
					}
				}
				SpannableStringBuilder ssb = new SpannableStringBuilder(compilationsstr);
				int color = ContextCompat.getColor(ArtistActivity.this, R.color.text2);
				ForegroundColorSpan fcsRed = new ForegroundColorSpan(color);
				ssb.setSpan(fcsRed, compilations.get(_position).get("name").toString().length(), compilationsstr.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
				_marque(Name, ssb);
				if (compilations.get(_position).containsKey("image")) {
					Glide.with(getApplicationContext()).load(Uri.parse(compilations.get(_position).get("image").toString())).into(imageview1);
				}
				if (compilations.get(_position).get("artist").toString().equals(map.get(0).get("name").toString())) {
					album.setVisibility(View.GONE);
				} else {
					album.setVisibility(View.VISIBLE);
					_marquee(album, compilations.get(_position).get("artist").toString());
				}
				if (compilations.get(_position).containsKey("release_".concat(sp.getString("prefix", ""))) && compilations.get(_position).containsKey("time_".concat(sp.getString("prefix", "")))) {
					_marquee(Release, compilations.get(_position).get("release_".concat(sp.getString("prefix", ""))).toString().concat(" • ".concat(compilations.get(_position).get("time_".concat(sp.getString("prefix", ""))).toString())));
				}
				_typeface(Name);
				_typeface(album);
				_typeface(Release);
			} else {
				linear1.setVisibility(View.GONE);
			}
			return _view;
		}
	}

	public class Appears_listAdapter extends BaseAdapter {
		ArrayList < HashMap < String, Object >> _data;
		public Appears_listAdapter(ArrayList < HashMap < String, Object >> _arr) {
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
				_view = _inflater.inflate(R.layout.playlists, null);
			}
			final LinearLayout linear1 = _view.findViewById(R.id.linear1);
			final ImageView imageview1 = _view.findViewById(R.id.imageview1);
			final TextView Name = _view.findViewById(R.id.Name);
			final TextView album = _view.findViewById(R.id.album);
			final TextView Release = _view.findViewById(R.id.Release);
			final ImageView more = _view.findViewById(R.id.more);
			if (!appears.get(_position).containsKey("link")) {
				Release.setAlpha((float) 0.5d);
				album.setAlpha((float) 0.5d);
				imageview1.setAlpha((float) 0.5d);
				Name.setAlpha((float) 0.5d);
				more.setAlpha((float) 0.5d);
			}
			if (_position < 5) {
				if (appears.get(_position).get("explicit").toString().equals("yes")) {
					if (appears.get(_position).containsKey("prefix")) {
						appearsstr = appears.get(_position).get("name").toString().concat(" ").concat(appears.get(_position).get("prefix").toString()).concat(" 🅴");
					} else {
						appearsstr = appears.get(_position).get("name").toString().concat(" 🅴");
					}
				} else {
					if (appears.get(_position).containsKey("prefix")) {
						appearsstr = appears.get(_position).get("name").toString().concat(" ").concat(appears.get(_position).get("prefix").toString());
					} else {
						appearsstr = appears.get(_position).get("name").toString();
					}
				}
				SpannableStringBuilder ssb = new SpannableStringBuilder(appearsstr);
				int color = ContextCompat.getColor(ArtistActivity.this, R.color.text2);
				ForegroundColorSpan fcsRed = new ForegroundColorSpan(color);
				ssb.setSpan(fcsRed, appears.get(_position).get("name").toString().length(), appearsstr.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
				_marque(Name, ssb);
				if (appears.get(_position).containsKey("image")) {
					Glide.with(getApplicationContext()).load(Uri.parse(appears.get(_position).get("image").toString())).into(imageview1);
				}
				if (appears.get(_position).get("artist").toString().equals(map.get(0).get("name").toString())) {
					album.setVisibility(View.GONE);
				} else {
					album.setVisibility(View.VISIBLE);
					_marquee(album, appears.get(_position).get("artist").toString());
				}
				if (appears.get(_position).containsKey("release_".concat(sp.getString("prefix", ""))) && appears.get(_position).containsKey("time_".concat(sp.getString("prefix", "")))) {
					_marquee(Release, appears.get(_position).get("release_".concat(sp.getString("prefix", ""))).toString().concat(" • ".concat(appears.get(_position).get("time_".concat(sp.getString("prefix", ""))).toString())));
				}
				_typeface(Name);
				_typeface(album);
				_typeface(Release);
			} else {
				linear1.setVisibility(View.GONE);
			}
			return _view;
		}
	}

	public class Links_listAdapter extends BaseAdapter {
		ArrayList < HashMap < String, Object >> _data;
		public Links_listAdapter(ArrayList < HashMap < String, Object >> _arr) {
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
				_view = _inflater.inflate(R.layout.links, null);
			}
			final ImageView link_image = _view.findViewById(R.id.link_image);
			final TextView link_text = _view.findViewById(R.id.link_text);
			final TextView link_adress = _view.findViewById(R.id.link_adress);
			Glide.with(getApplicationContext()).load(Uri.parse(links.get(_position).get("image").toString())).into(link_image);
			link_text.setText(links.get(_position).get("text_".concat(sp.getString("prefix", ""))).toString());
			_marquee(link_adress, links.get(_position).get("link").toString());
			_typeface(link_text);
			_typeface(link_adress);
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