package com.rockaport.mobile.mail.messagelist;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

class MessageListItemTouchHelper extends ItemTouchHelper.SimpleCallback {
    private MessageListContract.Presenter presenter;

    MessageListItemTouchHelper(MessageListContract.Presenter presenter) {
        super(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT);

        this.presenter = presenter;
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        return false;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        presenter.deleteMessage(((MessageListAdapter.ViewHolder) viewHolder).messageId);
    }
}