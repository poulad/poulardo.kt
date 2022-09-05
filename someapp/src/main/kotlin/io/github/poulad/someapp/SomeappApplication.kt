package io.github.poulad.someapp

import org.springframework.boot.Banner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class SomeAppApplication

fun main(args: Array<String>) {
    runApplication<SomeAppApplication>(*args) {
        setBannerMode(Banner.Mode.OFF)
    }
}

/*
2022-09-04T22:44:33.183493+00:00 heroku[web.1]: Starting process with command `java $JAVA_OPTS -jar ./someapp/build/libs/someapp-0.0.1-SNAPSHOT.jar`
2022-09-04T22:44:34.156988+00:00 heroku[web.1]: Process exited with status 1
2022-09-04T22:44:33.859444+00:00 app[web.1]: Setting JAVA_TOOL_OPTIONS defaults based on dyno size. Custom settings will override them.
2022-09-04T22:44:33.862669+00:00 app[web.1]: Picked up JAVA_TOOL_OPTIONS: -XX:+UseContainerSupport -Xmx300m -Xss512k -XX:CICompilerCount=2 -Dfile.encoding=UTF-8
2022-09-04T22:44:34.025615+00:00 app[web.1]: Exception in thread "main" java.lang.ClassNotFoundException: io.github.poulad.someapp.SomeAppApplicationKt
2022-09-04T22:44:34.025935+00:00 app[web.1]: 	at java.base/java.net.URLClassLoader.findClass(URLClassLoader.java:445)
2022-09-04T22:44:34.025961+00:00 app[web.1]: 	at java.base/java.lang.ClassLoader.loadClass(ClassLoader.java:587)
2022-09-04T22:44:34.025985+00:00 app[web.1]: 	at org.springframework.boot.loader.LaunchedURLClassLoader.loadClass(LaunchedURLClassLoader.java:151)
2022-09-04T22:44:34.026007+00:00 app[web.1]: 	at java.base/java.lang.ClassLoader.loadClass(ClassLoader.java:520)
2022-09-04T22:44:34.026028+00:00 app[web.1]: 	at java.base/java.lang.Class.forName0(Native Method)
2022-09-04T22:44:34.026049+00:00 app[web.1]: 	at java.base/java.lang.Class.forName(Class.java:467)
2022-09-04T22:44:34.026071+00:00 app[web.1]: 	at org.springframework.boot.loader.MainMethodRunner.run(MainMethodRunner.java:46)
2022-09-04T22:44:34.026104+00:00 app[web.1]: 	at org.springframework.boot.loader.Launcher.launch(Launcher.java:108)
2022-09-04T22:44:34.026162+00:00 app[web.1]: 	at org.springframework.boot.loader.Launcher.launch(Launcher.java:58)
2022-09-04T22:44:34.026183+00:00 app[web.1]: 	at org.springframework.boot.loader.JarLauncher.main(JarLauncher.java:65)
2022-09-04T22:44:34.263756+00:00 heroku[web.1]: State changed from starting to crashed

 */
