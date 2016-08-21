package com.deckerchan.ml.classifier.k;

import com.deckerchan.ml.classifier.entities.Cluster;
import com.deckerchan.ml.classifier.entities.RealItemHDPoint;
import com.deckerchan.ml.classifier.utils.PointUtils;
import com.deckerchan.ml.classifier.utils.RandomUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public abstract class KClassifierBase {
    private int kValue;
    private int maxiumAttempt;
    private List<Cluster> clusters;
    private List<Cluster> lastClusters;
    private List<RealItemHDPoint> itemPoints;

    public KClassifierBase(int kValue, List<RealItemHDPoint> itemPoints) {
        this.kValue = kValue;
        this.itemPoints = itemPoints;
        this.clusters = new ArrayList<>();
        this.initiallizeClusters();
    }

    private void initiallizeClusters() {

        Set<Integer> randomIndexSet = RandomUtils.generateRandomIntegers(this.kValue, itemPoints.size());

        this.clusters = randomIndexSet.stream().map(index -> new Cluster(this.itemPoints.get(index).getDimensionValueMap())).collect(Collectors.toList());
    }

    public int getkValue() {
        return kValue;
    }

    protected void setkValue(int kValue) {
        this.kValue = kValue;
    }

    public List<RealItemHDPoint> getItemPoints() {
        return itemPoints;
    }

    protected void setItemPoints(List<RealItemHDPoint> itemPoints) {
        this.itemPoints = itemPoints;
    }

    public List<Cluster> getClusters() {
        return clusters;
    }

    protected void setClusters(List<Cluster> clusters) {
        this.clusters = clusters;
    }

    protected void doClassify() {

        this.clusters.parallelStream().forEach(e -> e.getRelatedPointWithDistance().clear());

        for (RealItemHDPoint itemPoint :
                this.getItemPoints()) {
            Cluster nearClusterPoint = itemPoint.chooseNearestClusterPoint(this.clusters);
            nearClusterPoint.getRelatedPointWithDistance().put(itemPoint, PointUtils.pointDistance(nearClusterPoint, itemPoint));

        }
    }

    protected abstract void doBalance();

    protected boolean isStablized() {
        if (this.clusters.equals(this.lastClusters)) {
            return true;
        } else {
            this.lastClusters = new ArrayList<>(this.clusters);
            return false;
        }
    }

    public void calculate(int maxiumAttempt) {

        for (int attempt = 0; (!isStablized()) && attempt < maxiumAttempt; attempt++) {
            System.out.printf("Attempt %d%n",attempt);
            this.doClassify();
            this.doBalance();
        }

    }

    public String report() {
        if (!this.isStablized()) {
            return "Unestablished classify. N/A report.";
        } else {
            StringBuilder report = new StringBuilder();

            for (Cluster cluster : this.clusters) {
                report.append(cluster.toString());
            }
            return report.toString();
        }

    }

    public int getMaxiumAttempt() {
        return maxiumAttempt;
    }

    protected void setMaxiumAttempt(int maxiumAttempt) {
        this.maxiumAttempt = maxiumAttempt;
    }


}
