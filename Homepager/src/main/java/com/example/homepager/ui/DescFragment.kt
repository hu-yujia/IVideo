package com.example.homepager.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.common.logDebug
import com.example.homepager.R
import com.example.homepager.database.homeDatabase
import com.example.homepager.databinding.FragmentDescBinding
import com.example.homepager.viewmodel.DetailsIntent
import com.example.homepager.viewmodel.DetialsState
import com.example.homepager.viewmodel.DetialsViewModel
import com.example.mvicore.BaseFragment
import kotlinx.coroutines.launch

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val VIDEO_ID = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [DescFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class DescFragment : BaseFragment<FragmentDescBinding,DetialsViewModel>() {
    // TODO: Rename and change types of parameters
    private var id: Int? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            id = it.getInt(VIDEO_ID)

        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lifecycleScope.launch {
            viewModel.intent.send(DetailsIntent.LoadLocal(id?:0))
        }
    }
    override val defaultViewModelProviderFactory
        get() = viewModelFactory { initializer { DetialsViewModel(requireContext().homeDatabase.getVideoDao()) } }

    fun loaded(response: DetialsState.LocalResponse){
        binding.video=response.data
    }


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment DescFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(id: Int) =
            DescFragment().apply {
                arguments = Bundle().apply {
                    putInt(VIDEO_ID, id)

                }
            }
    }
}