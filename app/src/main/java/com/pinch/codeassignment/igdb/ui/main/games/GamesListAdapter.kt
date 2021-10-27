package com.pinch.codeassignment.igdb.ui.main.games

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.pinch.codeassignment.igdb.R
import com.pinch.codeassignment.igdb.databinding.ItemGamesListViewBinding
import com.pinch.codeassignment.igdb.domain.model.Game
import com.pinch.codeassignment.igdb.ui.base.BaseViewHolder
import com.pinch.codeassignment.igdb.utils.Constants
import com.pinch.codeassignment.igdb.utils.buildImageUrl
import com.pinch.codeassignment.igdb.utils.loadImage

class GamesListAdapter(val onItemClick: (Game) -> Unit) :
    ListAdapter<Game, GamesListAdapter.GamesListViewHolder>(DifCallback) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GamesListViewHolder {
        return GamesListViewHolder(
            ItemGamesListViewBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: GamesListViewHolder, position: Int) {
        val game = getItem(position)
        holder.onBind(game)
    }


    inner class GamesListViewHolder(private val item: ItemGamesListViewBinding) :
        BaseViewHolder<Game>(item.root) {
        override fun onBind(obj: Game) {

            checkTextName(obj)
            obj.rating?.let {
                item.txtRatingItem.text = obj.rating.toString().substringBefore(".").plus("%")
            }

            obj.genres?.let {
                item.txtGenreItem.text = it.first()
            }

            loadImage(
                item.imgGameItem,
                buildImageUrl(
                    imageSize = Constants.IMAGE_COVER_SMALL_2X_SIZE,
                    imageId = obj.imageId
                )
            )
            item.root.setOnClickListener {
                onItemClick(obj)
            }
        }

        private fun checkTextName(obj: Game) {
            if (obj.name.length > Constants.MAX_STRING_LENGTH)
                item.txtGameNameItem.text =
                    obj.name.substring(0, Constants.MAX_STRING_LENGTH).plus(
                        item.root.context.getString(
                            R.string.line_dots
                        )
                    )
            else item.txtGameNameItem.text = obj.name
        }

    }

    object DifCallback : DiffUtil.ItemCallback<Game>() {
        override fun areItemsTheSame(oldItem: Game, newItem: Game): Boolean {
            return oldItem.id == newItem.id

        }

        override fun areContentsTheSame(oldItem: Game, newItem: Game): Boolean {
            return oldItem == newItem
        }

    }

}