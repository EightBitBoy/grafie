# TODO

## Development
* Use the new syntax for applying plugins.
* Make sure the password is set and not empty.
* Look in properties for a default password location?
* Support custom file encodings.
* Find out how to mark a task as FAILED when something goes wrong!
* Improve file suffix validity check.
* Use Apache commons text RandomStringGenerator to generate passwords for EncryptionKeyTest.

## Links

https://github.com/gradle/gradle/tree/master/subprojects/docs/src/samples/testKit/gradleRunner
https://github.com/gradle/gradle/blob/master/design-docs/testing-toolkit.md#milestone-3
https://blog.gradle.org/introducing-testkit
https://github.com/ysb33r/gradleTest
https://github.com/eriwen/gradle-js-plugin/tree/master/src/test/groovy/com/eriwen/gradle/js/util
https://discuss.gradle.org/t/separate-execution-for-java-unit-and-integration-tests/8713
https://discuss.gradle.org/t/how-do-i-unit-test-custom-tasks/3815
https://discuss.gradle.org/t/how-to-execute-a-task-in-unit-test-for-custom-plugin/6771/3

//TODO https://docs.gradle.org/current/userguide/custom_plugins.html#sec:mapping_extension_properties_to_task_properties

https://guides.gradle.org/writing-gradle-plugins/
https://guides.gradle.org/implementing-gradle-plugins/
https://github.com/gradle/gradle/tree/master/subprojects
https://speakerdeck.com/bmuschko/gradle-plugin-best-practices-by-example

//validateExtension(project) //FIXME Make sure a password is provided!
//TODO use project.file in tasks to access files
//TODO use project.files !!!11!!!1!
// see https://docs.gradle.org/3.3/userguide/working_with_files.html
//TODO read about input / output annotation


//TODO use this.logger in FileCryptoTask

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
    
    
    