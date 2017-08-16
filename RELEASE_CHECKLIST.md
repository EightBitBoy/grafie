# Release Checklist
This is a checklist for preparing releases.

* Adjust the version in **build.gradle**.
* Adjust the versions in **README.md** in the section about including the plugin.
* Adjust the changelog in **README.md**.
* Create a git tag.
* Publish the plugin to Bintray: **gradlew bintrayUpload**
* Publish the plugin to the Gradle plugin repository: **gradlew publishPlugins**
