package com.example.homepager.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.common.adapter.CommonAdapter
import com.example.homepager.R
import com.example.homepager.database.homeDatabase
import com.example.homepager.databinding.FragmentCommentBinding
import com.example.homepager.model.Comment
import com.example.homepager.viewmodel.CommentIntent
import com.example.homepager.viewmodel.CommentState
import com.example.homepager.viewmodel.CommentViewModel
import com.example.homepager.viewmodel.DetailsIntent
import com.example.homepager.viewmodel.DetialsState
import com.example.homepager.viewmodel.DetialsViewModel
import com.example.mvicore.BaseFragment
import kotlinx.coroutines.launch
import com.example.homepager.BR
import com.example.homepager.model.VideoModel
import com.example.network.user


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val VIDEO_ID = "param1"
private val adapter = CommonAdapter<Comment>(R.layout.item_comment,BR.comment)
private lateinit var video:VideoModel
/**
 * A simple [Fragment] subclass.
 * Use the [CommentFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class CommentFragment : BaseFragment<FragmentCommentBinding,CommentViewModel>() {
    // TODO: Rename and change types of parameters
    private var id: Int? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            id = it.getInt(VIDEO_ID)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lifecycleScope.launch {
            viewModel.intent.send(CommentIntent.LoadLocal(id?:0))
        }
        binding.rv.adapter = adapter
        binding.send.setOnClickListener{
            if (user==null) {
                Toast.makeText(requireContext(), "请先登录", Toast.LENGTH_SHORT).show()
            }else if(binding.content.text.isNullOrEmpty()){
                Toast.makeText(requireContext(), "评论为空", Toast.LENGTH_SHORT).show()
            }else{
                lifecycleScope.launch {
                    viewModel.intent.send(CommentIntent.PublishComment(Comment(datatype = 0, content = binding.content.text.toString(), itemid = video.item_id)))
                }
            }
        }
    }

    fun loaded(response: CommentState.LocalResponse){
        video = response.data
        lifecycleScope.launch {
            viewModel.intent.send(CommentIntent.LoadComment(video.item_id))
        }
    }

    fun loadComment(response: CommentState.CommentResponse){
        adapter+=response.data
    }

    fun published(response: CommentState.PublishResponse){
        adapter+= listOf(response.data)
        Toast.makeText(requireContext(), "评论成功", Toast.LENGTH_SHORT).show()
        binding.content.text=null
    }

    fun error(error: CommentState.Error){
        Toast.makeText(requireContext(), error.msg, Toast.LENGTH_SHORT).show()
    }
    override val defaultViewModelProviderFactory
        get() = viewModelFactory { initializer { CommentViewModel(requireContext().homeDatabase.getVideoDao()) } }


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment CommentFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(id: Int) =
            CommentFragment().apply {
                arguments = Bundle().apply {
                    putInt(VIDEO_ID, id)
                }
            }
    }
}