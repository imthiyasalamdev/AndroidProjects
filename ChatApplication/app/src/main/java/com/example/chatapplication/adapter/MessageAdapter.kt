package com.example.chatapplication.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.chatapplication.R
import com.example.chatapplication.models.Message
import com.google.firebase.auth.FirebaseAuth

class MessageAdapter(val context: Context, val messageList: ArrayList<Message>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    val ITEM_RECEIVE = 1
    val ITEM_SENT = 2

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        if (viewType == 1) {
            val view: View =
                LayoutInflater.from(context).inflate(R.layout.receive_message, parent, false)
            return ReceiveViewHolder(view)
        } else {
            val view: View =
                LayoutInflater.from(context).inflate(R.layout.send_message, parent, false)
            return SendViewHolder(view)
        }
    }


    override fun getItemViewType(position: Int): Int {
        val currentMessage = messageList[position]
        if (FirebaseAuth.getInstance().currentUser?.uid.equals(currentMessage.senderId)) {
            return ITEM_SENT
        } else {
            return ITEM_RECEIVE
        }

        return super.getItemViewType(position)
    }

    override fun getItemCount(): Int {
        return messageList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (holder.javaClass == SendViewHolder::class.java) {

            val currentSentMessage = messageList[position]

            val sendViewHolder = holder as SendViewHolder
            holder.sendMessageTv.text = currentSentMessage.message
        } else {

            val currentReceiveMsg = messageList[position]
            val receiveViewHolder = holder as ReceiveViewHolder
            holder.receiveMessageTv.text = currentReceiveMsg.message
        }
    }


    class SendViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val sendMessageTv = itemView.findViewById<TextView>(R.id.sendMessageTv)
    }

    class ReceiveViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val receiveMessageTv = itemView.findViewById<TextView>(R.id.receiveMessageTv)
    }
}