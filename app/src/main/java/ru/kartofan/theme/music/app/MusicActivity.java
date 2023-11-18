package ru.kartofan.theme.music.app;

import androidx.appcompat.app.AppCompatActivity;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.os.*;
import android.view.*;
import android.widget.*;
import android.graphics.*;
import android.text.*;
import android.util.*;
import java.util.*;
import java.util.ArrayList;
import java.util.HashMap;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.recyclerview.widget.RecyclerView;
import android.widget.SeekBar;
import android.media.MediaPlayer;
import java.util.Timer;
import java.util.TimerTask;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.AdapterView;
import com.bumptech.glide.Glide;
import android.graphics.Typeface;
import java.text.DecimalFormat;

import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class MusicActivity extends AppCompatActivity {

	private final Timer _timer = new Timer();
	private String str = "";
	private String currentfile = "";
	private double song_duration = 0;
	private double pos = 0;
	private String text = "";
	private double time = 0;
	private String str1 = "";
	private double play1 = 0;
	private String str2 = "";
	private double tr = 0;
	private ArrayList < HashMap < String, Object >> map = new ArrayList < > ();
	private final ArrayList < String > n = new ArrayList < > ();
	private ArrayList < HashMap < String, Object >> play = new ArrayList < > ();
	private ArrayList < HashMap < String, Object >> uri = new ArrayList < > ();
	private ArrayList < HashMap < String, Object >> artists = new ArrayList < > ();
	private LinearLayout linear1;
	private TextView textview5;
	private LinearLayout linear11;
	private ImageView imageview6;
	private LinearLayout linear14;
	private LinearLayout linear2;
	private ProgressBar progressbar1;
	private ProgressBar progressbar2;
	private ListView listview3;
	private RecyclerView recyclerview1;
	private ImageView imageview1;
	private ImageView explicit;
	private LinearLayout linear3;
	private ImageView imageview2;
	private TextView textview1;
	private TextView textview2;
	private TextView textview3;
	private TextView textview4;
	private ImageView imageview3;
	private ImageView imageview4;
	private ImageView imageview5;
	private SeekBar seekbar1;
	private MediaPlayer raone;
	private TimerTask t;
	private TimerTask x;
	private TimerTask s;
	private TimerTask a;
	private final Intent i = new Intent();
	private SharedPreferences sp;

	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);
		setContentView(R.layout.music);
		//ConstraintLayout constraintLayout = findViewById(R.id.main);
		//AnimationDrawable animationDrawable = (AnimationDrawable) constraintLayout.getBackground();
		//animationDrawable.setEnterFadeDuration(2500);
		//animationDrawable.setExitFadeDuration(5000);
		//animationDrawable.start();
		initialize(_savedInstanceState);
		initializeLogic();
	}

	private void initialize(Bundle _savedInstanceState) {
		seekbar1 = (SeekBar) findViewById(R.id.seekbar1);
		linear1 = (LinearLayout) findViewById(R.id.linear1);
		textview5 = (TextView) findViewById(R.id.textview5);
		linear11 = (LinearLayout) findViewById(R.id.linear11);
		imageview6 = (ImageView) findViewById(R.id.imageview6);
		linear14 = (LinearLayout) findViewById(R.id.linear14);
		linear2 = (LinearLayout) findViewById(R.id.linear2);
		progressbar1 = (ProgressBar) findViewById(R.id.progressbar1);
		progressbar2 = (ProgressBar) findViewById(R.id.progressbar2);
		listview3 = (ListView) findViewById(R.id.listview3);
		recyclerview1 = (RecyclerView) findViewById(R.id.recyclerview1);
		imageview1 = (ImageView) findViewById(R.id.imageview1);
		linear3 = (LinearLayout) findViewById(R.id.linear3);
		imageview2 = (ImageView) findViewById(R.id.imageview2);
		textview1 = (TextView) findViewById(R.id.textview1);
		textview2 = (TextView) findViewById(R.id.textview2);
		textview3 = (TextView) findViewById(R.id.textview3);
		textview4 = (TextView) findViewById(R.id.textview4);
		imageview3 = (ImageView) findViewById(R.id.imageview3);
		imageview4 = (ImageView) findViewById(R.id.imageview4);
		imageview5 = (ImageView) findViewById(R.id.imageview5);
		explicit = (ImageView) findViewById(R.id.explicit);
		sp = getSharedPreferences("sp", Activity.MODE_PRIVATE);

		seekbar1.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
			@Override
			public void onProgressChanged(SeekBar _param1, int _param2, boolean _param3) {
				final int _progressValue = _param2;
			}

			@Override
			public void onStartTrackingTouch(SeekBar _param1) {
				textview5.setVisibility(View.VISIBLE);
				textview3.setVisibility(View.GONE);
				textview4.setVisibility(View.GONE);
				t.cancel();
				t = new TimerTask() {
					@Override
					public void run() {
						runOnUiThread(new Runnable() {
							@Override
							public void run() {
								textview3.setText(new DecimalFormat("00").format((raone.getCurrentPosition() / 1000) / 60).concat(":".concat(new DecimalFormat("00").format((raone.getCurrentPosition() / 1000) % 60))));
								textview4.setText("-".concat(new DecimalFormat("00").format(((raone.getDuration() - raone.getCurrentPosition()) / 1000) / 60).concat(":".concat(new DecimalFormat("00").format(((raone.getDuration() - raone.getCurrentPosition()) / 1000) % 60)))));
								textview5.setText(new DecimalFormat("00").format((seekbar1.getProgress() / 1000) / 60).concat(":".concat(new DecimalFormat("00").format((seekbar1.getProgress() / 1000) % 60))));
								textview5.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/moscow.ttf"), Typeface.NORMAL);
							}
						});
					}
				};
				_timer.scheduleAtFixedRate(t, (int)(100), (int)(100));
			}

			@Override
			public void onStopTrackingTouch(SeekBar _param2) {
				textview5.setVisibility(View.GONE);
				textview3.setVisibility(View.VISIBLE);
				textview4.setVisibility(View.VISIBLE);
				imageview4.setImageResource(R.drawable.ic_pause);
				if (raone.isPlaying()) {
					raone.pause();
					t.cancel();
					raone.seekTo((int)(seekbar1.getProgress()));
					raone.start();
					_player();
				} else {
					raone.seekTo((int)(seekbar1.getProgress()));
					raone.start();
					_player();
				}
			}
		});

		imageview2.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				_bottom();
			}
		});

		imageview6.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				if (map.get((int) 0).containsKey("text")) {
					imageview6.setVisibility(View.GONE);
					imageview1.setVisibility(View.VISIBLE);
					recyclerview1.setVisibility(View.VISIBLE);
					listview3.setVisibility(View.VISIBLE);
					linear11.setVisibility(View.GONE);
					linear14.setVisibility(View.GONE);
				}
			}
		});

		imageview3.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				if (map.get((int) 0).containsKey("previous")) {
						raone.pause();
						t.cancel();
					new BackTask().execute(map.get((int) 0).get("previous").toString());
				}
			}
		});

		imageview5.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				if (map.get((int) 0).containsKey("next")) {
						raone.pause();
						t.cancel();
					new BackTask().execute(map.get((int) 0).get("next").toString());
				}
			}
		});

		listview3.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView < ? > _param1, View _param2, int _param3, long _param4) {
				final int _position = _param3;
				if (play.get((int) _position).get("text").toString().equals("!")) {
					i.setClass(getApplicationContext(), ImageActivity.class);
					i.putExtra("imageq", play.get((int) _position).get("link").toString());
					startActivity(i);
				} else {}
			}
		});

		imageview1.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				imageview6.setVisibility(View.VISIBLE);
				imageview1.setVisibility(View.GONE);
				recyclerview1.setVisibility(View.GONE);
				listview3.setVisibility(View.GONE);
				linear11.setVisibility(View.VISIBLE);
				linear14.setVisibility(View.VISIBLE);
			}
		});

		imageview4.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				if (raone.isPlaying()) {
					raone.pause();
					t.cancel();
					imageview4.setImageResource(R.drawable.ic_play_arrow);
				} else {
					if (song_duration < raone.getDuration()) {
						raone.start();
						imageview4.setImageResource(R.drawable.ic_pause);
						_player();
					} else {
						if (map.get((int) 0).containsKey("next")) {
							new BackTask().execute(map.get((int) 0).get("next").toString());
						} else {
							_music();
						}
					}
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
		pos = 0;
		tr = 0;
		textview5.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/moscow.ttf"), Typeface.NORMAL);
		progressbar1.setVisibility(View.GONE);
		new BackTask().execute(getIntent().getStringExtra("link"));
		progressbar2.setVisibility(View.VISIBLE);
		listview3.setVisibility(View.GONE);
		imageview6.setVisibility(View.GONE);
		linear11.setVisibility(View.GONE);
		linear14.setVisibility(View.GONE);
		textview5.setVisibility(View.GONE);
	}

	@Override
	protected void onActivityResult(int _requestCode, int _resultCode, Intent _data) {
		super.onActivityResult(_requestCode, _resultCode, _data);
		switch (_requestCode) {
			default:
				break;
		}
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
			uri = new Gson().fromJson(map.get((int) 0).get("artist_uri").toString(), new TypeToken<ArrayList<HashMap<String, Object>>>() {}.getType());
			_me();
		}
	}

	public void _player() {
		imageview4.setImageResource(R.drawable.ic_pause);
		seekbar1.setMax((int) raone.getDuration());
		seekbar1.setProgress((int) song_duration);
		t = new TimerTask() {
			@Override
			public void run() {
				runOnUiThread(new Runnable() {
					@Override
					public void run() {
						textview3.setText(new DecimalFormat("00").format((raone.getCurrentPosition() / 1000) / 60).concat(":".concat(new DecimalFormat("00").format((raone.getCurrentPosition() / 1000) % 60))));
						textview4.setText("-".concat(new DecimalFormat("00").format(((raone.getDuration() - raone.getCurrentPosition()) / 1000) / 60).concat(":".concat(new DecimalFormat("00").format(((raone.getDuration() - raone.getCurrentPosition()) / 1000) % 60)))));
						textview4.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/moscow.ttf"), Typeface.NORMAL);
						textview3.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/moscow.ttf"), Typeface.NORMAL);
						seekbar1.setProgress((int) raone.getCurrentPosition());
						song_duration = raone.getCurrentPosition();
						seekbar1.setProgress((int) song_duration);
					}
				});
			}
		};
		_timer.scheduleAtFixedRate(t, (int)(0), (int)(100));
	}

	public void _music() {
		currentfile = map.get((int) 0).get("music").toString();
		raone = new MediaPlayer();
		raone.setAudioStreamType(3);
		try {
			raone.setDataSource(currentfile);
		} catch (IllegalArgumentException e) {
			Toast.makeText(getApplicationContext(), "Unknown URL!", Toast.LENGTH_LONG).show();
		} catch (SecurityException e2) {
			Toast.makeText(getApplicationContext(), "Unknown URL!", Toast.LENGTH_LONG).show();
		} catch (IllegalStateException e3) {
			Toast.makeText(getApplicationContext(), "Unknown URL!", Toast.LENGTH_LONG).show();
		} catch (java.io.IOException e4) {
			e4.printStackTrace();
		}
		try {
			raone.prepare();
		} catch (IllegalStateException e5) {
			Toast.makeText(getApplicationContext(), "Unknown URL!", Toast.LENGTH_LONG).show();
		} catch (java.io.IOException e6) {
			Toast.makeText(getApplicationContext(), "Unknown URL!", Toast.LENGTH_LONG).show();
		}
		raone.start();
		raone.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
			@Override
			public void onCompletion(MediaPlayer mp) {
				if (map.get((int) 0).containsKey("next")) {
					new BackTask().execute(map.get((int) 0).get("next").toString());
				}
				imageview4.setImageResource(R.drawable.ic_play_arrow);
				t.cancel();
			}
		});
		play1 = 1;
		song_duration = raone.getCurrentPosition();
		seekbar1.setProgress((int) raone.getCurrentPosition());
		seekbar1.setMax((int) raone.getDuration());
		textview4.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/moscow.ttf"), Typeface.NORMAL);
		textview3.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/moscow.ttf"), Typeface.NORMAL);
		if (map.get((int) 0).containsKey("next")) {
			imageview5.setAlpha((float)(1));
		} else {
			imageview5.setAlpha((float)(0.5d));
		}
		if (map.get((int) 0).containsKey("previous")) {
			imageview3.setAlpha((float)(1));
		} else {
			imageview3.setAlpha((float)(0.5d));
		}
		_player();
	}

	public void _marquee(final TextView _textview, final String _text) {
		_textview.setText(_text);
		_textview.setSingleLine(true);
		_textview.setEllipsize(TextUtils.TruncateAt.MARQUEE);
		_textview.setSelected(true);
	}

	public void _bottom() {
		final com.google.android.material.bottomsheet.BottomSheetDialog bs_base = new com.google.android.material.bottomsheet.BottomSheetDialog(MusicActivity.this);
		bs_base.setCancelable(true);
		View layBase = getLayoutInflater().inflate(R.layout.info, null);
		bs_base.setContentView(layBase);
		ImageView image = (ImageView) layBase.findViewById(R.id.image);
		ImageView explicit = (ImageView) layBase.findViewById(R.id.explicit);
		ImageView artist_image = (ImageView) layBase.findViewById(R.id.artist_image);
		ImageView album_image = (ImageView) layBase.findViewById(R.id.album_image);
		ImageView lyrics_image = (ImageView) layBase.findViewById(R.id.lyrics_image);
		ImageView info_image = (ImageView) layBase.findViewById(R.id.info_image);
		TextView name = (TextView) layBase.findViewById(R.id.name);
		TextView album = (TextView) layBase.findViewById(R.id.album);
		TextView artist = (TextView) layBase.findViewById(R.id.artist);
		TextView artist_text = (TextView) layBase.findViewById(R.id.artist_text);
		TextView album_text = (TextView) layBase.findViewById(R.id.album_text);
		TextView lyrics_text = (TextView) layBase.findViewById(R.id.lyrics_text);
		TextView info_text = (TextView) layBase.findViewById(R.id.info_text);
		LinearLayout linear1 = (LinearLayout) layBase.findViewById(R.id.linear1);
		LinearLayout artist_linear = (LinearLayout) layBase.findViewById(R.id.artist_linear);
		LinearLayout album_linear = (LinearLayout) layBase.findViewById(R.id.album_linear);
		LinearLayout lyrics_linear = (LinearLayout) layBase.findViewById(R.id.lyrics_linear);
		LinearLayout info_linear = (LinearLayout) layBase.findViewById(R.id.info_linear);
		name.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/moscow.ttf"), Typeface.NORMAL);
		album.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/moscow.ttf"), Typeface.NORMAL);
		artist.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/moscow.ttf"), Typeface.NORMAL);
		artist_text.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/moscow.ttf"), Typeface.NORMAL);
		album_text.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/moscow.ttf"), Typeface.NORMAL);
		lyrics_text.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/moscow.ttf"), Typeface.NORMAL);
		info_text.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/moscow.ttf"), Typeface.NORMAL);
		if (uri.size() > 1) {
			artist_text.setText(getString(R.string.go_to_artists));
		} else {
			artist_text.setText(getString(R.string.go_to_artist));
		}
		if (map.get((int) 0).get("explicit").toString().equals("yes")) {
			explicit.setVisibility(View.VISIBLE);
		} else {
			explicit.setVisibility(View.GONE);
		}
		if (map.get((int) 0).containsKey("text")) {
			lyrics_linear.setVisibility(View.VISIBLE);
		} else {
			lyrics_linear.setVisibility(View.GONE);
		}
		image.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
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
		artist_text.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				_artist();
			}
		});
		artist_image.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				bs_base.cancel();
				_artist();
			}
		});
		artist_linear.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				bs_base.cancel();
				_artist();
			}
		});
		album_text.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				bs_base.cancel();
				_album();
			}
		});
		album_image.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				bs_base.cancel();
				_album();
			}
		});
		album_linear.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				bs_base.cancel();
				_album();
			}
		});
		lyrics_text.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				_lyrics();
			}
		});
		lyrics_image.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				_lyrics();
			}
		});
		lyrics_linear.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				_lyrics();
			}
		});
		_marquee(name, map.get((int)0).get("name").toString());
		album.setText(map.get((int)0).get("album").toString());
		artist.setText(map.get((int) 0).get("artist").toString());
		Glide.with(getApplicationContext()).load(Uri.parse(map.get((int) 0).get("image").toString())).into(image);
		bs_base.show();
	}

	public void _artist() {
		if (uri.size() > 1) {
			i.setClass(getApplicationContext(), FullActivity.class);
			i.putExtra("data", new Gson().toJson(uri));
			i.putExtra("artist", map.get((int) 0).get("artist").toString());
			i.putExtra("title", getString(R.string.featured_artists_1));
			startActivity(i);
		} else {
			if (uri.get((int) 0).containsKey("link")) {
			i.setClass(getApplicationContext(), ArtistActivity.class);
			i.putExtra("link", uri.get((int) 0).get("link").toString());
			startActivity(i);
			} else {
				com.google.android.material.snackbar.Snackbar.make(linear1, R.string.artist_will_be_added_soon, Snackbar.LENGTH_INDEFINITE).setAction("Ok", new View.OnClickListener() {
					@Override
					public void onClick(View _view) {}
				}).show();
			}
		}
	}

	public void _album() {
		i.setClass(getApplicationContext(), AlbumActivity.class);
		i.putExtra("link", map.get((int) 0).get("album_uri").toString());
		startActivity(i);
	}

	public void _lyrics() {
		final com.google.android.material.bottomsheet.BottomSheetDialog bs_base = new com.google.android.material.bottomsheet.BottomSheetDialog(MusicActivity.this);
		bs_base.setCancelable(true);
		View layBase = getLayoutInflater().inflate(R.layout.bottom, null);
		bs_base.setContentView(layBase);
		TextView text = (TextView) layBase.findViewById(R.id.text);
		if (play.get((int) 0).containsKey("written")) {
			text.setText(play.get((int) 0).get("text").toString().concat(getString(R.string.written_by)).concat(play.get((int) 0).get("written").toString()));
		} else {
			text.setText(play.get((int) 0).get("text").toString());
		}
		text.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/moscow.ttf"), Typeface.NORMAL);
		TextView about = (TextView) layBase.findViewById(R.id.about);
		about.setVisibility(View.VISIBLE);
		about.setText(map.get((int) 0).get("name").toString().concat("\n").concat(map.get((int) 0).get("artist").toString()));
		about.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/moscow.ttf"), Typeface.NORMAL);
		bs_base.show();
	}

	public void _me() {
		currentfile = map.get((int) 0).get("music").toString();
			if (map.get((int) 0).get("explicit").toString().equals("yes")) {
				explicit.setVisibility(View.VISIBLE);
			} else {
				explicit.setVisibility(View.GONE);
			}
		if (map.get((int) 0).containsKey("text")) {
			imageview6.setVisibility(View.GONE);
			imageview1.setVisibility(View.VISIBLE);
			recyclerview1.setVisibility(View.GONE);
			listview3.setVisibility(View.VISIBLE);
			progressbar1.setVisibility(View.GONE);
			progressbar2.setVisibility(View.GONE);
			linear11.setVisibility(View.GONE);
			linear14.setVisibility(View.GONE);
			_marquee(textview1, map.get((int) 0).get("name").toString());
			_marquee(textview2, map.get((int) 0).get("artist").toString());
			Glide.with(getApplicationContext()).load(Uri.parse(map.get((int) 0).get("image").toString())).into(imageview1);
			Glide.with(getApplicationContext()).load(Uri.parse(map.get((int) 0).get("image").toString())).into(imageview6);
			textview1.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/moscow.ttf"), Typeface.NORMAL);
			textview2.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/moscow.ttf"), Typeface.NORMAL);
				play = new Gson().fromJson(map.get((int) 0).get("text").toString(), new TypeToken<ArrayList<HashMap<String, Object>>>() {}.getType());
				listview3.setAdapter(new Listview1Adapter(play));
			((BaseAdapter)listview3.getAdapter()).notifyDataSetChanged();
			_music();
		} else {
			imageview6.setVisibility(View.VISIBLE);
			imageview1.setVisibility(View.GONE);
			recyclerview1.setVisibility(View.GONE);
			listview3.setVisibility(View.GONE);
			progressbar2.setVisibility(View.GONE);
			linear11.setVisibility(View.VISIBLE);
			linear14.setVisibility(View.VISIBLE);
			_marquee(textview1, map.get((int) 0).get("name").toString());
			_marquee(textview2, map.get((int) 0).get("artist").toString());
			Glide.with(getApplicationContext()).load(Uri.parse(map.get((int) 0).get("image").toString())).into(imageview6);
			textview1.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/moscow.ttf"), Typeface.NORMAL);
			textview2.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/moscow.ttf"), Typeface.NORMAL);
			_music();
		}
	}

	public class Listview1Adapter extends BaseAdapter {
		ArrayList<HashMap<String, Object>> _data;
		public Listview1Adapter(ArrayList<HashMap<String, Object>> _arr) {
			_data = _arr;
		}

		@Override
		public int getCount() {
			return _data.size();
		}

		@Override
		public HashMap<String, Object> getItem(int _index) {
			return _data.get(_index);
		}

		@Override
		public long getItemId(int _index) {
			return _index;
		}
		@Override
		public View getView(final int _position, View _v, ViewGroup _container) {
			LayoutInflater _inflater = (LayoutInflater)getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			View _view = _v;
			if (_view == null) {
				_view = _inflater.inflate(R.layout.text, null);
			}

			final LinearLayout linear1 = (LinearLayout) _view.findViewById(R.id.linear1);
			final TextView textview1 = (TextView) _view.findViewById(R.id.textview1);

			if (play.get((int)_position).containsKey("written")) {
				textview1.setText(play.get((int)_position).get("text").toString().concat(getString(R.string.written_by).concat(play.get((int)_position).get("written").toString())));
				textview1.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/moscow.ttf"), Typeface.NORMAL);
			}
			else {
				textview1.setText(play.get((int)_position).get("text").toString());
				textview1.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/moscow.ttf"), Typeface.NORMAL);
			}

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