package reversi;

/**
 * Extend this abstract class for human/ AI agent
 *
 * @author Azad
 *
 */
public abstract class Agent {

    String name; // Name of the agent
    int role; // This is important, Each agent will be assigned a role beforehand. 
    //For example, for tick tack toe X will be assigned to one agent, and 0 will be assigned to another agent
    int score;			// The roles are stored as integer. 

    public Agent(String name) {
        // TODO Auto-generated constructor stub
        this.name = name;

    }

    /**
     * Sets the role of this agent. Typically will be called by your extended
     * Game class (The class which extends the Game Class).
     *
     * @param role
     */
    public void setRole(int role) {
        this.role = role;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getName() {
        return name;
    }

    public int getRole() {
        return role;
    }

    public int getScore() {
        return score;
    }

    /**
     * Implement this method to select a move, and change the game state
     * according to the chosen move.
     *
     * @param game
     */
    public abstract int makeMove(Game game);

    void increaseScore(int flipCount) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        score += flipCount;
    }

    void decreaseScore(int flipCount) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        score -= flipCount;
    }

}
