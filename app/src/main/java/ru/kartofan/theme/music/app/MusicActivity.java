package ru.kartofan.theme.music.app;

import androidx.appcompat.app.AppCompatActivity;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.*;
import android.text.style.ForegroundColorSpan;
import android.view.*;
import android.widget.*;
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
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import android.widget.SeekBar;
import android.media.MediaPlayer;
import java.util.Timer;
import java.util.TimerTask;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import com.bumptech.glide.Glide;
import android.graphics.Typeface;
import java.text.DecimalFormat;
import android.app.Notification;
import android.support.v4.media.session.MediaSessionCompat;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import static ru.kartofan.theme.music.app.App.CHANNEL_2_ID;

public class MusicActivity extends AppCompatActivity {
	private final Timer _timer = new Timer();
	private String str;
	private String namestr;
	private String infostr;
	private String notifname;
	private String currentfile;
	private String text;
	private String lyrics;
	private String str1;
	private String image;
	private String str2;
	private final String color = "";
	private double song_duration, pos, time, play1, tr = 0;
	private ArrayList < HashMap < String, Object >> map;
	private ArrayList < HashMap < String, Object >> play;
	private ArrayList < HashMap < String, Object >> uri;
	private final ArrayList < HashMap < String, Object >> artists = new ArrayList < > ();
	private final ArrayList < String > n = new ArrayList < > ();
	private LinearLayout linear1, linear11, linear14, linear2, linear3;
	private TextView textview5, textview1, textview2, textview3, textview4;
	private ImageView imageview6, imageview1, imageview2, imageview3, imageview4, imageview5;
	private ProgressBar progressbar1, progressbar2;
	private ListView listview3;
	private RecyclerView recyclerview1;
	private SeekBar seekbar1;
	private MediaPlayer raone;
	private TimerTask t;
	private final Intent i = new Intent();
	private SharedPreferences sp;
    private NotificationManagerCompat notificationManager;
    private MediaSessionCompat mediaSession;

	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);
		setContentView(R.layout.music);
		notificationManager = NotificationManagerCompat.from(this);
        mediaSession = new MediaSessionCompat(this, "tag");
		initialize();
		initializeLogic();
	}

	private void initialize() {
		seekbar1 = findViewById(R.id.seekbar1);
		linear1 = findViewById(R.id.linear1);
		textview5 = findViewById(R.id.textview5);
		linear11 = findViewById(R.id.linear11);
		imageview6 = findViewById(R.id.imageview6);
		linear14 = findViewById(R.id.linear14);
		linear2 = findViewById(R.id.linear2);
		progressbar1 = findViewById(R.id.progressbar1);
		progressbar2 = findViewById(R.id.progressbar2);
		listview3 = findViewById(R.id.listview3);
		recyclerview1 = findViewById(R.id.recyclerview1);
		imageview1 = findViewById(R.id.imageview1);
		linear3 = findViewById(R.id.linear3);
		imageview2 = findViewById(R.id.imageview2);
		textview1 = findViewById(R.id.textview1);
		textview2 = findViewById(R.id.textview2);
		textview3 = findViewById(R.id.textview3);
		textview4 = findViewById(R.id.textview4);
		imageview3 = findViewById(R.id.imageview3);
		imageview4 = findViewById(R.id.imageview4);
		imageview5 = findViewById(R.id.imageview5);
		sp = getSharedPreferences("sp", Activity.MODE_PRIVATE);

		seekbar1.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
			@Override
			public void onProgressChanged(SeekBar _param1, int _param2, boolean _param3) {
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
						runOnUiThread(() -> {
							textview3.setText(new DecimalFormat("00").format((raone.getCurrentPosition() / 1000) / 60).concat(":".concat(new DecimalFormat("00").format((raone.getCurrentPosition() / 1000) % 60))));
							textview4.setText("-".concat(new DecimalFormat("00").format(((raone.getDuration() - raone.getCurrentPosition()) / 1000) / 60).concat(":".concat(new DecimalFormat("00").format(((raone.getDuration() - raone.getCurrentPosition()) / 1000) % 60)))));
							textview5.setText(new DecimalFormat("00").format((seekbar1.getProgress() / 1000) / 60).concat(":".concat(new DecimalFormat("00").format((seekbar1.getProgress() / 1000) % 60))));
							textview5.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/moscow.ttf"), Typeface.NORMAL);
						});
					}
				};
				_timer.scheduleAtFixedRate(t, 100, 100);
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
				}
				raone.seekTo(seekbar1.getProgress());
				raone.start();
				_player();
			}
		});

		imageview2.setOnClickListener(_view -> _bottom());
		textview2.setOnClickListener(_view -> _bottom());
		textview1.setOnClickListener(_view -> _bottom());
		linear3.setOnClickListener(_view -> _bottom());

		imageview6.setOnClickListener(_view -> {
			if (map.get(0).containsKey("text")) {
				imageview6.setVisibility(View.GONE);
				imageview1.setVisibility(View.VISIBLE);
				recyclerview1.setVisibility(View.VISIBLE);
				listview3.setVisibility(View.VISIBLE);
				linear11.setVisibility(View.GONE);
				linear14.setVisibility(View.GONE);
			}
		});

		imageview3.setOnClickListener(_view -> {
			if (map.get(0).containsKey("previous")) {
					raone.pause();
					t.cancel();
				new BackTask().execute(map.get(0).get("previous").toString());
			}
		});

		imageview5.setOnClickListener(_view -> {
			if (map.get(0).containsKey("next")) {
					raone.pause();
					t.cancel();
				new BackTask().execute(map.get(0).get("next").toString());
			}
		});

		listview3.setOnItemClickListener((_param1, _param2, _param3, _param4) -> {
			if (play.get(_param3).get("text").toString().equals("!")) {
				i.setClass(getApplicationContext(), ImageActivity.class);
				i.putExtra("imageq", play.get(_param3).get("link").toString());
				startActivity(i);
			}
		});

		imageview1.setOnClickListener(_view -> {
			imageview6.setVisibility(View.VISIBLE);
			imageview1.setVisibility(View.GONE);
			recyclerview1.setVisibility(View.GONE);
			listview3.setVisibility(View.GONE);
			linear11.setVisibility(View.VISIBLE);
			linear14.setVisibility(View.VISIBLE);
		});

		imageview4.setOnClickListener(_view -> {
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
					if (map.get(0).containsKey("next")) {
						new BackTask().execute(map.get(0).get("next").toString());
					} else {
						_music();
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
			uri = new Gson().fromJson(map.get(0).get("artist_uri").toString(), new TypeToken<ArrayList<HashMap<String, Object>>>() {}.getType());
			if (sp.getString("player", "").equals("yes")) {
				if (raone != null && raone.isPlaying()) {
					raone.stop();
				}
			}
			_me();
		}
	}

	public void _player() {
		imageview4.setImageResource(R.drawable.ic_pause);
		seekbar1.setMax(raone.getDuration());
		seekbar1.setProgress((int) song_duration);
		t = new TimerTask() {
			@Override
			public void run() {
				runOnUiThread(() -> {
						textview3.setText(new DecimalFormat("00").format((raone.getCurrentPosition() / 1000) / 60).concat(":".concat(new DecimalFormat("00").format((raone.getCurrentPosition() / 1000) % 60))));
						textview4.setText("-".concat(new DecimalFormat("00").format(((raone.getDuration() - raone.getCurrentPosition()) / 1000) / 60).concat(":".concat(new DecimalFormat("00").format(((raone.getDuration() - raone.getCurrentPosition()) / 1000) % 60)))));
						textview4.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/moscow.ttf"), Typeface.NORMAL);
						textview3.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/moscow.ttf"), Typeface.NORMAL);
						seekbar1.setProgress(raone.getCurrentPosition());
						song_duration = raone.getCurrentPosition();
						seekbar1.setProgress((int) song_duration);
				});
			}
		};
		_timer.scheduleAtFixedRate(t, 0, 100);
	}

	public void _music() {
		currentfile = map.get(0).get("music").toString();
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
		raone.setOnCompletionListener(mp -> {
			if (map.get(0).containsKey("next")) {
				new BackTask().execute(map.get(0).get("next").toString());
			}
			imageview4.setImageResource(R.drawable.ic_play_arrow);
			t.cancel();
		});
		play1 = 1;
		song_duration = raone.getCurrentPosition();
		seekbar1.setProgress(raone.getCurrentPosition());
		seekbar1.setMax(raone.getDuration());
		textview4.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/moscow.ttf"), Typeface.NORMAL);
		textview3.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/moscow.ttf"), Typeface.NORMAL);
		if (map.get(0).containsKey("next")) {
			imageview5.setAlpha((float)(1));
		} else {
			imageview5.setAlpha((float)(0.5d));
		}
		if (map.get(0).containsKey("previous")) {
			imageview3.setAlpha((float)(1));
		} else {
			imageview3.setAlpha((float)(0.5d));
		}
		_player();
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

	public void _bottom() {
		final com.google.android.material.bottomsheet.BottomSheetDialog bs_base = new com.google.android.material.bottomsheet.BottomSheetDialog(MusicActivity.this);
		bs_base.setCancelable(true);
		View layBase = getLayoutInflater().inflate(R.layout.info, null);
		bs_base.setContentView(layBase);
		ImageView image = layBase.findViewById(R.id.image);
		ImageView artist_image = layBase.findViewById(R.id.artist_image);
		ImageView album_image = layBase.findViewById(R.id.album_image);
		ImageView lyrics_image = layBase.findViewById(R.id.lyrics_image);
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
		name.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/moscow.ttf"), Typeface.NORMAL);
		album.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/moscow.ttf"), Typeface.NORMAL);
		artist.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/moscow.ttf"), Typeface.NORMAL);
		artist_text.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/moscow.ttf"), Typeface.NORMAL);
		album_text.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/moscow.ttf"), Typeface.NORMAL);
		lyrics_text.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/moscow.ttf"), Typeface.NORMAL);
		info_text.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/moscow.ttf"), Typeface.NORMAL);
		if (uri.size() > 1) {
			artist_image.setImageResource(R.drawable.ic_people);
			artist_text.setText(getString(R.string.go_to_artists));
		} else {
			artist_image.setImageResource(R.drawable.ic_timer_auto);
			artist_text.setText(getString(R.string.go_to_artist));
		}
		if (map.get(0).containsKey("text")) {
			lyrics_linear.setVisibility(View.VISIBLE);
		} else {
			lyrics_linear.setVisibility(View.GONE);
		}
		image.setOnClickListener(v -> {
			if (map.get(0).containsKey("image4k")) {
				if (sp.getString("quality", "").equals("mobile")) {
					i.setClass(getApplicationContext(), ImageActivity.class);
					i.putExtra("imageq", map.get(0).get("image4k").toString());
					i.putExtra("name", name.getText().toString().concat(" - ").concat(artist.getText().toString()));
					i.putExtra("artist", album.getText().toString());
					startActivity(i);
				} else {
					i.setClass(getApplicationContext(), ImageActivity.class);
					i.putExtra("imageq", map.get(0).get("image").toString());
					i.putExtra("name", name.getText().toString().concat(" - ").concat(artist.getText().toString()));
					i.putExtra("artist", album.getText().toString());
					startActivity(i);
				}
			} else {
				i.setClass(getApplicationContext(), ImageActivity.class);
				i.putExtra("imageq", map.get(0).get("image").toString());
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
		if (map.get(0).get("explicit").toString().equals("yes")) {
			if (map.get(0).containsKey("prefix")) {
				infostr = map.get(0).get("name").toString().concat(" ").concat(map.get(0).get("prefix").toString()).concat(" 🅴");
			} else {
				infostr = map.get(0).get("name").toString().concat(" 🅴");
			}
		} else {
			if (map.get(0).containsKey("prefix")) {
				infostr = map.get(0).get("name").toString().concat(" ").concat(map.get(0).get("prefix").toString());
			} else {
				infostr = map.get(0).get("name").toString();
			}
		}
		SpannableStringBuilder ssb = new SpannableStringBuilder(infostr);
		int color = ContextCompat.getColor(MusicActivity.this, R.color.text2);
		ForegroundColorSpan fcsRed = new ForegroundColorSpan(color);
		ssb.setSpan(fcsRed, map.get(0).get("name").toString().length(), infostr.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
		_marque(name, ssb);
		if (map.get(0).containsKey("additional")) {
			if (map.get(0).get("additional").equals("single")) {
				_marquee(album, map.get(0).get("album").toString().concat(" - ").concat(getString(R.string.single)));
			} else {
				_marquee(album, map.get(0).get("album").toString().concat(" - ").concat(getString(R.string.ep)));
			}
		} else {
			_marquee(album, map.get(0).get("album").toString());
		}
		artist.setText(map.get(0).get("artist").toString());
		Glide.with(getApplicationContext()).load(Uri.parse(map.get(0).get("image").toString())).into(image);
		bs_base.show();
	}

	public void _artist() {
		if (uri.size() > 1) {
			i.setClass(getApplicationContext(), FullActivity.class);
			i.putExtra("data", new Gson().toJson(uri));
			i.putExtra("artist", map.get(0).get("artist").toString());
			i.putExtra("title", getString(R.string.featured_artists_1));
			startActivity(i);
		} else {
			if (uri.get(0).containsKey("link")) {
			i.setClass(getApplicationContext(), ArtistActivity.class);
			i.putExtra("link", uri.get(0).get("link").toString());
			startActivity(i);
			} else {
				com.google.android.material.snackbar.Snackbar.make(linear1, R.string.artist_will_be_added_soon, Snackbar.LENGTH_INDEFINITE).setAction("Ok", _view -> {}).show();
			}
		}
	}

	public void _album() {
		i.setClass(getApplicationContext(), AlbumActivity.class);
		i.putExtra("link", map.get(0).get("album_uri").toString());
		startActivity(i);
	}

	public void _lyrics() {
		final com.google.android.material.bottomsheet.BottomSheetDialog bs_base = new com.google.android.material.bottomsheet.BottomSheetDialog(MusicActivity.this);
		bs_base.setCancelable(true);
		View layBase = getLayoutInflater().inflate(R.layout.bottom, null);
		bs_base.setContentView(layBase);
		TextView text = layBase.findViewById(R.id.text);
		if (play.get(0).containsKey("written")) {
			text.setText(play.get(0).get("text").toString().concat(getString(R.string.written_by)).concat(play.get(0).get("written").toString()));
		} else {
			text.setText(play.get(0).get("text").toString());
		}
		text.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/moscow.ttf"), Typeface.NORMAL);
		TextView about = layBase.findViewById(R.id.about);
		about.setVisibility(View.VISIBLE);
		if (map.get(0).get("explicit").toString().equals("yes")) {
			if (map.get(0).containsKey("prefix")) {
				lyrics = map.get(0).get("name").toString().concat(" ").concat(map.get(0).get("prefix").toString()).concat(" 🅴\n").concat(map.get(0).get("artist").toString());
			} else {
				lyrics = map.get(0).get("name").toString().concat(" 🅴\n").concat(map.get(0).get("artist").toString());
			}
		} else {
			if (map.get(0).containsKey("prefix")) {
				lyrics = map.get(0).get("name").toString().concat(" ").concat(map.get(0).get("prefix").toString()).concat(" \n").concat(map.get(0).get("artist").toString());
			} else {
				lyrics = map.get(0).get("name").toString().concat("\n").concat(map.get(0).get("artist").toString());
			}
		}
		SpannableStringBuilder ssb = new SpannableStringBuilder(lyrics);
		int color = ContextCompat.getColor(this, R.color.text2);
		ForegroundColorSpan fcsRed = new ForegroundColorSpan(color);
		ssb.setSpan(fcsRed, map.get(0).get("name").toString().length() + 1, lyrics.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
		about.setText(ssb);
		about.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/moscow.ttf"), Typeface.NORMAL);
		bs_base.show();
	}

    public void sendOnChannel2(View v) {
		if (map.get(0).get("explicit").toString().equals("yes")) {
			if (map.get(0).containsKey("prefix")) {
				notifname = map.get(0).get("name").toString().concat(" ").concat(map.get(0).get("prefix").toString()).concat(" 🅴");
			} else {
				notifname = map.get(0).get("name").toString().concat(" 🅴");
			}
		} else {
			if (map.get(0).containsKey("prefix")) {
				notifname = map.get(0).get("name").toString().concat(" ").concat(map.get(0).get("prefix").toString());
			} else {
				notifname = map.get(0).get("name").toString();
			}
		}
		image = map.get(0).get("image").toString();
		Bitmap artwork = BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher_background);
        Notification notification = new NotificationCompat.Builder(this, CHANNEL_2_ID)
                .setSmallIcon(R.mipmap.ic_launcher_foreground)
                .setContentTitle(notifname)
                .setContentText(map.get(0).get("artist").toString())
				.setLargeIcon(artwork)
                .addAction(R.drawable.ic_fast_rewind, "Previous", null)
                .addAction(R.drawable.ic_pause, "Pause", null)
                .addAction(R.drawable.ic_fast_forward, "Next", null)
                .setStyle(new androidx.media.app.NotificationCompat.MediaStyle()
                        .setShowActionsInCompactView(1)
                        .setMediaSession(mediaSession.getSessionToken()))
                .setSubText(map.get(0).get("album").toString())
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .build();
        notificationManager.notify(2, notification);
    }

	public void _me() {
		currentfile = map.get(0).get("music").toString();
		if (map.get(0).get("explicit").toString().equals("yes")) {
			if (map.get(0).containsKey("prefix")) {
				namestr = map.get(0).get("name").toString().concat(" ").concat(map.get(0).get("prefix").toString()).concat(" 🅴");
			} else {
				namestr = map.get(0).get("name").toString().concat(" 🅴");
			}
		} else {
			if (map.get(0).containsKey("prefix")) {
				namestr = map.get(0).get("name").toString().concat(" ").concat(map.get(0).get("prefix").toString());
			} else {
				namestr = map.get(0).get("name").toString();
			}
		}
		SpannableStringBuilder ssb = new SpannableStringBuilder(namestr);
		int color = ContextCompat.getColor(MusicActivity.this, R.color.text2);
		ForegroundColorSpan fcsRed = new ForegroundColorSpan(color);
		ssb.setSpan(fcsRed, map.get(0).get("name").toString().length(), namestr.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
		_marque(textview1, ssb);
		_marquee(textview2, map.get(0).get("artist").toString());
		textview1.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/moscow.ttf"), Typeface.NORMAL);
		textview2.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/moscow.ttf"), Typeface.NORMAL);
		Glide.with(getApplicationContext()).load(Uri.parse(map.get(0).get("image").toString())).into(imageview6);
		_music();
		if (map.get(0).containsKey("text")) {
			imageview6.setVisibility(View.GONE);
			imageview1.setVisibility(View.VISIBLE);
			recyclerview1.setVisibility(View.GONE);
			listview3.setVisibility(View.VISIBLE);
			progressbar1.setVisibility(View.GONE);
			progressbar2.setVisibility(View.GONE);
			linear11.setVisibility(View.GONE);
			linear14.setVisibility(View.GONE);
			play = new Gson().fromJson(map.get(0).get("text").toString(), new TypeToken<ArrayList<HashMap<String, Object>>>() {}.getType());
			listview3.setAdapter(new Listview1Adapter(play));
			((BaseAdapter)listview3.getAdapter()).notifyDataSetChanged();
			Glide.with(getApplicationContext()).load(Uri.parse(map.get(0).get("image").toString())).into(imageview1);
		} else {
			imageview6.setVisibility(View.VISIBLE);
			imageview1.setVisibility(View.GONE);
			recyclerview1.setVisibility(View.GONE);
			listview3.setVisibility(View.GONE);
			progressbar2.setVisibility(View.GONE);
			linear11.setVisibility(View.VISIBLE);
			linear14.setVisibility(View.VISIBLE);
			}
		sendOnChannel2(imageview6);
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
			final TextView textview1 = _view.findViewById(R.id.textview1);
			if (play.get(_position).containsKey("written")) {
				textview1.setText(play.get(_position).get("text").toString().concat(getString(R.string.written_by).concat(play.get(_position).get("written").toString())));
				textview1.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/moscow.ttf"), Typeface.NORMAL);
			} else {
				textview1.setText(play.get(_position).get("text").toString());
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