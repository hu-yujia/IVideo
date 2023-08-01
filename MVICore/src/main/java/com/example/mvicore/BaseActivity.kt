package com.example.mvicore

import android.os.Bundle
import android.os.PersistableBundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.mvicore.viewmodel.BaseViewModel
import com.example.mvicore.viewmodel.IState
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.lang.reflect.Method
import java.lang.reflect.ParameterizedType


/*
这段代码是一个基础活动类，它继承自AppCompatActivity，并使用了泛型来定义了两个类型参数BINDING和MODEL。

在该类中，首先定义了一个lateinit属性binding，用于存储视图绑定对象。然后定义了一个lateinit属性viewModel
，用于存储视图模型对象。还定义了一个私有的map属性，用于存储状态类和对应处理方法的映射关系。

在onCreate方法中，首先获取当前类的泛型超类（即该类的父类的泛型信息）。然后通过判断泛型类型的实际参数，获取绑定类和视图模型类的类型。
接着，根据绑定类的类型，通过反射调用inflate方法创建绑定对象，并将其设置为活动的内容视图。
再根据视图模型类的类型，使用ViewModelProvider创建视图模型对象，并通过反射获取视图模型的泛型超类。
然后，通过反射获取状态类和处理方法的映射关系，并使用lifecycleScope启动一个协程来观察视图模型的状态，并将状态对象传递给对应的处理方法。

最后，定义了一个私有的collect方法，用于根据状态对象获取对应的处理方法，并通过反射调用该方法。

这段代码的作用是为活动提供了视图绑定和视图模型的基本功能，并通过反射实现了状态类和处理方法的自动映射。
这样，在派生类中只需要实现对应状态类的处理方法，就可以自动触发处理方法来更新界面。
*/
@Suppress("UNCHECKED_CAST")
open class BaseActivity<BINDING:ViewDataBinding,MODEL:BaseViewModel<*,*>>:AppCompatActivity() {
    lateinit var binding:BINDING
    lateinit var viewModel:MODEL
    private lateinit var map:Map<*,Method>
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
                val vmSuper = vm.genericSuperclass
                if (vmSuper is ParameterizedType) {
                    val state = vmSuper.actualTypeArguments[1]
                    if (state is Class<*>) {
                        map=this.javaClass.methods
                            .filter{it.parameterTypes.size ==1 && state.isAssignableFrom(it.parameterTypes[0])}
                            .associateBy { it.parameterTypes[0] }
                        lifecycleScope.launch {
                            viewModel.state.collect(this@BaseActivity::collect)
                        }
                    }
                }

            }

        }

    }
    private fun collect(state: IState?){
        map[state?.javaClass]?.invoke(this, state)
    }

}