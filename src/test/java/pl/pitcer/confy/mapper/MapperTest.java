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

import java.util.Map;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.pitcer.confy.ComplexAnnotatedConfigWithConstructor;
import pl.pitcer.confy.ComplexConfigWithConstructor;
import pl.pitcer.confy.SimpleAnnotatedConfigWithConstructor;
import pl.pitcer.confy.SimpleConfigWithConstructor;

public class MapperTest {

	private Mapper mapper;

	@BeforeEach
	public void setUp() {
		this.mapper = new Mapper();
	}

	@Test
	public void testMapWithSimpleConfig() {
		SimpleConfigWithConstructor config = new SimpleConfigWithConstructor("test", 2);
		Map<String, Object> map = this.mapper.map(config);
		Assertions.assertEquals("test", map.get("foobar"));
		Assertions.assertEquals(2, map.get("integer"));
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testMapWithComplexConfig() {
		ComplexConfigWithConstructor config = new ComplexConfigWithConstructor("test1", 1, new SimpleConfigWithConstructor("testPart", 3));
		Map<String, Object> map = this.mapper.map(config);
		Assertions.assertEquals("test1", map.get("foobar"));
		Assertions.assertEquals(1, map.get("integer"));
		map = (Map<String, Object>) map.get("part");
		Assertions.assertEquals("testPart", map.get("foobar"));
		Assertions.assertEquals(3, map.get("integer"));
	}

	@Test
	public void testMapWithSimpleAnnotatedConfig() {
		SimpleAnnotatedConfigWithConstructor config = new SimpleAnnotatedConfigWithConstructor("test", "test2", 2, "notnull");
		Map<String, Object> map = this.mapper.map(config);
		Assertions.assertEquals("test", map.get("foobarA"));
		Assertions.assertEquals("test2", map.get("foobarWithoutAnnotation"));
		Assertions.assertEquals(2, map.get("integerA"));
		Assertions.assertNull(map.get("ignored"));
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testMapWithComplexAnnotatedConfig() {
		ComplexAnnotatedConfigWithConstructor config = new ComplexAnnotatedConfigWithConstructor("test", "test2", 2, "notnull", new SimpleAnnotatedConfigWithConstructor("test", "test2", 2, "notnull"));
		Map<String, Object> map = this.mapper.map(config);
		Assertions.assertEquals("test", map.get("foobarA"));
		Assertions.assertEquals("test2", map.get("foobarWithoutAnnotation"));
		Assertions.assertEquals(2, map.get("integerA"));
		Assertions.assertNull(map.get("ignored"));
		map = (Map<String, Object>) map.get("part");
		Assertions.assertEquals("test", map.get("foobarA"));
		Assertions.assertEquals("test2", map.get("foobarWithoutAnnotation"));
		Assertions.assertEquals(2, map.get("integerA"));
		Assertions.assertNull(map.get("ignored"));
	}
}
