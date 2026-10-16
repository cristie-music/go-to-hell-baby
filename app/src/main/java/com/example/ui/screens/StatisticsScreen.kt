package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
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
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material.icons.filled.PieChart
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.TrendingUp
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.SinRepository
import com.example.model.SinCategory
import com.example.model.SinRecord
import com.example.ui.theme.AngelGold
import com.example.ui.theme.AngelWhite
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

@Composable
fun StatisticsScreen(
  totalPoints: Int,
  totalSinsCount: Int,
  goodDeedsCount: Int,
  history: List<SinRecord>,
  modifier: Modifier = Modifier
) {
  val scrollState = rememberScrollState()

  // Calculate top 3 most frequent sins
  val topSins = remember(history) {
    history.groupBy { it.title }
      .map { (title, records) ->
        Triple(title, records.size, records.first().category)
      }
      .sortedByDescending { it.second }
      .take(3)
  }

  // Calculate ratio
  val totalActions = (totalSinsCount + goodDeedsCount).coerceAtLeast(1)
  val sinPercentage = (totalSinsCount.toFloat() / totalActions.toFloat() * 100).toInt()
  val deedPercentage = 100 - sinPercentage

  // Category breakdown
  val categoryCounts = remember(history) {
    SinCategory.values().associateWith { cat ->
      history.count { it.category == cat }
    }
  }

  Column(
    modifier = modifier
      .fillMaxSize()
      .background(DarkObsidian)
      .verticalScroll(scrollState)
      .padding(horizontal = 16.dp, vertical = 14.dp)
      .testTag("statistics_screen")
  ) {
    // Title
    Row(
      modifier = Modifier.fillMaxWidth(),
      horizontalArrangement = Arrangement.SpaceBetween,
      verticalAlignment = Alignment.CenterVertically
    ) {
      Column {
        Text(
          text = "АДСКАЯ СТАТИСТИКА 📊",
          color = HellGold,
          fontSize = 20.sp,
          fontWeight = FontWeight.Black,
          letterSpacing = 1.sp
        )
        Text(
          text = "Досье на твою грешную душу",
          color = TextSecondary,
          fontSize = 12.sp
        )
      }
      Box(
        modifier = Modifier
          .clip(CircleShape)
          .background(HellRed.copy(alpha = 0.2f))
          .padding(8.dp)
      ) {
        Icon(
          imageVector = Icons.Default.TrendingUp,
          contentDescription = "Статистика",
          tint = HellRed
        )
      }
    }

    Spacer(modifier = Modifier.height(16.dp))

    // 1. Уровень приближения к Преисподней
    Box(
      modifier = Modifier
        .fillMaxWidth()
        .clip(RoundedCornerShape(20.dp))
        .background(DarkSurfaceCard)
        .border(1.5.dp, HellRed, RoundedCornerShape(20.dp))
        .padding(16.dp)
        .testTag("hell_proximity_card")
    ) {
      Column {
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Text(
            text = "Уровень приближения к Преисподней",
            color = HellGold,
            fontSize = 13.sp,
            fontWeight = FontWeight.Bold
          )
          Text(
            text = "${(totalPoints.toFloat() / SinRepository.HELL_TARGET_POINTS * 100).toInt()}%",
            color = HellRed,
            fontSize = 16.sp,
            fontWeight = FontWeight.Black
          )
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Progress bar
        val progress = (totalPoints.toFloat() / SinRepository.HELL_TARGET_POINTS).coerceIn(0f, 1f)
        Box(
          modifier = Modifier
            .fillMaxWidth()
            .height(14.dp)
            .clip(CircleShape)
            .background(DarkSurfaceElevated)
        ) {
          Box(
            modifier = Modifier
              .fillMaxWidth(progress)
              .height(14.dp)
              .clip(CircleShape)
              .background(
                Brush.horizontalGradient(
                  colors = listOf(HellCrimson, HellRed, HellOrange, HellGold)
                )
              )
          )
        }

        Spacer(modifier = Modifier.height(8.dp))

        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween
        ) {
          Text(
            text = "Набрано: $totalPoints очков",
            color = TextPrimary,
            fontSize = 12.sp,
            fontWeight = FontWeight.SemiBold
          )
          Text(
            text = "До котла: ${(SinRepository.HELL_TARGET_POINTS - totalPoints).coerceAtLeast(0)}",
            color = HellOrange,
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold
          )
        }
      }
    }

    Spacer(modifier = Modifier.height(14.dp))

    // 2. Грехи vs Добрые дела карточки
    Row(
      modifier = Modifier.fillMaxWidth(),
      horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
      // Sins Card
      Box(
        modifier = Modifier
          .weight(1f)
          .clip(RoundedCornerShape(18.dp))
          .background(DarkSurfaceCard)
          .border(1.dp, HellRed.copy(alpha = 0.5f), RoundedCornerShape(18.dp))
          .padding(14.dp)
          .testTag("total_sins_stat_card")
      ) {
        Column {
          Text(text = "🔥 Грехопадений", color = TextSecondary, fontSize = 11.sp)
          Spacer(modifier = Modifier.height(4.dp))
          Text(
            text = "$totalSinsCount",
            color = HellRed,
            fontSize = 24.sp,
            fontWeight = FontWeight.Black
          )
          Text(text = "актов искушения", color = TextMuted, fontSize = 10.sp)
        }
      }

      // Deeds Card
      Box(
        modifier = Modifier
          .weight(1f)
          .clip(RoundedCornerShape(18.dp))
          .background(DarkSurfaceCard)
          .border(1.dp, AngelGold.copy(alpha = 0.5f), RoundedCornerShape(18.dp))
          .padding(14.dp)
          .testTag("good_deeds_stat_card")
      ) {
        Column {
          Text(text = "🕊️ Срывов в добро", color = TextSecondary, fontSize = 11.sp)
          Spacer(modifier = Modifier.height(4.dp))
          Text(
            text = "$goodDeedsCount",
            color = AngelGold,
            fontSize = 24.sp,
            fontWeight = FontWeight.Black
          )
          Text(text = "попыток искупления", color = TextMuted, fontSize = 10.sp)
        }
      }
    }

    Spacer(modifier = Modifier.height(14.dp))

    // 3. Индекс Греховности vs Святости (Диаграмма соотношения)
    Box(
      modifier = Modifier
        .fillMaxWidth()
        .clip(RoundedCornerShape(20.dp))
        .background(DarkSurfaceCard)
        .border(1.dp, DarkBorder, RoundedCornerShape(20.dp))
        .padding(16.dp)
        .testTag("ratio_diagram_card")
    ) {
      Column {
        Text(
          text = "Индекс Греховности vs Святости",
          color = TextPrimary,
          fontSize = 14.sp,
          fontWeight = FontWeight.Bold
        )
        Text(
          text = "Процентное соотношение твоих жизненных выборов",
          color = TextSecondary,
          fontSize = 11.sp
        )

        Spacer(modifier = Modifier.height(12.dp))

        // Ratio Bar
        Row(
          modifier = Modifier
            .fillMaxWidth()
            .height(20.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(DarkSurfaceElevated)
        ) {
          if (sinPercentage > 0) {
            Box(
              modifier = Modifier
                .fillMaxHeight()
                .weight(sinPercentage.toFloat().coerceAtLeast(1f))
                .background(HellRed),
              contentAlignment = Alignment.Center
            ) {
              if (sinPercentage > 15) {
                Text(text = "$sinPercentage%", color = Color.White, fontSize = 10.sp, fontWeight = FontWeight.Black)
              }
            }
          }
          if (deedPercentage > 0) {
            Box(
              modifier = Modifier
                .fillMaxHeight()
                .weight(deedPercentage.toFloat().coerceAtLeast(1f))
                .background(AngelGold),
              contentAlignment = Alignment.Center
            ) {
              if (deedPercentage > 15) {
                Text(text = "$deedPercentage%", color = Color.Black, fontSize = 10.sp, fontWeight = FontWeight.Black)
              }
            }
          }
        }

        Spacer(modifier = Modifier.height(8.dp))

        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween
        ) {
          Row(verticalAlignment = Alignment.CenterVertically) {
            Box(modifier = Modifier.size(10.dp).clip(CircleShape).background(HellRed))
            Spacer(modifier = Modifier.width(6.dp))
            Text(text = "Греховность: $sinPercentage%", color = HellRed, fontSize = 12.sp, fontWeight = FontWeight.Bold)
          }

          Row(verticalAlignment = Alignment.CenterVertically) {
            Box(modifier = Modifier.size(10.dp).clip(CircleShape).background(AngelGold))
            Spacer(modifier = Modifier.width(6.dp))
            Text(text = "Святость: $deedPercentage%", color = AngelGold, fontSize = 12.sp, fontWeight = FontWeight.Bold)
          }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Ironic conclusion
        Text(
          text = when {
            sinPercentage >= 90 -> "😈 Вердикт: Чертовка аплодирует стоя! Ты абсолютный грешник!"
            sinPercentage >= 60 -> "🍷 Вердикт: Балансируешь на грани, но огонь манит сильнее."
            sinPercentage >= 40 -> "⚖️ Вердикт: Ни то, ни сё. В Аду не любят половинчатых!"
            else -> "😇 Вердикт: Осторожно, нимб начинает светиться! Срочно согреши!"
          },
          color = TextSecondary,
          fontSize = 11.sp,
          fontStyle = androidx.compose.ui.text.font.FontStyle.Italic
        )
      }
    }

    Spacer(modifier = Modifier.height(14.dp))

    // 4. Топ-3 самых частых грехов (Пьедестал)
    Box(
      modifier = Modifier
        .fillMaxWidth()
        .clip(RoundedCornerShape(20.dp))
        .background(DarkSurfaceCard)
        .border(1.dp, DarkBorder, RoundedCornerShape(20.dp))
        .padding(16.dp)
        .testTag("top_sins_card")
    ) {
      Column {
        Row(verticalAlignment = Alignment.CenterVertically) {
          Icon(
            imageVector = Icons.Default.EmojiEvents,
            contentDescription = "Топ грехов",
            tint = HellGold,
            modifier = Modifier.size(20.dp)
          )
          Spacer(modifier = Modifier.width(8.dp))
          Text(
            text = "Топ-3 любимых порока",
            color = HellGold,
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold
          )
        }

        Spacer(modifier = Modifier.height(10.dp))

        if (topSins.isEmpty()) {
          Text(
            text = "Топ пуст! Соверши первые грехи, чтобы выявить любимые слабости! 😈",
            color = TextMuted,
            fontSize = 12.sp
          )
        } else {
          topSins.forEachIndexed { index, (title, count, cat) ->
            val medal = when (index) {
              0 -> "🥇"
              1 -> "🥈"
              else -> "🥉"
            }
            Row(
              modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 5.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(DarkSurfaceElevated)
                .padding(10.dp),
              verticalAlignment = Alignment.CenterVertically
            ) {
              Text(text = medal, fontSize = 20.sp, modifier = Modifier.padding(end = 8.dp))
              Column(modifier = Modifier.weight(1f)) {
                Text(
                  text = title,
                  color = TextPrimary,
                  fontSize = 13.sp,
                  fontWeight = FontWeight.SemiBold
                )
                Text(
                  text = "${cat.emoji} ${cat.titleRu}",
                  color = Color(cat.badgeColor),
                  fontSize = 11.sp
                )
              }
              Box(
                modifier = Modifier
                  .clip(CircleShape)
                  .background(HellRed.copy(alpha = 0.2f))
                  .padding(horizontal = 8.dp, vertical = 4.dp)
              ) {
                Text(
                  text = "$count раз",
                  color = HellRed,
                  fontSize = 12.sp,
                  fontWeight = FontWeight.Bold
                )
              }
            }
          }
        }
      }
    }

    Spacer(modifier = Modifier.height(14.dp))

    // 5. Карта 7 смертных грехов (Гистограмма по категориям)
    Box(
      modifier = Modifier
        .fillMaxWidth()
        .clip(RoundedCornerShape(20.dp))
        .background(DarkSurfaceCard)
        .border(1.dp, DarkBorder, RoundedCornerShape(20.dp))
        .padding(16.dp)
        .testTag("categories_chart_card")
    ) {
      Column {
        Text(
          text = "Карта 7 смертных грехов",
          color = TextPrimary,
          fontSize = 14.sp,
          fontWeight = FontWeight.Bold
        )
        Text(
          text = "Распределение по видам искушений",
          color = TextSecondary,
          fontSize = 11.sp
        )

        Spacer(modifier = Modifier.height(12.dp))

        val maxCategoryCount = categoryCounts.values.maxOrNull()?.coerceAtLeast(1) ?: 1

        SinCategory.values().forEach { cat ->
          val count = categoryCounts[cat] ?: 0
          val fraction = count.toFloat() / maxCategoryCount.toFloat()

          Column(modifier = Modifier.padding(vertical = 4.dp)) {
            Row(
              modifier = Modifier.fillMaxWidth(),
              horizontalArrangement = Arrangement.SpaceBetween
            ) {
              Text(text = "${cat.emoji} ${cat.titleRu}", color = TextPrimary, fontSize = 12.sp)
              Text(text = "$count раз", color = Color(cat.badgeColor), fontSize = 11.sp, fontWeight = FontWeight.Bold)
            }
            Spacer(modifier = Modifier.height(4.dp))
            Box(
              modifier = Modifier
                .fillMaxWidth()
                .height(6.dp)
                .clip(CircleShape)
                .background(DarkSurfaceElevated)
            ) {
              Box(
                modifier = Modifier
                  .fillMaxWidth(fraction.coerceIn(0.02f, 1f))
                  .height(6.dp)
                  .clip(CircleShape)
                  .background(Color(cat.badgeColor))
              )
            }
          }
        }
      }
    }

    Spacer(modifier = Modifier.height(24.dp))
  }
}
