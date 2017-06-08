Which one to pick?
------------------

Often we face a choice where there is no clear winner. E.g. when testing happy path for user's first name
should we choose John, Adam, Chris or what? Even if we choose one - aren't we afraid that with new functionality other
examples may fail while the hardcoded example keeps passing (a.k.a. Pesticides Effect)?

## Story about countries

Once we were testing a dropdown with countries (there are something like 250 of them) on Selenium. We didn't care which
one to choose (e.g. UK?) - it wasn't relevant, we just need to ensure the value gets saved on the server. So we could 
pick something popular and run the tests with that example every time. But we chose to randomize. At some point
it appeared that `Cote d'Ivoire` wasn't working because of it's apostrophe that wasn't copied correctly from COUNTRIES
table by one of the team members.

## Traditional Example (username)

```java
@Test public void usernameValidation_passesHappyPath_traditional() {
    new Person("John").validate();
}
```

## Randomization Example (username)

```java
@Test public void usernameValidation_passesHappyPath_randomization() {
    new Person(english(1, 50)).validate();
}
```

## Traditional Example (country)

```java
@Test public void countryValidation_passesHappyPath_traditional() {
    new Person("John").country(Country.US).validate();
}
```

## Randomization Example (country)

```java
@Test public void countryValidation_passesHappyPath_randomization() {
    new Person(english(1, 50)).country(Country.random()).validate();
}
```

`Country.random()` method:

```java
public static Country random() {
    return sample(values());
}
```