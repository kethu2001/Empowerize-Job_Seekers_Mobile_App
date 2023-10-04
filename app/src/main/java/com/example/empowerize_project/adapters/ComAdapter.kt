package com.example.empowerize_project.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.empowerize_project.R
import com.example.empowerize_project.models.CompanyModel

class ComAdapter (private val comList:ArrayList<CompanyModel>) :
    RecyclerView.Adapter<ComAdapter.ViewHolder>() {

    private lateinit var mListener: onItemClickListener

    interface onItemClickListener{
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(clickListener: onItemClickListener){
        mListener = clickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.com_list_item, parent, false)
        return ViewHolder(itemView, mListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentCom =  comList[position]
        holder.tvComName.text = currentCom.comName
    }

    override fun getItemCount(): Int {
        return comList.size
    }

    class ViewHolder( itemView: View, clickListener: onItemClickListener) : RecyclerView.ViewHolder(itemView) {
        val tvComName : TextView = itemView.findViewById(R.id.tvComName)

        init {
            itemView.setOnClickListener {
                clickListener.onItemClick(adapterPosition)
            }
        }
    }

}