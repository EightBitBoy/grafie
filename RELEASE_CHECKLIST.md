# Releases
This is a checklist for preparing releases.

* Adjust the version in **build.gradle**.
* Adjust the changelog in **README.md**.
* Create a git tag.
* Publish the plugin to Bintray: **gradlew bintrayUpload**
* Publish the plugin to the Gradle plugin repository: **gradlew publishPlugins**