package com.example.live

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.example.common.lifecycle.GSYLifecycleObServer
import com.example.common.logDebug
import com.example.live.adapter.MsgAdapter
import com.example.live.databinding.ActivityLiveBinding
import com.example.live.xmpp.XMPPManager
import com.example.network.user
import org.jivesoftware.smack.MessageListener
import org.jivesoftware.smack.packet.Message
import org.jivesoftware.smackx.muc.MultiUserChat
import org.jivesoftware.smackx.muc.ParticipantStatusListener
import org.jxmpp.jid.EntityFullJid
import kotlin.concurrent.thread

@Route(path = "/live/LiveActivity")
class LiveActivity : AppCompatActivity(),MessageListener,ParticipantStatusListener {
    val binding:ActivityLiveBinding by lazy { DataBindingUtil.setContentView(this, R.layout.activity_live) }
    @Autowired
    @JvmField
    var title:String = ""
    var muc: MultiUserChat? = null
    lateinit var adapter: MsgAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ARouter.getInstance().inject(this)
        binding.player.setUp("http://10.161.9.80:8066/live/livestream.flv", false, title)
        binding.player.backButton.setOnClickListener { finish() }
        lifecycle.addObserver(GSYLifecycleObServer(binding.player))
        binding.player.startPlayLogic()
        adapter = MsgAdapter()
        binding.chatroom.adapter = adapter
        thread {
            user?.let {
                muc = XMPPManager.joinChatRoom(title, it.username)
                muc?.addMessageListener(this)
                muc?.addParticipantStatusListener(this)
            }
        }
        binding.send.setOnClickListener {
            if (binding.message.text.isNotEmpty()) {
                muc?.sendMessage(binding.message.text.toString())
                binding.message.text.clear()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        thread {
            XMPPManager.leaveChatRoom(muc)
        }
    }

    override fun processMessage(message: Message?) {
        message?.takeIf { it.from.hasResource() }?.let {
            val name = it.from.resourceOrEmpty
            logDebug( "$name: ${it.body}")
            adapter += com.example.live.model.Message(name.toString() + (if(name == muc?.nickname) "(我)" else ""), it.body)
        }
    }

    override fun joined(participant: EntityFullJid?) {
        participant?.let {
            logDebug("${it.resourceOrEmpty} 加入直播间")
            adapter += "${it.resourceOrEmpty} 加入直播间"
        }
    }

    override fun left(participant: EntityFullJid?) {
        participant?.let {
            logDebug("${it.resourceOrEmpty} 离开直播间")
            adapter += "${it.resourceOrEmpty} 离开直播间"
        }
    }
}



