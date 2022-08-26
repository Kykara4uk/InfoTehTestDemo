package com.example.infotehtestdemo.presentation.places

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.request.RequestOptions
import com.example.infotehtestdemo.databinding.PlacesListItemBinding
import com.example.infotehtestdemo.domain.models.PlaceListing
import com.example.infotehtestdemo.utils.Constants

class PlacesAdapter(
    var places: List<PlaceListing>,
    private val context: Context
) : RecyclerView.Adapter<PlacesAdapter.PlacesViewHolder>() {
    inner class PlacesViewHolder(val binding: PlacesListItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlacesViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = PlacesListItemBinding.inflate(layoutInflater, parent, false)
        return PlacesViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PlacesViewHolder, position: Int) {
        holder.binding.apply {
            name.text = places[position].name
            var requestOptions = RequestOptions()
            requestOptions = requestOptions
                .transforms(CenterCrop())
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)

            val picture = if (position%2==0)
                Constants.PLACES_PICTURE_1
            else
                Constants.PLACES_PICTURE_2

            Glide.with(context).load(picture).apply(requestOptions).into(imageView)

            holder.itemView.setOnClickListener{
                val action = PlacesFragmentDirections.actionPlacesFragmentToDetailFragment(places[position])
                Navigation.findNavController(holder.itemView).navigate(action)
            }
        }
    }

    override fun getItemCount(): Int = places.size


}