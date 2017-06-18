
class JavatarVirus extends DiseaseBlueprint {
    
    @Override
    public String getName() {
        return "Javatar Virus";
    }
    
    @Override
    public double getInfectivity(AgeGroup ageGroup) {
        return 0;
    }
    
    @Override
    public double getToxigenicity(AgeGroup ageGroup) {
        return 0;
    }
    
    @Override
    public double getResistance(AgeGroup ageGroup) {
        return 0;
    }
    
    
    private int lastEnergy = 0;
    private boolean passedLatency = false;
    private boolean passedIncubation = false;
    
    @Override
    public DiseaseAction move(SimulatedHost host) {
        DiseaseAction action = DiseaseAction.MULTIPLY;
        
        int days = host.getDaysSinceInfection();
        // virus stage > inital > latent > incubated > death
        // inital - can only multiply
        // given that the latent threshold is smaller than the incubated threshold
        // latent - can multiply and exit the host
        // incubated - can do all three actions
        
        // bacteria will die no eventually, no matter which stages are achieved
        // multiplying is the only way to get the latent, then incubated stages
        // energy cost is greater the more bacteria there are in the host
        // exit cost is cheaper than toxin cost, but will halt multiplying

        if (host.isIncubated()) {
            if (!passedIncubation) {
                System.out.println("Bacteria passed incubation in " + days + " days.");
                passedIncubation = true;
            }
            // since the disease doesn't know its death date, this will cycle through, release, exit, and multiply
            // a better way can be to record the energy use of each action and derive the EXIT and TOXIN constants
            if (days % 5 == 0) {
                action = DiseaseAction.RELEASE;
            }
            else if (days % 5 == 1) {
                action = DiseaseAction.EXIT;
            }
        }
        else if (host.isLatent()) {
            if (!passedLatency) {
                System.out.println("Bacteria passed latency in " + days + " days.");
                passedLatency = true;
            }
            
            if (days % 10 == 0) {
                action = DiseaseAction.EXIT;
            }
        }
        
        // The goal is to balance multiply, exit, and release so that the bacteria's life is near
        // but not greater than its maximum number of days of survival in the host
        
        // I feel like there are missing classes and code that would make this simulated more complex
        lastEnergy = host.getEnergy();
        return action;
    }
    
}