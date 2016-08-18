/**
 * Simple Naive Bayes algorithm.
 *
 * @author Scott Sanner
 */

package edu.anu.csit.ml.classifier;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class NaiveBayes {

    public static DecimalFormat DECIMAL_FORMAT = new DecimalFormat("0.####");

    public ArffData arffData = null;
    public int classIndex = -1;

    public double DIRICHLET_PRIOR = 1d;
    public ArrayList<ClassCondProb> _condProb = null;

    public NaiveBayes(double dirichlet_prior) {
        DIRICHLET_PRIOR = dirichlet_prior;

        // Bad to have zero counts... makes cases not seen in data
        // impossible and can lead to divide by zero.
        if (DIRICHLET_PRIOR == 0d)
            DIRICHLET_PRIOR = 1e-6d;
    }

    public static void main(String args[]) {

        System.out.println("Running NaiveBayes:\n");

        ArffData data = new ArffData("data/classifier/vote.arff");
        //ArffData data = new ArffData("data/classifier/vote_sparse.arff");
        //ArffData data = new ArffData("data/classifier/newsgroups.arff");

        // Assume classification attribute always comes last
        int CLASS_INDEX = data._attr.size() - 1;

        // Split data into train (80%) / test (20%)
        ArffData.SplitData s = data.splitData(.8d);

        // Build a NB classifier and train
        NaiveBayes naiveBayes = new NaiveBayes(1.0d /* prior counts */);
        naiveBayes.clear();
        naiveBayes.setTrainData(s._train);
        naiveBayes.train(CLASS_INDEX);

        // Diagnostic output
        System.out.println(data); // View data
        System.out.println(naiveBayes); // View what has been learned

        // Evaluate accuracy of trained classifier on train and test data
        System.out.println("Accuracy on train: " + naiveBayes.accuracy(s._train._data));
        System.out.println("Accuracy on test:  " + naiveBayes.accuracy(s._test._data));
    }

    public void setTrainData(ArffData arff_data) {
        //System.out.println("Setting data: " + arff_data.toString());
        arffData = arff_data;
    }

    public String toString() {
        StringBuffer sb = new StringBuffer("\nNaive Bayes CPTs [" + _condProb.size() + "]:\n\n");
        for (int i = 0; i < _condProb.size(); i++) {
            ClassCondProb ccp = _condProb.get(i);
            sb.append("Attribute: ").append(arffData._attr.get(i).name).append("\n");
            sb.append(ccp.toString()).append("\n");
        }
        return sb.toString();
    }

    public String getName() {
        return "NaiveBayes";
    }

    public void clear() {
        _condProb = null;
    }

    // TODO: Should redo training to be incremental!
    public void train(int class_index) {

        classIndex = class_index;
        if (arffData == null) {
            System.out.println("No data!");
        }

        _condProb = new ArrayList<ClassCondProb>(arffData._attr.size());

        //System.out.println("Training for " + _condProb.size() + " attributes.");

        // Build conditional probability tables
        ArffData.Attribute ca = arffData._attr.get(class_index);
        if (ca.type != ArffData.TYPE_CLASS) {
            System.out.println("Cannot classify non-class attribute index " +
                    class_index + ":\n" + ca);
            System.exit(1);
        }

        // For each class, record count with positive and record
        // count with negative
        for (int i = 0; i < arffData._attr.size(); i++) {

            // TODO: Inefficient to constantly recompute
            int[] overall_count = new int[ca.class_vals.size()];
            //System.out.println("Processing " + i);
            ClassCondProb ccp = new ClassCondProb(i);
            _condProb.add(ccp);

            // Put the prior in this class
            if (i == class_index) {
                ccp._logprob = new double[ca.class_vals.size()][];
                for (int j = 0; j < ca.class_vals.size(); j++) {
                    ccp._logprob[j] = new double[1];
                }
                for (int j = 0; j < arffData._data.size(); j++) {
                    ArffData.DataEntry de = arffData._data.get(j);
                    int class_value = (Integer) de.getData(class_index);
                    ccp._logprob[class_value][0] = ccp._logprob[class_value][0] + 1d;
                }
                // Normalize and take log
                for (int j = 0; j < ca.class_vals.size(); j++) {
                    if (DIRICHLET_PRIOR + ccp._logprob[j][0] > 0d)
                        ccp._logprob[j][0] = Math.log((DIRICHLET_PRIOR + ccp._logprob[j][0]) /
                                (arffData._data.size() + ca.class_vals.size() * DIRICHLET_PRIOR));
                }
                continue;
            }

            // Otherwise compute the conditional probabilities for this attribute
            ArffData.Attribute a = arffData._attr.get(i);
            if (a.type != ArffData.TYPE_CLASS) {
                System.out.println("Cannot classify non-class attribute index " +
                        i + ":\n" + a);
                System.exit(1);
            }

            ccp._logprob = new double[a.class_vals.size()][];
            for (int j = 0; j < a.class_vals.size(); j++) {
                ccp._logprob[j] = new double[ca.class_vals.size()];
            }

            // Sort data entries into subnodes
            for (int j = 0; j < arffData._data.size(); j++) {
                ArffData.DataEntry de = arffData._data.get(j);
                int attr_value = (Integer) de.getData(i);
                int class_value = (Integer) de.getData(class_index);
                ccp._logprob[attr_value][class_value] = ccp._logprob[attr_value][class_value] + 1d;
                overall_count[class_value]++;
            }

            // Normalize and take log
            for (int av = 0; av < a.class_vals.size(); av++) {
                for (int cv = 0; cv < ca.class_vals.size(); cv++) {
                    if (DIRICHLET_PRIOR + ccp._logprob[av][cv] != 0d)
                        ccp._logprob[av][cv] = Math.log((DIRICHLET_PRIOR + ccp._logprob[av][cv])
                                / (overall_count[cv] + DIRICHLET_PRIOR * ca.class_vals.size()));
                }
            }
        }
        //System.out.println("Constructed " + _condProb.size() + " CPTs.");
        //System.out.println(this);
    }

    public int evaluate(ArffData.DataEntry de) {

        // Get class attribute
        ArffData.Attribute ca = arffData._attr.get(classIndex);
        if (ca.type != ArffData.TYPE_CLASS) {
            System.out.println("Cannot classify non-class attribute index " +
                    classIndex + ":\n" + ca);
            System.exit(1);
        }

        // For each class, record count with positive and record
        // count with negative
        int best_class = -1;
        double best_class_value = Double.NEGATIVE_INFINITY;
        for (int i = 0; i < ca.class_vals.size(); i++) {

            double class_value = 0d;
            for (int j = 0; j < _condProb.size(); j++) {

                ClassCondProb ccp = _condProb.get(j);
                if (j == classIndex) {
                    class_value += ccp._logprob[i][0];
                } else {
                    //System.out.print(((Integer)de.getData(j)).intValue() + " ");
                    class_value += ccp._logprob[(Integer) de.getData(j)][i];
                }
            }

            //System.out.println("[" + i + "] " + class_value);
            if (class_value > best_class_value) {
                best_class = i;
                best_class_value = class_value;
            }
        }

        //System.out.println("Best [" + best_class + "] " + best_class_value + " :: " + de);
        return best_class;
    }

    public double accuracy(ArrayList<ArffData.DataEntry> data) {
        int correct = 0;
        for (ArffData.DataEntry de : data) {
            int pred = evaluate(de); // Evaluate returns sorted results
            int actual = (Integer) de.getData(classIndex);
            if (pred == actual) correct++;
            //System.out.println(/*de + " :: " +*/ pred + " == " + actual);
        }
        return (double) correct / data.size();
    }

    public class ClassCondProb {
        public double[][] _logprob; // For each class and attribute,
        int _attr_index;
        // a probability that sums to 1

        public ClassCondProb(int index) {
            _attr_index = index;
        }

        public String toString() {
            StringBuffer sb = new StringBuffer();
            ArffData.Attribute a = arffData._attr.get(_attr_index);
            ArffData.Attribute ca = arffData._attr.get(classIndex);
            if (_attr_index == classIndex) {
                for (int cv = 0; cv < ca.class_vals.size(); cv++) {
                    sb.append("P( ").append(ca.name).append(" = ").append(ca.getClassName(cv)).append(" ) = ").append(DECIMAL_FORMAT.format(Math.exp(_logprob[cv][0]))).append("\n");
                }
            } else {
                for (int cv = 0; cv < ca.class_vals.size(); cv++) {
                    for (int av = 0; av < a.class_vals.size(); av++) {
                        sb.append("P( ").append(a.name).append(" = ").append(a.getClassName(av)).append(" | ").append(ca.name).append(" = ").append(ca.getClassName(cv)).append(" ) = ").append(DECIMAL_FORMAT.format(Math.exp(_logprob[av][cv]))).append("\n");
                    }
                }
            }
            return sb.toString();
        }
    }

}
