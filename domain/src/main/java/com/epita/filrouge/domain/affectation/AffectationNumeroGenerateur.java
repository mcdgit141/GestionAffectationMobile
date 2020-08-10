package com.epita.filrouge.domain.affectation;

import java.util.Random;

public class AffectationNumeroGenerateur {

    public static Long genererNumeroAffectation() {
        final Random random = new Random();
        return random.nextLong();
    }
}
