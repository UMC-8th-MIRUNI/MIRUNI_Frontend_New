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
        maven(url = "https://jitpack.io")
    }
}

rootProject.name = "MIRUNI_FE"
include(":app")

include(":domain:model")
include(":domain:repository")
include(":domain:usecase")

include(":data:service")
include(":data:dto")
include(":data:repository")

include(":feature:aiplanner")
include(":feature:home")
include(":feature:login")
include(":feature:calendar")
include(":feature:mypage")
include(":feature:onboard")
include(":feature:signup")
include(":feature:splash")
include(":feature:survey")
include(":feature:pwreset")

// convention plugin
includeBuild("build-logic")
//include(":build-logic:convention:lib")
include(":core")
