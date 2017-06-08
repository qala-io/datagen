Fighting with Unique Constraints
--------------------------------
Preconditions: 

* Our Person has unique `username` - 2 people cannot register with the same username.
* Our test is run against a remote instance of the app and Persons persist between 2 test runs

The 1st time we run the test everything is great:

```java
@Test public void canRegisterNewUser() {
        String username = "feynman";
        RestService.instance.save(new Person(username));
    }
```

The 2nd time we run it - it fails because user `feynman` already exists in DB. What do we do?

## Option 1: Clear DB between test runs

* Write an external shell script that logs in to the DB and clears it. Ouch! This appears to be more
_complicated_ than it looks.
* The script needs to be run both from CI Server as well as on local machines of devs/testers.
* Won't save when multiple people are running tests at the same time against the same env

## Option 2: Clear tests data before test run

* Again - _complicated_ since for each unique piece of data we need a way to clean it.
* Need to write logic to access DB? If DB is refactored the tests would fail.
* Need to write backdoors in the API? Still additional efforts. And need to keep that API from production some how.
* Still won't save when multiple people are running tests against the same env

## Option 3: Randomization

```java
@Test public void canRegisterNewUser() {
    String username = alphanumeric(1, 20);
    RestService.instance.save(new Person(username));
}
```