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

package pl.pitcer.confy;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ConfigTest {

	@Test
	public void testLoad() throws URISyntaxException {
		File file = getResourceFile("test-load.conf");
		Config<ComplexAnnotatedConfigWithConstructor> config = new Config<>(ComplexAnnotatedConfigWithConstructor.class, file);
		ComplexAnnotatedConfigWithConstructor configObject = config.load();
		Assertions.assertEquals("test", configObject.getFoobar());
		Assertions.assertEquals("test2", configObject.getFoobarWithoutAnnotation());
		Assertions.assertEquals(1, configObject.getInteger());
		Assertions.assertNull(configObject.getIgnored());
		SimpleAnnotatedConfigWithConstructor part = configObject.getPart();
		Assertions.assertEquals("test", part.getFoobar());
		Assertions.assertEquals("test2", part.getFoobarWithoutAnnotation());
		Assertions.assertEquals(1, part.getInteger());
		Assertions.assertNull(part.getIgnored());
	}

	@Test
	public void testSave() throws URISyntaxException {
		File file = getResourceFile("test-save.conf");
		Config<ComplexAnnotatedConfigWithConstructor> config = new Config<>(ComplexAnnotatedConfigWithConstructor.class, file);
		ComplexAnnotatedConfigWithConstructor configObject = new ComplexAnnotatedConfigWithConstructor("test", "test2", 1, "test3", new SimpleAnnotatedConfigWithConstructor("test", "test2", 1, "test3"));
		Assertions.assertDoesNotThrow(() -> config.save(configObject));
	}

	private File getResourceFile(String name) throws URISyntaxException {
		ClassLoader loader = ConfigTest.class.getClassLoader();
		URL resource = loader.getResource(name);
		Assertions.assertNotNull(resource);
		URI uri = resource.toURI();
		Path path = Path.of(uri);
		return path.toFile();
	}
}
