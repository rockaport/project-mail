package com.rockaport.mobile.mail.messagelist.adapters;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.rockaport.mobile.mail.R;
import com.rockaport.mobile.mail.messagelist.MessageListContract;
import com.rockaport.mobile.mail.models.message.Message;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import de.svenjacobs.loremipsum.LoremIpsum;

public class MessageListAdapter extends RecyclerView.Adapter<MessageListAdapter.ViewHolder> implements MessageListContract.ListView {
    private static final String TAG = "MessageListAdapter";
    private MessageListContract.Presenter presenter;
    private List<Message> messages = new ArrayList<>();

    public MessageListAdapter(MessageListContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.d(TAG, "onCreateViewHolder() called with: parent = [" + parent + "], viewType = [" + viewType + "]");

        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_message_list_item, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Message message = messages.get(holder.getAdapterPosition());

        // Update the sql id for this view
        holder.messageId = message.getId();

        // Update the status icon
        switch (message.getStatus()) {
            case NONE:
                break;
            case READ:
                holder.messageIcon.setImageResource(R.drawable.ic_read_message_black_24dp);
                break;
            case UNREAD:
                holder.messageIcon.setImageResource(R.drawable.ic_message_black_24dp);
                break;
            case DRAFT:
                holder.messageIcon.setImageResource(R.drawable.ic_draft_message_black_24dp);
                break;
        }

        // Update the list of people
        holder.people.setText(new LoremIpsum().getWords(new Random().nextInt(8) + 4));

        // Update the date
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMM dd", Locale.getDefault());
        holder.dateTime.setText(simpleDateFormat.format(new Date(messages.get(position).getDateTime())));

        // Update the number of attachments
        if (messages.get(position).getAttachmentCount() > 0) {
            holder.numAttachments.setText(String.valueOf(messages.get(position).getAttachmentCount()));
            holder.numAttachmentsIcon.setVisibility(View.VISIBLE);
        } else {
            holder.numAttachments.setVisibility(View.GONE);
            holder.numAttachmentsIcon.setVisibility(View.GONE);
        }

        // Show a snippet of the message body
        holder.body.setText(messages.get(position).getMessage());

        // Update the click listener
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
    public void showMessages(List<Message> messages) {
        Log.d(TAG, "showMessages() called with: messages = [" + messages + "]");

        this.messages.clear();
        this.messages.addAll(messages);

        notifyDataSetChanged();
    }

    @Override
    public void removeMessage(long messageId) {
        Log.d(TAG, "removeMessage() called with: messageId = [" + messageId + "]");

        for (int i = 0; i < messages.size(); i++) {
            if (messages.get(i).getId() == messageId) {
                messages.remove(i);
                notifyItemRemoved(i);

                break;
            }
        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        long messageId;
        ImageView messageIcon;
        TextView people;
        TextView dateTime;
        TextView numAttachments;
        ImageView numAttachmentsIcon;
        TextView body;

        ViewHolder(View itemView) {
            super(itemView);

            messageIcon = (ImageView) itemView.findViewById(R.id.message_icon);
            people = (TextView) itemView.findViewById(R.id.people);
            dateTime = (TextView) itemView.findViewById(R.id.date_time);
            numAttachments = (TextView) itemView.findViewById(R.id.num_attachments);
            numAttachmentsIcon = (ImageView) itemView.findViewById(R.id.num_attachments_icon);
            body = (TextView) itemView.findViewById(R.id.body);

        }

        public long getMessageId() {
            return messageId;
        }
    }
}
