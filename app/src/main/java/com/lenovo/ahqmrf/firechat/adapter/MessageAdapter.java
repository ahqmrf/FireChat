package com.lenovo.ahqmrf.firechat.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.lenovo.ahqmrf.firechat.R;
import com.lenovo.ahqmrf.firechat.model.Message;

import java.util.ArrayList;

/**
 * Created by Lenovo on 2/22/2017.
 */

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ViewHolder> {

    private final int LEFT = 1;
    private final int RIGHT = 2;
    private Context context;
    private String id;
    private ArrayList<Message> itemList;

    public MessageAdapter(Context context, String id, ArrayList<Message> itemList) {
        this.context = context;
        this.id = id;
        this.itemList = itemList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewHolder holder;
        LayoutInflater inflater = LayoutInflater.from(context);
        switch (viewType) {
            case RIGHT:
                holder = new ViewHolder(inflater.inflate(R.layout.sent_msg_item, parent, false));
                break;
            default:
                holder = new ViewHolder(inflater.inflate(R.layout.received_msg_item, parent, false));
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Message message = itemList.get(position);
        holder.msg.setText(message.getText());
        if(message.getReadBySentTo().equals("1") && holder.getItemViewType() == RIGHT) {
            holder.seen.setBackgroundResource(R.drawable.ic_seen);
        }
        else if(message.getReadBySentTo().equals("0") && holder.getItemViewType() == RIGHT) {
            holder.seen.setBackgroundResource(R.drawable.ic_unseen);
        }
    }


    @Override
    public int getItemCount() {
        return itemList.size();
    }

    @Override
    public int getItemViewType(int position) {
        if(itemList.get(position).getId().equals(id)) {
            return RIGHT;
        }
        return LEFT;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView msg;
        public ImageView seen;

        public ViewHolder(View itemView) {
            super(itemView);
            msg = (TextView) itemView.findViewById(R.id.tv_msg);
            seen = (ImageView) itemView.findViewById(R.id.iv_seen);
        }
    }
}
