package com.example.myapplication

import ImagemPesquisada
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView

import com.example.myapplication.placeholder.PlaceholderContent.PlaceholderItem
import com.example.myapplication.databinding.FragmentImagemItemBinding
import com.squareup.picasso.Picasso

/**
 * [RecyclerView.Adapter] that can display a [PlaceholderItem].
 * TODO: Replace the implementation with code for your data type.
 */
class MyListaImagemPesquisadaRecyclerViewAdapter(
    private val values: List<ImagemPesquisada>,
    val funcaoParaClic:(Int)->Unit
) : RecyclerView.Adapter<MyListaImagemPesquisadaRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val imagemItemViewHolder = ViewHolder(
            FragmentImagemItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        ){ posicao->
            funcaoParaClic(posicao)
        }

        return imagemItemViewHolder

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

            val item = values[position]



            Picasso.get().load(item.big)
                .centerCrop().resize(580, 60)
                .error(R.drawable.ic_launcher_background)
                .into(holder.imgeView)

            holder.titulo.text = item.titulo
            holder.contentView.text = item.texto


    //holder.fundoDaNota.setOnClickListener {  }

    }

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(binding: FragmentImagemItemBinding,funcaoDeClic:(Int)->Unit ) : RecyclerView.ViewHolder(binding.root) {
        val titulo: TextView = binding.titulo
        val contentView: TextView = binding.content
        val fundoDaNota: FrameLayout = binding.itemImgContainer
        val imgeView: ImageView = binding.imageView

        init{
            fundoDaNota.setOnClickListener {
                //val l = bindingAdapterPosition
                funcaoDeClic(bindingAdapterPosition)
            }
        }

        override fun toString(): String {
            return super.toString() + " '" + contentView.text + "'"
        }

    }
    fun atualizaTamanhoViewHolder(){
        
    }



}