
import java.util.Map;

public class PathFinding{
/**	
	//Il faut vérifier les mouvements
	//Il faut finir la méthode getAdjacentSquare()
	
	private Map<Coordinates,Square> shortReachableSquare;
	private Map<Coordinates,Square> longReachableSquare;
		
	
	public PathFinding(){
		this.shortReachableSquare = new HashMap<>();
		this.longReachableSquare = new HashMap<>();
	}
	
	
	public Map<Coordinates,Square> getAdjacentSquare(Coordinates c){
		Map<Coordinates,Square> adjacentSquare = new HashMap<>();
		
		//A terminer
		
		
	}
	
	public void getFirstReachableSquare(Coordinates c){
		Map<Coordinates,Square> adjacentSquare1 = getAdjacentSquare(c);
		for(Coordinates c1 : adjacentSquare1){
			if(adjacentSquare1.get(c1).isFree)
				this.shortReachableSquare.put(c1,adjacentSquare1.get(c1));
			else if(!adjacentSquare.get(c1).isFree){
				Map<Coordinates,Square> adjacentSquare2 = getAdjacentSquare(c1);
				for(Coordinates c2 : adjacentSquare2){
					if(adjacentSquare2.get(c2).isFree)
						this.longReachableSquare.put(c2,adjacentSquare2.get(c2));
				}				
			}	
		}		
	}
	
	public void getLongReachableSquare(Coordinates c){
		//this.longReachableSquare.clear(); si on clear on fera case par case sinon on aura toutes les cases.
		Map<Coordinates,Square> adjacentSquare1 = getAdjacentSquare(c);
		for(Coordinates c1 : adjacentSquare1){
			Map<Coordinates,Square> adjacentSquare2 = getAdjacentSquare(c1);
			for(Coordinates c2 : adjacentSquare2){
				if(adjacentSquare2.get(c2).isFree)
					this.longReachableSquare.put(c2,adjacentSquare2.get(c2));			
			}		
		
		}	
		
		
	}
**/	
}