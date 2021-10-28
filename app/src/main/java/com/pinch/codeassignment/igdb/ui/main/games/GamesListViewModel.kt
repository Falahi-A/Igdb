package com.pinch.codeassignment.igdb.ui.main.games

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pinch.codeassignment.igdb.domain.usecase.GetGamesUseCase
import com.pinch.codeassignment.igdb.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class GamesListViewModel @Inject constructor(private val getGamesUseCase: GetGamesUseCase) :
    ViewModel() {

    private val _games = MutableLiveData<GamesListViewState>()
    val games: LiveData<GamesListViewState> = _games

    fun getGames(isNetworkAvailable: Boolean) = getGamesUseCase(isNetworkAvailable).onEach { result ->

        when (result) {
            is Resource.Loading -> {
                _games.value = GamesListViewState(isLoading = true)
            }
            is Resource.Success -> {
                _games.value = GamesListViewState(gamesList = result.data ?: emptyList())
            }
            is Resource.Error -> {
                _games.value =
                    GamesListViewState(
                        gamesList = result.data ?: emptyList(),
                        error = result.message ?: "An unexpected error happened"
                    )
            }
        }

    }.launchIn(viewModelScope)

}