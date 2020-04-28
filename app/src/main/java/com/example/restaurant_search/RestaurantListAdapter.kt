package com.example.restaurant_search

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.SearchYelpResQuery
import kotlinx.android.synthetic.main.item_restaurant.view.*

class RestaurantListAdapter(
    var restaurantListResults: MutableList<SearchYelpResQuery.Business?>,
    val onItemClick: (() -> Unit)? = null
): RecyclerView.Adapter<RestaurantListViewHolder>() {

    override fun getItemCount(): Int {
        return restaurantListResults.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RestaurantListViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val itemView = layoutInflater.inflate(R.layout.item_restaurant, parent, false)
        return RestaurantListViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: RestaurantListViewHolder, position: Int) {

        val item = restaurantListResults[position]
        holder.setupView(item)

        holder.itemView.apply {
            setOnClickListener {
                onItemClick?.invoke()
            }
        }

    }

}

class RestaurantListViewHolder(private val view: View): RecyclerView.ViewHolder(view) {

    fun setupView(item: SearchYelpResQuery.Business?) {

        view.text_view_restaurant_name.text = item?.name
        view.text_view_restaurant_address.text = item?.location?.address1 ?: "No Address Listed"
        view.text_view_restaurant_price.text = item?.price ?: "$"
        view.text_view_restaurant_phone.text = item?.display_phone ?: "No Phone # Listed"

    }
}