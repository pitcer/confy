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

import pl.pitcer.confy.annotation.Ignore;
import pl.pitcer.confy.annotation.Property;

public class ComplexAnnotatedConfigWithConstructor {

	@Property("foobarA")
	private String foobar;
	private String foobarWithoutAnnotation;
	@Property("integerA")
	private Integer integer;
	@Ignore
	private String ignored;
	private SimpleAnnotatedConfigWithConstructor part;

	public ComplexAnnotatedConfigWithConstructor(String foobar, String foobarWithoutAnnotation, Integer integer, String ignored, SimpleAnnotatedConfigWithConstructor part) {
		this.foobar = foobar;
		this.foobarWithoutAnnotation = foobarWithoutAnnotation;
		this.integer = integer;
		this.ignored = ignored;
		this.part = part;
	}

	public ComplexAnnotatedConfigWithConstructor() {}

	public String getFoobar() {
		return this.foobar;
	}

	public void setFoobar(String foobar) {
		this.foobar = foobar;
	}

	public String getFoobarWithoutAnnotation() {
		return this.foobarWithoutAnnotation;
	}

	public void setFoobarWithoutAnnotation(String foobarWithoutAnnotation) {
		this.foobarWithoutAnnotation = foobarWithoutAnnotation;
	}

	public Integer getInteger() {
		return this.integer;
	}

	public void setInteger(Integer integer) {
		this.integer = integer;
	}

	public String getIgnored() {
		return this.ignored;
	}

	public void setIgnored(String ignored) {
		this.ignored = ignored;
	}

	public SimpleAnnotatedConfigWithConstructor getPart() {
		return this.part;
	}

	public void setPart(SimpleAnnotatedConfigWithConstructor part) {
		this.part = part;
	}
}
