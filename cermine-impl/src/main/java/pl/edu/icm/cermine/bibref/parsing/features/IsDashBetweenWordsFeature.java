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

package pl.edu.icm.cermine.bibref.parsing.features;

import org.apache.commons.lang.ArrayUtils;
import pl.edu.icm.cermine.bibref.parsing.model.Citation;
import pl.edu.icm.cermine.bibref.parsing.model.CitationToken;
import pl.edu.icm.cermine.tools.classification.features.FeatureCalculator;

/**
 *
 * @author Dominika Tkaczyk (dtkaczyk@icm.edu.pl)
 */
public class IsDashBetweenWordsFeature extends FeatureCalculator<CitationToken, Citation> {

    private static final char[] DASH_CHARS = {
        '-', '\u002D', '\u2010', '\u2011', '\u2012', '\u2013', '\u2014', '\u2015', '\u207B', '\u208B', '\u2212'};
    
    @Override
    public double calculateFeatureValue(CitationToken object, Citation context) {
        String text = object.getText();
        if (text.length() != 1 || object.getStartIndex() <= 0 || object.getEndIndex() >= context.getText().length()) {
            return 0;
        }
        if (!ArrayUtils.contains(DASH_CHARS, text.charAt(0))) {
            return 0;
        }
        return (Character.isLetter(context.getText().charAt(object.getStartIndex() - 1))
                && Character.isLetter(context.getText().charAt(object.getEndIndex()))) ? 1 : 0;
    }

}
