package io.qala.datagen.examples;

import java.util.UUID;

import static io.qala.datagen.RandomShortApi.Double;
import static io.qala.datagen.RandomShortApi.*;

class Material {
    private String id = UUID.randomUUID().toString();
    private String name;
    private double mass;
    private double molecularWeight;

    static Material random() {
        Material result = new Material();
        result.mass = positiveDouble();
        result.name = alphanumeric(11);
        result.molecularWeight = Double(100, 1500);
        return result;
    }
}