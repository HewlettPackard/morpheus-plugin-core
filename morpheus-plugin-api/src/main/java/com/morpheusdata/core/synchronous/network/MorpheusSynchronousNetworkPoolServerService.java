package com.morpheusdata.core.synchronous.network;

import com.morpheusdata.core.MorpheusSynchronousDataService;
import com.morpheusdata.core.network.MorpheusNetworkService;
import com.morpheusdata.model.NetworkPoolServer;

/**
 * This Context deals with synchronous interactions related to {@link com.morpheusdata.model.NetworkPoolServer} objects. It can normally
 * be accessed via the primary {@link com.morpheusdata.core.MorpheusContext} via the {@link MorpheusSynchronousNetworkService}
 * <p><strong>Examples:</strong></p>
 * <pre>{@code
 * morpheusContext.getNetwork().getPoolServer()
 * }</pre>
 * @see MorpheusNetworkService
 * @since 1.2.4
 * @author Mike Carlin
 */
public interface MorpheusSynchronousNetworkPoolServerService extends MorpheusSynchronousDataService<NetworkPoolServer, NetworkPoolServer> {

}
