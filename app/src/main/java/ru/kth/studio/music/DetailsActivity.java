package ru.kth.studio.music;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.palette.graphics.Palette;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

public class DetailsActivity extends Activity {
    private ImageView imageview1;
    private String str, alb;
    private Bitmap btm;
    private SharedPreferences sp;
    private ArrayList < HashMap < String, Object >> map, perform, lyric, prod = new ArrayList < > ();
    private TextView name, artist, album, performing_text, lyrics_text, production_text;
    private LinearLayout performing, lyrics, production;
    private ListView performing_list, lyrics_list, production_list;

    @Override
    protected void onCreate(Bundle _savedInstanceState) {
        super.onCreate(_savedInstanceState);
        setContentView(R.layout.details);
        initialize();
        com.google.firebase.FirebaseApp.initializeApp(this);
        initializeLogic();}

    private void initialize() {
        imageview1 = findViewById(R.id.imageview1);
        name = findViewById(R.id.name);
        artist = findViewById(R.id.artist);
        album = findViewById(R.id.album);
        performing_text = findViewById(R.id.performing_text);
        lyrics_text = findViewById(R.id.lyrics_text);
        production_text = findViewById(R.id.production_text);
        performing = findViewById(R.id.performing);
        lyrics = findViewById(R.id.lyrics);
        production = findViewById(R.id.production);
        performing_list = findViewById(R.id.performing_list);
        lyrics_list = findViewById(R.id.lyrics_list);
        production_list = findViewById(R.id.production_list);
        sp = getSharedPreferences("sp", Activity.MODE_PRIVATE);}

    private void initializeLogic() {
        switch (sp.getString("theme", "")) {
            case "system": AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM); break;
            case "dark": AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES); break;
            case "light": AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO); break;}
        new DetailsActivity.BackTask().execute(getIntent().getStringExtra("info"));
        performing.setVisibility(View.GONE);
        lyrics.setVisibility(View.GONE);
        production.setVisibility(View.GONE);
       typeface(performing_text);
       typeface(lyrics_text);
       typeface(production_text);
       typeface(name);
       typeface(artist);
       typeface(album);}

    private class BackTask extends AsyncTask< String, Integer, String > {
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
            map = new Gson().fromJson(str, new TypeToken<ArrayList<HashMap< String, Object >>>() {}.getType());
            getBitmapFromURL(getString(R.string.site).concat(map.get(0).get("image").toString()));
            _marquee(name, map.get(0).get("name").toString());
            _marquee(artist, map.get(0).get("artist").toString());
            alb = map.get(0).get("album").toString();
            if (map.get(0).containsKey("additional")) {
                if (map.get(0).get("additional").equals("single")) {
                    alb = alb.concat(" • ").concat(getString(R.string.single));
                } else { alb = alb.concat(" • ").concat(getString(R.string.ep));}}
            _marquee(album, alb);
            Glide.with(getApplicationContext()).load(Uri.parse(getString(R.string.site).concat(map.get(0).get("image").toString()))).into(imageview1);
            if (map.get(0).containsKey("perform")) {
                performing.setVisibility(View.VISIBLE);
                perform = new Gson().fromJson(map.get(0).get("perform").toString(), new TypeToken < ArrayList < HashMap < String, Object >>> () {}.getType());
                _ViewSetHeight(performing_list, perform.size() *getDip(getApplicationContext(), 65));
                performing_text.setText(getString(R.string.performing_artists).concat(" (").concat(String.valueOf(perform.size())).concat(")"));
                performing_list.setAdapter(new DetailsActivity.PerformAdapter(perform));
                ((BaseAdapter) performing_list.getAdapter()).notifyDataSetChanged();}
            if (map.get(0).containsKey("lyrics")) {
                lyrics.setVisibility(View.VISIBLE);
                lyric = new Gson().fromJson(map.get(0).get("lyrics").toString(), new TypeToken < ArrayList < HashMap < String, Object >>> () {}.getType());
                _ViewSetHeight(lyrics_list, lyric.size() *getDip(getApplicationContext(), 65));
                lyrics_text.setText(getString(R.string.composition_lyrics).concat(" (").concat(String.valueOf(lyric.size())).concat(")"));
                lyrics_list.setAdapter(new DetailsActivity.LyricsAdapter(lyric));
                ((BaseAdapter) lyrics_list.getAdapter()).notifyDataSetChanged();}
            if (map.get(0).containsKey("prod")) {
                production.setVisibility(View.VISIBLE);
                prod = new Gson().fromJson(map.get(0).get("prod").toString(), new TypeToken < ArrayList < HashMap < String, Object >>> () {}.getType());
                _ViewSetHeight(production_list, prod.size() *getDip(getApplicationContext(), 65));
                production_text.setText(getString(R.string.production_engineering).concat(" (").concat(String.valueOf(prod.size())).concat(")"));
                production_list.setAdapter(new DetailsActivity.ProdAdapter(prod));
                ((BaseAdapter) production_list.getAdapter()).notifyDataSetChanged();}
        _dumb();}
    }

    private void _dumb() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        if (map.get(0).containsKey("image")) {
            Palette.from(btm).generate(palette -> {
                Palette.Swatch vibrantSwatch = palette.getMutedSwatch();
                int backgroundColor = ContextCompat.getColor(DetailsActivity.this, R.color.inverse);
                int textColor = ContextCompat.getColor(DetailsActivity.this, R.color.text1);
                if(vibrantSwatch != null) {
                    backgroundColor = vibrantSwatch.getRgb();
                    textColor = vibrantSwatch.getTitleTextColor();}
                toolbar.setBackgroundColor(backgroundColor);
                name.setTextColor(textColor);});}}

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

    private void _ViewSetHeight(final View _view, final double _num) { _view.getLayoutParams().height = (int)(_num);}
    private void typeface(TextView _txt) { _txt.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/raleway.ttf"), Typeface.BOLD);}
    private float getDip(Context _context, int _input) { return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, _input, _context.getResources().getDisplayMetrics());}
    public void _marquee(final TextView _textview, final String _text) {
        _textview.setText(_text);
        _textview.setSingleLine(true);
        _textview.setEllipsize(TextUtils.TruncateAt.MARQUEE);
        _textview.setSelected(true);}

    public void _adapter(View _view, int _position, ArrayList<HashMap<String, Object>> _map) {
        final ImageView imageview2 = _view.findViewById(R.id.imageview2);
        final TextView textview2 = _view.findViewById(R.id.textview2);
        final TextView textview3 = _view.findViewById(R.id.textview3);
        if (_map.get(_position).containsKey("image")) { Glide.with(getApplicationContext()).load(Uri.parse(getString(R.string.site).concat(_map.get(_position).get("image").toString()))).into(imageview2);}
        _marquee(textview2, _map.get(_position).get("name").toString());
        _marquee(textview3, _map.get(_position).get("info_".concat(sp.getString("prefix", ""))).toString());
       typeface(textview3);
       typeface(textview2);}

    public class PerformAdapter extends BaseAdapter {
        ArrayList < HashMap < String, Object >> _data;

        public PerformAdapter(ArrayList < HashMap < String, Object >> _arr) { _data = _arr;}

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
            _adapter(_view, _position, perform);
            return _view;}}

    public class LyricsAdapter extends BaseAdapter {
        ArrayList < HashMap < String, Object >> _data;

        public LyricsAdapter(ArrayList < HashMap < String, Object >> _arr) { _data = _arr;}

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
            _adapter(_view, _position, lyric);
            return _view;}}

    public class ProdAdapter extends BaseAdapter {
        ArrayList < HashMap < String, Object >> _data;

        public ProdAdapter(ArrayList < HashMap < String, Object >> _arr) { _data = _arr; }

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
            _adapter(_view, _position, prod);
            return _view;}}
}