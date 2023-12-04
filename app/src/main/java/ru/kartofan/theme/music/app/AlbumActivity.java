package ru.kartofan.theme.music.app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.*;
import android.os.*;
import android.text.style.ForegroundColorSpan;
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
import android.view.View;
import com.bumptech.glide.Glide;
import android.graphics.Typeface;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

public class AlbumActivity extends AppCompatActivity {
	private ImageView image_quality, imageview1;
	private String str, about, talestr, titlestr, playstr, otherstr, morestr, infostr, lyrics, str1 = "";
	private double myster, num, myst = 0;
	private ArrayList < HashMap < String, Object >> map, play, other, more, artists, uri, map1, play1 = new ArrayList < > ();
	private LinearLayout other_versions_linear, more_by_linear, featured_artists_linear;
	private TextView tale, name, artist, date, time, copyright, other_versions_text, more_by_text, featured_artists_text;
	private ListView listview1, more_by_list, featured_artists_list, other_versions_list;
	private final Intent i = new Intent();
	private final Intent l = new Intent();
	private final Intent p = new Intent();
	private SharedPreferences sp;

	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);
		setContentView(R.layout.album);
		initialize();
		initializeLogic();
	}

	private void initialize() {
		image_quality = findViewById(R.id.image_quality);
		tale = findViewById(R.id.tale);
		listview1 = findViewById(R.id.listview1);
		imageview1 = findViewById(R.id.imageview1);
		name = findViewById(R.id.name);
		artist = findViewById(R.id.artist);
		date = findViewById(R.id.date);
		time = findViewById(R.id.time);
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

		tale.setOnClickListener(_view -> {
			final com.google.android.material.bottomsheet.BottomSheetDialog bs_base = new com.google.android.material.bottomsheet.BottomSheetDialog(AlbumActivity.this);
			bs_base.setCancelable(true);
			View layBase = getLayoutInflater().inflate(R.layout.bottom, null);
			bs_base.setContentView(layBase);
			TextView text = layBase.findViewById(R.id.text);
			text.setText(map.get(0).get("tale_".concat(sp.getString("prefix", ""))).toString());
			_typeface(text);
			TextView about = layBase.findViewById(R.id.about);
			about.setVisibility(View.VISIBLE);
			if (map.get(0).containsKey("prefix")) {
				talestr = map.get(0).get("name").toString().concat(" ").concat(map.get(0).get("prefix").toString());
			} else {
				talestr = map.get(0).get("name").toString();
			}
			SpannableStringBuilder ssb = new SpannableStringBuilder(talestr);
			int color = ContextCompat.getColor(AlbumActivity.this, R.color.text2);
			ForegroundColorSpan fcsRed = new ForegroundColorSpan(color);
			ssb.setSpan(fcsRed, map.get(0).get("name").toString().length(), talestr.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
			_marque(about, ssb);
			_typeface(about);
			bs_base.show();
		});

		listview1.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
			@Override
			public boolean onItemLongClick(AdapterView<?> _param1, View _param2, int _param3, long _param4) {
				if (play.get(_param3).containsKey("link")) {
					new BackTask1().execute(play.get(_param3).get("link").toString());
				} else {} return true;}});

		listview1.setOnItemClickListener((_param1, _param2, _param3, _param4) -> {
			if (play.get(_param3).containsKey("mystery")) {} else {
				if (sp.getString("explicit", "").equals("no")) {
					if (play.get(_param3).get("explicit").toString().equals("yes")) {
						Snackbar.make(listview1, R.string.not_for_children, Snackbar.LENGTH_SHORT).setAction("Ok", _view -> {}).show();
					} else {
						_list(play, _param3, listview1, R.string.song_will_be_added_soon, MusicActivity.class);
					}} else {
					_list(play, _param3, listview1, R.string.song_will_be_added_soon, MusicActivity.class);
				}}});
		other_versions_list.setOnItemClickListener((_param1, _param2, _param3, _param4) -> {
			_list(other, _param3, other_versions_list, R.string.album_will_be_added_soon, AlbumActivity.class);});
		more_by_list.setOnItemClickListener((_param1, _param2, _param3, _param4) -> {
			_list(more, _param3, more_by_list, R.string.album_will_be_added_soon, AlbumActivity.class);});
		featured_artists_list.setOnItemClickListener((_param1, _param2, _param3, _param4) -> {
			_list(artists, _param3, featured_artists_list, R.string.artist_will_be_added_soon, ArtistActivity.class);});

		other_versions_text.setOnClickListener(v -> {_extra(other, R.string.other_versions_1);});
		featured_artists_text.setOnClickListener(v -> {_extra(artists, R.string.featured_artists_1);});
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
				i.putExtra("link", uri.get(0).get("link").toString());
				startActivity(i);
			}
		});

		imageview1.setOnClickListener(_view -> {
			if (map.get(0).containsKey("image4k")) {
				if (sp.getString("quality", "").equals("yes")) {
					_image("image4k");
				} else {_image("image");
				}} else {_image("image");
			}});
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
		new BackTask().execute(getIntent().getStringExtra("link"));
		other_versions_linear.setVisibility(View.GONE);
		more_by_linear.setVisibility(View.GONE);
		featured_artists_linear.setVisibility(View.GONE);
		_typeface(other_versions_text);
		_typeface(more_by_text);
		_typeface(featured_artists_text);
		image_quality.setVisibility(View.GONE);
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
				} in.close();
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
			play = new Gson().fromJson(map.get(0).get("songs").toString(), new TypeToken < ArrayList < HashMap < String, Object >>> () {}.getType());
			uri = new Gson().fromJson(map.get(0).get("artist_uri").toString(), new TypeToken<ArrayList<HashMap<String, Object>>>() {}.getType());
			listview1.setAdapter(new AlbumActivity.Listview1Adapter(play));
			num = 0;
			for (int _repeat21 = 0; _repeat21 < play.size(); _repeat21++) {
				if (play.get((int) num).containsKey("mystery")) {
					myst++;
				}
				num++;
			}
			_ViewSetHeight(listview1, (play.size() * kTHUtil.getDip(getApplicationContext(), 51)) - (myst * kTHUtil.getDip(getApplicationContext(), 26)));
			((BaseAdapter) listview1.getAdapter()).notifyDataSetChanged();
			_text();
			if (map.get(0).containsKey("other")) {
				other_versions_linear.setVisibility(View.VISIBLE);
				other = new Gson().fromJson(map.get(0).get("other").toString(), new TypeToken < ArrayList < HashMap < String, Object >>> () {}.getType());
				if (other.size() < 5) {
					_ViewSetHeight(other_versions_list, other.size() * kTHUtil.getDip(getApplicationContext(), 77));
				} else {
					_ViewSetHeight(other_versions_list, 5 * kTHUtil.getDip(getApplicationContext(), 77));
				}
				other_versions_text.setText(getString(R.string.other_versions_1).concat(" (").concat(String.valueOf(other.size())).concat(") >"));
				other_versions_list.setAdapter(new AlbumActivity.Other_listAdapter(other));
				((BaseAdapter) other_versions_list.getAdapter()).notifyDataSetChanged();
			}
			if (map.get(0).containsKey("more")) {
				more_by_linear.setVisibility(View.VISIBLE);
				more = new Gson().fromJson(map.get(0).get("more").toString(), new TypeToken < ArrayList < HashMap < String, Object >>> () {}.getType());
				if (more.size() < 5) {
					_ViewSetHeight(more_by_list, more.size() * kTHUtil.getDip(getApplicationContext(), 77));
				} else {
					_ViewSetHeight(more_by_list, 5 * kTHUtil.getDip(getApplicationContext(), 77));
				}
				more_by_text.setText(getString(R.string.more_by).concat(" ").concat(map.get(0).get("artist").toString().concat(" (").concat(String.valueOf(more.size())).concat(") >")));
				more_by_list.setAdapter(new AlbumActivity.More_listAdapter(more));
				((BaseAdapter) more_by_list.getAdapter()).notifyDataSetChanged();
			}
			if (map.get(0).containsKey("artists")) {
				featured_artists_linear.setVisibility(View.VISIBLE);
				artists = new Gson().fromJson(map.get(0).get("artists").toString(), new TypeToken < ArrayList < HashMap < String, Object >>> () {}.getType());
				if (artists.size() < 5) {
					_ViewSetHeight(featured_artists_list, artists.size() * kTHUtil.getDip(getApplicationContext(), 64));
				} else {
					_ViewSetHeight(featured_artists_list, 5 * kTHUtil.getDip(getApplicationContext(), 64));
					featured_artists_text.setText(getString(R.string.featured_artists_1).concat(" (").concat(String.valueOf(artists.size())).concat(") >"));
				}
				featured_artists_list.setAdapter(new AlbumActivity.ArtistsAdapter(artists));
				((BaseAdapter) featured_artists_list.getAdapter()).notifyDataSetChanged();
			}
		}
	}

	private class BackTask1 extends AsyncTask < String, Integer, String > {
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
				} in.close();
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
			str1 = s;
			map1 = new Gson().fromJson(str1, new TypeToken < ArrayList < HashMap < String, Object >>> () {}.getType());
			if (map1.get(0).containsKey("text")) {
				play1 = new Gson().fromJson(map1.get(0).get("text").toString(), new TypeToken<ArrayList<HashMap<String, Object>>>() {}.getType());
			}
			uri = new Gson().fromJson(map1.get(0).get("artist_uri").toString(), new TypeToken<ArrayList<HashMap<String, Object>>>() {}.getType());
			_bottom();
		}
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

	public void _text() {
		if (map.get(0).containsKey("tale_".concat(sp.getString("prefix", "")))) {
			tale.setVisibility(View.VISIBLE);
			about = map.get(0).get("tale_".concat(sp.getString("prefix", ""))).toString();
			_marquee1(tale, map.get(0).get("tale_".concat(sp.getString("prefix", ""))).toString());
			_typeface(tale);
		} else {
			tale.setVisibility(View.GONE);
		}
		if (map.get(0).containsKey("prefix")) {
			if (map.get(0).containsKey("additional")) {
				if (map.get(0).get("additional").equals("single")) {
					if (map.get(0).get("explicit").equals("yes")) {
						titlestr = map.get(0).get("name").toString().concat(" ").concat(map.get(0).get("prefix").toString()).concat(" ").concat(getString(R.string.single)).concat(" 🅴");
					} else {
						titlestr = map.get(0).get("name").toString().concat(" ").concat(map.get(0).get("prefix").toString()).concat(" ").concat(getString(R.string.single));
					}
				} else {
					if (map.get(0).get("explicit").equals("yes")) {
						titlestr = map.get(0).get("name").toString().concat(" ").concat(map.get(0).get("prefix").toString()).concat(" ").concat(getString(R.string.ep)).concat(" 🅴");
					} else  {
						titlestr = map.get(0).get("name").toString().concat(" ").concat(map.get(0).get("prefix").toString()).concat(" ").concat(getString(R.string.ep));
					}
				}
			} else {
				if (map.get(0).get("explicit").equals("yes")) {
					titlestr = map.get(0).get("name").toString().concat(" ").concat(map.get(0).get("prefix").toString()).concat(" ").concat(" 🅴");
				} else {
					titlestr = map.get(0).get("name").toString().concat(" ").concat(map.get(0).get("prefix").toString()).concat(" ");
				}
			}
		} else {
			if (map.get(0).containsKey("additional")) {
				if (map.get(0).get("additional").equals("single")) {
					if (map.get(0).get("explicit").equals("yes")) {
						titlestr = map.get(0).get("name").toString().concat(" - ").concat(getString(R.string.single)).concat(" 🅴");
					} else {
						titlestr = map.get(0).get("name").toString().concat(" - ").concat(getString(R.string.single));
					}
				} else {
					if (map.get(0).get("explicit").equals("yes")) {
						titlestr = map.get(0).get("name").toString().concat(" - ").concat(getString(R.string.ep)).concat(" 🅴");
					} else  {
						titlestr = map.get(0).get("name").toString().concat(" - ").concat(getString(R.string.ep));
					}
				}
			} else {
				if (map.get(0).get("explicit").equals("yes")) {
					titlestr = map.get(0).get("name").toString().concat(" 🅴");
				} else {
					titlestr = map.get(0).get("name").toString();
				}
			}
		}
		_span(titlestr, name, map, 0);
		_marquee(artist, map.get(0).get("artist").toString().concat(" • ".concat(map.get(0).get("genre_".concat(sp.getString("prefix", ""))).toString().concat(" • ".concat(map.get(0).get("release").toString())))));
		sp.edit().putString("artist", map.get(0).get("artist").toString()).apply();
		date.setText(map.get(0).get("date_".concat(sp.getString("prefix", ""))).toString());
		time.setText(map.get(0).get("number_".concat(sp.getString("prefix", ""))).toString().concat(" • ".concat(map.get(0).get("time_".concat(sp.getString("prefix", ""))).toString())));
		copyright.setText(map.get(0).get("copyright").toString());
		Toolbar toolbar = findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back);
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
		_typeface(name);
		_typeface(artist);
		_typeface(date);
		_typeface(copyright);
		_typeface(time);
	}

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
		name.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/moscow.ttf"), Typeface.NORMAL);
		album.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/moscow.ttf"), Typeface.NORMAL);
		artist.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/moscow.ttf"), Typeface.NORMAL);
		artist_text.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/moscow.ttf"), Typeface.NORMAL);
		album_text.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/moscow.ttf"), Typeface.NORMAL);
		lyrics_text.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/moscow.ttf"), Typeface.NORMAL);
		info_text.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/moscow.ttf"), Typeface.NORMAL);
		if (!map1.get(0).containsKey("info")) {
			info_image.setAlpha((float) 0.5d);
			info_linear.setAlpha((float) 0.5d);
			info_text.setAlpha((float) 0.5d);
		}
		if (uri.size() > 1) {
			artist_image.setImageResource(R.drawable.ic_people);
			artist_text.setText(getString(R.string.go_to_artists));
		} else {
			artist_image.setImageResource(R.drawable.ic_timer_auto);
			artist_text.setText(getString(R.string.go_to_artist));
		}
		if (map1.get(0).containsKey("text")) {
			lyrics_linear.setVisibility(View.VISIBLE);
		} else {
			lyrics_linear.setVisibility(View.GONE);
		}
		image.setOnClickListener(v -> {
			if (map1.get(0).containsKey("image4k")) {
				if (sp.getString("quality", "").equals("mobile")) {
					i.setClass(getApplicationContext(), ImageActivity.class);
					i.putExtra("imageq", map1.get(0).get("image4k").toString());
					i.putExtra("name", name.getText().toString().concat(" - ").concat(artist.getText().toString()));
					i.putExtra("artist", album.getText().toString());
					startActivity(i);
				} else {
					i.setClass(getApplicationContext(), ImageActivity.class);
					i.putExtra("imageq", map1.get(0).get("image").toString());
					i.putExtra("name", name.getText().toString().concat(" - ").concat(artist.getText().toString()));
					i.putExtra("artist", album.getText().toString());
					startActivity(i);
				}
			} else {
				i.setClass(getApplicationContext(), ImageActivity.class);
				i.putExtra("imageq", map1.get(0).get("image").toString());
				i.putExtra("name", name.getText().toString().concat(" - ").concat(artist.getText().toString()));
				i.putExtra("artist", album.getText().toString());
				startActivity(i);
			}
		});
		artist_text.setOnClickListener(v -> _artist());
		artist_image.setOnClickListener(v -> {
			bs_base.cancel();
			_artist();
		});
		artist_linear.setOnClickListener(v -> {
			bs_base.cancel();
			_artist();
		});
		album_text.setOnClickListener(v -> {
			bs_base.cancel();
			_album();
		});
		album_image.setOnClickListener(v -> {
			bs_base.cancel();
			_album();
		});
		album_linear.setOnClickListener(v -> {
			bs_base.cancel();
			_album();
		});
		lyrics_text.setOnClickListener(v -> _lyrics());
		lyrics_image.setOnClickListener(v -> _lyrics());
		lyrics_linear.setOnClickListener(v -> _lyrics());
		info_text.setOnClickListener(v -> { if (map1.get(0).containsKey("info")) {
			_info();}});
		info_image.setOnClickListener(v -> { if (map1.get(0).containsKey("info")) {
			_info();}});
		info_linear.setOnClickListener(v -> { if (map1.get(0).containsKey("info")) {
			_info();}});
		if (map1.get(0).get("explicit").toString().equals("yes")) {
			if (map1.get(0).containsKey("prefix")) {
				infostr = map1.get(0).get("name").toString().concat(" ").concat(map1.get(0).get("prefix").toString()).concat(" 🅴");
			} else {
				infostr = map1.get(0).get("name").toString().concat(" 🅴");
			}
		} else {
			if (map1.get(0).containsKey("prefix")) {
				infostr = map1.get(0).get("name").toString().concat(" ").concat(map1.get(0).get("prefix").toString());
			} else {
				infostr = map1.get(0).get("name").toString();
			}
		}
		SpannableStringBuilder ssb = new SpannableStringBuilder(infostr);
		int color = ContextCompat.getColor(AlbumActivity.this, R.color.text2);
		ForegroundColorSpan fcsRed = new ForegroundColorSpan(color);
		ssb.setSpan(fcsRed, map1.get(0).get("name").toString().length(), infostr.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
		_marque(name, ssb);
		if (map1.get(0).containsKey("additional")) {
			if (map1.get(0).get("additional").equals("single")) {
				_marquee(album, map1.get(0).get("album").toString().concat(" - ").concat(getString(R.string.single)));
			} else {
				_marquee(album, map1.get(0).get("album").toString().concat(" - ").concat(getString(R.string.ep)));
			}
		} else {
			_marquee(album, map1.get(0).get("album").toString());
		}
		artist.setText(map1.get(0).get("artist").toString());
		Glide.with(getApplicationContext()).load(Uri.parse(map1.get(0).get("image").toString())).into(image);
		bs_base.show();
	}

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
				i.putExtra("link", uri.get(0).get("link").toString());
				startActivity(i);
			} else {
				com.google.android.material.snackbar.Snackbar.make(listview1, R.string.artist_will_be_added_soon, Snackbar.LENGTH_INDEFINITE).setAction("Ok", _view -> {}).show();
			}
		}
	}

	public void _album() {
		i.setClass(getApplicationContext(), AlbumActivity.class);
		i.putExtra("link", map1.get(0).get("album_uri").toString());
		startActivity(i);
	}

	public void _info() {
		if (map1.get(0).containsKey("info")) {
			i.setClass(getApplicationContext(), DetailsActivity.class);
			i.putExtra("info", map1.get(0).get("info").toString());
			startActivity(i);
		}
	}

	public void _lyrics() {
		final com.google.android.material.bottomsheet.BottomSheetDialog bs_base = new com.google.android.material.bottomsheet.BottomSheetDialog(AlbumActivity.this);
		bs_base.setCancelable(true);
		View layBase = getLayoutInflater().inflate(R.layout.bottom, null);
		bs_base.setContentView(layBase);
		TextView text = layBase.findViewById(R.id.text);
		if (play1.get(0).containsKey("written")) {
			text.setText(play1.get(0).get("text").toString().concat(getString(R.string.written_by)).concat(play1.get(0).get("written").toString()));
		} else {
			text.setText(play1.get(0).get("text").toString());
		}
		text.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/moscow.ttf"), Typeface.NORMAL);
		TextView about = layBase.findViewById(R.id.about);
		about.setVisibility(View.VISIBLE);
		if (map1.get(0).get("explicit").toString().equals("yes")) {
			if (map1.get(0).containsKey("prefix")) {
				lyrics = map1.get(0).get("name").toString().concat(" ").concat(map1.get(0).get("prefix").toString()).concat(" 🅴\n").concat(map1.get(0).get("artist").toString());
			} else {
				lyrics = map1.get(0).get("name").toString().concat(" 🅴\n").concat(map1.get(0).get("artist").toString());
			}
		} else {
			if (map.get(0).containsKey("prefix")) {
				lyrics = map1.get(0).get("name").toString().concat(" ").concat(map1.get(0).get("prefix").toString()).concat(" \n").concat(map1.get(0).get("artist").toString());
			} else {
				lyrics = map1.get(0).get("name").toString().concat("\n").concat(map1.get(0).get("artist").toString());
			}
		}
		SpannableStringBuilder ssb = new SpannableStringBuilder(lyrics);
		int color = ContextCompat.getColor(this, R.color.text2);
		ForegroundColorSpan fcsRed = new ForegroundColorSpan(color);
		ssb.setSpan(fcsRed, map1.get(0).get("name").toString().length() + 1, lyrics.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
		about.setText(ssb);
		about.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/moscow.ttf"), Typeface.NORMAL);
		bs_base.show();
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

	public void _list(ArrayList<HashMap<String, Object>> _map, int _param3, ListView _list, int _str, Class _act) {
		if (_map.get(_param3).containsKey("link")) {
			i.setClass(getApplicationContext(), _act);
			i.putExtra("link", _map.get(_param3).get("link").toString());
			startActivity(i);
		} else {
			Snackbar.make(_list, _str, Snackbar.LENGTH_SHORT).setAction("Ok", _view -> {}).show();
		}
	}

	public void _extra(ArrayList<HashMap<String, Object>> _map, int _str) {
		p.setClass(getApplicationContext(), FullActivity.class);
		p.putExtra("data", new Gson().toJson(_map));
		p.putExtra("artist", map.get(0).get("artist").toString());
		p.putExtra("title", getString(_str));
		startActivity(p);
	}

	public void _image(String _key) {
		i.setClass(getApplicationContext(), ImageActivity.class);
		i.putExtra("imageq", map.get(0).get(_key).toString());
		i.putExtra("name", name.getText().toString());
		i.putExtra("artist", artist.getText().toString());
		startActivity(i);
	}

	public void _quality(String _key, int _str) {
		Glide.with(getApplicationContext()).load(Uri.parse(map.get(0).get(_key).toString())).into(imageview1);
		image_quality.setImageResource(_str);
		image_quality.setVisibility(View.VISIBLE);
	}

	public void _typeface(TextView _txt) {
		_txt.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/moscow.ttf"), Typeface.NORMAL);
	}

	public void _span(String _str, TextView _txt, ArrayList<HashMap<String, Object>> _map, int _n) {
		SpannableStringBuilder ssb = new SpannableStringBuilder(_str);
		int color = ContextCompat.getColor(AlbumActivity.this, R.color.text2);
		ForegroundColorSpan fcsRed = new ForegroundColorSpan(color);
		ssb.setSpan(fcsRed, _map.get((int) _n).get("name").toString().length(), _str.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
		_marque(_txt, ssb);
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
			final LinearLayout tale = _view.findViewById(R.id.tale);
			final LinearLayout linear1 = _view.findViewById(R.id.linear1);
			final ImageView imageview1 = _view.findViewById(R.id.imageview1);
			final TextView number = _view.findViewById(R.id.number);
			final TextView mystery = _view.findViewById(R.id.mystery);
			final TextView time = _view.findViewById(R.id.time);
			final TextView name = _view.findViewById(R.id.name);
			final TextView artist = _view.findViewById(R.id.artist);
			final ImageView more = _view.findViewById(R.id.more);
			more.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					new BackTask1().execute(play.get(_position).get("link").toString());
				}
			});
			if (!play.get(_position).containsKey("link")) {
				number.setAlpha((float) 0.5d);
				name.setAlpha((float) 0.5d);
				artist.setAlpha((float) 0.5d);
				time.setAlpha((float) 0.5d);
				imageview1.setAlpha((float) 0.5d);
				more.setAlpha((float) 0.5d);
			}
			if (play.get(_position).containsKey("mystery")) {
				linear1.setVisibility(View.GONE);
				tale.setVisibility(View.VISIBLE);
				mystery.setText(play.get(_position).get("mystery").toString());
				_typeface(mystery);
				myster = myster + 1;
				sp.edit().putString("myster", String.valueOf((long)(myster))).apply();
			} else {
				linear1.setVisibility(View.VISIBLE);
				tale.setVisibility(View.GONE);
				number.setText(play.get(_position).get("number").toString());
				time.setText(play.get(_position).get("time").toString());
				if (play.get(_position).get("explicit").toString().equals("yes")) {
					if (play.get(_position).containsKey("prefix")) {
						playstr = play.get(_position).get("name").toString().concat(" ").concat(play.get(_position).get("prefix").toString()).concat(" 🅴");
					} else {
						playstr = play.get(_position).get("name").toString().concat(" 🅴");
					}
				} else {
					if (play.get(_position).containsKey("prefix")) {
						playstr = play.get(_position).get("name").toString().concat(" ").concat(play.get(_position).get("prefix").toString());
					} else {
						playstr = play.get(_position).get("name").toString();
					}
				}
				_span(playstr, name, play, _position);
				_marquee(artist, play.get(_position).get("artist").toString());
				if (play.get(_position).get("star").toString().equals("yes")) {
					imageview1.setVisibility(View.VISIBLE);
				} else {
					imageview1.setVisibility(View.GONE);
				}
				if (play.get(_position).get("artist").toString().equals(sp.getString("artist", ""))) {
					artist.setVisibility(View.GONE);
				} else {
					artist.setVisibility(View.VISIBLE);
				}
				if (sp.getString("explicit", "").equals("no")) {
					if (play.get(_position).get("explicit").toString().equals("yes")) {
						number.setAlpha((float) 0.5d);
						name.setAlpha((float) 0.5d);
						artist.setAlpha((float) 0.5d);
						time.setAlpha((float) 0.5d);
						imageview1.setAlpha((float) 0.5d);
						more.setAlpha((float) 0.5d);
					}
				}
				_typeface(number);
				_typeface(time);
				_typeface(name);
				_typeface(artist);
			}
			_ViewSetHeight(listview1, (play.size() * kTHUtil.getDip(getApplicationContext(), 51)) - (myster * kTHUtil.getDip(getApplicationContext(), 18)));
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
			final LinearLayout linear1 = _view.findViewById(R.id.linear1);
			final ImageView imageview1 = _view.findViewById(R.id.imageview1);
			final TextView Name = _view.findViewById(R.id.Name);
			final TextView album = _view.findViewById(R.id.album);
			final TextView Release = _view.findViewById(R.id.Release);
			if (!other.get(_position).containsKey("link")) {
				Release.setAlpha((float) 0.5d);
				album.setAlpha((float) 0.5d);
				imageview1.setAlpha((float) 0.5d);
				Name.setAlpha((float) 0.5d);
			}
			if (_position < 5) {
				if (other.get(_position).get("explicit").toString().equals("yes")) {
					if (other.get(_position).containsKey("prefix")) {
						otherstr = other.get(_position).get("name").toString().concat(" ").concat(other.get(_position).get("prefix").toString()).concat(" 🅴");
					} else {
						otherstr = other.get(_position).get("name").toString().concat(" 🅴");
					}
				} else {
					if (other.get(_position).containsKey("prefix")) {
						otherstr = other.get(_position).get("name").toString().concat(" ").concat(other.get(_position).get("prefix").toString());
					} else {
						otherstr = other.get(_position).get("name").toString();
					}
				}
				_span(otherstr, Name, other, _position);
				if (other.get(_position).containsKey("image")) {
					Glide.with(getApplicationContext()).load(Uri.parse(other.get(_position).get("image").toString())).into(imageview1);
				}
				if (other.get(0).get("artist").toString().equals(map.get(0).get("artist").toString())) {
					album.setVisibility(View.GONE);
				} else {
					album.setVisibility(View.VISIBLE);
					_marquee(album, other.get(0).get("artist").toString());
				}
				if (other.get(_position).containsKey("release_".concat(sp.getString("prefix", ""))) && other.get(_position).containsKey("time_".concat(sp.getString("prefix", "")))) {
					_marquee(Release, other.get(_position).get("release_".concat(sp.getString("prefix", ""))).toString().concat(" • ".concat(other.get(_position).get("time_".concat(sp.getString("prefix", ""))).toString())));
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
			final LinearLayout linear1 = _view.findViewById(R.id.linear1);
			final ImageView imageview1 = _view.findViewById(R.id.imageview1);
			final TextView Name = _view.findViewById(R.id.Name);
			final TextView album = _view.findViewById(R.id.album);
			final TextView Release = _view.findViewById(R.id.Release);
			if (!more.get(_position).containsKey("link")) {
				Release.setAlpha((float) 0.5d);
				album.setAlpha((float) 0.5d);
				imageview1.setAlpha((float) 0.5d);
				Name.setAlpha((float) 0.5d);
			}
			if (_position < 5) {
				if (more.get(_position).get("explicit").toString().equals("yes")) {
					if (more.get(_position).containsKey("prefix")) {
						morestr = more.get(_position).get("name").toString().concat(" ").concat(more.get(_position).get("prefix").toString()).concat(" 🅴");
					} else {
						morestr = more.get(_position).get("name").toString().concat(" 🅴");
					}
				} else {
					if (more.get(_position).containsKey("prefix")) {
						morestr = more.get(_position).get("name").toString().concat(" ").concat(more.get(_position).get("prefix").toString());
					} else {
						morestr = more.get(_position).get("name").toString();
					}
				}
				_span(morestr, Name, more, _position);
				if (more.get(_position).containsKey("image")) {
					Glide.with(getApplicationContext()).load(Uri.parse(more.get(_position).get("image").toString())).into(imageview1);
				}
				if (more.get(_position).get("artist").toString().equals(map.get(0).get("artist").toString())) {
					album.setVisibility(View.GONE);
				} else {
					album.setVisibility(View.VISIBLE);
					_marquee(album, more.get(_position).get("artist").toString());
				}
				if (more.get(_position).containsKey("release_".concat(sp.getString("prefix", ""))) && more.get(_position).containsKey("time_".concat(sp.getString("prefix", "")))) {
					_marquee(Release, more.get(_position).get("release_".concat(sp.getString("prefix", ""))).toString().concat(" • ".concat(more.get(_position).get("time_".concat(sp.getString("prefix", ""))).toString())));
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

			final de.hdodenhof.circleimageview.CircleImageView imageview2 = _view.findViewById(R.id.imageview2);
			final TextView textview2 = _view.findViewById(R.id.textview2);
			final TextView textview3 = _view.findViewById(R.id.textview3);
			if (!artists.get(_position).containsKey("link")) {
				textview3.setAlpha((float) 0.5d);
				textview2.setAlpha((float) 0.5d);
				imageview2.setAlpha((float) 0.5d);
			}
			if (artists.get(_position).containsKey("image")) {
				Glide.with(getApplicationContext()).load(Uri.parse(artists.get(_position).get("image").toString())).into(imageview2);
			}
			_marquee(textview2, artists.get(_position).get("name").toString());
			textview3.setVisibility(View.GONE);
			_typeface(textview3);
			_typeface(textview2);
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