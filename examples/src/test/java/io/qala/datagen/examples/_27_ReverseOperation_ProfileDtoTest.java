package io.qala.datagen.examples;

import org.junit.Test;

import static org.unitils.reflectionassert.ReflectionAssert.assertReflectionEquals;

public class _27_ReverseOperation_ProfileDtoTest {
    @Test public void transformingBackToEntity_resultsInSameEntity() {
        Person original = Person.random();
        ProfileDto dto = ProfileDto.fromEntity(original);
        Person transformed = dto.toEntity();
        assertReflectionEquals(original, transformed);
    }
}