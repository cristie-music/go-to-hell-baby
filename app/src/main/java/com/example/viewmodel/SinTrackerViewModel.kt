package com.example.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.R
import com.example.data.GoodDeedResult
import com.example.data.SinCommitResult
import com.example.data.SinRepository
import com.example.model.AchievementRegistry
import com.example.model.AchievementUiModel
import com.example.model.DefaultSins
import com.example.model.DevilAdviceQuotes
import com.example.model.PraiseQuotes
import com.example.model.SinCategory
import com.example.model.SinItem
import com.example.model.SinRecord
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlin.random.Random

data class MilestoneDialogData(
  val imageResId: Int,
  val praiseQuote: String,
  val sinCount: Int,
  val points: Int,
  val remaining: Int
)

data class SinTrackerUiState(
  val totalPoints: Int = 0,
  val remainingToHell: Int = 10000,
  val progressFraction: Float = 0f,
  val totalSinsCount: Int = 0,
  val goodDeedsCount: Int = 0,
  val history: List<SinRecord> = emptyList(),
  val selectedCategory: SinCategory? = null,
  val searchQuery: String = "",
  val availableSins: List<SinItem> = DefaultSins.list,
  val milestoneEvent: MilestoneDialogData? = null,
  val goodDeedEvent: GoodDeedResult? = null,
  val isHellUnlocked: Boolean = false,
  val showHellWelcome: Boolean = false,
  val lastCommitFeedback: String? = null,
  val currentAdvice: String = DevilAdviceQuotes.list.first(),
  val showGoodDeedShockDialog: Boolean = false,
  val selectedNavTab: Int = 0, // 0 = Главная, 1 = Статистика, 2 = Достижения
  val achievements: List<AchievementUiModel> = emptyList(),
  val newlyUnlockedAchievement: AchievementUiModel? = null
)

class SinTrackerViewModel(application: Application) : AndroidViewModel(application) {

  private val repository = SinRepository(application)

  private val _selectedCategory = MutableStateFlow<SinCategory?>(null)
  private val _searchQuery = MutableStateFlow("")
  private val _milestoneEvent = MutableStateFlow<MilestoneDialogData?>(null)
  private val _goodDeedEvent = MutableStateFlow<GoodDeedResult?>(null)
  private val _showHellWelcome = MutableStateFlow(false)
  private val _lastFeedback = MutableStateFlow<String?>(null)
  private val _currentAdvice = MutableStateFlow(DevilAdviceQuotes.list.first())
  private val _showGoodDeedShockDialog = MutableStateFlow(false)
  private val _selectedNavTab = MutableStateFlow(0)
  private val _newlyUnlockedAchievement = MutableStateFlow<AchievementUiModel?>(null)

  private val allSinsList = MutableStateFlow(DefaultSins.list)
  private val previousUnlockedIds = mutableSetOf<String>()

  val uiState: StateFlow<SinTrackerUiState> = combine(
    repository.pointsFlow,
    repository.totalSinsCountFlow,
    repository.goodDeedsCountFlow,
    repository.historyFlow,
    _selectedCategory,
    _searchQuery,
    _milestoneEvent,
    _goodDeedEvent,
    _showHellWelcome,
    _lastFeedback,
    _currentAdvice,
    _showGoodDeedShockDialog,
    _selectedNavTab,
    _newlyUnlockedAchievement,
    allSinsList
  ) { args ->
    val points = args[0] as Int
    val count = args[1] as Int
    val deeds = args[2] as Int
    @Suppress("UNCHECKED_CAST")
    val history = args[3] as List<SinRecord>
    val category = args[4] as? SinCategory
    val search = args[5] as String
    val milestone = args[6] as? MilestoneDialogData
    val deedResult = args[7] as? GoodDeedResult
    val showHell = args[8] as Boolean
    val feedback = args[9] as? String
    val advice = args[10] as String
    val showShock = args[11] as Boolean
    val navTab = args[12] as Int
    val newAch = args[13] as? AchievementUiModel
    @Suppress("UNCHECKED_CAST")
    val rawSins = args[14] as List<SinItem>

    val remaining = (SinRepository.HELL_TARGET_POINTS - points).coerceAtLeast(0)
    val fraction = (points.toFloat() / SinRepository.HELL_TARGET_POINTS.toFloat()).coerceIn(0f, 1f)

    val categoryPoints = SinCategory.values().associateWith { cat ->
      history.filter { it.category == cat }.sumOf { it.points }
    }

    val achievements = AchievementRegistry.allAchievements.map { def ->
      val progress = def.evaluateProgress(count, points, deeds, categoryPoints)
      val isUnlocked = progress >= def.targetValue
      AchievementUiModel(
        id = def.id,
        title = def.title,
        description = def.description,
        emoji = def.emoji,
        type = def.type,
        currentProgress = progress.coerceAtMost(def.targetValue),
        targetProgress = def.targetValue,
        isUnlocked = isUnlocked
      )
    }

    val filteredSins = rawSins.filter { item ->
      val matchesCategory = category == null || item.category == category
      val matchesSearch = search.isBlank() ||
        item.title.contains(search, ignoreCase = true) ||
        item.category.titleRu.contains(search, ignoreCase = true) ||
        item.funnyQuote.contains(search, ignoreCase = true)
      matchesCategory && matchesSearch
    }

    SinTrackerUiState(
      totalPoints = points,
      remainingToHell = remaining,
      progressFraction = fraction,
      totalSinsCount = count,
      goodDeedsCount = deeds,
      history = history,
      selectedCategory = category,
      searchQuery = search,
      availableSins = filteredSins,
      milestoneEvent = milestone,
      goodDeedEvent = deedResult,
      isHellUnlocked = points >= SinRepository.HELL_TARGET_POINTS,
      showHellWelcome = showHell,
      lastCommitFeedback = feedback,
      currentAdvice = advice,
      showGoodDeedShockDialog = showShock,
      selectedNavTab = navTab,
      achievements = achievements,
      newlyUnlockedAchievement = newAch
    )
  }.stateIn(
    scope = viewModelScope,
    started = SharingStarted.WhileSubscribed(5000),
    initialValue = SinTrackerUiState()
  )

  fun selectNavTab(tabIndex: Int) {
    _selectedNavTab.value = tabIndex
  }

  fun refreshAdvice() {
    val current = _currentAdvice.value
    val available = DevilAdviceQuotes.list.filter { it != current }
    _currentAdvice.value = available.randomOrNull() ?: DevilAdviceQuotes.list.random()
  }

  fun onAngelButtonClicked() {
    // Show shocked 14.png devil girl dialog first!
    _showGoodDeedShockDialog.value = true
  }

  fun dismissGoodDeedShockDialog() {
    _showGoodDeedShockDialog.value = false
  }

  fun stayWithDevil() {
    _showGoodDeedShockDialog.value = false
    _lastFeedback.value = "Вот и умничка! Чертовка снова счастлива и ждёт новых грехов! 😈💋"
  }

  fun confirmGoodDeed() {
    _showGoodDeedShockDialog.value = false
    val deedResult = repository.redeemGoodDeed(fullReset = true)
    _goodDeedEvent.value = deedResult
  }

  fun dismissGoodDeed() {
    _goodDeedEvent.value = null
  }

  fun selectCategory(category: SinCategory?) {
    _selectedCategory.value = category
  }

  fun updateSearchQuery(query: String) {
    _searchQuery.value = query
  }

  fun commitSin(item: SinItem) {
    val result: SinCommitResult = repository.commitSin(item)

    // Feedback banner
    _lastFeedback.value = "Грех засчитан (+${item.points})! До Ада осталось ${result.remainingToHell} очков."

    // Rotate devil advice periodically
    if (Random.nextFloat() < 0.5f) {
      refreshAdvice()
    }

    // Milestone every 5 sins: Randomly pick between 2.png and 6.png
    if (result.isMilestoneTriggered) {
      val imageRes = if (Random.nextBoolean()) {
        R.drawable.demon_praise_fangs // 2.png
      } else {
        R.drawable.demon_praise_split // 6.png
      }
      val randomQuote = PraiseQuotes.quotes.random()

      _milestoneEvent.value = MilestoneDialogData(
        imageResId = imageRes,
        praiseQuote = randomQuote,
        sinCount = result.milestoneSinCount,
        points = result.totalPoints,
        remaining = result.remainingToHell
      )
    }

    // Check if user just reached 10,000 points
    if (result.totalPoints >= SinRepository.HELL_TARGET_POINTS &&
      result.totalPoints - item.points < SinRepository.HELL_TARGET_POINTS
    ) {
      _showHellWelcome.value = true
    }
  }

  fun addCustomSin(title: String, points: Int, category: SinCategory) {
    val newSin = SinItem(
      id = "custom_${System.currentTimeMillis()}",
      title = title,
      category = category,
      points = points,
      funnyQuote = "Твой личный авторский грех! Чертовка восхищена такой изобретательностью!",
      emoji = "😈"
    )
    val current = allSinsList.value.toMutableList()
    current.add(0, newSin)
    allSinsList.value = current
    commitSin(newSin)
  }

  fun dismissMilestone() {
    _milestoneEvent.value = null
  }

  fun dismissHellWelcome() {
    _showHellWelcome.value = false
  }

  fun clearFeedback() {
    _lastFeedback.value = null
  }

  fun dismissAchievementNotification() {
    _newlyUnlockedAchievement.value = null
  }

  fun deleteHistoryItem(id: String) {
    repository.removeSinRecord(id)
  }

  fun getRankTitle(points: Int): String {
    return when {
      points < 1000 -> "Почти святой 😇 (Скучно...)"
      points < 2500 -> "Грешник-первогодка 😈"
      points < 5000 -> "Любитель погорячее 🔥"
      points < 7500 -> "Почётный завсегдатай Чистилища 🍷"
      points < 10000 -> "VIP-кандидат на сковородку 👑"
      else -> "🔥 ВЛАДЫКА ПРЕИСПОДНЕЙ 🔥"
    }
  }
}
