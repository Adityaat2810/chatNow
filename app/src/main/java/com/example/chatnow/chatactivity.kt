package com.example.chatnow

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.database.ktx.getValue

class chatactivity : AppCompatActivity() {

    private lateinit var messageRecyclerView:RecyclerView
    private lateinit var messageBox:EditText
    private lateinit var sendButton: ImageView
    private lateinit var messageAdapter: messageAdapter
    private lateinit var messageList: ArrayList<Message>
    private lateinit var mDbRef:DatabaseReference

    var receiverRoom:String?=null
    var senderRoom:String?=null



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chatactivity)
        val name=intent.getStringExtra("name")
        val recieveruid=intent.getStringExtra("uid")

        val senderuid=FirebaseAuth.getInstance().currentUser?.uid
        mDbRef=FirebaseDatabase.getInstance().getReference()

        senderRoom=recieveruid+senderuid
        receiverRoom=senderuid+recieveruid

        supportActionBar?.title=name


        messageRecyclerView=findViewById(R.id.chatRecyclerView)
        messageBox=findViewById(R.id.messagebox)
        sendButton=findViewById(R.id.imgview)
        messageList=ArrayList()
        messageAdapter=messageAdapter(this,messageList)

        messageRecyclerView.layoutManager=LinearLayoutManager(this)
        messageRecyclerView.adapter=messageAdapter



        mDbRef.child("chats").child(senderRoom!!).child("messages")
            .addValueEventListener(object:ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {

                    messageList.clear()

                    for(postSnapshot in snapshot.children){
                        val message=postSnapshot.getValue(Message::class.java)
                        messageList.add(message!!)

                    }
                    messageAdapter.notifyDataSetChanged()
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }

            })

        sendButton.setOnClickListener{
            val message=messageBox.text.toString()
            val messageObject=Message(message,senderuid)

            mDbRef.child("chats").child(senderRoom!!).child("messages").push()
                .setValue(messageObject).addOnSuccessListener {
                    mDbRef.child("chats").child(receiverRoom!!).child("messages").push()
                        .setValue(messageObject)

                }
            messageBox.setText("")



        }

    }
}