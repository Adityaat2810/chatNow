package com.example.chatnow
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.google.firebase.auth.FirebaseAuth
import androidx.recyclerview.widget.RecyclerView as RecyclerView

class UserAdapter(val context: Context,val userlist:ArrayList<user>):
    RecyclerView.Adapter<UserAdapter.userViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): userViewHolder {
     val view:View= LayoutInflater.from(context).inflate(R.layout.userlayout,parent,false)
     return userViewHolder(view)
    }

    override fun onBindViewHolder(holder: userViewHolder, position: Int) {
        val currentUser=userlist[position]
        holder.textName.text=currentUser.name

        holder.itemView.setOnClickListener{
            val intent =Intent(context,chatactivity::class.java)

            intent.putExtra("name",currentUser.name)
            intent.putExtra("uid",currentUser.uid)
            context.startActivity(intent)
        }

    }

    override fun getItemCount(): Int {
        return userlist.size
    }

    class userViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val textName=itemView.findViewById<TextView>(R.id.txt_name)

    }
}