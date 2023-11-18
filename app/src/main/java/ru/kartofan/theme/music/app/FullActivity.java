package ru.kartofan.theme.music.app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import com.google.android.material.appbar.AppBarLayout;
import android.os.*;
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

    private Toolbar _toolbar;
    private AppBarLayout _app_bar;
    private CoordinatorLayout _coordinator;
    private ArrayList < HashMap < String, Object >> map = new ArrayList < > ();
    private ArrayList < HashMap < String, Object >> play = new ArrayList < > ();
    private ListView listview1;
    private Intent i = new Intent();
    private SharedPreferences sp;
    @Override
    protected void onCreate(Bundle _savedInstanceState) {
        super.onCreate(_savedInstanceState);
        setContentView(R.layout.full);
        initialize(_savedInstanceState);
        com.google.firebase.FirebaseApp.initializeApp(this);
        initializeLogic();
    }

    private void initialize(Bundle _savedInstanceState) {
        _app_bar = (AppBarLayout) findViewById(R.id._app_bar);
        _coordinator = (CoordinatorLayout) findViewById(R.id._coordinator);
        _toolbar = (Toolbar) findViewById(R.id._toolbar);
        listview1 = (ListView) findViewById(R.id.listview1);
        sp = getSharedPreferences("sp", Activity.MODE_PRIVATE);

        listview1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView < ? > _param1, View _param2, int _param3, long _param4) {
                final int _position = _param3;
                if (play.get((int) _position).containsKey("link")) {
                    if (getIntent().getStringExtra("title").equals(getString(R.string.featured_artists_1))) {
                        i.setClass(getApplicationContext(), ArtistActivity.class);
                        i.putExtra("link", play.get((int) _position).get("link").toString());
                        startActivity(i);
                    } else {
                        i.setClass(getApplicationContext(), AlbumActivity.class);
                        i.putExtra("link", play.get((int) _position).get("link").toString());
                        startActivity(i);
                    }
                } else {
                    if (getIntent().getStringExtra("title").equals(getString(R.string.albums_1))) {
                        com.google.android.material.snackbar.Snackbar.make(listview1, R.string.album_will_be_added_soon, com.google.android.material.snackbar.Snackbar.LENGTH_SHORT).setAction("ok", new View.OnClickListener() {
                            @Override
                            public void onClick(View _view) {}
                        }).show();
                    } else if (getIntent().getStringExtra("title").equals(getString(R.string.latest_release_1))) {
                        com.google.android.material.snackbar.Snackbar.make(listview1, R.string.album_will_be_added_soon, com.google.android.material.snackbar.Snackbar.LENGTH_SHORT).setAction("ok", new View.OnClickListener() {
                            @Override
                            public void onClick(View _view) {}
                        }).show();
                    } else if (getIntent().getStringExtra("title").equals(getString(R.string.top_songs_1))) {
                        com.google.android.material.snackbar.Snackbar.make(listview1, R.string.song_will_be_added_soon, com.google.android.material.snackbar.Snackbar.LENGTH_SHORT).setAction("ok", new View.OnClickListener() {
                            @Override
                            public void onClick(View _view) {}
                        }).show();
                    } else if (getIntent().getStringExtra("title").equals(getString(R.string.singles_1))) {
                        com.google.android.material.snackbar.Snackbar.make(listview1, R.string.song_will_be_added_soon, com.google.android.material.snackbar.Snackbar.LENGTH_SHORT).setAction("ok", new View.OnClickListener() {
                            @Override
                            public void onClick(View _view) {}
                        }).show();
                    } else if (getIntent().getStringExtra("title").equals(getString(R.string.appears_on_1))) {
                        com.google.android.material.snackbar.Snackbar.make(listview1, R.string.album_will_be_added_soon, com.google.android.material.snackbar.Snackbar.LENGTH_SHORT).setAction("ok", new View.OnClickListener() {
                            @Override
                            public void onClick(View _view) {}
                        }).show();
                    } else if (getIntent().getStringExtra("title").equals(getString(R.string.compilations_1))) {
                        com.google.android.material.snackbar.Snackbar.make(listview1, R.string.album_will_be_added_soon, com.google.android.material.snackbar.Snackbar.LENGTH_SHORT).setAction("ok", new View.OnClickListener() {
                            @Override
                            public void onClick(View _view) {}
                        }).show();
                    } else if (getIntent().getStringExtra("title").equals(getString(R.string.live_1))) {
                        com.google.android.material.snackbar.Snackbar.make(listview1, R.string.album_will_be_added_soon, com.google.android.material.snackbar.Snackbar.LENGTH_SHORT).setAction("ok", new View.OnClickListener() {
                            @Override
                            public void onClick(View _view) {}
                        }).show();
                    } else if (getIntent().getStringExtra("title").equals(getString(R.string.other_versions_1))) {
                        com.google.android.material.snackbar.Snackbar.make(listview1, R.string.album_will_be_added_soon, com.google.android.material.snackbar.Snackbar.LENGTH_SHORT).setAction("ok", new View.OnClickListener() {
                            @Override
                            public void onClick(View _view) {}
                        }).show();
                    } else if (getIntent().getStringExtra("title").equals(getString(R.string.featured_artists_1))) {
                        com.google.android.material.snackbar.Snackbar.make(listview1, R.string.artist_will_be_added_soon, com.google.android.material.snackbar.Snackbar.LENGTH_SHORT).setAction("ok", new View.OnClickListener() {
                            @Override
                            public void onClick(View _view) {}
                        }).show();
                    } else {
                        com.google.android.material.snackbar.Snackbar.make(listview1, R.string.album_will_be_added_soon, com.google.android.material.snackbar.Snackbar.LENGTH_SHORT).setAction("ok", new View.OnClickListener() {
                            @Override
                            public void onClick(View _view) {}
                        }).show();
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
        play = new Gson().fromJson(getIntent().getStringExtra("data"), new TypeToken < ArrayList < HashMap < String, Object >>> () {}.getType());
        if (getIntent().getStringExtra("title").equals(getString(R.string.featured_artists_1))) {
            listview1.setAdapter(new ArtistsAdapter(play));
        } else {
            listview1.setAdapter(new Listview1Adapter(play));
        }
        ((BaseAdapter) listview1.getAdapter()).notifyDataSetChanged();
        setSupportActionBar(_toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        setTitle(getIntent().getStringExtra("title").concat(" (").concat(String.valueOf(play.size())).concat(")"));
        _toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View _v) {
                onBackPressed();
            }
        });
    }

    @Override
    protected void onActivityResult(int _requestCode, int _resultCode, Intent _data) {
        super.onActivityResult(_requestCode, _resultCode, _data);
        switch (_requestCode) {
            default:
                break;
        }
    }

    public void _marquee(final TextView _textview, final String _text) {
        _textview.setText(_text);
        _textview.setSingleLine(true);
        _textview.setEllipsize(TextUtils.TruncateAt.MARQUEE);
        _textview.setSelected(true);
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
                _view = _inflater.inflate(R.layout.playlists, null);
            }
            final LinearLayout linear1 = (LinearLayout) _view.findViewById(R.id.linear1);
            final ImageView imageview1 = (ImageView) _view.findViewById(R.id.imageview1);
            final ImageView explicit = (ImageView) _view.findViewById(R.id.explicit);
            final TextView Name = (TextView) _view.findViewById(R.id.Name);
            final TextView album = (TextView) _view.findViewById(R.id.album);
            final TextView Release = (TextView) _view.findViewById(R.id.Release);
            _marquee(Name, play.get((int) _position).get("name").toString());
            if (!play.get((int) _position).containsKey("link")) {
                explicit.setAlpha((float) 0.5d);
                Release.setAlpha((float) 0.5d);
                album.setAlpha((float) 0.5d);
                imageview1.setAlpha((float) 0.5d);
                Name.setAlpha((float) 0.5d);
            }
            if (play.get((int) _position).containsKey("image")) {
                Glide.with(getApplicationContext()).load(Uri.parse(play.get((int) _position).get("image").toString())).into(imageview1);
            }
            if (play.get((int) _position).get("artist").toString().equals(getIntent().getStringExtra("artist"))) {
                album.setVisibility(View.GONE);
            } else {
                album.setVisibility(View.VISIBLE);
                _marquee(album, play.get((int) _position).get("artist").toString());
            }
            if (play.get((int) _position).containsKey("release_".concat(sp.getString("prefix", ""))) && play.get((int) _position).containsKey("time_".concat(sp.getString("prefix", "")))) {
                _marquee(Release, play.get((int) _position).get("release_".concat(sp.getString("prefix", ""))).toString().concat(" • ".concat(play.get((int) _position).get("time_".concat(sp.getString("prefix", ""))).toString())));
            }
            if (play.get((int) _position).get("explicit").toString().equals("yes")) {
                explicit.setVisibility(View.VISIBLE);
            } else {
                explicit.setVisibility(View.GONE);
            }
            Name.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/moscow.ttf"), Typeface.NORMAL);
            album.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/moscow.ttf"), Typeface.NORMAL);
            Release.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/moscow.ttf"), Typeface.NORMAL);
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
            if (play.get((int) _position).containsKey("link")) {
                textview3.setAlpha((float) 1);
                textview2.setAlpha((float) 1);
                imageview2.setAlpha((float) 1);
            } else {
                textview3.setAlpha((float) 0.5d);
                textview2.setAlpha((float) 0.5d);
                imageview2.setAlpha((float) 0.5d);
            }
            if (play.get((int) _position).containsKey("image")) {
                Glide.with(getApplicationContext()).load(Uri.parse(play.get((int) _position).get("image").toString())).into(imageview2);
            }
            _marquee(textview2, play.get((int) _position).get("name").toString());
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
        int _location[] = new int[2];
        _v.getLocationInWindow(_location);
        return _location[0];
    }

    @Deprecated
    public int getLocationY(View _v) {
        int _location[] = new int[2];
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