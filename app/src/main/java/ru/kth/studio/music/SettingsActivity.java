package ru.kth.studio.music;

import static androidx.biometric.BiometricManager.Authenticators.BIOMETRIC_STRONG;
import static androidx.biometric.BiometricManager.Authenticators.BIOMETRIC_WEAK;
import static androidx.biometric.BiometricManager.Authenticators.DEVICE_CREDENTIAL;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.SwitchCompat;
import androidx.biometric.BiometricPrompt;
import androidx.core.content.ContextCompat;
import com.google.android.material.snackbar.Snackbar;
import android.app.Activity;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.*;
import android.view.*;
import android.widget.*;
import android.content.*;
import java.util.Locale;
import java.util.concurrent.Executor;
import android.widget.TextView;

public class SettingsActivity extends AppCompatActivity {
    private Button privacy, agreement;
    private ImageView back, more;
    private LinearLayout test_features;
    private Executor executor;
    private BiometricPrompt biometricPrompt;
    private BiometricPrompt.PromptInfo promptInfo;
    private String lang, pref;
    private TextView additional, additional2, beta, name, artist, album, language, language_text, language_text1, theme, theme_text, theme_text1, restrictions, display, textview1, version, version1, build, build1, release, release1, copyright;
    private SwitchCompat test_colors, test_quality, explicit_switch, name_switch, display_switch, quality_switch;
    private SharedPreferences sp;
    private final Intent settings = new Intent();

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
                switch (errorCode) {
                    case 14: com.google.android.material.snackbar.Snackbar.make(name, R.string.no_pin, Snackbar.LENGTH_INDEFINITE).setAction("Ok", _view -> {}).show(); break;
                    case 10: com.google.android.material.snackbar.Snackbar.make(name, R.string.canceled, Snackbar.LENGTH_INDEFINITE).setAction("Ok", _view -> {}).show(); break;
                    default: com.google.android.material.snackbar.Snackbar.make(name, errString, Snackbar.LENGTH_INDEFINITE).setAction("Ok", _view -> {}).show(); break;}}

            @Override
            public void onAuthenticationSucceeded(@NonNull BiometricPrompt.AuthenticationResult result) {
                super.onAuthenticationSucceeded(result);
                sp.edit().putString("explicit", "yes").apply();}});

        promptInfo = new BiometricPrompt.PromptInfo.Builder().setTitle(getString(R.string.confirm_short)).setSubtitle(getString(R.string.confirm_long)).setAllowedAuthenticators(BIOMETRIC_STRONG | BIOMETRIC_WEAK | DEVICE_CREDENTIAL).build();
        initialize();
        com.google.firebase.FirebaseApp.initializeApp(this);
        initializeLogic();}

    private void initialize() {
        privacy = findViewById(R.id.privacy);
        agreement = findViewById(R.id.agreement);
        back = findViewById(R.id.back);
        more = findViewById(R.id.more);
        additional = findViewById(R.id.additional);
        additional2 = findViewById(R.id.additional2);
        name = findViewById(R.id.name);
        artist = findViewById(R.id.artist);
        album = findViewById(R.id.album);
        beta = findViewById(R.id.beta);
        test_colors = findViewById(R.id.test_colors);
        test_quality = findViewById(R.id.test_quality);
        test_features = findViewById(R.id.test_features);
        sp = getSharedPreferences("sp", Activity.MODE_PRIVATE);
        explicit_switch = findViewById(R.id.explicit_switch);
        name_switch = findViewById(R.id.name_switch);
        display_switch = findViewById(R.id.display_switch);
        quality_switch = findViewById(R.id.quality_switch);
        language = findViewById(R.id.language);
        language_text = findViewById(R.id.language_text);
        language_text1 = findViewById(R.id.language_text1);
        theme = findViewById(R.id.theme);
        theme_text = findViewById(R.id.theme_text);
        theme_text1 = findViewById(R.id.theme_text1);
        restrictions = findViewById(R.id.restrictions);
        display = findViewById(R.id.display);
        textview1 = findViewById(R.id.textview1);
        version = findViewById(R.id.version);
        version1 = findViewById(R.id.version1);
        build = findViewById(R.id.build);
        build1 = findViewById(R.id.build1);
        release = findViewById(R.id.release);
        release1 = findViewById(R.id.release1);
        copyright = findViewById(R.id.copyright);
        _check("explicit", explicit_switch);
        _check("exp_song", name_switch);
        _check("animation", display_switch);
        _check("quality", quality_switch);

        privacy.setOnClickListener(v -> {
            settings.setAction(Intent.ACTION_VIEW);
            settings.setData(Uri.parse("https://kth-studio.github.io/".concat(pref.toString()).concat("/privacy.html")));
            startActivity(settings);});
        agreement.setOnClickListener(v -> {
            settings.setAction(Intent.ACTION_VIEW);
            settings.setData(Uri.parse("https://kth-studio.github.io/".concat(pref.toString()).concat("/agreement.html")));
            startActivity(settings);});

        back.setOnClickListener(v -> onBackPressed());
        explicit_switch.setOnClickListener(v -> {
            switch (sp.getString("explicit", "")) {
                case "no": biometricPrompt.authenticate(promptInfo); break;
                case "yes": sp.edit().putString("explicit", "no").apply(); break;}});
        name_switch.setOnCheckedChangeListener((_param1, _param2) -> _click("exp_song", _param2));
        display_switch.setOnCheckedChangeListener((_param1, _param2) -> _click("animation", _param2));
        quality_switch.setOnCheckedChangeListener((_param1, _param2) -> _click("quality", _param2));
        theme_text1.setOnClickListener(v -> _theme());
        theme_text.setOnClickListener(v -> _theme());}

    private void initializeLogic() {
        more.setVisibility(View.INVISIBLE);
        name_switch.setVisibility(View.GONE);
        if (!sp.getString("test", "").equals("yes")) { test_features.setVisibility(View.GONE);}
        lang = Locale.getDefault().getDisplayLanguage();
        switch (lang) {
            case "Deutsch": language_text1.setText(R.string.de); pref = "en"; break;
            case "Русский": case "русский": language_text1.setText(R.string.ru); pref = "ru"; break;
            default: language_text1.setText(R.string.en); pref = "en"; break;}
        switch (sp.getString("theme", "")) {
            case "system": AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM); theme_text1.setText(R.string.system_default); break;
            case "dark": AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES); theme_text1.setText(R.string.dark); break;
            case "light": AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO); theme_text1.setText(R.string.light); break;}
        name.setText(R.string.settings);
        album.setVisibility(View.GONE);
        artist.setVisibility(View.GONE);
        typeface(privacy);
        typeface(agreement);
        typeface(additional);
        typeface(additional2);
        typeface(beta);
        typeface(test_colors);
        typeface(test_quality);
        typeface(name);
        typeface(language);
        typeface(language_text);
        typeface(language_text1);
        typeface(theme);
        typeface(theme_text);
        typeface(theme_text1);
        typeface(restrictions);
        typeface(display);
        typeface(textview1);
        typeface(version);
        typeface(version1);
        typeface(build);
        typeface(build1);
        typeface(release);
        typeface(release1);
        typeface(copyright);
        typeface(explicit_switch);
        typeface(name_switch);
        typeface(display_switch);
        typeface(quality_switch);}

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.artist, menu);
        return true;}

    private void typeface(TextView _txt) { _txt.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/raleway.ttf"), Typeface.BOLD);}

    public void _click(String _key, boolean _param2) {
        if (_param2) { sp.edit().putString(_key, "yes").apply();
        } else { sp.edit().putString(_key, "no").apply();}}

    public void _check(String _key, SwitchCompat _switch) {
        switch(sp.getString(_key, "")) {
            case "no": _switch.setChecked(false); break;
            case "yes": _switch.setChecked(true); break;}}

    public void _theme(){
        final com.google.android.material.bottomsheet.BottomSheetDialog bs_base = new com.google.android.material.bottomsheet.BottomSheetDialog(SettingsActivity.this);
        bs_base.setCancelable(true);
        View layBase = getLayoutInflater().inflate(R.layout.theme, null);
        bs_base.setContentView(layBase);
        TextView theme = layBase.findViewById(R.id.theme);
        Button system = layBase.findViewById(R.id.system);
        Button light = layBase.findViewById(R.id.light);
        Button dark = layBase.findViewById(R.id.dark);
        theme.setText(getString(R.string.theme));
        typeface(theme);
        typeface(system);
        typeface(light);
        typeface(dark);
        system.setOnClickListener(v -> {
            theme_text1.setText(getString(R.string.system_default));
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
            sp.edit().putString("theme", "system").apply();
            bs_base.cancel();});
        light.setOnClickListener(v -> {
            theme_text1.setText(getString(R.string.light));
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            sp.edit().putString("theme", "light").apply();
            bs_base.cancel();});
        dark.setOnClickListener(v -> {
            theme_text1.setText(getString(R.string.dark));
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            sp.edit().putString("theme", "dark").apply();
            bs_base.cancel();});
        bs_base.show();}}