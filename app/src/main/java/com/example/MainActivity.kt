package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.ui.screens.MainScreen
import com.example.ui.theme.MyApplicationTheme
import com.example.viewmodel.SinTrackerViewModel

class MainActivity : ComponentActivity() {
  private val viewModel: SinTrackerViewModel by viewModels()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    enableEdgeToEdge()
    setContent {
      MyApplicationTheme {
        val uiState by viewModel.uiState.collectAsStateWithLifecycle()

        MainScreen(
          uiState = uiState,
          onCommitSin = viewModel::commitSin,
          onAddCustomSin = viewModel::addCustomSin,
          onAngelButtonClick = viewModel::onAngelButtonClicked,
          onConfirmGoodDeed = viewModel::confirmGoodDeed,
          onStayWithDevil = viewModel::stayWithDevil,
          onDismissGoodDeedShock = viewModel::dismissGoodDeedShockDialog,
          onDeleteHistoryItem = viewModel::deleteHistoryItem,
          onSelectCategory = viewModel::selectCategory,
          onSearchQueryChange = viewModel::updateSearchQuery,
          onDismissMilestone = viewModel::dismissMilestone,
          onDismissGoodDeed = viewModel::dismissGoodDeed,
          onDismissHellWelcome = viewModel::dismissHellWelcome,
          onClearFeedback = viewModel::clearFeedback,
          onRefreshAdvice = viewModel::refreshAdvice,
          onSelectNavTab = viewModel::selectNavTab,
          getRankTitle = viewModel::getRankTitle
        )
      }
    }
  }
}
