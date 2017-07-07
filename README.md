# grafie - Gradle File Encryption Plugin
[![Build Status](https://travis-ci.org/EightBitBoy/grafie.svg?branch=master)](https://travis-ci.org/EightBitBoy/grafie)

# ATTENTION

**The plugin has not been released yet! Everything below is still work in progress!**

## About

## Usage

Include **grafie** in your build file:
```
```

For every file you want to encrypt create an empty file with the same name and the added extension "**.grafie**":
```
$>ls
build.gradle
secret.txt
$> cat secret.txt
This is a secret text!
$>touch secret.txt.grafie
```

Execute the task **encryptFiles**:
```
$>gradle encryptFiles
$>cat secret.txt.grafie
oisaujfipfusifuspifaufofuoefk
```

## Resources that helped developing the plugin
* [Gradle documentation: Writing Custom Plugins](https://docs.gradle.org/current/userguide/custom_plugins.html)
* [Gradle documentation: The Gradle TestKit](https://docs.gradle.org/current/userguide/test_kit.html)
* [Gradle sample: Custom plugin](https://github.com/gradle/gradle/tree/master/subprojects/docs/src/samples/customPlugin)
* [jonathanhood's gradle-plugin-example](https://github.com/jonathanhood/gradle-plugin-example)

## Changelog

### 0.1.0 - not yet released
* Initial release
