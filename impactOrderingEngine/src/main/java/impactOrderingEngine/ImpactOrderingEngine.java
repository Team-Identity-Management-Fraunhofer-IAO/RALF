package impactOrderingEngine;

import java.util.ArrayList;
import java.util.List;

import com.sql.data.objects.persistence.threatmaster.risks.BusinessRisk;
import com.sql.data.objects.persistence.threatmaster.risks.BusinessRiskPairwiseComparison;

import engines.ahp.BusinessRiskAHP;

public class ImpactOrderingEngine {

	public static void main(String[] args) {
		List<BusinessRiskPairwiseComparison> comparisons = new ArrayList<BusinessRiskPairwiseComparison>();
		List<BusinessRisk> businessRisks = new ArrayList<BusinessRisk>();
		System.out.println("Creating comparisons...");
		for (int i = 1; i < 6; i++) {			
			/*for (int j = i+1; j<6; j++) {
				BusinessRiskPairwiseComparison comparison = new BusinessRiskPairwiseComparison();
				comparison.setBusiness_risk_id1(i);
				comparison.setBusiness_risk_id2(j);
				comparison.setService_id(0);
				double comp = Math.random()*10;
				comp = (comp > 9.0?9.0:comp);
				comp = (comp < 1.0?1.0:comp);
				comparison.setComparison((int) Math.round(comp));
				comparisons.add(comparison);
				System.out.println("[Business Risk "+i+"] versus [Business Risk "+j+"] --> "+comparison.getComparison());
			}*/
			BusinessRisk risk = new BusinessRisk();
			risk.setBusiness_risk_id(i);
			risk.setBusiness_risk_name("Business Risk "+i);
			businessRisks.add(risk);
		}
		BusinessRiskPairwiseComparison c12 = new BusinessRiskPairwiseComparison();
		c12.setBusiness_risk_id1(1);
		c12.setBusiness_risk_id2(2);
		c12.setService_id(0);
		c12.setComparison(8);
		comparisons.add(c12);
		BusinessRiskPairwiseComparison c13 = new BusinessRiskPairwiseComparison();
		c13.setBusiness_risk_id1(1);
		c13.setBusiness_risk_id2(3);
		c13.setService_id(0);
		c13.setComparison(2);
		comparisons.add(c13);
		BusinessRiskPairwiseComparison c14 = new BusinessRiskPairwiseComparison();
		c14.setBusiness_risk_id1(1);
		c14.setBusiness_risk_id2(4);
		c14.setService_id(0);
		c14.setComparison(2);
		comparisons.add(c14);
		BusinessRiskPairwiseComparison c15 = new BusinessRiskPairwiseComparison();
		c15.setBusiness_risk_id1(1);
		c15.setBusiness_risk_id2(5);
		c15.setService_id(0);
		c15.setComparison(9);
		comparisons.add(c15);
		BusinessRiskPairwiseComparison c23 = new BusinessRiskPairwiseComparison();
		c23.setBusiness_risk_id1(2);
		c23.setBusiness_risk_id2(3);
		c23.setService_id(0);
		c23.setComparison(1);
		comparisons.add(c23);
		BusinessRiskPairwiseComparison c24 = new BusinessRiskPairwiseComparison();
		c24.setBusiness_risk_id1(2);
		c24.setBusiness_risk_id2(4);
		c24.setService_id(0);
		c24.setComparison(3);
		comparisons.add(c24);
		BusinessRiskPairwiseComparison c25 = new BusinessRiskPairwiseComparison();
		c25.setBusiness_risk_id1(2);
		c25.setBusiness_risk_id2(5);
		c25.setService_id(0);
		c25.setComparison(3);
		comparisons.add(c25);
		BusinessRiskPairwiseComparison c35 = new BusinessRiskPairwiseComparison();
		c35.setBusiness_risk_id1(3);
		c35.setBusiness_risk_id2(5);
		c35.setService_id(0);
		c35.setComparison(3);
		comparisons.add(c35);
		BusinessRiskPairwiseComparison c43 = new BusinessRiskPairwiseComparison();
		c43.setBusiness_risk_id1(4);
		c43.setBusiness_risk_id2(3);
		c43.setService_id(0);
		c43.setComparison(3);
		comparisons.add(c43);
		BusinessRiskPairwiseComparison c45 = new BusinessRiskPairwiseComparison();
		c45.setBusiness_risk_id1(4);
		c45.setBusiness_risk_id2(5);
		c45.setService_id(0);
		c45.setComparison(3);
		comparisons.add(c45);
		System.out.println("");
		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
		BusinessRiskAHP orderingEngine = new BusinessRiskAHP(businessRisks, comparisons);
		double[] weights = orderingEngine.compute();
		double consistencyIndex = orderingEngine.getConsistencyIndex();
		System.out.println("Consistency Index "+consistencyIndex);
		for (BusinessRisk  risk : businessRisks) {
			System.out.println(risk.getBusiness_risk_id()+" ["+risk.getBusiness_risk_name()+"] --> "+(weights[businessRisks.indexOf(risk)]*100));
		}
		orderingEngine.findInconsistencies();
	}

}
