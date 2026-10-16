package com.example.ui.dialogs

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.R
import com.example.data.GoodDeedResult
import com.example.ui.theme.AngelGlow
import com.example.ui.theme.AngelGold
import com.example.ui.theme.AngelSoftBlue
import com.example.ui.theme.AngelWhite
import com.example.ui.theme.DarkObsidian
import com.example.ui.theme.DarkSurfaceCard
import com.example.ui.theme.DarkVelvet
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary

@Composable
fun GoodDeedDialog(
  result: GoodDeedResult,
  onDismiss: () -> Unit
) {
  Dialog(onDismissRequest = onDismiss) {
    Box(
      modifier = Modifier
        .fillMaxWidth(0.92f)
        .clip(RoundedCornerShape(28.dp))
        .background(
          Brush.verticalGradient(
            colors = listOf(
              Color(0xFF2A1C36),
              DarkObsidian
            )
          )
        )
        .border(2.5.dp, AngelGold, RoundedCornerShape(28.dp))
        .padding(20.dp)
        .testTag("good_deed_dialog")
    ) {
      Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth()
      ) {
        // Angel Avatar
        Box(
          modifier = Modifier
            .size(120.dp)
            .shadow(elevation = 16.dp, shape = CircleShape, spotColor = AngelGold)
            .clip(CircleShape)
            .background(AngelWhite)
            .border(3.dp, AngelGold, CircleShape),
          contentAlignment = Alignment.Center
        ) {
          Image(
            painter = painterResource(id = R.drawable.angel_avatar),
            contentDescription = "Ангел с крылышками",
            contentScale = ContentScale.Crop,
            modifier = Modifier.size(110.dp)
          )
        }

        Spacer(modifier = Modifier.height(14.dp))

        // Headline
        Text(
          text = "СДЕЛАНО ДОБРОЕ ДЕЛО! 😇",
          color = AngelGold,
          fontSize = 18.sp,
          fontWeight = FontWeight.Black,
          textAlign = TextAlign.Center,
          letterSpacing = 0.5.sp,
          modifier = Modifier.testTag("good_deed_title")
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Points cleared badge
        Box(
          modifier = Modifier
            .clip(RoundedCornerShape(12.dp))
            .background(Color(0xFF388E3C).copy(alpha = 0.2f))
            .border(1.dp, Color(0xFF66BB6A), RoundedCornerShape(12.dp))
            .padding(horizontal = 14.dp, vertical = 6.dp)
        ) {
          Text(
            text = "Списано грехов: -${result.pointsCleared} очков!",
            color = Color(0xFF81C784),
            fontSize = 13.sp,
            fontWeight = FontWeight.Bold
          )
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Funny Ironic Message
        Text(
          text = result.message,
          color = TextPrimary,
          fontSize = 14.sp,
          textAlign = TextAlign.Center,
          lineHeight = 20.sp,
          modifier = Modifier
            .fillMaxWidth()
            .testTag("good_deed_message")
        )

        Spacer(modifier = Modifier.height(10.dp))

        Text(
          text = "Добрых дел совершено всего: ${result.totalGoodDeeds} 🕊️",
          color = TextSecondary,
          fontSize = 12.sp
        )

        Spacer(modifier = Modifier.height(18.dp))

        Button(
          onClick = onDismiss,
          colors = ButtonDefaults.buttonColors(
            containerColor = AngelWhite,
            contentColor = Color(0xFF22112C)
          ),
          shape = RoundedCornerShape(16.dp),
          modifier = Modifier
            .fillMaxWidth()
            .height(48.dp)
            .testTag("good_deed_confirm_button")
        ) {
          Text(
            text = "Святость принята ✨",
            fontSize = 15.sp,
            fontWeight = FontWeight.Bold
          )
        }
      }
    }
  }
}
