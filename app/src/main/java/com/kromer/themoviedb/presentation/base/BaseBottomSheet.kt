package com.kromer.themoviedb.presentation.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

abstract class BaseBottomSheet<T : ViewBinding> : BottomSheetDialogFragment() {

    private var _binding: T? = null
    val binding get() = _binding!!

    private fun inflate(
        clazz: Class<T>,
        inflater: LayoutInflater,
        container: ViewGroup?,
        attachToRoot: Boolean
    ) {
        _binding = clazz.getMethod(
            "inflate",
            LayoutInflater::class.java,
            ViewGroup::class.java,
            Boolean::class.java
        )
            .invoke(null, inflater, container, attachToRoot) as T
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        inflate(getVBClass(), inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    protected abstract fun getVBClass(): Class<T>
}
