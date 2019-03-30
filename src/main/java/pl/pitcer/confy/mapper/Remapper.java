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
import java.util.Map;
import pl.pitcer.confy.InstanceFactory;

public class Remapper {

	private InstanceFactory instanceFactory;

	public Remapper(InstanceFactory instanceFactory) {
		this.instanceFactory = instanceFactory;
	}

	public <T> T remap(Map<String, Object> map, Class<T> clazz) {
		T instance = this.instanceFactory.createInstance(clazz);
		Field[] fields = clazz.getDeclaredFields();
		for (Field field : fields) {
			//TODO: transform field name to HOCON format (e.g. foo-bar instead of fooBar)
			//TODO: get name from annotation
			String fieldName = field.getName();
			Object value = map.get(fieldName);
			setFieldValue(field, instance, value);
		}
		return instance;
	}

	private void setFieldValue(Field field, Object instance, Object value) {
		try {
			field.setAccessible(true);
			field.set(instance, value);
		} catch (IllegalAccessException exception) {
			exception.printStackTrace();
		}
	}
}
