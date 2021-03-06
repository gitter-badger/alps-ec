package ec.alps.util;

import ec.EvolutionState;
import ec.Individual;
import ec.Initializer;
import ec.Population;
import ec.util.Parameter;

import java.util.ArrayList;

/**
 * @author Anthony Awuley
 * @version 1.0
 */
public class Operations {

    public static final String POP_SIZE = "size";

    /**
     * Empties the individuals specifield individuals in a subpopulation
     *
     * @param individuals population from a subpopulation
     * @return
     */
    public static Individual[] emptyPop(Individual[] individuals) {
        ArrayList<Individual> dummy = listArrayAdaptor(individuals);
        @SuppressWarnings("unchecked")
        ArrayList<Individual> deleteList = (ArrayList<Individual>) dummy.clone();

        for (int id = 0; id < deleteList.size(); id++)
            dummy.remove(deleteList.get(id));

        return arrayListAdaptor(dummy);
    }

    /**
     * this deletes deletesList from subpopulation individuals
     *
     * @param individuals individuals in a subpopulation
     * @param deleteList  arrayList to delete
     * @return
     */
    public static Individual[] emptyPop(Individual[] individuals, ArrayList<Individual> deleteList) {
        ArrayList<Individual> dummy = listArrayAdaptor(individuals);
        for (int id = 0; id < deleteList.size(); id++)
            dummy.remove(deleteList.get(id));

        return arrayListAdaptor(dummy);
    }


    /**
     * @param Individuals
     * @return
     */
    private static ArrayList<Individual> listArrayAdaptor(Individual[] individuals) {
        ArrayList<Individual> list = new ArrayList<>();
        for (Individual i : individuals)
            list.add(i);
        return list;
    }


    /**
     * @param individuals
     * @return
     */
    private static Individual[] arrayListAdaptor(ArrayList<Individual> individuals) {
        Individual[] list = new Individual[individuals.size()];
        for (int i = 0; i < individuals.size(); i++)
            list[i] = individuals.get(i);

        return list;
    }


    /**
     * Calculate population size of all sub-populations in a layer.
     * This only represents current evolving population. Which means if there is inter layer individual movement
     * without filling, a subpopulation might have lower than expected full population size as specified in
     * parameter file
     *
     * @param state
     * @return current population size of evolving population
     */
    public static int activePopulaton(EvolutionState state) {
        int total = 0;
        for (int x = 0; x < state.population.subpops.length; x++)
            total += state.population.subpops[x].individuals.length;

        return total;
    }

    /**
     * calculates from parameter file the total expected population for all subpopulations
     * This is mostly useful when performing steadyStateEvolution
     *
     * @param state
     * @return total parameter population size for all subpopulations
     */
    public static int expectedPopulation(EvolutionState state) {
        int total = 0;
        for (int sub = 0; sub < state.population.subpops.length; sub++) {
            total += state.parameters.getInt(new Parameter(Initializer.P_POP).
                    push(Population.P_SUBPOP).push(sub + "").push(POP_SIZE), null);
        }

        return total;

    }


}
