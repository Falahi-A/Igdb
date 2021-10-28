package com.pinch.codeassignment.igdb.ui.main.games

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.pinch.codeassignment.igdb.databinding.FragmentGamesListBinding
import com.pinch.codeassignment.igdb.domain.model.Game
import com.pinch.codeassignment.igdb.ui.base.BaseBindingFragment
import com.pinch.codeassignment.igdb.ui.main.MainActivity
import com.pinch.codeassignment.igdb.utils.Constants

import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import javax.inject.Named


/**
 * Present list of games
 */
@AndroidEntryPoint
class GamesListFragment : BaseBindingFragment<FragmentGamesListBinding>() {

    private val viewModel: GamesListViewModel by viewModels()


    private val adapter: GamesListAdapter by lazy {
        GamesListAdapter { game ->
            goToGameDetails(game)
        }
    }

    private fun goToGameDetails(game: Game) {
        val action =
            GamesListFragmentDirections.actionGamesListFragmentToGameDetailsFragment(game)
        findNavController().navigate(
            action
        )
    }

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentGamesListBinding =
        { layoutInflater, viewGroup, isAttached ->
            FragmentGamesListBinding.inflate(layoutInflater, viewGroup, isAttached)
        }

    override fun initView() {

        binding.recyclerGames.adapter = adapter
        viewModel.getGames((activity as MainActivity).isNetworkAvailable())

        refreshListener()



        viewModel.games.observe(viewLifecycleOwner, { viewState ->

            when {
                viewState.isLoading -> {
                    (activity as MainActivity).displayProgress(true)

                }
                viewState.error != "" -> {
                    (activity as MainActivity).displayProgress(false)
                    (activity as MainActivity).displayMessage(viewState.error)
                    adapter.submitList(viewState.gamesList)

                }
                viewState.gamesList.isNotEmpty() -> {
                    (activity as MainActivity).displayProgress(false)
                    adapter.submitList(viewState.gamesList)
                }

            }

        })

    }

    private fun refreshListener() {
        binding.swipeRefreshLayout.setOnRefreshListener {
            (activity as MainActivity).displayProgress(true)
            viewModel.getGames((activity as MainActivity).isNetworkAvailable())
            binding.swipeRefreshLayout.isRefreshing = false
        }
    }

}