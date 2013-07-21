package pl.edu.icm.cermine.metadata.zoneclassification.features;

import pl.edu.icm.cermine.metadata.zoneclassification.tools.ZoneClassificationUtils;
import pl.edu.icm.cermine.structure.model.BxPage;
import pl.edu.icm.cermine.structure.model.BxZone;

public class FullWordsRelativeFeature extends AbstractFeatureCalculator<BxZone, BxPage> {
    
	@Override
	public double calculateFeatureValue(BxZone object, BxPage context) {
		String text = object.toText();
		String[] words = text.split("\\s");
		Integer numberOfWords = 0;
		Integer numberOfFullWords = 0;
		for(String word: words) {
			if(ZoneClassificationUtils.isConjunction(word)) {
				++numberOfFullWords;
			}
			else if(word.length() > 2 && !word.matches(".*\\d.*") && !word.matches(".*[^\\p{Alnum}].*")) {
    			++numberOfFullWords;
			}
			++numberOfWords;
		}
		return (double)numberOfFullWords/numberOfWords;
	}

}
