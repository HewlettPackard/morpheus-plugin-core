package com.morpheusdata.system.example

import com.morpheusdata.core.Plugin
import com.morpheusdata.core.MorpheusContext
import com.morpheusdata.core.providers.InputTypeLibraryProvider
import com.morpheusdata.views.Renderer

/**
 * Provides custom input type JavaScript library for Arcus system configuration.
 * Registers the custom slider component and any other custom input types
 * needed for the Arcus system workflow.
 */
class ArcusInputTypeLibraryProvider implements InputTypeLibraryProvider {

    Plugin plugin
    MorpheusContext morpheusContext

    ArcusInputTypeLibraryProvider(Plugin plugin, MorpheusContext morpheusContext) {
        this.plugin = plugin
        this.morpheusContext = morpheusContext
    }

    @Override
    Plugin getPlugin() {
        return plugin
    }

    @Override
    MorpheusContext getMorpheus() {
        return morpheusContext
    }

    @Override
    Renderer<?> getRenderer() {
        return null
    }

    @Override
    String getCode() {
        return 'arcus-input-types'
    }

    @Override
    String getName() {
        return 'Arcus Custom Input Types'
    }

    @Override
    String getLibraryScriptPath(Map<String, Object> opts) {
        return '/custom-slider.js'
    }
    
    /**
     * Optional load priority for loading order control.
     * Lower numbers load first.
     */
    Integer getLoadPriority() {
        return 0
    }
}
