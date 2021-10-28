package com.pinch.codeassignment.igdb.ui.main.games

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.pinch.codeassignment.igdb.R
import com.pinch.codeassignment.igdb.databinding.FragmentGamesListBinding
import com.pinch.codeassignment.igdb.domain.model.Game
import com.pinch.codeassignment.igdb.ui.base.BaseBindingFragment
import com.pinch.codeassignment.igdb.ui.main.MainActivity
import dagger.hilt.android.AndroidEntryPoint



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
                viewState.isLoading -> {  // Data is fetching
                    (activity as MainActivity).displayProgress(true)
                }
                viewState.error != "" && viewState.gamesList.isNotEmpty() -> {   // Internet is connected but an server error happened,
                    (activity as MainActivity).displayProgress(false) // so data is fetched from database
                    (activity as MainActivity).displayMessage(viewState.error)
                    adapter.submitList(viewState.gamesList)

                }
                viewState.error == "" && viewState.gamesList.isNotEmpty() -> {  // Internet is connected and there is no error
                    (activity as MainActivity).displayProgress(false)
                    adapter.submitList(viewState.gamesList)
                }
                viewState.error == "" && viewState.gamesList.isEmpty() -> {    // Internet is not connected and dataBase has no data.
                    (activity as MainActivity).displayProgress(false) // Usually this situation happens for first time you install
                    (activity as MainActivity).displayMessage(                    // the app and there is no internet connection.
                        requireContext().getString(R.string.internet_message)
                    )
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