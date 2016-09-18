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
import com.rockaport.mobile.mail.R;
import com.rockaport.mobile.mail.composemessage.ComposeMessageActivity;
import com.rockaport.mobile.mail.message.Message;

import java.util.List;

public class MessageListActivity extends AppCompatActivity implements MessageListContract.View {
    private static final String TAG = "MessageListActivity";
    MessageListContract.Presenter presenter;

    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerView;
    private FloatingActionButton floatingActionButton;

    private MessageListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_list);

        presenter = new MessageListPresenter(this);

        bindViews();

        setupRecyclerView();

        floatingActionButton.setOnClickListener(view -> presenter.composeMessage());
    }

    private void bindViews() {
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_layout);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        floatingActionButton = (FloatingActionButton) findViewById(R.id.floating_action_button);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume() called");


        presenter.loadMessages();
    }

    private void setupRecyclerView() {
        adapter = new MessageListAdapter(presenter);
        recyclerView.setAdapter(adapter);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                Log.d(TAG, "onSwiped() called with: viewHolder = [" + viewHolder + "], direction = [" + direction + "]");
                presenter.deleteMessage(viewHolder.getAdapterPosition(), 0);
            }
        }).attachToRecyclerView(recyclerView);

        recyclerView.addItemDecoration(new DividerItemDecoration());

        swipeRefreshLayout.setColorSchemeResources(R.color.material_blue_500, R.color.material_blue_300, R.color.material_blue_100);
        swipeRefreshLayout.setOnRefreshListener(() -> presenter.loadMessages());
    }

    @Override
    public void removeMessage(int position) {
        adapter.removeMessage(position);
    }

    @Override
    public void showComposeMessage() {
        startActivity(new Intent(this, ComposeMessageActivity.class));
    }

    @Override
    public void showMessage(long messageId) {
        Intent intent = new Intent(this, ComposeMessageActivity.class);
        intent.putExtra(ComposeMessageActivity.MESSAGE_ID_EXTRA, messageId);

        startActivity(intent);
    }

    @Override
    public void showLoadingSpinner(boolean loading) {
        swipeRefreshLayout.setRefreshing(loading);
    }

    @Override
    public void showMessages(List<Message> messages) {
        adapter.showMessages(messages);
    }
}
