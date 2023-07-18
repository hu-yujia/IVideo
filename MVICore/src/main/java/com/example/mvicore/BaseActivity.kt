package com.example.mvicore

import android.os.Bundle
import android.os.PersistableBundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.lang.reflect.ParameterizedType

class BaseActivity<BINDING:ViewDataBinding,MODEL:ViewModel>:AppCompatActivity() {
    lateinit var binding:BINDING
    lateinit var viewModel:MODEL
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val type = this.javaClass.genericSuperclass
        if (type is ParameterizedType) {
            val types = type.actualTypeArguments
            val b = types[0]
            if (b is Class<*>) {
                val method = b.getMethod("inflate", LayoutInflater::class.java)
                binding = method.invoke(null,layoutInflater) as BINDING
                setContentView(binding.root)
            }

            val vm = types[1]
            if (vm is Class<*>) {
                viewModel=ViewModelProvider(this).get(vm as Class<ViewModel>) as MODEL
            }

        }

    }
}