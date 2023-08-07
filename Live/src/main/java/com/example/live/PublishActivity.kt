package com.example.live

import android.Manifest
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import androidx.activity.result.contract.ActivityResultContracts
import androidx.databinding.DataBindingUtil
import com.alibaba.android.arouter.facade.annotation.Route
import com.example.common.logDebug
import com.example.live.adapter.MsgAdapter
import com.example.live.databinding.ActivityPublishBinding
import com.example.live.xmpp.XMPPManager
import com.example.network.user

import com.ivideo.avcore.rtmplive.Config
import com.ivideo.avcore.rtmplive.MediaPublisher
import com.ivideo.avcore.wiget.CameraGLSurfaceView.OnSurfaceCallback
import org.jivesoftware.smack.MessageListener
import org.jivesoftware.smack.packet.Message
import org.jivesoftware.smackx.muc.MultiUserChat
import org.jivesoftware.smackx.muc.ParticipantStatusListener
import org.jxmpp.jid.EntityFullJid
import javax.microedition.khronos.opengles.GL10
import kotlin.concurrent.thread


@Route(path = "/live/PublishActivity")
class PublishActivity : AppCompatActivity(),MessageListener,ParticipantStatusListener {
    lateinit var binding: ActivityPublishBinding
    lateinit var mediaPublisher: MediaPublisher
    var muc:MultiUserChat? = null
    lateinit var adapter:MsgAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_publish)
        adapter = MsgAdapter()
        binding.chatroom.adapter = adapter
        thread {
            user?.let {
                muc = XMPPManager.createChatRoom("${it.username}的房间", it.username)
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

    override fun onResume() {
        super.onResume()
        mediaPublisher = MediaPublisher.newInstance(
            Config.Builder()
                .setFps(30)
                .setMaxWidth(720)
                .setMinWidth(360)
                .setUrl(BuildConfig.RTMP_URL)
                .build()
        )
        mediaPublisher.init()
        binding.surface.setCallback(object :OnSurfaceCallback{
            override fun surfaceCreated() {
                mediaPublisher.initVideoGatherer(this@PublishActivity, binding.surface.surfaceTexture)
            }

            override fun surfaceChanged(gl: GL10?, width: Int, height: Int) {
                mediaPublisher.initVideoGatherer(this@PublishActivity, binding.surface.surfaceTexture)
            }
        })
        binding.checkbox.setOnCheckedChangeListener { _, b ->
            if (b) {
                start()
            } else {
                stop()
            }
        }
    }
    fun start() {
        mediaPublisher.initAudioGatherer()
        mediaPublisher.initEncoders()
        mediaPublisher.startGather()
        mediaPublisher.startEncoder()
        mediaPublisher.starPublish()

    }
    fun stop() {
        mediaPublisher.stopPublish()
        mediaPublisher.stopEncoder()
        mediaPublisher.stopGather()
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaPublisher.release()
        thread {
            XMPPManager.destroyChatRoom(muc)
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