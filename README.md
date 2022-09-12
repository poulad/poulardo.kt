# Poulardo.kt

Learning Kotlin

## Dependencies

- [Ktor]
- [Kotlin Logging] for a more performant logging
- [Kodein DI] for dependency injection

## Notes

See dependency tree of a sub-project:

```shell
./gradlew :shared-lib:dependencies --configuration runtimeClasspath
```

Start and stop the background worker process:

```shell
heroku scale --app poulardo worker=1
heroku scale --app poulardo worker=0
```

Read dyno logs:

```shell
heroku logs --app poulardo --dyno worker --tail
```

Build and prepare WASM files for serve:

```shell
./gradlew nativeBinaries
cp -vf ./webapp-wasm/build/bin/native/releaseExecutable/* ./assets/
```

[Ktor]: (https://ktor.io)
[Kotlin Logging]: (https://github.com/MicroUtils/kotlin-logging)
[Kodein DI]: (https://github.com/kosi-libs/Kodein)
