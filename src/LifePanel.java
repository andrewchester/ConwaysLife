import java.awt.Graphics; 
import java.awt.Point;
import java.util.ArrayList;
import javax.swing.JPanel;
/*
 * This class is the jpanel that handles drawing the cells and the very clunky neighbors method.
 * Also has all the logic for the next generation and which cell lives and dies. 
 */
public class LifePanel extends JPanel{
	Cell[][] cells;
	ArrayList<Cell> alive_cells;
	ArrayList<Cell> dead_cells;
	//You need these two arrays because you can't test if a cell is alive and then remove it in the same for loop, you need to add it to a separate array and then remove that array from the original array
	ArrayList<Cell> alive_cells_to_remove;
	ArrayList<Cell> dead_cells_to_remove;
	LifePanel(Cell[][] cells){
		this.cells = cells;
		alive_cells = new ArrayList<Cell>();
		dead_cells = new ArrayList<Cell>();
		alive_cells_to_remove = new ArrayList<Cell>();
		dead_cells_to_remove = new ArrayList<Cell>();
	}
	
	//Drawing the cells to the panel
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		for(int x = 0; x < cells.length; x++)
			for(int y = 0; y < cells[0].length; y++)
				cells[x][y].draw(g);
	}
	//This is called when you click on the panel, if you clicked on a cell it'll toggle whether the cell is alive or not
	public void clicked(int x, int y){
		Point click = new Point(x,y);
		for(int x1 = 0; x1 < cells.length; x1++){
			for(int y1 = 0; y1 < cells[0].length; y1++){
				if(cells[x1][y1].getBounds().contains(click)){
					cells[x1][y1].toggleLife();
				}
			}
		}
		this.repaint();
	}
	//I'm sure there's a way better way to do this, I just went with the clunky method of individually declaring each neighbor if it was on the edge of the panel
	public Cell[] getNeighbors(int xPos, int yPos){
		int x_max = cells.length - 1;
		int y_max = cells[0].length - 1;
		
		Cell[] neighbors = new Cell[8];
		
		if(xPos == 0 && yPos == 0){
			neighbors[0] = cells[x_max][y_max]; 
			neighbors[1] = cells[0][y_max];
			neighbors[2] = cells[1][y_max];
			neighbors[3] = cells[xPos + 1][yPos];
			neighbors[4] = cells[xPos + 1][yPos + 1];
			neighbors[5] = cells[xPos][yPos + 1];
			neighbors[6] = cells[x_max][1];
			neighbors[7] = cells[x_max][0];
		}else if(xPos == 0 && yPos != 0 && yPos != y_max){
			neighbors[0] = cells[x_max][yPos - 1];
			neighbors[1] = cells[xPos][yPos - 1];
			neighbors[2] = cells[xPos + 1][yPos - 1];
			neighbors[3] = cells[xPos + 1][yPos];
			neighbors[4] = cells[xPos + 1][yPos + 1];
			neighbors[5] = cells[xPos][yPos + 1];
			neighbors[6] = cells[x_max][yPos + 1];
			neighbors[7] = cells[x_max][yPos];
		}else if(xPos == 0 && yPos == y_max){
			neighbors[0] = cells[x_max][yPos - 1];
			neighbors[1] = cells[xPos][yPos - 1];
			neighbors[2] = cells[xPos + 1][yPos - 1];
			neighbors[3] = cells[xPos + 1][yPos];
			neighbors[4] = cells[xPos + 1][0];
			neighbors[5] = cells[xPos][0];
			neighbors[6] = cells[x_max][0];
			neighbors[7] = cells[x_max][y_max];
		}else if(xPos == x_max && yPos == 0){
			neighbors[0] = cells[xPos - 1][y_max];
			neighbors[1] = cells[xPos][y_max];
			neighbors[2] = cells[0][y_max];
			neighbors[3] = cells[0][0];
			neighbors[4] = cells[0][yPos + 1];
			neighbors[5] = cells[xPos][yPos + 1];
			neighbors[6] = cells[xPos - 1][yPos + 1];
			neighbors[7] = cells[xPos - 1][yPos];
		}else if(xPos != 0 && xPos != x_max && yPos == 0){
			neighbors[0] = cells[xPos - 1][y_max];
			neighbors[1] = cells[xPos][y_max];
			neighbors[2] = cells[xPos + 1][y_max];
			neighbors[3] = cells[xPos + 1][yPos];
			neighbors[4] = cells[xPos + 1][yPos + 1];
			neighbors[5] = cells[xPos][yPos + 1];
			neighbors[6] = cells[xPos - 1][yPos + 1];
			neighbors[7] = cells[xPos - 1][yPos];
		}else if(xPos == x_max && yPos == y_max){
			neighbors[0] = cells[xPos - 1][yPos - 1];
			neighbors[1] = cells[xPos][yPos - 1];
			neighbors[2] = cells[0][yPos - 1];
			neighbors[3] = cells[0][yPos];
			neighbors[4] = cells[0][0];
			neighbors[5] = cells[xPos][0];
			neighbors[6] = cells[xPos - 1][0];
			neighbors[7] = cells[xPos - 1][yPos];
		}else if(xPos == x_max && yPos != 0 && yPos != y_max){
			neighbors[0] = cells[xPos - 1][yPos - 1];
			neighbors[1] = cells[xPos][yPos - 1];
			neighbors[2] = cells[0][yPos - 1];
			neighbors[3] = cells[0][yPos];
			neighbors[4] = cells[0][yPos + 1];
			neighbors[5] = cells[xPos][yPos + 1];
			neighbors[6] = cells[xPos - 1][yPos + 1];
			neighbors[7] = cells[xPos - 1][yPos];
		}else if(xPos != x_max && xPos != 0 && yPos == y_max){
			neighbors[0] = cells[xPos - 1][yPos - 1];
			neighbors[1] = cells[xPos][yPos - 1];
			neighbors[2] = cells[xPos + 1][yPos - 1];
			neighbors[3] = cells[xPos + 1][yPos];
			neighbors[4] = cells[xPos + 1][0];
			neighbors[5] = cells[xPos][0];
			neighbors[6] = cells[xPos - 1][0];
			neighbors[7] = cells[xPos - 1][yPos];
		}else{
			neighbors[0] = cells[xPos - 1][yPos - 1];
			neighbors[1] = cells[xPos][yPos - 1];
			neighbors[2] = cells[xPos + 1][yPos - 1];
			neighbors[3] = cells[xPos + 1][yPos];
			neighbors[4] = cells[xPos + 1][yPos + 1];
			neighbors[5] = cells[xPos][yPos + 1];
			neighbors[6] = cells[xPos - 1][yPos + 1];
			neighbors[7] = cells[xPos - 1][yPos];
		}
		
		return neighbors;
	}
	//Decides if a cell should be alive next round based on the number of alive neighbors, <2 it dies, 2-3 it lives, >=4 its dead, and it comes back to life if it has 3 alive neighbors
	public boolean aliveNext(Cell cell) {
		Cell[] neighbors = getNeighbors(cell.getXPos(), cell.getYPos());
		int alive_neighbors = 0;
		for(int i = 0; i < neighbors.length; i++) 
			if(neighbors[i].getLife())
				alive_neighbors++;
		
		if(cell.getLife()){
			if(alive_neighbors == 2 || alive_neighbors == 3)
				return true;
			else if(alive_neighbors >= 4)
				return false;
			else if(alive_neighbors < 2)
				return false;
			else
				return false;
			
		}else{
			if(alive_neighbors == 3)
				return true;
			else
				return false;
			
		}
	}
	//Updates the 2d array of cells, this is called when you step 1 generation and during the loop
	public void update() {
		//Goes through all of the cells, sorts the alive ones into alive_cells and the dead ones into dead_cells
		for(int i = 0; i < cells.length; i++) 
			for(int I = 0; I < cells[0].length; I++) 
				if(cells[i][I].getLife()) 
					alive_cells.add(cells[i][I]);
				else
					dead_cells.add(cells[i][I]);
		
		//If there's a dead cell in alive_cells, remove it
		for(Cell cell : alive_cells){
			if(!cell.getLife()){
				alive_cells_to_remove.add(alive_cells.get(alive_cells.indexOf(cell)));
			}
			cell.setAliveNext(aliveNext(cell));
		}
		//If there's an alive cell in dead_cells, remove it
		for(Cell cell : dead_cells){
			if(cell.getLife()){
				dead_cells_to_remove.add(dead_cells.get(dead_cells.indexOf(cell)));
			}
			cell.setAliveNext(aliveNext(cell));
		}
		//The two methods above also update each cell's aliveNext variable, it works since theoretically every single cell should be in those two arrays
		
		//Cleans up the arrays, removing dead cells from alive_cells, alive cells from dead_cells, and clearing the arrays used to keep track of cells to remove
		alive_cells.removeAll(alive_cells_to_remove);
		dead_cells.removeAll(dead_cells_to_remove);
		alive_cells_to_remove.removeAll(alive_cells_to_remove);
		dead_cells_to_remove.removeAll(dead_cells_to_remove);
		
		//After each cell's fate is decided for next round, it sets its current status to what its status is going to be next round
		for(int i = 0; i < cells.length; i++){
			for(int I = 0; I < cells[0].length; I++){
				cells[i][I].setLife(cells[i][I].getAliveNext());
			}
		}
		this.repaint();
	}
	
	public ArrayList<Cell> getAliveCells(){return alive_cells;}
	public ArrayList<Cell> getDeadCells() {return dead_cells;}
}
