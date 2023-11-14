package ru.kartofan.theme.music.app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.*;

import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
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
import android.widget.ImageView;
import android.content.Intent;
import android.net.Uri;
import android.app.Activity;
import android.content.SharedPreferences;
import java.util.Timer;
import java.util.TimerTask;
import android.widget.AdapterView;
import android.view.View;
import com.bumptech.glide.Glide;
import android.graphics.Typeface;
import com.google.android.material.appbar.SubtitleCollapsingToolbarLayout;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;

public class AlbumActivity extends AppCompatActivity {

	private final Timer _timer = new Timer();
	private String str = "";
	private final String str1 = "";
	private String about = "";
	private double myster = 0;
	private double explicit = 0;
	private double num = 0;
	private double myst = 0;
	private final double pos = 0;
	private ArrayList < HashMap < String, Object >> map = new ArrayList < > ();
	private ArrayList < HashMap < String, Object >> play = new ArrayList < > ();
	private ArrayList < HashMap < String, Object >> other = new ArrayList < > ();
	private ArrayList < HashMap < String, Object >> more = new ArrayList < > ();
	private ArrayList < HashMap < String, Object >> artists = new ArrayList < > ();
	private ArrayList < HashMap < String, Object >> uri = new ArrayList < > ();
	private LinearLayout linear2;
	private LinearLayout linear5;
	private LinearLayout other_versions_linear;
	private LinearLayout more_by_linear;
	private LinearLayout featured_artists_linear;
	private TextView tale;
	private ListView listview1;
	private ListView more_by_list;
	private ListView featured_artists_list;
	private ListView other_versions_list;
	private LinearLayout linear4;
	private ImageView imageview1;
	private TextView name;
	private TextView artist;
	private TextView date;
	private TextView time;
	private TextView copyright;
	private TextView textview1;
	private TextView other_versions_text;
	private TextView more_by_text;
	private TextView featured_artists_text;
	private final Intent i = new Intent();
	private final Intent l = new Intent();
	private final Intent p = new Intent();
	private SharedPreferences sp;
	private TimerTask t;

	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);
		setContentView(R.layout.album);
		initialize(_savedInstanceState);
		initializeLogic();
	}

	private void initialize(Bundle _savedInstanceState) {
		linear2 = (LinearLayout) findViewById(R.id.linear2);
		linear5 = (LinearLayout) findViewById(R.id.linear5);
		tale = (TextView) findViewById(R.id.tale);
		listview1 = (ListView) findViewById(R.id.listview1);
		linear4 = (LinearLayout) findViewById(R.id.linear4);
		imageview1 = (ImageView) findViewById(R.id.imageview1);
		name = (TextView) findViewById(R.id.name);
		artist = (TextView) findViewById(R.id.artist);
		date = (TextView) findViewById(R.id.date);
		time = (TextView) findViewById(R.id.time);
		copyright = (TextView) findViewById(R.id.copyright);
		textview1 = (TextView) findViewById(R.id.textview1);
		other_versions_linear = (LinearLayout) findViewById(R.id.other_versions_linear);
		other_versions_text = (TextView) findViewById(R.id.other_versions_text);
		other_versions_list = (ListView) findViewById(R.id.other_versions_list);
		more_by_linear = (LinearLayout) findViewById(R.id.more_by_linear);
		more_by_text = (TextView) findViewById(R.id.more_by_text);
		more_by_list = (ListView) findViewById(R.id.more_by_list);
		featured_artists_linear = (LinearLayout) findViewById(R.id.featured_artists_linear);
		featured_artists_text = (TextView) findViewById(R.id.featured_artists_text);
		featured_artists_list = (ListView) findViewById(R.id.featured_artists_list);
		sp = getSharedPreferences("sp", Activity.MODE_PRIVATE);

		tale.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				final com.google.android.material.bottomsheet.BottomSheetDialog bs_base = new com.google.android.material.bottomsheet.BottomSheetDialog(AlbumActivity.this);
				bs_base.setCancelable(true);
				View layBase = getLayoutInflater().inflate(R.layout.bottom, null);
				bs_base.setContentView(layBase);
				TextView text = (TextView) layBase.findViewById(R.id.text);
				text.setText(map.get((int) 0).get("tale_".concat(sp.getString("prefix", ""))).toString());
				text.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/moscow.ttf"), Typeface.NORMAL);
				TextView about = (TextView) layBase.findViewById(R.id.about);
				about.setVisibility(View.VISIBLE);
				about.setText(map.get((int) 0).get("name").toString());
				about.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/moscow.ttf"), Typeface.NORMAL);
				bs_base.show();
			}
		});

		listview1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView < ? > _param1, View _param2, int _param3, long _param4) {
				final int _position = _param3;
				if (play.get((int) _position).containsKey("mystery")) {} else {
					if (sp.getString("explicit", "").equals("no")) {
						if (play.get((int) _position).get("explicit").toString().equals("yes")) {
							com.google.android.material.snackbar.Snackbar.make(listview1, R.string.not_for_children, com.google.android.material.snackbar.Snackbar.LENGTH_SHORT).setAction("Ok", new View.OnClickListener() {
								@Override
								public void onClick(View _view) {}
							}).show();
						} else {
							if (play.get((int) _position).containsKey("link")) {
								i.setClass(getApplicationContext(), MusicActivity.class);
								i.putExtra("link", play.get((int) _position).get("link").toString());
								startActivity(i);
							} else {
								com.google.android.material.snackbar.Snackbar.make(listview1, R.string.song_will_be_added_soon, com.google.android.material.snackbar.Snackbar.LENGTH_SHORT).setAction("Ok", new View.OnClickListener() {
									@Override
									public void onClick(View _view) {}
								}).show();
							}
						}
					} else {
						if (play.get((int) _position).containsKey("link")) {
							i.setClass(getApplicationContext(), MusicActivity.class);
							i.putExtra("link", play.get((int) _position).get("link").toString());
							startActivity(i);
						} else {
							com.google.android.material.snackbar.Snackbar.make(listview1, R.string.song_will_be_added_soon, com.google.android.material.snackbar.Snackbar.LENGTH_SHORT).setAction("Ok", new View.OnClickListener() {
								@Override
								public void onClick(View _view) {}
							}).show();
						}
					}
				}
			}
		});

		other_versions_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView < ? > _param1, View _param2, int _param3, long _param4) {
				final int _position = _param3;
				if (other.get((int) _position).containsKey("link")) {
					l.setClass(getApplicationContext(), AlbumActivity.class);
					l.putExtra("link", other.get((int) _position).get("link").toString());
					startActivity(l);
				} else {
					com.google.android.material.snackbar.Snackbar.make(other_versions_list, R.string.album_will_be_added_soon, com.google.android.material.snackbar.Snackbar.LENGTH_SHORT).setAction("ok", new View.OnClickListener() {
						@Override
						public void onClick(View _view) {}
					}).show();
				}
			}
		});
		more_by_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView < ? > _param1, View _param2, int _param3, long _param4) {
				final int _position = _param3;
				if (more.get((int) _position).containsKey("link")) {
					l.setClass(getApplicationContext(), AlbumActivity.class);
					l.putExtra("link", more.get((int) _position).get("link").toString());
					startActivity(l);
				} else {
					com.google.android.material.snackbar.Snackbar.make(more_by_list, R.string.album_will_be_added_soon, com.google.android.material.snackbar.Snackbar.LENGTH_SHORT).setAction("ok", new View.OnClickListener() {
						@Override
						public void onClick(View _view) {}
					}).show();
				}
			}
		});
		featured_artists_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView < ? > _param1, View _param2, int _param3, long _param4) {
				final int _position = _param3;
				if (artists.get((int) _position).containsKey("link")) {
					i.setClass(getApplicationContext(), ArtistActivity.class);
					i.putExtra("link", artists.get((int) _position).get("link").toString());
					startActivity(i);
				} else {
					com.google.android.material.snackbar.Snackbar.make(featured_artists_list, R.string.artist_will_be_added_soon, com.google.android.material.snackbar.Snackbar.LENGTH_SHORT).setAction("Ok", new View.OnClickListener() {
						@Override
						public void onClick(View _view) {}
					}).show();
				}
			}
		});

		other_versions_text.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				p.setClass(getApplicationContext(), FullActivity.class);
				p.putExtra("data", new Gson().toJson(other));
				p.putExtra("artist", map.get((int) 0).get("artist").toString());
				p.putExtra("title", getString(R.string.other_versions_1));
				startActivity(p);
			}
		});
		more_by_text.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				p.setClass(getApplicationContext(), FullActivity.class);
				p.putExtra("data", new Gson().toJson(more));
				p.putExtra("artist", map.get((int) 0).get("artist").toString());
				p.putExtra("title", getString(R.string.more_by).concat(" ").concat(map.get((int) 0).get("artist").toString()));
				startActivity(p);
			}
		});

		artist.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (uri.size() > 1) {
					i.setClass(getApplicationContext(), FullActivity.class);
					i.putExtra("data", new Gson().toJson(uri));
					i.putExtra("artist", map.get((int) 0).get("artist").toString());
					i.putExtra("title", getString(R.string.featured_artists_1));
					startActivity(i);
				} else {
					i.setClass(getApplicationContext(), ArtistActivity.class);
					i.putExtra("link", uri.get((int) 0).get("link").toString());
					startActivity(i);
				}
			}
		});

		featured_artists_text.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				p.setClass(getApplicationContext(), FullActivity.class);
				p.putExtra("data", new Gson().toJson(artists));
				p.putExtra("artist", map.get((int) 0).get("artist").toString());
				p.putExtra("title", getString(R.string.featured_artists_1));
				startActivity(p);
			}
		});

		imageview1.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				if (map.get((int) 0).containsKey("image4k")) {
					if (sp.getString("quality", "").equals("mobile")) {
						i.setClass(getApplicationContext(), ImageActivity.class);
						i.putExtra("imageq", map.get((int) 0).get("image4k").toString());
						startActivity(i);
					} else {
						i.setClass(getApplicationContext(), ImageActivity.class);
						i.putExtra("imageq", map.get((int) 0).get("image").toString());
						startActivity(i);
					}
				} else {
					i.setClass(getApplicationContext(), ImageActivity.class);
					i.putExtra("imageq", map.get((int) 0).get("image").toString());
					startActivity(i);
				}
			}
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
		if (Build.VERSION.SDK_INT >=23) {
			getWindow().setFlags(WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_DEFAULT, WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_DEFAULT);
			getWindow().setStatusBarColor(Color.TRANSPARENT);
		}
		new BackTask().execute(getIntent().getStringExtra("link"));
		other_versions_linear.setVisibility(View.GONE);
		more_by_linear.setVisibility(View.GONE);
		featured_artists_linear.setVisibility(View.GONE);
		other_versions_text.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/moscow.ttf"), Typeface.NORMAL);
		more_by_text.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/moscow.ttf"), Typeface.NORMAL);
		featured_artists_text.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/moscow.ttf"), Typeface.NORMAL);
	}

	@Override
	protected void onActivityResult(int _requestCode, int _resultCode, Intent _data) {

		super.onActivityResult(_requestCode, _resultCode, _data);

		switch (_requestCode) {
			default:
				break;
		}
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
			play = new Gson().fromJson(map.get((int) 0).get("songs").toString(), new TypeToken < ArrayList < HashMap < String, Object >>> () {}.getType());
			uri = new Gson().fromJson(map.get((int) 0).get("artist_uri").toString(), new TypeToken<ArrayList<HashMap<String, Object>>>() {}.getType());
			listview1.setAdapter(new AlbumActivity.Listview1Adapter(play));
			num = 0;
			for (int _repeat21 = 0; _repeat21 < (int)(play.size()); _repeat21++) {
				if (play.get((int) num).containsKey("mystery")) {
					myst++;
				}
				num++;
			}
			_ViewSetHeight(listview1, (play.size() * SketchwareUtil.getDip(getApplicationContext(), (int)(51))) - (myst * SketchwareUtil.getDip(getApplicationContext(), (int)(26))));
			((BaseAdapter) listview1.getAdapter()).notifyDataSetChanged();
			_text();
			if (map.get(0).containsKey("other")) {
				other_versions_linear.setVisibility(View.VISIBLE);
				other = new Gson().fromJson(map.get((int) 0).get("other").toString(), new TypeToken < ArrayList < HashMap < String, Object >>> () {}.getType());
				if (other.size() < 5) {
					_ViewSetHeight(other_versions_list, other.size() * SketchwareUtil.getDip(getApplicationContext(), (int)(77)));
				} else {
					_ViewSetHeight(other_versions_list, 5 * SketchwareUtil.getDip(getApplicationContext(), (int)(77)));
				}
				other_versions_text.setText(getString(R.string.other_versions_1).concat(" (").concat(String.valueOf(other.size())).concat(") >"));
				other_versions_list.setAdapter(new AlbumActivity.Other_listAdapter(other));
				((BaseAdapter) other_versions_list.getAdapter()).notifyDataSetChanged();
			}
			if (map.get(0).containsKey("more")) {
				more_by_linear.setVisibility(View.VISIBLE);
				more = new Gson().fromJson(map.get((int) 0).get("more").toString(), new TypeToken < ArrayList < HashMap < String, Object >>> () {}.getType());
				if (more.size() < 5) {
					_ViewSetHeight(more_by_list, more.size() * SketchwareUtil.getDip(getApplicationContext(), (int)(77)));
				} else {
					_ViewSetHeight(more_by_list, 5 * SketchwareUtil.getDip(getApplicationContext(), (int)(77)));
				}
				more_by_text.setText(getString(R.string.more_by).concat(" ").concat(map.get((int) 0).get("artist").toString().concat(" (").concat(String.valueOf(more.size())).concat(") >")));
				more_by_list.setAdapter(new AlbumActivity.More_listAdapter(more));
				((BaseAdapter) more_by_list.getAdapter()).notifyDataSetChanged();
			}
			if (map.get(0).containsKey("artists")) {
				featured_artists_linear.setVisibility(View.VISIBLE);
				artists = new Gson().fromJson(map.get((int) 0).get("artists").toString(), new TypeToken < ArrayList < HashMap < String, Object >>> () {}.getType());
				if (artists.size() < 5) {
					_ViewSetHeight(featured_artists_list, artists.size() * SketchwareUtil.getDip(getApplicationContext(), (int)(64)));
				} else {
					_ViewSetHeight(featured_artists_list, 5 * SketchwareUtil.getDip(getApplicationContext(), (int)(64)));
					featured_artists_text.setText(getString(R.string.featured_artists_1).concat(" (").concat(String.valueOf(artists.size())).concat(") >"));
				}
				featured_artists_list.setAdapter(new AlbumActivity.ArtistsAdapter(artists));
				((BaseAdapter) featured_artists_list.getAdapter()).notifyDataSetChanged();
			}
		}
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

	public void _text() {
		if (map.get((int) 0).containsKey("tale_".concat(sp.getString("prefix", "")))) {
			tale.setVisibility(View.VISIBLE);
			about = map.get((int) 0).get("tale_".concat(sp.getString("prefix", ""))).toString();
			_marquee1(tale, map.get((int) 0).get("tale_".concat(sp.getString("prefix", ""))).toString());
			tale.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/moscow.ttf"), Typeface.NORMAL);
		} else {
			tale.setVisibility(View.GONE);
		}
		_marquee(name, map.get((int) 0).get("name").toString());
		_marquee(artist, map.get((int) 0).get("artist").toString().concat(" • ".concat(map.get((int) 0).get("genre_".concat(sp.getString("prefix", ""))).toString().concat(" • ".concat(map.get((int) 0).get("release").toString())))));
		sp.edit().putString("artist", map.get((int) 0).get("artist").toString()).commit();
		date.setText(map.get((int) 0).get("date_".concat(sp.getString("prefix", ""))).toString());
		time.setText(map.get((int) 0).get("number_".concat(sp.getString("prefix", ""))).toString().concat(" • ".concat(map.get((int) 0).get("time_".concat(sp.getString("prefix", ""))).toString())));
		copyright.setText(map.get((int) 0).get("copyright").toString());
		Toolbar toolbar = findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back);
		final SubtitleCollapsingToolbarLayout subtitleCollapsingToolbarLayout = findViewById(R.id.toolbar_layout);
		subtitleCollapsingToolbarLayout.setTitle(map.get((int) 0).get("name").toString());
		subtitleCollapsingToolbarLayout.setSubtitle(map.get((int) 0).get("artist").toString().concat(" • ".concat(map.get((int) 0).get("genre_".concat(sp.getString("prefix", ""))).toString().concat(" • ".concat(map.get((int) 0).get("release").toString())))));
		Glide.with(getApplicationContext()).load(Uri.parse(map.get((int) 0).get("image").toString())).into(imageview1);
		if (sp.getString("quality", "").equals("mobile")) {
			if (map.get((int) 0).containsKey("image4k")) {
				Glide.with(getApplicationContext()).load(Uri.parse(map.get((int) 0).get("image4k").toString())).into(imageview1);
			}
		} else if (sp.getString("quality", "").equals("wifi")) {
			if (map.get((int) 0).containsKey("image4k")) {
				ConnectivityManager connManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
				NetworkInfo mWifi = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
				if (mWifi.isConnected()) {
					Glide.with(getApplicationContext()).load(Uri.parse(map.get((int) 0).get("image4k").toString())).into(imageview1);
				}
			}
		} else {
			Glide.with(getApplicationContext()).load(Uri.parse(map.get((int) 0).get("image").toString())).into(imageview1);
		}
		if (sp.getString("animation", "").equals("yes")) {
			Glide.with(getApplicationContext()).load(Uri.parse(map.get((int) 0).get("gif").toString())).into(imageview1);
		}
		name.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/moscow.ttf"), Typeface.NORMAL);
		artist.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/moscow.ttf"), Typeface.NORMAL);
		date.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/moscow.ttf"), Typeface.NORMAL);
		copyright.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/moscow.ttf"), Typeface.NORMAL);
		time.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/moscow.ttf"), Typeface.NORMAL);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.artist, menu);
		return true;
	}
	@Override
	public boolean onOptionsItemSelected(@NonNull MenuItem item) {
		if (item.getItemId() == android.R.id.home) {
			onBackPressed();
		}
		return super.onOptionsItemSelected(item);
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
				_view = _inflater.inflate(R.layout.songs, null);
			}

			final LinearLayout tale = (LinearLayout) _view.findViewById(R.id.tale);
			final LinearLayout linear1 = (LinearLayout) _view.findViewById(R.id.linear1);
			final ImageView imageview1 = (ImageView) _view.findViewById(R.id.imageview1);
			final ImageView imageview2 = (ImageView) _view.findViewById(R.id.imageview2);
			final TextView number = (TextView) _view.findViewById(R.id.number);
			final TextView mystery = (TextView) _view.findViewById(R.id.mystery);
			final TextView time = (TextView) _view.findViewById(R.id.time);
			final LinearLayout linear2 = (LinearLayout) _view.findViewById(R.id.linear2);
			final TextView name = (TextView) _view.findViewById(R.id.name);
			final TextView artist = (TextView) _view.findViewById(R.id.artist);
			final ImageView more = (ImageView) _view.findViewById(R.id.more);
			if (!play.get((int) _position).containsKey("link")) {
				number.setAlpha((float) 0.5d);
				name.setAlpha((float) 0.5d);
				artist.setAlpha((float) 0.5d);
				time.setAlpha((float) 0.5d);
				imageview1.setAlpha((float) 0.5d);
				imageview2.setAlpha((float) 0.5d);
				more.setAlpha((float) 0.5d);
			}
			if (play.get((int) _position).containsKey("mystery")) {
				linear1.setVisibility(View.GONE);
				tale.setVisibility(View.VISIBLE);
				mystery.setText(play.get((int) _position).get("mystery").toString());
				mystery.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/moscow.ttf"), Typeface.NORMAL);
				myster = myster + 1;
				sp.edit().putString("myster", String.valueOf((long)(myster))).commit();
			} else {
				linear1.setVisibility(View.VISIBLE);
				tale.setVisibility(View.GONE);
				number.setText(play.get((int) _position).get("number").toString());
				time.setText(play.get((int) _position).get("time").toString());
				_marquee(name, play.get((int) _position).get("name").toString());
				_marquee(artist, play.get((int) _position).get("artist").toString());
				if (play.get((int) _position).get("star").toString().equals("yes")) {
					imageview1.setVisibility(View.VISIBLE);
				} else {
					imageview1.setVisibility(View.GONE);
				}
				if (play.get((int) _position).get("explicit").toString().equals("yes")) {
					imageview2.setVisibility(View.VISIBLE);
				} else {
					imageview2.setVisibility(View.GONE);
				}
				if (play.get((int) _position).get("artist").toString().equals(sp.getString("artist", ""))) {
					artist.setVisibility(View.GONE);
				} else {
					artist.setVisibility(View.VISIBLE);
				}
				if (sp.getString("explicit", "").equals("no")) {
					if (play.get((int) _position).get("explicit").toString().equals("yes")) {
						number.setAlpha((float) 0.5d);
						name.setAlpha((float) 0.5d);
						artist.setAlpha((float) 0.5d);
						time.setAlpha((float) 0.5d);
						imageview1.setAlpha((float) 0.5d);
						imageview2.setAlpha((float) 0.5d);
						more.setAlpha((float) 0.5d);
					}
				}
				number.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/moscow.ttf"), Typeface.NORMAL);
				time.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/moscow.ttf"), Typeface.NORMAL);
				name.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/moscow.ttf"), Typeface.NORMAL);
				artist.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/moscow.ttf"), Typeface.NORMAL);
			}
			_ViewSetHeight(listview1, (play.size() * SketchwareUtil.getDip(getApplicationContext(), (int)(51))) - (myster * SketchwareUtil.getDip(getApplicationContext(), (int)(18))));
			((BaseAdapter) listview1.getAdapter()).notifyDataSetChanged();
			return _view;
		}
	}

	public class Other_listAdapter extends BaseAdapter {
		ArrayList < HashMap < String, Object >> _data;
		public Other_listAdapter(ArrayList < HashMap < String, Object >> _arr) {
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

			final LinearLayout linear1 = (LinearLayout) _view.findViewById(R.id.linear1);
			final ImageView imageview1 = (ImageView) _view.findViewById(R.id.imageview1);
			final ImageView explicit = (ImageView) _view.findViewById(R.id.explicit);
			final TextView Name = (TextView) _view.findViewById(R.id.Name);
			final TextView album = (TextView) _view.findViewById(R.id.album);
			final TextView Release = (TextView) _view.findViewById(R.id.Release);
			if (!other.get((int) _position).containsKey("link")) {
				explicit.setAlpha((float) 0.5d);
				Release.setAlpha((float) 0.5d);
				album.setAlpha((float) 0.5d);
				imageview1.setAlpha((float) 0.5d);
				Name.setAlpha((float) 0.5d);
			}
			if (_position < 5) {
				_marquee(Name, other.get((int) _position).get("name").toString());
				if (other.get((int) _position).containsKey("image")) {
					Glide.with(getApplicationContext()).load(Uri.parse(other.get((int) _position).get("image").toString())).into(imageview1);
				}
				if (other.get((int) 0).get("artist").toString().equals(map.get((int) 0).get("artist").toString())) {
					album.setVisibility(View.GONE);
				} else {
					album.setVisibility(View.VISIBLE);
					_marquee(album, other.get((int) 0).get("artist").toString());
				}
				if (other.get((int) _position).containsKey("release_".concat(sp.getString("prefix", ""))) && other.get((int) _position).containsKey("time_".concat(sp.getString("prefix", "")))) {
					_marquee(Release, other.get((int) _position).get("release_".concat(sp.getString("prefix", ""))).toString().concat(" • ".concat(other.get((int) _position).get("time_".concat(sp.getString("prefix", ""))).toString())));
				}
				if (other.get((int) _position).get("explicit").toString().equals("yes")) {
					explicit.setVisibility(View.VISIBLE);
				} else {
					explicit.setVisibility(View.GONE);
				}
				Name.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/moscow.ttf"), Typeface.NORMAL);
				album.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/moscow.ttf"), Typeface.NORMAL);
				Release.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/moscow.ttf"), Typeface.NORMAL);
			} else {
				linear1.setVisibility(View.GONE);
			}
			return _view;
		}
	}

	public class More_listAdapter extends BaseAdapter {
		ArrayList < HashMap < String, Object >> _data;
		public More_listAdapter(ArrayList < HashMap < String, Object >> _arr) {
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

			final LinearLayout linear1 = (LinearLayout) _view.findViewById(R.id.linear1);
			final ImageView imageview1 = (ImageView) _view.findViewById(R.id.imageview1);
			final ImageView explicit = (ImageView) _view.findViewById(R.id.explicit);
			final TextView Name = (TextView) _view.findViewById(R.id.Name);
			final TextView album = (TextView) _view.findViewById(R.id.album);
			final TextView Release = (TextView) _view.findViewById(R.id.Release);
			if (!more.get((int) _position).containsKey("link")) {
				explicit.setAlpha((float) 0.5d);
				Release.setAlpha((float) 0.5d);
				album.setAlpha((float) 0.5d);
				imageview1.setAlpha((float) 0.5d);
				Name.setAlpha((float) 0.5d);
			}
			if (_position < 5) {
				_marquee(Name, more.get((int) _position).get("name").toString());
				if (more.get((int) _position).containsKey("image")) {
					Glide.with(getApplicationContext()).load(Uri.parse(more.get((int) _position).get("image").toString())).into(imageview1);
				}
				if (more.get((int) 0).get("artist").toString().equals(map.get((int) 0).get("artist").toString())) {
					album.setVisibility(View.GONE);
				} else {
					album.setVisibility(View.VISIBLE);
					_marquee(album, more.get((int) 0).get("artist").toString());
				}
				if (more.get((int) _position).containsKey("release_".concat(sp.getString("prefix", ""))) && more.get((int) _position).containsKey("time_".concat(sp.getString("prefix", "")))) {
					_marquee(Release, more.get((int) _position).get("release_".concat(sp.getString("prefix", ""))).toString().concat(" • ".concat(more.get((int) _position).get("time_".concat(sp.getString("prefix", ""))).toString())));
				}
				if (more.get((int) _position).get("explicit").toString().equals("yes")) {
					explicit.setVisibility(View.VISIBLE);
				} else {
					explicit.setVisibility(View.GONE);
				}
				Name.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/moscow.ttf"), Typeface.NORMAL);
				album.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/moscow.ttf"), Typeface.NORMAL);
				Release.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/moscow.ttf"), Typeface.NORMAL);
			} else {
				linear1.setVisibility(View.GONE);
			}
			return _view;
		}
	}

	public class ArtistsAdapter extends BaseAdapter {
		ArrayList < HashMap < String, Object >> _data;

		public ArtistsAdapter(ArrayList < HashMap < String, Object >> _arr) {
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

			final LinearLayout linear1 = (LinearLayout) _view.findViewById(R.id.linear1);
			final de.hdodenhof.circleimageview.CircleImageView imageview2 = (de.hdodenhof.circleimageview.CircleImageView) _view.findViewById(R.id.imageview2);
			final LinearLayout linear2 = (LinearLayout) _view.findViewById(R.id.linear2);
			final TextView textview2 = (TextView) _view.findViewById(R.id.textview2);
			final TextView textview3 = (TextView) _view.findViewById(R.id.textview3);
			if (!artists.get((int) _position).containsKey("link")) {
				textview3.setAlpha((float) 0.5d);
				textview2.setAlpha((float) 0.5d);
				imageview2.setAlpha((float) 0.5d);
			}
			if (artists.get((int) _position).containsKey("image")) {
				Glide.with(getApplicationContext()).load(Uri.parse(artists.get((int) _position).get("image").toString())).into(imageview2);
			}
			_marquee(textview2, artists.get((int) _position).get("name").toString());
			textview3.setVisibility(View.GONE);
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