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

package pl.pitcer.confy.util;

public final class NameTransformer {

	private NameTransformer() {
		throw new UnsupportedOperationException("Cannot create instance of utility class");
	}

	public static String transformName(String name) {
		int nameLength = name.length();
		StringBuilder builder = new StringBuilder(nameLength);
		for (int index = 0; index < nameLength; index++) {
			char character = name.charAt(index);
			if (Character.isUpperCase(character)) {
				if (isPreviousCharacterLowerCase(name, index) || isPreviousCharacterUpperCase(name, index) && isNextCharacterLowerCase(name, index)) {
					builder.append('-');
				}
				character = Character.toLowerCase(character);
			}
			builder.append(character);
		}
		return builder.toString();
	}

	private static boolean isPreviousCharacterLowerCase(String string, int index) {
		if (index > 0) {
			char previousCharacter = string.charAt(index - 1);
			return Character.isLowerCase(previousCharacter);
		}
		return false;
	}

	private static boolean isPreviousCharacterUpperCase(String string, int index) {
		if (index > 0) {
			char previousCharacter = string.charAt(index - 1);
			return Character.isUpperCase(previousCharacter);
		}
		return false;
	}

	private static boolean isNextCharacterLowerCase(String string, int index) {
		int nextIndex = index + 1;
		if (nextIndex < string.length()) {
			char nextCharacter = string.charAt(nextIndex);
			return Character.isLowerCase(nextCharacter);
		}
		return false;
	}
}
