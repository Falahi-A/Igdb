package com.pinch.codeassignment.igdb.ui.main.games

import com.pinch.codeassignment.igdb.domain.model.Game

data class GamesListViewState(
    val isLoading: Boolean = false,
    val gamesList: List<Game> = emptyList(),
    val error: String = ""
)