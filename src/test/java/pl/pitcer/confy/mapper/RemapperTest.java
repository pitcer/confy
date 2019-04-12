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

public class RemapperTest {

	private Remapper remapper;

	@BeforeEach
	public void setUp() {
		this.remapper = new Remapper();
	}

	@Test
	public void testRemapSimpleConfig() {
		Map<String, Object> map = Map.of("foobar", "test", "integer", 2);
		SimpleConfigWithConstructor config = this.remapper.remap(map, SimpleConfigWithConstructor.class);
		Assertions.assertEquals("test", config.getFoobar());
		Assertions.assertEquals(2, config.getInteger());
	}

	@Test
	public void testRemapComplexConfig() {
		Map<String, Object> map = Map.of("foobar", "test", "integer", 2, "part", Map.of("foobar", "test2", "integer", 3));
		ComplexConfigWithConstructor config = this.remapper.remap(map, ComplexConfigWithConstructor.class);
		Assertions.assertEquals("test", config.getFoobar());
		Assertions.assertEquals(2, config.getInteger());
		SimpleConfigWithConstructor part = config.getPart();
		Assertions.assertEquals("test2", part.getFoobar());
		Assertions.assertEquals(3, part.getInteger());
	}

	@Test
	public void testRemapSimpleAnnotatedConfig() {
		Map<String, Object> map = Map.of("foobarA", "test", "foobar-without-annotation", "test2", "integerA", 2, "ignored", "notNull");
		SimpleAnnotatedConfigWithConstructor config = this.remapper.remap(map, SimpleAnnotatedConfigWithConstructor.class);
		Assertions.assertEquals("test", config.getFoobar());
		Assertions.assertEquals("test2", config.getFoobarWithoutAnnotation());
		Assertions.assertEquals(2, config.getInteger());
		Assertions.assertNull(config.getIgnored());
	}

	@Test
	public void testRemapComplexAnnotatedConfig() {
		Map<String, Object> map = Map.of("foobarA", "test", "foobar-without-annotation", "test2", "integerA", 2, "part", Map.of("foobarA", "test", "foobar-without-annotation", "test2", "integerA", 2));
		ComplexAnnotatedConfigWithConstructor config = this.remapper.remap(map, ComplexAnnotatedConfigWithConstructor.class);
		Assertions.assertEquals("test", config.getFoobar());
		Assertions.assertEquals("test2", config.getFoobarWithoutAnnotation());
		Assertions.assertEquals(2, config.getInteger());
		Assertions.assertNull(config.getIgnored());
		SimpleAnnotatedConfigWithConstructor part = config.getPart();
		Assertions.assertEquals("test", part.getFoobar());
		Assertions.assertEquals("test2", part.getFoobarWithoutAnnotation());
		Assertions.assertEquals(2, part.getInteger());
		Assertions.assertNull(part.getIgnored());
	}
}
