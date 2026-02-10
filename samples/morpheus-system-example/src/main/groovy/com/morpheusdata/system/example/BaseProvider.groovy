package com.morpheusdata.system.example

import com.morpheusdata.core.MorpheusContext
import com.morpheusdata.core.Plugin

/**
 * Base class for all providers in this plugin
 */
abstract class BaseProvider {
    Plugin plugin
    MorpheusContext morpheusContext

    BaseProvider(Plugin plugin, MorpheusContext morpheusContext) {
        this.plugin = plugin
        this.morpheusContext = morpheusContext
    }

    Plugin getPlugin() {
        return plugin
    }

    MorpheusContext getMorpheus() {
        return morpheusContext
    }
}
