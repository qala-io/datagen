Randomized Tests with JUnit5
----------------------------

You can use Datagen + JUnit5 integration to facilitate randomization in parameterized tests. Examples:

```
    @Alphanumeric(length = 1, name = "min boundary")
    @Alphanumeric(min = 2, max = 29, name = "middle value")
    @Alphanumeric(length = 30, name = "max boundary")
    void validationPasses_forValidName(String dogName) {
        assertTrue(new Dog(dogName).isValid());
    }
```

This will run the test 3 times with different parameters according to the annotations. This test will run 2 times: 

```
    @RandomInt(min = 1, name = "greater than zero")
    @RandomInt(max = -1, name = "less than zero")
    void zeroIsNotPassed(int param) {
        assertNotEquals(0, param);
    }
```

Though if you need to run a test only once and you want to use randomization - it's going to be more concise to use 
[Datagen API](./../README.md) directly. More examples are available in 
[the test](src/test/java/io/qala/datagen/junit5/Junit5ParameterizedTest.java).

This integration is not stable yet because JUnit5 itself is not stable. But if you're not afraid of the fact that the 
API may change in the future, you can give it a try. In order for this to work you need the latest snapshot of JUnit5:
```
    <dependencies>
        <dependency>
            <groupId>io.qala.datagen</groupId>
            <artifactId>qala-datagen-junit5</artifactId>
            <version>1.11.0</version>
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