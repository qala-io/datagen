package io.qala.datagen;

public class RandomValueRecorder {
    private final RandomValue value;
    private final RandomString.Type stringType;

    public RandomValueRecorder(RandomValue value, RandomString.Type stringType) {
        this.value = value;
        this.stringType = stringType;
    }

    public String generate() {
        return stringType.generate(value);
    }
}
