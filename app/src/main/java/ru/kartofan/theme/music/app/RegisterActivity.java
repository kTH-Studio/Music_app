package ru.kartofan.theme.music.app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import com.google.android.material.appbar.AppBarLayout;

import android.app.Activity;
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
import java.util.TimerTask;
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
import com.google.android.gms.tasks.Task;
import android.view.View;

public class RegisterActivity extends  AppCompatActivity  {

    private Timer _timer = new Timer();
    private FirebaseDatabase _firebase = FirebaseDatabase.getInstance();
    private Toolbar _toolbar;
    private AppBarLayout _app_bar;
    private CoordinatorLayout _coordinator;
    private double connect = 0;
    private String error = "";
    private String var = "";
    private String verify = "";
    private HashMap<String, Object> map = new HashMap<>();
    private double n = 0;
    private String random = "";
    private String phone = "";
    private String codeSent = "";
    private String code = "";
    private ArrayList<HashMap<String, Object>> list = new ArrayList<>();
    private LinearLayout linear1;
    private LinearLayout linear10;
    private LinearLayout linear2;
    private TextView textview1;
    private TextView name;
    private TextView artist;
    private LinearLayout linear7;
    private LinearLayout linear4;
    private TextView textview3;
    private TextView number;
    private EditText edittext2;
    private LinearLayout linear5;
    private Button button1;
    private LinearLayout linear6;
    private EditText edittext3;
    private LinearLayout linear11;
    private Button button2;
    private Intent i = new Intent();
    private TimerTask t;
    private TimerTask c;
    private TimerTask g;
    private DatabaseReference user = _firebase.getReference("users");
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
        initialize(_savedInstanceState);
        com.google.firebase.FirebaseApp.initializeApp(this);
        initializeLogic();
    }

    private void initialize(Bundle _savedInstanceState) {
        _app_bar = (AppBarLayout) findViewById(R.id._app_bar);
        _coordinator = (CoordinatorLayout) findViewById(R.id._coordinator);
        linear1 = (LinearLayout) findViewById(R.id.linear1);
        linear10 = (LinearLayout) findViewById(R.id.linear10);
        linear2 = (LinearLayout) findViewById(R.id.linear2);
        textview1 = (TextView) findViewById(R.id.textview1);
        linear7 = (LinearLayout) findViewById(R.id.linear7);
        linear4 = (LinearLayout) findViewById(R.id.linear4);
        textview3 = (TextView) findViewById(R.id.textview3);
        edittext2 = (EditText) findViewById(R.id.edittext2);
        linear5 = (LinearLayout) findViewById(R.id.linear5);
        button1 = (Button) findViewById(R.id.button1);
        linear6 = (LinearLayout) findViewById(R.id.linear6);
        edittext3 = (EditText) findViewById(R.id.edittext3);
        linear11 = (LinearLayout) findViewById(R.id.linear11);
        button2 = (Button) findViewById(R.id.button2);
        name = (TextView) findViewById(R.id.name);
        artist = (TextView) findViewById(R.id.artist);
        number = (TextView) findViewById(R.id.number);
        fauth = FirebaseAuth.getInstance();
        sp = getSharedPreferences("sp", Activity.MODE_PRIVATE);

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View _view) {
                if (edittext2.length() == 10) {
                    _sendVerificationCode();
                    linear10.setVisibility(View.GONE);
                    linear2.setVisibility(View.VISIBLE);
                    SketchwareUtil.hideKeyboard(getApplicationContext());
                } else {
                    SketchwareUtil.hideKeyboard(getApplicationContext());
                    com.google.android.material.snackbar.Snackbar.make(linear1, R.string.phone_number_short, com.google.android.material.snackbar.Snackbar.LENGTH_SHORT).setAction("Ok", new View.OnClickListener() {
                        @Override
                        public void onClick(View _view) {}
                    }).show();
                }
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View _view) {
                if (edittext3.length() == 6) {
                    _verifyCode();
                } else {
                    SketchwareUtil.hideKeyboard(getApplicationContext());
                    com.google.android.material.snackbar.Snackbar.make(linear1, R.string.verify_code_short, com.google.android.material.snackbar.Snackbar.LENGTH_SHORT).setAction("Ok", new View.OnClickListener() {
                        @Override
                        public void onClick(View _view) {}
                    }).show();
                }
            }
        });

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
                                list.add(_map);
                            }
                        }
                        catch (Exception _e) {
                            _e.printStackTrace();
                        }
                    }
                    @Override
                    public void onCancelled(DatabaseError _databaseError) {
                    }
                });
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
                                list.add(_map);
                            }
                        }
                        catch (Exception _e) {
                            _e.printStackTrace();
                        }
                    }
                    @Override
                    public void onCancelled(DatabaseError _databaseError) {
                    }
                });
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

        fauth_updateEmailListener = new OnCompleteListener<Void>() {
            @Override
            public void onComplete(Task<Void> _param1) {
                final boolean _success = _param1.isSuccessful();
                final String _errorMessage = _param1.getException() != null ? _param1.getException().getMessage() : "";
            }
        };

        fauth_updatePasswordListener = new OnCompleteListener<Void>() {
            @Override
            public void onComplete(Task<Void> _param1) {
                final boolean _success = _param1.isSuccessful();
                final String _errorMessage = _param1.getException() != null ? _param1.getException().getMessage() : "";
            }
        };

        fauth_emailVerificationSentListener = new OnCompleteListener<Void>() {
            @Override
            public void onComplete(Task<Void> _param1) {
                final boolean _success = _param1.isSuccessful();
                final String _errorMessage = _param1.getException() != null ? _param1.getException().getMessage() : "";
            }
        };

        fauth_deleteUserListener = new OnCompleteListener<Void>() {
            @Override
            public void onComplete(Task<Void> _param1) {
                final boolean _success = _param1.isSuccessful();
                final String _errorMessage = _param1.getException() != null ? _param1.getException().getMessage() : "";
            }
        };

        fauth_phoneAuthListener = new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(Task<AuthResult> task){
                final boolean _success = task.isSuccessful();
                final String _errorMessage = task.getException() != null ? task.getException().getMessage() : "";
            }
        };

        fauth_updateProfileListener = new OnCompleteListener<Void>() {
            @Override
            public void onComplete(Task<Void> _param1) {
                final boolean _success = _param1.isSuccessful();
                final String _errorMessage = _param1.getException() != null ? _param1.getException().getMessage() : "";
            }
        };

        fauth_googleSignInListener = new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(Task<AuthResult> task){
                final boolean _success = task.isSuccessful();
                final String _errorMessage = task.getException() != null ? task.getException().getMessage() : "";
            }
        };

        _fauth_create_user_listener = new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(Task<AuthResult> _param1) {
                final boolean _success = _param1.isSuccessful();
                final String _errorMessage = _param1.getException() != null ? _param1.getException().getMessage() : "";
                if (_success) {
                    _user();
                } else {
                    SketchwareUtil.showMessage(getApplicationContext(), _errorMessage);
                }
            }
        };

        _fauth_sign_in_listener = new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(Task<AuthResult> _param1) {
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
                                    list.add(_map);
                                }
                            }
                            catch (Exception _e) {
                                _e.printStackTrace();
                            }
                            if (list.size() == 0) {
                                if (_success) {
                                    _user();
                                } else {
                                    SketchwareUtil.showMessage(getApplicationContext(), _errorMessage);
                                }
                            } else {
                                for(int _repeat72 = 0; _repeat72 < (int)(list.size()); _repeat72++) {
                                    if (list.get((int)n).containsKey(FirebaseAuth.getInstance().getCurrentUser().getUid())) {
                                    } else {
                                        if (_success) {
                                            _user();
                                        } else {
                                            SketchwareUtil.showMessage(getApplicationContext(), _errorMessage);
                                        }
                                    }
                                    n = n + 1;
                                }
                            }
                        }
                        @Override
                        public void onCancelled(DatabaseError _databaseError) {
                        }
                    });
                    i.setClass(getApplicationContext(), MainActivity.class);
                    startActivity(i);
                    finishAffinity();
                } else {
                    SketchwareUtil.showMessage(getApplicationContext(), _errorMessage);
                }
            }
        };

        _fauth_reset_password_listener = new OnCompleteListener<Void>() {
            @Override
            public void onComplete(Task<Void> _param1) {
                final boolean _success = _param1.isSuccessful();
            }
        };
    }

    private void initializeLogic() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        name.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/moscow.ttf"), Typeface.NORMAL);
        artist.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/moscow.ttf"), Typeface.NORMAL);
        linear2.setVisibility(View.GONE);
        edittext2.setSingleLine(true);
        textview1.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/moscow.ttf"), Typeface.NORMAL);
        edittext2.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/moscow.ttf"), Typeface.NORMAL);
        button1.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/moscow.ttf"), Typeface.NORMAL);
        edittext3.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/moscow.ttf"), Typeface.NORMAL);
        button2.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/moscow.ttf"), Typeface.NORMAL);
    }

    @Override
    protected void onActivityResult(int _requestCode, int _resultCode, Intent _data) {
        super.onActivityResult(_requestCode, _resultCode, _data);
        switch (_requestCode) {
            default: break;
        }
    }

    public void _user () {
        map = new HashMap<>();
        map.put("id", FirebaseAuth.getInstance().getCurrentUser().getUid());
        map.put("phone", phone.toString());
        map.put("device", (Build.MANUFACTURER.concat(" ".concat(Build.MODEL))).toString());
        map.put("os", (Build.VERSION.BASE_OS.concat(Build.VERSION.RELEASE).toString()));
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
        com.google.firebase.auth.PhoneAuthProvider.getInstance().verifyPhoneNumber(phone, 60, java.util.concurrent.TimeUnit.SECONDS, this, mCallbacks);
        fauth.useAppLanguage();
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
        }
    };{}

    public void _verifyCode () {
        code = edittext3.getText().toString();
        com.google.firebase.auth.PhoneAuthCredential credential = com.google.firebase.auth.PhoneAuthProvider.getCredential(codeSent, code);
        signInWithPhoneAuthCredential(credential);
    }

    private void signInWithPhoneAuthCredential(com.google.firebase.auth.PhoneAuthCredential credential) {
        fauth.signInWithCredential(credential) .addOnCompleteListener(this, _fauth_sign_in_listener);
    }{}


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