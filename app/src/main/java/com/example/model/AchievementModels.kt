package com.example.model

enum class AchievementType {
  SIN,
  GOOD_DEED
}

data class AchievementDefinition(
  val id: String,
  val title: String,
  val description: String,
  val emoji: String,
  val type: AchievementType,
  val targetValue: Int,
  val evaluateProgress: (sinsCount: Int, points: Int, deedsCount: Int, categoryPoints: Map<SinCategory, Int>) -> Int
)

data class AchievementUiModel(
  val id: String,
  val title: String,
  val description: String,
  val emoji: String,
  val type: AchievementType,
  val currentProgress: Int,
  val targetProgress: Int,
  val isUnlocked: Boolean
)

object AchievementRegistry {
  val allAchievements = listOf(
    // 16 Достижений грехов
    AchievementDefinition(
      id = "sin_1",
      title = "Первый шаг во тьму",
      description = "Совершить свой самый первый грех. Добро пожаловать!",
      emoji = "👣",
      type = AchievementType.SIN,
      targetValue = 1,
      evaluateProgress = { count, _, _, _ -> count }
    ),
    AchievementDefinition(
      id = "sin_3",
      title = "Разогрев сковородки",
      description = "Совершить 3 греха. Черти подкинули первые дровишки.",
      emoji = "🪵",
      type = AchievementType.SIN,
      targetValue = 3,
      evaluateProgress = { count, _, _, _ -> count }
    ),
    AchievementDefinition(
      id = "sin_6",
      title = "Шесть грехов",
      description = "Число зверя уже маячит на горизонте! Зафиксировано 6 грехов.",
      emoji = "😈",
      type = AchievementType.SIN,
      targetValue = 6,
      evaluateProgress = { count, _, _, _ -> count }
    ),
    AchievementDefinition(
      id = "sin_10",
      title = "Десятка грехопадений",
      description = "Набрать 10 совершенных грехов. Ты вошел(ла) во вкус!",
      emoji = "🔟",
      type = AchievementType.SIN,
      targetValue = 10,
      evaluateProgress = { count, _, _, _ -> count }
    ),
    AchievementDefinition(
      id = "sin_13",
      title = "Пятница 13-е",
      description = "13 грехов в копилке. Никаких суеверий, чистый кураж!",
      emoji = "🔮",
      type = AchievementType.SIN,
      targetValue = 13,
      evaluateProgress = { count, _, _, _ -> count }
    ),
    AchievementDefinition(
      id = "sin_20",
      title = "Опытный греховодник",
      description = "Совершить 20 грехов. Совесть официально взяла отпуск.",
      emoji = "🍸",
      type = AchievementType.SIN,
      targetValue = 20,
      evaluateProgress = { count, _, _, _ -> count }
    ),
    AchievementDefinition(
      id = "sin_33",
      title = "Красивое число 33",
      description = "33 греха. Чертовка шлёт тебе воздушный поцелуй!",
      emoji = "💋",
      type = AchievementType.SIN,
      targetValue = 33,
      evaluateProgress = { count, _, _, _ -> count }
    ),
    AchievementDefinition(
      id = "sin_50",
      title = "Полсотни искушений",
      description = "50 грехов. Тебя уже узнают в адском караоке!",
      emoji = "🎤",
      type = AchievementType.SIN,
      targetValue = 50,
      evaluateProgress = { count, _, _, _ -> count }
    ),
    AchievementDefinition(
      id = "sin_100",
      title = "На пути в Ад",
      description = "100 грехов! Золотая карта постоянного клиента преисподней.",
      emoji = "🔥",
      type = AchievementType.SIN,
      targetValue = 100,
      evaluateProgress = { count, _, _, _ -> count }
    ),
    AchievementDefinition(
      id = "sin_gluttony_master",
      title = "Ночной обжора",
      description = "Набрать 1000 очков в категории Чревоугодие. Холодильник — твой дом.",
      emoji = "🌭",
      type = AchievementType.SIN,
      targetValue = 1000,
      evaluateProgress = { _, _, _, catMap -> catMap[SinCategory.GLUTTONY] ?: 0 }
    ),
    AchievementDefinition(
      id = "sin_sloth_master",
      title = "Ленивый повелитель",
      description = "Набрать 1000 очков в Лени. Диван стал продолжением твоего тела.",
      emoji = "🛋️",
      type = AchievementType.SIN,
      targetValue = 1000,
      evaluateProgress = { _, _, _, catMap -> catMap[SinCategory.SLOTH] ?: 0 }
    ),
    AchievementDefinition(
      id = "sin_wrath_master",
      title = "Огнедышащий вулкан",
      description = "Набрать 1000 очков в Гневе. С тобой спорить опасно для жизни!",
      emoji = "🌋",
      type = AchievementType.SIN,
      targetValue = 1000,
      evaluateProgress = { _, _, _, catMap -> catMap[SinCategory.WRATH] ?: 0 }
    ),
    AchievementDefinition(
      id = "sin_pride_master",
      title = "Центр Галактики",
      description = "Набрать 1000 очков в Гордыне. Зеркало треснуло от твоего блеска.",
      emoji = "👑",
      type = AchievementType.SIN,
      targetValue = 1000,
      evaluateProgress = { _, _, _, catMap -> catMap[SinCategory.PRIDE] ?: 0 }
    ),
    AchievementDefinition(
      id = "sin_envy_master",
      title = "Шпион под прикрытием",
      description = "Набрать 1000 очков в Зависти. Соцсети изучены до мельчайших пикселей.",
      emoji = "👀",
      type = AchievementType.SIN,
      targetValue = 1000,
      evaluateProgress = { _, _, _, catMap -> catMap[SinCategory.ENVY] ?: 0 }
    ),
    AchievementDefinition(
      id = "sin_greed_master",
      title = "Скрудж Макдак",
      description = "Набрать 1000 очков в Жадности. Моё сокровище никому не отдам!",
      emoji = "💰",
      type = AchievementType.SIN,
      targetValue = 1000,
      evaluateProgress = { _, _, _, catMap -> catMap[SinCategory.GREED] ?: 0 }
    ),
    AchievementDefinition(
      id = "sin_hell_target",
      title = "VIP-Котёл с джакузи",
      description = "Набрать 10 000 очков в Преисподнюю! Ты легенда преисподней!",
      emoji = "🏆",
      type = AchievementType.SIN,
      targetValue = 10000,
      evaluateProgress = { _, points, _, _ -> points }
    ),

    // 5 Достижений добрых дел
    AchievementDefinition(
      id = "deed_1",
      title = "Минутная слабость",
      description = "Совершить 1 доброе дело. Ангел улыбнулся, чертовка вздохнула.",
      emoji = "🕊️",
      type = AchievementType.GOOD_DEED,
      targetValue = 1,
      evaluateProgress = { _, _, deeds, _ -> deeds }
    ),
    AchievementDefinition(
      id = "deed_3",
      title = "Ангельское терпение",
      description = "Совершить 3 добрых дела. Неужели ты всерьез решил(а) стать святым?!",
      emoji = "🪽",
      type = AchievementType.GOOD_DEED,
      targetValue = 3,
      evaluateProgress = { _, _, deeds, _ -> deeds }
    ),
    AchievementDefinition(
      id = "deed_5",
      title = "Белое перышко",
      description = "Совершить 5 добрых дел. Карма очищается с подозрительной скоростью.",
      emoji = "🪶",
      type = AchievementType.GOOD_DEED,
      targetValue = 5,
      evaluateProgress = { _, _, deeds, _ -> deeds }
    ),
    AchievementDefinition(
      id = "deed_10",
      title = "Светлый путь",
      description = "Совершить 10 добрых дел. Черти в панике прячут вилы!",
      emoji = "☀️",
      type = AchievementType.GOOD_DEED,
      targetValue = 10,
      evaluateProgress = { _, _, deeds, _ -> deeds }
    ),
    AchievementDefinition(
      id = "deed_20",
      title = "Святоша района",
      description = "Совершить 20 добрых дел. Твой нимб светит ярче уличных фонарей!",
      emoji = "✨",
      type = AchievementType.GOOD_DEED,
      targetValue = 20,
      evaluateProgress = { _, _, deeds, _ -> deeds }
    )
  )
}
