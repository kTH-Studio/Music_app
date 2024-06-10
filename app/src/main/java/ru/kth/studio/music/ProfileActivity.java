package ru.kth.studio.music;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import com.google.android.material.appbar.AppBarLayout;
import android.os.*;
import android.widget.*;
import android.graphics.*;
import android.util.*;
import java.util.*;
import java.util.HashMap;
import java.util.ArrayList;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.EditText;
import android.widget.Button;
import android.content.Intent;
import android.content.ClipData;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.OnProgressListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnCompleteListener;
import android.net.Uri;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import android.app.Activity;
import android.content.SharedPreferences;
import android.view.View;

public class ProfileActivity extends  AppCompatActivity  {
    public final int REQ_CD_FP = 101;
    private final Intent i = new Intent();
    private final FirebaseDatabase _firebase = FirebaseDatabase.getInstance();
    private final FirebaseStorage _firebase_storage = FirebaseStorage.getInstance();
    private Toolbar _toolbar;
    private AppBarLayout _app_bar;
    private CoordinatorLayout _coordinator;
    private HashMap<String, Object> map = new HashMap<>();
    private String path;
    private final String pic = "";
    private double connect, prev = 0;
    private ImageView avatar, verified, more;
    private TextView name, nickname, description, hint_info;
    private EditText name_field, surname_field, nickname_field, description_field;
    private Button save, preview;
    private SharedPreferences sp;
    private final Intent fp = new Intent(Intent.ACTION_GET_CONTENT);
    private final DatabaseReference user = _firebase.getReference("users");
    private ChildEventListener _user_child_listener;
    private final StorageReference fstore = _firebase_storage.getReference("profile_pics");
    private OnCompleteListener<Uri> _fstore_upload_success_listener;
    private OnSuccessListener<FileDownloadTask.TaskSnapshot> _fstore_download_success_listener;
    private OnSuccessListener _fstore_delete_success_listener;
    private OnProgressListener _fstore_upload_progress_listener;
    private OnProgressListener _fstore_download_progress_listener;
    private OnFailureListener _fstore_failure_listener;
    private FirebaseAuth fauth;
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
        setContentView(R.layout.profile);
        initialize();
        com.google.firebase.FirebaseApp.initializeApp(this);
        initializeLogic();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1000) {
            initializeLogic();}
    }

    private void initialize() {
        avatar = findViewById(R.id.imageview1);
        more = findViewById(R.id.more);
        name = findViewById(R.id.name);
        description = findViewById(R.id.album);
        name_field = findViewById(R.id.name_field);
        surname_field = findViewById(R.id.surname_field);
        nickname_field = findViewById(R.id.nickname_field);
        description_field = findViewById(R.id.description_field);
        save = findViewById(R.id.save);
        preview = findViewById(R.id.preview);
        hint_info = findViewById(R.id.hint_info);
        fp.setType("image/*");
        fp.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        fauth = FirebaseAuth.getInstance();

        save.setOnClickListener(_view -> {});

        preview.setOnClickListener(_view -> {
            if (prev == 0) {
                prev++;
                hint_info.setVisibility(View.GONE);
                name_field.setVisibility(View.GONE);
                surname_field.setVisibility(View.GONE);
                nickname_field.setVisibility(View.GONE);
                description_field.setVisibility(View.GONE);
                save.setVisibility(View.GONE);
                preview.setText(R.string.edit);
            } else {
                prev = 0;
                hint_info.setVisibility(View.VISIBLE);
                name_field.setVisibility(View.VISIBLE);
                surname_field.setVisibility(View.VISIBLE);
                nickname_field.setVisibility(View.VISIBLE);
                description_field.setVisibility(View.VISIBLE);
                save.setVisibility(View.VISIBLE);
                preview.setText(R.string.preview);}
        });

        _user_child_listener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot _param1, String _param2) {
                GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
                final String _childKey = _param1.getKey();
                final HashMap<String, Object> _childValue = _param1.getValue(_ind);
                i.setClass(getApplicationContext(), MainActivity.class);
                startActivity(i);
                finishAffinity();
            }

            @Override
            public void onChildChanged(DataSnapshot _param1, String _param2) {
                GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
                final String _childKey = _param1.getKey();
                final HashMap<String, Object> _childValue = _param1.getValue(_ind);
                i.setClass(getApplicationContext(), MainActivity.class);
                startActivity(i);
                finishAffinity();
            }

            @Override
            public void onChildMoved(DataSnapshot _param1, String _param2) {}

            @Override
            public void onChildRemoved(DataSnapshot _param1) {
                GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
                final String _childKey = _param1.getKey();
                final HashMap<String, Object> _childValue = _param1.getValue(_ind);
            }

            @Override
            public void onCancelled(DatabaseError _param1) {
                final int _errorCode = _param1.getCode();
                final String _errorMessage = _param1.getMessage();}
        };
        user.addChildEventListener(_user_child_listener);

        _fstore_upload_progress_listener = (OnProgressListener<UploadTask.TaskSnapshot>) _param1 -> {
            double _progressValue = (100.0 * _param1.getBytesTransferred()) / _param1.getTotalByteCount();};

        _fstore_download_progress_listener = (OnProgressListener<FileDownloadTask.TaskSnapshot>) _param1 -> {
            double _progressValue = (100.0 * _param1.getBytesTransferred()) / _param1.getTotalByteCount();};

        _fstore_upload_success_listener = _param1 -> {
            final String _downloadUrl = _param1.getResult().toString();
            map = new HashMap<>();
            map.put("username", "");
            map.put("pic", _downloadUrl);
            user.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).updateChildren(map);
            user.addChildEventListener(_user_child_listener);
        };

        _fstore_download_success_listener = _param1 -> {
            final long _totalByteCount = _param1.getTotalByteCount();};

        _fstore_delete_success_listener = _param1 -> {};

        _fstore_failure_listener = _param1 -> {
            final String _message = _param1.getMessage();};

        fauth_updateEmailListener = _param1 -> {
            final boolean _success = _param1.isSuccessful();
            final String _errorMessage = _param1.getException() != null ? _param1.getException().getMessage() : "";
        };

        fauth_updatePasswordListener = _param1 -> {
            final boolean _success = _param1.isSuccessful();
            final String _errorMessage = _param1.getException() != null ? _param1.getException().getMessage() : "";};

        fauth_emailVerificationSentListener = _param1 -> {
            final boolean _success = _param1.isSuccessful();
            final String _errorMessage = _param1.getException() != null ? _param1.getException().getMessage() : "";};

        fauth_deleteUserListener = _param1 -> {
            final boolean _success = _param1.isSuccessful();
            final String _errorMessage = _param1.getException() != null ? _param1.getException().getMessage() : "";};

        fauth_phoneAuthListener = task -> {
            final boolean _success = task.isSuccessful();
            final String _errorMessage = task.getException() != null ? task.getException().getMessage() : "";};

        fauth_updateProfileListener = _param1 -> {
            final boolean _success = _param1.isSuccessful();
            final String _errorMessage = _param1.getException() != null ? _param1.getException().getMessage() : "";};

        fauth_googleSignInListener = task -> {
            final boolean _success = task.isSuccessful();
            final String _errorMessage = task.getException() != null ? task.getException().getMessage() : "";};

        _fauth_create_user_listener = _param1 -> {
            final boolean _success = _param1.isSuccessful();
            final String _errorMessage = _param1.getException() != null ? _param1.getException().getMessage() : "";};

        _fauth_sign_in_listener = _param1 -> {
            final boolean _success = _param1.isSuccessful();
            final String _errorMessage = _param1.getException() != null ? _param1.getException().getMessage() : "";};

        _fauth_reset_password_listener = new OnCompleteListener<Void>() {
            @Override
            public void onComplete(Task<Void> _param1) {
                final boolean _success = _param1.isSuccessful();
            }};
    }

    private void initializeLogic() {
        switch (sp.getString("theme", "")) {
            case "system": AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM); break;
            case "dark": AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES); break;
            case "light": AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO); break;}
        verified.setVisibility(View.GONE);
        description.setVisibility(View.GONE);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        name.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/moscow.ttf"), Typeface.NORMAL);
        nickname.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/moscow.ttf"), Typeface.NORMAL);
        hint_info.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/moscow.ttf"), Typeface.NORMAL);
        name_field.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/moscow.ttf"), Typeface.NORMAL);
        surname_field.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/moscow.ttf"), Typeface.NORMAL);
        nickname_field.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/moscow.ttf"), Typeface.NORMAL);
        description_field.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/moscow.ttf"), Typeface.NORMAL);
        description.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/moscow.ttf"), Typeface.NORMAL);
        save.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/moscow.ttf"), Typeface.NORMAL);
        preview.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/moscow.ttf"), Typeface.NORMAL);
        user.removeEventListener(_user_child_listener);
    }

    @Override
    protected void onActivityResult(int _requestCode, int _resultCode, Intent _data) {
        super.onActivityResult(_requestCode, _resultCode, _data);
        switch (_requestCode) {
            case REQ_CD_FP:
                if (_resultCode == Activity.RESULT_OK) {
                    ArrayList<String> _filePath = new ArrayList<>();
                    if (_data != null) {
                        if (_data.getClipData() != null) {
                            for (int _index = 0; _index < _data.getClipData().getItemCount(); _index++) {
                                ClipData.Item _item = _data.getClipData().getItemAt(_index);
                                _filePath.add(FileUtil.convertUriToFilePath(getApplicationContext(), _item.getUri()));}
                        } else {
                            _filePath.add(FileUtil.convertUriToFilePath(getApplicationContext(), _data.getData()));}}
                    path = _filePath.get(0);}
                break;
            default: break;}
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
    public ArrayList<Double> getCheckedItemPositionsToArray(ListView _list) {
        ArrayList<Double> _result = new ArrayList<Double>();
        SparseBooleanArray _arr = _list.getCheckedItemPositions();
        for (int _iIdx = 0; _iIdx < _arr.size(); _iIdx++) {
            if (_arr.valueAt(_iIdx))
                _result.add((double)_arr.keyAt(_iIdx));}
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