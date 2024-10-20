package app.game.preguntas1.daily

data class ChallengeData(
    var streak: Int,
    var cbYesterday: Boolean,
    var cbToday: Boolean,
    var lastDate: String,
    var tempLastDate: String,
    var tempLastDateAux: String
)

