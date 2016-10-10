package com.rockaport.mobile.mail.messagelist;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;

import com.rockaport.mobile.mail.DividerItemDecoration;
import com.rockaport.mobile.mail.Injection;
import com.rockaport.mobile.mail.R;
import com.rockaport.mobile.mail.composemessage.ComposeMessageActivity;
import com.rockaport.mobile.mail.messagelist.adapters.MessageListAdapter;
import com.rockaport.mobile.mail.models.message.Message;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MessageListActivity extends AppCompatActivity implements MessageListContract.View {
    public static final int REQUEST_CODE = (0x0000ffff & MessageListActivity.class.hashCode());
    private static final String TAG = "MessageListActivity";

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.floating_action_button)
    FloatingActionButton floatingActionButton;

    private MessageListContract.Presenter presenter;
    private MessageListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_list);

        presenter = new MessageListPresenter(this, Injection.provideDatabase(), Injection.provideTransport());

        ButterKnife.bind(this);

        setupRecyclerView();
    }

    @OnClick(R.id.floating_action_button)
    public void onFloatingActionButtonClicked(FloatingActionButton floatingActionButton) {
        presenter.composeMessage();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume() called");
        presenter.loadMessages();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode != REQUEST_CODE) {
            return;
        }

        // TODO: stuff
    }

    private void setupRecyclerView() {
        adapter = new MessageListAdapter(presenter);

        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new DividerItemDecoration());
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        new ItemTouchHelper(new MessageListItemTouchHelper(presenter)).attachToRecyclerView(recyclerView);

        swipeRefreshLayout.setColorSchemeResources(R.color.material_blue_500, R.color.material_blue_300, R.color.material_blue_100);
        swipeRefreshLayout.setOnRefreshListener(() -> presenter.loadMessages());
    }

    @Override
    public void removeMessage(long messageId) {
        adapter.removeMessage(messageId);
    }

    @Override
    public void showComposeMessage() {
        startActivityForResult(new Intent(this, ComposeMessageActivity.class), REQUEST_CODE);
    }

    @Override
    public void showMessage(long messageId) {
        Intent intent = new Intent(this, ComposeMessageActivity.class);

        intent.putExtra(ComposeMessageActivity.EXTRA_MESSAGE_ID, messageId);

        startActivity(intent);
    }

    @Override
    public void showLoadingSpinner(boolean loading) {
        Log.d(TAG, "showLoadingSpinner() called with: loading = [" + loading + "]");

        swipeRefreshLayout.setRefreshing(loading);
    }

    @Override
    public void showMessages(List<Message> messages) {
        adapter.showMessages(messages);
    }
}
