package ru.kartofan.theme.music.app;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatDelegate;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.util.ArrayList;
import java.util.HashMap;

public class DetailsActivity extends Activity {

    private ImageView imageview1;
    private String str;
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
        initializeLogic();
    }

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
        sp = getSharedPreferences("sp", Activity.MODE_PRIVATE);
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
        new DetailsActivity.BackTask().execute(getIntent().getStringExtra("info"));
        performing.setVisibility(View.GONE);
        lyrics.setVisibility(View.GONE);
        production.setVisibility(View.GONE);
        _typeface(performing_text);
        _typeface(lyrics_text);
        _typeface(production_text);
        _typeface(name);
        _typeface(artist);
        _typeface(album);
    }

    private class BackTask extends AsyncTask< String, Integer, String > {
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
            map = new Gson().fromJson(str, new TypeToken<ArrayList<HashMap< String, Object >>>() {}.getType());
            _marquee(name, map.get(0).get("name").toString());
            _marquee(artist, map.get(0).get("artist").toString());
            if (map.get(0).containsKey("additional")) {
                if (map.get(0).get("additional").toString().equals("single")) {
                    _marquee(album, map.get(0).get("album").toString().concat(" - ").concat(getString(R.string.single)).concat(" • ").concat(map.get(0).get("date_".concat(sp.getString("prefix", ""))).toString()));
                } else {
                    _marquee(album, map.get(0).get("album").toString().concat(" - ").concat(getString(R.string.ep)).concat(" • ").concat(map.get(0).get("date_".concat(sp.getString("prefix", ""))).toString()));
                }
            } else {
                _marquee(album, map.get(0).get("album").toString().concat(" • ").concat(map.get(0).get("date_".concat(sp.getString("prefix", ""))).toString()));
            }
            Glide.with(getApplicationContext()).load(Uri.parse(map.get(0).get("image").toString())).into(imageview1);
            if (map.get(0).containsKey("perform")) {
                performing.setVisibility(View.VISIBLE);
                perform = new Gson().fromJson(map.get(0).get("perform").toString(), new TypeToken < ArrayList < HashMap < String, Object >>> () {}.getType());
                _ViewSetHeight(performing_list, perform.size() * kTHUtil.getDip(getApplicationContext(), 65));
                performing_text.setText(getString(R.string.performing_artists).concat(" (").concat(String.valueOf(perform.size())).concat(")"));
                performing_list.setAdapter(new DetailsActivity.PerformAdapter(perform));
                ((BaseAdapter) performing_list.getAdapter()).notifyDataSetChanged();
            }
            if (map.get(0).containsKey("lyrics")) {
                lyrics.setVisibility(View.VISIBLE);
                lyrics = new Gson().fromJson(map.get(0).get("lyrics").toString(), new TypeToken < ArrayList < HashMap < String, Object >>> () {}.getType());
                _ViewSetHeight(lyrics_list, lyric.size() * kTHUtil.getDip(getApplicationContext(), 65));
                lyrics_text.setText(getString(R.string.composition_lyrics).concat(" (").concat(String.valueOf(lyric.size())).concat(")"));
                lyrics_list.setAdapter(new DetailsActivity.LyricsAdapter(lyric));
                ((BaseAdapter) lyrics_list.getAdapter()).notifyDataSetChanged();
            }
            if (map.get(0).containsKey("prod")) {
                production.setVisibility(View.VISIBLE);
                prod = new Gson().fromJson(map.get(0).get("prod").toString(), new TypeToken < ArrayList < HashMap < String, Object >>> () {}.getType());
                _ViewSetHeight(production_list, prod.size() * kTHUtil.getDip(getApplicationContext(), 65));
                production_text.setText(getString(R.string.production_engineering).concat(" (").concat(String.valueOf(prod.size())).concat(")"));
                production_list.setAdapter(new DetailsActivity.ProdAdapter(prod));
                ((BaseAdapter) production_list.getAdapter()).notifyDataSetChanged();
            }
        }
    }

    private void _ViewSetHeight(final View _view, final double _num) {
        _view.getLayoutParams().height = (int)(_num);
    }

    public void _typeface(TextView _txt) {
        _txt.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/moscow.ttf"), Typeface.NORMAL);
    }

    public void _marquee(final TextView _textview, final String _text) {
        _textview.setText(_text);
        _textview.setSingleLine(true);
        _textview.setEllipsize(TextUtils.TruncateAt.MARQUEE);
        _textview.setSelected(true);
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    public class PerformAdapter extends BaseAdapter {
        ArrayList < HashMap < String, Object >> _data;

        public PerformAdapter(ArrayList < HashMap < String, Object >> _arr) {
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
            if (perform.get(_position).containsKey("image")) {
                Glide.with(getApplicationContext()).load(Uri.parse(perform.get(_position).get("image").toString())).into(imageview2);
            }
            _marquee(textview2, perform.get(_position).get("name").toString());
            _marquee(textview3, perform.get(_position).get("info_".concat(sp.getString("prefix", ""))).toString());
            _typeface(textview3);
            _typeface(textview2);
            return _view;
        }
    }

    public class LyricsAdapter extends BaseAdapter {
        ArrayList < HashMap < String, Object >> _data;

        public LyricsAdapter(ArrayList < HashMap < String, Object >> _arr) {
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
            if (lyric.get(_position).containsKey("image")) {
                Glide.with(getApplicationContext()).load(Uri.parse(lyric.get(_position).get("image").toString())).into(imageview2);
            }
            _marquee(textview2, lyric.get(_position).get("name").toString());
            _marquee(textview3, lyric.get(_position).get("info_".concat(sp.getString("prefix", ""))).toString());
            _typeface(textview3);
            _typeface(textview2);
            return _view;
        }
    }

    public class ProdAdapter extends BaseAdapter {
        ArrayList < HashMap < String, Object >> _data;

        public ProdAdapter(ArrayList < HashMap < String, Object >> _arr) {
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
            if (prod.get(_position).containsKey("image")) {
                Glide.with(getApplicationContext()).load(Uri.parse(prod.get(_position).get("image").toString())).into(imageview2);
            }
            _marquee(textview2, prod.get(_position).get("name").toString());
            _marquee(textview3, prod.get(_position).get("info_".concat(sp.getString("prefix", ""))).toString());
            _typeface(textview3);
            _typeface(textview2);
            return _view;
        }
    }
}
