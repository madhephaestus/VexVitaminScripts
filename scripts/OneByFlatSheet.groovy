import com.neuronrobotics.bowlerstudio.vitamins.Vitamins;
import eu.mihosoft.vrl.v3d.parametrics.*;

CSG getFlat() {
    String type = "vexFlatSheet"
    if (args==null) {
    	args=["Steel 1x5"]
    }
    StringParameter size = new StringParameter(type + " Default",
    									args.get(0),
    									Vitamins.listVitaminSizes(type))
    									
    HashMap<String,Object> vexConfig = Vitamins.getConfiguration(type, size.getStrValue())

    double thickness = vexConfig.thickness
    double holeWidth = 4.6609;
    double holeXInset = 3.3, holeYInset = 3.3;
    double pitch = 12.7;
    
    int holeCountLength = (int) vexConfig.holeCountLength
    int holeCountWidth = (int) vexConfig.holeCountWidth
    
    CSG plate = new Cube(holeCountLength * pitch, holeCountWidth * pitch - thickness, thickness).toCSG();
    plate = plate.movex(-plate.getMinX()).movey(-plate.getMinY()).movez(-plate.getMinZ());
    
    //Build y holes
    CSG hole = new Cube(holeWidth, holeWidth, thickness).toCSG();
    hole = hole.movex(-hole.getMinX()).movey(-hole.getMinY()).movez(-hole.getMinZ());
    
    CSG yHoles = hole.clone();
    
    //Lined up holes
    for (int i = 1; i < holeCountWidth; i++) {
        yHoles = yHoles.union(hole.movey(i * pitch));
    }
    
    //Holes are inset from the side
    yHoles = yHoles.movey(plate.getMaxY() - yHoles.getMaxY());
    yHoles = yHoles.movex(holeXInset).movey(-holeYInset);
    
    //Build hole set
    CSG holeSet = yHoles.clone();
    
    for (int i = 1; i < holeCountLength; i++) {
        holeSet = holeSet.union(yHoles.movex(i * pitch));
    }
    
    plate = plate.difference(holeSet);
    
    //Center corner at (0, 0)
    plate = plate.movey(-plate.getMinY());
    plate.setParameter(size);
    plate.setRegenerate({
    	return getFlat()
    })
}

return getFlat()
