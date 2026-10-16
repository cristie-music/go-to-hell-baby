package com.example.data

import android.content.Context
import android.content.SharedPreferences
import com.example.model.SinCategory
import com.example.model.SinItem
import com.example.model.SinRecord
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import org.json.JSONArray
import org.json.JSONObject
import java.util.UUID

data class SinCommitResult(
  val record: SinRecord,
  val totalPoints: Int,
  val remainingToHell: Int,
  val isMilestoneTriggered: Boolean,
  val milestoneSinCount: Int
)

data class GoodDeedResult(
  val previousPoints: Int,
  val pointsCleared: Int,
  val totalGoodDeeds: Int,
  val message: String
)

class SinRepository(context: Context) {

  private val prefs: SharedPreferences =
    context.getSharedPreferences("sin_tracker_prefs", Context.MODE_PRIVATE)

  private val _pointsFlow = MutableStateFlow(loadPoints())
  val pointsFlow: StateFlow<Int> = _pointsFlow.asStateFlow()

  private val _totalSinsCountFlow = MutableStateFlow(loadSinsCount())
  val totalSinsCountFlow: StateFlow<Int> = _totalSinsCountFlow.asStateFlow()

  private val _historyFlow = MutableStateFlow(loadHistory())
  val historyFlow: StateFlow<List<SinRecord>> = _historyFlow.asStateFlow()

  private val _goodDeedsCountFlow = MutableStateFlow(loadGoodDeedsCount())
  val goodDeedsCountFlow: StateFlow<Int> = _goodDeedsCountFlow.asStateFlow()

  companion object {
    const val HELL_TARGET_POINTS = 10000
    private const val KEY_POINTS = "key_points"
    private const val KEY_SINS_COUNT = "key_sins_count"
    private const val KEY_HISTORY = "key_history"
    private const val KEY_GOOD_DEEDS = "key_good_deeds"
  }

  private fun loadPoints(): Int = prefs.getInt(KEY_POINTS, 0)

  private fun loadSinsCount(): Int = prefs.getInt(KEY_SINS_COUNT, 0)

  private fun loadGoodDeedsCount(): Int = prefs.getInt(KEY_GOOD_DEEDS, 0)

  private fun loadHistory(): List<SinRecord> {
    val jsonString = prefs.getString(KEY_HISTORY, null) ?: return emptyList()
    val list = mutableListOf<SinRecord>()
    try {
      val array = JSONArray(jsonString)
      for (i in 0 until array.length()) {
        val obj = array.getJSONObject(i)
        val categoryName = obj.optString("category", SinCategory.GLUTTONY.name)
        val category = try {
          SinCategory.valueOf(categoryName)
        } catch (e: Exception) {
          SinCategory.GLUTTONY
        }
        list.add(
          SinRecord(
            id = obj.getString("id"),
            title = obj.getString("title"),
            category = category,
            points = obj.getInt("points"),
            timestamp = obj.getLong("timestamp"),
            funnyQuote = obj.optString("funnyQuote", "")
          )
        )
      }
    } catch (e: Exception) {
      e.printStackTrace()
    }
    return list
  }

  private fun saveHistory(list: List<SinRecord>) {
    try {
      val array = JSONArray()
      for (record in list) {
        val obj = JSONObject().apply {
          put("id", record.id)
          put("title", record.title)
          put("category", record.category.name)
          put("points", record.points)
          put("timestamp", record.timestamp)
          put("funnyQuote", record.funnyQuote)
        }
        array.put(obj)
      }
      prefs.edit().putString(KEY_HISTORY, array.toString()).apply()
      _historyFlow.value = list
    } catch (e: Exception) {
      e.printStackTrace()
    }
  }

  fun commitSin(item: SinItem): SinCommitResult {
    val currentPoints = loadPoints()
    val newPoints = currentPoints + item.points
    val newCount = loadSinsCount() + 1

    prefs.edit()
      .putInt(KEY_POINTS, newPoints)
      .putInt(KEY_SINS_COUNT, newCount)
      .apply()

    _pointsFlow.value = newPoints
    _totalSinsCountFlow.value = newCount

    val record = SinRecord(
      id = UUID.randomUUID().toString(),
      title = item.title,
      category = item.category,
      points = item.points,
      timestamp = System.currentTimeMillis(),
      funnyQuote = item.funnyQuote
    )

    val currentHistory = loadHistory().toMutableList()
    currentHistory.add(0, record) // Most recent first
    saveHistory(currentHistory)

    val remaining = (HELL_TARGET_POINTS - newPoints).coerceAtLeast(0)
    val isMilestone = (newCount % 5 == 0)

    return SinCommitResult(
      record = record,
      totalPoints = newPoints,
      remainingToHell = remaining,
      isMilestoneTriggered = isMilestone,
      milestoneSinCount = newCount
    )
  }

  fun removeSinRecord(id: String) {
    val currentHistory = loadHistory().toMutableList()
    val itemToRemove = currentHistory.find { it.id == id } ?: return
    currentHistory.remove(itemToRemove)
    saveHistory(currentHistory)

    val newPoints = (loadPoints() - itemToRemove.points).coerceAtLeast(0)
    prefs.edit().putInt(KEY_POINTS, newPoints).apply()
    _pointsFlow.value = newPoints
  }

  fun redeemGoodDeed(fullReset: Boolean = true): GoodDeedResult {
    val previousPoints = loadPoints()
    val pointsCleared = if (fullReset) previousPoints else 1500.coerceAtMost(previousPoints)
    val remainingPoints = (previousPoints - pointsCleared).coerceAtLeast(0)
    val deedsCount = loadGoodDeedsCount() + 1

    prefs.edit()
      .putInt(KEY_POINTS, remainingPoints)
      .putInt(KEY_GOOD_DEEDS, deedsCount)
      .apply()

    _pointsFlow.value = remainingPoints
    _goodDeedsCountFlow.value = deedsCount

    val message = if (fullReset) {
      "Ангелочек смахнул все твои грехи крылышком! Счёт обнулён. Но чертовка хитро улыбается: 'Ты вернёшься...' 😇✨"
    } else {
      "Сделано доброе дело! Списано $pointsCleared очков. Совесть временно чиста! 🕊️"
    }

    return GoodDeedResult(
      previousPoints = previousPoints,
      pointsCleared = pointsCleared,
      totalGoodDeeds = deedsCount,
      message = message
    )
  }

  fun resetAll() {
    prefs.edit().clear().apply()
    _pointsFlow.value = 0
    _totalSinsCountFlow.value = 0
    _historyFlow.value = emptyList()
    _goodDeedsCountFlow.value = 0
  }
}
