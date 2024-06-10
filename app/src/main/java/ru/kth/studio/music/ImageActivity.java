package ru.kth.studio.music;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.palette.graphics.Palette;
import android.app.Activity;
import android.os.*;
import android.text.TextUtils;
import android.view.*;
import android.widget.*;
import android.content.*;
import android.graphics.*;
import android.webkit.WebView;
import android.webkit.WebSettings;
import android.webkit.WebViewClient;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Objects;

public class ImageActivity extends AppCompatActivity {
	private int bg, txt = 0;
	private ImageView back, more;
	private Toolbar toolbar;
	private WebView webview1;
	private TextView t1, t2, t3, t4, t5, t6, name, artist, album;
	private LinearLayout test;
	private Bitmap btm;
	private SharedPreferences sp;

	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);
		setContentView(R.layout.image);
		initialize();
		initializeLogic();}

	private void initialize() {
		back = findViewById(R.id.back);
		more = findViewById(R.id.more);
		toolbar = findViewById(R.id.toolbar);
		t1 = findViewById(R.id.t1);
		t2 = findViewById(R.id.t2);
		t3 = findViewById(R.id.t3);
		t4 = findViewById(R.id.t4);
		t5 = findViewById(R.id.t5);
		t6 = findViewById(R.id.t6);
		test = findViewById(R.id.test);
		webview1 = findViewById(R.id.webview1);
		name = findViewById(R.id.name);
		artist = findViewById(R.id.artist);
		album = findViewById(R.id.album);
		webview1.getSettings().setJavaScriptEnabled(true);
		webview1.getSettings().setSupportZoom(true);
		sp = getSharedPreferences("sp", Activity.MODE_PRIVATE);

		back.setOnClickListener(v -> onBackPressed());
		webview1.setWebViewClient(new WebViewClient() {
			@Override
			public void onPageStarted(WebView _param1, String _param2, Bitmap _param3) { super.onPageStarted(_param1, _param2, _param3);}

			@Override
			public void onPageFinished(WebView _param1, String _param2) { super.onPageFinished(_param1, _param2);}});}

	private void initializeLogic() {
		webview1.loadUrl(Objects.requireNonNull(getIntent().getStringExtra("image_q")));
		getBitmapFromURL(getIntent().getStringExtra("image_q"));
		webview1.getSettings().setLoadWithOverviewMode(false);
		webview1.getSettings().setDisplayZoomControls(false);
		webview1.getSettings().setBuiltInZoomControls(true);
		webview1.getSettings().setLoadWithOverviewMode(true);
		webview1.getSettings().setUseWideViewPort(true);
		final WebSettings webSettings = webview1.getSettings();
		final String newUserAgent;
		newUserAgent = "Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/41.0.2228.0 Safari/537.36";
		webSettings.setUserAgentString(newUserAgent);
		Palette.from(btm).generate(palette -> {
            assert palette != null;
            Palette.Swatch vibrantSwatch1 = palette.getLightVibrantSwatch();
			Palette.Swatch vibrantSwatch2 = palette.getVibrantSwatch();
			Palette.Swatch vibrantSwatch3 = palette.getDarkVibrantSwatch();
			Palette.Swatch vibrantSwatch4 = palette.getLightMutedSwatch();
			Palette.Swatch vibrantSwatch5 = palette.getMutedSwatch();
			Palette.Swatch vibrantSwatch6 = palette.getDarkMutedSwatch();
			switch (Objects.requireNonNull(getIntent().getStringExtra("color"))) {
				case "1": if (vibrantSwatch1 != null) {
					bg = vibrantSwatch1.getRgb();
					txt = vibrantSwatch1.getTitleTextColor();}
					_title(); break;
				case "2": if (vibrantSwatch2 != null) {
					bg = vibrantSwatch2.getRgb();
					txt = vibrantSwatch2.getTitleTextColor();}
					_title(); break;
				case "3": if (vibrantSwatch3 != null) {
					bg = vibrantSwatch3.getRgb();
					txt = vibrantSwatch3.getTitleTextColor();}
					_title(); break;
				case "4": if (vibrantSwatch4 != null) {
					bg = vibrantSwatch4.getRgb();
					txt = vibrantSwatch4.getTitleTextColor();}
					_title(); break;
				case "5": if (vibrantSwatch5 != null) {
					bg = vibrantSwatch5.getRgb();
					txt = vibrantSwatch5.getTitleTextColor();}
					_title(); break;
				case "6": if (vibrantSwatch6 != null) {
					bg = vibrantSwatch6.getRgb();
					txt = vibrantSwatch6.getTitleTextColor();}
					_title(); break;
				case "0": _test_features(); break;}});

		if (Objects.equals(getIntent().getStringExtra("name"), "")) { name.setVisibility(View.GONE);
		} else { _marquee(name, getIntent().getStringExtra("name")); typeface(name);}
        if (Objects.requireNonNull(getIntent().getStringExtra("artist")).isEmpty()) {
            artist.setVisibility(View.GONE); } else {
            _marquee(artist, getIntent().getStringExtra("artist"));
            typeface(artist);}
        if (Objects.requireNonNull(getIntent().getStringExtra("album")).equals("")) {
            album.setVisibility(View.GONE); } else {
            _marquee(album, getIntent().getStringExtra("album"));
            typeface(album);}}

	private void _title() {
		toolbar.setBackgroundColor(bg);
		back.setColorFilter(txt, PorterDuff.Mode.SRC_IN);
		more.setColorFilter(txt, PorterDuff.Mode.SRC_IN);
		name.setTextColor(txt);
		artist.setTextColor(txt);
		album.setTextColor(txt);
	}

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
		else { test.setVisibility(View.GONE);}}

	private void typeface(TextView _txt) { _txt.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/raleway.ttf"), Typeface.BOLD);}
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

	public void _marquee(final TextView _textview, final String _text) {
		_textview.setText(_text);
		_textview.setSingleLine(true);
		_textview.setEllipsize(TextUtils.TruncateAt.MARQUEE);
		_textview.setSelected(true);}

	@Override
	protected void onActivityResult(int _requestCode, int _resultCode, Intent _data) { super.onActivityResult(_requestCode, _resultCode, _data);}}