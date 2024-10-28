package app.game.preguntas1.daily

data class ChallengeData(
    var streak: Int,
    var challengeHistory: MutableMap<String, Boolean>
)

