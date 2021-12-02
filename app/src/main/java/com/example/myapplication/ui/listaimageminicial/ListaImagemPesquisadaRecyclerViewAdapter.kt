package com.example.myapplication.ui.listaimageminicial

import com.example.myapplication.domain.ImagemPesquisada
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import com.example.myapplication.R

import com.example.myapplication.databinding.FragmentImagemItemBinding
import com.squareup.picasso.Picasso

/**
 * [RecyclerView.Adapter] that can display a [PlaceholderItem].
 * TODO: Replace the implementation with code for your data type.
 */
class ListaImagemPesquisadaRecyclerViewAdapter(
    private var listaNotasImg: List<ImagemPesquisada>,
    val funcaoParaClic:(Int)->Unit
) : RecyclerView.Adapter<ListaImagemPesquisadaRecyclerViewAdapter.ViewHolder>() {

    fun mudarLista(listaNotasImgNova: List<ImagemPesquisada>){
        listaNotasImg = listaNotasImgNova
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val minhaBindingView = FragmentImagemItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false)

        val imagemItemViewHolder = ViewHolder(minhaBindingView,funcaoParaClic)

        return imagemItemViewHolder

    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

            val item = listaNotasImg[position]
            Picasso.get().load(item.big)
                .centerCrop()
                .resize(580, 120)
                .error(R.drawable.ic_launcher_foreground)
                .into(holder.imgeView)

            holder.titulo.text = item.titulo
            holder.contentView.text = item.texto

    }

    override fun getItemCount(): Int = listaNotasImg.size

    inner class ViewHolder(binding: FragmentImagemItemBinding,funcaoDeClic:(Int)->Unit ) : RecyclerView.ViewHolder(binding.root) {
        val titulo: TextView = binding.titulo
        val contentView: TextView = binding.content
        val fundoDaNota: FrameLayout = binding.itemImgContainer
        val imgeView: ImageView = binding.imageView

        init{
            fundoDaNota.setOnClickListener {
                funcaoDeClic(bindingAdapterPosition)
            }
        }

        override fun toString(): String {
            return super.toString() + " '" + contentView.text + "'"
        }

    }




}