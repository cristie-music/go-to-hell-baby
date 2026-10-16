package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DeleteOutline
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.SinCategory
import com.example.model.SinRecord
import com.example.ui.theme.DarkBorder
import com.example.ui.theme.DarkSurfaceCard
import com.example.ui.theme.DarkSurfaceElevated
import com.example.ui.theme.HellGold
import com.example.ui.theme.HellRed
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun SinHistorySection(
  history: List<SinRecord>,
  onDeleteRecord: (String) -> Unit,
  modifier: Modifier = Modifier
) {
  var selectedTab by remember { mutableStateOf(0) } // 0 = История, 1 = Анализ грехов

  Column(
    modifier = modifier
      .fillMaxWidth()
      .clip(RoundedCornerShape(20.dp))
      .background(DarkSurfaceCard)
      .border(1.dp, DarkBorder, RoundedCornerShape(20.dp))
      .padding(16.dp)
      .testTag("sin_history_section")
  ) {
    // Tab selector
    Row(
      modifier = Modifier
        .fillMaxWidth()
        .clip(RoundedCornerShape(12.dp))
        .background(DarkSurfaceElevated)
        .padding(4.dp),
      horizontalArrangement = Arrangement.SpaceBetween
    ) {
      Box(
        modifier = Modifier
          .weight(1f)
          .clip(RoundedCornerShape(10.dp))
          .background(if (selectedTab == 0) HellRed else Color.Transparent)
          .clickable { selectedTab = 0 }
          .padding(vertical = 8.dp),
        contentAlignment = Alignment.Center
      ) {
        Text(
          text = "Лента грехов (${history.size})",
          color = if (selectedTab == 0) Color.White else TextSecondary,
          fontSize = 12.sp,
          fontWeight = FontWeight.Bold
        )
      }
      Box(
        modifier = Modifier
          .weight(1f)
          .clip(RoundedCornerShape(10.dp))
          .background(if (selectedTab == 1) HellRed else Color.Transparent)
          .clickable { selectedTab = 1 }
          .padding(vertical = 8.dp),
        contentAlignment = Alignment.Center
      ) {
        Text(
          text = "Адский диагноз 😈",
          color = if (selectedTab == 1) Color.White else TextSecondary,
          fontSize = 12.sp,
          fontWeight = FontWeight.Bold
        )
      }
    }

    Spacer(modifier = Modifier.height(14.dp))

    if (selectedTab == 0) {
      // Timeline of sins
      if (history.isEmpty()) {
        Box(
          modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 24.dp),
          contentAlignment = Alignment.Center
        ) {
          Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = "🕊️", fontSize = 36.sp)
            Spacer(modifier = Modifier.height(6.dp))
            Text(
              text = "Пока ни одного греха!",
              color = TextPrimary,
              fontWeight = FontWeight.Bold,
              fontSize = 14.sp
            )
            Text(
              text = "Ты чист(а) как стеклышко... Нажми красную кнопку!",
              color = TextSecondary,
              fontSize = 12.sp
            )
          }
        }
      } else {
        Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
          history.take(15).forEach { record ->
            SinHistoryItemRow(
              record = record,
              onDelete = { onDeleteRecord(record.id) }
            )
          }
        }
      }
    } else {
      // Sin Stats / Breakdown
      SinBreakdownView(history = history)
    }
  }
}

@Composable
fun SinHistoryItemRow(
  record: SinRecord,
  onDelete: () -> Unit
) {
  val dateFormatted = remember(record.timestamp) {
    val sdf = SimpleDateFormat("HH:mm, dd MMM", Locale.getDefault())
    sdf.format(Date(record.timestamp))
  }

  Row(
    modifier = Modifier
      .fillMaxWidth()
      .clip(RoundedCornerShape(12.dp))
      .background(DarkSurfaceElevated)
      .padding(10.dp),
    verticalAlignment = Alignment.CenterVertically
  ) {
    Text(text = record.category.emoji, fontSize = 22.sp, modifier = Modifier.padding(end = 8.dp))

    Column(modifier = Modifier.weight(1f)) {
      Text(
        text = record.title,
        color = TextPrimary,
        fontSize = 13.sp,
        fontWeight = FontWeight.SemiBold
      )
      Row(verticalAlignment = Alignment.CenterVertically) {
        Text(
          text = record.category.titleRu,
          color = Color(record.category.badgeColor),
          fontSize = 11.sp
        )
        Text(text = " • $dateFormatted", color = TextMuted, fontSize = 10.sp)
      }
    }

    Text(
      text = "+${record.points}",
      color = HellRed,
      fontSize = 13.sp,
      fontWeight = FontWeight.Bold,
      modifier = Modifier.padding(horizontal = 6.dp)
    )

    IconButton(
      onClick = onDelete,
      modifier = Modifier.size(28.dp)
    ) {
      Icon(
        imageVector = Icons.Default.DeleteOutline,
        contentDescription = "Удалить грех",
        tint = TextMuted,
        modifier = Modifier.size(16.dp)
      )
    }
  }
}

@Composable
fun SinBreakdownView(history: List<SinRecord>) {
  if (history.isEmpty()) {
    Box(
      modifier = Modifier
        .fillMaxWidth()
        .padding(20.dp),
      contentAlignment = Alignment.Center
    ) {
      Text(text = "Соверши хотя бы один грех для постановки диагноза! 😈", color = TextSecondary, fontSize = 12.sp)
    }
    return
  }

  val totalPoints = history.sumOf { it.points }.coerceAtLeast(1)
  val categoryPoints = SinCategory.values().associateWith { cat ->
    history.filter { it.category == cat }.sumOf { it.points }
  }

  val topCategory = categoryPoints.maxByOrNull { it.value }?.key

  val diagnosis = when (topCategory) {
    SinCategory.GLUTTONY -> "Твой диагноз: Главный Ночной Дожорщик Преисподней 🌭"
    SinCategory.SLOTH -> "Твой диагноз: Мастер Лени и Серийный Прокрастинатор 🛋️"
    SinCategory.WRATH -> "Твой диагноз: Огнедышащий Вспыльчивый Дракон 🔥"
    SinCategory.PRIDE -> "Твой диагноз: Самовлюбленный Гений с Золотой Короной 👑"
    SinCategory.ENVY -> "Твой диагноз: Профессиональный Детектив по Чужой Жизни 👀"
    SinCategory.GREED -> "Твой диагноз: Рыцарь Маркетплейсов и Скидочных Купонов 💰"
    SinCategory.LUST -> "Твой диагноз: Сердцеед и Ночной Ностальгирующий Романтик 💋"
    null -> "Грехи равномерно распределены!"
  }

  Column {
    Box(
      modifier = Modifier
        .fillMaxWidth()
        .clip(RoundedCornerShape(12.dp))
        .background(HellGold.copy(alpha = 0.15f))
        .border(1.dp, HellGold.copy(alpha = 0.4f), RoundedCornerShape(12.dp))
        .padding(10.dp)
    ) {
      Text(
        text = diagnosis,
        color = HellGold,
        fontSize = 12.sp,
        fontWeight = FontWeight.Bold
      )
    }

    Spacer(modifier = Modifier.height(12.dp))

    SinCategory.values().forEach { cat ->
      val points = categoryPoints[cat] ?: 0
      val pct = (points.toFloat() / totalPoints.toFloat() * 100).toInt()
      if (points > 0) {
        Column(modifier = Modifier.padding(vertical = 4.dp)) {
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
          ) {
            Text(
              text = "${cat.emoji} ${cat.titleRu}",
              color = TextPrimary,
              fontSize = 12.sp
            )
            Text(
              text = "$points очков ($pct%)",
              color = Color(cat.badgeColor),
              fontSize = 11.sp,
              fontWeight = FontWeight.Bold
            )
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
                .fillMaxWidth(pct / 100f)
                .height(6.dp)
                .clip(CircleShape)
                .background(Color(cat.badgeColor))
            )
          }
        }
      }
    }
  }
}
