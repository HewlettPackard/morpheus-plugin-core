package com.morpheusdata.system.example.datasets

import com.morpheusdata.core.data.DatasetInfo
import com.morpheusdata.core.data.DatasetQuery
import com.morpheusdata.core.providers.AbstractDatasetProvider
import io.reactivex.rxjava3.core.Observable

/**
 * Dataset provider for storage type options
 * Provides a list of available storage types (SAN, NAS, Local)
 */
class StorageTypeDatasetProvider extends AbstractDatasetProvider<Map, String> {

    StorageTypeDatasetProvider(com.morpheusdata.core.Plugin plugin, com.morpheusdata.core.MorpheusContext morpheusContext) {
        this.plugin = plugin
        this.morpheusContext = morpheusContext
        this.datasetInfo = new DatasetInfo(
            'arcus',                           // namespace
            'storageTypes',                    // key
            'Storage Types',                   // name
            'Available storage types for Arcus systems'  // description
        )
    }

    @Override
    DatasetInfo getInfo() {
        return datasetInfo
    }

    @Override
    Class<Map> getItemType() {
        return Map.class
    }

    @Override
    Observable<Map> list(DatasetQuery query) {
        def items = [
            [name: 'SAN', value: 'san'],
            [name: 'NAS', value: 'nas'],
            [name: 'Local', value: 'local']
        ]
        return Observable.fromIterable(items)
    }

    @Override
    Observable<Map> listOptions(DatasetQuery query) {
        return list(query)
    }

    @Override
    Map fetchItem(Object value) {
        def items = [
            [name: 'SAN', value: 'san'],
            [name: 'NAS', value: 'nas'],
            [name: 'Local', value: 'local']
        ]
        return items.find { it.value == value?.toString() }
    }

    @Override
    Map item(String value) {
        return fetchItem(value)
    }

    @Override
    String itemName(Map item) {
        return item?.name
    }

    @Override
    String itemValue(Map item) {
        return item?.value
    }
}
