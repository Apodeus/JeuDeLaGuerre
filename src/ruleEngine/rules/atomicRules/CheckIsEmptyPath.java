package ruleEngine.rules.atomicRules;

import game.board.Board;
import ruleEngine.GameAction;
import ruleEngine.IRule;
import ruleEngine.RuleResult;
import game.gameMaster.GameState;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class CheckIsEmptyPath implements IRule {

    private int length;

    private Vertex[][] initMap(int MP, GameAction.Coordinates src){
        length = 2 * MP + 1;
        Vertex[][] map = new Vertex[length][length];
        for(int i = 0; i < length; i++){
            for(int j = 0; j < length; j++){
                map[i][j] = new Vertex(src.getX() - MP + i, src.getY() - MP + j, i, j);
            }
        }
        return map;
    }

    @Override
    public boolean checkAction(Board board, GameState state, GameAction action, RuleResult result) {
        GameAction.Coordinates src = action.getSourceCoordinates();
        GameAction.Coordinates target = action.getTargetCoordinates();

        int MP = board.getUnit(src.getX(), src.getY()).getUnit().getMovementValue();

        Vertex[][] map = initMap(MP, src);

        List<Vertex> queue = new LinkedList<>();

        map[length/2][length/2].isMarked = true;
        map[length/2][length/2].dist = 0;
        Vertex actual = map[length/2][length/2];
        queue.add(actual);
        while(!queue.isEmpty()){
            actual = queue.remove(0);
            //If the cell we check has the same coords than the target cell
            //  and it has the right distance, we win, return true.
            if(actual.x == target.getX() && actual.y == target.getY() && actual.dist <= MP){
                return true;
            }
            for(int i = actual.i - 1; i <= actual.i + 1; i++){
                for(int j = actual.j - 1; j <= actual.j + 1; j++) {

                    //Don't add the neighbours with invalid coords or those we have already added
                    if (i < 0 || j < 0 || i >= length || j >= length){
                        continue;
                    }

                    int x = map[i][j].x;
                    int y = map[i][j].y;

                    if (board.edge(x, y) || map[i][j].isMarked) {
                        continue;
                    }
                    //A cell containing a unit isn't valid to find the path
                    if(board.getUnit(x, y) != null) {
                        continue;
                    }
                    //If there is building and it's a mountain, we can't add it
                    if(board.getBuilding(x, y) != null && board.getBuilding(x, y).getBuilding().isAccessible()) {
                        continue;
                    }

                    if(x == target.getX() && y == target.getY() && actual.dist <= MP){
                        return true;
                    }

                    //Just create the valid neighbour, with dist + 1
                    map[i][j].isMarked = true;
                    map[i][j].dist = actual.dist + 1;
                    Vertex v = map[i][j];

                    //If the neighbours is at a distance > MP, we loose
                    /*
                    if(v.dist > MP)
                        continue;
                        */
                    queue.add(v);
                }
            }
        }
        result.addMessage(this, "There is no empty path found.");
        result.invalidate();
        return false;
    }

    private class Vertex{
        private int x, y, i , j;
        private boolean isMarked = false;
        int dist;
        Vertex(int x, int y, int i, int j){
            this.x = x;
            this.y = y;
            this.i = i;
            this.j = j;
        }
    }
}
