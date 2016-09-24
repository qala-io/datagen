Person Greetings
----------------
Given person with `Username`, `First Name`, `Last Name`:

* It: Greets with first and last name if both are present. E.g. "Hello, Mickey Mouse"
* It: Greets with username if first or last name is missing (or both). E.g. "Hello, admin"

## Sample Based Tests
### Tests: uses first and last name if both are present
|#  |First Name|Last Name|
|---|---       |---      |
|1  | Mickey   |Mouse    |

### Tests: Uses username if first or last name is missing (or both)
|#  |First Name|Last Name|
|---|---       |---      |
|1  |null      |null     |
|2  |Mickey    |null     |
|3  |null      |Mouse    |
|4  |""        |""       |
|5  |Mickey    |""       |
|6  |""        |Mouse    |
|7  |" "       |" "      |
|8  |Mickey    |" "      |
|9  |" "       |Mouse    |
|10 |null      |""       |
|11 |null      |" "      |
|12 |""        |null     |
|13 |""        |" "      |

## Randomized Tests
### Tests: uses first and last name if both are present
|#  |First Name|Last Name|
|---|---       |---      |
|1  | Mickey   |Mouse    |

### Tests: Uses username if first or last name is missing (or both)
|#  |First Name             |Last Name                       |
|---|---                    |---                             |
|1  |Mickey                 |`sample("", " ", null)`         |
|2  |`sample("", " ", null)`|`sample("", " ", null, "Mouse")`|

## Resume 

See **[example in JUnit + Datagen](./src/test/java/io/qala/datagen/examples/CombinatorialGreetingsTest.java)**

**Bonuses** that you get from randomization:

* Faster to write and easier to maintain (1 or 2 instead of 13)
* Better coverage. Few people would cover all 13 cases, so better to have 1 randomized test than 1 sample based test.
* Much faster test runs (again because we reduced the number of tests). This is especially visible when running 
[Component or System tests](http://qala.io/blog/holes-in-test-terminology.html).

**Be careful** replacing full combinatorial testing with randomization:

* Critical parts of the app still need to be covered with full combinations. Otherwise you may miss something during 
this run but not the other
* Use your IDE to repeat the tests N times when first writing randomized tests and when changing parts that may break.
In IntelliJ it's `Edit Configuration` of your test and then change `Repeat` option. This works with JUnit only as for 
09/2016.