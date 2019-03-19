package com.ms.mvvm.interfaces

interface IRecyclerViewClickHandler<T> {
    fun onClick(viewModel: T)
}