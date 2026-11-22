pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "DendyOS"

include(":app")
include(":core:designsystem")
include(":core:vehicle")
include(":core:ai")
include(":core:media")
include(":feature:home")
include(":feature:navigation")
include(":feature:music")
include(":feature:apps")
include(":feature:vehicle")
include(":feature:climate")
include(":feature:settings")
include(":feature:assistant")
