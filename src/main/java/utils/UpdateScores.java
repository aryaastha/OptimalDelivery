package utils;

import beans.OrderAssignment;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by astha.a on 15/02/18.
 */
public class UpdateScores {
    private HashMap<OrderAssignment, Double> updatedScores;

    public UpdateScores() {
        this.updatedScores = new HashMap<>();
    }

    public void updateScores(ArrayList<Pair<OrderAssignment, Double>> attributeScore, Double weight){
        for (Pair<OrderAssignment, Double> assign : attributeScore) {
            if (this.updatedScores.containsKey(assign.getKey())){
                this.updatedScores.put(assign.getKey(),assign.getValue()+this.updatedScores.get(assign.getKey()));
            }else this.updatedScores.put(assign.getKey(),assign.getValue());
        }
    }

    public ArrayList<Pair<OrderAssignment, Double>> getUpdatedScores(){
        ArrayList<Pair<OrderAssignment, Double>> allCombinationScores = new ArrayList<>();
        for(Map.Entry<OrderAssignment, Double> entries : updatedScores.entrySet()){
            allCombinationScores.add(new Pair<>(entries.getKey(),entries.getValue()));
        }
        return allCombinationScores;
    }
}
