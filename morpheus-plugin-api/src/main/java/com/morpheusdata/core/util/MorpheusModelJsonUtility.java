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

package com.morpheusdata.core.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.morpheusdata.model.MorpheusModel;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

/**
 * Utility class for serializing and deserializing MorpheusModel objects to/from JSON.
 * Provides a consistent serialization format for MorpheusModel transport.
 *
 * <p>Uses a pre-configured ObjectMapper with:
 * <ul>
 *   <li>ISO 8601 date formatting</li>
 *   <li>GString serialization support (Groovy compatibility)</li>
 * </ul>
 *
 * <p>Usage:
 * <pre>
 *   // Serialize
 *   Role role = new Role();
 *   role.setAuthority("DevOps");
 *   String json = MorpheusModelJsonUtility.serialize(role);
 *
 *   // Deserialize
 *   Role restored = MorpheusModelJsonUtility.deserialize(json, Role.class);
 *
 *   // Lists
 *   String listJson = MorpheusModelJsonUtility.serializeList(roles);
 *   List&lt;Role&gt; roles = MorpheusModelJsonUtility.deserializeList(listJson, Role.class);
 * </pre>
 *
 * @since 1.4.1
 * @author Morpheus Data
 */
public class MorpheusModelJsonUtility {
	private static final ObjectMapper objectMapper = buildObjectMapper();

	private static ObjectMapper buildObjectMapper() {
		ObjectMapper mapper = new ObjectMapper();
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
		mapper.setDateFormat(df);
		mapper.registerModule(new SimpleModule().addSerializer(CharSequence.class, new GStringJsonSerializer()));
		return mapper;
	}

	/**
	 * Serialize a single MorpheusModel instance to JSON.
	 *
	 * @param model the model to serialize
	 * @param <T>   the model type
	 * @return JSON string representation
	 * @throws JsonProcessingException if serialization fails
	 */
	public static <T extends MorpheusModel> String serialize(T model) throws JsonProcessingException {
		return objectMapper.writeValueAsString(model);
	}

	/**
	 * Serialize a list of MorpheusModel instances to JSON array.
	 *
	 * @param models the list of models to serialize
	 * @param <T>    the model type
	 * @return JSON array string representation
	 * @throws JsonProcessingException if serialization fails
	 */
	public static <T extends MorpheusModel> String serializeList(List<T> models) throws JsonProcessingException {
		return objectMapper.writeValueAsString(models);
	}

	/**
	 * Serialize a map of String → MorpheusModel to JSON object.
	 *
	 * @param models the map to serialize
	 * @param <T>    the model type
	 * @return JSON object string representation
	 * @throws JsonProcessingException if serialization fails
	 */
	public static <T extends MorpheusModel> String serializeMap(Map<String, T> models) throws JsonProcessingException {
		return objectMapper.writeValueAsString(models);
	}

	/**
	 * Deserialize a JSON string to a single MorpheusModel instance.
	 *
	 * @param json       the JSON string
	 * @param modelClass the target class
	 * @param <T>        the model type
	 * @return deserialized model instance
	 * @throws JsonProcessingException if deserialization fails
	 */
	public static <T extends MorpheusModel> T deserialize(String json, Class<T> modelClass) throws JsonProcessingException {
		return objectMapper.readValue(json, modelClass);
	}

	/**
	 * Deserialize a JSON array string to a list of MorpheusModel instances.
	 *
	 * @param json       the JSON array string
	 * @param modelClass the target element class
	 * @param <T>        the model type
	 * @return list of deserialized model instances
	 * @throws JsonProcessingException if deserialization fails
	 */
	public static <T extends MorpheusModel> List<T> deserializeList(String json, Class<T> modelClass) throws JsonProcessingException {
		JavaType type = objectMapper.getTypeFactory().constructCollectionType(List.class, modelClass);
		return objectMapper.readValue(json, type);
	}

	/**
	 * Deserialize a JSON object string to a map of String → MorpheusModel.
	 *
	 * @param json       the JSON object string
	 * @param modelClass the target value class
	 * @param <T>        the model type
	 * @return map of deserialized model instances
	 * @throws JsonProcessingException if deserialization fails
	 */
	public static <T extends MorpheusModel> Map<String, T> deserializeMap(String json, Class<T> modelClass) throws JsonProcessingException {
		JavaType type = objectMapper.getTypeFactory().constructMapType(Map.class, String.class, modelClass);
		return objectMapper.readValue(json, type);
	}
}
