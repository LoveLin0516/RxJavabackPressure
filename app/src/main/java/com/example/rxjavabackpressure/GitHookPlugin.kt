//package com.example.rxjavabackpressure
//
//import org.gradle.api.Plugin
//import org.gradle.api.Project
//import org.gradle.api.tasks.Copy
//import org.gradle.kotlin.dsl.task
//import java.io.File
//
///**
// * 将 scripts/git-hook 里面的 git-hook 文件拷贝到 .git/hooks 目录下
// *
// * commit-msg
// */
//class GitHookPlugin : Plugin<Project> {
//    override fun apply(project: Project) {
//        val copyGitHookScriptTask = project.task(name = "copyGitHookScript", type = Copy::class) {
//            inputs.dir("${project.rootDir}/scripts${File.separator}git-hooks")
//            outputs.dir("${project.rootDir}/.git${File.separator}hooks")
//
//            from("${project.rootDir}/scripts${File.separator}git-hooks${File.separator}commit-msg")
//            into("${project.rootDir}/.git${File.separator}hooks")
//
//            from("${project.rootDir}/scripts${File.separator}git-hooks${File.separator}prepare-commit-msg")
//            into("${project.rootDir}/.git${File.separator}hooks")
//        }
//        project.gradle.afterProject {
//            if (name == "bigovlog") {
//                tasks.named("preBuild") {
//                    dependsOn(copyGitHookScriptTask)
//                }
//            }
//        }
//    }
//}