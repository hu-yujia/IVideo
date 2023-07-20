package com.example.homepager.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.example.homepager.adapter.SimpleTypeAdapter
import com.example.homepager.databinding.FragmentHomepageBinding
import com.example.homepager.viewmodel.HomepageIntent
import com.example.homepager.viewmodel.HomepageState
import com.example.homepager.viewmodel.HomepageViewModel
import com.example.mvicore.BaseFragment
import kotlinx.coroutines.launch

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [HomepageFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HomepageFragment : BaseFragment<FragmentHomepageBinding,HomepageViewModel>() {
    val adapter by lazy { SimpleTypeAdapter(childFragmentManager) }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lifecycleScope.launch{
            viewModel.intent.send(HomepageIntent.LoadType)
        }
        binding.page.adapter=adapter
        binding.tab.setupWithViewPager(binding.page)
        binding.page.currentItem=1
    }
    fun erro(error:HomepageState.Error){
        Toast.makeText(context, error.msg, Toast.LENGTH_SHORT).show()
    }
    fun loaded(response:HomepageState.Response){
        adapter+=response.data
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @return A new instance of fragment HomepageFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance() = HomepageFragment()
    }

}