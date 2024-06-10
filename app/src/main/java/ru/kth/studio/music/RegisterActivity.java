package ru.kth.studio.music;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import com.google.android.material.appbar.AppBarLayout;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.*;
import android.widget.*;
import android.util.*;
import java.util.*;
import java.util.HashMap;
import java.util.ArrayList;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.EditText;
import android.widget.Button;
import android.content.Intent;
import java.util.Timer;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.android.gms.tasks.OnCompleteListener;
import android.view.View;

public class RegisterActivity extends  AppCompatActivity  {
    private final Timer _timer = new Timer();
    private final FirebaseDatabase _firebase = FirebaseDatabase.getInstance();
    private double connect, n = 0;
    private String error, var, verify, random, phone, codeSent, code = "";
    private HashMap<String, Object> map = new HashMap<>();
    private ArrayList<HashMap<String, Object>> list = new ArrayList<>();
    private LinearLayout linear1, linear10, linear2, linear7, linear4, linear5, linear6, linear11;
    private TextView textview1, name, artist, album, textview3, number;
    private EditText edittext2, edittext3;
    private Button button1, button2;
    private final Intent i = new Intent();
    private final DatabaseReference user = _firebase.getReference("users");
    private ChildEventListener _user_child_listener;
    private FirebaseAuth fauth;
    private SharedPreferences sp;
    private OnCompleteListener<Void> fauth_updateEmailListener;
    private OnCompleteListener<Void> fauth_updatePasswordListener;
    private OnCompleteListener<Void> fauth_emailVerificationSentListener;
    private OnCompleteListener<Void> fauth_deleteUserListener;
    private OnCompleteListener<Void> fauth_updateProfileListener;
    private OnCompleteListener<AuthResult> fauth_phoneAuthListener;
    private OnCompleteListener<AuthResult> fauth_googleSignInListener;
    private OnCompleteListener<AuthResult> _fauth_create_user_listener;
    private OnCompleteListener<AuthResult> _fauth_sign_in_listener;
    private OnCompleteListener<Void> _fauth_reset_password_listener;

    @Override
    protected void onCreate(Bundle _savedInstanceState) {
        super.onCreate(_savedInstanceState);
        setContentView(R.layout.register);
        initialize();
        com.google.firebase.FirebaseApp.initializeApp(this);
        initializeLogic();
    }

    private void initialize() {
        linear1 = findViewById(R.id.linear1);
        linear10 = findViewById(R.id.linear10);
        linear2 = findViewById(R.id.linear2);
        textview1 = findViewById(R.id.textview1);
        linear7 = findViewById(R.id.linear7);
        linear4 = findViewById(R.id.linear4);
        textview3 = findViewById(R.id.textview3);
        edittext2 = findViewById(R.id.edittext2);
        linear5 = findViewById(R.id.linear5);
        button1 = findViewById(R.id.button1);
        linear6 = findViewById(R.id.linear6);
        edittext3 = findViewById(R.id.edittext3);
        button2 = findViewById(R.id.button2);
        name = findViewById(R.id.name);
        artist = findViewById(R.id.artist);
        number = findViewById(R.id.number);
        album = findViewById(R.id.album);
        fauth = FirebaseAuth.getInstance();
        sp = getSharedPreferences("sp", Activity.MODE_PRIVATE);

        button1.setOnClickListener(_view -> {
            android.net.ConnectivityManager connMgr = (android.net.ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            android.net.NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
            if (networkInfo != null && networkInfo.isConnected()) {
                if (edittext2.length() == 10) {
                    _sendVerificationCode();
                    linear10.setVisibility(View.GONE);
                    linear2.setVisibility(View.VISIBLE);
                } else {
                    Snackbar.make(linear1, R.string.phone_number_short, Snackbar.LENGTH_SHORT).setAction("Ok", _view13 -> {}).show();}
            } else {
                Snackbar.make(linear1, R.string.internet_lost, Snackbar.LENGTH_INDEFINITE).setAction("Ok", _view12 -> {}).show();}});

        button2.setOnClickListener(_view -> {
            if (edittext3.length() == 6) {
                _verifyCode();
            } else {
                Snackbar.make(linear1, R.string.verify_code_short, Snackbar.LENGTH_SHORT).setAction("Ok", _view1 -> {}).show();}});

        _user_child_listener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot _param1, String _param2) {
                GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
                final String _childKey = _param1.getKey();
                final HashMap<String, Object> _childValue = _param1.getValue(_ind);
                user.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot _dataSnapshot) {
                        list = new ArrayList<>();
                        try {
                            GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
                            for (DataSnapshot _data : _dataSnapshot.getChildren()) {
                                HashMap<String, Object> _map = _data.getValue(_ind);
                                list.add(_map);}}
                        catch (Exception _e) {
                            _e.printStackTrace();}}
                    @Override
                    public void onCancelled(DatabaseError _databaseError) {
                    }});
            }

            @Override
            public void onChildChanged(DataSnapshot _param1, String _param2) {
                GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
                final String _childKey = _param1.getKey();
                final HashMap<String, Object> _childValue = _param1.getValue(_ind);
                user.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot _dataSnapshot) {
                        list = new ArrayList<>();
                        try {
                            GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
                            for (DataSnapshot _data : _dataSnapshot.getChildren()) {
                                HashMap<String, Object> _map = _data.getValue(_ind);
                                list.add(_map);}}
                        catch (Exception _e) {
                            _e.printStackTrace();}}
                    @Override
                    public void onCancelled(DatabaseError _databaseError) {
                    }});
            }

            @Override
            public void onChildMoved(DataSnapshot _param1, String _param2) {
            }

            @Override
            public void onChildRemoved(DataSnapshot _param1) {
                GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
                final String _childKey = _param1.getKey();
                final HashMap<String, Object> _childValue = _param1.getValue(_ind);
            }

            @Override
            public void onCancelled(DatabaseError _param1) {
                final int _errorCode = _param1.getCode();
                final String _errorMessage = _param1.getMessage();
            }
        };
        user.addChildEventListener(_user_child_listener);

        fauth_phoneAuthListener = task -> {
            final boolean _success = task.isSuccessful();
            final String _errorMessage = task.getException() != null ? task.getException().getMessage() : "";};

        _fauth_create_user_listener = _param1 -> {
            final boolean _success = _param1.isSuccessful();
            final String _errorMessage = _param1.getException() != null ? _param1.getException().getMessage() : "";
            if (_success) {
                _user();
            } else {}};

        _fauth_sign_in_listener = _param1 -> {
            final boolean _success = _param1.isSuccessful();
            final String _errorMessage = _param1.getException() != null ? _param1.getException().getMessage() : "";
            n = 0;
            if (_success) {
                user.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot _dataSnapshot) {
                        list = new ArrayList<>();
                        try {
                            GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
                            for (DataSnapshot _data : _dataSnapshot.getChildren()) {
                                HashMap<String, Object> _map = _data.getValue(_ind);
                                list.add(_map);}}
                        catch (Exception _e) {
                            _e.printStackTrace();}
                        if (list.size() == 0) {
                            if (_success) {
                                _user();} else {}
                        } else {
                            for(int _repeat72 = 0; _repeat72 < list.size(); _repeat72++) {
                                if (list.get((int)n).containsKey(FirebaseAuth.getInstance().getCurrentUser().getUid())) {
                                } else {
                                    if (_success) {
                                        _user();}}
                                n = n + 1;
                            }}
                    }
                    @Override
                    public void onCancelled(DatabaseError _databaseError) {}});
                i.setClass(getApplicationContext(), MainActivity.class);
                startActivity(i);
                finishAffinity();
            }};
    }

    private void initializeLogic() {
        album.setVisibility(View.GONE);
        switch (sp.getString("theme", "")) {
            case "system": AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM); break;
            case "dark": AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES); break;
            case "light": AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO); break;}
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        name.setText(R.string.app_name);
        artist.setText(R.string.login);
        name.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/raleway.ttf"), Typeface.NORMAL);
        artist.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/raleway.ttf"), Typeface.NORMAL);
        linear2.setVisibility(View.GONE);
        edittext2.setSingleLine(true);
        textview1.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/raleway.ttf"), Typeface.NORMAL);
        edittext2.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/raleway.ttf"), Typeface.NORMAL);
        button1.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/raleway.ttf"), Typeface.NORMAL);
        edittext3.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/raleway.ttf"), Typeface.NORMAL);
        button2.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/raleway.ttf"), Typeface.NORMAL);
    }

    @Override
    protected void onActivityResult(int _requestCode, int _resultCode, Intent _data) {
        super.onActivityResult(_requestCode, _resultCode, _data);
    }

    public void _user () {
        map = new HashMap<>();
        map.put("id", FirebaseAuth.getInstance().getCurrentUser().getUid());
        map.put("phone", phone);
        map.put("device", (Build.MANUFACTURER.concat(" ".concat(Build.MODEL))));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            map.put("os", (Build.VERSION.BASE_OS.concat(Build.VERSION.RELEASE)));
        } else {
            map.put("os", (Build.VERSION.RELEASE));}
        map.put("name", "");
        map.put("surname", "");
        map.put("nickname", "");
        map.put("verified", "no");
        map.put("photo", "");
        map.put("sex", "");
        map.put("description", "");
        user.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).updateChildren(map);
    }

    public void _sendVerificationCode () {
        phone = number.getText().toString().concat(edittext2.getText().toString());
        fauth.useAppLanguage();
        com.google.firebase.auth.PhoneAuthProvider.getInstance().verifyPhoneNumber(phone, 60, java.util.concurrent.TimeUnit.SECONDS, this, mCallbacks);
    }

    com.google.firebase.auth.PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks = new com.google.firebase.auth.PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        @Override
        public void onVerificationCompleted(com.google.firebase.auth.PhoneAuthCredential phoneAuthCredential) {
        }

        @Override
        public void onVerificationFailed(com.google.firebase.FirebaseException e) {
        }

        @Override
        public void onCodeSent(String s, com.google.firebase.auth.PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);
            codeSent = s;
        }};{}

    public void _verifyCode () {
        code = edittext3.getText().toString();
        com.google.firebase.auth.PhoneAuthCredential credential = com.google.firebase.auth.PhoneAuthProvider.getCredential(codeSent, code);
        signInWithPhoneAuthCredential(credential);}

    private void signInWithPhoneAuthCredential(com.google.firebase.auth.PhoneAuthCredential credential) {
        fauth.signInWithCredential(credential) .addOnCompleteListener(this, _fauth_sign_in_listener);}{}

    @Deprecated
    public void showMessage(String _s) {
        Toast.makeText(getApplicationContext(), _s, Toast.LENGTH_SHORT).show();}

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
    public ArrayList<Double> getCheckedItemPositionsToArray(ListView _list) {
        ArrayList<Double> _result = new ArrayList<Double>();
        SparseBooleanArray _arr = _list.getCheckedItemPositions();
        for (int _iIdx = 0; _iIdx < _arr.size(); _iIdx++) {
            if (_arr.valueAt(_iIdx))
                _result.add((double)_arr.keyAt(_iIdx));
        }
        return _result;
    }

    @Deprecated
    public float getDip(int _input){
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, _input, getResources().getDisplayMetrics());
    }

    @Deprecated
    public int getDisplayWidthPixels(){
        return getResources().getDisplayMetrics().widthPixels;
    }

    @Deprecated
    public int getDisplayHeightPixels(){
        return getResources().getDisplayMetrics().heightPixels;
    }
}