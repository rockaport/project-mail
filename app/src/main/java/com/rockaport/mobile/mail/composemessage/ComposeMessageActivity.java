package com.rockaport.mobile.mail.composemessage;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import com.rockaport.mobile.mail.R;
import com.rockaport.mobile.mail.message.Message;

public class ComposeMessageActivity extends AppCompatActivity implements ComposeMessageContract.View {
    public static final String MESSAGE_ID_EXTRA = "MESSAGE_ID_EXTRA";
    private static final String TAG = "ComposeMessageActivity";
    private EditText messageText;
    private Message message;
    private ComposeMessagePresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_compose_message);

        bindViews();

        presenter = new ComposeMessagePresenter(this);

        presenter.loadMessage(getIntent().getLongExtra(MESSAGE_ID_EXTRA, -1));
    }

    @Override
    protected void onResume() {
        super.onResume();

        Log.d(TAG, "onResume() called");

    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause() called");

        presenter.saveMessage(buildMessage());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.d(TAG, "onOptionsItemSelected() called with: item = [" + item + "]");

        switch (item.getItemId()) {
            case R.id.action_attach_file:
                break;
            case R.id.action_send:
                presenter.saveMessage(buildMessage());
                finish();
                break;
            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void bindViews() {
        messageText = (EditText) findViewById(R.id.message);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_compose_message_activity, menu);
        return true;
    }

    @Override
    public void showMessage(Message message) {
        this.message = message;
        messageText.setText(message.getMessage());
        messageText.setSelection(message.getMessage().length());
    }

    Message buildMessage() {
        if (message == null) {
            message = new Message();
        }

        message.setDateTime(System.currentTimeMillis());
        message.setMessage(messageText.getText().toString());

        return message;
    }
}
