# grafie - Gradle File Encryption Plugin
[![Build Status](https://travis-ci.org/EightBitBoy/grafie.svg?branch=master)](https://travis-ci.org/EightBitBoy/grafie)

# ATTENTION
**The plugin has not been released yet! Everything below is still work in progress!**

## About
Grafie encrypts files which contain sensitive information in your repository.

Sometimes it is difficult to exclude (semi-)secret but important information from a repository. This might be special configuration files or API keys which should not be visible to the general public, it does no damage if the information leaks to the outside but you still do not want everybody to see it. Removing such information from a repository makes working on a project and building it difficult for everyone, secrets must be configured on every developer's machine and every continuous integration system. Changes to those many secrets means reconfiguring all systems in many places too.

The Gradle plugin "Grafie" symmetrically encrypts files containing secrets using a single password, it is the only thing every developer or CI server must know. Encrypted files are added to the repository and versioned while the original cleartext versions of those files are added to a repository's ignore list. Whenever someone clones the repository and builds the project cleartext files are decrypted from the encrypted files.

The encryption happens with the AES algorithm. A 128 bit key is used which is derived from the SHA-256 hash of a user-provided password with an arbitrary length. 

## Include Grafie in your project
Add Grafie to your ```build.gradle``` file.

### All Gradle versions:
```
buildscript {
    repositories {
        maven {
            url 'https://plugins.gradle.org/m2/'
        }
    }
    dependencies {
        classpath 'gradle.plugin.de.eightbitboy:grafie:0.1.0'
    }
}
apply plugin: "de.eightbitboy.grafie"
```

### Gradle 2.1 and higher:
```
plugins {
  id 'de.eightbitboy.grafie' version '0.1.0'
}
```

## Usage

### Configure a password
Provide a password in your ```build.gradle``` file.
```
grafie {
    password = 'yourPassword' 
}
```
 
Obviously you do not want to save the password directly in ```build.gradle```, instead use ```gradle.properties``` to provide it locally. Get the property in ```build.gradle```:
```
grafie {
    password = findProperty('MY_PASSWORD')
}
```
Define the property in ```gradle.properties```:
```
MY_PASSWORD=thisIsSecret
```

When working with continuous integration systems the password may be provided via environment variables:
```
grafie {
    password = System.getenv('MY_PASSWORD')
}
```
  
### Encrypt files
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
### Decrypt files

### Full plugin configuration example
```
grafie {
    password = 'aSecretPassword'
}
```

## Contributions
Any contribution is welcome. Do not hesitate to contact the developer via E-Mail or to create an issue to discuss bugs or features.

## Resources that helped developing the plugin
* [Gradle documentation: Writing Custom Plugins](https://docs.gradle.org/current/userguide/custom_plugins.html)
* [Gradle documentation: The Gradle TestKit](https://docs.gradle.org/current/userguide/test_kit.html)
* [Gradle sample: Custom plugin](https://github.com/gradle/gradle/tree/master/subprojects/docs/src/samples/customPlugin)
* [jonathanhood's gradle-plugin-example](https://github.com/jonathanhood/gradle-plugin-example)

## Changelog

### 0.1.0 - not yet released
* Initial release
