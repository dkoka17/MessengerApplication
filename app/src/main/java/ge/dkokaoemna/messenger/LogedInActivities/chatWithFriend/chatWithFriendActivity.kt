package ge.dkokaoemna.messenger.LogedInActivities.chatWithFriend

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import android.media.MediaRecorder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.google.android.material.bottomappbar.BottomAppBar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import ge.dkokaoemna.messenger.Firebase.models.Chat
import ge.dkokaoemna.messenger.Firebase.models.Sms
import ge.dkokaoemna.messenger.Firebase.models.User
import ge.dkokaoemna.messenger.LogedInActivities.Chats.ChatPresenter
import ge.dkokaoemna.messenger.R
import ge.dkokaoemna.messenger.authentification.ViewPagerAdapter1
import java.io.File
import java.io.IOException
import java.util.*

class chatWithFriendActivity : AppCompatActivity(){

    private var mediaRecorder: MediaRecorder? = null
    private var state: Boolean = false
    private var mFileName: String = ""

    private lateinit var recView: RecyclerView
    private lateinit var adapter: smsListAdapter
    private lateinit var presenter: ChatPresenter

    private lateinit var database: FirebaseDatabase
    private lateinit var auth: FirebaseAuth
    private var position: Int = 0
    private lateinit var chat: Chat

    private lateinit var viewPager : ViewPager2
    private lateinit var upFragment : Fragment
    private lateinit var downFragment : Fragment
    private lateinit var adapter1 : ViewPagerAdapter1


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.chat_with_friend_layout)

        database = Firebase.database
        auth = Firebase.auth

        var email = auth.currentUser?.email
        email = email?.length?.minus(10)?.let { email!!.substring(0, it) }
        adapter  = smsListAdapter(Collections.emptyList(), email.toString())

        chat = intent.extras?.get("chat") as Chat

        position = intent.extras?.get("position") as Int


        val smsItems : RecyclerView = findViewById(R.id.smsRecycler)
        smsItems.layoutManager = StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL)

        recView = findViewById(R.id.smsRecycler)
        recView.adapter = adapter

        database.getReference("Users").get().addOnSuccessListener {
            val user = it.child(chat.friendName!!).getValue(User::class.java) as User
            val img : ImageView = findViewById(R.id.friend_image)
            Glide.with(this@chatWithFriendActivity)
                .load(user.imgUrl)
                .circleCrop()
                .into(img)
            viewPager = findViewById(R.id.friend_description_fragments)
            upFragment = UpFragment(this@chatWithFriendActivity, user.nickname, user.job)
            downFragment = DownFragment(this@chatWithFriendActivity, user.nickname, user.job)
            adapter1 = ViewPagerAdapter1(upFragment, downFragment, this@chatWithFriendActivity)
            viewPager.adapter = adapter1
            viewPager.setCurrentItem(0)
            recView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    if (dy > 0) {
                        viewPager.setCurrentItem(1)
                    } else if (dy < 0 && !recyclerView.canScrollVertically(-1)) {
                        viewPager.setCurrentItem(0)
                    }
                }
            })
        }

        database.getReference("Users").child(email!!).child("chats").child(position.toString()).addValueEventListener(object :
                ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                chat = dataSnapshot.getValue(Chat::class.java) as Chat

                val other: List<Sms> = chat.smses
                adapter.list = other
                adapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                //Toast.makeText(this@ChatsActivity, "error", Toast.LENGTH_SHORT).show()
            }
        })


        var record : Button = findViewById(R.id.button_voice_record)


        record.setOnClickListener {
            if (!state ){
                if (ContextCompat.checkSelfPermission(this,
                                Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(this,
                                Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    val permissions = arrayOf(android.Manifest.permission.RECORD_AUDIO, android.Manifest.permission.WRITE_EXTERNAL_STORAGE, android.Manifest.permission.READ_EXTERNAL_STORAGE)
                    ActivityCompat.requestPermissions(this, permissions, 0)
                } else {
                    startRecording()
                }
            }else{
                stopRecording()
            }

        }


        var sendMessage : Button = findViewById(R.id.button_gchat_send)
        sendMessage.setOnClickListener {

            var editText = findViewById(R.id.edit_gchat_message) as EditText
            var txt = editText.text.toString()
            editText.text.clear()
            database.getReference("Users").child(email!!).child("chats").child(position.toString()).child("smses").child(chat.size).setValue(Sms(email, Calendar.getInstance().timeInMillis.toString(), "text",txt))
            database.getReference("Users").child(email!!).child("chats").child(position.toString()).child("size").setValue((chat.size.toInt() + 1).toString())
            chat.size =  (chat.size.toInt() + 1).toString()

            database.getReference(chat.friendChatPath).get().addOnSuccessListener {
                var friendChat = it.getValue(Chat::class.java) as Chat

                database.getReference(chat.friendChatPath).child("smses").child(friendChat.size).setValue(Sms(email,Calendar.getInstance().timeInMillis.toString(), "text",txt))
                database.getReference(chat.friendChatPath).child("size").setValue((friendChat.size.toInt() + 1).toString())

            }


        }

    }

    private fun stopRecording(){
        if(state){
            mediaRecorder?.stop()
            mediaRecorder?.release()
            state = false



            val storageRef = Firebase.storage.reference
            val imageRef = storageRef.child("voices/" + UUID.randomUUID().toString());
            val file: Uri = Uri.fromFile(File(mFileName))
            var uploadTask = imageRef.putFile(file)
            val urlTask = uploadTask.continueWithTask { task ->
                if (!task.isSuccessful) {
                    task.exception?.let {
                        throw it
                    }
                }
                imageRef.downloadUrl
            }.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val downloadUri = task.result
                    var voiceUrl = downloadUri.toString()

                    var email = auth.currentUser?.email
                    email = email?.length?.minus(10)?.let { email!!.substring(0, it) }

                    database.getReference("Users").child(email!!).child("chats").child(position.toString()).child("smses").child(chat.size).setValue(Sms(email, Calendar.getInstance().timeInMillis.toString(), "voice",voiceUrl))
                    database.getReference("Users").child(email!!).child("chats").child(position.toString()).child("size").setValue((chat.size.toInt() + 1).toString())
                    chat.size =  (chat.size.toInt() + 1).toString()

                    database.getReference(chat.friendChatPath).get().addOnSuccessListener {
                        var friendChat = it.getValue(Chat::class.java) as Chat

                        database.getReference(chat.friendChatPath).child("smses").child(friendChat.size).setValue(Sms(email,Calendar.getInstance().timeInMillis.toString(), "voice",voiceUrl))
                        database.getReference(chat.friendChatPath).child("size").setValue((friendChat.size.toInt() + 1).toString())

                    }

                    Toast.makeText(this, "UPloaded started!", Toast.LENGTH_SHORT).show()
                }
            }



        }else{
            Toast.makeText(this, "You are not recording right now!", Toast.LENGTH_SHORT).show()
        }
    }

    private fun startRecording() {
        try {
            mediaRecorder = MediaRecorder()
            mediaRecorder?.setAudioSource(MediaRecorder.AudioSource.MIC)
            mediaRecorder?.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4)
            mediaRecorder?.setAudioEncoder(MediaRecorder.AudioEncoder.AAC)

            mFileName = applicationContext.getExternalFilesDir(null)!!.absolutePath + "/audiorecordtest.3gp"
            mediaRecorder?.setOutputFile(mFileName)

            mediaRecorder?.prepare()
            mediaRecorder?.start()
            state = true
            Toast.makeText(this, "Recording started!", Toast.LENGTH_SHORT).show()
        } catch (e: IllegalStateException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    fun goBack() {
        finish()
    }

}