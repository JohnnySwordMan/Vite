package com.mars.infra.plugin.internal.util

import com.google.gson.Gson
import com.mars.infra.plugin.Vite
import com.mars.infra.plugin.internal.model.MODULE_LAST_MODIFY
import com.mars.infra.plugin.internal.model.TAG_STEP_ONE
import com.mars.infra.plugin.internal.model.ModuleData
import org.gradle.api.Project
import java.io.File

/**
 * Created by Mars on 2022/5/16
 */
object FileUtils {

    fun getLocalMavenCache(project: Project?): File {
        project ?: throw NullPointerException("project must not be null!")
//        val path = project.rootProject.projectDir.absolutePath
        val path = project.rootProject.buildDir.absolutePath
        val localMavenCache = "$path/local-maven/cache"
        val cache = File(localMavenCache).apply {
            if (!this.exists()) {
                this.mkdirs()
            }
        }
        return cache
    }

    fun readModuleModifyInfo(): ModuleData? {
        val cache = getLocalMavenCache(Vite.mAppProject)
        val moduleLastModifyJson = File(cache, MODULE_LAST_MODIFY)
        if (!moduleLastModifyJson.exists()) {
            return null
        }
        val moduleData =
            Gson().fromJson(moduleLastModifyJson.inputStream().reader(), ModuleData::class.java)
        Logger.i(TAG_STEP_ONE, "readModuleModifyInfo---moduleData = $moduleData")
        return moduleData
    }
}