package ba.unsa.etf.rma.data

fun favoriteMovies(): List<Movie> {
    return listOf(
            Movie(1,"Pride and prejudice",
                    "Sparks fly when spirited Elizabeth Bennet meets single, rich, and proud Mr. Darcy. But Mr. Darcy reluctantly finds himself falling in love with a woman beneath his class. Can each overcome their own pride and prejudice?",
                    "16.02.2005.",
                    "drama",null),
            Movie(2,"Matrix", "Film starring Keanu Reeves","04.03.1999.","action",null),
            Movie(3,"Jurassic Park", "Film starring dinosaurus","04.03.1994.","scifi",null),
            Movie(4,"Shutter Island", "Film starring Leonardo DiCaprio","04.03.2012.","thriller",null),
            Movie(5,"Spiderman", "Film starring spider","04.03.2005.", "action",null),
            Movie(5,"Spiderman 2", "Film starring spider","04.03.2005.","action",null),
            Movie(5,"Spiderman 3", "Film starring spider","04.03.2005.","action",null)
    )
}

fun recentMovies(): List<Movie> {
    return listOf(
            Movie(1,"The Courier",
                    "Cold War spy Greville Wynne and his Russian source try to put an end to the Cuban Missile Crisis.",
                    "17.05.2021.","thriller",null),
            Movie(2,"The Courier",
                    "Cold War spy Greville Wynne and his Russian source try to put an end to the Cuban Missile Crisis.",
                    "17.05.2021.",
                    "thriller",null),
            Movie(3,"The Courier",
                    "Cold War spy Greville Wynne and his Russian source try to put an end to the Cuban Missile Crisis.",
                    "17.05.2021.",
                    "thriller",null),
            Movie(4,"The Courier",
                    "Cold War spy Greville Wynne and his Russian source try to put an end to the Cuban Missile Crisis.",
                    "17.05.2021.",
                    "thriller",null),
            Movie(5,"The Courier",
                    "Cold War spy Greville Wynne and his Russian source try to put an end to the Cuban Missile Crisis.",
                    "17.05.2021.",
                    "thriller",null)

    )
}