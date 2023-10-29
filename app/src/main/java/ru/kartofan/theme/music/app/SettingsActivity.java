package ru.kartofan.theme.music.app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import com.google.android.material.appbar.AppBarLayout;
import android.app.Activity;
import android.graphics.Typeface;
import android.os.*;
import android.view.*;
import android.widget.*;
import android.content.*;
import android.util.*;
import java.util.*;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Switch;

public class SettingsActivity extends AppCompatActivity {
    private Toolbar _toolbar;
    private AppBarLayout _app_bar;
    private CoordinatorLayout _coordinator;
    private LinearLayout settings_fragment;
    private TextView language_settings;
    private LinearLayout language_linear;
    private LinearLayout segment_1;
    private TextView theme_settings;
    private LinearLayout theme_linear;
    private LinearLayout segment_2;
    private TextView restrictions_settings;
    private Switch restrictions_hint;
    private LinearLayout explicit_linear;
    private LinearLayout segment_3;
    private TextView video_settings;
    private Switch display_switch;
    private LinearLayout linear2;
    private TextView textview1;
    private LinearLayout linear3;
    private TextView language_hint;
    private TextView language_choice;
    private TextView theme_hint;
    private TextView theme_choice;
    private LinearLayout explicit_linear_2;
    private Switch explicit_switch;
    private TextView explicit_hint;
    private TextView explicit_choice;
    private TextView textview2;
    private TextView textview3;
    private TextView display_settings;
    private TextView display_hint;
    private TextView display_choice;
    private LinearLayout quality_linear;
    private TextView quality_hint;
    private TextView quality_choice;
    private LinearLayout display_linear;
    private LinearLayout display_linear_2;
    private SharedPreferences sp;
    @Override
    protected void onCreate(Bundle _savedInstanceState) {
        super.onCreate(_savedInstanceState);
        setContentView(R.layout.settings);
        initialize(_savedInstanceState);
        com.google.firebase.FirebaseApp.initializeApp(this);
        initializeLogic();
    }

    private void initialize(Bundle _savedInstanceState) {
        _app_bar = (AppBarLayout) findViewById(R.id._app_bar);
        _coordinator = (CoordinatorLayout) findViewById(R.id._coordinator);
        _toolbar = (Toolbar) findViewById(R.id._toolbar);
        setSupportActionBar(_toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        sp = getSharedPreferences("sp", Activity.MODE_PRIVATE);
        _toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View _v) {
                onBackPressed();
            }
        });
        settings_fragment = (LinearLayout) findViewById(R.id.settings_fragment);
        language_settings = (TextView) findViewById(R.id.language_settings);
        language_linear = (LinearLayout) findViewById(R.id.language_linear);
        segment_1 = (LinearLayout) findViewById(R.id.segment_1);
        theme_settings = (TextView) findViewById(R.id.theme_settings);
        theme_linear = (LinearLayout) findViewById(R.id.theme_linear);
        segment_2 = (LinearLayout) findViewById(R.id.segment_2);
        restrictions_settings = (TextView) findViewById(R.id.restrictions_settings);
        restrictions_hint = (Switch) findViewById(R.id.restrictions_hint);
        explicit_linear = (LinearLayout) findViewById(R.id.explicit_linear);
        segment_3 = (LinearLayout) findViewById(R.id.segment_3);
        linear2 = (LinearLayout) findViewById(R.id.linear2);
        textview1 = (TextView) findViewById(R.id.textview1);
        linear3 = (LinearLayout) findViewById(R.id.linear3);
        language_hint = (TextView) findViewById(R.id.language_hint);
        language_choice = (TextView) findViewById(R.id.language_choice);
        theme_hint = (TextView) findViewById(R.id.theme_hint);
        theme_choice = (TextView) findViewById(R.id.theme_choice);
        explicit_linear_2 = (LinearLayout) findViewById(R.id.explicit_linear_2);
        explicit_switch = (Switch) findViewById(R.id.explicit_switch);
        explicit_hint = (TextView) findViewById(R.id.explicit_hint);
        explicit_choice = (TextView) findViewById(R.id.explicit_choice);
        textview2 = (TextView) findViewById(R.id.textview2);
        textview3 = (TextView) findViewById(R.id.textview3);
        display_linear = (LinearLayout) findViewById(R.id.display_linear);
        display_linear_2 = (LinearLayout) findViewById(R.id.display_linear_2);
        display_settings = (TextView) findViewById(R.id.display_settings);
        display_hint = (TextView) findViewById(R.id.display_hint);
        display_choice = (TextView) findViewById(R.id.display_choice);
        display_switch = (Switch) findViewById(R.id.display_switch);
        quality_linear = (LinearLayout) findViewById(R.id.quality_linear);
        quality_hint = (TextView) findViewById(R.id.quality_hint);
        quality_choice = (TextView) findViewById(R.id.quality_choice);

        restrictions_hint.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton _param1, boolean _param2) {
                final boolean _isChecked = _param2;
                if (_isChecked) {
                    sp.edit().putString("restrictions", "yes").commit();
                    restrictions_hint.setChecked(true);
                    explicit_choice.setAlpha(1);
                    explicit_switch.setAlpha(1);
                    explicit_hint.setAlpha(1);
                } else {
                    sp.edit().putString("restrictions", "no").commit();
                    restrictions_hint.setChecked(false);
                    explicit_choice.setAlpha((float) 0.5d);
                    explicit_switch.setAlpha((float) 0.5 );
                    explicit_hint.setAlpha((float) 0.5d);
                    explicit_switch.setChecked(false);
                }
            }
        });

        explicit_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton _param1, boolean _param2) {
                final boolean _isChecked = _param2;
                if (!sp.getString("restrictions", "").equals("no")) {
                    if (_isChecked) {
                        sp.edit().putString("explicit", "yes").commit();
                        explicit_switch.setChecked(true);
                        explicit_choice.setText(getString(R.string.explicit_on));
                    } else {
                        sp.edit().putString("explicit", "no").commit();
                        explicit_switch.setChecked(false);
                        explicit_choice.setText(getString(R.string.explicit_off));
                    }
                } else {
                    sp.edit().putString("explicit", "no").commit();
                    explicit_switch.setChecked(false);
                    explicit_choice.setText(getString(R.string.explicit_off));
                }
            }
        });

        explicit_choice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!sp.getString("restrictions", "").equals("no")) {
                    if (sp.getString("explicit", "").equals("no")) {
                        sp.edit().putString("explicit", "yes").commit();
                        explicit_switch.setChecked(true);
                        explicit_choice.setText(getString(R.string.explicit_on));
                    } else {
                        sp.edit().putString("explicit", "no").commit();
                        explicit_switch.setChecked(false);
                        explicit_choice.setText(getString(R.string.explicit_off));
                    }
                } else {
                    sp.edit().putString("explicit", "no").commit();
                    explicit_switch.setChecked(false);
                    explicit_choice.setText(getString(R.string.explicit_off));
                }
            }
        });

        explicit_hint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                explicit_choice.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (!sp.getString("restrictions", "").equals("no")) {
                            if (sp.getString("explicit", "").equals("no")) {
                                sp.edit().putString("explicit", "yes").commit();
                                explicit_switch.setChecked(true);
                                explicit_choice.setText(getString(R.string.explicit_on));
                            } else {
                                sp.edit().putString("explicit", "no").commit();
                                explicit_switch.setChecked(false);
                                explicit_choice.setText(getString(R.string.explicit_off));
                            }
                        } else {
                            sp.edit().putString("explicit", "no").commit();
                            explicit_switch.setChecked(false);
                            explicit_choice.setText(getString(R.string.explicit_off));
                        }
                    }
                });
            }
        });

        display_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton _param1, boolean _param2) {
                final boolean _isChecked = _param2;
                if (_isChecked) {
                    sp.edit().putString("video", "yes").commit();
                    display_switch.setChecked(true);
                } else {
                    sp.edit().putString("video", "no").commit();
                    display_switch.setChecked(false);
                }
            }
        });
    }

    private void initializeLogic() {
        language_settings.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/moscow.ttf"), Typeface.NORMAL);
        theme_settings.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/moscow.ttf"), Typeface.NORMAL);
        restrictions_settings.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/moscow.ttf"), Typeface.NORMAL);
        display_settings.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/moscow.ttf"), Typeface.NORMAL);
        display_choice.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/moscow.ttf"), Typeface.NORMAL);
        textview1.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/moscow.ttf"), Typeface.NORMAL);
        quality_choice.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/moscow.ttf"), Typeface.NORMAL);
        quality_hint.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/moscow.ttf"), Typeface.NORMAL);
        language_hint.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/moscow.ttf"), Typeface.NORMAL);
        theme_hint.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/moscow.ttf"), Typeface.NORMAL);
        restrictions_hint.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/moscow.ttf"), Typeface.NORMAL);
        explicit_hint.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/moscow.ttf"), Typeface.NORMAL);
        display_hint.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/moscow.ttf"), Typeface.NORMAL);
        textview2.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/moscow.ttf"), Typeface.NORMAL);
        language_choice.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/moscow.ttf"), Typeface.NORMAL);
        theme_choice.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/moscow.ttf"), Typeface.NORMAL);
        explicit_choice.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/moscow.ttf"), Typeface.NORMAL);
        textview3.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/moscow.ttf"), Typeface.NORMAL);
        if (sp.getString("restrictions", "").equals("no")) {
            restrictions_hint.setChecked(false);
            explicit_choice.setAlpha((float) 0.5d);
            explicit_switch.setAlpha((float) 0.5d);
            explicit_hint.setAlpha((float) 0.5d);
        } else {
            restrictions_hint.setChecked(true);
            explicit_choice.setAlpha(1);
            explicit_switch.setAlpha(1);
            explicit_hint.setAlpha(1);
            if (sp.getString("explicit", "").equals("no")) {
                explicit_switch.setChecked(false);
                explicit_choice.setText(getString(R.string.explicit_off));
            } else {
                explicit_switch.setChecked(true);
                explicit_choice.setText(getString(R.string.explicit_on));
            }
        }
        if (sp.getString("video", "").equals("no")) {
            display_switch.setChecked(false);
        } else {
            display_switch.setChecked(true);
        }
    }

    @Override
    protected void onActivityResult(int _requestCode, int _resultCode, Intent _data) {
        super.onActivityResult(_requestCode, _resultCode, _data);
        switch (_requestCode) {
            default:
                break;
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