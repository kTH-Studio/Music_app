package ru.kth.studio.music;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.content.ContextCompat;
import com.google.android.material.appbar.AppBarLayout;
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
import android.widget.ListView;
import android.widget.BaseAdapter;
import android.content.Intent;
import android.net.Uri;
import android.app.Activity;
import android.content.SharedPreferences;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.bumptech.glide.Glide;
import android.graphics.Typeface;

public class FullActivity extends AppCompatActivity {
    private TextView name, artist, album;
    private String playstr, time, date, month;
    private AppBarLayout _app_bar;
    private CoordinatorLayout _coordinator;
    private ArrayList < HashMap < String, Object >> play = new ArrayList < > ();
    private ListView listview1;
    private final Intent i = new Intent();
    private SharedPreferences sp;
    @Override
    protected void onCreate(Bundle _savedInstanceState) {
        super.onCreate(_savedInstanceState);
        setContentView(R.layout.full);
        initialize();
        com.google.firebase.FirebaseApp.initializeApp(this);
        initializeLogic();
    }

    private void initialize() {
        name = findViewById(R.id.name);
        artist = findViewById(R.id.artist);
        album = findViewById(R.id.album);
        listview1 = findViewById(R.id.listview1);
        sp = getSharedPreferences("sp", Activity.MODE_PRIVATE);
        listview1.setOnItemClickListener((_param1, _param2, _param3, _param4) -> {
            if (play.get(_param3).containsKey("link")) {
                if (getIntent().getStringExtra("title").equals(getString(R.string.featured_artists_1))) {
                    i.setClass(getApplicationContext(), ArtistActivity.class);
                } else {
                    i.setClass(getApplicationContext(), AlbumActivity.class);
                }
                i.putExtra("link", getString(R.string.site).concat(play.get(_param3).get("link").toString()));
                startActivity(i); } else {
                if (getIntent().getStringExtra("title").equals(getString(R.string.albums_1))) {
                    com.google.android.material.snackbar.Snackbar.make(listview1, R.string.album_will_be_added_soon, com.google.android.material.snackbar.Snackbar.LENGTH_SHORT).setAction("ok", _view -> {}).show();
                } else if (getIntent().getStringExtra("title").equals(getString(R.string.latest_release_1))) {
                    com.google.android.material.snackbar.Snackbar.make(listview1, R.string.album_will_be_added_soon, com.google.android.material.snackbar.Snackbar.LENGTH_SHORT).setAction("ok", _view -> {}).show();
                } else if (getIntent().getStringExtra("title").equals(getString(R.string.top_songs_1))) {
                    com.google.android.material.snackbar.Snackbar.make(listview1, R.string.song_will_be_added_soon, com.google.android.material.snackbar.Snackbar.LENGTH_SHORT).setAction("ok", _view -> {}).show();
                } else if (getIntent().getStringExtra("title").equals(getString(R.string.singles_1))) {
                    com.google.android.material.snackbar.Snackbar.make(listview1, R.string.song_will_be_added_soon, com.google.android.material.snackbar.Snackbar.LENGTH_SHORT).setAction("ok", _view -> {}).show();
                } else if (getIntent().getStringExtra("title").equals(getString(R.string.appears_on_1))) {
                    com.google.android.material.snackbar.Snackbar.make(listview1, R.string.album_will_be_added_soon, com.google.android.material.snackbar.Snackbar.LENGTH_SHORT).setAction("ok", _view -> {}).show();
                } else if (getIntent().getStringExtra("title").equals(getString(R.string.compilations_1))) {
                    com.google.android.material.snackbar.Snackbar.make(listview1, R.string.album_will_be_added_soon, com.google.android.material.snackbar.Snackbar.LENGTH_SHORT).setAction("ok", _view -> {}).show();
                } else if (getIntent().getStringExtra("title").equals(getString(R.string.live_1))) {
                    com.google.android.material.snackbar.Snackbar.make(listview1, R.string.album_will_be_added_soon, com.google.android.material.snackbar.Snackbar.LENGTH_SHORT).setAction("ok", _view -> {}).show();
                } else if (getIntent().getStringExtra("title").equals(getString(R.string.other_versions_1))) {
                    com.google.android.material.snackbar.Snackbar.make(listview1, R.string.album_will_be_added_soon, com.google.android.material.snackbar.Snackbar.LENGTH_SHORT).setAction("ok", _view -> {}).show();
                } else if (getIntent().getStringExtra("title").equals(getString(R.string.featured_artists_1))) {
                    com.google.android.material.snackbar.Snackbar.make(listview1, R.string.artist_will_be_added_soon, com.google.android.material.snackbar.Snackbar.LENGTH_SHORT).setAction("ok", _view -> {}).show();
                } else {
                    com.google.android.material.snackbar.Snackbar.make(listview1, R.string.album_will_be_added_soon, com.google.android.material.snackbar.Snackbar.LENGTH_SHORT).setAction("ok", _view -> {}).show();}}});
    }

    private void initializeLogic() {
        artist.setVisibility(View.GONE);
        album.setVisibility(View.GONE);
        switch (sp.getString("theme", "")) {
            case "system": AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM); break;
            case "dark": AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES); break;
            case "light": AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO); break;}
        play = new Gson().fromJson(getIntent().getStringExtra("data"), new TypeToken < ArrayList < HashMap < String, Object >>> () {}.getType());
        if (getIntent().getStringExtra("title").equals(getString(R.string.featured_artists_1))) {
            listview1.setAdapter(new ArtistsAdapter(play)); } else {
            listview1.setAdapter(new Listview1Adapter(play));}
        ((BaseAdapter) listview1.getAdapter()).notifyDataSetChanged();
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        typeface(name);
        _marquee(name, getIntent().getStringExtra("title").concat(" (").concat(String.valueOf(play.size())).concat(")"));
        toolbar.setNavigationOnClickListener(_v -> onBackPressed());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.artist, menu);
        return true;}

    private void typeface(TextView _txt) { _txt.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/raleway.ttf"), Typeface.BOLD);}

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

    public void _name(String _str, int _position, ArrayList<HashMap<String, Object>> play, TextView Name) {
        if (sp.getString("explicit", "").equals("no") && play.get(_position).containsKey("not_explicit")) {
            _str = play.get(_position).get("not_explicit").toString();
        } else {
            _str = play.get(_position).get("name").toString();}
        if (play.get(_position).containsKey("prefix")) {
            _str = _str.concat(" ").concat(play.get(_position).get("prefix").toString());}
        if (play.get(_position).get("explicit").equals("yes")) {
            _str = _str.concat(" 🅴");}
        SpannableStringBuilder ssb = new SpannableStringBuilder(_str);
        int color = ContextCompat.getColor(FullActivity.this, R.color.text2);
        ForegroundColorSpan fcsRed = new ForegroundColorSpan(color);
        ssb.setSpan(fcsRed, play.get(_position).get("name").toString().length(), _str.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        _marque(Name, ssb);
    }

    public void _date(ArrayList<HashMap<String, Object>> play, int _position) {
        if (play.get(_position).get("month").equals("Jan")) {
            month = getString(R.string.Jan);
        } else if (play.get(_position).get("month").equals("Feb")) {
            month = getString(R.string.Feb);
        } else if (play.get(_position).get("month").equals("Mar")) {
            month = getString(R.string.Mar);
        } else if (play.get(_position).get("month").equals("Apr")) {
            month = getString(R.string.Apr);
        } else if (play.get(_position).get("month").equals("May")) {
            month = getString(R.string.May);
        } else if (play.get(_position).get("month").equals("Jun")) {
            month = getString(R.string.Jun);
        } else if (play.get(_position).get("month").equals("Jul")) {
            month = getString(R.string.Jul);
        } else if (play.get(_position).get("month").equals("Aug")) {
            month = getString(R.string.Aug);
        } else if (play.get(_position).get("month").equals("Sep")) {
            month = getString(R.string.Sep);
        } else if (play.get(_position).get("month").equals("Oct")) {
            month = getString(R.string.Oct);
        } else if (play.get(_position).get("month").equals("Nov")) {
            month = getString(R.string.Nov);
        } else if (play.get(_position).get("month").equals("Dec")) {
            month = getString(R.string.Dec);}
        if (sp.getString("prefix", "").equals("en")) {
            date = month.concat(" ").concat(play.get(_position).get("date").toString()).concat(", ").concat(play.get(_position).get("year").toString());
        } else if (sp.getString("prefix", "").equals("ru")) {
            date = play.get(_position).get("date").toString().concat(month).concat(" ").concat(play.get(_position).get("year").toString());
        } else if (sp.getString("prefix", "").equals("de")) {
            date = play.get(_position).get("date").toString().concat(".").concat(month).concat(" ").concat(play.get(_position).get("year").toString());}
    }

    public void _time(ArrayList<HashMap<String, Object>> play, int _position) {
        if (play.get(_position).get("sec_add").equals("1")) {
            time = play.get(_position).get("sec").toString().concat(getString(R.string.sec1));
        } else if (play.get(_position).get("sec_add").equals("2")) {
            time = play.get(_position).get("sec").toString().concat(getString(R.string.sec2));
        } else if (play.get(_position).get("sec_add").equals("3")) {
            time = play.get(_position).get("sec").toString().concat(getString(R.string.sec3));
        } else if(play.get(_position).get("sec_add").equals("4")) {
            time = play.get(_position).get("sec").toString().concat(getString(R.string.sec4));}
        if (play.get(_position).containsKey("min") && play.get(_position).containsKey("min_add")) {
            if (play.get(_position).get("min_add").equals("1")) {
                time = play.get(_position).get("min").toString().concat(getString(R.string.min1)).concat(" ").concat(time);
            } else if (play.get(_position).get("min_add").equals("2")) {
                time = play.get(_position).get("min").toString().concat(getString(R.string.min2)).concat(" ").concat(time);
            } else if (play.get(_position).get("min_add").equals("3")) {
                time = play.get(_position).get("min").toString().concat(getString(R.string.min3)).concat(" ").concat(time);
            } else if(play.get(_position).get("min_add").equals("4")) {
                time = play.get(_position).get("min").toString().concat(getString(R.string.min4)).concat(" ").concat(time);}}
        if (play.get(_position).containsKey("hour") && play.get(_position).containsKey("hour_add")) {
            if (play.get(_position).get("hour_add").equals("1")) {
                time = play.get(_position).get("hour").toString().concat(getString(R.string.hour1)).concat(" ").concat(time);
            } else if (play.get(_position).get("hour_add").equals("2")) {
                time = play.get(_position).get("hour").toString().concat(getString(R.string.hour2)).concat(" ").concat(time);
            } else if (play.get(_position).get("hour_add").equals("3")) {
                time = play.get(_position).get("hour").toString().concat(getString(R.string.hour3)).concat(" ").concat(time);
            } else if(play.get(_position).get("hour_add").equals("4")) {
                time = play.get(_position).get("hour").toString().concat(getString(R.string.hour4)).concat(" ").concat(time);}}
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
                _view = _inflater.inflate(R.layout.playlists, null); }
            final LinearLayout linear1 = _view.findViewById(R.id.linear1);
            final ImageView imageview1 = _view.findViewById(R.id.imageview1);
            final TextView Name = _view.findViewById(R.id.Name);
            final TextView album = _view.findViewById(R.id.album);
            final TextView Release = _view.findViewById(R.id.Release);
            final ImageView more = _view.findViewById(R.id.more);
            if (!play.get(_position).containsKey("link")) {
                Release.setAlpha((float) 0.5d);
                album.setAlpha((float) 0.5d);
                imageview1.setAlpha((float) 0.5d);
                Name.setAlpha((float) 0.5d);
                more.setAlpha((float) 0.5d);}
                _name(playstr, _position, play, Name);
                _date(play, _position);
                _time(play, _position);
                if (play.get(_position).containsKey("image")) {
                    Glide.with(getApplicationContext()).load(Uri.parse(getString(R.string.site).concat(play.get(_position).get("image").toString()))).into(imageview1);}
                if (play.get(_position).get("artist").toString().equals(getIntent().getStringExtra("artist"))) {
                    _marquee(album, date);
                    _marquee(Release, time);
                } else {
                    _marquee(album, play.get(_position).get("artist").toString());
                    _marquee(Release, date.concat(" • ").concat(time));}
                typeface(Name);
                typeface(album);
                typeface(Release);
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
            if (play.get(_position).containsKey("link")) {
                textview3.setAlpha((float) 1);
                textview2.setAlpha((float) 1);
                imageview2.setAlpha((float) 1);
            } else {
                textview3.setAlpha((float) 0.5d);
                textview2.setAlpha((float) 0.5d);
                imageview2.setAlpha((float) 0.5d);}
            if (play.get(_position).containsKey("image")) {
                Glide.with(getApplicationContext()).load(Uri.parse(getString(R.string.site).concat(play.get(_position).get("image").toString()))).into(imageview2);
            } else {
                imageview2.setImageResource(R.drawable.ic_timer_auto);}
            _marquee(textview2, play.get(_position).get("name").toString());
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