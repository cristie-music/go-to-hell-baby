package com.example.ui.dialogs

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.window.DialogProperties
import com.example.R
import com.example.ui.theme.DarkObsidian
import com.example.ui.theme.DarkVelvet
import com.example.ui.theme.HellCrimson
import com.example.ui.theme.HellGold
import com.example.ui.theme.HellRed
import com.example.ui.theme.TextPrimary

@Composable
fun HellWelcomeDialog(
  onDismiss: () -> Unit
) {
  Dialog(
    onDismissRequest = onDismiss,
    properties = DialogProperties(usePlatformDefaultWidth = false)
  ) {
    Box(
      modifier = Modifier
        .fillMaxWidth(0.92f)
        .clip(RoundedCornerShape(28.dp))
        .background(
          Brush.verticalGradient(
            colors = listOf(
              DarkVelvet,
              DarkObsidian
            )
          )
        )
        .border(3.dp, HellGold, RoundedCornerShape(28.dp))
        .padding(20.dp)
        .testTag("hell_welcome_dialog")
    ) {
      Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth()
      ) {
        Text(
          text = "🔥 ДОБРО ПОЖАЛОВАТЬ В АД! 🔥",
          color = HellGold,
          fontSize = 18.sp,
          fontWeight = FontWeight.Black,
          textAlign = TextAlign.Center,
          letterSpacing = 1.sp
        )

        Spacer(modifier = Modifier.height(10.dp))

        Box(
          modifier = Modifier
            .size(200.dp)
            .shadow(elevation = 16.dp, shape = RoundedCornerShape(20.dp), spotColor = HellRed)
            .clip(RoundedCornerShape(20.dp))
            .border(2.dp, HellRed, RoundedCornerShape(20.dp))
        ) {
          Image(
            painter = painterResource(id = R.drawable.demon_praise_fangs),
            contentDescription = "Добро пожаловать в Ад",
            contentScale = ContentScale.Crop,
            modifier = Modifier.matchParentSize()
          )
        }

        Spacer(modifier = Modifier.height(14.dp))

        Text(
          text = "10 000 очков набрано! Ты официально заслужил(а) отдельный котёл с гидромассажем! Черти аплодируют стоя, сковородка начищена до блеска! 😈🍾",
          color = TextPrimary,
          fontSize = 14.sp,
          textAlign = TextAlign.Center,
          lineHeight = 20.sp
        )

        Spacer(modifier = Modifier.height(18.dp))

        Button(
          onClick = onDismiss,
          colors = ButtonDefaults.buttonColors(containerColor = HellRed),
          shape = RoundedCornerShape(16.dp),
          modifier = Modifier
            .fillMaxWidth()
            .height(48.dp)
            .testTag("hell_welcome_dismiss_button")
        ) {
          Text("Принять судьбу! 🔥", fontWeight = FontWeight.Black, color = Color.White)
        }
      }
    }
  }
}
