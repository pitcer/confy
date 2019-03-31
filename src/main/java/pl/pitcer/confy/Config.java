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

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;
import com.typesafe.config.ConfigFactory;
import com.typesafe.config.ConfigRenderOptions;
import com.typesafe.config.ConfigValue;
import com.typesafe.config.ConfigValueFactory;
import pl.pitcer.confy.mapper.InstanceFactory;
import pl.pitcer.confy.mapper.Mapper;
import pl.pitcer.confy.mapper.Remapper;
import pl.pitcer.confy.util.NameTransformer;

public class Config<T> {

	private Class<? extends T> configClass;
	private File file;
	private Mapper mapper = new Mapper();
	private Remapper remapper = new Remapper(new InstanceFactory());

	public Config(Class<? extends T> configClass, File file) {
		this.configClass = configClass;
		this.file = file;
	}

	@SuppressWarnings("unchecked")
	public T load() {
		com.typesafe.config.Config config = ConfigFactory.parseFile(this.file);
		String configName = getConfigName();
		Map<String, Object> map = (Map<String, Object>) config.getAnyRef(configName);
		return this.remapper.remap(map, this.configClass);
	}

	public void save(T config) {
		Map<String, Object> mapped = this.mapper.map(config);
		ConfigValue value = ConfigValueFactory.fromAnyRef(mapped);
		String configName = getConfigName();
		ConfigRenderOptions options = ConfigRenderOptions.defaults()
			.setJson(false)
			.setOriginComments(false);
		String rendered = ConfigFactory.empty()
			.withValue(configName, value)
			.root()
			.render(options);
		createFile();
		writeToFile(rendered);
	}

	private String getConfigName() {
		pl.pitcer.confy.annotation.Config config = this.configClass.getAnnotation(pl.pitcer.confy.annotation.Config.class);
		if (config != null) {
			return config.value();
		}
		String name = this.configClass.getSimpleName();
		return NameTransformer.transformName(name);
	}

	private void createFile() {
		try {
			if (!this.file.exists()) {
				this.file.createNewFile();
			}
		} catch (IOException exception) {
			throw new RuntimeException(exception);
		}
	}

	private void writeToFile(String rendered) {
		try (FileWriter fileWriter = new FileWriter(this.file); BufferedWriter writer = new BufferedWriter(fileWriter)) {
			writer.write(rendered);
			writer.flush();
		} catch (IOException exception) {
			throw new RuntimeException(exception);
		}
	}
}
