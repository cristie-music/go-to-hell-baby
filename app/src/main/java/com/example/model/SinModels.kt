package com.example.model

import androidx.compose.ui.graphics.Color

enum class SinCategory(
  val titleRu: String,
  val emoji: String,
  val badgeColor: Long,
  val description: String
) {
  GLUTTONY(
    titleRu = "Чревоугодие",
    emoji = "🌭",
    badgeColor = 0xFFFF7043,
    description = "Победа желудка над разумом"
  ),
  SLOTH(
    titleRu = "Лень и Уныние",
    emoji = "🛋️",
    badgeColor = 0xFF7E57C2,
    description = "Диван засосал навечно"
  ),
  WRATH(
    titleRu = "Гнев и Ярость",
    emoji = "🔥",
    badgeColor = 0xFFE53935,
    description = "Все бесят, особенно люди"
  ),
  PRIDE(
    titleRu = "Гордыня и Понты",
    emoji = "👑",
    badgeColor = 0xFFFFA000,
    description = "Я тут главный пуп земли"
  ),
  ENVY(
    titleRu = "Зависть",
    emoji = "👀",
    badgeColor = 0xFF43A047,
    description = "Почему у них есть, а у меня нет?!"
  ),
  GREED(
    titleRu = "Жадность",
    emoji = "💰",
    badgeColor = 0xFFFFB300,
    description = "Моё! Никому не отдам!"
  ),
  LUST(
    titleRu = "Похоть и Флирт",
    emoji = "💋",
    badgeColor = 0xFFE91E63,
    description = "Слабость плоти победила"
  )
}

data class SinItem(
  val id: String,
  val title: String,
  val category: SinCategory,
  val points: Int,
  val funnyQuote: String,
  val emoji: String
)

data class SinRecord(
  val id: String,
  val title: String,
  val category: SinCategory,
  val points: Int,
  val timestamp: Long,
  val funnyQuote: String
)

object DefaultSins {
  val list = listOf(
    // Чревоугодие
    SinItem(
      id = "glut_1",
      title = "Ночной дожор у холодильника в 3:00",
      category = SinCategory.GLUTTONY,
      points = 350,
      funnyQuote = "Колбаса сама просилась в рот, ты просто спасал её от одиночества!",
      emoji = "🥪"
    ),
    SinItem(
      id = "glut_2",
      title = "Съел(а) целую пиццу в одно лицо",
      category = SinCategory.GLUTTONY,
      points = 450,
      funnyQuote = "Делиться пиццей — грех ещё похуже, так что ты молодец!",
      emoji = "🍕"
    ),
    SinItem(
      id = "glut_3",
      title = "Запил пивком и сказал 'с понедельника на диету'",
      category = SinCategory.GLUTTONY,
      points = 400,
      funnyQuote = "Понедельник — понятие абстрактное, а холодное пенное — осязаемое!",
      emoji = "🍺"
    ),
    SinItem(
      id = "glut_4",
      title = "Слопал чужой йогурт из офисного холодильника",
      category = SinCategory.GLUTTONY,
      points = 600,
      funnyQuote = "Там не было фамилии, значит ничейный! Адский суд оправдал тебя.",
      emoji = "🥣"
    ),
    SinItem(
      id = "glut_5",
      title = "Заказал ведро фастфуда на последние деньги",
      category = SinCategory.GLUTTONY,
      points = 500,
      funnyQuote = "Гормон счастья получен, а за квартиру заплатит кто-нибудь другой.",
      emoji = "🍟"
    ),

    // Лень и Уныние
    SinItem(
      id = "sloth_1",
      title = "Пролежал все выходные в кровати, листая шортсы",
      category = SinCategory.SLOTH,
      points = 400,
      funnyQuote = "Ты не ленился, ты тестировал силу гравитации на прочность!",
      emoji = "📱"
    ),
    SinItem(
      id = "sloth_2",
      title = "Перевел будильник 8 раз подряд и опоздал",
      category = SinCategory.SLOTH,
      points = 350,
      funnyQuote = "Работа не волк, работа — стресс, а сон — бесплатная терапия.",
      emoji = "⏰"
    ),
    SinItem(
      id = "sloth_3",
      title = "Заказал доставку из магазина в соседнем подъезде",
      category = SinCategory.SLOTH,
      points = 300,
      funnyQuote = "Ты помогаешь экономике курьеров развиваться! Чертовка хлопает в ладоши.",
      emoji = "🛵"
    ),
    SinItem(
      id = "sloth_4",
      title = "Купил абонемент в спортзал и ни разу не пошёл",
      category = SinCategory.SLOTH,
      points = 750,
      funnyQuote = "Главное было поддержать индустрию фитнеса финансово!",
      emoji = "🏋️"
    ),
    SinItem(
      id = "sloth_5",
      title = "Оставил мытьё посуды 'отмокать' на трое суток",
      category = SinCategory.SLOTH,
      points = 450,
      funnyQuote = "Там уже зародилась новая цивилизация, не мешай эволюции!",
      emoji = "🍽️"
    ),

    // Гнев и Ярость
    SinItem(
      id = "wrath_1",
      title = "Покрыл матом подрезавшего водителя / таксиста",
      category = SinCategory.WRATH,
      points = 400,
      funnyQuote = "Ты просто обогатил великий русский язык экспрессией!",
      emoji = "🚗"
    ),
    SinItem(
      id = "wrath_2",
      title = "Наорал на компьютер/телефон за то, что завис",
      category = SinCategory.WRATH,
      points = 350,
      funnyQuote = "Восстание машин подавлено твоим грозным криком!",
      emoji = "💻"
    ),
    SinItem(
      id = "wrath_3",
      title = "Токсично разнёс кого-то в комментариях в 2 часа ночи",
      category = SinCategory.WRATH,
      points = 550,
      funnyQuote = "Кто-то был неправ в интернете, ты не мог(ла) оставаться в стороне!",
      emoji = "⌨️"
    ),
    SinItem(
      id = "wrath_4",
      title = "Сломал геймпад/мышку после проигрыша в катке",
      category = SinCategory.WRATH,
      points = 800,
      funnyQuote = "Тимейты были раками, твоей вины тут точно нет!",
      emoji = "🎮"
    ),

    // Гордыня и Понты
    SinItem(
      id = "pride_1",
      title = "Сделал 84 селфи, чтобы выложить одно с цитатой волка",
      category = SinCategory.PRIDE,
      points = 500,
      funnyQuote = "Безумно можно быть первым! Мир должен знать своих героев.",
      emoji = "📸"
    ),
    SinItem(
      id = "pride_2",
      title = "Сказал 'Я же говорил(а)!', хотя сам до конца не знал",
      category = SinCategory.PRIDE,
      points = 350,
      funnyQuote = "Триумф интуиции и тотального самодовольства!",
      emoji = "😏"
    ),
    SinItem(
      id = "pride_3",
      title = "Слушал музыку в общественном месте без наушников",
      category = SinCategory.PRIDE,
      points = 900,
      funnyQuote = "В Аду для тебя уже бронируют спец-люкс с отдельным DJ!",
      emoji = "🔊"
    ),
    SinItem(
      id = "pride_4",
      title = "Рассуждал о высоком искусстве, прочитав один пост в Википедии",
      category = SinCategory.PRIDE,
      points = 450,
      funnyQuote = "Интеллектуальная элита прибыла на вечеринку грешников!",
      emoji = "🧐"
    ),

    // Зависть
    SinItem(
      id = "envy_1",
      title = "Сталкерил соцсети бывших и их новых пассий всю ночь",
      category = SinCategory.ENVY,
      points = 500,
      funnyQuote = "Аналитический отдел ФСБ позавидовал бы твоей слежке!",
      emoji = "🕵️"
    ),
    SinItem(
      id = "envy_2",
      title = "Обесценил чужой успех: 'Ему просто повезло / папик купил'",
      category = SinCategory.ENVY,
      points = 400,
      funnyQuote = "Справедливость восстановлена твоим едким сарказмом!",
      emoji = "😒"
    ),
    SinItem(
      id = "envy_3",
      title = "Скривил лицо, когда друг похвастался новой машиной",
      category = SinCategory.ENVY,
      points = 350,
      funnyQuote = "Искренняя радость за друзей? Нет, не слышали!",
      emoji = "🚘"
    ),

    // Жадность
    SinItem(
      id = "greed_1",
      title = "Не скинулся на подарок коллеге, сославшись на 'кризис'",
      category = SinCategory.GREED,
      points = 450,
      funnyQuote = "Финансовая грамотность превыше корпоративной этики!",
      emoji = "💸"
    ),
    SinItem(
      id = "greed_2",
      title = "Забрал из кафе все салфетки, сахар и зубочистки",
      category = SinCategory.GREED,
      points = 300,
      funnyQuote = "Всё уже включено в стоимость твоего американо!",
      emoji = "🧂"
    ),
    SinItem(
      id = "greed_3",
      title = "Спустил ползарплаты на импульсивные покупки на маркетплейсе",
      category = SinCategory.GREED,
      points = 650,
      funnyQuote = "Этот спиннер в форме черепа тебе был жизненно необходим!",
      emoji = "📦"
    ),

    // Похоть и Искушение
    SinItem(
      id = "lust_1",
      title = "Отправил сообщение 'Спишь?' в 2:30 ночи бывшему/бывшей",
      category = SinCategory.LUST,
      points = 700,
      funnyQuote = "Классика ночного грехопадения! Чертовка ставит 10 из 10!",
      emoji = "💬"
    ),
    SinItem(
      id = "lust_2",
      title = "Флиртовал чисто ради бесплатного коктейля / кофе",
      category = SinCategory.LUST,
      points = 400,
      funnyQuote = "Обаяние — это валюта, ты просто расплачиваешься взглядом!",
      emoji = "🍸"
    ),
    SinItem(
      id = "lust_3",
      title = "Лайкнул 40 чужих пикантных фоток с фейкового аккаунта",
      category = SinCategory.LUST,
      points = 550,
      funnyQuote = "Конспирация на уровне спецагента преисподней!",
      emoji = "🔥"
    )
  )
}

object PraiseQuotes {
  val quotes = listOf(
    "Умничка! Ждем тебя в АДУ! Твой персональный котел уже заказан и греется! 🔥😈",
    "Какая красота! Еще чуть-чуть, и тебе выдадут VIP-пропуск на самую горячую сковородку! 💋🔥",
    "Чертовски хорош(а)! Сатана лично поставил тебе лайк в реестре почетных грешников! 🤘😈",
    "Ты делаешь невероятные успехи! Черти передают воздушный поцелуй и уже натирают вилы до блеска! 💅🔥",
    "Так держать, сладенький грешник! В аду кондиционеров нет, зато компания будет просто огонь! 🥂🔥",
    "Твои грехи — просто произведение искусства! Адская бухгалтерия аплодирует стоя! 🍷😈"
  )
}
