Change Log
----------

The format is based on [Keep a Changelog](http://keepachangelog.com/) and this project SOMEWHAT (but not strictly) 
adheres to [Semantic Versioning](http://semver.org/).

## [release-1.9.4] - 2017-01-03
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