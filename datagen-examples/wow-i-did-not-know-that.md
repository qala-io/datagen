Wow I didn't know that
----------------------
When crafting examples for test cases they are restricted to what we know. We know best practices like testing boundary
values, right? But what if there is something that we didn't know and thus couldn't test for? Randomization can help 
with that.

Given: 

* There is a validation on Back End side - username length is restricted by 50
* There is a DB constraint too - username length is restricted by 128 (on DB level we often have wider range because 
if business rules change, DB is the last thing that we want to touch) 

## Traditional Approach

Test could look like this:

```java
@Test public void usernameValidation_passesForMaxBoundary() {
    db.save(new Person("12345678901234567890123456789012345678901234567890"));
}
```

Or maybe instead of numbers we'll put different kind of symbols:

```java
@Test public void usernameValidation_passesForMaxBoundary() {
    db.save(new Person(" ABCDEFGHIJАБВГДИЙКЛмƒœ˙´þ¥¨ʼ,?ABCDEFGHIJАБВГДИЙКЛ"));
}
```

There are 50 characters, right? So the test will pass. But maybe (just maybe) we don't know that we save characters in 
NVARCHAR columns of Oracle where constraints are for number of _bytes_ (this differs in different RDBMS's) and not for
the number of symbols.

## Randomized Approach
But if we try this, it almost certainly will fail:

```java
@Test public void usernameValidation_passesForMaxBoundary() {
    db.save(new Person(unicode(50)));
}
```

Why? There are still 50 symbols.. Because in case of UTF-8 encoding different symbols are encoded with different number
of bytes. English is 1 byte, cyrillic is 2, but there are lots of symbols that are for example 3 bytes long. In that 
case while on Back End validation passes (there are still 50 symbols), in Oracle it will fail since `50 x 3 = 150` which
is larger than 128 (our DB constraint).

## Sum up
 
Randomization may find very tricky situations that not everyone may know of. Whether those are bugs or not - that's a
question to the BAs, but we probably want to be aware of them.

Check out [the unicode example in Java](./src/test/java/io/qala/datagen/examples/ValidationTest.java).