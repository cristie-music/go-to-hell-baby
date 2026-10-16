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
import androidx.compose.ui.window.DialogProperties
import com.example.ui.theme.DarkBorder
import com.example.ui.theme.DarkObsidian
import com.example.ui.theme.DarkSurfaceCard
import com.example.ui.theme.DarkVelvet
import com.example.ui.theme.HellCrimson
import com.example.ui.theme.HellGold
import com.example.ui.theme.HellOrange
import com.example.ui.theme.HellRed
import com.example.ui.theme.HellRedDark
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary
import com.example.viewmodel.MilestoneDialogData

@Composable
fun MilestonePraiseDialog(
  data: MilestoneDialogData,
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
        .border(2.5.dp, HellRed, RoundedCornerShape(28.dp))
        .padding(20.dp)
        .testTag("milestone_praise_dialog")
    ) {
      Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth()
      ) {
        // Badge
        Box(
          modifier = Modifier
            .clip(RoundedCornerShape(12.dp))
            .background(HellRed.copy(alpha = 0.2f))
            .border(1.dp, HellGold, RoundedCornerShape(12.dp))
            .padding(horizontal = 14.dp, vertical = 6.dp)
        ) {
          Text(
            text = "🔥 ${data.sinCount}-Й ГРЕХ ЗАСЧИТАН! 🔥",
            color = HellGold,
            fontSize = 13.sp,
            fontWeight = FontWeight.Black,
            letterSpacing = 1.sp
          )
        }

        Spacer(modifier = Modifier.height(14.dp))

        // Demon Photo (Random 2.png or 6.png)
        Box(
          modifier = Modifier
            .size(240.dp)
            .shadow(elevation = 16.dp, shape = RoundedCornerShape(20.dp), spotColor = HellRed)
            .clip(RoundedCornerShape(20.dp))
            .border(2.dp, HellGold, RoundedCornerShape(20.dp))
        ) {
          Image(
            painter = painterResource(id = data.imageResId),
            contentDescription = "Демоническая похвала от чертовки",
            contentScale = ContentScale.Crop,
            modifier = Modifier
              .matchParentSize()
              .testTag("milestone_demon_photo")
          )

          // Red ember gradient overlay at bottom
          Box(
            modifier = Modifier
              .fillMaxWidth()
              .height(60.dp)
              .align(Alignment.BottomCenter)
              .background(
                Brush.verticalGradient(
                  colors = listOf(Color.Transparent, Color.Black.copy(alpha = 0.8f))
                )
              )
          )
        }

        Spacer(modifier = Modifier.height(14.dp))

        // Witty Praise Quote
        Text(
          text = data.praiseQuote,
          color = TextPrimary,
          fontSize = 16.sp,
          fontWeight = FontWeight.Bold,
          textAlign = TextAlign.Center,
          lineHeight = 22.sp,
          modifier = Modifier
            .fillMaxWidth()
            .testTag("milestone_praise_text")
        )

        Spacer(modifier = Modifier.height(12.dp))

        // Progress Info in Dialog
        Row(
          modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(14.dp))
            .background(DarkSurfaceCard)
            .border(1.dp, DarkBorder, RoundedCornerShape(14.dp))
            .padding(12.dp),
          horizontalArrangement = Arrangement.SpaceAround
        ) {
          Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = "Набрано очков", color = TextSecondary, fontSize = 11.sp)
            Text(
              text = "${data.points}",
              color = HellGold,
              fontSize = 17.sp,
              fontWeight = FontWeight.ExtraBold
            )
          }
          Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = "Осталось до Ада", color = TextSecondary, fontSize = 11.sp)
            Text(
              text = "${data.remaining}",
              color = HellRed,
              fontSize = 17.sp,
              fontWeight = FontWeight.ExtraBold
            )
          }
        }

        Spacer(modifier = Modifier.height(18.dp))

        // Proud Button
        Button(
          onClick = onDismiss,
          colors = ButtonDefaults.buttonColors(
            containerColor = HellRed,
            contentColor = Color.White
          ),
          shape = RoundedCornerShape(16.dp),
          modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
            .testTag("milestone_dismiss_button")
        ) {
          Text(
            text = "Гордиться собой! 🔥",
            fontSize = 15.sp,
            fontWeight = FontWeight.Black,
            letterSpacing = 1.sp
          )
        }
      }
    }
  }
}
