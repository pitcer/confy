/*
 * MIT License
 *
 * Copyright (c) 2019 Piotr Dobiech
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package pl.pitcer.confy.mapper;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.jetbrains.annotations.Nullable;

public class Mapper {

	private static final List<Class<?>> BASIC_TYPES = List.of(boolean.class, Boolean.class, String.class, Number.class, double.class, Double.class, int.class, Integer.class, long.class, Long.class, Iterable.class, Map.class);

	public Map<String, Object> map(Object object) {
		Map<String, Object> rootMap = new HashMap<>(1);
		Class<?> objectClass = object.getClass();
		//TODO: transform class name to HOCON format (e.g. foo-bar instead of fooBar)
		//TODO: get name from annotation
		String className = objectClass.getSimpleName();
		Map<String, Object> mappedObject = map(objectClass, object);
		rootMap.put(className, mappedObject);
		return rootMap;
	}

	private Map<String, Object> map(Class<?> objectClass, Object object) {
		Field[] fields = objectClass.getDeclaredFields();
		Map<String, Object> fieldsMap = new HashMap<>(fields.length);
		for (Field field : fields) {
			//TODO: transform field name to HOCON format (e.g. foo-bar instead of fooBar)
			//TODO: get name from annotation
			String fieldName = field.getName();
			Object fieldValue = getFieldValue(field, object);
			if (fieldValue != null) {
				Class<?> fieldValueClass = fieldValue.getClass();
				if (!isBasicType(fieldValueClass)) {
					fieldValue = map(fieldValueClass, fieldValue);
				}
			}
			fieldsMap.put(fieldName, fieldValue);
		}
		return fieldsMap;
	}

	@Nullable
	private Object getFieldValue(Field field, Object fieldHolderObject) {
		try {
			field.setAccessible(true);
			return field.get(fieldHolderObject);
		} catch (IllegalAccessException exception) {
			return null;
		}
	}

	private boolean isBasicType(Class<?> type) {
		return BASIC_TYPES.stream()
			.anyMatch(basicType -> basicType.isAssignableFrom(type));
	}
}
