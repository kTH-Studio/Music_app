package ru.kartofan.theme.music.app;

import androidx.annotation.NonNull;
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
import android.widget.AdapterView;
import android.graphics.Typeface;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

public class ArtistActivity extends AppCompatActivity {

	private ImageView image_quality;
	private String str;
	private String lateststr;
	private String topstr;
	private String albumsstr;
	private String singlesstr;
	private String livestr;
	private String compilationsstr;
	private String appearsstr;
	private ArrayList < HashMap < String, Object >> map = new ArrayList < > ();
	private ArrayList < HashMap < String, Object >> latest = new ArrayList < > ();
	private ArrayList < HashMap < String, Object >> top = new ArrayList < > ();
	private ArrayList < HashMap < String, Object >> albums = new ArrayList < > ();
	private ArrayList < HashMap < String, Object >> singles = new ArrayList < > ();
	private ArrayList < HashMap < String, Object >> live = new ArrayList < > ();
	private ArrayList < HashMap < String, Object >> compilations = new ArrayList < > ();
	private ArrayList < HashMap < String, Object >> appears = new ArrayList < > ();
	private ArrayList < HashMap < String, Object >> links = new ArrayList < > ();
	private ArrayList < HashMap < String, Object >> info = new ArrayList < > ();
	private LinearLayout about_segment;
	private LinearLayout links_segment;
	private LinearLayout about_line;
	private LinearLayout hometown_line;
	private LinearLayout born_line;
	private LinearLayout genre_line;
	private LinearLayout segment;
	private LinearLayout segment_more;
	private LinearLayout latest_release_linear;
	private LinearLayout top_songs_linear;
	private LinearLayout albums_linear;
	private LinearLayout singles_linear;
	private LinearLayout live_linear;
	private LinearLayout compilations_linear;
	private LinearLayout appear_on_linear;
	private ImageView imageview1;
	private TextView textview1;
	private TextView about_hint;
	private TextView about_text;
	private TextView hometown_hint;
	private TextView hometown_text;
	private TextView born_hint;
	private TextView born_text;
	private TextView genre_hint;
	private TextView genre_text;
	private TextView links_hint;
	private TextView links_more;
	private TextView albums_hint;
	private TextView latest_release_hint;
	private TextView top_songs_hint;
	private TextView singles_hint;
	private TextView live_hint;
	private TextView compilations_hint;
	private TextView appears_on_hint;
	private double visibility = 0;
	private ListView albums_list;
	private ListView links_list;
	private ListView latest_release_list;
	private ListView top_songs_list;
	private ListView singles_list;
	private ListView live_list;
	private ListView compilations_list;
	private ListView appears_on_list;
	private SharedPreferences sp;
	private final Intent i = new Intent();
	private final Intent p = new Intent();
	private final Intent l = new Intent();
	private final Intent v = new Intent();

	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);
		setContentView(R.layout.artist);
		initialize(_savedInstanceState);
		initializeLogic();
	}

	private void initialize(Bundle _savedInstanceState) {
		image_quality = (ImageView) findViewById(R.id.image_quality);
		imageview1 = (pl.droidsonroids.gif.GifImageView) findViewById(R.id.imageview1);
		textview1 = (TextView) findViewById(R.id.textview1);
		about_segment = (LinearLayout) findViewById(R.id.about_segment);
		links_segment = (LinearLayout) findViewById(R.id.links_segment);
		about_line = (LinearLayout) findViewById(R.id.about_line);
		hometown_line = (LinearLayout) findViewById(R.id.hometown_line);
		born_line = (LinearLayout) findViewById(R.id.born_line);
		genre_line = (LinearLayout) findViewById(R.id.genre_line);
		about_hint = (TextView) findViewById(R.id.about_hint);
		about_text = (TextView) findViewById(R.id.about_text);
		hometown_hint = (TextView) findViewById(R.id.hometown_hint);
		hometown_text = (TextView) findViewById(R.id.hometown_text);
		born_hint = (TextView) findViewById(R.id.born_hint);
		born_text = (TextView) findViewById(R.id.born_text);
		genre_hint = (TextView) findViewById(R.id.genre_hint);
		genre_text = (TextView) findViewById(R.id.genre_text);
		links_hint = (TextView) findViewById(R.id.links_hint);
		links_list = (ListView) findViewById(R.id.links_list);
		segment = (LinearLayout) findViewById(R.id.segment);
		segment_more = (LinearLayout) findViewById(R.id.segment_more);
		links_more = (TextView) findViewById(R.id.links_more);
		albums_hint = (TextView) findViewById(R.id.albums_hint);
		latest_release_hint = (TextView) findViewById(R.id.latest_release_hint);
		latest_release_linear = (LinearLayout) findViewById(R.id.latest_release_linear);
		latest_release_list = (ListView) findViewById(R.id.latest_release_list);
		top_songs_linear = (LinearLayout) findViewById(R.id.top_songs_linear);
		top_songs_hint = (TextView) findViewById(R.id.top_songs_hint);
		top_songs_list = (ListView) findViewById(R.id.top_songs_list);
		albums_linear = (LinearLayout) findViewById(R.id.albums_linear);
		albums_list = (ListView) findViewById(R.id.albums_list);
		singles_linear = (LinearLayout) findViewById(R.id.singles_linear);
		singles_hint = (TextView) findViewById(R.id.singles_hint);
		singles_list = (ListView) findViewById(R.id.singles_list);
		live_linear = (LinearLayout) findViewById(R.id.live_linear);
		live_hint = (TextView) findViewById(R.id.live_hint);
		live_list = (ListView) findViewById(R.id.live_list);
		compilations_linear = (LinearLayout) findViewById(R.id.compilations_linear);
		compilations_hint = (TextView) findViewById(R.id.compilations_hint);
		compilations_list = (ListView) findViewById(R.id.compilations_list);
		appear_on_linear = (LinearLayout) findViewById(R.id.appears_on_linear);
		appears_on_hint = (TextView) findViewById(R.id.appears_on_hint);
		appears_on_list = (ListView) findViewById(R.id.appears_on_list);
		sp = getSharedPreferences("sp", Activity.MODE_PRIVATE);

		latest_release_hint.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				p.setClass(getApplicationContext(), FullActivity.class);
				p.putExtra("data", new Gson().toJson(latest));
				p.putExtra("artist", map.get((int) 0).get("name").toString());
				p.putExtra("title", getString(R.string.latest_release_1));
				startActivity(p);
			}
		});

		//top_songs_hint.setOnClickListener(new View.OnClickListener() {
		//	@Override
		//	public void onClick(View v) {
		//		p.setClass(getApplicationContext(), FullActivity.class);
		//		p.putExtra("data", new Gson().toJson(top));
		//		startActivity(p);
		//	}
		//});

		albums_hint.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				p.setClass(getApplicationContext(), FullActivity.class);
				p.putExtra("data", new Gson().toJson(albums));
				p.putExtra("artist", map.get((int) 0).get("name").toString());
				p.putExtra("title", getString(R.string.albums_1));
				startActivity(p);
			}
		});

		singles_hint.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				p.setClass(getApplicationContext(), FullActivity.class);
				p.putExtra("data", new Gson().toJson(singles));
				p.putExtra("artist", map.get((int) 0).get("name").toString());
				p.putExtra("title", getString(R.string.singles_1));
				startActivity(p);
			}
		});

		live_hint.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				p.setClass(getApplicationContext(), FullActivity.class);
				p.putExtra("data", new Gson().toJson(live));
				p.putExtra("artist", map.get((int) 0).get("name").toString());
				p.putExtra("title", getString(R.string.live_1));
				startActivity(p);
			}
		});

		compilations_hint.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				p.setClass(getApplicationContext(), FullActivity.class);
				p.putExtra("data", new Gson().toJson(compilations));
				p.putExtra("artist", map.get((int) 0).get("name").toString());
				p.putExtra("title", getString(R.string.compilations_1));
				startActivity(p);
			}
		});

		appears_on_hint.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				p.setClass(getApplicationContext(), FullActivity.class);
				p.putExtra("data", new Gson().toJson(appears));
				p.putExtra("artist", map.get((int) 0).get("name").toString());
				p.putExtra("title", getString(R.string.appears_on_1));
				startActivity(p);
			}
		});

		segment_more.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (map.get((int) 0).containsKey("about")) {
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
		});

		links_hint.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (map.get((int) 0).containsKey("about")) {
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
		});

		segment_more.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (map.get((int) 0).containsKey("about")) {
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
		});

		imageview1.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				if (map.get((int) 0).containsKey("image4k")) {
					if (sp.getString("quality", "").equals("yes")) {
						i.setClass(getApplicationContext(), ImageActivity.class);
						i.putExtra("imageq", map.get((int) 0).get("image4k").toString());
						i.putExtra("name", map.get((int) 0).get("name").toString());
						i.putExtra("artist", "");
						startActivity(i);
					} else {
						i.setClass(getApplicationContext(), ImageActivity.class);
						i.putExtra("imageq", map.get((int) 0).get("image").toString());
						i.putExtra("name", map.get((int) 0).get("name").toString());
						i.putExtra("artist", "");
						startActivity(i);
					}
				} else {
					i.setClass(getApplicationContext(), ImageActivity.class);
					i.putExtra("imageq", map.get((int) 0).get("image").toString());
					i.putExtra("name", map.get((int) 0).get("name").toString());
					i.putExtra("artist", "");
					startActivity(i);
				}
			}
		});

		latest_release_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView < ? > _param1, View _param2, int _param3, long _param4) {
				final int _position = _param3;
				if (latest.get((int) _position).containsKey("link")) {
					l.setClass(getApplicationContext(), AlbumActivity.class);
					l.putExtra("link", albums.get((int) _position).get("link").toString());
					startActivity(l);
				} else {
					com.google.android.material.snackbar.Snackbar.make(latest_release_list, R.string.album_will_be_added_soon, com.google.android.material.snackbar.Snackbar.LENGTH_SHORT).setAction("ok", new View.OnClickListener() {
						@Override
						public void onClick(View _view) {}
					}).show();
				}
			}
		});

		top_songs_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView < ? > _param1, View _param2, int _param3, long _param4) {
				final int _position = _param3;
				if (top.get((int) _position).containsKey("link")) {
					l.setClass(getApplicationContext(), AlbumActivity.class);
					l.putExtra("link", albums.get((int) _position).get("link").toString());
					startActivity(l);
				} else {
					com.google.android.material.snackbar.Snackbar.make(top_songs_list, R.string.song_will_be_added_soon, com.google.android.material.snackbar.Snackbar.LENGTH_SHORT).setAction("ok", new View.OnClickListener() {
						@Override
						public void onClick(View _view) {}
					}).show();
				}
			}
		});

		albums_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView < ? > _param1, View _param2, int _param3, long _param4) {
				final int _position = _param3;
				if (albums.get((int) _position).containsKey("link")) {
					l.setClass(getApplicationContext(), AlbumActivity.class);
					l.putExtra("link", albums.get((int) _position).get("link").toString());
					startActivity(l);
				} else {
					com.google.android.material.snackbar.Snackbar.make(albums_list, R.string.album_will_be_added_soon, com.google.android.material.snackbar.Snackbar.LENGTH_SHORT).setAction("ok", new View.OnClickListener() {
						@Override
						public void onClick(View _view) {}
					}).show();
				}
			}
		});

		singles_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView < ? > _param1, View _param2, int _param3, long _param4) {
				final int _position = _param3;
				if (singles.get((int) _position).containsKey("link")) {
					l.setClass(getApplicationContext(), AlbumActivity.class);
					l.putExtra("link", singles.get((int) _position).get("link").toString());
					startActivity(l);
				} else {
					com.google.android.material.snackbar.Snackbar.make(singles_list, R.string.song_will_be_added_soon, com.google.android.material.snackbar.Snackbar.LENGTH_SHORT).setAction("ok", new View.OnClickListener() {
						@Override
						public void onClick(View _view) {}
					}).show();
				}
			}
		});

		live_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView < ? > _param1, View _param2, int _param3, long _param4) {
				final int _position = _param3;
				if (live.get((int) _position).containsKey("link")) {
					l.setClass(getApplicationContext(), AlbumActivity.class);
					l.putExtra("link", albums.get((int) _position).get("link").toString());
					startActivity(l);
				} else {
					com.google.android.material.snackbar.Snackbar.make(compilations_list, R.string.album_will_be_added_soon, com.google.android.material.snackbar.Snackbar.LENGTH_SHORT).setAction("ok", new View.OnClickListener() {
						@Override
						public void onClick(View _view) {}
					}).show();
				}
			}
		});

		compilations_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView < ? > _param1, View _param2, int _param3, long _param4) {
				final int _position = _param3;
				if (compilations.get((int) _position).containsKey("link")) {
					l.setClass(getApplicationContext(), AlbumActivity.class);
					l.putExtra("link", compilations.get((int) _position).get("link").toString());
					startActivity(l);
				} else {
					com.google.android.material.snackbar.Snackbar.make(compilations_list, R.string.album_will_be_added_soon, com.google.android.material.snackbar.Snackbar.LENGTH_SHORT).setAction("ok", new View.OnClickListener() {
						@Override
						public void onClick(View _view) {}
					}).show();
				}
			}
		});

		appears_on_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView < ? > _param1, View _param2, int _param3, long _param4) {
				final int _position = _param3;
				if (appears.get((int) _position).containsKey("link")) {
					l.setClass(getApplicationContext(), AlbumActivity.class);
					l.putExtra("link", albums.get((int) _position).get("link").toString());
					startActivity(l);
				} else {
					com.google.android.material.snackbar.Snackbar.make(appears_on_list, R.string.album_will_be_added_soon, com.google.android.material.snackbar.Snackbar.LENGTH_SHORT).setAction("ok", new View.OnClickListener() {
						@Override
						public void onClick(View _view) {}
					}).show();
				}
			}
		});

		links_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView < ? > _param1, View _param2, int _param3, long _param4) {
				final int _position = _param3;
				v.setAction(Intent.ACTION_VIEW);
				v.setData(Uri.parse(links.get((int) _position).get("link").toString()));
				startActivity(v);
			}
		});

		about_text.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				final com.google.android.material.bottomsheet.BottomSheetDialog bs_base = new com.google.android.material.bottomsheet.BottomSheetDialog(ArtistActivity.this);
				bs_base.setCancelable(true);
				View layBase = getLayoutInflater().inflate(R.layout.bottom, null);
				bs_base.setContentView(layBase);
				TextView text = (TextView) layBase.findViewById(R.id.text);
				text.setText(info.get((int) 0).get("tale_".concat(sp.getString("prefix", ""))).toString());
				text.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/moscow.ttf"), Typeface.NORMAL);
				TextView about = (TextView) layBase.findViewById(R.id.about);
				about.setVisibility(View.GONE);
				about.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/moscow.ttf"), Typeface.NORMAL);
				bs_base.show();
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
		latest_release_hint.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/moscow.ttf"), Typeface.NORMAL);
		top_songs_hint.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/moscow.ttf"), Typeface.NORMAL);
		albums_hint.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/moscow.ttf"), Typeface.NORMAL);
		singles_hint.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/moscow.ttf"), Typeface.NORMAL);
		live_hint.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/moscow.ttf"), Typeface.NORMAL);
		compilations_hint.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/moscow.ttf"), Typeface.NORMAL);
		appears_on_hint.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/moscow.ttf"), Typeface.NORMAL);
		about_segment.setVisibility(View.GONE);
		latest_release_linear.setVisibility(View.GONE);
		top_songs_linear.setVisibility(View.GONE);
		albums_linear.setVisibility(View.GONE);
		singles_linear.setVisibility(View.GONE);
		live_linear.setVisibility(View.GONE);
		compilations_linear.setVisibility(View.GONE);
		appear_on_linear.setVisibility(View.GONE);
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
			if (map.get((int) 0).containsKey("latest")) {
				latest_release_linear.setVisibility(View.VISIBLE);
				latest = new Gson().fromJson(map.get((int) 0).get("latest").toString(), new TypeToken < ArrayList < HashMap < String, Object >>> () {}.getType());
				latest_release_list.setAdapter(new Latest_release_listAdapter(albums));
					_ViewSetHeight(latest_release_list, latest.size() * kTHUtil.getDip(getApplicationContext(), (int)(77)));
					latest_release_hint.setText(getString(R.string.albums_1).concat(" (").concat(String.valueOf(latest.size())).concat(") >"));
				((BaseAdapter) latest_release_list.getAdapter()).notifyDataSetChanged();
			} else {
				latest_release_linear.setVisibility(View.GONE);
			}
			if (map.get((int) 0).containsKey("top_songs")) {
				top_songs_linear.setVisibility(View.VISIBLE);
				top = new Gson().fromJson(map.get((int) 0).get("top_songs").toString(), new TypeToken < ArrayList < HashMap < String, Object >>> () {}.getType());
				top_songs_list.setAdapter(new Top_songs_listAdapter(top));
				_ViewSetHeight(top_songs_list, top.size() * kTHUtil.getDip(getApplicationContext(), (int)(51)));
				((BaseAdapter) top_songs_list.getAdapter()).notifyDataSetChanged();
			} else {
				top_songs_linear.setVisibility(View.GONE);
			}
			if (map.get((int) 0).containsKey("albums")) {
				albums_linear.setVisibility(View.VISIBLE);
				albums = new Gson().fromJson(map.get((int) 0).get("albums").toString(), new TypeToken < ArrayList < HashMap < String, Object >>> () {}.getType());
				albums_list.setAdapter(new Albums_listAdapter(albums));
				if (albums.size() < 5) {
					_ViewSetHeight(albums_list, albums.size() * kTHUtil.getDip(getApplicationContext(), (int)(77)));
				} else {
					_ViewSetHeight(albums_list, 5 * kTHUtil.getDip(getApplicationContext(), (int)(77)));
				}
				albums_hint.setText(getString(R.string.albums_1).concat(" (").concat(String.valueOf(albums.size())).concat(") >"));
				((BaseAdapter) albums_list.getAdapter()).notifyDataSetChanged();
			} else {
				albums_linear.setVisibility(View.GONE);
			}
			if (map.get((int) 0).containsKey("singles")) {
				singles_linear.setVisibility(View.VISIBLE);
				singles = new Gson().fromJson(map.get((int) 0).get("singles").toString(), new TypeToken < ArrayList < HashMap < String, Object >>> () {}.getType());
				singles_list.setAdapter(new Singles_listAdapter(singles));
				if (singles.size() < 5) {
					_ViewSetHeight(singles_list, singles.size() * kTHUtil.getDip(getApplicationContext(), (int)(77)));
				} else {
					_ViewSetHeight(singles_list, 5 * kTHUtil.getDip(getApplicationContext(), (int)(77)));
				}
				singles_hint.setText(getString(R.string.singles_1).concat(" (").concat(String.valueOf(singles.size())).concat(") >"));
				((BaseAdapter) singles_list.getAdapter()).notifyDataSetChanged();
			} else {
				singles_linear.setVisibility(View.GONE);
			}
			if (map.get((int) 0).containsKey("live")) {
				live_linear.setVisibility(View.VISIBLE);
				live = new Gson().fromJson(map.get((int) 0).get("live").toString(), new TypeToken < ArrayList < HashMap < String, Object >>> () {}.getType());
				live_list.setAdapter(new Live_listAdapter(live));
				if (live.size() < 5) {
					_ViewSetHeight(live_list, live.size() * kTHUtil.getDip(getApplicationContext(), (int)(77)));
				} else {
					_ViewSetHeight(live_list, 5 * kTHUtil.getDip(getApplicationContext(), (int)(77)));
				}
				live_hint.setText(getString(R.string.live_1).concat(" (").concat(String.valueOf(live.size())).concat(") >"));
				((BaseAdapter) live_list.getAdapter()).notifyDataSetChanged();
			} else {
				live_linear.setVisibility(View.GONE);
			}
			if (map.get((int) 0).containsKey("compilations")) {
				compilations_linear.setVisibility(View.VISIBLE);
				compilations = new Gson().fromJson(map.get((int) 0).get("compilations").toString(), new TypeToken < ArrayList < HashMap < String, Object >>> () {}.getType());
				compilations_list.setAdapter(new Compilations_listAdapter(compilations));
				if (compilations.size() < 5) {
					_ViewSetHeight(compilations_list, compilations.size() * kTHUtil.getDip(getApplicationContext(), (int)(77)));
				} else {
					_ViewSetHeight(compilations_list, 5 * kTHUtil.getDip(getApplicationContext(), (int)(77)));
				}
				compilations_hint.setText(getString(R.string.compilations_1).concat(" (").concat(String.valueOf(compilations.size())).concat(") >"));
				((BaseAdapter) compilations_list.getAdapter()).notifyDataSetChanged();
			} else {
				compilations_linear.setVisibility(View.GONE);
			}
			if (map.get((int) 0).containsKey("appears")) {
				appear_on_linear.setVisibility(View.VISIBLE);
				appears = new Gson().fromJson(map.get((int) 0).get("appear").toString(), new TypeToken < ArrayList < HashMap < String, Object >>> () {}.getType());
				appears_on_list.setAdapter(new Appears_listAdapter(appears));
				if (appears.size() < 5) {
					_ViewSetHeight(appears_on_list, appears.size() * kTHUtil.getDip(getApplicationContext(), (int)(77)));
				} else {
					_ViewSetHeight(appears_on_list, 5 * kTHUtil.getDip(getApplicationContext(), (int)(77)));
				}
				appears_on_hint.setText(getString(R.string.appears_on_1).concat(" (").concat(String.valueOf(appears.size())).concat(") >"));
				((BaseAdapter) appears_on_list.getAdapter()).notifyDataSetChanged();
			} else {
				appear_on_linear.setVisibility(View.GONE);
			}
			if (map.get((int) 0).containsKey("about")) {
				info = new Gson().fromJson(map.get((int) 0).get("about").toString(), new TypeToken < ArrayList < HashMap < String, Object >>> () {}.getType());
			}
			if (map.get((int) 0).containsKey("links")) {
				links = new Gson().fromJson(map.get((int) 0).get("links").toString(), new TypeToken < ArrayList < HashMap < String, Object >>> () {}.getType());
				links_list.setAdapter(new Links_listAdapter(links));
				_ViewSetHeight(links_list, links.size() * kTHUtil.getDip(getApplicationContext(), (int)(50)));
				((BaseAdapter) links_list.getAdapter()).notifyDataSetChanged();
				if (map.get((int) 0).containsKey("about")) {
					links_list.setVisibility(View.GONE);
					segment.setVisibility(View.GONE);
					links_more.setVisibility(View.VISIBLE);
					links_more.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/moscow.ttf"), Typeface.NORMAL);
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
		Glide.with(getApplicationContext()).load(Uri.parse(map.get((int) 0).get("image").toString())).into(imageview1);
		image_quality.setImageResource(R.drawable.ic_hd);
		if (sp.getString("quality", "").equals("yes")) {
			if (map.get((int) 0).containsKey("image4k")) {
				Glide.with(getApplicationContext()).load(Uri.parse(map.get((int) 0).get("image4k").toString())).into(imageview1);
				image_quality.setImageResource(R.drawable.ic_4k);
			}
		} else {
			Glide.with(getApplicationContext()).load(Uri.parse(map.get((int) 0).get("image").toString())).into(imageview1);
			image_quality.setImageResource(R.drawable.ic_hd);
		}
		if (sp.getString("animation", "").equals("yes")) {
			if (map.get((int) 0).containsKey("gif")) {
				Glide.with(getApplicationContext()).load(Uri.parse(map.get((int) 0).get("gif").toString())).into(imageview1);
			image_quality.setImageResource(R.drawable.ic_gif);
			}
		}
		_marquee(textview1, map.get((int) 0).get("name").toString());
		textview1.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/moscow.ttf"), Typeface.NORMAL);
		Toolbar toolbar = findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back);
		if (map.get((int) 0).containsKey("about")) {
			about_segment.setVisibility(View.VISIBLE);
			if (info.get((int) 0).containsKey("tale_".concat(sp.getString("prefix", "")))) {
				_marquee1(about_text, info.get((int) 0).get("tale_".concat(sp.getString("prefix", ""))).toString());
				about_line.setVisibility(View.VISIBLE);
				about_hint.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/moscow.ttf"), Typeface.NORMAL);
				about_text.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/moscow.ttf"), Typeface.NORMAL);
			} else {
				about_line.setVisibility(View.GONE);
			}
			if (info.get((int) 0).containsKey("hometown_".concat(sp.getString("prefix", ""))) || info.get((int) 0).containsKey("from_".concat(sp.getString("prefix", "")))) {
				if (info.get((int) 0).containsKey("hometown_".concat(sp.getString("prefix", "")))) {
					hometown_hint.setText(R.string.hometown);
					_marquee(hometown_text, info.get((int) 0).get("hometown_".concat(sp.getString("prefix", ""))).toString());
					hometown_line.setVisibility(View.VISIBLE);
					hometown_hint.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/moscow.ttf"), Typeface.NORMAL);
					hometown_text.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/moscow.ttf"), Typeface.NORMAL);
				} else {
					hometown_hint.setText(R.string.from);
					_marquee(hometown_text, info.get((int) 0).get("from_".concat(sp.getString("prefix", ""))).toString());
					hometown_line.setVisibility(View.VISIBLE);
					hometown_hint.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/moscow.ttf"), Typeface.NORMAL);
					hometown_text.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/moscow.ttf"), Typeface.NORMAL);
				}
			} else {
				hometown_line.setVisibility(View.GONE);
			}
			if (info.get((int) 0).containsKey("born_".concat(sp.getString("prefix", ""))) || info.get((int) 0).containsKey("formed_".concat(sp.getString("prefix", "")))) {
				if (info.get((int) 0).containsKey("born_".concat(sp.getString("prefix", "")))) {
					born_hint.setText(R.string.born);
					born_text.setText(info.get((int) 0).get("born_".concat(sp.getString("prefix", ""))).toString());
					born_line.setVisibility(View.VISIBLE);
					born_hint.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/moscow.ttf"), Typeface.NORMAL);
					born_text.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/moscow.ttf"), Typeface.NORMAL);
				} else {
					born_hint.setText(R.string.formed);
					born_text.setText(info.get((int) 0).get("formed_".concat(sp.getString("prefix", ""))).toString());
					born_line.setVisibility(View.VISIBLE);
					born_hint.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/moscow.ttf"), Typeface.NORMAL);
					born_text.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/moscow.ttf"), Typeface.NORMAL);
				}
			} else {
				born_line.setVisibility(View.GONE);
			}
			if (info.get((int) 0).containsKey("genre_".concat(sp.getString("prefix", "")))) {
				genre_text.setText(info.get((int) 0).get("genre_".concat(sp.getString("prefix", ""))).toString());
				genre_line.setVisibility(View.VISIBLE);
				genre_hint.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/moscow.ttf"), Typeface.NORMAL);
				genre_text.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/moscow.ttf"), Typeface.NORMAL);
			} else {
				genre_line.setVisibility(View.GONE);
			}
		} else {
			about_segment.setVisibility(View.GONE);
		}
		if (map.get((int) 0).containsKey("links")) {
			links_hint.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/moscow.ttf"), Typeface.NORMAL);
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
	public boolean onOptionsItemSelected(@NonNull MenuItem item) {
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
			final LinearLayout linear1 = (LinearLayout) _view.findViewById(R.id.linear1);
			final ImageView imageview1 = (ImageView) _view.findViewById(R.id.imageview1);
			final TextView Name = (TextView) _view.findViewById(R.id.Name);
			final TextView album = (TextView) _view.findViewById(R.id.album);
			final TextView Release = (TextView) _view.findViewById(R.id.Release);
			final ImageView more = (ImageView) _view.findViewById(R.id.more);
			if (!latest.get((int) _position).containsKey("link")) {
				Release.setAlpha((float) 0.5d);
				album.setAlpha((float) 0.5d);
				imageview1.setAlpha((float) 0.5d);
				Name.setAlpha((float) 0.5d);
				more.setAlpha((float) 0.5d);
			}
			if (_position < 1) {
				if (latest.get((int) _position).get("explicit").toString().equals("yes")) {
					if (latest.get((int) _position).containsKey("prefix")) {
						lateststr = latest.get((int) _position).get("name").toString().concat(" ").concat(latest.get((int) _position).get("prefix").toString()).concat(" 🅴");
					} else {
						lateststr = latest.get((int) _position).get("name").toString().concat(" 🅴");
					}
				} else {
					if (latest.get((int) _position).containsKey("prefix")) {
						lateststr = latest.get((int) _position).get("name").toString().concat(" ").concat(latest.get((int) _position).get("prefix").toString());
					} else {
						lateststr = latest.get((int) _position).get("name").toString();
					}
				}
				SpannableString ss = new SpannableString(lateststr);
				SpannableStringBuilder ssb = new SpannableStringBuilder(lateststr);
				int color = ContextCompat.getColor(ArtistActivity.this, R.color.text2);
				ForegroundColorSpan fcsRed = new ForegroundColorSpan(color);
				ssb.setSpan(fcsRed, latest.get((int) _position).get("name").toString().length(), lateststr.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
				_marque(Name, ssb);
				if (latest.get((int) _position).containsKey("image")) {
					Glide.with(getApplicationContext()).load(Uri.parse(latest.get((int) _position).get("image").toString())).into(imageview1);
				}
				if (latest.get((int) _position).get("artist").toString().equals(map.get((int) 0).get("name").toString())) {
					album.setVisibility(View.GONE);
				} else {
					album.setVisibility(View.VISIBLE);
					_marquee(album, latest.get((int) _position).get("artist").toString());
				}
				if (latest.get((int) _position).containsKey("release_".concat(sp.getString("prefix", ""))) && latest.get((int) _position).containsKey("time_".concat(sp.getString("prefix", "")))) {
					_marquee(Release, latest.get((int) _position).get("release_".concat(sp.getString("prefix", ""))).toString().concat(" • ".concat(latest.get((int) _position).get("time_".concat(sp.getString("prefix", ""))).toString())));
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
			final ImageView imageview1 = (ImageView) _view.findViewById(R.id.imageview1);
			final TextView Name = (TextView) _view.findViewById(R.id.Name);
			final TextView album = (TextView) _view.findViewById(R.id.album);
			final TextView Release = (TextView) _view.findViewById(R.id.Release);
			if (top.get((int) _position).get("explicit").toString().equals("yes")) {
				if (top.get((int) _position).containsKey("prefix")) {
					topstr = top.get((int) _position).get("name").toString().concat(" ").concat(top.get((int) _position).get("prefix").toString()).concat(" 🅴");
				} else {
					topstr = top.get((int) _position).get("name").toString().concat(" 🅴");
				}
			} else {
				if (top.get((int) _position).containsKey("prefix")) {
					topstr = top.get((int) _position).get("name").toString().concat(" ").concat(top.get((int) _position).get("prefix").toString());
				} else {
					topstr = top.get((int) _position).get("name").toString();
				}
			}
			SpannableString ss = new SpannableString(topstr);
			SpannableStringBuilder ssb = new SpannableStringBuilder(topstr);
			int color = ContextCompat.getColor(ArtistActivity.this, R.color.text2);
			ForegroundColorSpan fcsRed = new ForegroundColorSpan(color);
			ssb.setSpan(fcsRed, top.get((int) _position).get("name").toString().length(), topstr.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
			_marque(Name, ssb);
			if (top.get((int) _position).containsKey("image")) {
				Glide.with(getApplicationContext()).load(Uri.parse(top.get((int) _position).get("image").toString())).into(imageview1);
			}
			if (top.get((int) _position).get("artist").toString().equals(map.get((int) 0).get("name").toString())) {
				album.setVisibility(View.GONE);
			} else {
				album.setVisibility(View.VISIBLE);
				_marquee(album, top.get((int) _position).get("artist").toString());
			}
			if (top.get((int) _position).containsKey("release_".concat(sp.getString("prefix", ""))) && top.get((int) _position).containsKey("time_".concat(sp.getString("prefix", "")))) {
				_marquee(Release, top.get((int) _position).get("release_".concat(sp.getString("prefix", ""))).toString().concat(" • ".concat(top.get((int) _position).get("time_".concat(sp.getString("prefix", ""))).toString())));
			}
			Name.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/moscow.ttf"), Typeface.NORMAL);
			album.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/moscow.ttf"), Typeface.NORMAL);
			Release.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/moscow.ttf"), Typeface.NORMAL);
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
			final LinearLayout linear1 = (LinearLayout) _view.findViewById(R.id.linear1);
			final ImageView imageview1 = (ImageView) _view.findViewById(R.id.imageview1);
			final TextView Name = (TextView) _view.findViewById(R.id.Name);
			final TextView album = (TextView) _view.findViewById(R.id.album);
			final TextView Release = (TextView) _view.findViewById(R.id.Release);
			final ImageView more = (ImageView) _view.findViewById(R.id.more);
			if (!albums.get((int) _position).containsKey("link")) {
				Release.setAlpha((float) 0.5d);
				album.setAlpha((float) 0.5d);
				imageview1.setAlpha((float) 0.5d);
				Name.setAlpha((float) 0.5d);
				more.setAlpha((float) 0.5d);
			}
			if (_position < 5) {
				if (albums.get((int) _position).get("explicit").toString().equals("yes")) {
					if (albums.get((int) _position).containsKey("prefix")) {
						albumsstr = albums.get((int) _position).get("name").toString().concat(" ").concat(albums.get((int) _position).get("prefix").toString()).concat(" 🅴");
					} else {
						albumsstr = albums.get((int) _position).get("name").toString().concat(" 🅴");
					}
				} else {
					if (albums.get((int) _position).containsKey("prefix")) {
						albumsstr = albums.get((int) _position).get("name").toString().concat(" ").concat(albums.get((int) _position).get("prefix").toString());
					} else {
						albumsstr = albums.get((int) _position).get("name").toString();
					}
				}
				SpannableString ss = new SpannableString(albumsstr);
				SpannableStringBuilder ssb = new SpannableStringBuilder(albumsstr);
				int color = ContextCompat.getColor(ArtistActivity.this, R.color.text2);
				ForegroundColorSpan fcsRed = new ForegroundColorSpan(color);
				ssb.setSpan(fcsRed, albums.get((int) _position).get("name").toString().length(), albumsstr.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
				_marque(Name, ssb);
				if (albums.get((int) _position).containsKey("image")) {
					Glide.with(getApplicationContext()).load(Uri.parse(albums.get((int) _position).get("image").toString())).into(imageview1);
				}
				if (albums.get((int) _position).get("artist").toString().equals(map.get((int) 0).get("name").toString())) {
					album.setVisibility(View.GONE);
				} else {
					album.setVisibility(View.VISIBLE);
					_marquee(album, albums.get((int) _position).get("artist").toString());
				}
				if (albums.get((int) _position).containsKey("release_".concat(sp.getString("prefix", ""))) && albums.get((int) _position).containsKey("time_".concat(sp.getString("prefix", "")))) {
					_marquee(Release, albums.get((int) _position).get("release_".concat(sp.getString("prefix", ""))).toString().concat(" • ".concat(albums.get((int) _position).get("time_".concat(sp.getString("prefix", ""))).toString())));
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
			final LinearLayout linear1 = (LinearLayout) _view.findViewById(R.id.linear1);
			final ImageView imageview1 = (ImageView) _view.findViewById(R.id.imageview1);
			final TextView Name = (TextView) _view.findViewById(R.id.Name);
			final TextView album = (TextView) _view.findViewById(R.id.album);
			final TextView Release = (TextView) _view.findViewById(R.id.Release);
			final ImageView more = (ImageView) _view.findViewById(R.id.more);
			if (!singles.get((int) _position).containsKey("link")) {
				Release.setAlpha((float) 0.5d);
				album.setAlpha((float) 0.5d);
				imageview1.setAlpha((float) 0.5d);
				Name.setAlpha((float) 0.5d);
				more.setAlpha((float) 0.5d);
			}
			if (_position < 5) {
				if (singles.get((int) _position).get("explicit").toString().equals("yes")) {
					if (singles.get((int) _position).containsKey("prefix")) {
						singlesstr = singles.get((int) _position).get("name").toString().concat(" ").concat(singles.get((int) _position).get("prefix").toString()).concat(" 🅴");
					} else {
						singlesstr = singles.get((int) _position).get("name").toString().concat(" 🅴");
					}
				} else {
					if (singles.get((int) _position).containsKey("prefix")) {
						singlesstr = singles.get((int) _position).get("name").toString().concat(" ").concat(singles.get((int) _position).get("prefix").toString());
					} else {
						singlesstr = singles.get((int) _position).get("name").toString();
					}
				}
				SpannableString ss = new SpannableString(singlesstr);
				SpannableStringBuilder ssb = new SpannableStringBuilder(singlesstr);
				int color = ContextCompat.getColor(ArtistActivity.this, R.color.text2);
				ForegroundColorSpan fcsRed = new ForegroundColorSpan(color);
				ssb.setSpan(fcsRed, singles.get((int) _position).get("name").toString().length(), singlesstr.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
				_marque(Name, ssb);
				if (singles.get((int) _position).containsKey("image")) {
					Glide.with(getApplicationContext()).load(Uri.parse(singles.get((int) _position).get("image").toString())).into(imageview1);
				}
				if (singles.get((int) _position).get("artist").toString().equals(map.get((int) 0).get("name").toString())) {
					album.setVisibility(View.GONE);
				} else {
					album.setVisibility(View.VISIBLE);
					_marquee(album, singles.get((int) _position).get("artist").toString());
				}
				if (singles.get((int) _position).containsKey("release_".concat(sp.getString("prefix", ""))) && singles.get((int) _position).containsKey("time_".concat(sp.getString("prefix", "")))) {
					_marquee(Release, singles.get((int) _position).get("release_".concat(sp.getString("prefix", ""))).toString().concat(" • ".concat(singles.get((int) _position).get("time_".concat(sp.getString("prefix", ""))).toString())));
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
			final LinearLayout linear1 = (LinearLayout) _view.findViewById(R.id.linear1);
			final ImageView imageview1 = (ImageView) _view.findViewById(R.id.imageview1);
			final TextView Name = (TextView) _view.findViewById(R.id.Name);
			final TextView album = (TextView) _view.findViewById(R.id.album);
			final TextView Release = (TextView) _view.findViewById(R.id.Release);
			final ImageView more = (ImageView) _view.findViewById(R.id.more);
			if (!live.get((int) _position).containsKey("link")) {
				Release.setAlpha((float) 0.5d);
				album.setAlpha((float) 0.5d);
				imageview1.setAlpha((float) 0.5d);
				Name.setAlpha((float) 0.5d);
				more.setAlpha((float) 0.5d);
			}
			if (_position < 5) {
				if (live.get((int) _position).get("explicit").toString().equals("yes")) {
					if (live.get((int) _position).containsKey("prefix")) {
						livestr = live.get((int) _position).get("name").toString().concat(" ").concat(live.get((int) _position).get("prefix").toString()).concat(" 🅴");
					} else {
						livestr = live.get((int) _position).get("name").toString().concat(" 🅴");
					}
				} else {
					if (live.get((int) _position).containsKey("prefix")) {
						livestr = live.get((int) _position).get("name").toString().concat(" ").concat(live.get((int) _position).get("prefix").toString());
					} else {
						livestr = live.get((int) _position).get("name").toString();
					}
				}
				SpannableString ss = new SpannableString(livestr);
				SpannableStringBuilder ssb = new SpannableStringBuilder(livestr);
				int color = ContextCompat.getColor(ArtistActivity.this, R.color.text2);
				ForegroundColorSpan fcsRed = new ForegroundColorSpan(color);
				ssb.setSpan(fcsRed, live.get((int) _position).get("name").toString().length(), livestr.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
				_marque(Name, ssb);
				if (live.get((int) _position).containsKey("image")) {
					Glide.with(getApplicationContext()).load(Uri.parse(live.get((int) _position).get("image").toString())).into(imageview1);
				}
				if (live.get((int) _position).get("artist").toString().equals(map.get((int) 0).get("name").toString())) {
					album.setVisibility(View.GONE);
				} else {
					album.setVisibility(View.VISIBLE);
					_marquee(album, live.get((int) _position).get("artist").toString());
				}
				if (live.get((int) _position).containsKey("release_".concat(sp.getString("prefix", ""))) && live.get((int) _position).containsKey("time_".concat(sp.getString("prefix", "")))) {
					_marquee(Release, live.get((int) _position).get("release_".concat(sp.getString("prefix", ""))).toString().concat(" • ".concat(live.get((int) _position).get("time_".concat(sp.getString("prefix", ""))).toString())));
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
			final LinearLayout linear1 = (LinearLayout) _view.findViewById(R.id.linear1);
			final ImageView imageview1 = (ImageView) _view.findViewById(R.id.imageview1);
			final TextView Name = (TextView) _view.findViewById(R.id.Name);
			final TextView album = (TextView) _view.findViewById(R.id.album);
			final TextView Release = (TextView) _view.findViewById(R.id.Release);
			final ImageView more = (ImageView) _view.findViewById(R.id.more);
			if (!compilations.get((int) _position).containsKey("link")) {
				Release.setAlpha((float) 0.5d);
				album.setAlpha((float) 0.5d);
				imageview1.setAlpha((float) 0.5d);
				Name.setAlpha((float) 0.5d);
				more.setAlpha((float) 0.5d);
			}
			if (_position < 5) {
				if (compilations.get((int) _position).get("explicit").toString().equals("yes")) {
					if (compilations.get((int) _position).containsKey("prefix")) {
						compilationsstr = compilations.get((int) _position).get("name").toString().concat(" ").concat(compilations.get((int) _position).get("prefix").toString()).concat(" 🅴");
					} else {
						compilationsstr = compilations.get((int) _position).get("name").toString().concat(" 🅴");
					}
				} else {
					if (compilations.get((int) _position).containsKey("prefix")) {
						compilationsstr = compilations.get((int) _position).get("name").toString().concat(" ").concat(compilations.get((int) _position).get("prefix").toString());
					} else {
						compilationsstr = compilations.get((int) _position).get("name").toString();
					}
				}
				SpannableString ss = new SpannableString(compilationsstr);
				SpannableStringBuilder ssb = new SpannableStringBuilder(compilationsstr);
				int color = ContextCompat.getColor(ArtistActivity.this, R.color.text2);
				ForegroundColorSpan fcsRed = new ForegroundColorSpan(color);
				ssb.setSpan(fcsRed, compilations.get((int) _position).get("name").toString().length(), compilationsstr.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
				_marque(Name, ssb);
				if (compilations.get((int) _position).containsKey("image")) {
					Glide.with(getApplicationContext()).load(Uri.parse(compilations.get((int) _position).get("image").toString())).into(imageview1);
				}
				if (compilations.get((int) _position).get("artist").toString().equals(map.get((int) 0).get("name").toString())) {
					album.setVisibility(View.GONE);
				} else {
					album.setVisibility(View.VISIBLE);
					_marquee(album, compilations.get((int) _position).get("artist").toString());
				}
				if (compilations.get((int) _position).containsKey("release_".concat(sp.getString("prefix", ""))) && compilations.get((int) _position).containsKey("time_".concat(sp.getString("prefix", "")))) {
					_marquee(Release, compilations.get((int) _position).get("release_".concat(sp.getString("prefix", ""))).toString().concat(" • ".concat(compilations.get((int) _position).get("time_".concat(sp.getString("prefix", ""))).toString())));
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
			final LinearLayout linear1 = (LinearLayout) _view.findViewById(R.id.linear1);
			final ImageView imageview1 = (ImageView) _view.findViewById(R.id.imageview1);
			final TextView Name = (TextView) _view.findViewById(R.id.Name);
			final TextView album = (TextView) _view.findViewById(R.id.album);
			final TextView Release = (TextView) _view.findViewById(R.id.Release);
			final ImageView more = (ImageView) _view.findViewById(R.id.more);
			if (!appears.get((int) _position).containsKey("link")) {
				Release.setAlpha((float) 0.5d);
				album.setAlpha((float) 0.5d);
				imageview1.setAlpha((float) 0.5d);
				Name.setAlpha((float) 0.5d);
				more.setAlpha((float) 0.5d);
			}
			if (_position < 5) {
				if (appears.get((int) _position).get("explicit").toString().equals("yes")) {
					if (appears.get((int) _position).containsKey("prefix")) {
						appearsstr = appears.get((int) _position).get("name").toString().concat(" ").concat(appears.get((int) _position).get("prefix").toString()).concat(" 🅴");
					} else {
						appearsstr = appears.get((int) _position).get("name").toString().concat(" 🅴");
					}
				} else {
					if (appears.get((int) _position).containsKey("prefix")) {
						appearsstr = appears.get((int) _position).get("name").toString().concat(" ").concat(appears.get((int) _position).get("prefix").toString());
					} else {
						appearsstr = appears.get((int) _position).get("name").toString();
					}
				}
				SpannableString ss = new SpannableString(appearsstr);
				SpannableStringBuilder ssb = new SpannableStringBuilder(appearsstr);
				int color = ContextCompat.getColor(ArtistActivity.this, R.color.text2);
				ForegroundColorSpan fcsRed = new ForegroundColorSpan(color);
				ssb.setSpan(fcsRed, appears.get((int) _position).get("name").toString().length(), appearsstr.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
				_marque(Name, ssb);
				if (appears.get((int) _position).containsKey("image")) {
					Glide.with(getApplicationContext()).load(Uri.parse(appears.get((int) _position).get("image").toString())).into(imageview1);
				}
				if (appears.get((int) _position).get("artist").toString().equals(map.get((int) 0).get("name").toString())) {
					album.setVisibility(View.GONE);
				} else {
					album.setVisibility(View.VISIBLE);
					_marquee(album, appears.get((int) _position).get("artist").toString());
				}
				if (appears.get((int) _position).containsKey("release_".concat(sp.getString("prefix", ""))) && appears.get((int) _position).containsKey("time_".concat(sp.getString("prefix", "")))) {
					_marquee(Release, appears.get((int) _position).get("release_".concat(sp.getString("prefix", ""))).toString().concat(" • ".concat(appears.get((int) _position).get("time_".concat(sp.getString("prefix", ""))).toString())));
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
			final LinearLayout linear1 = (LinearLayout) _view.findViewById(R.id.linear1);
			final ImageView link_image = (ImageView) _view.findViewById(R.id.link_image);
			final LinearLayout linear2 = (LinearLayout) _view.findViewById(R.id.linear2);
			final ImageView open = (ImageView) _view.findViewById(R.id.open);
			final TextView link_text = (TextView) _view.findViewById(R.id.link_text);
			final TextView link_adress = (TextView) _view.findViewById(R.id.link_adress);
			Glide.with(getApplicationContext()).load(Uri.parse(links.get((int) _position).get("image").toString())).into(link_image);
			link_text.setText(links.get((int) _position).get("text_".concat(sp.getString("prefix", ""))).toString());
			_marquee(link_adress, links.get((int) _position).get("link").toString());
			link_text.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/moscow.ttf"), Typeface.NORMAL);
			link_adress.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/moscow.ttf"), Typeface.NORMAL);
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