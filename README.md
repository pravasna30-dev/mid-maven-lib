# mid-maven-lib

A mid-level Java library that demonstrates Maven-to-Bazel source integration with [ecmonorepo](https://github.com/pravasna30-dev/ecmonorepo).

## Prerequisites

- Java 21
- Maven 3.9+

## Build

```bash
mvn clean compile
```

## Run Tests

Unit tests:

```bash
mvn test
```

Integration tests:

```bash
mvn verify
```

## API

| Method | Description |
|--------|-------------|
| `MidLibService.say()` | Prints "I am a MID level library" to stdout |
| `getLevel()` | Returns the library level (`"MID"`) |
| `getVersion()` | Returns the library version (`"1.0.0"`) |
| `identity()` | Returns a formatted identity string |
| `process(List<String>)` | Prefixes each item with `[MID]` |

## ECMonorepo Integration

This library is integrated into ecmonorepo as a `build_system: source` producer. Java sources are copied into a native Bazel `java_library` package at build time. See [PRODUCERS.yaml](https://github.com/pravasna30-dev/ecmonorepo/blob/main/PRODUCERS.yaml) for configuration.
