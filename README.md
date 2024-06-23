# QuestionsApp

## Proximos pasos
1. Agregar boton con un "+" y otro con un tacho de basura en cada esquina de abajo de la actividad de preguntas
2. El boton del + abrira un modal para rellenar con la nueva pregunta y la guardara en sharedPreferences
3. El boton del tacho eliminara la pregunta de la lista y actualizara la lista en sharedPreferences
4. Hacer que el cardView de la pregunta se divida en dos mitades
5. La mitad izquierda volvera a la pregunta anterior y la mitad derecha pasara a la siguiente pregunta random.
   Para esto guardar el numero random de acceso a la ultima pregunta mostrada en una lista, y cada vez que se genere un nuevo numero random se debera verificar que este no pertenesca a la lista de preguntas realizadas.
   De esta forma se evitara que se repita la pregunta y se podra volver para atras a preguntas anteriores
6. Agregar un boton de reiniciar que vaciara la lista de preguntas realizadas para que se puedan volver a mostrar sin tener que reiniciar la aplicación
