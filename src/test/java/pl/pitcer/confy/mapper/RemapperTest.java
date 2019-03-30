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
import pl.pitcer.confy.InstanceFactory;
import pl.pitcer.confy.SimpleConfigWithConstructor;

public class RemapperTest {

	private Remapper remapper;

	@BeforeEach
	public void setUp() {
		InstanceFactory instanceFactory = new InstanceFactory();
		this.remapper = new Remapper(instanceFactory);
	}

	@Test
	public void testRemapSimpleConfig() {
		Map<String, Object> map = Map.of("foobar", "test", "integer", 2);
		SimpleConfigWithConstructor config = this.remapper.remap(map, SimpleConfigWithConstructor.class);
		Assertions.assertEquals("test", config.getFoobar());
		Assertions.assertEquals(2, config.getInteger());
	}
}
