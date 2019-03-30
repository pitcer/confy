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

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import org.jetbrains.annotations.Nullable;
import sun.reflect.ReflectionFactory;

public class InstanceFactory {

	@SuppressWarnings("UseOfSunClasses")
	private static final ReflectionFactory REFLECTION_FACTORY = ReflectionFactory.getReflectionFactory();
	private static final Constructor<Object> OBJECT_CONSTRUCTOR = getObjectConstructor();

	private static Constructor<Object> getObjectConstructor() {
		try {
			return Object.class.getDeclaredConstructor();
		} catch (NoSuchMethodException exception) {
			throw new RuntimeException(exception);
		}
	}

	public <T> T createInstance(Class<T> clazz) {
		try {
			Constructor<T> constructor = getConstructor(clazz);
			constructor.setAccessible(true);
			return constructor.newInstance();
		} catch (InstantiationException | IllegalAccessException | InvocationTargetException exception) {
			throw new RuntimeException(exception);
		}
	}

	private <T> Constructor<T> getConstructor(Class<T> clazz) {
		Constructor<T> emptyConstructor = getEmptyConstructor(clazz);
		if (emptyConstructor != null) {
			return emptyConstructor;
		}
		return getSerializationConstructor(clazz);
	}

	@Nullable
	private <T> Constructor<T> getEmptyConstructor(Class<T> clazz) {
		try {
			return clazz.getDeclaredConstructor();
		} catch (NoSuchMethodException exception) {
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	private <T> Constructor<T> getSerializationConstructor(Class<T> clazz) {
		Constructor<?> constructor = REFLECTION_FACTORY.newConstructorForSerialization(clazz, OBJECT_CONSTRUCTOR);
		return (Constructor<T>) constructor;
	}
}
