package ru.kartofan.theme.music.app;

import static androidx.biometric.BiometricManager.Authenticators.BIOMETRIC_STRONG;
import static androidx.biometric.BiometricManager.Authenticators.BIOMETRIC_WEAK;
import static androidx.biometric.BiometricManager.Authenticators.DEVICE_CREDENTIAL;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;
import androidx.biometric.BiometricPrompt;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.content.ContextCompat;
import com.google.android.material.appbar.AppBarLayout;
import android.app.Activity;
import android.graphics.Typeface;
import android.os.*;
import android.text.TextUtils;
import android.view.*;
import android.widget.*;
import android.content.*;
import android.util.*;
import java.util.*;
import java.util.concurrent.Executor;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Switch;

public class SettingsActivity extends AppCompatActivity {
    private Executor executor;
    private BiometricPrompt biometricPrompt;
    private BiometricPrompt.PromptInfo promptInfo;
    private Toolbar _toolbar;
    private AppBarLayout _app_bar;
    private CoordinatorLayout _coordinator;
    private LinearLayout settings_fragment, language_linear, segment_1, theme_linear, explicit_linear, segment_2, segment_3, linear2, linear3, explicit_linear_2, quality_linear, display_linear, display_linear_2;
    private TextView language_settings, theme_settings, restrictions_settings, video_settings, textview1, language_hint, language_choice, theme_hint, theme_choice, explicit_hint, explicit_choice, textview2, textview3, display_settings, display_hint, display_choice, quality_hint, quality_choice;
    private Switch display_switch, explicit_switch;
    private SharedPreferences sp;

    @Override
    protected void onCreate(Bundle _savedInstanceState) {
        super.onCreate(_savedInstanceState);
        setContentView(R.layout.settings);
        executor = ContextCompat.getMainExecutor(this);
        biometricPrompt = new BiometricPrompt(SettingsActivity.this, executor, new BiometricPrompt.AuthenticationCallback() {
            @Override
            public void onAuthenticationError(int errorCode, @NonNull CharSequence errString) {
                super.onAuthenticationError(errorCode, errString);
                explicit_switch.setChecked(false);
                explicit_choice.setText(getString(R.string.explicit_off));
            }

            @Override
            public void onAuthenticationSucceeded(@NonNull BiometricPrompt.AuthenticationResult result) {
                super.onAuthenticationSucceeded(result);
                sp.edit().putString("explicit", "yes").apply();
            }

            @Override
            public void onAuthenticationFailed() {
                super.onAuthenticationFailed();
                explicit_switch.setChecked(false);
                explicit_choice.setText(getString(R.string.explicit_off));
            }
        });

        promptInfo = new BiometricPrompt.PromptInfo.Builder()
                .setTitle(getString(R.string.confirm_short))
                .setSubtitle(getString(R.string.confirm_long))
                .setAllowedAuthenticators(BIOMETRIC_STRONG | BIOMETRIC_WEAK | DEVICE_CREDENTIAL)
                .build();
        initialize();
        com.google.firebase.FirebaseApp.initializeApp(this);
        initializeLogic();
    }

    private void initialize() {
        _app_bar = findViewById(R.id._app_bar);
        _coordinator = findViewById(R.id._coordinator);
        _toolbar = findViewById(R.id._toolbar);
        setSupportActionBar(_toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        sp = getSharedPreferences("sp", Activity.MODE_PRIVATE);
        _toolbar.setNavigationOnClickListener(_v -> onBackPressed());
        settings_fragment = findViewById(R.id.settings_fragment);
        language_settings = findViewById(R.id.language_settings);
        language_linear = findViewById(R.id.language_linear);
        segment_1 = findViewById(R.id.segment_1);
        theme_settings = findViewById(R.id.theme_settings);
        theme_linear = findViewById(R.id.theme_linear);
        segment_2 = findViewById(R.id.segment_2);
        restrictions_settings = findViewById(R.id.restrictions_settings);
        explicit_linear = findViewById(R.id.explicit_linear);
        segment_3 = findViewById(R.id.segment_3);
        linear2 = findViewById(R.id.linear2);
        textview1 = findViewById(R.id.textview1);
        linear3 = findViewById(R.id.linear3);
        language_hint = findViewById(R.id.language_hint);
        language_choice = findViewById(R.id.language_choice);
        theme_hint = findViewById(R.id.theme_hint);
        theme_choice = findViewById(R.id.theme_choice);
        explicit_linear_2 = findViewById(R.id.explicit_linear_2);
        explicit_switch = findViewById(R.id.explicit_switch);
        explicit_hint = findViewById(R.id.explicit_hint);
        explicit_choice = findViewById(R.id.explicit_choice);
        textview2 = findViewById(R.id.textview2);
        textview3 = findViewById(R.id.textview3);
        display_linear = findViewById(R.id.display_linear);
        display_linear_2 = findViewById(R.id.display_linear_2);
        display_settings = findViewById(R.id.display_settings);
        display_hint = findViewById(R.id.display_hint);
        display_choice = findViewById(R.id.display_choice);
        display_switch = findViewById(R.id.display_switch);
        quality_linear = findViewById(R.id.quality_linear);
        quality_hint = findViewById(R.id.quality_hint);
        quality_choice = findViewById(R.id.quality_choice);

        explicit_switch.setOnClickListener(v -> {
                if (sp.getString("explicit", "").equals("no")) {
                    biometricPrompt.authenticate(promptInfo);
                    explicit_switch.setChecked(true);
                    explicit_choice.setText(getString(R.string.explicit_on));
                } else {
                    sp.edit().putString("explicit", "no").apply();
                    explicit_switch.setChecked(false);
                    explicit_choice.setText(getString(R.string.explicit_off));
                }
        });

        explicit_choice.setOnClickListener(v -> {
                if (sp.getString("explicit", "").equals("no")) {
                    biometricPrompt.authenticate(promptInfo);
                    explicit_switch.setChecked(true);
                    explicit_choice.setText(getString(R.string.explicit_on));
                } else {
                    sp.edit().putString("explicit", "no").apply();
                    explicit_switch.setChecked(false);
                    explicit_choice.setText(getString(R.string.explicit_off));
                }
        });

        explicit_hint.setOnClickListener(v -> {
                if (sp.getString("explicit", "").equals("no")) {
                    biometricPrompt.authenticate(promptInfo);
                    explicit_switch.setChecked(true);
                    _marquee(explicit_choice, getString(R.string.explicit_on));
                } else {
                    sp.edit().putString("explicit", "no").apply();
                    explicit_switch.setChecked(false);
                    _marquee(explicit_choice, getString(R.string.explicit_off));
                }
        });

        display_switch.setOnCheckedChangeListener((_param1, _param2) -> {
            if (_param2) {
                    sp.edit().putString("animation", "yes").apply();
                    display_switch.setChecked(true);
                } else {
                    sp.edit().putString("animation", "no").apply();
                    display_switch.setChecked(false);
                }
        });

        display_choice.setOnClickListener(v -> {
                if (sp.getString("animation", "").equals("no")) {
                    sp.edit().putString("animation", "yes").apply();
                    display_switch.setChecked(true);
                } else {
                    sp.edit().putString("animation", "no").apply();
                    display_switch.setChecked(false);
                }
        });

        display_hint.setOnClickListener(v -> {
            if (sp.getString("animation", "").equals("no")) {
                sp.edit().putString("animation", "yes").apply();
                display_switch.setChecked(true);
            } else {
                sp.edit().putString("animation", "no").apply();
                display_switch.setChecked(false);
            }
        });

        quality_choice.setOnClickListener(v -> _quality());

        quality_hint.setOnClickListener(v -> _quality());

        theme_choice.setOnClickListener(v -> _theme());

        theme_hint.setOnClickListener(v -> _theme());
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
        explicit_hint.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/moscow.ttf"), Typeface.NORMAL);
        display_hint.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/moscow.ttf"), Typeface.NORMAL);
        textview2.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/moscow.ttf"), Typeface.NORMAL);
        language_choice.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/moscow.ttf"), Typeface.NORMAL);
        theme_choice.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/moscow.ttf"), Typeface.NORMAL);
        explicit_choice.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/moscow.ttf"), Typeface.NORMAL);
        textview3.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/moscow.ttf"), Typeface.NORMAL);
        _marquee(display_choice, getString(R.string.animated_art_hint));
        display_switch.setChecked(!sp.getString("animation", "").equals("no"));
        if (sp.getString("explicit", "").equals("no")) {
            explicit_switch.setChecked(false);
            _marquee(explicit_choice, getString(R.string.explicit_off));
        } else {
            explicit_switch.setChecked(true);
            _marquee(explicit_choice, getString(R.string.explicit_on));
        }
        if (sp.getString("quality", "").equals("yes")) {
            quality_choice.setText(getString(R.string.enabled));
       } else {
            quality_choice.setText(getString(R.string.disabled));
        }
        if (sp.getString("theme", "").equals("battery")) {
            theme_choice.setText(getString(R.string.battery));
        } else if (sp.getString("theme", "").equals("light")) {
            theme_choice.setText(getString(R.string.light));
        } else if (sp.getString("theme", "").equals("dark")) {
            theme_choice.setText(getString(R.string.dark));
        } else {
            theme_choice.setText(getString(R.string.system_default));
        }
    }

    public void _marquee(final TextView _textview, final String _text) {
        _textview.setText(_text);
        _textview.setSingleLine(true);
        _textview.setEllipsize(TextUtils.TruncateAt.MARQUEE);
        _textview.setSelected(true);
    }

    public void _theme(){
        final com.google.android.material.bottomsheet.BottomSheetDialog bs_base = new com.google.android.material.bottomsheet.BottomSheetDialog(SettingsActivity.this);
        bs_base.setCancelable(true);
        View layBase = getLayoutInflater().inflate(R.layout.theme, null);
        bs_base.setContentView(layBase);
        TextView theme = layBase.findViewById(R.id.theme);
        Button system = layBase.findViewById(R.id.system);
        Button battery = layBase.findViewById(R.id.battery);
        Button light = layBase.findViewById(R.id.light);
        Button dark = layBase.findViewById(R.id.dark);
        theme.setText(getString(R.string.theme));
        theme.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/moscow.ttf"), Typeface.NORMAL);
        system.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/moscow.ttf"), Typeface.NORMAL);
        battery.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/moscow.ttf"), Typeface.NORMAL);
        light.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/moscow.ttf"), Typeface.NORMAL);
        dark.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/moscow.ttf"), Typeface.NORMAL);
        system.setOnClickListener(v -> {
            theme_choice.setText(getString(R.string.system_default));
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
            sp.edit().putString("theme", "system").apply();
            bs_base.cancel();
        });

        battery.setOnClickListener(v -> {
            theme_choice.setText(getString(R.string.battery));
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_AUTO_BATTERY);
            sp.edit().putString("theme", "battery").apply();
            bs_base.cancel();
        });

        light.setOnClickListener(v -> {
            theme_choice.setText(getString(R.string.light));
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            sp.edit().putString("theme", "light").apply();
            bs_base.cancel();
        });

        dark.setOnClickListener(v -> {
            theme_choice.setText(getString(R.string.dark));
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            sp.edit().putString("theme", "dark").apply();
            bs_base.cancel();
        });
        bs_base.show();
    }

    public void _quality(){
        final com.google.android.material.bottomsheet.BottomSheetDialog bs_base = new com.google.android.material.bottomsheet.BottomSheetDialog(SettingsActivity.this);
        bs_base.setCancelable(true);
        View layBase = getLayoutInflater().inflate(R.layout.quality, null);
        bs_base.setContentView(layBase);
        TextView quality = layBase.findViewById(R.id.quality);
        Button mobile = layBase.findViewById(R.id.mobile);
        Button never = layBase.findViewById(R.id.never);
        quality.setText(getString(R.string.high_quality));
        quality.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/moscow.ttf"), Typeface.NORMAL);
        mobile.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/moscow.ttf"), Typeface.NORMAL);
        never.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/moscow.ttf"), Typeface.NORMAL);
        mobile.setOnClickListener(v -> {
            quality_choice.setText(getString(R.string.enabled));
            sp.edit().putString("quality", "yes").apply();
            bs_base.cancel();
        });
        never.setOnClickListener(v -> {
            quality_choice.setText(getString(R.string.disabled));
            sp.edit().putString("quality", "no").apply();
            bs_base.cancel();
        });
        bs_base.show();
    }

    @Override
    protected void onActivityResult(int _requestCode, int _resultCode, Intent _data) {
        super.onActivityResult(_requestCode, _resultCode, _data);
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
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                _input, getResources().getDisplayMetrics());
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