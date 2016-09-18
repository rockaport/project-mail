package com.rockaport.mobile.mail.messagelist;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.rockaport.mobile.mail.R;
import com.rockaport.mobile.mail.message.Message;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import de.svenjacobs.loremipsum.LoremIpsum;

class MessageListAdapter extends RecyclerView.Adapter<MessageListAdapter.ViewHolder> implements MessageListContract.ListView {
    private static final String TAG = "MessageListAdapter";
    private MessageListContract.Presenter presenter;
    private List<Message> messages = new ArrayList<>();

    MessageListAdapter(MessageListContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.d(TAG, "onCreateViewHolder() called with: parent = [" + parent + "], viewType = [" + viewType + "]");

        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_message_list_item, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder() called with: holder = [" + holder + "], position = [" + position + "]");

        holder.people.setText(new LoremIpsum().getWords(new Random().nextInt(8) + 4));
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMM dd", Locale.getDefault());
        holder.dateTime.setText(simpleDateFormat.format(new Date(messages.get(position).getDateTime())));
        holder.body.setText(messages.get(position).getMessage());

        holder.itemView.setOnClickListener(view -> {
            Log.d(TAG, "onClick() called with: view = [" + view + "], position = [" + holder.getAdapterPosition() + "]");
            presenter.openMessage(messages.get(holder.getAdapterPosition()).getId());
        });
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    @Override
    public void showMessage(Message message) {
        Log.d(TAG, "showMessage() called with: message = [" + message + "]");

        messages.add(message);
        notifyItemInserted(messages.size() - 1);
    }

    @Override
    public void showMessages(List<Message> messages) {
        Log.d(TAG, "showMessages() called with: messages = [" + messages + "]");

        this.messages.clear();
        this.messages.addAll(messages);

        notifyDataSetChanged();
    }

    @Override
    public void removeMessage(int position) {
        Log.d(TAG, "removeMessage() called with: position = [" + position + "]");

        messages.remove(position);

        notifyItemRemoved(position);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView people;
        TextView dateTime;
        TextView body;

        ViewHolder(View itemView) {
            super(itemView);

            people = (TextView) itemView.findViewById(R.id.people);
            dateTime = (TextView) itemView.findViewById(R.id.date_time);
            body = (TextView) itemView.findViewById(R.id.body);

        }
    }
}
