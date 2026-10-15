import dev.detekt.gradle.Detekt
import dev.detekt.gradle.extensions.DetektExtension
import org.jlleitschuh.gradle.ktlint.KtlintExtension

// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.compose) apply false
    alias(libs.plugins.kotlin.serialization) apply false
    alias(libs.plugins.ktlint) apply false
    alias(libs.plugins.detekt) apply false
}

// The `libs` catalog accessor only exists in this script's own scope, not inside
// allprojects/subprojects blocks, so read what those blocks need up front.
val ktlintVersion = libs.versions.ktlint

// Static analysis is applied from here rather than per-module so that every module
// (and any future one) is checked with identical settings.
//
// ktlint owns formatting: indentation, imports, line width, trailing commas. It is
// driven entirely by .editorconfig — there are no rule settings in this file.
// detekt owns size/complexity limits, configured in config/detekt/detekt.yml.
//
// Both attach to `check`, and `build` depends on `check`, so `./gradlew build`
// fails on any violation.
allprojects {
    apply(plugin = "org.jlleitschuh.gradle.ktlint")

    extensions.configure<KtlintExtension> {
        // The plugin bundles an older engine by default; pin the version we want.
        version.set(ktlintVersion)
        // Report every violation in one run instead of stopping at the first file.
        outputToConsole.set(true)

        filter {
            // Generated sources are not ours to format.
            exclude { it.file.path.contains("${File.separator}build${File.separator}") }
        }
    }
}

subprojects {
    apply(plugin = "dev.detekt")

    extensions.configure<DetektExtension> {
        source.setFrom(layout.projectDirectory.dir("src"))

        // Our detekt.yml only lists the rules we deliberately changed; everything
        // else falls back to detekt's defaults.
        buildUponDefaultConfig = true
        config.setFrom(rootProject.file("config/detekt/detekt.yml"))
        // Report paths relative to the repo root so they match between local and CI.
        basePath = rootProject.layout.projectDirectory
        parallel = true
    }

    tasks.withType<Detekt>().configureEach {
        // A compilation's sources include generated code, which we neither wrote nor
        // can fix. A glob ("**/build/**") does not work here: detekt hands the task a
        // flat file collection, so there is no relative path for the pattern to match.
        val buildDirSegment = "${File.separator}build${File.separator}"
        exclude { it.file.absolutePath.contains(buildDirSegment) }
    }
}

// Many of detekt's rules — LongParameterList, UnusedImport, UnusedPrivateFunction,
// VarCouldBeVal and friends — need a compiler classpath and are SKIPPED SILENTLY
// without one. Only detekt's per-variant tasks get a classpath; the aggregate
// `detekt` task does not.
//
// One variant is enough: debug and release compile the same src/main, so running
// both (which is all `detektMain` does) reports every finding twice. Debug covers
// production code, its unit tests and its instrumented tests. Add a release task here
// only if src/release ever gets sources of its own.
val typeResolvedDetektTasks = setOf("detektDebug", "detektDebugUnitTest", "detektDebugAndroidTest")

subprojects {
    // `check` depends on the aggregate `detekt` task. Rather than run it alongside
    // the type-resolved ones (reporting every finding twice, half of them weaker),
    // turn it into an alias: a disabled task skips its own work but still runs its
    // dependencies, so `./gradlew detekt` and `check` both get the full analysis.
    tasks.named<Detekt>("detekt") {
        enabled = false
        dependsOn(tasks.withType<Detekt>().matching { it.name in typeResolvedDetektTasks })
    }
}

// `./gradlew build` already runs both tools through `check`, but it compiles and
// tests everything first. This is the fast entry point for CI and for a pre-push
// hook: static analysis only, nothing tested.
//
// Registered in every project, so `./gradlew staticAnalysis` matches the task by
// name in all of them — the root included, which is what covers the root build
// script itself. Add --continue to see every finding in one run instead of
// stopping at the first failure.
allprojects {
    tasks.register("staticAnalysis") {
        group = LifecycleBasePlugin.VERIFICATION_GROUP
        description = "Runs ktlint and detekt over this module."
        dependsOn(tasks.named("ktlintCheck"))
        dependsOn(tasks.withType<Detekt>().matching { it.name in typeResolvedDetektTasks })
    }
}
