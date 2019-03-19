package com.ms.mvvm.base

import androidx.databinding.DataBindingUtil
import androidx.databinding.ObservableArrayList
import androidx.databinding.ObservableList
import androidx.databinding.ViewDataBinding
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ms.mvvm.interfaces.IRecyclerViewClickHandler
import com.ms.mvvm.interfaces.IRecyclerViewLongClickHandler
import java.lang.ref.WeakReference

class BaseRecyclerViewAdapter<T : BaseRecyclerViewItemViewModel>(items: Collection<T>) :
    RecyclerView.Adapter<BaseRecyclerViewAdapter.ViewHolder>(),
    View.OnClickListener, View.OnLongClickListener {

    private lateinit var list: ObservableList<T>
    private var inflater: LayoutInflater? = null
    private val ITEM_MODEL = -124
    private lateinit var clickHandler: IRecyclerViewClickHandler<T>
    private lateinit var longClickHandler: IRecyclerViewLongClickHandler<T>
    var onListChangedCallback: WeakReferenceOnListChangedCallback<T>

    val TAG: String = "BaseRecyclerViewAdapter"

    fun setItems(items: Collection<T>) {
        if (::list.isInitialized && list == items) {
            return
        }

        if (::list.isInitialized) {
            list.removeOnListChangedCallback(onListChangedCallback)
            notifyItemRangeRemoved(0, list.size)
        }

        if (items is ObservableList<T>) {
            list = items as ObservableList<T>
            notifyItemRangeInserted(0, list.size)
            list.addOnListChangedCallback(onListChangedCallback)
        } else if (::list.isInitialized) {
            list = ObservableArrayList()
            list.addOnListChangedCallback(onListChangedCallback)
            list.addAll(items)
        } else {
            list.clear()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        if (inflater == null) {
            inflater = LayoutInflater.from(parent.getContext())
        }

        val binding = DataBindingUtil.inflate<ViewDataBinding>(inflater!!, viewType, parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return if (!::list.isInitialized) 0 else list.size
    }

    override fun getItemViewType(position: Int): Int {
        return list[position].getLayoutId()
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val item = list[position]
        viewHolder.binding.setVariable(item.getBindingVariable(), item) // set variable name that are needed by the view
        viewHolder.binding.root.setTag(ITEM_MODEL, item)
        viewHolder.binding.root.setOnClickListener(this)
        viewHolder.binding.root.setOnLongClickListener(this)
        viewHolder.binding.executePendingBindings()
    }

    override fun onClick(v: View?) {
        if (::clickHandler.isInitialized) {
            val item = v?.getTag(ITEM_MODEL) as T
            clickHandler.onClick(item)
        }
    }

    override fun onLongClick(v: View?): Boolean {
        if (::longClickHandler.isInitialized) {
            val item = v?.getTag(ITEM_MODEL) as T
            longClickHandler.onLongClick(item)
            return true
        }
        return false
    }

    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
        if (::list.isInitialized) {
            list.removeOnListChangedCallback(onListChangedCallback)
        }
    }

    class ViewHolder(binding: ViewDataBinding) : RecyclerView.ViewHolder(binding.root) {
        var binding: ViewDataBinding

        init {
            this.binding = binding
        }
    }

    class WeakReferenceOnListChangedCallback<T : BaseRecyclerViewItemViewModel>(bindingRecyclerViewAdapter: BaseRecyclerViewAdapter<T>) :
        ObservableList.OnListChangedCallback<ObservableList<T>>() {

        override fun onChanged(p0: ObservableList<T>?) {
            val adapter = adapterReference.get()
            adapter?.notifyDataSetChanged()
        }

        override fun onItemRangeRemoved(p0: ObservableList<T>?, positionStart: Int, itemCount: Int) {
            val adapter = adapterReference.get()
            adapter?.notifyItemRangeRemoved(positionStart, itemCount)
        }

        override fun onItemRangeMoved(p0: ObservableList<T>?, fromPosition: Int, toPosition: Int, itemCount: Int) {
            val adapter = adapterReference.get()
            adapter?.notifyItemMoved(fromPosition, toPosition)
        }

        override fun onItemRangeInserted(p0: ObservableList<T>?, positionStart: Int, itemCount: Int) {
            val adapter = adapterReference.get()
            adapter?.notifyItemRangeInserted(positionStart, itemCount)
        }

        override fun onItemRangeChanged(p0: ObservableList<T>?, positionStart: Int, itemCount: Int) {
            val adapter = adapterReference.get()
            adapter?.notifyItemRangeChanged(positionStart, itemCount)
        }


        var adapterReference: WeakReference<BaseRecyclerViewAdapter<T>>

        init {
            this.adapterReference = WeakReference(bindingRecyclerViewAdapter)
        }
    }

    init {
        this.onListChangedCallback = WeakReferenceOnListChangedCallback(this)
        setItems(items)
    }

    fun setClickHandler(clickHandlerI: IRecyclerViewClickHandler<T>) {
        this.clickHandler = clickHandlerI
    }

    fun setLongClickHandler(clickHandlerI: IRecyclerViewLongClickHandler<T>) {
        this.longClickHandler = clickHandlerI
    }
}
