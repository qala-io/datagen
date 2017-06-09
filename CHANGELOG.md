Change Log
----------

The format is based on [Keep a Changelog](http://keepachangelog.com/) and this project SOMEWHAT (but not strictly) 
adheres to [Semantic Versioning](http://semver.org/).

## [release-1.11.0] - 2017-06-09
### Added
* Pilot JUni5 Integration, see in [docs](./junit5/README.md)
```
    @Alphanumeric(length = 1, name = "min boundary")
    @Alphanumeric(min = 2, max = 29, name = "middle value")
    @Alphanumeric(length = 30, name = "max boundary")
    void validationPasses_forValidName(String dogName) {
        assertTrue(new Dog(dogName).isValid());
    }
```


## [release-1.10.1] - 2017-01-06
### Fixed
* `unicodeWithoutBoundarySpaces()` returned symbols that were treated as whitespaces by `String#trim()`. Now these 
symbols are also not returned by the method (as well as `Character#isWhitespace()` and `Character#isSpaceChar()`). 

## [release-1.10.0] - 2017-01-03
### Added
* `unicodeWithoutBoundarySpaces()`

## [release-1.9.3] - 2017-01-02
### Added
* Support for `OffsetDateTime`
* `greaterDouble()`

## [release-1.9.2] - 2016-10-01
### Added
* callXxx() Methods: `callOneOf()`, `callNoneOrMore()`, `callOneOrMore()`
 
### Changed
* Deprecated: `oneOf()`, `noneOrMore()`, `oneOrMore()`

## [release-1.9.0] - 2016-09-24
### Added
* Generating random doubles like `positiveDouble()`
* `blankOr(String)` to return one of: `null`, empty string, string with spaces, passed string.