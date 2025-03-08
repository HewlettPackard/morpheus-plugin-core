package com.morpheusdata.core.network;

import com.morpheusdata.core.MorpheusDataService;
import com.morpheusdata.model.NetworkPoolServer;

/**
 * This Context deals with interactions related to {@link com.morpheusdata.model.NetworkPoolServer} objects. It can normally
 * be accessed via the primary {@link com.morpheusdata.core.MorpheusContext} via the {@link MorpheusNetworkService}
 * <p><strong>Examples:</strong></p>
 * <pre>{@code
 * morpheusContext.getNetwork().getPoolServer()
 * }</pre>
 * @see MorpheusNetworkService
 * @since 1.2.4
 * @author Mike Carlin
 */
public interface MorpheusNetworkPoolServerService extends MorpheusDataService<NetworkPoolServer, NetworkPoolServer> {

}
