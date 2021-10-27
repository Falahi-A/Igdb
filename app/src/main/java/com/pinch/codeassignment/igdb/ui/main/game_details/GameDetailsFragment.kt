package com.pinch.codeassignment.igdb.ui.main.game_details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.pinch.codeassignment.igdb.R
import com.pinch.codeassignment.igdb.databinding.FragmentGameDetailsBinding
import com.pinch.codeassignment.igdb.domain.model.Game
import com.pinch.codeassignment.igdb.ui.base.BaseBindingFragment
import com.pinch.codeassignment.igdb.utils.Constants
import com.pinch.codeassignment.igdb.utils.buildImageUrl
import com.pinch.codeassignment.igdb.utils.loadImage
import com.smarteist.autoimageslider.SliderView
import dagger.hilt.android.AndroidEntryPoint
import java.lang.StringBuilder


/**
 * Present details of a game
 */

@AndroidEntryPoint
class GameDetailsFragment : BaseBindingFragment<FragmentGameDetailsBinding>() {

    private lateinit var game: Game

    private val args: GameDetailsFragmentArgs by navArgs()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        game = args.game
    }


    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentGameDetailsBinding =
        { layoutInflater, viewGroup, isAttached ->
            FragmentGameDetailsBinding.inflate(layoutInflater, viewGroup, isAttached)
        }

    override fun initView() {
        binding.apply {
            game.apply {
                // init game's image
                loadImage(
                    imgGameDetails,
                    buildImageUrl(
                        imageSize = Constants.IMAGE_COVER_SMALL_2X_SIZE,
                        imageId = imageId
                    )
                )
                // set name of game
                txtGameNameDetails.text = name
                //set followers of game
                txtGameFollowersDetails.text = getString(R.string.followers).plus(game.follows ?: 0)
                // set summary of game
                txtGameSummaryDetails.text = summary ?: ""
            }

        }

        initSliderView()
        initGenresView()
        initPlatformsView()
    }

    private fun initGenresView() {
        val genresBuilder = StringBuilder()
        game.genres?.map { genre ->
            genresBuilder.append(genre).append(", ")
        }
        binding.txtGameGenreDetails.text = getString(R.string.genres).plus(genresBuilder.toString())
    }

    private fun initPlatformsView() {
        val platformsBuilder = StringBuilder()
        game.platforms?.map { genre ->
            platformsBuilder.append(genre).append(", ")
        }
        binding.txtGamePlatformsDetails.text =
            getString(R.string.platforms).plus(platformsBuilder.toString())

    }

    // The sliderView shows screenshots
    private fun initSliderView() {

        game.apply {
            binding.apply {
                if (screenshots != null) {
                    binding.imgHolderDetails.visibility = View.GONE
                    screenshots.map { imgScreenshotId -> // building screenshot urls
                        buildImageUrl(
                            imageSize = Constants.IMAGE_SCREENSHOT_BIG_SIZE,
                            imageId = imgScreenshotId
                        )
                    }.also { screenshotsUrls ->
                        sliderGameScreenshotsDetails.apply {  // initialising sliderView
                            autoCycleDirection = SliderView.LAYOUT_DIRECTION_LTR
                            setSliderAdapter(SliderAdapter(screenshotsUrls))
                            scrollTimeInSec = 3
                            isAutoCycle = true
                            startAutoCycle()
                        }
                    }
                } else {
                    imgHolderDetails.visibility =
                        View.VISIBLE // It will present an image in case of no screenshots
                }
            }
        }

    }

}

