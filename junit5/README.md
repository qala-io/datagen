Randomized Tests with JUnit5
----------------------------

[![Maven Central](https://maven-badges.herokuapp.com/maven-central/io.qala.datagen/qala-datagen-junit5/badge.svg)](http://search.maven.org/#search%7Cga%7C1%7Cg%3A%22io.qala.datagen%22)
[![experimental](http://badges.github.io/stability-badges/dist/experimental.svg)](http://github.com/badges/stability-badges)

You can use Datagen + JUnit5 integration to facilitate randomization in parameterized tests. Examples:

```java
@Alphanumeric(length = 1, name = "min boundary")
@Alphanumeric(min = 2, max = 29, name = "middle value")
@Alphanumeric(length = 30, name = "max boundary")
@English(max=30)
@Unicode(max=30)
@Numeric(max=30)
void invokedForEachAnnotation(String value, String name) {
    assertTrue(value.length() >= 1 && value.length() <= 31, "Failed case: " + name);
}
```

This will run the test 6 times with different parameters according to the annotations. These tests will run 2 times
each:

```java
@RandomInt(min = 1, name = "greater than zero")
@RandomInt(max = -1, name = "less than zero")
void zeroIsNotPassed(int param, String name) {
    assertNotEquals(0, param, "Failed case: " + name);
}

@RandomLong(min = 1, name = "greater than zero")
@RandomLong(max = -1, name = "less than zero")
void zeroIsNotPassed(long value, String name) {
    assertNotEquals(0, value, "Failed case: " + name);
}

@RandomDouble(min = 1, name = "greater than zero")
@RandomDouble(max = -1, name = "less than zero")
void zeroIsNotPassed(double value, String name) {
    assertFalse(value > -1 && value < 1, "Failed case: " + name);
}
```

In the end results look like this:
[![IntelliJ output](https://photos-3.dropbox.com/t/2/AAAKXLKisD5KWZSnkHonWgswSTFSE3BDI6ok3jHaI32VvA/12/2397949/png/32x32/3/1497142800/0/2/datagen-docs-junit5-test-results.png/ENCQ7gEYs-OnQSAHKAc/jJI1HI8_Zl_IzmC14CNLg3VrsyKjSZIJRiEzlimctMs?dl=0&size=1280x960&size_mode=3)](../examples/src/test/java/io/qala/datagen/examples/junit5/JUnit5ExampleTest.java)

Though if you need to run a test only once and you want to use randomization - it's going to be more concise to use 
[Datagen API](./../README.md) directly. More examples are available in 
[the test](src/test/java/io/qala/datagen/junit5/Junit5ParameterizedTest.java).

This integration is not stable yet because JUnit5 itself is not stable. But if you're not afraid of the fact that the 
API may change in the future, you can give it a try. In order for this to work you need the latest snapshot of JUnit5:
```xml
<dependencies>
    <dependency>
        <groupId>io.qala.datagen</groupId>
        <artifactId>qala-datagen-junit5</artifactId>
        <version>1.14.0</version>
        <scope>test</scope>
    </dependency>

    <dependency>
        <groupId>org.junit.jupiter</groupId>
        <artifactId>junit-jupiter-params</artifactId>
        <version>5.0.0-SNAPSHOT</version>
        <scope>test</scope>
    </dependency>
    <dependency>
        <groupId>org.junit.jupiter</groupId>
        <artifactId>junit-jupiter-engine</artifactId>
        <version>5.0.0-SNAPSHOT</version>
        <scope>test</scope>
    </dependency>
    <dependency>
        <groupId>org.junit.platform</groupId>
        <artifactId>junit-platform-commons</artifactId>
        <version>1.0.0-SNAPSHOT</version>
        <scope>test</scope>
    </dependency>
    <dependency>
        <groupId>org.junit.platform</groupId>
        <artifactId>junit-platform-surefire-provider</artifactId>
        <version>1.0.0-SNAPSHOT</version>
        <scope>test</scope>
    </dependency>
</dependencies>

<build>
    <plugins>
        <plugin>
            <artifactId>maven-surefire-plugin</artifactId>
            <version>2.20</version>
            <dependencies>
                <dependency>
                    <groupId>org.junit.platform</groupId>
                    <artifactId>junit-platform-surefire-provider</artifactId>
                    <version>1.0.0-SNAPSHOT</version>
                </dependency>
                <dependency>
                    <groupId>org.junit.jupiter</groupId>
                    <artifactId>junit-jupiter-engine</artifactId>
                    <version>5.0.0-SNAPSHOT</version>
                </dependency>
            </dependencies>
        </plugin>
    </plugins>
</build>
```