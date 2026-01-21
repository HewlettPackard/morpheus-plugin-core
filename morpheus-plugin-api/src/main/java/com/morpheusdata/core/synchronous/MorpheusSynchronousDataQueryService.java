/*
 *  Copyright 2024 Morpheus Data, LLC.
 *
 * Licensed under the PLUGIN CORE SOURCE LICENSE (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://raw.githubusercontent.com/gomorpheus/morpheus-plugin-core/v1.0.x/LICENSE
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.morpheusdata.core.synchronous;

import com.morpheusdata.core.*;
import com.morpheusdata.core.data.DataQuery;
import com.morpheusdata.core.data.DataQueryResult;
import com.morpheusdata.core.synchronous.admin.MorpheusSynchronousUserService;
import com.morpheusdata.model.MorpheusModel;
import io.reactivex.rxjava3.core.Observable;

import java.util.List;
import java.util.Map;

/**
 * This interface is a standard base synchronous service so all services provide consistent finder methods. It features
 * the ability to create dynamic queries using the {@link DataQuery} object. There are methods that can be implemented
 * to provide both {@link MorpheusModel} related objects as well as Map objects for use in
 * {@link com.morpheusdata.core.providers.DatasetProvider} use cases (dropdown and type-ahead components in UI option types).
 *
 * Often times this interface is used in conjunction with the {@link MorpheusSynchronousIdentityService} for providing
 * an efficient way to sync objects via the {@link MorpheusSynchronousIdentityService#listIdentityProjections(DataQuery)} method.
 *
 * Another implementation also exists for asynchronous rxjava querying of the objects called the
 * {@link MorpheusDataQueryService}. Typically, both should be implemented for use within the plugin api.
 *
 * It is recommended that the Asynchronous version of the service class is used where possible as it is the most efficient.
 * An example of where this may be more useful would be in UI rendering methods which are already blocking as is.
 *
 * <p><strong>Note:</strong> This object requires its asynchronous counterpart be implemented as it acts as a simple
 * delegate to that service.</p>
 *
 * @author Alex Clement
 * @since 1.3.0
 * @param <M> The {@link MorpheusModel} class type for this service to query against
 * @see MorpheusSynchronousIdentityService
 * @see MorpheusDataService
 * @see DataQuery
 */
public interface MorpheusSynchronousDataQueryService<M extends MorpheusModel> {

	/**
	 * Reference to the asynchronous data service {@link MorpheusDataService} implementation as this interface acts
	 * as a simple delegate blocking wrapper for it.
	 * @return the asynchronous data service to be used by the default method implementations in this interface.
	 */
	MorpheusDataQueryService<M> getDataQueryService();


	//generic list and get
	/**
	 * Performs a query operation based on the filters set in the {@link DataQuery} object passed and returns a simple
	 * total count of the results. This could be useful for paging.
	 *
	 * <p><strong>Note:</strong> For more information on how to query please refer to the documentation for the {@link DataQuery} class.</p>
	 *
	 * @param query An instance of the {@link DataQuery} object used for filtering results. This should often include an account / user
	 *              scope for security but does not always need to if being used for sync or multi-tenant reporting.
	 * @return A Long value with the total count.
	 * @see DataQuery
	 */
	default Long count(DataQuery query) {
		return getDataQueryService().count(query).blockingGet();
	}

	/**
	 * Fetches a single {@link MorpheusModel} by its Identifier (id) field. For more advanced
	 * single object fetches please refer to {@link MorpheusDataService#find(DataQuery)}.
	 *
	 * <p><strong>Note:</strong> This method does not factor in any sort of access/security control and should not be used in areas where this is required.</p>
	 *
	 * @param id the database identifier to fetch an object by.
	 * @return a representation of a {@link MorpheusModel} depending on if the object was found or not.
	 */
	default M get(Long id) {
		return getDataQueryService().get(id).blockingGet();
	}

	/**
	 * Fetches a stream of {@link MorpheusModel} objects based on a collection of Identifiers (id). This is often
	 * used in conjunction with the {@link MorpheusSynchronousIdentityService#listIdentityProjections(DataQuery)} and the {@link com.morpheusdata.core.util.SyncTask}
	 * for efficiently only fetching batches of objects we want to perform update operations on.
	 *
	 * <p><strong>Note:</strong> This method does not factor in any sort of access/security control and should not be used in areas where this is required.</p>
	 *
	 * @param ids a collection of Identifiers (ids) to fetch the objects by.
	 * @return A List of {@link MorpheusModel} objects based on the ids passed in
	 */
	default List<M> listById(List<Long> ids) {
		return getDataQueryService().listById(ids).toList().blockingGet();
	}


	/**
	 * Performs a list all operation on the database returning the results as {@link MorpheusModel} objects.
	 **
	 * @return an Observable stream of {@link com.morpheusdata.model.MorpheusModel} objects based on the passed in query.
	 */
	default List<M> list() {return getDataQueryService().list().toList().blockingGet(); }

	/**
	 * Performs a query operation on the database returning the results as {@link MorpheusModel} objects. These
	 * queries can be scoped to an {@link com.morpheusdata.model.projection.AccountIdentity} or {@link com.morpheusdata.model.projection.UserIdentity}
	 * as well as various filters on arbitrary fields in the database.
	 *
	 * <p><strong>Note:</strong> For more information on how to query please refer to the documentation for the {@link DataQuery} class.</p>
	 *
	 * @param query An instance of the {@link DataQuery} object used for filtering results. This should often include an account / user
	 * 	            scope for security but does not always need to if being used for sync or multi-tenant reporting.
	 * @return A List of {@link MorpheusModel} objects based on the passed in query.
	 */
	default List<M> list(DataQuery query) {
		return getDataQueryService().list(query).toList().blockingGet();
	}

	/**
	 * Performs a list all operation on the database returning the results as Map objects typically containing keys of (name,value) for use
	 * in dropdown or type-ahead components within the UI. This would typically be paired with a {@link com.morpheusdata.core.providers.DatasetProvider}.
	 *
	 * @return an Observable stream of Map objects based on the passed in query containing name,value pairs.
	 */
	default List<Map> listOptions(){ return getDataQueryService().listOptions().toList().blockingGet(); }

	/**
	 * Performs a query operation on the database returning the results as Map objects typically containing keys of (name,value) for use
	 * in dropdown or type-ahead components within the UI. This would typically be paired with a {@link com.morpheusdata.core.providers.DatasetProvider}.
	 * Queries can be scoped to an {@link com.morpheusdata.model.projection.AccountIdentity} or {@link com.morpheusdata.model.projection.UserIdentity}
	 * as well as various filters on arbitrary fields in the database.
	 *
	 * <p><strong>Note:</strong> For more information on how to query please refer to the documentation for the {@link DataQuery} class.</p>
	 *
	 * @param query An instance of the {@link DataQuery} object used for filtering results. This should often include an account / user
	 * 	            scope for security but does not always need to if being used for sync or multi-tenant reporting.
	 * @return A List of Map objects based on the passed in query containing name,value pairs.
	 */
	default List<Map> listOptions(DataQuery query) {
		return getDataQueryService().listOptions(query).toList().blockingGet();
	}

	/**
	 * Performs a query operation on the database returning the first result as a {@link MorpheusModel} object. These
	 * queries can be scoped to an {@link com.morpheusdata.model.projection.AccountIdentity} or {@link com.morpheusdata.model.projection.UserIdentity}
	 * as well as various filters on arbitrary fields in the database.
	 *
	 * <p><strong>Note:</strong> For more information on how to query please refer to the documentation for the {@link DataQuery} class.</p>
	 *
	 * @param query An instance of the {@link DataQuery} object used for filtering results. This should often include an account / user
	 * 	            scope for security but does not always need to if being used for sync or multi-tenant reporting.
	 * @return A {@link MorpheusModel} object based on the passed in query.
	 */
	default M find(DataQuery query) {
		return getDataQueryService().find(query).blockingGet();
	}

	/**
	 * Performs a query operation on the database just like {@link MorpheusDataService#list(DataQuery)} with a query, but the result is no longer a
	 * stream of individual {@link MorpheusModel}.
	 *
	 * <p><strong>Note:</strong> This is a reactive method and will not perform any operation until subscribed or blockingGet() is called on it.</p>
	 * <p><strong>Note:</strong> For more information on how to query please refer to the documentation for the {@link DataQuery} class.</p>
	 *
	 * @param query An instance of the {@link DataQuery} object used for filtering results. This should often include an account / user
	 * 	            scope for security but does not always need to if being used for sync or multi-tenant reporting.
	 * @return a Single DataQueryResult representing a collection of result objects along with the metadata about the result. This could be paging data for example.
	 */
	default DataQueryResult search(DataQuery query) {
		return getDataQueryService().search(query).blockingGet();
	}
}
