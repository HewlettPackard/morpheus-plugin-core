/*
 *  Copyright 2026 Morpheus Data, LLC.
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

package com.morpheusdata.request;

/**
 * Request object for {@link com.morpheusdata.core.providers.DatastoreTypeProvider.VolumeConfigValidationFacet#validateVolumeConfig}.
 * Encapsulates the volume configuration submitted by the user during provisioning or reconfiguration.
 * Properties can be null. Plugins are responsible for validating their own requirements.
 */
public class ValidateVolumeConfigRequest {

       /**
        * The existing volume ID, or {@code null} for new volumes.
        */
       public Long id;

       /**
        * The volume display name.
        */
       public String name;

       /**
        * The requested volume size in bytes.
        */
       public Long size;

       /**
        * Whether this is the root/boot volume.
        */
       public Boolean rootVolume;

       /**
        * The {@code StorageVolumeType} ID.
        */
       public Long storageType;

       /**
        * The maximum IOPS requested for this volume.
        */
       public Long maxIOPS;

       /**
        * The {@code Datastore} ID that identifies the backing storage server.
        */
       public Long datastoreId;

       /**
        * Whether the volume supports resizing.
        */
       public Boolean resizeable;

       /**
        * Whether the service plan allows resizing this volume.
        */
       public Boolean planResizable;

       /**
        * The controller mount point identifier (e.g. bus/unit address).
        */
       public String controllerMountPoint;

       public ValidateVolumeConfigRequest() {
       }

       public Long getId() {
               return id;
       }

       public void setId(Long id) {
               this.id = id;
       }

       public String getName() {
               return name;
       }

       public void setName(String name) {
               this.name = name;
       }

       public Long getSize() {
               return size;
       }

       public void setSize(Long size) {
               this.size = size;
       }

       public Boolean getRootVolume() {
               return rootVolume;
       }

       public void setRootVolume(Boolean rootVolume) {
               this.rootVolume = rootVolume;
       }

       public Long getStorageType() {
               return storageType;
       }

       public void setStorageType(Long storageType) {
               this.storageType = storageType;
       }

       public Long getMaxIOPS() {
               return maxIOPS;
       }

       public void setMaxIOPS(Long maxIOPS) {
               this.maxIOPS = maxIOPS;
       }

       public Long getDatastoreId() {
               return datastoreId;
       }

       public void setDatastoreId(Long datastoreId) {
               this.datastoreId = datastoreId;
       }

       public Boolean getResizeable() {
               return resizeable;
       }

       public void setResizeable(Boolean resizeable) {
               this.resizeable = resizeable;
       }

       public Boolean getPlanResizable() {
               return planResizable;
       }

       public void setPlanResizable(Boolean planResizable) {
               this.planResizable = planResizable;
       }

       public String getControllerMountPoint() {
               return controllerMountPoint;
       }

       public void setControllerMountPoint(String controllerMountPoint) {
               this.controllerMountPoint = controllerMountPoint;
       }
}
