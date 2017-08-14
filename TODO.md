# TODO

## Development
* Publish, see https://guides.gradle.org/publishing-plugins-to-gradle-plugin-portal/
* Read about up-to-date checks, see https://docs.gradle.org/4.0/userguide/more_about_tasks.html
* Check a repository's .gitignore!
* Use the new syntax for applying plugins.
* Make sure the password is set and not empty.
* Look in properties for a default password location?
* Support custom file encodings.
* Find out how to mark a task as FAILED when something goes wrong!
* Improve file suffix validity check.
* Use Apache commons text RandomStringGenerator to generate passwords for EncryptionKeyTest.
* Use "project.file", see https://docs.gradle.org/current/userguide/custom_plugins.html
* Map extension properties to task properties, see https://docs.gradle.org/current/userguide/custom_plugins.html
* Find information about other encryption types.
* Separate unit tests and integration tests.
* What is the correct way to throw errors from within a plugin?
* Support different file encodings?
* Test multiple Gradle versions, see https://docs.gradle.org/current/userguide/test_kit.html#sub:gradle-runner-gradle-version, and https://github.com/gradle/gradle/blob/master/subprojects/docs/src/samples/testKit/gradleRunner/gradleVersion/src/test/groovy/org/gradle/sample/BuildLogicFunctionalTest.groovy, and https://docs.gradle.org/current/javadoc/org/gradle/testkit/runner/GradleRunner.html#withGradleVersion(java.lang.String)
* Use project.files, see https://docs.gradle.org/3.3/userguide/working_with_files.html
* Read about logging in plugins/tasks, use this.logger in task.
* Read bout input/output annotations for tasks.

## Links
https://docs.gradle.org/current/userguide/custom_plugins.html#sec:mapping_extension_properties_to_task_properties

https://guides.gradle.org/writing-gradle-plugins/
https://guides.gradle.org/implementing-gradle-plugins/
https://github.com/gradle/gradle/tree/master/subprojects
https://speakerdeck.com/bmuschko/gradle-plugin-best-practices-by-example

https://stackoverflow.com/questions/15554296/simple-java-aes-encrypt-decrypt-example
PBESpec
https://stackoverflow.com/questions/3451670/java-aes-and-using-my-own-key
https://stackoverflow.com/questions/18362137/encryption-with-aes-256-java
https://stackoverflow.com/questions/3954611/encrypt-and-decrypt-with-aes-and-base64-encoding


/* TODO
Currently the test checks for an exception.
It would be nicer if the task being executed does not just throw an exception and
 rather marks the task as FAILED. Read about error handling for gradle plugins!

 https://stackoverflow.com/questions/10312259/recommended-way-to-stop-a-gradle-build
 */

def "executing a task without defining a password fails"() {


/*
https://github.com/bintray/gradle-bintray-plugin#readme
https://github.com/bintray/bintray-examples/tree/master/gradle-bintray-plugin-examples
*/
