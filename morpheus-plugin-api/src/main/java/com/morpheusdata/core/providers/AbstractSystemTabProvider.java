package com.morpheusdata.core.providers;

import com.morpheusdata.model.ContentSecurityPolicy;
import com.morpheusdata.views.HandlebarsRenderer;
import com.morpheusdata.views.Renderer;

/**
 * Abstract base for SystemTabProvider with a default Handlebars Renderer.
 *
 * @see SystemTabProvider
 * @since 1.4.0
 * @author Prathyusha Vemula
 */
public abstract class AbstractSystemTabProvider implements SystemTabProvider {
	private HandlebarsRenderer renderer;

	@Override
    public ContentSecurityPolicy getContentSecurityPolicy() {
        // Required if loading external resources
        ContentSecurityPolicy csp = new ContentSecurityPolicy();
        csp.scriptSrc = "*.jsdelivr.net 'unsafe-eval'";
        csp.styleSrc = "https: 'unsafe-inline'";
        return csp;
    }

	@Override
	public Renderer<?> getRenderer() {
		if(renderer == null) {
			renderer = new HandlebarsRenderer("renderer", getPlugin().getClassLoader());
			renderer.registerAssetHelper(getPlugin().getName());
			renderer.registerNonceHelper(getMorpheus().getWebRequest());
			renderer.registerI18nHelper(getPlugin(), getMorpheus());
		}
		return renderer;
	}
}


