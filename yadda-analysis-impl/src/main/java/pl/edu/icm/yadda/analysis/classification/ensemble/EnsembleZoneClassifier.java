package pl.edu.icm.yadda.analysis.classification.ensemble;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import pl.edu.icm.yadda.analysis.AnalysisException;
import pl.edu.icm.yadda.analysis.textr.ZoneClassifier;
import pl.edu.icm.yadda.analysis.textr.model.BxDocument;
import pl.edu.icm.yadda.analysis.textr.model.BxZone;
import pl.edu.icm.yadda.analysis.textr.model.BxZoneLabel;
import pl.edu.icm.yadda.analysis.textr.model.BxZoneLabelCategory;

public class EnsembleZoneClassifier implements ZoneClassifier {
	
	private List<ZoneClassifier> classifiers; 
	private List<Double> wrongClassificationCosts;
    private List<BxZoneLabel> zoneLabels = BxZoneLabel.valuesOfCategory(BxZoneLabelCategory.CAT_GENERAL);

    public EnsembleZoneClassifier(List<ZoneClassifier> classifiers, List<Double> wrongClassificationCosts, List<BxZoneLabel> zoneLabels) {
    	assert wrongClassificationCosts.size() == zoneLabels.size();
    	
    	this.classifiers = classifiers;
    	this.wrongClassificationCosts = wrongClassificationCosts;
    	this.zoneLabels = zoneLabels;
    }
    
    
	@Override
	public BxDocument classifyZones(BxDocument document) throws AnalysisException 
	{
		List<Map<BxZoneLabel, Integer>> votesForZones = new ArrayList<Map<BxZoneLabel, Integer>>(document.asZones().size());
		for(Integer zoneIdx=0; zoneIdx < document.asZones().size(); ++zoneIdx) {
			votesForZones.add(new HashMap<BxZoneLabel, Integer>());
			for(BxZoneLabel label: zoneLabels) {
				votesForZones.get(zoneIdx).put(label, 0);
			}
		}

		for(ZoneClassifier classifier: classifiers) {
			classifier.classifyZones(document);
			for(Integer zoneIdx=0; zoneIdx < votesForZones.size(); ++zoneIdx) {
				BxZoneLabel curZoneLabel = document.asZones().get(zoneIdx).getLabel();
				Integer votesUntilNow = votesForZones.get(zoneIdx).get(curZoneLabel);
				votesForZones.get(zoneIdx).put(curZoneLabel, votesUntilNow+1);
			}
		}
		
		chooseBestLabels(document, votesForZones);
			
		for(BxZone zone: document.asZones()) {
			assert zoneLabels.contains(zone.getLabel());
		}
        
        return document;
	}


	private void chooseBestLabels(BxDocument document, List<Map<BxZoneLabel, Integer>> votesForZones) {
		//iterate over all the zones
		for(Integer zoneIdx=0; zoneIdx < document.asZones().size(); ++zoneIdx) {
			Integer bestLabelIdx = -1;
			Double bestLabelVote = Double.NEGATIVE_INFINITY;
			
			//iterate over all possible labels
			for(Integer labelIdx=0; labelIdx < zoneLabels.size(); ++labelIdx) {
				//check current label counter
				Integer labelCounter = votesForZones.get(zoneIdx).get(zoneLabels.get(labelIdx));
				//calculate biased counter value
				Double labelVote = wrongClassificationCosts.get(labelIdx)*labelCounter;
				//check if it's the best one
				if(labelVote > bestLabelVote) {
					bestLabelIdx = labelIdx;
					bestLabelVote = labelVote;
				}
			}
			assert bestLabelIdx != -1 && bestLabelVote != Double.NEGATIVE_INFINITY;
			BxZoneLabel chosenLabel = zoneLabels.get(bestLabelIdx);
			//assign the best label to the current zone
			document.asZones().get(zoneIdx).setLabel(chosenLabel);
		}
	}

}
