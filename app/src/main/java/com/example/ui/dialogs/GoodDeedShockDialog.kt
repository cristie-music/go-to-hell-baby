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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.R
import com.example.ui.theme.AngelGold
import com.example.ui.theme.DarkObsidian
import com.example.ui.theme.DarkVelvet
import com.example.ui.theme.HellCrimson
import com.example.ui.theme.HellGold
import com.example.ui.theme.HellRed
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary

private val shockQuotes = listOf(
  "Ты что наделал(а)?! Я тебя в АДУ жду, сковородку начистила до блеска, а ты тут в праведники записался?! 😱💔",
  "Предатель(ница)! А как же наш персональный котёл с джакузи?! Неужели ты бросишь меня ради нимба?! 😭🔥",
  "Ты разбиваешь мне сердечко! Кто теперь будет грешить по ночам и есть пиццу в 3:00?! 🍕💔",
  "Остановись! В раю даже нормального Wi-Fi нет, а у нас в Аду — вечная вечеринка! Вернись к чертям! 😈🍾",
  "Нимб тебе не идёт, рожки сидели куда сексуальнее! Подумай ещё раз, пока не поздно! 💅🔥"
)

@Composable
fun GoodDeedShockDialog(
  onConfirmGoodDeed: () -> Unit,
  onStayWithDevil: () -> Unit,
  onDismiss: () -> Unit
) {
  val randomQuote = remember { shockQuotes.random() }

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
        .testTag("good_deed_shock_dialog")
    ) {
      Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth()
      ) {
        // Shock Headline
        Text(
          text = "ТЫ ЧТО НАДЕЛАЛ(А)?! 😱💔",
          color = HellRed,
          fontSize = 18.sp,
          fontWeight = FontWeight.Black,
          textAlign = TextAlign.Center,
          letterSpacing = 0.5.sp
        )

        Spacer(modifier = Modifier.height(12.dp))

        // Image from 14.png (demon_good_deed_shock.jpg)
        Box(
          modifier = Modifier
            .size(230.dp)
            .shadow(elevation = 16.dp, shape = RoundedCornerShape(20.dp), spotColor = HellRed)
            .clip(RoundedCornerShape(20.dp))
            .border(2.dp, HellRed, RoundedCornerShape(20.dp))
        ) {
          Image(
            painter = painterResource(id = R.drawable.demon_good_deed_shock),
            contentDescription = "Шокированная чертовка",
            contentScale = ContentScale.Crop,
            modifier = Modifier
              .matchParentSize()
              .testTag("shocked_devil_photo")
          )
        }

        Spacer(modifier = Modifier.height(14.dp))

        // Shocked quote
        Text(
          text = randomQuote,
          color = TextPrimary,
          fontSize = 15.sp,
          fontWeight = FontWeight.Bold,
          textAlign = TextAlign.Center,
          lineHeight = 21.sp,
          modifier = Modifier
            .fillMaxWidth()
            .testTag("shocked_devil_quote")
        )

        Spacer(modifier = Modifier.height(18.dp))

        // Actions: Proceed with Good Deed OR Stay With Devil!
        Column(
          modifier = Modifier.fillMaxWidth(),
          verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
          Button(
            onClick = onStayWithDevil,
            colors = ButtonDefaults.buttonColors(containerColor = HellRed),
            shape = RoundedCornerShape(14.dp),
            modifier = Modifier
              .fillMaxWidth()
              .height(48.dp)
              .testTag("stay_with_devil_button")
          ) {
            Text(
              text = "Остаться с чертовкой! 🔥😈",
              fontSize = 14.sp,
              fontWeight = FontWeight.Black
            )
          }

          Button(
            onClick = onConfirmGoodDeed,
            colors = ButtonDefaults.buttonColors(
              containerColor = Color(0xFF382348),
              contentColor = AngelGold
            ),
            shape = RoundedCornerShape(14.dp),
            modifier = Modifier
              .fillMaxWidth()
              .height(46.dp)
              .testTag("confirm_good_deed_button")
          ) {
            Text(
              text = "Всё равно искупить грех 😇",
              fontSize = 13.sp,
              fontWeight = FontWeight.Bold
            )
          }
        }
      }
    }
  }
}
