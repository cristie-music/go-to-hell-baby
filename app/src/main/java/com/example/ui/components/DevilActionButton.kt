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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
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
import com.example.ui.theme.HellCrimson
import com.example.ui.theme.HellGold
import com.example.ui.theme.HellOrange
import com.example.ui.theme.HellRed
import com.example.ui.theme.HellRedDark
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary

@Composable
fun DevilActionButton(
  onClick: () -> Unit,
  modifier: Modifier = Modifier
) {
  val infiniteTransition = rememberInfiniteTransition(label = "pulse_devil")
  val pulseScale by infiniteTransition.animateFloat(
    initialValue = 1f,
    targetValue = 1.06f,
    animationSpec = infiniteRepeatable(
      animation = tween(1100, easing = FastOutSlowInEasing),
      repeatMode = RepeatMode.Reverse
    ),
    label = "devil_scale"
  )

  val ringAlpha by infiniteTransition.animateFloat(
    initialValue = 0.3f,
    targetValue = 0.8f,
    animationSpec = infiniteRepeatable(
      animation = tween(1100, easing = FastOutSlowInEasing),
      repeatMode = RepeatMode.Reverse
    ),
    label = "devil_ring_alpha"
  )

  Column(
    horizontalAlignment = Alignment.CenterHorizontally,
    modifier = modifier
  ) {
    Box(
      contentAlignment = Alignment.Center,
      modifier = Modifier
        .size(170.dp)
        .scale(pulseScale)
    ) {
      // Outer fiery pulse ring
      Box(
        modifier = Modifier
          .size(165.dp)
          .clip(CircleShape)
          .background(
            Brush.radialGradient(
              colors = listOf(
                HellRed.copy(alpha = ringAlpha),
                HellCrimson.copy(alpha = ringAlpha * 0.5f),
                Color.Transparent
              )
            )
          )
      )

      // Main Red Devil Button
      Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
          .size(136.dp)
          .shadow(elevation = 20.dp, shape = CircleShape, spotColor = HellRed)
          .clip(CircleShape)
          .background(
            Brush.linearGradient(
              colors = listOf(HellRed, HellRedDark, HellCrimson)
            )
          )
          .border(3.dp, HellGold, CircleShape)
          .clickable(
            interactionSource = remember { MutableInteractionSource() },
            indication = null,
            onClick = onClick
          )
          .testTag("devil_button")
      ) {
        // Devil character portrait
        Image(
          painter = painterResource(id = R.drawable.devil_avatar),
          contentDescription = "Девушка-чертовка",
          contentScale = ContentScale.Crop,
          modifier = Modifier
            .size(114.dp)
            .clip(CircleShape)
        )
      }

      // Small badge on the button: "🔥"
      Box(
        modifier = Modifier
          .align(Alignment.BottomEnd)
          .offset(x = (-8).dp, y = (-8).dp)
          .size(36.dp)
          .clip(CircleShape)
          .background(HellGold)
          .border(2.dp, HellRedDark, CircleShape),
        contentAlignment = Alignment.Center
      ) {
        Text(text = "🔥", fontSize = 18.sp)
      }
    }

    Spacer(modifier = Modifier.height(8.dp))

    Text(
      text = "ГРЕШИТЬ! 🔥",
      color = TextPrimary,
      fontSize = 20.sp,
      fontWeight = FontWeight.Black,
      letterSpacing = 1.sp
    )
    Text(
      text = "Нажми на чертовку, чтобы выбрать грех",
      color = TextSecondary,
      fontSize = 12.sp
    )
  }
}
