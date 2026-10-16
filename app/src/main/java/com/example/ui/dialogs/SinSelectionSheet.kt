package com.example.ui.dialogs

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.model.SinCategory
import com.example.model.SinItem
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SinSelectionSheet(
  sheetState: SheetState,
  availableSins: List<SinItem>,
  selectedCategory: SinCategory?,
  searchQuery: String,
  onCategorySelect: (SinCategory?) -> Unit,
  onSearchChange: (String) -> Unit,
  onSinSelected: (SinItem) -> Unit,
  onAddCustomSin: (title: String, points: Int, category: SinCategory) -> Unit,
  onDismiss: () -> Unit
) {
  var showCustomDialog by remember { mutableStateOf(false) }

  ModalBottomSheet(
    onDismissRequest = onDismiss,
    sheetState = sheetState,
    containerColor = DarkVelvet,
    scrimColor = Color.Black.copy(alpha = 0.7f),
    modifier = Modifier.testTag("sin_selection_sheet")
  ) {
    Column(
      modifier = Modifier
        .fillMaxWidth()
        .fillMaxHeight(0.85f)
        .padding(horizontal = 16.dp)
    ) {
      // Header
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Column {
          Text(
            text = "МЕНЮ ГРЕХОПАДЕНИЯ 🔥",
            color = HellGold,
            fontSize = 18.sp,
            fontWeight = FontWeight.Black,
            letterSpacing = 1.sp
          )
          Text(
            text = "Выбери, чем согрешил(а) сегодня:",
            color = TextSecondary,
            fontSize = 13.sp
          )
        }

        // Custom Sin Button
        Button(
          onClick = { showCustomDialog = true },
          colors = ButtonDefaults.buttonColors(
            containerColor = HellRed.copy(alpha = 0.2f),
            contentColor = HellRed
          ),
          shape = RoundedCornerShape(12.dp),
          modifier = Modifier.testTag("add_custom_sin_button")
        ) {
          Icon(
            imageVector = Icons.Default.Add,
            contentDescription = "Свой грех",
            modifier = Modifier.size(16.dp)
          )
          Spacer(modifier = Modifier.width(4.dp))
          Text("Свой грех", fontSize = 12.sp, fontWeight = FontWeight.Bold)
        }
      }

      Spacer(modifier = Modifier.height(12.dp))

      // Search Bar
      val focusManager = LocalFocusManager.current
      OutlinedTextField(
        value = searchQuery,
        onValueChange = onSearchChange,
        placeholder = { Text("Поиск греха (например, пицца, лень, маты)...", color = TextMuted, fontSize = 13.sp) },
        leadingIcon = {
          Icon(Icons.Default.Search, contentDescription = "Поиск", tint = HellOrange)
        },
        trailingIcon = {
          if (searchQuery.isNotEmpty()) {
            IconButton(onClick = { onSearchChange("") }) {
              Icon(Icons.Default.Close, contentDescription = "Очистить", tint = TextMuted)
            }
          }
        },
        singleLine = true,
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
        keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() }),
        colors = OutlinedTextFieldDefaults.colors(
          focusedContainerColor = DarkSurfaceCard,
          unfocusedContainerColor = DarkSurfaceCard,
          focusedBorderColor = HellRed,
          unfocusedBorderColor = DarkBorder,
          focusedTextColor = TextPrimary,
          unfocusedTextColor = TextPrimary
        ),
        shape = RoundedCornerShape(14.dp),
        modifier = Modifier
          .fillMaxWidth()
          .testTag("sin_search_input")
      )

      Spacer(modifier = Modifier.height(12.dp))

      // Category filter chips
      val scrollState = rememberScrollState()
      Row(
        modifier = Modifier
          .fillMaxWidth()
          .horizontalScroll(scrollState),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
      ) {
        CategoryFilterChip(
          title = "Все грехи",
          emoji = "🔥",
          isSelected = selectedCategory == null,
          onClick = { onCategorySelect(null) }
        )
        SinCategory.values().forEach { cat ->
          CategoryFilterChip(
            title = cat.titleRu,
            emoji = cat.emoji,
            isSelected = selectedCategory == cat,
            onClick = { onCategorySelect(cat) }
          )
        }
      }

      Spacer(modifier = Modifier.height(12.dp))

      // Sins list
      if (availableSins.isEmpty()) {
        Box(
          modifier = Modifier
            .fillMaxWidth()
            .weight(1f),
          contentAlignment = Alignment.Center
        ) {
          Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = "🤷‍♀️", fontSize = 48.sp)
            Spacer(modifier = Modifier.height(8.dp))
            Text(
              text = "Такого греха в преисподней пока нет!",
              color = TextPrimary,
              fontWeight = FontWeight.Bold
            )
            Text(
              text = "Но ты можешь добавить его сам через кнопку наверху!",
              color = TextSecondary,
              fontSize = 12.sp
            )
          }
        }
      } else {
        LazyColumn(
          modifier = Modifier
            .fillMaxWidth()
            .weight(1f)
            .testTag("sins_list"),
          verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
          items(availableSins, key = { it.id }) { item ->
            SinItemCard(
              item = item,
              onClick = {
                onSinSelected(item)
                onDismiss()
              }
            )
          }
          item {
            Spacer(modifier = Modifier.height(24.dp))
          }
        }
      }
    }
  }

  // Custom Sin Dialog
  if (showCustomDialog) {
    CustomSinDialog(
      onDismiss = { showCustomDialog = false },
      onConfirm = { title, points, cat ->
        onAddCustomSin(title, points, cat)
        showCustomDialog = false
        onDismiss()
      }
    )
  }
}

@Composable
fun CategoryFilterChip(
  title: String,
  emoji: String,
  isSelected: Boolean,
  onClick: () -> Unit
) {
  Box(
    modifier = Modifier
      .clip(RoundedCornerShape(20.dp))
      .background(if (isSelected) HellRed else DarkSurfaceElevated)
      .border(1.dp, if (isSelected) HellGold else DarkBorder, RoundedCornerShape(20.dp))
      .clickable(onClick = onClick)
      .padding(horizontal = 14.dp, vertical = 8.dp)
  ) {
    Row(verticalAlignment = Alignment.CenterVertically) {
      Text(text = emoji, fontSize = 14.sp)
      Spacer(modifier = Modifier.width(6.dp))
      Text(
        text = title,
        color = if (isSelected) Color.White else TextSecondary,
        fontSize = 12.sp,
        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium
      )
    }
  }
}

@Composable
fun SinItemCard(
  item: SinItem,
  onClick: () -> Unit
) {
  Box(
    modifier = Modifier
      .fillMaxWidth()
      .clip(RoundedCornerShape(16.dp))
      .background(DarkSurfaceCard)
      .border(1.dp, DarkBorder, RoundedCornerShape(16.dp))
      .clickable(onClick = onClick)
      .padding(14.dp)
      .testTag("sin_card_${item.id}")
  ) {
    Column {
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.Top
      ) {
        Row(
          modifier = Modifier.weight(1f),
          verticalAlignment = Alignment.Top
        ) {
          Text(text = item.emoji, fontSize = 24.sp, modifier = Modifier.padding(end = 10.dp))
          Column {
            Text(
              text = item.title,
              color = TextPrimary,
              fontSize = 15.sp,
              fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(4.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
              Box(
                modifier = Modifier
                  .clip(RoundedCornerShape(6.dp))
                  .background(Color(item.category.badgeColor).copy(alpha = 0.2f))
                  .padding(horizontal = 6.dp, vertical = 2.dp)
              ) {
                Text(
                  text = item.category.titleRu,
                  color = Color(item.category.badgeColor),
                  fontSize = 11.sp,
                  fontWeight = FontWeight.SemiBold
                )
              }
            }
          }
        }

        // Points badge
        Box(
          modifier = Modifier
            .clip(RoundedCornerShape(10.dp))
            .background(HellRed.copy(alpha = 0.15f))
            .border(1.dp, HellRed.copy(alpha = 0.5f), RoundedCornerShape(10.dp))
            .padding(horizontal = 10.dp, vertical = 6.dp)
        ) {
          Text(
            text = "+${item.points}",
            color = HellRed,
            fontSize = 13.sp,
            fontWeight = FontWeight.Black
          )
        }
      }

      Spacer(modifier = Modifier.height(8.dp))

      // Funny quote
      Text(
        text = "💬 \"${item.funnyQuote}\"",
        color = TextSecondary,
        fontSize = 11.sp,
        lineHeight = 15.sp
      )
    }
  }
}

@Composable
fun CustomSinDialog(
  onDismiss: () -> Unit,
  onConfirm: (title: String, points: Int, category: SinCategory) -> Unit
) {
  var title by remember { mutableStateOf("") }
  var pointsStr by remember { mutableStateOf("500") }
  var selectedCategory by remember { mutableStateOf(SinCategory.GLUTTONY) }

  Dialog(onDismissRequest = onDismiss) {
    Box(
      modifier = Modifier
        .fillMaxWidth()
        .clip(RoundedCornerShape(20.dp))
        .background(DarkVelvet)
        .border(1.5.dp, HellRed, RoundedCornerShape(20.dp))
        .padding(20.dp)
    ) {
      Column {
        Text(
          text = "ТВОЙ АВТОРСКИЙ ГРЕХ 😈",
          color = HellGold,
          fontSize = 17.sp,
          fontWeight = FontWeight.Black
        )
        Text(
          text = "Признавайся, что ты вытворил(а)?",
          color = TextSecondary,
          fontSize = 13.sp
        )

        Spacer(modifier = Modifier.height(14.dp))

        OutlinedTextField(
          value = title,
          onValueChange = { title = it },
          placeholder = { Text("Название греха...", color = TextMuted) },
          colors = OutlinedTextFieldDefaults.colors(
            focusedTextColor = TextPrimary,
            unfocusedTextColor = TextPrimary,
            focusedBorderColor = HellRed,
            unfocusedBorderColor = DarkBorder,
            focusedContainerColor = DarkSurfaceCard,
            unfocusedContainerColor = DarkSurfaceCard
          ),
          modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(10.dp))

        OutlinedTextField(
          value = pointsStr,
          onValueChange = { pointsStr = it.filter { char -> char.isDigit() } },
          placeholder = { Text("Очки (например 500)", color = TextMuted) },
          keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
          colors = OutlinedTextFieldDefaults.colors(
            focusedTextColor = TextPrimary,
            unfocusedTextColor = TextPrimary,
            focusedBorderColor = HellRed,
            unfocusedBorderColor = DarkBorder,
            focusedContainerColor = DarkSurfaceCard,
            unfocusedContainerColor = DarkSurfaceCard
          ),
          modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(10.dp))

        Text(text = "Категория:", color = TextSecondary, fontSize = 12.sp)
        Spacer(modifier = Modifier.height(6.dp))

        // Category selection
        val scrollState = rememberScrollState()
        Row(
          modifier = Modifier
            .fillMaxWidth()
            .horizontalScroll(scrollState),
          horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
          SinCategory.values().forEach { cat ->
            Box(
              modifier = Modifier
                .clip(RoundedCornerShape(12.dp))
                .background(if (selectedCategory == cat) HellRed else DarkSurfaceCard)
                .clickable { selectedCategory = cat }
                .padding(horizontal = 10.dp, vertical = 6.dp)
            ) {
              Text(
                text = "${cat.emoji} ${cat.titleRu}",
                color = Color.White,
                fontSize = 11.sp,
                fontWeight = if (selectedCategory == cat) FontWeight.Bold else FontWeight.Normal
              )
            }
          }
        }

        Spacer(modifier = Modifier.height(20.dp))

        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.End
        ) {
          Button(
            onClick = onDismiss,
            colors = ButtonDefaults.buttonColors(containerColor = DarkSurfaceCard)
          ) {
            Text("Отмена", color = TextSecondary)
          }
          Spacer(modifier = Modifier.width(10.dp))
          Button(
            onClick = {
              if (title.isNotBlank()) {
                val pts = pointsStr.toIntOrNull() ?: 500
                onConfirm(title, pts, selectedCategory)
              }
            },
            colors = ButtonDefaults.buttonColors(containerColor = HellRed),
            enabled = title.isNotBlank()
          ) {
            Text("Засчитать! 🔥", color = Color.White, fontWeight = FontWeight.Bold)
          }
        }
      }
    }
  }
}
