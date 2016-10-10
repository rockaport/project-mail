package com.rockaport.mobile.mail.composemessage;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import com.rockaport.mobile.mail.R;
import com.rockaport.mobile.mail.Util;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Subscriber;
import rx.subjects.PublishSubject;

public class ComposeMessageActivity extends AppCompatActivity implements ComposeMessageContract.View {
    public static final String EXTRA_MESSAGE_ID = "EXTRA_MESSAGE_ID";

    private static final String TAG = "ComposeMessageActivity";
    private static final int REQUEST_CODE = Util.getRequestCode(ComposeMessageActivity.class);
    private static final int TEXT_CHANGED_DEBOUNCE = 500;

    @BindView(R.id.message)
    EditText messageText;

    private ComposeMessagePresenter presenter = new ComposeMessagePresenter(this);

    private PublishSubject<Editable> textChangedSubject = PublishSubject.create();
    private Subscriber<Editable> subscriber = new Subscriber<Editable>() {
        @Override
        public void onCompleted() {
        }

        @Override
        public void onError(Throwable e) {
        }

        @Override
        public void onNext(Editable editable) {
            presenter.saveMessage(editable.toString());
        }
    };

    private TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            // unused
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            // unused
        }

        @Override
        public void afterTextChanged(Editable s) {
            textChangedSubject.onNext(s);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_compose_message);

        ButterKnife.bind(this);

        presenter.loadMessage(getIntent().getLongExtra(EXTRA_MESSAGE_ID, -1));
    }

    @Override
    protected void onStop() {
        super.onStop();

        textChangedSubject.onCompleted();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.d(TAG, "onOptionsItemSelected() called with: item = [" + item + "]");

        switch (item.getItemId()) {
            case R.id.action_attach_file:
                presenter.getFile();
                break;
            case R.id.action_send:
                presenter.sendMessage();
                break;
            case R.id.action_delete:
                presenter.deleteMessage();
            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode != REQUEST_CODE || resultCode != RESULT_OK) {
            return;
        }

        try {
            presenter.attachFile(new File(data.getData().getPath()).getName(),
                    getContentResolver().openFileDescriptor(data.getData(), "r"));
        } catch (FileNotFoundException e) {
            Snackbar.make(findViewById(android.R.id.content), "Error getting file " + e.getMessage(), Snackbar.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_compose_message_activity, menu);
        return true;
    }

    @Override
    public void showMessage(String message) {
        messageText.setText(message);
        messageText.setSelection(message.length());
        messageText.addTextChangedListener(textWatcher);

        textChangedSubject.debounce(TEXT_CHANGED_DEBOUNCE, TimeUnit.MILLISECONDS).subscribe(subscriber);
    }

    @Override
    public void displayFilePicker() {
        Log.d(TAG, "displayFilePicker() called: " + REQUEST_CODE);
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);

        intent.setType("*/*");
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.putExtra(Intent.EXTRA_LOCAL_ONLY, true);

        // ensure an activity can consume this intent
        if (getPackageManager().queryIntentActivities(intent, 0).size() <= 0) {
            Snackbar.make(findViewById(android.R.id.content), "No file chooser available", Snackbar.LENGTH_SHORT).show();
            return;
        }

        startActivityForResult(intent, REQUEST_CODE);
    }

    @Override
    public void close() {
        finish();
    }
}
