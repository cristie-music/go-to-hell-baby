package com.example

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.example.data.SinRepository
import com.example.model.DefaultSins
import com.example.model.SinCategory
import com.example.model.SinItem
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [34])
class ExampleRobolectricTest {

  private lateinit var context: Context
  private lateinit var repository: SinRepository

  @Before
  fun setUp() {
    context = ApplicationProvider.getApplicationContext<Context>()
    repository = SinRepository(context)
    repository.resetAll()
  }

  @Test
  fun testAppNameIsCorrect() {
    val appName = context.getString(R.string.app_name)
    assertEquals("GO to Hell Baby", appName)
  }

  @Test
  fun testSinAccumulationAndCountdown() {
    val sampleSin = SinItem(
      id = "test_sin",
      title = "Тестовый грех",
      category = SinCategory.GLUTTONY,
      points = 500,
      funnyQuote = "Очень смешно",
      emoji = "🍔"
    )

    val result1 = repository.commitSin(sampleSin)
    assertEquals(500, result1.totalPoints)
    assertEquals(9500, result1.remainingToHell)
    assertFalse(result1.isMilestoneTriggered)

    val result2 = repository.commitSin(sampleSin)
    assertEquals(1000, result2.totalPoints)
    assertEquals(9000, result2.remainingToHell)
  }

  @Test
  fun testMilestoneTriggerEveryFiveSins() {
    val sampleSin = DefaultSins.list.first()

    for (i in 1..4) {
      val res = repository.commitSin(sampleSin)
      assertFalse("Sin $i should not trigger milestone", res.isMilestoneTriggered)
    }

    val res5 = repository.commitSin(sampleSin)
    assertTrue("5th sin must trigger milestone", res5.isMilestoneTriggered)
    assertEquals(5, res5.milestoneSinCount)
  }

  @Test
  fun testGoodDeedResetsSinPoints() {
    val sampleSin = DefaultSins.list.first()
    repository.commitSin(sampleSin)
    repository.commitSin(sampleSin)
    assertTrue(repository.pointsFlow.value > 0)

    val deedResult = repository.redeemGoodDeed(fullReset = true)
    assertEquals(0, repository.pointsFlow.value)
    assertEquals(1, deedResult.totalGoodDeeds)
    assertTrue(deedResult.message.contains("Ангелочек"))
  }

  @Test
  fun testAchievementsCountRequirements() {
    val sinAchievements = com.example.model.AchievementRegistry.allAchievements
      .filter { it.type == com.example.model.AchievementType.SIN }
    val deedAchievements = com.example.model.AchievementRegistry.allAchievements
      .filter { it.type == com.example.model.AchievementType.GOOD_DEED }

    assertTrue("Must have at least 15 sin achievements", sinAchievements.size >= 15)
    assertTrue("Must have at least 5 good deed achievements", deedAchievements.size >= 5)
  }

  @Test
  fun testDevilAdviceIsPresent() {
    val adviceList = com.example.model.DevilAdviceQuotes.list
    assertTrue("Devil advice list must have multiple quotes", adviceList.size >= 15)
  }
}

