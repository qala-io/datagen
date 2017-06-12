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

# Seeds

Seed is a number that determines which random values are generated. By default it's initialized with `System.nanoTime()`
but it's possible to hardcode it, in that case every time the test is run the same random data is going to be generated:

```java
@Test @Seed(123)
void explicitSeed_generatesSameDataEveryTime() {
    assertEquals("56847945", numeric(integer(1, 10)));
    assertEquals("0o2V9KpUJc6", alphanumeric(integer(1, 20)));
    assertEquals("lfBi", english(1, 10));
    assertEquals(1876573356364443993L, Long());
    assertEquals(-8.9316016195567002E18, Double());
    assertEquals(false, bool());
}
```

This will work for parameterized tests as well. It's possible to set the `@Seed` per class which would make all the
test methods generate the same values over and over again.

Usually you don't need to set the seed, but if your test fails it's nice if you could reproduce it exactly again -
that's the primary use case for setting seeds manually. If you add this extension to your test classes it will put the
method and class seeds into logs (use SLF4J implementations) if a test fails:

```java
@ExtendWith(DatagenSeedExtension.class)
class Junit5ParameterizedTest {}
```

According to [JUnit5 docs](http://junit.org/junit5/docs/current/user-guide/#extensions-registration) you can pass system
argument to enable auto-registration instead of marking each class with `@ExtendWith`:

```
-Djunit.extensions.autodetection.enabled=true
```

The output may look something like this:

```
Random Seeds:  testMethod[162024700321388] NestedTestClass[162024700321388] EnclosingTestClass[286157404280696]
```


## Seed Caveats

1. Right now JUnit5 [doesn't have a deterministic order of tests](https://github.com/junit-team/junit5/issues/111#issuecomment-307644332).
This means that if you run your tests twice the order can change. If you put `@Seed` on your class and that
actually happens - it will generate different data in some cases.
This is especially possible if your tests start with the same name and then are followed by underscores. So if you
use `@Seed` you may need to run the test class multiple times before the order repeats. Or just put the annotation on
methods instead.

2. If you use `@MethodSource` the `@Seed` is applied only after the data came from the data methods. This is due to
[current restrictions of JUnit5](https://github.com/junit-team/junit5/issues/883) which doesn't have any callbacks
to impact `@MethodSource`. So this seed will not work:

```java
@Seed(1234)
@ParameterizedTest
@MethodSource("numericsMethod")
void test(String value) {
    assertFalse(value.contains(english(1)));
}
private static Stream<? extends Arguments> numericsMethod() {
    return Stream.of(numeric(10)).map(Arguments::of);
}
```

# Add to your project

This integration is not stable yet because JUnit5 itself is not stable. But if you're not afraid of the fact that the 
API may change in the future, you can give it a try. In order for this to work you need the latest snapshot of JUnit5:
```xml
<dependencies>
    <dependency>
        <groupId>io.qala.datagen</groupId>
        <artifactId>qala-datagen-junit5</artifactId>
        <version>2.0.0</version>
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