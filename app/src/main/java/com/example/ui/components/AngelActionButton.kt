package com.example.ui.components

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.ui.theme.AngelGlow
import com.example.ui.theme.AngelGold
import com.example.ui.theme.AngelWhite

@Composable
fun AngelActionButton(
  onClick: () -> Unit,
  modifier: Modifier = Modifier
) {
  val infiniteTransition = rememberInfiniteTransition(label = "angel_halo")
  val haloGlow by infiniteTransition.animateFloat(
    initialValue = 0.4f,
    targetValue = 0.9f,
    animationSpec = infiniteRepeatable(
      animation = tween(1500, easing = FastOutSlowInEasing),
      repeatMode = RepeatMode.Reverse
    ),
    label = "halo_glow"
  )

  Column(
    horizontalAlignment = Alignment.CenterHorizontally,
    modifier = modifier
  ) {
    Box(
      contentAlignment = Alignment.Center,
      modifier = Modifier.size(76.dp)
    ) {
      // Gentle heavenly glow
      Box(
        modifier = Modifier
          .size(74.dp)
          .clip(CircleShape)
          .background(
            Brush.radialGradient(
              colors = listOf(
                AngelGlow.copy(alpha = haloGlow),
                Color.Transparent
              )
            )
          )
      )

      // Small White Angel Button
      Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
          .size(60.dp)
          .shadow(elevation = 12.dp, shape = CircleShape, spotColor = AngelGold)
          .clip(CircleShape)
          .background(AngelWhite)
          .border(2.5.dp, AngelGold, CircleShape)
          .clickable(
            interactionSource = remember { MutableInteractionSource() },
            indication = null,
            onClick = onClick
          )
          .testTag("angel_good_deed_button")
      ) {
        Image(
          painter = painterResource(id = R.drawable.angel_avatar),
          contentDescription = "Кнопка с крылышками ангела: сделать доброе дело",
          contentScale = ContentScale.Crop,
          modifier = Modifier
            .size(48.dp)
            .clip(CircleShape)
        )
      }

      // Small Wings / Halo Badge
      Box(
        modifier = Modifier
          .align(Alignment.TopEnd)
          .offset(x = 2.dp, y = (-2).dp)
          .size(24.dp)
          .clip(CircleShape)
          .background(AngelWhite)
          .border(1.5.dp, AngelGold, CircleShape),
        contentAlignment = Alignment.Center
      ) {
        Text(text = "🪽", fontSize = 12.sp)
      }
    }

    Spacer(modifier = Modifier.height(4.dp))

    Text(
      text = "Доброе дело",
      color = AngelWhite,
      fontSize = 11.sp,
      fontWeight = FontWeight.Bold
    )
    Text(
      text = "Обнулить грех",
      color = AngelGold,
      fontSize = 10.sp
    )
  }
}
