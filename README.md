# Poulardo.kt

Learning Kotlin

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
