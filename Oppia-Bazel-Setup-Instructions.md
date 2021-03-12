## Overview
Bazel is an open-source build and test tool similar to Make, Maven, and Gradle. It uses a human-readable, high-level build language.


## Installation
**We don't officially support Windows for now so the Windows instructions may not be complete and may require additional tweaking.**<br>

1. Install Bazel from [here](https://docs.bazel.build/versions/master/install.html). 

1. Ensure that your `ANDROID_HOME` environment variable is set to the location of your Android SDK. To do this, find the path to the installed SDK using Android Studio’s SDK Manager (install SDK 28). Assuming the SDK is installed to default locations, you can use the following commands to set the `ANDROID_HOME` variable:<br>
    - Linux: `export ANDROID_HOME=$HOME/Android/Sdk/`<br>
    - macOS: `export ANDROID_HOME=$HOME/Library/Android/sdk`

1. Follow the instructions in [oppia-bazel-tools](https://github.com/oppia/oppia-bazel-tools).

#### Possible Error:
```
ERROR: While parsing option --override_repository=android_tools=~/oppia-bazel/android_tools: Repository 
override directory must be an absolute path
```
Try to delete the `.bazelrc` file to solve the above. error. 

After the installation completes you can build the app using Bazel. Move you command line head to the `~/opensource/oppia-android` and run the below bazel command:

#### Building the app

```
~/oppia-bazel/bazel build //:oppia
```

#### Building + installing the app

```
~/oppia-bazel/bazel mobile-install //:oppia
```

#### Running specific module (app) Robolectric tests

```
~/oppia-bazel/bazel test //app/...
```

#### Running all Robolectric tests (slow)

```
~/oppia-bazel/bazel test //...
```

## Concepts and Terminology
**[Workspace](https://github.com/oppia/oppia-android/blob/develop/WORKSPACE)**<br>
A workspace is a directory where we add targetted SDK version, all the required dependencies and there required Rules. The directory containing the WORKSPACE file is the root of the main repository, which in our case is the `oppia-android` root directory is the main directory. 

**[Packages](https://github.com/oppia/oppia-android/tree/develop/app)**<br>
A package is defined as a directory containing a file named BUILD or BUILD.bazel.

**[Binary rules](https://github.com/oppia/oppia-android/blob/ba8d914480251e4a8543feb63a93b6c91e0a5a2f/BUILD.bazel#L3)**<br>
A rule specifies the relationship between inputs and outputs, and the steps to build the outputs.
In Android, rules are defined using `android_binary`. Android rules for testing are `android_instrumentation_test` and `android_local_test`.

**[BUILD files](https://github.com/oppia/oppia-android/blob/develop/app/BUILD.bazel)**<br>
Every package contains a BUILD file. This file is written in Starlark Language. In this Build file for module-level, we generally define `android_library`, `kt_android_library` to build our package files as per the requirement. 

**[Dependencies](https://github.com/oppia/oppia-android/blob/ba8d914480251e4a8543feb63a93b6c91e0a5a2f/BUILD.bazel#L16)**<br>
A target A depends upon a target B if B is needed by A at build. `A -> B`<br>
```
deps = [ "//app",]
```
Here, `deps` is used to define the dependencies which is a type of dependencies called `deps dependencies` and it includes the files/directory/target which are dependent. From the above example the dependency is the `app` target which is defined in the [Build file of app package](https://github.com/oppia/oppia-android/blob/ba8d914480251e4a8543feb63a93b6c91e0a5a2f/app/BUILD.bazel#L616). 

Example of Dependencies
1. [srcs dependencies](https://github.com/oppia/oppia-android/blob/ba8d914480251e4a8543feb63a93b6c91e0a5a2f/app/BUILD.bazel#L617)
2. [deps dependencies](https://github.com/oppia/oppia-android/blob/ba8d914480251e4a8543feb63a93b6c91e0a5a2f/app/BUILD.bazel#L622)

**[Loading an extension](https://github.com/oppia/oppia-android/blob/ba8d914480251e4a8543feb63a93b6c91e0a5a2f/app/BUILD.bazel#L13)**<br>
Bazel extensions are files ending in .bzl. Use the load statement to import a symbol from an extension.<br>
```
load("@io_bazel_rules_kotlin//kotlin:kotlin.bzl", "kt_android_library")
```
Here, we are loading `kotlin.bzl` and we are going to use it with a symbol name `kt_android_library`.
Arguments to the load function must be string literals. load statements must appear at top-level in the file.

**[Visibility of a file target](https://github.com/oppia/oppia-android/blob/ba8d914480251e4a8543feb63a93b6c91e0a5a2f/app/BUILD.bazel#L621)**<br>
With the example from our codebase, target `app` whose visibility is public. <br>
 - `visibility = ["//visibility:public"],` - Anyone can use this target.<br>
 - `"//visibility:private"` - Only targets in this package can use this target.

**[Testing](https://github.com/oppia/oppia-android/blob/ba8d914480251e4a8543feb63a93b6c91e0a5a2f/app/BUILD.bazel#L719)**<br>
when we want to run test cases on Bazel build environment, we usually pass arguments related to test which `app_test.bazl` required to run our test.
```
app_test(
    name = "HomeActivityLocalTest",
    srcs = ["src/test/java/org/oppia/android/app/home/HomeActivityLocalTest.kt"],
    test_class = "org.oppia.android.app.home.HomeActivityLocalTest",
    deps = TEST_DEPS,
)
```

