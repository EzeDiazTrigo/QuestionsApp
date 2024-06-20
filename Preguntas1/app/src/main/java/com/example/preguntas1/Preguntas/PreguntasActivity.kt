package com.example.preguntas1.Preguntas

import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.preguntas1.Menu.MenuActivity.Companion.DEEP_KEY
import com.example.preguntas1.Menu.MenuActivity.Companion.TYPE_KEY
import com.example.preguntas1.Menu.MenuActivity.Companion.WHO_KEY
import com.example.preguntas1.R
import kotlin.random.Random

class PreguntasActivity : AppCompatActivity() {

    private lateinit var tvQuestion: TextView
    private lateinit var btnNext: AppCompatButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_preguntas)
        val deepQuestions:List<String> = listOf(
            "¿Eres más mental o emocional?",
            "¿Eres más mental o emocional?",
            "¿Te resulta fácil aceptar ayuda para lograr tus sueños?",
            "¿Te arrepientes de no haber hecho algo en la vida?",
            "¿Qué es lo que te pone más nervioso?",
            "¿Cómo es la relación con tus padres?",
            "¿Te castigaban mucho cuando eras pequeño?",
            "¿Cuál ha sido la mejor época de tu vida? ¿Y la peor?",
            "¿Qué pesadillas te han provocado sudores más fríos?",
            "¿Cómo te describirías a ti mismo?",
            "¿Qué es lo que más valoras en una amistad?",
            "¿Cuándo fue la última vez que lloraste delante de otra persona?",
            "¿Qué significa para ti nuestra amistad?",
            "¿Cómo te gusta ser reconfortado o animado cuando estás triste o enfadado?",
            "¿Cuál es el momento de tu vida que más te puso a prueba?",
            "¿Te arrepientes de algo? Si es así, ¿de qué?",
            "Si tuvieras todo el valor posible para hacer aquello que más te asusta, ¿qué harías?",
            "¿Qué pensamientos te dificultan dormir por las noches?",
            "¿Qué es lo que más te estresa en la vida?",
            "¿Cuál es la peor decisión que has cometido?",
            "¿Cuál fue tu primera amistad? ¿Sigue en tu vida?",
            "¿Qué objeto tiene para ti mayor valor sentimental?",
            "¿Cómo puedo ser un mejor amigo para ti?",
            "Si pudieras cambiar una sola cosa sobre ti, ¿cuál sería?",
            "¿Qué es lo que más te hace llorar?",
            "¿Tienes alguna manía extraña que no conozco?",
            "¿Quién es la persona más importante de tu vida?",
            "¿Qué fue lo primero que pensaste de mí nada más conocernos?",
            "¿Qué es lo que realmente te saca de tus casillas?",
            "¿Te resulta fácil confiar en alguien?",
            "¿Podrías perdonar a alguien que te ha sido infiel si te enteraras que de todas maneras te ama?",
            "¿Qué persona ha ejercido una influencia decisiva en ti?",
            "¿Qué es lo que más te gusta de mí?",
            "¿Qué es aquello que me hace diferente del resto de personas?",
            "¿Cuándo fue la última vez que te pusiste triste?",
            "¿Por qué acabó tu última relación?",
            "¿Que te hace sentir culpa?",
            "¿Qué cualidades valoras más en alguien?",
            "¿Cómo me describirías?",
            "¿Hay algo que pienses que los demás piensan de ti y no es cierto?",
            "¿Qué cuatro adjetivos te dicen cómo eres?",
            "¿Qué es lo que más te gusta sobre tu persona?",
            "¿Qué es lo que te ha decepcionado más en una amistad?",
            "¿De qué estás más orgulloso en esta vida?",
            "¿De que te arrepientes?",
            "¿Cómo lidias con la ansiedad?",
            "¿Cómo lidias con el estres?",
            "¿Con qué tipo de personas sientes más inseguridad o timidez?",
            "¿Contratarías a un amigo en tu empresa?",
            "¿Preferirías tener un gran talento innato para una actividad, o la capacidad para poder esforzarte mucho?",
            "¿Cuál es tu principal miedo irracional?",
            "Del 1 al 100 que tan estresado/a te sentis",
            "Del 1 al 100 en que nivel esta tu autoestima",
            "Del 1 al 100 cuanta ansiedad consideras tener actualmente",
            "Del 1 al 100 que tanto confias en vos mismo/a",
            "¿A qué le tiene más miedo?",
            "¿Cuáles son tus mayores prioridades en la vida?",
            "¿A quién te pareces más en tu familia?",
            "¿Cómo te enfrentas a los disgustos?",
            "¿Qué es lo que más te apetece hacer cada día?",
            "¿Cuál ha sido tu momento de mayor orgullo?",
            "¿Has perdido a alguien cercano?",
            "¿Eres espiritual?",
            "¿Crees que eres honesto contigo mismo?",
            "¿Quién narraría un audiolibro sobre tu vida?",
            "Si pudieras cambiar algo de tu vida inmediatamente, ¿qué sería?",
            "¿Cuál es tu mayor habilidad?",
            "¿Qué afición siempre has querido practicar?",
            "¿Cómo esperas que te perciba la gente?",
            "¿Qué es lo que más te alegra?",
            "¿Ha perdido alguna vez a un mejor amigo?",
            "¿Qué es lo que te hace sentir más visto?",
            "¿Crees que guardar secretos es malo?",
            "¿Cuándo te sientes más libre?",
            "¿Crees que es bueno guardar rencor?",
            "¿Qué es lo más loco que has hecho por otra persona y por qué lo hiciste?",
            "¿Eres pesimista u optimista?",
            "¿Cuándo fue la última vez que lloraste?",
            "¿Cuál es el lugar más impresionante que has visitado en persona?",
            "¿Qué crees que la gente asume automáticamente cuando te conoce por primera vez?",
            "¿Qué desearías saber cuando eras más joven?",
            "¿Cuál es la mejor mala decisión que has tomado en tu vida?",
            "¿Qué intentarías si supiera que no va a fracasar?",
            "¿Cuándo fue la última vez que intentaste algo nuevo?",
            "¿Con quién te comparas más?",
            "¿Hay alguna lección importante que hayas aprendido por las malas?",
            "¿A qué desearías dedicar más tiempo?",
            "¿Hay algo en lo que sabes que te equivocas?",
            "¿Cuál es tu mayor creencia ciega?",
            "¿Cuál ha sido el momento más decisivo de tu vida hasta ahora?",
            "¿Alguna vez se ha hecho realidad tu miedo más profundo?",
            "¿Merece todo el mundo una segunda oportunidad?",
            "¿Cuál es el encuentro más extraño que has tenido?",
            "¿Qué es lo que te hace sentir más vivo?",
            "¿Cuándo fue la última vez que te sentiste perdido?",
            "¿Qué es lo que más te gusta de ti mismo?",
            "¿Cuál es la pregunta que más miedo te da?",
            "¿Qué es lo que más te gusta de tu personalidad?",
            "¿Qué has hecho como adulto de lo que tu yo más joven estaría orgulloso?",
            "Cuéntame un día en que fuiste feliz",
            "¿A qué lugar te gustaría volver a viajar?",
            "¿Qué te pone la piel de gallina?",
            "¿Cuál es tu lenguaje del amor?",
            "¿Cuál es la forma de expresión personal que no te has atrevido a explorar?",
            "¿Qué recuerdo te hace sonreír instantáneamente?",
            "¿De qué cosa crees que sentirás nostalgia dentro de 30 años?",
            "¿Tienes algún recuerdo de tu infancia que siempre estará contigo?",
            "¿Cómo te sientes mejor apoyado en los momentos difíciles?",
            "¿Qué te gustaría que la gente entendiera mejor de ti?",
            "¿Qué es lo que más te gusta de nuestra amistad? ¿Qué te gustaría mejorar?",
            "¿Cuál es el recuerdo favorito que tienes de nosotros juntos?",
            "¿Te recargas estando rodeado de otras personas o pasando tiempo a solas?",
        )
        val whoIsQuestions:List<String> = listOf(
            "¿Quién es más probable que atienda tu llamada a media noche?",
            "¿Quién es más probable que se sienta como en casa en tu casa?",
            "¿Quién se llevaría bien con los padres de todos?",
            "¿Quién es más probable que le organice una fiesta sorpresa a alguien?",
            "¿Quién tiene más probabilidades de ser un gran chófer profesional?",
            "¿Quién es más probable que haga a mano tu regalo de cumpleaños?",
            "¿Quién es más probable que haga reír a alguien cuando se siente triste?",
            "¿Quién es más probable que te haga una torta por tu cumpleaños?",
            "¿A quién es más probable que se le ocurra apodos para todos?",
            "¿Quién es más probable que responda un mensaje de texto o correo electrónico de inmediato?",
            "¿Quién es más probable que te preste su libro favorito?",
            "¿Quién es más probable que se olvide de devolver un libro que pidió prestado?",
            "¿Quién es más probable que se quede despierto toda la noche mirando su teléfono?",
            "¿Quién es más probable que sepa todos los chismes de la oficina?",
            "¿Quién tiene más probabilidades de quedarse dormido durante una reunión?",
            "¿Quién es más probable que guarde un secreto importante?",
            "¿Quién tiene más probabilidades de olvidarse un cumpleaños?",
            "¿Quién es más probable que gaste todo su sueldo en ropa o zapatos?",
            "¿Quién es más probable que deje caer su teléfono nuevo y lo rompa?",
            "¿Quién es más probable que olvide la bolsa de almuerzo que empacó?",
            "¿Quién es más probable que pierda un vuelo?",
            "¿Quién tiene más probabilidades de dar el mejor regalo del Amigo Invisible?",
            "¿Quién es más probable que tenga a su mascota en su regazo durante una reunión o llamada?",
            "¿Quién es más probable que se olvide de silenciar su micrófono durante una reunión o conferencia telefónica?",
            "¿Quién es más probable que diga algo importante mientras está silenciado en una llamada?",
            "¿Quién tiene más probabilidades de tener 20 pestañas abiertas en su escritorio?",
            "¿Quién tiene más probabilidades de hacer trampa en un juego de mesa?",
            "¿Quién tiene más probabilidades de presionar 5 veces el botón de posponer en el despertador?",
            "¿Quién es más probable que duerma mientras suena la alarma?",
            "¿Quién es más probable que pase horas leyendo chismes de celebridades?",
            "¿Quién es más probable que te anime en un día difícil?",
            "¿Quién está más dispuesto a ayudar a resolver un problema?",
            "¿Quién es más probable que te haga olvidar tus problemas por un tiempo?",
            "¿Quién es más probable que tenga un oído disponible para escuchar?",
            "¿Quién tiene más probabilidades de convertirse en un gran líder en el futuro?",
            "¿Quién tiene más probabilidades de salvar el día cuando las cosas salen mal?",
            "¿Quién es más probable que diga cosas buenas de ti a tus espaldas?",
            "¿Quién es más probable que te inspire?",
            "¿Quién tiene más probabilidades de sacar lo mejor de las personas que conoce?",
            "¿Quién tiene más probabilidades de tener un impacto en la historia?",
            "¿Quién es más probable que ofrezca apoyo moral a un amigo?",
            "¿Quién es más probable que te dé el regalo perfecto?",
            "¿Quién es más probable que ayude a un extraño?",
            "¿Quién es más probable que realice un acto de bondad al azar?",
            "¿Quién tiene más probabilidades de hacer nuevos amigos en un lugar nuevo?",
            "¿Quién tiene más probabilidades de entrenar a otros de alguna manera?",
            "¿Quién es más probable que coma pizza fría?",
            "¿Quién es más probable que coma desayuno en la cena?",
            "¿Quién es más propenso a olvidarse la billetera cuando sale a comer?",
            "¿Quién es más probable que hable en una sala de cine durante una función?",
            "¿Quién es más probable que regañe a otros por hablar en una sala de cine durante una función?",
            "¿Quién tiene más probabilidades de completar un desafío en público que conduzca a un arresto?",
            "¿Quién es más probable que use todas las mantas cuando duerme?",
            "¿Quién es más probable que sea el planificador de viajes para las vacaciones de sus familiares o amigos?",
            "¿Quién tiene más probabilidades de ver un programa completo en un solo día?",
            "¿Quién es más probable que se olvide de comprar alimentos para la semana?",
            "¿Quién es más probable que se coma todo el pochoclo durante una película?",
            "¿Quién es más probable que grite pidiendo ayuda cuando ve una araña?",
            "¿Quién tiene más probabilidades de matar una araña cuando la ve?",
            "¿Quién es más probable que se mude a una cabaña en el bosque?",
            "¿Quién es más probable que haga voces distintas para los personajes cuando lee un libro en voz alta?",
            "¿Quién tiene más probabilidades de hacerse un tatuaje vergonzoso?",
            "¿Quién tiene más probabilidades de copiar en un examen?",
            "¿Quién tiene más probabilidades de orinarse en la ducha?",
            "¿Quién tiene más probabilidades de no cambiar nunca las sábanas?",
            "¿Quién tiene más probabilidades de llegar tarde?",
            "¿Quién suele faltar a clase?",
            "¿Quién tiene más probabilidades de contestar a un profesor?",
            "¿Quién tiene más probabilidades de convertirse en diseñador de interiores?",
            "¿Quién tiene más probabilidades de responder a un mensaje de texto tres semanas después?",
            "¿Quién tiene más probabilidades de vivir en el extranjero?",
            "¿Quién tiene más probabilidades de ser médico?",
            "¿Quién tiene más probabilidades de dar los mejores consejos?",
            "¿Quién tiene más probabilidades de crear una empresa?",
            "¿Quién tiene más probabilidades de hacerse vegano?",
            "¿Quién tiene más probabilidades de viajar al espacio?",
            "¿Quién tiene más probabilidades de mantener la calma durante una gran tormenta?",
            "¿Quién tiene más probabilidades de dejarlo todo para ayudar a un desconocido?",
            "¿Quién tiene más probabilidades de pintar una obra famosa?",
            "¿Quién tiene más probabilidades de marcharse sin despedirse?",
            "¿Quién es el más propenso a dejar caer su ordenador portátil?",
            "¿Quién tiene más probabilidades de enseñarte algo nuevo?",
            "¿Quién tiene más probabilidades de convertirse en profesor?",
            "¿Quién tiene más probabilidades de viajar por el mundo?",
            "¿Quién tiene más probabilidades de crear un podcast?",
            "¿Quién tiene más probabilidades de dar el mejor abrazo?",
            "¿Quién tiene más probabilidades de escribir una carta a mano?",
            "¿Quién tiene más probabilidades de escribir un libro?",
            "¿Quién tiene más probabilidades de planificar las vacaciones de todo el grupo?",
            "¿Quién es más propenso a empezar peleas?",
            "¿Quién es mas probable que pida perdón primero?",
            "¿Quién es mas probable que organice las salidas más divertidas?",
            "¿Quién es más proable que cuente una historia una y otra vez?",
            "¿Quién tiene más probabilidades de cancelar una salida?",
            "¿Quién tiene más probabilidades de comer del plato de otra persona?",
            "¿Quién se duerme mas temprano?",
            "¿Quien es el que siempre propone salidas?",
            "¿Quien es el psicologo del grupo?",
            "¿Quien es el que siempre falta a las juntadas?",
            "¿Quien es el que cocina mejor del grupo?",
        )
        val type:String = intent.extras?.getString(TYPE_KEY) ?: ""
        tvQuestion = findViewById(R.id.Question)
        btnNext = findViewById(R.id.Next)

        when(type){
            WHO_KEY -> randomWhoIsQuestion(whoIsQuestions)
            DEEP_KEY -> randomDeepQuestion(deepQuestions)
        }

        btnNext.setOnClickListener {generateQuestion(type, whoIsQuestions, deepQuestions)}

    }

    private fun generateQuestion(type: String, whoIsQuestions: List<String>, deepQuestions: List<String>) {
        when(type){
            WHO_KEY -> randomWhoIsQuestion(whoIsQuestions)
            DEEP_KEY -> randomDeepQuestion(deepQuestions)
        }
    }

    private fun randomWhoIsQuestion(whoIsQuestions: List<String>) {
        val random = Random.nextInt(0, whoIsQuestions.size+1)
        tvQuestion.text = whoIsQuestions[random]
    }

    private fun randomDeepQuestion(deepQuestions: List<String>) {
        val random = Random.nextInt(0, deepQuestions.size+1)
        tvQuestion.text = deepQuestions[random]
    }
}