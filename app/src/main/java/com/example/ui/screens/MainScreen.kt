package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material.icons.filled.QueryStats
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.SinCategory
import com.example.model.SinItem
import com.example.ui.components.AngelActionButton
import com.example.ui.components.DevilActionButton
import com.example.ui.components.DevilAdviceCard
import com.example.ui.components.HellProgressBar
import com.example.ui.components.SinHistorySection
import com.example.ui.dialogs.GoodDeedDialog
import com.example.ui.dialogs.GoodDeedShockDialog
import com.example.ui.dialogs.HellWelcomeDialog
import com.example.ui.dialogs.MilestonePraiseDialog
import com.example.ui.dialogs.SinSelectionSheet
import com.example.ui.theme.AngelGold
import com.example.ui.theme.DarkBorder
import com.example.ui.theme.DarkObsidian
import com.example.ui.theme.DarkSurfaceCard
import com.example.ui.theme.DarkSurfaceElevated
import com.example.ui.theme.DarkVelvet
import com.example.ui.theme.HellCrimson
import com.example.ui.theme.HellGold
import com.example.ui.theme.HellOrange
import com.example.ui.theme.HellRed
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary
import com.example.viewmodel.SinTrackerUiState
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
  uiState: SinTrackerUiState,
  onCommitSin: (SinItem) -> Unit,
  onAddCustomSin: (String, Int, SinCategory) -> Unit,
  onAngelButtonClick: () -> Unit,
  onConfirmGoodDeed: () -> Unit,
  onStayWithDevil: () -> Unit,
  onDismissGoodDeedShock: () -> Unit,
  onDeleteHistoryItem: (String) -> Unit,
  onSelectCategory: (SinCategory?) -> Unit,
  onSearchQueryChange: (String) -> Unit,
  onDismissMilestone: () -> Unit,
  onDismissGoodDeed: () -> Unit,
  onDismissHellWelcome: () -> Unit,
  onClearFeedback: () -> Unit,
  onRefreshAdvice: () -> Unit,
  onSelectNavTab: (Int) -> Unit,
  getRankTitle: (Int) -> String,
  modifier: Modifier = Modifier
) {
  var showSinSheet by remember { mutableStateOf(false) }
  val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
  val scope = rememberCoroutineScope()

  // Auto-dismiss feedback banner after 4 seconds
  LaunchedEffect(uiState.lastCommitFeedback) {
    if (uiState.lastCommitFeedback != null) {
      delay(4000)
      onClearFeedback()
    }
  }

  Scaffold(
    modifier = modifier
      .fillMaxSize()
      .testTag("main_screen"),
    containerColor = DarkObsidian,
    bottomBar = {
      NavigationBar(
        containerColor = DarkVelvet,
        tonalElevation = 8.dp,
        modifier = Modifier
          .border(1.dp, DarkBorder)
          .testTag("bottom_nav_bar")
      ) {
        NavigationBarItem(
          selected = uiState.selectedNavTab == 0,
          onClick = { onSelectNavTab(0) },
          icon = {
            Icon(
              imageVector = Icons.Default.LocalFireDepartment,
              contentDescription = "Главная"
            )
          },
          label = { Text("Главная", fontSize = 11.sp, fontWeight = FontWeight.Bold) },
          colors = NavigationBarItemDefaults.colors(
            selectedIconColor = HellRed,
            selectedTextColor = HellRed,
            indicatorColor = HellRed.copy(alpha = 0.2f),
            unselectedIconColor = TextMuted,
            unselectedTextColor = TextMuted
          )
        )

        NavigationBarItem(
          selected = uiState.selectedNavTab == 1,
          onClick = { onSelectNavTab(1) },
          icon = {
            Icon(
              imageVector = Icons.Default.QueryStats,
              contentDescription = "Статистика"
            )
          },
          label = { Text("Статистика", fontSize = 11.sp, fontWeight = FontWeight.Bold) },
          colors = NavigationBarItemDefaults.colors(
            selectedIconColor = HellGold,
            selectedTextColor = HellGold,
            indicatorColor = HellGold.copy(alpha = 0.2f),
            unselectedIconColor = TextMuted,
            unselectedTextColor = TextMuted
          )
        )

        NavigationBarItem(
          selected = uiState.selectedNavTab == 2,
          onClick = { onSelectNavTab(2) },
          icon = {
            Icon(
              imageVector = Icons.Default.EmojiEvents,
              contentDescription = "Достижения"
            )
          },
          label = { Text("Достижения", fontSize = 11.sp, fontWeight = FontWeight.Bold) },
          colors = NavigationBarItemDefaults.colors(
            selectedIconColor = HellOrange,
            selectedTextColor = HellOrange,
            indicatorColor = HellOrange.copy(alpha = 0.2f),
            unselectedIconColor = TextMuted,
            unselectedTextColor = TextMuted
          )
        )
      }
    }
  ) { innerPadding ->
    Box(
      modifier = Modifier
        .fillMaxSize()
        .background(
          Brush.verticalGradient(
            colors = listOf(
              DarkVelvet,
              DarkObsidian
            )
          )
        )
        .padding(innerPadding)
    ) {
      when (uiState.selectedNavTab) {
        0 -> {
          // Главная вкладка
          val scrollState = rememberScrollState()

          Column(
            modifier = Modifier
              .fillMaxSize()
              .verticalScroll(scrollState)
              .padding(horizontal = 16.dp, vertical = 12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
          ) {
            // Top App Bar Header
            Row(
              modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp),
              horizontalArrangement = Arrangement.SpaceBetween,
              verticalAlignment = Alignment.CenterVertically
            ) {
              Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                  modifier = Modifier
                    .size(36.dp)
                    .clip(CircleShape)
                    .background(HellRed.copy(alpha = 0.2f)),
                  contentAlignment = Alignment.Center
                ) {
                  Text(text = "😈", fontSize = 20.sp)
                }
                Spacer(modifier = Modifier.width(10.dp))
                Column {
                  Text(
                    text = "GO TO HELL BABY",
                    color = HellGold,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Black,
                    letterSpacing = 1.sp
                  )
                  Text(
                    text = "Твой путеводитель в Преисподнюю",
                    color = TextSecondary,
                    fontSize = 11.sp
                  )
                }
              }

              // Good deeds counter pill
              if (uiState.goodDeedsCount > 0) {
                Box(
                  modifier = Modifier
                    .clip(RoundedCornerShape(12.dp))
                    .background(Color(0xFF2E1A3C))
                    .border(1.dp, AngelGold, RoundedCornerShape(12.dp))
                    .padding(horizontal = 10.dp, vertical = 4.dp)
                ) {
                  Text(
                    text = "🕊️ Дел: ${uiState.goodDeedsCount}",
                    color = AngelGold,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold
                  )
                }
              }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Feedback banner when sin is committed
            AnimatedVisibility(
              visible = uiState.lastCommitFeedback != null,
              enter = fadeIn() + slideInVertically(),
              exit = fadeOut() + slideOutVertically()
            ) {
              uiState.lastCommitFeedback?.let { feedback ->
                Box(
                  modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 12.dp)
                    .clip(RoundedCornerShape(14.dp))
                    .background(HellRed.copy(alpha = 0.2f))
                    .border(1.dp, HellRed, RoundedCornerShape(14.dp))
                    .padding(12.dp)
                    .testTag("feedback_banner")
                ) {
                  Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                      imageVector = Icons.Default.Info,
                      contentDescription = "Инфо",
                      tint = HellGold,
                      modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                      text = feedback,
                      color = TextPrimary,
                      fontSize = 13.sp,
                      fontWeight = FontWeight.SemiBold
                    )
                  }
                }
              }
            }

            // Hell Progress Bar (Countdown to 10,000 points)
            HellProgressBar(
              points = uiState.totalPoints,
              remainingToHell = uiState.remainingToHell,
              progressFraction = uiState.progressFraction,
              rankTitle = getRankTitle(uiState.totalPoints)
            )

            Spacer(modifier = Modifier.height(14.dp))

            // "Советы от чертенка" (Devil's Advice Card)
            DevilAdviceCard(
              advice = uiState.currentAdvice,
              onRefreshAdvice = onRefreshAdvice
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Action Buttons Row: Center Devil Red Button + Angel White Button
            Box(
              modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp),
              contentAlignment = Alignment.Center
            ) {
              // Central Devil Red Button
              DevilActionButton(
                onClick = { showSinSheet = true }
              )

              // Angel White Button positioned to the side
              Box(
                modifier = Modifier
                  .align(Alignment.CenterEnd)
                  .padding(end = 4.dp)
              ) {
                AngelActionButton(
                  onClick = onAngelButtonClick
                )
              }
            }

            Spacer(modifier = Modifier.height(18.dp))

            // Sin History and Breakdown
            SinHistorySection(
              history = uiState.history,
              onDeleteRecord = onDeleteHistoryItem
            )

            Spacer(modifier = Modifier.height(28.dp))
          }
        }

        1 -> {
          // Раздел статистики
          StatisticsScreen(
            totalPoints = uiState.totalPoints,
            totalSinsCount = uiState.totalSinsCount,
            goodDeedsCount = uiState.goodDeedsCount,
            history = uiState.history
          )
        }

        2 -> {
          // Раздел достижений
          AchievementsScreen(
            achievements = uiState.achievements
          )
        }
      }
    }
  }

  // Sin Selection Sheet
  if (showSinSheet) {
    SinSelectionSheet(
      sheetState = sheetState,
      availableSins = uiState.availableSins,
      selectedCategory = uiState.selectedCategory,
      searchQuery = uiState.searchQuery,
      onCategorySelect = onSelectCategory,
      onSearchChange = onSearchQueryChange,
      onSinSelected = { sin ->
        onCommitSin(sin)
        scope.launch { sheetState.hide() }.invokeOnCompletion {
          showSinSheet = false
        }
      },
      onAddCustomSin = onAddCustomSin,
      onDismiss = { showSinSheet = false }
    )
  }

  // Good Deed Shock Dialog (Devil girl from 14.png reacting in dismay)
  if (uiState.showGoodDeedShockDialog) {
    GoodDeedShockDialog(
      onConfirmGoodDeed = onConfirmGoodDeed,
      onStayWithDevil = onStayWithDevil,
      onDismiss = onDismissGoodDeedShock
    )
  }

  // Milestone Praise Dialog (Every 5 sins committed)
  uiState.milestoneEvent?.let { milestone ->
    MilestonePraiseDialog(
      data = milestone,
      onDismiss = onDismissMilestone
    )
  }

  // Good Deed Dialog (Reset sins confirmation)
  uiState.goodDeedEvent?.let { deed ->
    GoodDeedDialog(
      result = deed,
      onDismiss = onDismissGoodDeed
    )
  }

  // Hell Welcome Dialog (10,000 points reached)
  if (uiState.showHellWelcome) {
    HellWelcomeDialog(
      onDismiss = onDismissHellWelcome
    )
  }
}
