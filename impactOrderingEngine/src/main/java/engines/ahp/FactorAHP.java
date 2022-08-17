package engines.ahp;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.math3.linear.Array2DRowRealMatrix;
import org.apache.commons.math3.linear.EigenDecomposition;
import org.apache.commons.math3.linear.RealVector;

import engines.ahp.factor.orderingObjects.Factor;
import engines.ahp.factor.orderingObjects.FactorOrdering;

public class FactorAHP {
	private List<FactorOrdering> pairwiseComparisons;
	private List<Factor> indices;
	private Array2DRowRealMatrix comparisonMatrix;
	private EigenDecomposition evd;
	private int evIdx;  //Position of greatest eigenvalue
	private int nrOfAlternatives = 0;
	private int[][] adjacencyMatrix;
	private int highestIndex;
	
	private List<int[]> inconsistencies;
	
	public FactorAHP(List<Factor> indices) {
		this.indices = indices;
	}
	
	public FactorAHP(List<Factor> indicies, List<FactorOrdering> pairwiseComparisons) {
		this.indices = indicies;
		this.pairwiseComparisons = pairwiseComparisons;
	}
	
	public double[] compute() {
		nrOfAlternatives = this.indices.size();
		comparisonMatrix = new Array2DRowRealMatrix(nrOfAlternatives,nrOfAlternatives);
		adjacencyMatrix = new int[nrOfAlternatives][nrOfAlternatives];
		for (FactorOrdering pairwiseComparison : pairwiseComparisons) {
			Factor fac1 = new Factor();
			Factor fac2 = new Factor();
			fac1.setFactor_id(pairwiseComparison.getFactor_id1());
			fac2.setFactor_id(pairwiseComparison.getFactor_id2());
			int i1 = indices.indexOf(fac1);
			int i2 = indices.indexOf(fac2);
			double comparison = pairwiseComparison.getComparison();
			comparisonMatrix.setEntry(i1, i2, comparison);
			comparisonMatrix.setEntry(i2, i1, 1/comparison);
			comparisonMatrix.setEntry(i1, i1, 1.0);
			comparisonMatrix.setEntry(i2, i2, 1.0);
			adjacencyMatrix[i1][i2] = (int) Math.round(comparison);
			adjacencyMatrix[i2][i1] = 0;
			adjacencyMatrix[i1][i1] = 0;
		}
		System.out.println(comparisonMatrix);
		evd = new EigenDecomposition(comparisonMatrix);
		
		evIdx = 0;
		for (int i = 0; i < evd.getRealEigenvalues().length;i++) {
			evIdx = evd.getRealEigenvalue(i) > evd.getRealEigenvalue(evIdx) ? i : evIdx;
		}
		
		RealVector v = evd.getEigenvector(evIdx);
		
		double[] weights = new double[nrOfAlternatives];
		double sum = 0.0;
		for (Double d : v.toArray()) {
			sum += d;
		}
		highestIndex = 0;
		double highestWeight = 0.00;
		for (int i = 0; i < v.getDimension(); i++) {
			weights[i] = v.getEntry(i) / sum;
			if (highestWeight < weights[i]) {
				highestWeight = weights[i];
				highestIndex = i;
			}
		}
		
		return weights;
	}
	
	public double getConsistencyIndex() {
		return (evd.getRealEigenvalue(evIdx) - nrOfAlternatives) / (nrOfAlternatives -1);
	}
	
	private void findCycle(int currentIndex, int path[], int[] visitedIndicesOld, int[][] adjacencyMatrix) {
		int visitedIndices[] = visitedIndicesOld.clone();		
		for (int i = 0; i < adjacencyMatrix[currentIndex].length; i++) {
			if (adjacencyMatrix[currentIndex][i] == 0)
				continue;
			if (currentIndex == i)
				continue;
			int[] finalPath = new int[path.length+1];
			System.arraycopy(path, 0, finalPath, 0, path.length);
			finalPath[path.length] = i;
			if (visitedIndices[i] != 0) {				
				inconsistencies.add(finalPath);
			}else {
				visitedIndices[i] = 1;
				findCycle(i, finalPath, visitedIndices, adjacencyMatrix);
			}
		}
	}
	
	public void findInconsistencies() {
		int[] visitedIndices = new int[nrOfAlternatives];
		inconsistencies = new ArrayList<int[]>();
		findCycle(highestIndex, new int[0], visitedIndices, adjacencyMatrix);
		if (inconsistencies.isEmpty()) {
			System.out.println("No inconsistencies detected!");
		}else {
			for (int[] path : inconsistencies) {
				System.out.println("Inconsistent path detected: ");
				for (int i = 0; i < path.length; i++) {
					System.out.print(path[i]+(i==path.length-1?"":" -> "));
				}
				System.out.println();
			}
		}
	}
	

	

}
