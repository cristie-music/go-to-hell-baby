package com.example.ui.screens

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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
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
import com.example.model.AchievementType
import com.example.model.AchievementUiModel
import com.example.ui.theme.AngelGold
import com.example.ui.theme.DarkBorder
import com.example.ui.theme.DarkObsidian
import com.example.ui.theme.DarkSurfaceCard
import com.example.ui.theme.DarkSurfaceElevated
import com.example.ui.theme.HellGold
import com.example.ui.theme.HellOrange
import com.example.ui.theme.HellRed
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary

@Composable
fun AchievementsScreen(
  achievements: List<AchievementUiModel>,
  modifier: Modifier = Modifier
) {
  var selectedTab by remember { mutableIntStateOf(0) } // 0 = Все, 1 = Грехи, 2 = Добро, 3 = Открытые

  val unlockedCount = remember(achievements) { achievements.count { it.isUnlocked } }

  val filteredAchievements = remember(achievements, selectedTab) {
    when (selectedTab) {
      1 -> achievements.filter { it.type == AchievementType.SIN }
      2 -> achievements.filter { it.type == AchievementType.GOOD_DEED }
      3 -> achievements.filter { it.isUnlocked }
      else -> achievements
    }
  }

  Column(
    modifier = modifier
      .fillMaxSize()
      .background(DarkObsidian)
      .padding(horizontal = 16.dp, vertical = 14.dp)
      .testTag("achievements_screen")
  ) {
    // Header
    Row(
      modifier = Modifier.fillMaxWidth(),
      horizontalArrangement = Arrangement.SpaceBetween,
      verticalAlignment = Alignment.CenterVertically
    ) {
      Column {
        Text(
          text = "ДОСТИЖЕНИЯ 🏆",
          color = HellGold,
          fontSize = 20.sp,
          fontWeight = FontWeight.Black,
          letterSpacing = 1.sp
        )
        Text(
          text = "Твои заслуги перед Раем и Адом",
          color = TextSecondary,
          fontSize = 12.sp
        )
      }

      // Progress badge
      Box(
        modifier = Modifier
          .clip(RoundedCornerShape(12.dp))
          .background(HellRed.copy(alpha = 0.2f))
          .border(1.dp, HellGold, RoundedCornerShape(12.dp))
          .padding(horizontal = 10.dp, vertical = 6.dp)
      ) {
        Text(
          text = "$unlockedCount / ${achievements.size}",
          color = HellGold,
          fontSize = 13.sp,
          fontWeight = FontWeight.Black
        )
      }
    }

    Spacer(modifier = Modifier.height(14.dp))

    // Filter tabs
    Row(
      modifier = Modifier
        .fillMaxWidth()
        .clip(RoundedCornerShape(12.dp))
        .background(DarkSurfaceElevated)
        .padding(4.dp),
      horizontalArrangement = Arrangement.SpaceBetween
    ) {
      AchievementTabItem(
        title = "Все (${achievements.size})",
        isSelected = selectedTab == 0,
        onClick = { selectedTab = 0 },
        modifier = Modifier.weight(1f)
      )
      AchievementTabItem(
        title = "Грехи (16)",
        isSelected = selectedTab == 1,
        onClick = { selectedTab = 1 },
        modifier = Modifier.weight(1f)
      )
      AchievementTabItem(
        title = "Добро (5)",
        isSelected = selectedTab == 2,
        onClick = { selectedTab = 2 },
        modifier = Modifier.weight(1f)
      )
      AchievementTabItem(
        title = "Мои ($unlockedCount)",
        isSelected = selectedTab == 3,
        onClick = { selectedTab = 3 },
        modifier = Modifier.weight(1f)
      )
    }

    Spacer(modifier = Modifier.height(14.dp))

    // Achievements list
    LazyColumn(
      modifier = Modifier
        .fillMaxWidth()
        .weight(1f)
        .testTag("achievements_list"),
      verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
      items(filteredAchievements, key = { it.id }) { item ->
        AchievementCardItem(item = item)
      }
      item {
        Spacer(modifier = Modifier.height(24.dp))
      }
    }
  }
}

@Composable
fun AchievementTabItem(
  title: String,
  isSelected: Boolean,
  onClick: () -> Unit,
  modifier: Modifier = Modifier
) {
  Box(
    modifier = modifier
      .clip(RoundedCornerShape(10.dp))
      .background(if (isSelected) HellRed else Color.Transparent)
      .clickable(onClick = onClick)
      .padding(vertical = 8.dp),
    contentAlignment = Alignment.Center
  ) {
    Text(
      text = title,
      color = if (isSelected) Color.White else TextSecondary,
      fontSize = 11.sp,
      fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium
    )
  }
}

@Composable
fun AchievementCardItem(item: AchievementUiModel) {
  val borderColor = when {
    item.isUnlocked && item.type == AchievementType.SIN -> HellRed
    item.isUnlocked && item.type == AchievementType.GOOD_DEED -> AngelGold
    else -> DarkBorder
  }

  val backgroundColor = if (item.isUnlocked) DarkSurfaceCard else Color(0xFF170D1D)

  Box(
    modifier = Modifier
      .fillMaxWidth()
      .clip(RoundedCornerShape(16.dp))
      .background(backgroundColor)
      .border(1.dp, borderColor, RoundedCornerShape(16.dp))
      .padding(14.dp)
      .testTag("achievement_card_${item.id}")
  ) {
    Row(
      modifier = Modifier.fillMaxWidth(),
      verticalAlignment = Alignment.CenterVertically
    ) {
      // Icon
      Box(
        modifier = Modifier
          .size(46.dp)
          .clip(CircleShape)
          .background(
            if (item.isUnlocked) {
              if (item.type == AchievementType.SIN) HellRed.copy(alpha = 0.2f) else AngelGold.copy(alpha = 0.2f)
            } else {
              DarkSurfaceElevated
            }
          ),
        contentAlignment = Alignment.Center
      ) {
        if (item.isUnlocked) {
          Text(text = item.emoji, fontSize = 24.sp)
        } else {
          Icon(
            imageVector = Icons.Default.Lock,
            contentDescription = "Заблокировано",
            tint = TextMuted,
            modifier = Modifier.size(20.dp)
          )
        }
      }

      Spacer(modifier = Modifier.width(12.dp))

      Column(modifier = Modifier.weight(1f)) {
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Text(
            text = item.title,
            color = if (item.isUnlocked) TextPrimary else TextSecondary,
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold
          )

          if (item.isUnlocked) {
            Row(verticalAlignment = Alignment.CenterVertically) {
              Icon(
                imageVector = Icons.Default.CheckCircle,
                contentDescription = "Выполнено",
                tint = if (item.type == AchievementType.SIN) HellRed else AngelGold,
                modifier = Modifier.size(16.dp)
              )
              Spacer(modifier = Modifier.width(4.dp))
              Text(
                text = "Открыто!",
                color = if (item.type == AchievementType.SIN) HellRed else AngelGold,
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold
              )
            }
          } else {
            Text(
              text = "${item.currentProgress}/${item.targetProgress}",
              color = TextMuted,
              fontSize = 11.sp,
              fontWeight = FontWeight.SemiBold
            )
          }
        }

        Spacer(modifier = Modifier.height(4.dp))

        Text(
          text = item.description,
          color = if (item.isUnlocked) TextSecondary else TextMuted,
          fontSize = 12.sp,
          lineHeight = 16.sp
        )

        if (!item.isUnlocked) {
          Spacer(modifier = Modifier.height(6.dp))
          val fraction = (item.currentProgress.toFloat() / item.targetProgress.toFloat()).coerceIn(0f, 1f)
          Box(
            modifier = Modifier
              .fillMaxWidth()
              .height(4.dp)
              .clip(CircleShape)
              .background(DarkSurfaceElevated)
          ) {
            Box(
              modifier = Modifier
                .fillMaxWidth(fraction)
                .height(4.dp)
                .clip(CircleShape)
                .background(if (item.type == AchievementType.SIN) HellRed else AngelGold)
            )
          }
        }
      }
    }
  }
}
