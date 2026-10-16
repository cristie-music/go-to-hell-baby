package com.example.ui.components

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.SinRepository
import com.example.ui.theme.DarkBorder
import com.example.ui.theme.DarkSurfaceCard
import com.example.ui.theme.HellCrimson
import com.example.ui.theme.HellGold
import com.example.ui.theme.HellOrange
import com.example.ui.theme.HellRed
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary

@Composable
fun HellProgressBar(
  points: Int,
  remainingToHell: Int,
  progressFraction: Float,
  rankTitle: String,
  modifier: Modifier = Modifier
) {
  val animatedProgress by animateFloatAsState(
    targetValue = progressFraction,
    animationSpec = tween(durationMillis = 800, easing = FastOutSlowInEasing),
    label = "hell_progress_anim"
  )

  val infiniteTransition = rememberInfiniteTransition(label = "ember_glow")
  val glowAlpha by infiniteTransition.animateFloat(
    initialValue = 0.6f,
    targetValue = 1.0f,
    animationSpec = infiniteRepeatable(
      animation = tween(1200, easing = FastOutSlowInEasing),
      repeatMode = RepeatMode.Reverse
    ),
    label = "glow_alpha"
  )

  Column(
    modifier = modifier
      .fillMaxWidth()
      .clip(RoundedCornerShape(20.dp))
      .background(DarkSurfaceCard)
      .border(1.dp, DarkBorder, RoundedCornerShape(20.dp))
      .padding(16.dp)
      .testTag("hell_progress_container")
  ) {
    // Header with Rank
    Row(
      modifier = Modifier.fillMaxWidth(),
      horizontalArrangement = Arrangement.SpaceBetween,
      verticalAlignment = Alignment.CenterVertically
    ) {
      Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(
          imageVector = Icons.Default.LocalFireDepartment,
          contentDescription = "Огонь",
          tint = HellOrange,
          modifier = Modifier.padding(end = 6.dp)
        )
        Text(
          text = "ПУТЬ В ПРЕИСПОДНЮЮ",
          color = HellGold,
          fontSize = 12.sp,
          fontWeight = FontWeight.Black,
          letterSpacing = 1.sp
        )
      }
      Box(
        modifier = Modifier
          .clip(CircleShape)
          .background(HellRed.copy(alpha = 0.2f))
          .padding(horizontal = 10.dp, vertical = 4.dp)
      ) {
        Text(
          text = "${(progressFraction * 100).toInt()}%",
          color = HellRed,
          fontSize = 12.sp,
          fontWeight = FontWeight.Bold
        )
      }
    }

    Spacer(modifier = Modifier.height(10.dp))

    // Score display
    Row(
      modifier = Modifier.fillMaxWidth(),
      horizontalArrangement = Arrangement.SpaceBetween,
      verticalAlignment = Alignment.Bottom
    ) {
      Column {
        Text(
          text = "$points очков",
          color = TextPrimary,
          fontSize = 28.sp,
          fontWeight = FontWeight.ExtraBold,
          modifier = Modifier.testTag("current_points_text")
        )
        Text(
          text = rankTitle,
          color = HellOrange,
          fontSize = 13.sp,
          fontWeight = FontWeight.SemiBold
        )
      }
      Column(horizontalAlignment = Alignment.End) {
        Text(
          text = "Цель: ${SinRepository.HELL_TARGET_POINTS}",
          color = TextMuted,
          fontSize = 12.sp
        )
        Text(
          text = if (remainingToHell > 0) "Осталось: $remainingToHell" else "УЖЕ В АДУ!",
          color = if (remainingToHell > 0) HellRed else HellGold,
          fontSize = 13.sp,
          fontWeight = FontWeight.Bold,
          modifier = Modifier.testTag("remaining_points_text")
        )
      }
    }

    Spacer(modifier = Modifier.height(14.dp))

    // Fiery Progress Bar Track
    Box(
      modifier = Modifier
        .fillMaxWidth()
        .height(16.dp)
        .clip(RoundedCornerShape(8.dp))
        .background(Color(0xFF140718))
        .border(1.dp, Color(0xFF38153E), RoundedCornerShape(8.dp))
    ) {
      Box(
        modifier = Modifier
          .fillMaxHeight()
          .fillMaxWidth(animatedProgress)
          .clip(RoundedCornerShape(8.dp))
          .background(
            Brush.horizontalGradient(
              colors = listOf(
                HellCrimson,
                HellRed,
                HellOrange,
                HellGold
              )
            )
          )
      )
    }

    Spacer(modifier = Modifier.height(10.dp))

    // Ironic quote footer
    Text(
      text = if (points >= SinRepository.HELL_TARGET_POINTS) {
        "🔥 Сковородка раскалена до предела! Люцифер жмет тебе руку!"
      } else {
        "В АД нужно набрать 10 000 очков. Греши усерднее, черти ждут! 🔥"
      },
      color = TextSecondary,
      fontSize = 11.sp,
      lineHeight = 15.sp
    )
  }
}
