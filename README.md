# grafie - Gradle File Encryption Plugin
[![Build Status](https://travis-ci.org/EightBitBoy/grafie.svg?branch=master)](https://travis-ci.org/EightBitBoy/grafie)

# ATTENTION
**The plugin has not been released yet! Everything below is still work in progress!**

## About
Grafie encrypts files containing sensitive information in your repository.

Sometimes it is difficult to exclude (semi-)secret but important information from a repository. This might be special configuration files or API keys which should not be visible to the general public. Removing such information from a repository makes working on a project and building it difficult for everyone, secrets must be configured on every developer's machine and every continuous integration system. Changes to those secrets means reconfiguring all systems too.

The Gradle plugin "Grafie" symmetrically encrypts files containing secrets. Encrypted files are added to the repository and versioned while the original cleartext versions of those files are added to a repository's ignore list. Whenever someone clones the repository and builds the project cleartext files are decrypted from the encrypted files. 

## Include Grafie in your project
Add Grafie to your build file:

### All Gradle versions:
```
buildscript {
    repositories {
        maven {
            url "https://plugins.gradle.org/m2/"
        }
    }
    dependencies {
        classpath "gradle.plugin.de.eightbitboy:grafie:0.1.0"
    }
}
apply plugin: "de.eightbitboy.grafie"
```

### Gradle 2.1 and higher:
```
plugins {
  id "de.eightbitboy.grafie" version "0.1.0"
}
```

## Usage
For every file you want to encrypt create an empty file with the same name and the added extension "**.grafie**":
```
$> ls
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
