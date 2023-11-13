package com.example.chatapplication.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.chatapplication.R
import com.example.chatapplication.adapter.MessageAdapter
import com.example.chatapplication.models.Message
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class ChatActivity : AppCompatActivity() {
    private lateinit var chatRecyclerView: RecyclerView
    private lateinit var messageBoxEt: EditText
    private lateinit var sendBtnIv: ImageView
    private lateinit var messageAdapter: MessageAdapter
    private lateinit var messageList: ArrayList<Message>
    private lateinit var databaseReference: DatabaseReference

    var receiverRoom: String? = null
    var senderRoom: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)
        setTitle("Chat")

        databaseReference = FirebaseDatabase.getInstance().getReference()

        val intent = Intent()
        val name = intent.getStringArrayExtra("name")
        val receiverUid = intent.getStringArrayExtra("uid")
        supportActionBar?.title = name.toString()

        val senderUid = FirebaseAuth.getInstance().currentUser?.uid
            senderRoom = (receiverUid?.plus(senderUid)).toString()

        receiverRoom = senderUid + receiverUid


        chatRecyclerView = findViewById(R.id.chatRecyclerView)
        messageBoxEt = findViewById(R.id.messageBoxEt)
        sendBtnIv = findViewById(R.id.sendBtnIv)
        messageList = ArrayList()
        messageAdapter = MessageAdapter(this, messageList)
        chatRecyclerView.layoutManager = LinearLayoutManager(this)
        chatRecyclerView.adapter = messageAdapter


        databaseReference.child("chats").child(senderRoom!!).child("messages")
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    messageList.clear()
                    for (postSnapshot in snapshot.children) {
                        val message = postSnapshot.getValue(Message::class.java)
                        messageList.add(message!!)


                        messageAdapter.notifyDataSetChanged()
                    }
                }

                override fun onCancelled(error: DatabaseError) {

                }

            })

        sendBtnIv.setOnClickListener {

            val message = messageBoxEt.text.toString()
            val messageObject = Message(message, senderUid)

            databaseReference.child("chats").child(senderRoom!!).child("messages").push()
                .setValue(messageObject).addOnSuccessListener {
                    databaseReference.child("chats").child(receiverRoom!!).child("messages").push()
                        .setValue(messageObject)
                }


            messageBoxEt.setText("")
        }
    }
}