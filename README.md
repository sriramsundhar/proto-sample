# Proto Sample

This repository contains Proto files which are being used as schema for Orders Hub project.

The syntax for Proto files is `proto3`
- [reference](https://developers.google.com/protocol-buffers/docs/proto3).

The build tool to build the project is Gradle.

### Setup

<details>
<summary>Prerequisite</summary>
<p>

### Prerequisite

- [sdkman](https://sdkman.io/install)
- [java 11](https://sdkman.io/jdks)
- [gradle](https://sdkman.io/sdks#gradle)

</p>
</details>

<details>
<summary>Style guide</summary>

<p>

### Style guid

We are using `intellij-java-google-style` which can be found:

- [xml import](https://github.com/google/styleguide/blob/gh-pages/intellij-java-google-style.xml)
- [Intellij plugin](https://plugins.jetbrains.com/plugin/8527-google-java-format)

</p>
</details>

## Dev env

### Build and generate Proto files

```
./gradlew clean build
```

### Publish

- As a part of CI pipeline, snapshots and release candidates will get published into Nexus
  respective repositories.

### CI reference

CI pipeline is build based on

- [gitlab](https://docs.gitlab.com/ee/ci/ssh_keys/)
- [blog](https://marcosschroh.github.io/posts/autobumping-with-gitlab/).
