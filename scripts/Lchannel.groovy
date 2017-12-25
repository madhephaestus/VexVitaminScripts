import com.neuronrobotics.bowlerstudio.vitamins.Vitamins;
import eu.mihosoft.vrl.v3d.parametrics.*;

CSG getLChannel() {
    String type = "vexLchannel"
    if (args==null) {
    	args=["Steel 5"]
    }
    StringParameter size = new StringParameter(type + " Default",
    									args.get(0),
    									Vitamins.listVitaminSizes(type))
    									
    HashMap<String,Object> vexConfig = Vitamins.getConfiguration(type, size.getStrValue())

    double thickness = vexConfig.thickness
    double holeWidth = 4.6609, holeThinGap = 1.6764;
    double holeXInset = 4.1, holeYInset = 2.8;
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
    
    //2 wide has offset holes
    if (holeCountWidth == 2) {
        yHoles = yHoles.union(hole.movey(holeWidth + holeThinGap).movex(holeWidth + holeThinGap));
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
    
    CSG side = plate.mirrory().rotx(90).movez(thickness);
    plate = plate.movey(thickness).union(side);
    
    CSG cornerFill = new Cube(plate.getTotalX(), thickness, thickness).toCSG();
    cornerFill = cornerFill.movex(-cornerFill.getMinX()).movey(-cornerFill.getMinY()).movez(-cornerFill.getMinZ());
    plate = plate.union(cornerFill);
    
    return plate;
}

return getLChannel()
