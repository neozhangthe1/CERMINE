/**
 * This file is part of CERMINE project.
 * Copyright (c) 2011-2013 ICM-UW
 *
 * CERMINE is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * CERMINE is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with CERMINE. If not, see <http://www.gnu.org/licenses/>.
 */

package pl.edu.icm.cermine.parsing.features;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import pl.edu.icm.cermine.parsing.model.ParsableString;
import pl.edu.icm.cermine.parsing.model.Token;
import pl.edu.icm.cermine.parsing.tools.TextClassifier;

/**
 * A token is considered as rare if it is word that does not belong to the set of common words.
 * 
 * @author Bartosz Tarnawski
 */
public class IsRareFeature extends BinaryTokenFeatureCalculator {

	private Set<String> commonWords;
	private boolean caseSensitive;
	
	
	/**
	 * @param commonWordsList the words that are not considered as rare
	 * @param caseSensitive whether the lookups should be case-sensitive
	 */
	public IsRareFeature(List<String> commonWordsList, boolean caseSensitive) {
		this.commonWords = new HashSet<String>();
		this.caseSensitive = caseSensitive;
		
		for (String commonWord : commonWordsList) {
			if (caseSensitive) {
				commonWords.add(commonWord);
			} else {
				commonWords.add(commonWord.toLowerCase());
			}
		}
	}
	
	@Override
	public boolean calculateFeaturePredicate(Token<?> token, ParsableString<?> context) {
		String text = token.getText();
		if (!TextClassifier.isWord(text)) {
			return false;
		}
		if (!caseSensitive) {
			text = text.toLowerCase();
		}
		return !commonWords.contains(text);
	}

}
